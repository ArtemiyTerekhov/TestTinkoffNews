package terekhov.artemiy.testtinkoffnews.data.repository.news;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;
import terekhov.artemiy.testtinkoffnews.data.repository.DataStore;
import terekhov.artemiy.testtinkoffnews.di.component.DaggerDataStoreComponent;
import terekhov.artemiy.testtinkoffnews.di.component.DataStoreComponent;
import terekhov.artemiy.testtinkoffnews.di.module.DataStoreModule;

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

    public Observable<List<NewsEntity>> getNews(@DataStore.RequestType int type) {
        switch (type) {
            case DataStore.TYPE_REQUEST_LOCAL:
                return mLocalDataStore.getNews();
            case DataStore.TYPE_REQUEST_REMOTE:
            default:
                return mRemoteDataStore.getNews();
        }
    }

    public Observable<List<NewsEntity>> saveNews(
            @NonNull List<NewsEntity> news, @DataStore.RequestType int type) {
        return type == DataStore.TYPE_REQUEST_REMOTE
                ? mLocalDataStore.saveNews(news)
                : Observable.just(news);
    }

    public Observable<NewsContentEntity> getNewsContent(
            @NonNull String id, @DataStore.RequestType int type) {
        switch (type) {
            case DataStore.TYPE_REQUEST_LOCAL:
                return mLocalDataStore.getNewsContent(id);
            case DataStore.TYPE_REQUEST_REMOTE:
            default:
                return mRemoteDataStore.getNewsContent(id);
        }
    }

    public Observable<NewsContentEntity> saveNewsContent(@NonNull String id,
            @NonNull NewsContentEntity newsContentEntity, @DataStore.RequestType int type) {
        return type == DataStore.TYPE_REQUEST_REMOTE
                ? mLocalDataStore.saveNewsContent(id, newsContentEntity)
                : Observable.just(newsContentEntity);
    }
}
