package terekhov.artemiy.testtinkoffnews.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import terekhov.artemiy.testtinkoffnews.data.repository.news.NewsLocalDataStore;
import terekhov.artemiy.testtinkoffnews.data.repository.news.NewsRemoteDataStore;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

@Module
public class DataStoreModule {
    @Provides
    @Singleton
    NewsLocalDataStore provideNewsLocalDataStore() {
        return new NewsLocalDataStore();
    }

    @Provides
    @Singleton
    NewsRemoteDataStore provideNewsRemoteDataStore() {
        return new NewsRemoteDataStore();
    }
}
