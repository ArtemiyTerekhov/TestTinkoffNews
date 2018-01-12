package terekhov.artemiy.testtinkoffnews.presentation.routing;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class ScreenChain extends Chain {

    public ScreenChain() {
        super();
    }

    /**
     * Clear all screens in the stack and open a new root screen with a tag of screen
     *
     * @param screenTag the screen tag
     */
    public void createRootStackScreens(String screenTag) {
        createRootStackScreens(screenTag, null);
    }

    /**
     * Clear all screens in the stack and open a new root screen with a tag and a param of screen
     *
     * @param screenTag the screen tag
     * @param param     the param
     */
    public void createRootStackScreens(String screenTag, Object param) {
        backTo(screenTag);
        replace(screenTag, param);
    }

    /**
     * Clear all screens in the stack and open a root screen with a tag and a param of screen
     *
     * @param screenTag the screen tag
     * @param param     the param
     */
    public void backToRootStackScreens(String screenTag, Object param) {
        backTo(screenTag, param);
    }

    /**
     * Clear all screens in the stack, but not the root, and start after the root screen a new
     * screen with a tag of screen
     *
     * @param screenTag the screen tag
     */
    public void newStackScreens(String screenTag) {
        newStackScreens(screenTag, null);
    }

    /**
     * Clear all screens in the stack, but not the root, and start after the root screen a new
     * screen with a tag and a param of screen
     *
     * @param screenTag the screen tag
     * @param param     the param
     */
    public void newStackScreens(String screenTag, Object param) {
        backTo(null);
        to(screenTag, param);
    }

    /**
     * Replace the current screen in the stack to a new screen with tag of screen
     *
     * @param screenTag the screen tag
     */
    public void replaceScreen(String screenTag) {
        replaceScreen(screenTag, null);
    }

    /**
     * Replace the current screen in the stack to a new screen with tag and a param of screen
     *
     * @param screenTag the screen tag
     * @param param     thr param
     */
    public void replaceScreen(String screenTag, Object param) {
        replace(screenTag, param);
    }
}
