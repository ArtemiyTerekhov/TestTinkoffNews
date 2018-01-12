package terekhov.artemiy.testtinkoffnews;

import android.app.Application;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import terekhov.artemiy.testtinkoffnews.di.component.AppComponent;
import terekhov.artemiy.testtinkoffnews.di.component.DaggerAppComponent;
import terekhov.artemiy.testtinkoffnews.di.module.AppModule;
import terekhov.artemiy.testtinkoffnews.di.module.ScheduleModule;
import terekhov.artemiy.testtinkoffnews.presentation.IAppNavigation;
import terekhov.artemiy.testtinkoffnews.presentation.NavigationManager;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class App extends Application {
    private static AppComponent mAppComponent;
    private volatile boolean mIsNetworkConnected;
    private boolean mNetworkInit = false; // Only for test app
    private Observable<Connectivity> mNetworkObservable;
    private CompositeDisposable mDisposables;

    private static IAppNavigation mNavigationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mDisposables = new CompositeDisposable();

        if (Utils.isDebug()) {
            ButterKnife.setDebug(BuildConfig.DEBUG);
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
        return mIsNetworkConnected || !mNetworkInit;
    }

    public void subscribe() {
        mNetworkObservable.subscribe(connectivity -> {
            final NetworkInfo.State state = connectivity.getState();
            setNetworkConnected(state == NetworkInfo.State.CONNECTED);
            mNetworkInit = true;
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

    public boolean isNetworkInit() {
        return mNetworkInit;
    }
}
