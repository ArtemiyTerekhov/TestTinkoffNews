package terekhov.artemiy.testtinkoffnews.presentation.routing.command;

import android.support.v4.util.Pair;
import android.view.View;

import java.util.List;

import terekhov.artemiy.testtinkoffnews.presentation.routing.ICommand;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class CommandBack implements ICommand {
    private List<Pair<View, String>> mTransitions;

    public CommandBack() {
        super();
    }

    public CommandBack(List<Pair<View, String>> transitions) {
        super();
        mTransitions = transitions;
    }

    public List<Pair<View, String>> getTransitions() {
        return mTransitions;
    }
}
