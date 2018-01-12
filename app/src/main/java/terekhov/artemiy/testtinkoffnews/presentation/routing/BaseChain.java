package terekhov.artemiy.testtinkoffnews.presentation.routing;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public abstract class BaseChain {
    private CommandManager mCommandManager;

    public BaseChain() {
        mCommandManager = new CommandManager();
    }

    public CommandManager getCommandManager() {
        return mCommandManager;
    }

    protected void executeCommand(ICommand command) {
        mCommandManager.executeCommand(command);
    }
}
