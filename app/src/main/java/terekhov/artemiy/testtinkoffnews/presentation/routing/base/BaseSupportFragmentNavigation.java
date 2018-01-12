package terekhov.artemiy.testtinkoffnews.presentation.routing.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

import terekhov.artemiy.testtinkoffnews.Utils;
import terekhov.artemiy.testtinkoffnews.presentation.routing.ICommand;
import terekhov.artemiy.testtinkoffnews.presentation.routing.INavigation;
import terekhov.artemiy.testtinkoffnews.presentation.routing.command.CommandBack;
import terekhov.artemiy.testtinkoffnews.presentation.routing.command.CommandBackTo;
import terekhov.artemiy.testtinkoffnews.presentation.routing.command.CommandNextTo;
import terekhov.artemiy.testtinkoffnews.presentation.routing.command.CommandReplace;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public abstract class BaseSupportFragmentNavigation implements INavigation {
    private FragmentManager mFragmentManager;
    private int mContainerId;

    public BaseSupportFragmentNavigation(FragmentManager fragmentManager, int containerId) {
        mFragmentManager = fragmentManager;
        mContainerId = containerId;
    }

    @Override
    public void executeCommand(ICommand command) {
        if (command instanceof CommandNextTo) {
            executeNextTo(command);
        } else if (command instanceof CommandReplace) {
            executeReplace(command);
        } else if (command instanceof CommandBack) {
            executeBack(command);
        } else if (command instanceof CommandBackTo) {
            executeBackTo(command);
        }
    }

    protected abstract Fragment createFragment(String screenTag, Object param);

    protected abstract void finishTransition();

    private void executeNextTo(ICommand command) {
        CommandNextTo nextTo = (CommandNextTo) command;
        List<Pair<View, String>> transitions = nextTo.getTransitions();
        Fragment fragment = createFragment(nextTo.getTag(), nextTo.getParam());
        if (fragment == null) {
            if (Utils.isDebug()) {
                try {
                    throw new RuntimeException("Can't create a fragment with tag: " + nextTo.getTag());
                } catch (Exception e) {e.printStackTrace();}
            }
            return;
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.setAllowOptimization(true);
        transaction.replace(mContainerId, fragment, nextTo.getTag());
        transaction.addToBackStack(nextTo.getTag());
        if (transitions != null && !transitions.isEmpty()) {
            for (Pair<View, String> pair : transitions) {
                transaction.addSharedElement(pair.first, pair.second);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    private void executeBackTo(ICommand command) {
        CommandBackTo backTo = (CommandBackTo) command;
        String tag = backTo.getTag();
        if (TextUtils.isEmpty(tag)) {
            cleanStack();
            return;
        }

        if (mFragmentManager.getBackStackEntryCount() > 0) {
            cleanStack();
        }
    }

    private void executeBack(ICommand command) {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStackImmediate();
        } else {
            finishTransition();
        }
    }

    private void executeReplace(ICommand command) {
        CommandReplace replace = (CommandReplace) command;
        List<Pair<View, String>> transitions = replace.getTransitions();
        Fragment fragment = createFragment(replace.getTag(), replace.getParam());
        if (fragment == null) {
            if (Utils.isDebug()) {
                try {
                    throw new RuntimeException("Can't create a fragment with tag: " + replace.getTag());
                } catch (Exception e) {e.printStackTrace();}
            }
            return;
        }
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStackImmediate();
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.setAllowOptimization(true);
            transaction.replace(mContainerId, fragment);
            transaction.addToBackStack(replace.getTag());
            if (transitions != null && !transitions.isEmpty()) {
                for (Pair<View, String> pair : transitions) {
                    transaction.addSharedElement(pair.first, pair.second);
                }
            }
            transaction.commitAllowingStateLoss();
        } else {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.setAllowOptimization(true);
            transaction.replace(mContainerId, fragment);
            if (transitions != null && !transitions.isEmpty()) {
                for (Pair<View, String> pair : transitions) {
                    transaction.addSharedElement(pair.first, pair.second);
                }
            }
            transaction.commitAllowingStateLoss();
        }
    }

    private void cleanStack() {
        for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); i++) {
            mFragmentManager.popBackStack();
        }
        mFragmentManager.executePendingTransactions();
    }
}
