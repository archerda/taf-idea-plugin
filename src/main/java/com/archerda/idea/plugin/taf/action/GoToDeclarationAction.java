package com.archerda.idea.plugin.taf.action;

import com.archerda.idea.plugin.taf.executor.Execute;
import com.archerda.idea.plugin.taf.util.PsiElementUtil;
import com.archerda.idea.plugin.taf.executor.GoToImplExecute;
import com.archerda.idea.plugin.taf.handler.impl.TafMethodGoToDeclarationHandler;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiElement;

public class GoToDeclarationAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        PsiElement currentPsiElement = anActionEvent.getData(LangDataKeys.PSI_ELEMENT);
        if (currentPsiElement == null) {
            return;
        }
        Execute execute = null;
        if (PsiElementUtil.isTafImplClassOrMethod(currentPsiElement)) {
            execute = new GoToImplExecute(new TafMethodGoToDeclarationHandler(anActionEvent, currentPsiElement));
        }
        if (execute == null) {
            return;
        }
        execute.doExecute();
    }
}
