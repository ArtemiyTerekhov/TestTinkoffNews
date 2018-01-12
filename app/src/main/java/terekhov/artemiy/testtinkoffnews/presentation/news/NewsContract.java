package terekhov.artemiy.testtinkoffnews.presentation.news;

import java.util.List;

import terekhov.artemiy.testtinkoffnews.domain.interactor.SchedulerProvider;
import terekhov.artemiy.testtinkoffnews.domain.model.News;
import terekhov.artemiy.testtinkoffnews.presentation.mvp.BasePresenter;
import terekhov.artemiy.testtinkoffnews.presentation.mvp.BaseView;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public interface NewsContract {
    interface View extends BaseView {
        void loadingStarted();
        void loadingFinished();

        void updateList(List<News> items, boolean isRefresh);

        int getItemsCount();
    }

    interface Presenter extends BasePresenter<View> {
        void swipeToRefresh();
        void fetchNews(boolean isRefresh);

        SchedulerProvider getSchedulerProvider();
    }
}
