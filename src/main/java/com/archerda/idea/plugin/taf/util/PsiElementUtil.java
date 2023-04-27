package com.archerda.idea.plugin.taf.util;

import com.archerda.idea.plugin.taf.constant.Attribute;
import com.archerda.idea.plugin.taf.constant.QualifiedName;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiUtil;
import com.intellij.psi.xml.XmlFile;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * <p>描述：PsiElement 的工具类：
 * 1 获取属性、方法、类上面的注解
 * 2 获取属性、方法、类上面注解的属性值
 * 3 判断给定元素是否是接口、Mapper 接口或 Mapper 接口内容定义的方法</p>
 */
@SuppressWarnings("unused")
public class PsiElementUtil {

    /**
     * 获取类中属性上的注解
     *
     * @param psiElement              给定元素，该元素必须为属性
     * @param annotationQualifiedName 注解的限定名，即全类名
     * @return 注解
     */
    public static PsiAnnotation getAnnotationAtField(PsiElement psiElement, String annotationQualifiedName) {
        if (!(psiElement instanceof PsiField)) {
            return null;
        }

        PsiField psiField = (PsiField) psiElement;

        // 根据注解限定名获取指定注解
        return psiField.getAnnotation(annotationQualifiedName);
    }

    /**
     * 获取方法上的注解
     *
     * @param psiElement              给定元素，该元素必须为方法
     * @param annotationQualifiedName 注解的限定名，即全类名
     * @return 注解
     */
    public static PsiAnnotation getAnnotationAtMethod(PsiElement psiElement, String annotationQualifiedName) {
        if (!(psiElement instanceof PsiMethod)) {
            return null;
        }

        PsiMethod psiMethod = (PsiMethod) psiElement;

        // 根据注解限定名获取指定注解
        return psiMethod.getAnnotation(annotationQualifiedName);
    }

    /**
     * 获取类上的注解
     *
     * @param psiElement              给定元素，该元素可以为属性或方法
     * @param annotationQualifiedName 注解的限定名，即全类名
     * @return 注解
     */
    public static PsiAnnotation getAnnotationAtClass(PsiElement psiElement, String annotationQualifiedName) {
        // 如果是方法或变量，则获取父元素
        if (psiElement instanceof PsiField || psiElement instanceof PsiMethod) {
            psiElement = psiElement.getParent();
        }

        if (!(psiElement instanceof PsiClass)) {
            return null;
        }

        PsiClass psiClass = (PsiClass) psiElement;

        // 根据注解限定名获取指定注解
        return psiClass.getAnnotation(annotationQualifiedName);
    }

    /**
     * 获取类属性的注解中的属性的值
     *
     * @param psiElement              给定元素，该元素应该是一个类的属性
     * @param annotationQualifiedName 注解的限定名，即全类名
     * @param attributeName           注解属性的名称
     * @return 注解属性的值
     */
    public static String getAttributeValueOfAnnotationAtField(PsiElement psiElement,
                                                              String annotationQualifiedName,
                                                              String attributeName) {
        PsiAnnotation psiAnnotation = getAnnotationAtField(psiElement, annotationQualifiedName);
        return getAttributeName(attributeName, psiAnnotation);
    }

    /**
     * 获取方法上的注解中的属性的值
     *
     * @param psiElement              给定元素，该元素应该是一个类的方法
     * @param annotationQualifiedName 注解的限定名，即全类名
     * @param attributeName           注解属性的名称
     * @return 注解属性的值
     */
    public static String getAttributeValueOfAnnotationAtMethod(PsiElement psiElement,
                                                               String annotationQualifiedName,
                                                               String attributeName) {
        PsiAnnotation annotationAtMethod = getAnnotationAtMethod(psiElement, annotationQualifiedName);
        return getAttributeName(attributeName, annotationAtMethod);
    }

    /**
     * 获取类上的注解中的属性的值
     *
     * @param psiElement              给定元素，该元素应该是一个类
     * @param annotationQualifiedName 注解的限定名，即全类名
     * @param attributeName           注解属性的名称
     * @return 注解属性的值
     */
    public static String getAttributeValueOfAnnotationAtClass(PsiElement psiElement,
                                                              String annotationQualifiedName,
                                                              String attributeName) {
        PsiAnnotation annotationAtClass = getAnnotationAtClass(psiElement, annotationQualifiedName);
        return getAttributeName(attributeName, annotationAtClass);
    }

