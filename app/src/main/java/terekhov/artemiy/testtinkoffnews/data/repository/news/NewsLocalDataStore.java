package terekhov.artemiy.testtinkoffnews.data.repository.news;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import hugo.weaving.DebugLog;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.query.QueryFilter;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;
import io.objectbox.reactive.DataSubscriptionList;
import io.objectbox.reactive.ErrorObserver;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.data.db.RxSnappyClient;
import terekhov.artemiy.testtinkoffnews.data.db.exception.MissingDataException;
import terekhov.artemiy.testtinkoffnews.data.entities.BaseEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity_;
import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsLocalDataStore implements AbsNewsLocalDataStore {
    private BoxStore mBoxStore;

    private Box<NewsEntity> newsBox;
    private Query<NewsEntity> newsQuery;
    private DataSubscription mSubscription;

    public NewsLocalDataStore() {
        mBoxStore = App.getAppComponent().boxStore();
        newsBox = mBoxStore.boxFor(NewsEntity.class);
        newsQuery = newsBox.query().build();
        mBoxStore.boxFor(NewsEntity.class).removeAll();
    }

    public void registerNewsObserver(DataObserver<List<NewsEntity>> dataObserver) {
        mSubscription = newsQuery.subscribe()
                .onError(th -> {

                })
                .transform(data -> {
                    for (NewsEntity entity : data) {
                        entity.setPublicationDate(entity.getPublicationDateRelation().getTarget());
                    }
                    return data;
                })
                .on(AndroidScheduler.mainThread())
                .observer(dataObserver);
    }

    public void unregisterNewsObserver() {
        if (!mSubscription.isCanceled()) {
            mSubscription.cancel();
        }
    }

    @Override
    @DebugLog
    public Flowable<List<NewsEntity>> getNews() {
        List<NewsEntity> news = newsQuery.find();
        for (NewsEntity entity : news) {
            entity.setPublicationDate(entity.getPublicationDateRelation().getTarget());
        }
        return Flowable.just(news);
    }

    @Override
    @DebugLog
    public Completable saveNews(@NonNull List<NewsEntity> news) {
        for (NewsEntity entity : news) {
            entity.setPublicationDateRelation();
            entity.setPrimaryId(entity.getId().hashCode());
        }
        newsBox.put(news);

        return Completable.complete();
    }

    @Override
    public Single<NewsContentEntity> getNewsContent(@NonNull String id) {
        Query<NewsEntity> newsQuery = newsBox.query().filter(entity -> entity.getId().equals(id)).build();
        NewsEntity newsEntity = newsQuery.findFirst();
        if (newsEntity != null) {
            NewsContentEntity contentEntity = newsEntity.getNewsContentRelation().getTarget();
            return contentEntity != null ? Single.just(contentEntity) : Single.error(new MissingDataException());
        }
        return Single.error(new MissingDataException());
    }

    @Override
    public Completable saveNewsContent(
            @NonNull String id, @NonNull NewsContentEntity newsContentEntity) {
        Query<NewsEntity> newsQuery = newsBox.query().filter(entity -> entity.getId().equals(id)).build();
        NewsEntity newsEntity = newsQuery.findFirst();
        if (newsEntity != null) {
            Box<NewsContentEntity> newsContentBox = mBoxStore.boxFor(NewsContentEntity.class);
            newsContentEntity.getNewsRelation().setTarget(newsEntity);
            newsEntity.getNewsContentRelation().setTarget(newsContentEntity);
            newsContentBox.put(newsContentEntity);
            return Completable.complete();
        }

        return Completable.error(new MissingDataException());
    }

    public void testChangeItem() {
        Query<NewsEntity> query = newsBox.query()
                .filter(entity -> entity.getId().equals("10024")).build();
        List<NewsEntity> entities = query.find();
        if (!entities.isEmpty()) {
            NewsEntity entity = entities.get(0);
            //entity.setText("Измененный текст");
            //newsBox.put(entity);
            if (entity != null) {
                newsBox.remove(entity.getPrimaryId());
            }
        }
    }
}
