package terekhov.artemiy.testtinkoffnews.presentation.routing;

import terekhov.artemiy.testtinkoffnews.presentation.routing.command.CommandBack;
import terekhov.artemiy.testtinkoffnews.presentation.routing.command.CommandBackTo;
import terekhov.artemiy.testtinkoffnews.presentation.routing.command.CommandNextTo;
import terekhov.artemiy.testtinkoffnews.presentation.routing.command.CommandReplace;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class Chain extends BaseChain {
    public Chain() {
        super();
    }

    /**
     * Add a new element with a tag to the chain.
     *
     * @param tag the tag name
     */
    public void to(String tag) {
        to(tag, null);
    }

    /**
     * Add a new element with a tag and a param to the chain.
     *
     * @param tag   the tag name
     * @param param the param
     */
    public void to(String tag, Object param) {
        executeCommand(new CommandNextTo(tag, param));
    }

    /**
     * Return to the previous element in the chain.
     */
    public void back() {
        executeCommand(new CommandBack());
    }

    /**
     * Return back to the element with a tag from the chain.
     *
     * @param tag the tag name
     */
    public void backTo(String tag) {
        executeCommand(new CommandBackTo(tag));
    }

    /**
     * Return back to the element with a tag from the chain.
     *
     * @param tag the tag name
     * @param param the param
     */
    public void backTo(String tag, Object param) {
        executeCommand(new CommandBackTo(tag, param));
    }

    /**
     * Replace the current element in chain with a new element with a tag and a param
     *
     * @param tag   the tag name
     * @param param the param
     */
    public void replace(String tag, Object param) {
        executeCommand(new CommandReplace(tag, param));
    }

    /**
     * Remove all elements from the chain and exit.
     */
    public void finish() {
        backTo(null);
        back();
    }
}
