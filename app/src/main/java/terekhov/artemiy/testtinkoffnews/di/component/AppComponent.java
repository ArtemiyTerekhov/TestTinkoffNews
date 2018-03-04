package terekhov.artemiy.testtinkoffnews.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import io.objectbox.BoxStore;
import terekhov.artemiy.testtinkoffnews.data.db.RxSnappyClient;
import terekhov.artemiy.testtinkoffnews.data.net.news.NewsAPIService;
import terekhov.artemiy.testtinkoffnews.data.repository.news.NewsRepository;
import terekhov.artemiy.testtinkoffnews.di.module.AppModule;
import terekhov.artemiy.testtinkoffnews.di.module.ScheduleModule;
import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

@Singleton
@Component(modules = {AppModule.class, ScheduleModule.class})
public interface AppComponent {
    Application app();
    NewsRepository newsRepository();
    NewsAPIService newsAPIService();
    SchedulerProvider schedulerProvider();
    RxSnappyClient rxSnappyClient();
}
