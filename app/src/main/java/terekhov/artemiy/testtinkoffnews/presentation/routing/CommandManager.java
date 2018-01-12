package terekhov.artemiy.testtinkoffnews.presentation.routing;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class CommandManager implements INavigationManager {
    private Queue<ICommand> mQueuePendingCommands = new ConcurrentLinkedQueue<>();
    private INavigation mNavigation;

    public void executeCommand(ICommand command) {
        if (mNavigation == null) {
            mQueuePendingCommands.add(command);
        } else {
            mNavigation.executeCommand(command);
        }
    }

    @Override
    public void set(INavigation navigation) {
        mNavigation = navigation;

        while (!mQueuePendingCommands.isEmpty()) {
            if (mNavigation == null) break;
            mNavigation.executeCommand(mQueuePendingCommands.poll());
        }
    }

    @Override
    public void remove() {
        mNavigation = null;
    }
}
