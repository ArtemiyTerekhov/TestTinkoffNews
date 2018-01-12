package terekhov.artemiy.testtinkoffnews.presentation.mvp;

import android.os.Bundle;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public interface BasePresenter<V> {
    void subscribe(V view);
    void unsubscribe();

    void restoreState(Bundle state);
    void saveState(Bundle state);

    boolean isViewBound();
    String getTagName();

    void destroy();
}
