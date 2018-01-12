package terekhov.artemiy.testtinkoffnews.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import terekhov.artemiy.testtinkoffnews.data.db.RxSnappy;
import terekhov.artemiy.testtinkoffnews.data.db.RxSnappyClient;
import terekhov.artemiy.testtinkoffnews.data.net.news.NewsAPIService;
import terekhov.artemiy.testtinkoffnews.data.repository.news.NewsRepository;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    NewsRepository provideNewsRepository() {
        return new NewsRepository();
    }

    @Provides
    @Singleton
    NewsAPIService provideNewsAPIService() {
        return new NewsAPIService();
    }

    @Provides
    @Singleton
    RxSnappyClient provideRxSnappyClient(Application context) {
        RxSnappy.init(context);
        return new RxSnappyClient();
    }
}
