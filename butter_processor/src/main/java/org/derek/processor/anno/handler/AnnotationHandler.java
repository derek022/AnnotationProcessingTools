package org.derek.processor.anno.handler;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.VariableElement;

public interface AnnotationHandler {
    void attachProcessingEnv(ProcessingEnvironment processingEnv);
    Map<String,List<VariableElement>> handleAnnotation(RoundEnvironment roundEnv);
}
