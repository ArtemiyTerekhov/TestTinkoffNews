package terekhov.artemiy.testtinkoffnews.presentation.mvp;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public interface PresenterHolder {
    <T extends BasePresenter<?>> T getOrCreatePresenter(PresenterFactory<T> presenterFactory, String tagPrefix);

    void destroyPresenter(PresenterFactory<?> presenterFactory, String tagPrefix);
}
