package org.derek.processor.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public class AnnotationUtil {

    public static int value = 0;

    public static final String SUFFIX = "$InjectAdapter";

    public static String getPackageName(ProcessingEnvironment mProcessingEnv, TypeElement typeElement) {
        return mProcessingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
    }
}
