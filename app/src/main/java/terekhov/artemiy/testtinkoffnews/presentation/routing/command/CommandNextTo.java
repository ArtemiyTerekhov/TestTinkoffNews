package terekhov.artemiy.testtinkoffnews.presentation.routing.command;

import android.support.v4.util.Pair;
import android.view.View;

import java.util.List;

import terekhov.artemiy.testtinkoffnews.presentation.routing.ICommand;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class CommandNextTo implements ICommand {
    private final String mTag;
    private final Object mParam;
    private List<Pair<View, String>> mTransitions;

    public CommandNextTo(String tag, Object param) {
        super();
        mTag = tag;
        mParam = param;
    }

    public CommandNextTo(String tag, Object param, List<Pair<View, String>> transitions) {
        super();
        mTag = tag;
        mParam = param;
        mTransitions = transitions;
    }

    public String getTag() {
        return mTag;
    }

    public Object getParam() {
        return mParam;
    }

    public List<Pair<View, String>> getTransitions() {
        return mTransitions;
    }
}
