package terekhov.artemiy.testtinkoffnews.domain.interactor;

import io.reactivex.Scheduler;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public interface SchedulerProvider {
    Scheduler ui();
    Scheduler computation();
    Scheduler io();
    Scheduler thread();
}
