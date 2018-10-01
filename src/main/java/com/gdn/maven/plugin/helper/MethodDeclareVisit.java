package com.gdn.maven.plugin.helper;

import com.gdn.maven.plugin.model.AnnotationScanResult;
import com.gdn.maven.plugin.model.AnnotationType;
import com.gdn.maven.plugin.model.ClassScanResult;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


public class MethodDeclareVisit extends VoidVisitorAdapter<ClassScanResult> {
  @Override
  public void visit(MethodDeclaration methodDeclaration, ClassScanResult classScanResult) {
    if (methodDeclaration.getAnnotations().toString().contains(AnnotationType.BEFORE.getType())
        || methodDeclaration.getAnnotations().toString().contains(AnnotationType.TEST.getType())
        || methodDeclaration.getAnnotations().toString().contains(AnnotationType.AFTER.getType())) {

      if (!methodDeclaration.getAnnotations()
          .toString()
          .contains(AnnotationType.IGNORE.getType())) {
        AnnotationScanResult annotationScanResult = new AnnotationScanResult();
        annotationScanResult.setAnnotationName(getAnnotation(methodDeclaration.getAnnotations()
            .toString()));
        annotationScanResult.setLineNumber(methodDeclaration.getBegin().get().line);
        annotationScanResult.setSupermethodName(methodDeclaration.getNameAsString());

        classScanResult.getAnnotationScanResultList().add(annotationScanResult);

        methodDeclaration.accept(new MethodCallVisit(), classScanResult);
      }
    }
    super.visit(methodDeclaration, classScanResult);
  }

  private String getAnnotation(String annotation){
    String tempAnnotation = annotation.substring(1, annotation.length() - 1);
    return tempAnnotation.replace("@", "");
  }
}