package org.derek.processor.writer;

import org.derek.processor.anno.handler.AnnotationHandler;
import org.derek.processor.util.AnnotationUtil;
import org.derek.processor.util.IOUtil;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;


import static org.derek.processor.util.AnnotationUtil.SUFFIX;

public abstract class AbsWriter implements AdapterWriter {
    ProcessingEnvironment mProcessingEnv;
    Filer mFiler;

    public AbsWriter(ProcessingEnvironment mProcessingEnv) {
        this.mProcessingEnv = mProcessingEnv;
        mFiler = mProcessingEnv.getFiler();
    }

    @Override
    public void generate(Map<String, List<VariableElement>> typeMap) {
        Iterator<Map.Entry<String,List<VariableElement>>> iterator = typeMap.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<String,List<VariableElement>> entry = iterator.next();
            List<VariableElement> cacheElements = entry.getValue();
            if (cacheElements == null || cacheElements.size() == 0){
                continue;
            }

            InjectorInfo info = createInjectorInfo(cacheElements.get(0));
            Writer writer  = null;
            JavaFileObject javaFileObject;

            try {
                javaFileObject = mFiler.createSourceFile(info.getClassFullPath());
                writer = javaFileObject.openWriter();
                generateImport(writer,info);
                // 写入该类中的所有字段到findViews方法中
                for (VariableElement variableElement : entry.getValue()) {
                    IOUtil.print(variableElement.getSimpleName().toString() + ">>>>>>>>>>>>>>>>>>>>");
                    writeField(writer, variableElement, info);
                }
                writeEnd(writer);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                IOUtil.closeQuitly(writer);
            }

        }
    }

    /**
     * @param element
     * @return
     */
    protected InjectorInfo createInjectorInfo(VariableElement element) {
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        String packageName = AnnotationUtil.getPackageName(mProcessingEnv, typeElement);
        String className = typeElement.getSimpleName().toString();
        System.out.println("packageName " + packageName);
        System.out.println("className " + className);

        return new InjectorInfo(packageName, className);
    }

    /**
     * @param writer
     * @param info
     * @throws IOException
     */
    protected abstract void generateImport(Writer writer, InjectorInfo info) throws IOException;

    /**
     * @param writer
     * @param element
     * @param info
     * @throws IOException
     */
    protected abstract void writeField(Writer writer, VariableElement element, InjectorInfo info) throws IOException;

    /**
     * @param writer
     * @throws IOException
     */
    protected abstract void writeEnd(Writer writer) throws IOException;


    /**
     * 注解相关的信息实体类
     *
     * @author mrsimple
     */
    public static class InjectorInfo {
        /**
         * 被注解的类的包名
         */
        public String packageName;
        /**
         * 被注解的类的类名
         */
        public String classlName;
        /**
         * 要创建的InjectAdapter类的完整路径,新类的名字为被注解的类名 + "$InjectAdapter", 与被注解的类在同一个包下
         */
        public String newClassName;

        public InjectorInfo(String packageName, String classlName) {
            this.packageName = packageName;
            newClassName = classlName + SUFFIX;
            this.classlName = classlName;
        }

        public String getClassFullPath() {
            return packageName + "." + newClassName;
        }
    }
}
