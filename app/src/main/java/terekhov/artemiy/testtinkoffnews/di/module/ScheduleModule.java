package terekhov.artemiy.testtinkoffnews.di.module;

import dagger.Module;
import dagger.Provides;
import terekhov.artemiy.testtinkoffnews.domain.AppSchedulerProvider;
import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

@Module
public class ScheduleModule {
    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
