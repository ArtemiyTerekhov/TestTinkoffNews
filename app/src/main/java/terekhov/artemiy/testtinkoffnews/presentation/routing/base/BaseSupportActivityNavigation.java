package terekhov.artemiy.testtinkoffnews.presentation.routing.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.List;

import terekhov.artemiy.testtinkoffnews.presentation.routing.ICommand;
import terekhov.artemiy.testtinkoffnews.presentation.routing.command.CommandNextTo;
import terekhov.artemiy.testtinkoffnews.presentation.routing.command.CommandReplace;

import static android.support.v4.app.ActivityOptionsCompat.makeSceneTransitionAnimation;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public abstract class BaseSupportActivityNavigation extends BaseSupportFragmentNavigation {
    public BaseSupportActivityNavigation(FragmentManager fragmentManager, int containerId) {
        super(fragmentManager, containerId);
    }

    protected abstract Intent createActivityIntent(String screenTag, Object param);

    protected abstract ActivityOptionsCompat createActivityOptions(List<Pair<View, String>> transitions);

    protected abstract Activity getActivityContext();

    @Override
    protected void finishTransition() {
        Activity activity = getActivityContext();
        ActivityCompat.finishAfterTransition(activity);
    }

    @Override
    public void executeCommand(ICommand command) {
        if (command instanceof CommandNextTo && executeNextTo(command)) {
            return;
        } else if (command instanceof CommandReplace && executeReplace(command)) {
            return;
        }

        super.executeCommand(command);
    }

    private boolean executeNextTo(ICommand command) {
        CommandNextTo nextTo = (CommandNextTo) command;
        ActivityOptionsCompat opts = createActivityOptions(nextTo.getTransitions());
        Intent activityIntent = createActivityIntent(nextTo.getTag(), nextTo.getParam());
        Activity activity = getActivityContext();
        if (activity != null && activityIntent != null) {
            ActivityCompat.startActivity(activity, activityIntent, opts != null ? opts.toBundle() : null);
            return true;
        }

        return false;
    }

    private boolean executeReplace(ICommand command) {
        CommandReplace replace = (CommandReplace) command;
        ActivityOptionsCompat opts = createActivityOptions(replace.getTransitions());
        Intent activityIntent = createActivityIntent(replace.getTag(), replace.getParam());
        Activity activity = getActivityContext();
        if (activity != null && activityIntent != null) {
            ActivityCompat.startActivity(activity, activityIntent, opts != null ? opts.toBundle() : null);
            ActivityCompat.finishAfterTransition(activity);
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    protected ActivityOptionsCompat makeScene(List<Pair<View, String>> transitions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && transitions != null && !transitions.isEmpty()) {
            return makeSceneTransitionAnimation(getActivityContext(),
                    transitions.toArray(new Pair[transitions.size()]));
        }
        return makeSceneTransitionAnimation(getActivityContext());
    }
}
