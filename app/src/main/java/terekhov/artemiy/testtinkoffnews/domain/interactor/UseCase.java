package terekhov.artemiy.testtinkoffnews.domain.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public abstract class UseCase<T, Params> {
    protected final Scheduler mSubscribeScheduler;
    protected final Scheduler nObserveScheduler;
    protected final CompositeDisposable mDisposables;

    protected ObservableEmitter<T> mEmitter;
    protected Observable<T> mSourceObservable;
    protected Consumer<? super Throwable> mOnError;
    protected Action mOnComplete;

    public UseCase(Scheduler subscribeScheduler, Scheduler observeScheduler) {
        mSubscribeScheduler = subscribeScheduler;
        nObserveScheduler = observeScheduler;
        mDisposables = new CompositeDisposable();
        mSourceObservable = Observable.create(this::onSourceEmitter);
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    public abstract Observable<T> buildUseCaseObservable(Params params);


    /**
     * Executes the current use case.
     *
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    public void execute(@Nullable Params params) {
        buildUseCaseObservable(params)
                .subscribeOn(mSubscribeScheduler)
                .observeOn(nObserveScheduler)
                .doOnSubscribe(this::addDisposable)
                .doAfterTerminate(this::clear)
                .subscribe(this::doOnNext, this::doOnError, this::doOnComplete);
    }

    public Observable<T> requestObservable(@Nullable Params params) {
        return buildUseCaseObservable(params)
                .subscribeOn(mSubscribeScheduler)
                .observeOn(nObserveScheduler)
                .doOnSubscribe(this::addDisposable)
                .doAfterTerminate(this::clear);
    }

    public Observable<T> getObservable() {
        return getObservable(null, null);
    }

    public Observable<T> getObservable(Consumer<? super Throwable> onError) {
        return getObservable(onError, null);
    }

    public Observable<T> getObservable(Consumer<? super Throwable> onError, Action onComplete) {
        mOnError = onError;
        mOnComplete = onComplete;
        return mSourceObservable;
    }

    /**
     * Clear from current {@link CompositeDisposable}. Using clear will clear all, but can
     * accept new disposable
     */
    public void clear() {
        mDisposables.clear();
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    protected void addDisposable(@NonNull Disposable disposable) {
        mDisposables.add(disposable);
    }

    protected void onSourceEmitter(ObservableEmitter<T> emitter) {
        mEmitter = emitter;
    }

    protected void doOnNext(T value) {
        if (!mEmitter.isDisposed()) {
            mEmitter.onNext(value);
        }
    }

    protected void doOnError(Throwable throwable) {
        if (mOnError != null) {
            try {
                mOnError.accept(throwable);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(new CompositeException(throwable, e));
            }
        }
    }

    protected void doOnComplete() {
        if (mOnComplete != null) {
            try {
                mOnComplete.run();
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(e);
            }
        }
    }
}
