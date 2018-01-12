package terekhov.artemiy.testtinkoffnews.domain;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public final class AppSchedulerProvider implements SchedulerProvider {
    public AppSchedulerProvider() {
    }

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler thread() {
        return Schedulers.newThread();
    }
}
