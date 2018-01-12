package terekhov.artemiy.testtinkoffnews.data.repository.news;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;
import terekhov.artemiy.testtinkoffnews.data.net.news.NewsAPIService;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsRemoteDataStore implements NewsDataStore {
    private final NewsAPIService mAPIService;

    public NewsRemoteDataStore() {
        mAPIService = App.getAppComponent().newsAPIService();
    }

    @Override
    public Observable<List<NewsEntity>> getNews() {
        return mAPIService.getNews();
    }

    @Override
    public Observable<List<NewsEntity>> saveNews(@NonNull List<NewsEntity> news) {
        return Observable.empty();
    }

    @Override
    public Observable<NewsContentEntity> getNewsContent(@NonNull String id) {
        return mAPIService.getNewsContent(id);
    }

    @Override
    public Observable<NewsContentEntity> saveNewsContent(@NonNull String id,
            @NonNull NewsContentEntity newsContentEntity) {
        return Observable.empty();
    }
}
