//package com.archerda.tafhelper.handler.impl;
//
//
//import com.intellij.find.findUsages.AbstractFindUsagesDialog;
//import com.intellij.find.findUsages.DefaultFindUsagesHandlerFactory;
//import com.intellij.find.findUsages.FindUsagesHandler;
//import com.intellij.find.findUsages.PsiElement2UsageTargetAdapter;
//import com.intellij.openapi.actionSystem.AnActionEvent;
//import com.intellij.openapi.project.Project;
//import com.intellij.psi.PsiClass;
//import com.intellij.psi.PsiElement;
//import com.intellij.psi.PsiMethod;
//import com.intellij.psi.PsiReference;
//import com.intellij.psi.search.GlobalSearchScope;
//import com.intellij.psi.search.searches.ReferencesSearch;
//import com.intellij.usageView.UsageInfo;
//import com.intellij.usages.*;
//import com.intellij.util.Query;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TafMethodFindUsageHandler extends BaseGoToImplHandler {
//
//    private final AnActionEvent anActionEvent;
//    private final PsiElement currentPsiElement;
//
//    public TafMethodFindUsageHandler(AnActionEvent anActionEvent, PsiElement currentPsiElement) {
//        this.anActionEvent = anActionEvent;
//        this.currentPsiElement = currentPsiElement;
//    }
//
//    @Override
//    public void doHandle() {
//        super.doHandle();
//
//        Project project = currentPsiElement.getProject();
//
//        // 找到对应的prx类或者方法
//        PsiElement prxElement = getPrxElement(currentPsiElement);
//
//        // 全局搜索引用的地方
//        Query<PsiReference> psiReferences = ReferencesSearch.search(prxElement, GlobalSearchScope.projectScope(project), false);
//        for (PsiReference psiReference : psiReferences) {
//            PsiElement psiElement = psiReference.getElement();
//        }
//
//
//        //UsageViewManager usageViewManager = UsageViewManager.getInstance(project);
//        //UsageViewPresentation usageViewPresentation = new UsageViewPresentation();
//        //usageViewPresentation.setTargetsNodeText("Classes");
//        //usageViewPresentation.setCodeUsagesString("References");
//        //usageViewPresentation.setTabText("Find Usage Plus");
//        //
//        //UsageTarget[] usageTargets = {new PsiElement2UsageTargetAdapter(prxElement, false)};
//        //if (usageTargets.length == 0) {
//        //    return;
//        //}
//        //PsiElement[] primaryElements = new ArrayList<>();
//        //List<UsageInfo> usageInfo = new ArrayList<>();
//        //for (UsageTarget usageTarget : usageTargets) {
//        //    primaryElements[0] = currentPsiElement;
//        //    UsageInfo[] usageInfo = new UsageInfo[count];
//        //    usageInfo[0] = new UsageInfo(currentPsiElement);
//        //}
//        //if (primaryElements.size() == 0) {
//        //    UsageViewManager.getInstance(project).showUsages(UsageTarget.EMPTY_ARRAY, new Usage[]{}, usageViewPresentation);
//        //} else {
//        //    Usage[] convert = UsageInfoToUsageConverter.convert(primaryElements, usageInfo);
//        //    usageViewManager.showUsages(usageTargets, convert, usageViewPresentation);
//        //}
//
//    }
//
//    private PsiElement getPrxElement(PsiElement psiElement) {
//        if (psiElement instanceof PsiMethod) {
//            PsiMethod psiMethod = (PsiMethod) currentPsiElement;
//            PsiClass psiClass = (PsiClass)psiMethod.getParent();
//        } else if (currentPsiElement instanceof  PsiClass) {
//
//        } else {
//            return;
//        }
//    }
//
//}
