package terekhov.artemiy.testtinkoffnews.data.repository.news;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import io.objectbox.reactive.DataObserver;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;
import terekhov.artemiy.testtinkoffnews.data.repository.DataStore;
import terekhov.artemiy.testtinkoffnews.di.component.DaggerDataStoreComponent;
import terekhov.artemiy.testtinkoffnews.di.component.DataStoreComponent;
import terekhov.artemiy.testtinkoffnews.di.module.DataStoreModule;
import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;
import terekhov.artemiy.testtinkoffnews.domain.model.News;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsRepository {
    @Inject
    NewsLocalDataStore mLocalDataStore;
    @Inject
    NewsRemoteDataStore mRemoteDataStore;

    public NewsRepository() {
        DataStoreComponent component = DaggerDataStoreComponent.builder()
                .dataStoreModule(new DataStoreModule())
                .build();
        component.inject(this);
    }

    public void registerNewsObserver(@Nullable DataObserver<List<NewsEntity>> dataObserver) {
        mLocalDataStore.registerNewsObserver(dataObserver);
    }

    public void unregisterNewsObserver() {
        mLocalDataStore.unregisterNewsObserver();
    }

    public void registerNewsContentObserver(
            @NonNull String id, @Nullable DataObserver<List<NewsContentEntity>> dataObserver) {
        mLocalDataStore.registerNewsContentObserver(id, dataObserver);
    }

    public void unregisterNewsContentObserver() {
        mLocalDataStore.unregisterNewsContentObserver();
    }

    @DebugLog
    public Flowable<List<NewsEntity>> getNews() {
        return mLocalDataStore.getNews();
    }

    public Single<NewsContentEntity> getNewsContent(@NonNull String id) {
        return mLocalDataStore.getNewsContent(id);
    }

    @DebugLog
    public Observable<Boolean> saveNews(@NonNull final List<NewsEntity> news) {
        return mLocalDataStore.saveNews(news);
    }

    public Observable<Boolean> saveNewsContent(@NonNull String id, @NonNull NewsContentEntity newsContentEntity) {
        return mLocalDataStore.saveNewsContent(id, newsContentEntity);
    }

    public Observable<Boolean> syncNews() {
        return mRemoteDataStore.getNews().toObservable().flatMap(this::saveNews);
    }

    public Observable<Boolean> syncNewsContent(@NonNull String id) {
        return mRemoteDataStore.getNewsContent(id).toFlowable().toObservable()
                .flatMap(entity -> saveNewsContent(id, entity));
    }

    public void testChangeItem() {
        mLocalDataStore.testChangeItem();
    }
}
