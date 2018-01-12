package terekhov.artemiy.testtinkoffnews.presentation.mvp;

import java.io.Serializable;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public interface PresenterProvider<P extends BasePresenter<?>> extends Serializable {
    P getPresenterOf(PresenterHolder holder, PresenterFactory factory, String tagName);
}
