package com.archerda.idea.plugin.taf.handler.impl;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.PsiShortNamesCache;

import java.util.ArrayList;
import java.util.List;

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
        List<String> jumpContentList = new ArrayList<>();
        String prxClassName = null;
        String prxImplClassName = null;
        if (currentPsiElement instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) currentPsiElement;
            String methodName = psiMethod.getName();
            methodName = methodName.replace("async_", "");
            methodName = methodName.replace("promise_", "");
            jumpContentList.add("public int " + methodName + "(");
            jumpContentList.add("public CompletableFuture<Integer> " + methodName + "(");
            PsiElement parentElement = psiMethod.getParent();
            PsiClass psiClass = (PsiClass) parentElement;
            prxClassName = psiClass.getName();
            prxImplClassName = prxClassName.replace("Prx", "ServantImpl");
        } else if (currentPsiElement instanceof  PsiClass) {
            PsiClass psiClass = (PsiClass) currentPsiElement;
            prxImplClassName = psiClass.getName().replace("Prx", "ServantImpl");
            jumpContentList.add("public class " + prxImplClassName + " implements");
        } else {
            return;
        }

        Project project = anActionEvent.getProject();
        if (project == null) {
            System.out.println("Can't get the project by the action event.");
            return;
        }
        PsiFile[] tafImplPsiFiles = PsiShortNamesCache.getInstance(project).getFilesByName(prxImplClassName + ".java");
        this.doLocate(project, jumpContentList, tafImplPsiFiles);
    }

    private void doLocate(Project project, List<String> jumpContentList, PsiFile[] psiFiles) {
        if (project == null || anActionEvent.getProject() == null) {
            return;
        }
        for (PsiFile file : psiFiles) {
            for (String jumpContent : jumpContentList) {
                super.doLocate(project, jumpContent, file);
            }
        }
    }
}
