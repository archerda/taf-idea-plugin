package com.archerda.idea.plugin.taf.action;

import com.archerda.idea.plugin.taf.executor.Execute;
import com.archerda.idea.plugin.taf.util.PsiElementUtil;
import com.archerda.idea.plugin.taf.executor.GoToImplExecute;
import com.archerda.idea.plugin.taf.handler.impl.TafMethodGoToImplHandler;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiElement;

public class GoToImplAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        PsiElement currentPsiElement = anActionEvent.getData(LangDataKeys.PSI_ELEMENT);
        if (currentPsiElement == null) {
            return;
        }
        Execute execute = null;
        if (PsiElementUtil.isTafPrxClassOrMethod(currentPsiElement)) {
            execute = new GoToImplExecute(new TafMethodGoToImplHandler(anActionEvent, currentPsiElement));
        }
        if (execute == null) {
            return;
        }
        execute.doExecute();
    }
}
