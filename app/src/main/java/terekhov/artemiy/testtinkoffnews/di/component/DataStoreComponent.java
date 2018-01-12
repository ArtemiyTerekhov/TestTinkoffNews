package terekhov.artemiy.testtinkoffnews.di.component;

import javax.inject.Singleton;

import dagger.Component;
import terekhov.artemiy.testtinkoffnews.data.repository.news.NewsRepository;
import terekhov.artemiy.testtinkoffnews.di.module.DataStoreModule;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

@Singleton
@Component(modules = {DataStoreModule.class})
public interface DataStoreComponent {
    void inject(NewsRepository repository);
}
