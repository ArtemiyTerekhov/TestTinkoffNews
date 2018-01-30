package terekhov.artemiy.testtinkoffnews.data.repository.news;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;
import terekhov.artemiy.testtinkoffnews.data.net.news.NewsAPIService;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsRemoteDataStore implements AbsNewsRemoteDataStore {
    private final NewsAPIService mAPIService;

    public NewsRemoteDataStore() {
        mAPIService = App.getAppComponent().newsAPIService();
    }

    @Override
    public Flowable<List<NewsEntity>> getNews() {
        return mAPIService.getNews();
    }

    @Override
    public Single<NewsContentEntity> getNewsContent(@NonNull String id) {
        return mAPIService.getNewsContent(id);
    }
}
