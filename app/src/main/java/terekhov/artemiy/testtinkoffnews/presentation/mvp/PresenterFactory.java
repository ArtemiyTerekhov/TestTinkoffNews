package terekhov.artemiy.testtinkoffnews.presentation.mvp;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public interface PresenterFactory<T extends BasePresenter<?>> {
    T createPresenter();

    String getPresenterTag();
}
