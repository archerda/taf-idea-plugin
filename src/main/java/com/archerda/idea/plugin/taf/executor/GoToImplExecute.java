package com.archerda.idea.plugin.taf.executor;

import com.archerda.idea.plugin.taf.handler.Handler;

public class GoToImplExecute extends BaseExecute {

    private final Handler handler;

    public GoToImplExecute(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void doExecute() {
        handler.doHandle();
    }

}
