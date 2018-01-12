package terekhov.artemiy.testtinkoffnews.presentation;

import terekhov.artemiy.testtinkoffnews.presentation.routing.INavigation;
import terekhov.artemiy.testtinkoffnews.presentation.routing.NavigationProvider;
import terekhov.artemiy.testtinkoffnews.presentation.routing.ScreenChain;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NavigationManager implements IAppNavigation {
    private NavigationProvider<ScreenChain> mProvider;

    public NavigationManager() {
        mProvider = NavigationProvider.create();
    }

    @Override
    public void set(INavigation navigation) {
        mProvider.getManager().set(navigation);
    }

    @Override
    public void remove() {
        mProvider.getManager().remove();
    }

    @Override
    public void navigateToNewsScreen() {
        mProvider.getChain().to(Screens.TAG_SCREEN_NEWS);
    }

    @Override
    public void navigateToNewsContentScreen(String id) {
        mProvider.getChain().to(Screens.TAG_SCREEN_NEWS_CONTENT, id);
    }

    @Override
    public void navigateToBack() {
        mProvider.getChain().back();
    }
}
