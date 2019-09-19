package org.derek.processor;

import com.google.auto.service.AutoService;

import org.derek.annotation.ViewInjector;
import org.derek.processor.anno.handler.AnnotationHandler;
import org.derek.processor.anno.handler.ViewInjectHandler;
import org.derek.processor.writer.AdapterWriter;
import org.derek.processor.writer.DefaultJavaFileWriter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static javax.tools.Diagnostic.Kind.ERROR;

//自动设置services 的javax
//取代之前META-INF / services / javax.annotation.processing.Processor
@AutoService(Processor.class)

// 添加支持的注解类型 , source 版本 方式1
//@SupportedAnnotationTypes("org.derek.annotation.BindView")
//@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ViewInjectorProcessor extends AbstractProcessor {

    /**
     * // 添加支持的注解类型 方式2
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(ViewInjector.class.getCanonicalName());
        return types;
    }

    /**
     * 返回支持的 source 版本
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 所有注解处理器的列表
     */
    List<AnnotationHandler> mHandlers = new LinkedList<AnnotationHandler>();
    /**
     * 类型与字段的关联表,用于在写入Java文件时按类型来写不同的文件和字段
     */
    Map<String, List<VariableElement>> map = new HashMap<>();
    /**
     *
     */
    AdapterWriter mWriter;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        registerHandlers();
        mWriter = new DefaultJavaFileWriter(processingEnv);
    }

    private void registerHandlers() {
        // 添加View 注入
        mHandlers.add(new ViewInjectHandler());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        Map 在每次 处理前必须清空，一个Processor类要走三次process
        Set<?> sets = roundEnvironment.getRootElements();
        Iterator iterator = sets.iterator();
        System.out.println(">>>>>>>>>>>>>>  start >>>>>>>>>>>>>>>>>>");
        while (iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
        System.out.println(">>>>>>>>>>>>>>  end >>>>>>>>>>>>>>>>>>");
        map.clear();

        for (AnnotationHandler handler : mHandlers){
            handler.attachProcessingEnv(processingEnv);
            map.putAll(handler.handleAnnotation(roundEnvironment));
        }
        mWriter.generate(map);
        return false;
    }

    protected void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(ERROR, message, element);
    }

}
