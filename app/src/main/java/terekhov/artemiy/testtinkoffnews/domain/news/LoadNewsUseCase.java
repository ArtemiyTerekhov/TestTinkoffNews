package terekhov.artemiy.testtinkoffnews.domain.news;

import java.util.List;

import io.reactivex.Observable;
import terekhov.artemiy.testtinkoffnews.App;
import terekhov.artemiy.testtinkoffnews.data.db.exception.MissingDataException;
import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;
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

public class LoadNewsUseCase extends UseCase<List<NewsEntity>, Void> {
    private NewsRepository mAppRepository;

    public LoadNewsUseCase(SchedulerProvider schedulerProvider) {
        super(schedulerProvider.io(), schedulerProvider.ui());
        mAppRepository = ((App) App.getAppComponent().app()).newsRepository();
    }

    @Override
    public Observable<List<NewsEntity>> buildUseCaseObservable(Void aVoid) {
        return mAppRepository.getNews()
                //.map(NewsEntityDataMapper::transform)
                //.map(News::sort)
                .toObservable();
    }

    public void registerObserver() {
        mAppRepository.registerNewsObserver(data -> {
            if (!data.isEmpty()) {
                doOnNext(data);
            } else {
                doOnError(new MissingDataException());
            }
        });
    }

    public void unregisterObserver() {
        if (mAppRepository != null) {
            mAppRepository.unregisterNewsObserver();
        }
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

    public void testChangeItem() {
        mAppRepository.testChangeItem();
    }
}
