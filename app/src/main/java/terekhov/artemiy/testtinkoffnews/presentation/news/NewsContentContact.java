package terekhov.artemiy.testtinkoffnews.presentation.news;

import io.reactivex.annotations.NonNull;
import terekhov.artemiy.testtinkoffnews.domain.model.NewsContent;
import terekhov.artemiy.testtinkoffnews.presentation.mvp.BasePresenter;
import terekhov.artemiy.testtinkoffnews.presentation.mvp.BaseView;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public interface NewsContentContact {
    interface View extends BaseView {
        void loadingStarted();
        void loadingFinished();

        void update(NewsContent content);
    }

    interface Presenter extends BasePresenter<View> {
        void setId(@NonNull String id);
        void fetchNewsContent();
    }
}