    /**
     * 获取指定元素 psiElement 的指定注解 annotationQualifiedName 的指定属性 attributeName 的值
     *
     * @param psiElement              可以是属性，方法，类
     * @param annotationQualifiedName 注解限定名
     * @param attributeName           注解中的属性名
     * @return 属性值
     */
    public static String getAttributeValue(PsiElement psiElement,
                                           String annotationQualifiedName,
                                           String attributeName) {
        return getAttributeValue(psiElement, annotationQualifiedName, attributeName, true);
    }

    /**
     * 获取指定元素 psiElement 的指定注解 annotationQualifiedName 的指定属性 attributeName 的值
     *
     * @param psiElement              可以是属性，方法，类
     * @param annotationQualifiedName 注解限定名
     * @param attributeName           注解中的属性名
     * @param isExtensible            是不允许扩展搜索注解
     * @return 属性值
     */
    public static String getAttributeValue(PsiElement psiElement,
                                           String annotationQualifiedName,
                                           String attributeName,
                                           boolean isExtensible) {
        if (psiElement instanceof PsiField) {
            String value = getAttributeValueOfAnnotationAtField(psiElement, annotationQualifiedName, attributeName);
            if (StringUtils.isBlank(value) && isExtensible) {
                value = getAttributeValueOfAnnotationAtMethod(psiElement, annotationQualifiedName, attributeName);
                if (StringUtils.isBlank(value)) {
                    value = getAttributeValueOfAnnotationAtClass(psiElement, annotationQualifiedName, attributeName);
                }
            }
            return value;
        }

        if (psiElement instanceof PsiMethod) {
            String value = getAttributeValueOfAnnotationAtMethod(psiElement, annotationQualifiedName, attributeName);
            if (StringUtils.isBlank(value) && isExtensible) {
                value = getAttributeValueOfAnnotationAtClass(psiElement, annotationQualifiedName, attributeName);
            }
            return value;
        }

        if (psiElement instanceof PsiClass) {
            return getAttributeValueOfAnnotationAtClass(psiElement, annotationQualifiedName, attributeName);
        }

        return null;
    }

    /**
     * 从指定注解中获取属性值
     *
     * @param attributeName 属性名称
     * @param psiAnnotation 指定注解
     * @return 属性值
     */
    private static String getAttributeName(String attributeName, PsiAnnotation psiAnnotation) {
        if (psiAnnotation == null) {
            return null;
        }

        // 获取 @RequestMapping 中的 url
        PsiAnnotationMemberValue value = psiAnnotation.findAttributeValue(attributeName);
        if (value == null) {
            return null;
        }

        // Text 里面多了引号
        return value.getText().replaceAll("\"", "");
    }

