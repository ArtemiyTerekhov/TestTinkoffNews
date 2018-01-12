package terekhov.artemiy.testtinkoffnews.presentation;

import io.reactivex.annotations.NonNull;
import terekhov.artemiy.testtinkoffnews.presentation.routing.INavigationManager;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public interface IAppNavigation extends INavigationManager {
    void navigateToNewsScreen();
    void navigateToNewsContentScreen(@NonNull String id);
    void navigateToBack();
}
