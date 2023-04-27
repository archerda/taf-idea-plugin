package com.archerda.idea.plugin.taf.handler.impl;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.PsiShortNamesCache;

public class TafMethodGoToImplHandler extends BaseGoToImplHandler {

    private final AnActionEvent anActionEvent;
    private final PsiElement currentPsiElement;

    public TafMethodGoToImplHandler(AnActionEvent anActionEvent, PsiElement currentPsiElement) {
        this.anActionEvent = anActionEvent;
        this.currentPsiElement = currentPsiElement;
    }

    @Override
    public void doHandle() {
        super.doHandle();
        String jumpContent = null;
        String prxClassName = null;
        String prxImplClassName = null;
        if (currentPsiElement instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) currentPsiElement;
            String methodName = psiMethod.getName();
            methodName = methodName.replace("async_", "");
            methodName = methodName.replace("promise_", "");
            jumpContent = "public int " + methodName + "(";
            PsiElement parentElement = psiMethod.getParent();
            PsiClass psiClass = (PsiClass) parentElement;
            prxClassName = psiClass.getName();
            prxImplClassName = prxClassName.replace("Prx", "ServantImpl");
        } else if (currentPsiElement instanceof  PsiClass) {
            PsiClass psiClass = (PsiClass) currentPsiElement;
            prxImplClassName = psiClass.getName().replace("Prx", "ServantImpl");
            jumpContent = "public class " + prxImplClassName + " implements";
        } else {
            return;
        }

        Project project = anActionEvent.getProject();
        if (project == null) {
            System.out.println("Can't get the project by the action event.");
            return;
        }
        PsiFile[] tafImplPsiFiles = PsiShortNamesCache.getInstance(project).getFilesByName(prxImplClassName + ".java");
        this.doLocate(project, jumpContent, tafImplPsiFiles);
    }

    private void doLocate(Project project, String content, PsiFile[] psiFiles) {
        if (project == null || anActionEvent.getProject() == null) {
            return;
        }
        for (PsiFile file : psiFiles) {
            super.doLocate(project, content, file);
        }
    }
}