    /**
     * 获取方法的 url
     *
     * @param psiElement 该方法
     * @param annotation 方法所有类的注解
     * @param attribute  属性
     * @return url
     */
    public static String getReferenceUrl(PsiElement psiElement, String annotation, String attribute) {
        if (psiElement instanceof PsiMethod) {
            PsiMethod currentPsiMethod = (PsiMethod) psiElement;
            // 获取 @RequestMapping 注解的 value/name 属性，即接口的 Url 地址
            String valueInRequestMapping = PsiElementUtil.getAttributeValue(currentPsiMethod,
                    QualifiedName.Annotation.REQUEST_MAPPING, Attribute.RequestMapping.VALUE, false);
            String nameInRequestMapping = PsiElementUtil.getAttributeValue(currentPsiMethod,
                    QualifiedName.Annotation.REQUEST_MAPPING, Attribute.RequestMapping.NAME, false);
            String requestMapping = (StringUtils.isBlank(valueInRequestMapping) ? "" : valueInRequestMapping)
                    + (StringUtils.isBlank(nameInRequestMapping) ? "" : nameInRequestMapping);

            // 获取 @GetMapping 注解的 value/name 属性，即接口的 Url 地址
            String valueInGetMapping = PsiElementUtil.getAttributeValue(currentPsiMethod,
                    QualifiedName.Annotation.GET_MAPPING, Attribute.RequestMapping.VALUE);
            String nameInGetMapping = PsiElementUtil.getAttributeValue(currentPsiMethod,
                    QualifiedName.Annotation.GET_MAPPING, Attribute.RequestMapping.NAME);
            String getMapping = (StringUtils.isBlank(valueInGetMapping) ? "" : valueInGetMapping)
                    + (StringUtils.isBlank(nameInGetMapping) ? "" : nameInGetMapping);

            // 获取 @PostMapping 注解的 value/name 属性，即接口的 Url 地址
            String valueInPostMapping = PsiElementUtil.getAttributeValue(currentPsiMethod,
                    QualifiedName.Annotation.POST_MAPPING, Attribute.RequestMapping.VALUE);
            String nameInPostMapping = PsiElementUtil.getAttributeValue(currentPsiMethod,
                    QualifiedName.Annotation.POST_MAPPING, Attribute.RequestMapping.NAME);
            String postMapping = (StringUtils.isBlank(valueInPostMapping) ? "" : valueInPostMapping)
                    + (StringUtils.isBlank(nameInPostMapping) ? "" : nameInPostMapping);

            // 获取 @DeleteMapping 注解的 value/name 属性，即接口的 Url 地址
            String valueInDeleteMapping = PsiElementUtil.getAttributeValueOfAnnotationAtMethod(psiElement,
                    QualifiedName.Annotation.DELETE_MAPPING, Attribute.RequestMapping.VALUE);
            String nameInDeleteMapping = PsiElementUtil.getAttributeValueOfAnnotationAtMethod(psiElement,
                    QualifiedName.Annotation.DELETE_MAPPING, Attribute.RequestMapping.NAME);
            String deleteMapping = (StringUtils.isBlank(valueInDeleteMapping) ? "" : valueInDeleteMapping)
                    + (StringUtils.isBlank(nameInDeleteMapping) ? "" : nameInDeleteMapping);

            // 获取 @PutMapping 注解的 value/name 属性，即接口的 Url 地址
            String valueInPutMapping = PsiElementUtil.getAttributeValueOfAnnotationAtMethod(psiElement,
                    QualifiedName.Annotation.PUT_MAPPING, Attribute.RequestMapping.VALUE);
            String nameInPutMapping = PsiElementUtil.getAttributeValueOfAnnotationAtMethod(psiElement,
                    QualifiedName.Annotation.PUT_MAPPING, Attribute.RequestMapping.NAME);
            String putMapping = (StringUtils.isBlank(valueInPutMapping) ? "" : valueInPutMapping)
                    + (StringUtils.isBlank(nameInPutMapping) ? "" : nameInPutMapping);

            // psiElement 所在 Class 的注解属性值
            String attributeValueOfAnnotationAtClass = PsiElementUtil.getAttributeValueOfAnnotationAtClass(psiElement, annotation, attribute);
            String attributeValue = StringUtils.isBlank(attributeValueOfAnnotationAtClass) ? "" : attributeValueOfAnnotationAtClass;
            System.out.println(attributeValue);
            return attributeValue + requestMapping + getMapping + postMapping + putMapping + deleteMapping;
        }

        return PsiElementUtil.getAttributeValue(psiElement, annotation, attribute);
    }

    /**
     * 判断 PsiElement 是否是接口：
     * 1 必须是 PsiClass
     * 2 必须是 Interface
     *
     * @param psiElement 给定元素
     * @return 是/否是接口
     */
    public static boolean isInterface(PsiElement psiElement) {
        return psiElement instanceof PsiClass && ((PsiClass) psiElement).isInterface();
    }

    /**
     * 是否是 Mapper 接口：
     * 1 必须是 PsiClass
     * 2 必须是 Interface
     * 3 必须使用 @Repository 注解，对于使用 @Component 或 @Service 标注的 Mapper 接口不予考虑
     *
     * @param psiElement 给定元素
     * @return 是/否是 Mapper 接口
     */
    public static boolean isMapperInterface(PsiElement psiElement) {
        return isInterface(psiElement)
                && (getAnnotationAtClass(psiElement, QualifiedName.Annotation.REPOSITORY) != null
                || getAnnotationAtClass(psiElement, QualifiedName.Annotation.MAPPER) != null
                || Objects.requireNonNull(((PsiClass) psiElement).getName()).endsWith("Mapper")
        );
    }

    /**
     * 判断是否是一个 Mapper 对象变量
     *
     * @param currentPsiElement 给定元素
     * @return 是/否是 Mapper 对象变量
     */
    public static boolean isMapperInterfaceField(PsiElement currentPsiElement) {
        if (currentPsiElement instanceof PsiField) {
            PsiTypeElement psiTypeElement = ((PsiField) currentPsiElement).getTypeElement();
            if (psiTypeElement == null) {
                return false;
            }

            PsiClass psiClass = PsiUtil.resolveClassInType(psiTypeElement.getType());
            if (psiClass == null) {
                return false;
            }
            return isMapperInterface(psiClass);
        }

        return false;
    }

