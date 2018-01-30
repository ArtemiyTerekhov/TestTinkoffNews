package terekhov.artemiy.testtinkoffnews.data.net.news;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;
import terekhov.artemiy.testtinkoffnews.data.net.api.AppService;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsAPIService extends AppService {
    private INewsAPI mNewsAPI;

    public NewsAPIService() {
        super();
        mNewsAPI = getRetrofit(APP_HTTP_BASE_URL_V1).create(INewsAPI.class);
    }

    public Flowable<List<NewsEntity>> getNews() {
        return mNewsAPI.getNews().flatMap(this::parseResponse)
                .toFlowable(BackpressureStrategy.DROP);
    }

    public Single<NewsContentEntity> getNewsContent(@NonNull String id) {
        return mNewsAPI.getNewsContent(id).flatMap(this::parseResponse)
                .firstOrError();
    }
}
