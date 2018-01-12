package terekhov.artemiy.testtinkoffnews.domain.news;

import io.reactivex.Observable;
import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.data.entities.mapper.NewsContentEntityDataMapper;
import terekhov.artemiy.testtinkoffnews.data.repository.DataStore;
import terekhov.artemiy.testtinkoffnews.data.repository.news.NewsRepository;
import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;
import terekhov.artemiy.testtinkoffnews.domain.interactor.UseCase;
import terekhov.artemiy.testtinkoffnews.domain.model.NewsContent;

import static terekhov.artemiy.testtinkoffnews.data.repository.DataStore.TYPE_REQUEST_LOCAL;
import static terekhov.artemiy.testtinkoffnews.data.repository.DataStore.TYPE_REQUEST_REMOTE;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class GetNewsContentUseCase extends UseCase<NewsContent, GetNewsContentUseCase.Request> {
    private final NewsRepository mAppRepository;

    public GetNewsContentUseCase(SchedulerProvider schedulerProvider) {
        super(schedulerProvider.io(), schedulerProvider.ui());
        mAppRepository = App.getAppComponent().newsRepository();
    }

    @Override
    public Observable<NewsContent> buildUseCaseObservable(Request request) {
        return mAppRepository.getNewsContent(request.getId(), request.getRequestType())
                .flatMap(entity -> mAppRepository
                        .saveNewsContent(request.getId(), entity, request.getRequestType()))
                .map(NewsContentEntityDataMapper::transform);
    }

    public static final class Request {
        private final String id;
        private final int requestType;

        private Request(Builder builder) {
            this.id = builder.id;
            this.requestType = builder.requestType;
        }

        public int getRequestType() {
            App app = (App) App.getAppComponent().app();
            return app.isNetworkConnected() ? requestType : TYPE_REQUEST_LOCAL;
        }

        public String getId() {
            return id;
        }

        public static class Builder {
            private final String id;
            @DataStore.RequestType
            private int requestType;

            public Builder(String id) {
                this.requestType = TYPE_REQUEST_REMOTE;
                this.id = id;
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
