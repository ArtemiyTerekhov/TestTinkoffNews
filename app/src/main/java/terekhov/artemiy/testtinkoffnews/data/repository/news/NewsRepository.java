package terekhov.artemiy.testtinkoffnews.data.repository.news;

import android.support.annotation.NonNull;

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

    public void registerNewsObserver(DataObserver<List<NewsEntity>> dataObserver) {
        mLocalDataStore.registerNewsObserver(dataObserver);
    }

    public void unregisterNewsObserver() {
        mLocalDataStore.unregisterNewsObserver();
    }

    @DebugLog
    public Flowable<List<NewsEntity>> getNews() {
        return mLocalDataStore.getNews();
    }

    @DebugLog
    public Completable saveNews(@NonNull List<NewsEntity> news) {
        return mLocalDataStore.saveNews(news);
    }

    public Single<NewsContentEntity> getNewsContent(@NonNull String id) {
        return mLocalDataStore.getNewsContent(id);
    }

    public Completable saveNewsContent(@NonNull String id, @NonNull NewsContentEntity newsContentEntity) {
        return mLocalDataStore.saveNewsContent(id, newsContentEntity);
    }

    public Completable syncNews() {
        return Completable.merge(
                mRemoteDataStore.getNews().map(this::saveNews)
        );
    }

    public Completable syncNewsContent(@NonNull String id) {
        return Completable.merge(
                mRemoteDataStore.getNewsContent(id).toFlowable()
                        .map(entity -> saveNewsContent(id, entity))
        );
    }

    public void testChangeItem() {
        mLocalDataStore.testChangeItem();
    }
}
