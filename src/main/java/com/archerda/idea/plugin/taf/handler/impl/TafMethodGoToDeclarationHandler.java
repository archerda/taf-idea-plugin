package com.archerda.idea.plugin.taf.handler.impl;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.PsiShortNamesCache;

public class TafMethodGoToDeclarationHandler extends BaseGoToImplHandler {

    private final AnActionEvent anActionEvent;
    private final PsiElement currentPsiElement;

    public TafMethodGoToDeclarationHandler(AnActionEvent anActionEvent, PsiElement currentPsiElement) {
        this.anActionEvent = anActionEvent;
        this.currentPsiElement = currentPsiElement;
    }

    @Override
    public void doHandle() {
        super.doHandle();
        String jumpContent;
        String prxClassName;
        String prxImplClassName;
        if (currentPsiElement instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) currentPsiElement;
            jumpContent = "public int " + psiMethod.getName() + "(";
            PsiElement parentElement = psiMethod.getParent();
            PsiClass psiClass = (PsiClass) parentElement;
            prxClassName = psiClass.getName();
            prxImplClassName = prxClassName.replace("ServantImpl", "Prx");
        } else if (currentPsiElement instanceof  PsiClass) {
            PsiClass psiClass = (PsiClass) currentPsiElement;
            prxImplClassName = psiClass.getName().replace("ServantImpl", "Prx");
            jumpContent = "public interface " + prxImplClassName;
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
