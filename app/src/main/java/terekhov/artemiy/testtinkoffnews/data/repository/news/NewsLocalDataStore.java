package terekhov.artemiy.testtinkoffnews.data.repository.news;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hugo.weaving.DebugLog;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.data.db.exception.MissingDataException;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.mapper.NewsEntityDataMapper;
import terekhov.artemiy.testtinkoffnews.domain.model.News;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsLocalDataStore implements AbsNewsLocalDataStore {
    private DataSubscription subscription;
    private DataSubscription subscriptionNewsContent;

    public NewsLocalDataStore() {
        // FIXME: 03.03.2018 only for test
        BoxStore boxStore = ((App) App.getAppComponent().app()).boxStore();

        boxStore.boxFor(NewsEntity.class).removeAll();
        boxStore.boxFor(NewsContentEntity.class).removeAll();
    }

    private Box<NewsEntity> newsBox() {
        BoxStore boxStore = ((App) App.getAppComponent().app()).boxStore();
        return boxStore.boxFor(NewsEntity.class);
    }

    private Box<NewsContentEntity> newsContentBox() {
        BoxStore boxStore = ((App) App.getAppComponent().app()).boxStore();
        return boxStore.boxFor(NewsContentEntity.class);
    }

    /**
     *
     * @param dataObserver always type {@code DataObserver<List<?>>}
     */
    @Override
    public void registerNewsObserver(@Nullable DataObserver<List<NewsEntity>> dataObserver) {
        if (dataObserver != null) {
            subscription = newsBox().query().build().subscribe()
                    .onError(throwable -> Observable.error(new MissingDataException()))
                    .on(AndroidScheduler.mainThread())
                    .observer(dataObserver);
        }
    }

    @Override
    public void unregisterNewsObserver() {
        if (subscription != null
                && !subscription.isCanceled()) {
            subscription.cancel();
            subscription = null;
        }
    }

    /**
     *
     * @param id news - filter for query
     * @param dataObserver always type {@code DataObserver<List<?>>}
     */
    @Override
    public void registerNewsContentObserver(@NonNull String id, DataObserver<List<NewsContentEntity>> dataObserver) {
        if (dataObserver != null) {
            subscriptionNewsContent = newsContentBox().query().filter(entity -> {
                NewsEntity newsEntity = entity.getNewsRelation().getTarget();
                return newsEntity != null && id.equals(newsEntity.getId());
            }).build().subscribe()
                    .onError(throwable -> Observable.error(new MissingDataException()))
                    .on(AndroidScheduler.mainThread())
                    .observer(dataObserver);
        }
    }

    @Override
    public void unregisterNewsContentObserver() {
        if (subscriptionNewsContent != null
                && !subscriptionNewsContent.isCanceled()) {
            subscriptionNewsContent.cancel();
            subscriptionNewsContent = null;
        }
    }

    @Override
    @DebugLog
    public Flowable<List<NewsEntity>> getNews() {
        List<NewsEntity> news = newsBox().query().build().find();
        return Flowable.just(news);
    }

    @Override
    @DebugLog
    public Observable<Boolean> saveNews(@NonNull List<NewsEntity> news) {
        for (NewsEntity entity : news) {
            entity.setPrimaryId(entity.getId().hashCode());
        }
        newsBox().put(news);

        return Observable.just(true);
    }

    @Override
    public Single<NewsContentEntity> getNewsContent(@NonNull String id) {
        Query<NewsEntity> newsQuery = newsBox().query().filter(entity -> entity.getId().equals(id)).build();
        NewsEntity newsEntity = newsQuery.findFirst();
        if (newsEntity != null) {
            NewsContentEntity contentEntity = newsEntity.getNewsContentRelation().getTarget();
            return contentEntity != null ? Single.just(contentEntity) : Single.error(new MissingDataException());
        }
        return Single.error(new MissingDataException());
    }

    @Override
    public Observable<Boolean> saveNewsContent(
            @NonNull String id, @NonNull NewsContentEntity newsContentEntity) {
        Query<NewsEntity> newsQuery = newsBox().query().filter(entity -> entity.getId().equals(id)).build();
        List<NewsEntity> entities = newsQuery.find();
        NewsEntity newsEntity = !entities.isEmpty() ? entities.get(0) : null;
        if (newsEntity != null) {
            BoxStore boxStore = ((App) App.getAppComponent().app()).boxStore();
            Box<NewsContentEntity> newsContentBox = boxStore.boxFor(NewsContentEntity.class);
            newsContentEntity.getNewsRelation().setTarget(newsEntity);
            newsEntity.getNewsContentRelation().setTarget(newsContentEntity);
            newsContentBox.put(newsContentEntity);
            return Observable.just(true);
        }

        return Observable.error(new MissingDataException());
    }

    public void testChangeItem() {
        Query<NewsEntity> query = newsBox().query()
                .filter(entity -> entity.getId().equals("10024")).build();
        List<NewsEntity> entities = query.find();
        if (!entities.isEmpty()) {
            NewsEntity entity = entities.get(0);
            entity.setText("Измененный текст : " + new Random().nextInt(100));
            newsBox().put(entity);
//            if (entity != null) {
//                newsBox().remove(entity.getPrimaryId());
//            }
        }
    }
}
