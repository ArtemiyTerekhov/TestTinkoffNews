package terekhov.artemiy.testtinkoffnews.presentation.routing.command;

import android.support.v4.util.Pair;
import android.view.View;

import java.util.List;

import terekhov.artemiy.testtinkoffnews.presentation.routing.ICommand;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class CommandBackTo implements ICommand {
    private final String mTag;
    private Object mParam;
    private List<Pair<View, String>> mTransitions;

    public CommandBackTo(String tag) {
        super();
        mTag = tag;
    }

    public CommandBackTo(String tag, Object param) {
        super();
        mTag = tag;
        mParam = param;
    }

    public CommandBackTo(String tag, List<Pair<View, String>> transitions) {
        super();
        mTag = tag;
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
