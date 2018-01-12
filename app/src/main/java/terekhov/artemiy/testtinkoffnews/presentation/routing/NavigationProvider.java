package terekhov.artemiy.testtinkoffnews.presentation.routing;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NavigationProvider <C extends BaseChain> {
    private final C mChain;

    private NavigationProvider(C chain) {
        mChain = chain;
    }

    public C getChain() {
        return mChain;
    }

    public INavigationManager getManager() {
        return mChain.getCommandManager();
    }

    /**
     * Create the NavigationProvider instance with the default {@link ScreenChain}
     */
    public static NavigationProvider<ScreenChain> create() {
        return create(new ScreenChain());
    }

    /**
     * Create the NavigationProvider instance with the custom chain.
     *
     * @param customChain the custom chain extending {@link BaseChain}
     */
    public static <R extends BaseChain> NavigationProvider<R> create(R customChain) {
        return new NavigationProvider<>(customChain);
    }
}
