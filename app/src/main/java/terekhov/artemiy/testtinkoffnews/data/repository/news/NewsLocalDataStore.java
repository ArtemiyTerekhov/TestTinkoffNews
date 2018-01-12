package terekhov.artemiy.testtinkoffnews.data.repository.news;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.data.db.RxSnappyClient;
import terekhov.artemiy.testtinkoffnews.data.entities.BaseEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsLocalDataStore implements NewsDataStore {
    private final RxSnappyClient mRxSnappyClient;

    public NewsLocalDataStore() {
        mRxSnappyClient = App.getAppComponent().rxSnappyClient();
    }

    @Override
    public Observable<List<NewsEntity>> getNews() {
        return mRxSnappyClient.findKeys(KEY_NEWS_ENTITY)
                .flatMap(Observable::fromArray)
                .flatMap(this::getKey)
                .flatMap(id -> getNewsById(id).onErrorResumeNext(Observable.empty()))
                .toSortedList(this::sortPosts).toObservable();
    }

    @Override
    public Observable<List<NewsEntity>> saveNews(@NonNull List<NewsEntity> news) {
        return Observable.fromIterable(news).sorted(this::sortPosts)
                .flatMap(this::saveMergeNews).toList().toObservable();
    }

    @Override
    public Observable<NewsContentEntity> getNewsContent(@NonNull String id) {
        return getNewsContentById(id);
    }

    @Override
    public Observable<NewsContentEntity> saveNewsContent(
            @NonNull String id, @NonNull NewsContentEntity newsContentEntity) {
        return getNewsContentById(id)
                .onErrorReturnItem(newsContentEntity)
                .map(savedEntity -> BaseEntity.merge(savedEntity, newsContentEntity))
                .flatMap(entity -> saveContent(id, entity));
    }

    private int sortPosts(@NonNull NewsEntity o1, @NonNull NewsEntity o2) {
        if (o2.getPublicationDate() == null || o1.getPublicationDate() == null) return 0;
        long dateDiff = o2.getPublicationDate().getDate() - o1.getPublicationDate().getDate();
        return dateDiff == 0 ? 0 : dateDiff > 0 ? 1 : -1;
    }

    private Observable<String> getKey(@NonNull String key) {
        return mRxSnappyClient.getString(key, null);
    }

    private Observable<NewsEntity> getNewsById(String id) {
        return mRxSnappyClient.getString(KEY_NEWS_ENTITY + id, null)
                .map(json -> BaseEntity.fromJson(json, NewsEntity.class));
    }

    private Observable<NewsEntity> saveMergeNews(@NonNull NewsEntity entity) {
        return getNewsById(entity.getId())
                .onErrorReturnItem(entity)
                .map(savedEntity -> BaseEntity.merge(savedEntity, entity))
                .flatMap(this::saveNews);
    }

    private Observable<NewsEntity> saveNews(@NonNull NewsEntity entity) {
        return mRxSnappyClient.setString(KEY_NEWS_ENTITY + entity.getId(),
                BaseEntity.toJson(entity, NewsEntity.class), true)
                .map(result -> entity);
    }

    private Observable<NewsContentEntity> getNewsContentById(String id) {
        return mRxSnappyClient.getString(KEY_NEWS_CONTENT_ENTITY + id, null)
                .map(json -> BaseEntity.fromJson(json, NewsContentEntity.class));
    }

    private Observable<NewsContentEntity> saveContent(
            @NonNull String id, @NonNull NewsContentEntity entity) {
        return mRxSnappyClient.setString(KEY_NEWS_CONTENT_ENTITY + id,
                BaseEntity.toJson(entity, NewsContentEntity.class), true)
                .map(result -> entity);
    }
}
