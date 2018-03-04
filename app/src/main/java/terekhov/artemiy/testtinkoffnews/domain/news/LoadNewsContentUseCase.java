package terekhov.artemiy.testtinkoffnews.domain.news;

import io.reactivex.Observable;
import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.data.db.exception.MissingDataException;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.data.repository.DataStore;
import terekhov.artemiy.testtinkoffnews.data.repository.news.NewsRepository;
import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;
import terekhov.artemiy.testtinkoffnews.domain.interactor.UseCase;

import static terekhov.artemiy.testtinkoffnews.data.repository.DataStore.TYPE_REQUEST_LOCAL;
import static terekhov.artemiy.testtinkoffnews.data.repository.DataStore.TYPE_REQUEST_REMOTE;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class LoadNewsContentUseCase extends UseCase<NewsContentEntity, LoadNewsContentUseCase.Request> {
    private final NewsRepository mAppRepository;

    public LoadNewsContentUseCase(SchedulerProvider schedulerProvider) {
        super(schedulerProvider.io(), schedulerProvider.ui());
        mAppRepository = ((App) App.getAppComponent().app()).newsRepository();
    }

    @Override
    public Observable<NewsContentEntity> buildUseCaseObservable(Request request) {
        return mAppRepository.getNewsContent(request.getId()).toObservable();
                //.map(NewsContentEntityDataMapper::transform);
    }

    public void registerObserver(Request request) {
        mAppRepository.registerNewsContentObserver(request.getId(), data -> {
            if (!data.isEmpty()) {
                doOnNext(data.get(0));
            } else {
                doOnError(new MissingDataException());
            }
        });
    }

    public void unregisterObserver() {
        if (mAppRepository != null) {
            mAppRepository.unregisterNewsContentObserver();
        }
    }

    public void testChangeItem() {
        mAppRepository.testChangeItem();
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