    /**
     * 是否是 Mapper 接口下定义的方法：
     * 1 必须是 PsiMethod
     * 2 其父元素必须是 Interface
     * 3 其父元素必须使用 @Repository 注解，对于使用 @Component 或 @Service 标注的 Mapper 接口不予考虑
     *
     * @param psiElement 给定元素
     * @return 是/否是 Mapper 接口方法
     */
    public static boolean isMapperMethod(PsiElement psiElement) {
        return psiElement instanceof PsiMethod && isMapperInterface(psiElement.getParent());
    }

    /**
     * 判断是否是一个 Feign 方法：
     * 1 给定元素必须是 PsiMethod
     * 2 给定元素的方法上必须使用了 @RequestMapping 注解声明 Url
     * 3 给定元素的类上必须使用 @FeignClient 注解来指定 Feign 方法的具体实现
     *
     * @param psiElement 给定元素
     * @return 是/否是 Feign 方法
     */
    public static boolean isFeignMethod(PsiElement psiElement) {
        return psiElement instanceof PsiMethod && (
                getAnnotationAtMethod(psiElement, QualifiedName.Annotation.REQUEST_MAPPING) != null
                        || getAnnotationAtMethod(psiElement, QualifiedName.Annotation.GET_MAPPING) != null
                        || getAnnotationAtMethod(psiElement, QualifiedName.Annotation.POST_MAPPING) != null
                        || getAnnotationAtMethod(psiElement, QualifiedName.Annotation.PUT_MAPPING) != null
                        || getAnnotationAtMethod(psiElement, QualifiedName.Annotation.DELETE_MAPPING) != null
        ) && getAnnotationAtClass(psiElement.getParent(), QualifiedName.Annotation.FEIGN_CLIENT) != null;
    }

    /**
     * 判断是否是一个 Feign 方法的 Controller 实现
     * 1 给定元素必须是 PsiMethod
     * 2 给定元素的方法上必须使用了 @RequestMapping、@GetMapping、@PostMapping 注解声明了该方法
     * 3 给定元素的类上必须使用 @Controller 或 @RestController 注解来指定 Feign 方法的具体实现
     *
     * @param psiElement 给定元素
     * @return 是/否是 Feign 的实现方法
     */
    public static boolean isFeignImplMethod(PsiElement psiElement) {
        if (!(psiElement instanceof PsiMethod)) {
            return false;
        }

        if (getAnnotationAtMethod(psiElement, QualifiedName.Annotation.REQUEST_MAPPING) == null
                && getAnnotationAtMethod(psiElement, QualifiedName.Annotation.GET_MAPPING) == null
                && getAnnotationAtMethod(psiElement, QualifiedName.Annotation.PUT_MAPPING) == null
                && getAnnotationAtMethod(psiElement, QualifiedName.Annotation.DELETE_MAPPING) == null
                && getAnnotationAtMethod(psiElement, QualifiedName.Annotation.POST_MAPPING) == null) {
            return false;
        }

        return getAnnotationAtClass(psiElement.getParent(), QualifiedName.Annotation.CONTROLLER) != null
                || getAnnotationAtClass(psiElement.getParent(), QualifiedName.Annotation.REST_CONTROLLER) != null;
    }

    /**
     * 判断是否是 Mapper 映射文件
     *
     * @param psiFile 给定元素
     * @return 是/否是 Mapper 映射文件
     */
    public static boolean isMapperXml(PsiFile psiFile) {
        return psiFile instanceof XmlFile;
    }


    /**
     * 判断是否taf的接口prx类或者方法
     */
    public static boolean isTafPrxClassOrMethod(PsiElement psiElement) {
        if (!(psiElement instanceof PsiMethod || psiElement instanceof PsiClass)) {
            return false;
        }
        PsiClass psiClass;
        if (psiElement instanceof PsiClass) {
            psiClass = (PsiClass) psiElement;
        } else {
            psiClass = (PsiClass) psiElement.getParent();
        }
        return getAnnotationAtClass(psiClass, QualifiedName.Annotation.SERVANT) != null;
    }

    /**
     * 判断是否taf的实现类或者方法
     */
    public static boolean isTafImplClassOrMethod(PsiElement psiElement) {
        if (!(psiElement instanceof PsiMethod || psiElement instanceof PsiClass)) {
            return false;
        }
        PsiClass psiClass;
        if (psiElement instanceof PsiClass) {
            psiClass = (PsiClass) psiElement;
        } else {
            psiClass = (PsiClass) psiElement.getParent();
        }
        return getAnnotationAtClass(psiClass, QualifiedName.Annotation.TAF_SERVANT) != null;
    }
}
