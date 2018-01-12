package terekhov.artemiy.testtinkoffnews.domain.news;

import android.support.v4.util.Pair;

import java.util.List;

import io.reactivex.Observable;
import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.data.entities.mapper.NewsEntityDataMapper;
import terekhov.artemiy.testtinkoffnews.data.repository.DataStore;
import terekhov.artemiy.testtinkoffnews.data.repository.news.NewsRepository;
import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;
import terekhov.artemiy.testtinkoffnews.domain.interactor.UseCase;
import terekhov.artemiy.testtinkoffnews.domain.model.News;

import static terekhov.artemiy.testtinkoffnews.data.repository.DataStore.TYPE_REQUEST_LOCAL;
import static terekhov.artemiy.testtinkoffnews.data.repository.DataStore.TYPE_REQUEST_REMOTE;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class GetNewsUseCase extends UseCase<Pair<List<News>, Boolean>, GetNewsUseCase.Request> {
    private final NewsRepository mAppRepository;

    public GetNewsUseCase(SchedulerProvider schedulerProvider) {
        super(schedulerProvider.io(), schedulerProvider.ui());
        mAppRepository = App.getAppComponent().newsRepository();
    }

    @Override
    public Observable<Pair<List<News>, Boolean>> buildUseCaseObservable(GetNewsUseCase.Request params) {
        return mAppRepository.getNews(params.getRequestType())
                .flatMap(entities -> mAppRepository.saveNews(entities, params.getRequestType()))
                .map(NewsEntityDataMapper::transform)
                .map(news -> Pair.create(news, params.isClean()));
    }

    public static final class Request {
        private boolean clean;
        private int requestType;

        private Request(Builder builder) {
            this.clean = builder.clean;
            this.requestType = builder.requestType;
        }

        public int getRequestType() {
            App app = (App) App.getAppComponent().app();
            return app.isNetworkConnected() ? requestType : TYPE_REQUEST_LOCAL;
        }

        public boolean isClean() {
            App app = (App) App.getAppComponent().app();
            return clean && app.isNetworkConnected();
        }

        public static class Builder {
            private boolean clean;
            @DataStore.RequestType
            private int requestType;

            public Builder() {
                this.requestType = TYPE_REQUEST_REMOTE;
                this.clean = false;
            }

            public Builder clean(boolean clean) {
                this.clean = clean;
                return this;
            }

            public Builder requestType(int requestType) {
                this.requestType = requestType;
                return this;
            }

            public Request build() {
                return new Request(this);
            }
        }
    }
}
