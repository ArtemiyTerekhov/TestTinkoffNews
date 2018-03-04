package terekhov.artemiy.testtinkoffnews;

import android.app.Application;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import butterknife.ButterKnife;
import io.objectbox.BoxStore;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import terekhov.artemiy.testtinkoffnews.data.entities.MyObjectBox;
import terekhov.artemiy.testtinkoffnews.data.repository.news.NewsRepository;
import terekhov.artemiy.testtinkoffnews.di.component.AppComponent;
import terekhov.artemiy.testtinkoffnews.di.component.DaggerAppComponent;
import terekhov.artemiy.testtinkoffnews.di.module.AppModule;
import terekhov.artemiy.testtinkoffnews.di.module.ScheduleModule;
import terekhov.artemiy.testtinkoffnews.presentation.IAppNavigation;
import terekhov.artemiy.testtinkoffnews.presentation.NavigationManager;
import timber.log.Timber;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class App extends Application {
    private static AppComponent mAppComponent;
    private volatile boolean mIsNetworkConnected = true;
    private Observable<Connectivity> mNetworkObservable;
    private CompositeDisposable mDisposables;
    private BoxStore mBoxStore;
    private NewsRepository mNewsRepository;

    private static IAppNavigation mNavigationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mBoxStore = MyObjectBox.builder().androidContext(this).build();
        mDisposables = new CompositeDisposable();

        if (Utils.isDebug()) {
            ButterKnife.setDebug(BuildConfig.DEBUG);
            Timber.plant(new Timber.DebugTree());
        }

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .scheduleModule(new ScheduleModule())
                .build();

        mNetworkObservable = ReactiveNetwork.observeNetworkConnectivity(
                mAppComponent.app().getApplicationContext())
                .subscribeOn(mAppComponent.schedulerProvider().io())
                .observeOn(mAppComponent.schedulerProvider().ui())
                .doOnSubscribe(this::addDisposable)
                .share();

        mNewsRepository = mAppComponent.newsRepository();
    }

    public IAppNavigation getNavigationManager() {
        if (mNavigationManager == null) {
            mNavigationManager = new NavigationManager();
        }
        return mNavigationManager;
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

    public Observable<Connectivity> getNetworkObservable() {
        return mNetworkObservable;
    }

    public boolean isNetworkConnected() {
        return mIsNetworkConnected;
    }

    public void subscribe() {
        mNetworkObservable.subscribe(connectivity -> {
            final NetworkInfo.State state = connectivity.getState();
            setNetworkConnected(state == NetworkInfo.State.CONNECTED);
        });
    }

    public void unsubscribe() {
        clear();
    }

    private void setNetworkConnected(boolean networkConnected) {
        mIsNetworkConnected = networkConnected;
    }

    public void clear() {
        mDisposables.clear();
    }

    private void addDisposable(@NonNull Disposable disposable) {
        mDisposables.add(disposable);
    }

    public BoxStore boxStore() {
        return mBoxStore;
    }

    public NewsRepository newsRepository() {
        return mNewsRepository;
    }
}
