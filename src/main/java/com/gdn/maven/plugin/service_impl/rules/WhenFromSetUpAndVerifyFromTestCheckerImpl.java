package com.gdn.maven.plugin.service_impl.rules;

import com.gdn.maven.plugin.model.*;
import com.gdn.maven.plugin.service_api.UnitTestRulesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WhenFromSetUpAndVerifyFromTestCheckerImpl implements UnitTestRulesService {

  private static WhenFromSetUpAndVerifyFromTestCheckerImpl instance = null;

  private WhenFromSetUpAndVerifyFromTestCheckerImpl(){}

  public synchronized static WhenFromSetUpAndVerifyFromTestCheckerImpl getInstance() {
    if (instance == null) {
      instance = new WhenFromSetUpAndVerifyFromTestCheckerImpl();
    }
    return instance;
  }

  public ClassScanResult check(ClassScanResult classScanResult,
      List<ClassReference> classReferenceList) {

    List<CalledMethodScanResult> removeMethodList = new ArrayList<>();
    /*
    find annotation before from the @Before
     */
    AnnotationScanResult annotationBefore = classScanResult.getAnnotationScanResultList()
        .stream()
        .filter(annotationScanResult -> annotationScanResult.getAnnotationName()
            .contains(AnnotationType.BEFORE.getType()))
        .findAny()
        .orElse(null);

    /*
    check when method under the annotation before
     */
    if (annotationBefore != null) {
      List<CalledMethodScanResult> whenMethodList = annotationBefore.getCalledMethodScanResultList()
          .stream()
          .filter(Objects::nonNull)
          .filter(calledMethodScanResult -> !calledMethodScanResult.getMethodCalledArguments().contains("any"))
          .filter(calledMethod -> calledMethod.getMethodCalledName()
              .equals(MethodType.WHEN.getType()))
          .collect(Collectors.toList());
      /*
      for every when method found on before, search thorough for matching verify from every
      annotations down below
       */
      for (CalledMethodScanResult whenMethod : whenMethodList) {
        List<AnnotationScanResult> allAnnotations = classScanResult.getAnnotationScanResultList()
            .stream()
            .filter(initialScanResult -> initialScanResult.getAnnotationName()
                .contains(AnnotationType.TEST.getType()))
            .collect(Collectors.toList());
        /*
        for every matched verify, collect and remove them and also the when method from @Before
         */
        for (AnnotationScanResult annotationWithVerify : allAnnotations) {
          removeMethodList.addAll(findVerifyMethodsFromAnnotation(whenMethod,
              annotationWithVerify));
        }
        removeMethodList.add(whenMethod);
      }
      classScanResult.getAnnotationScanResultList()
          .forEach(e -> e.getCalledMethodScanResultList().removeAll(removeMethodList));
    }
    return classScanResult;
  }

  private List<CalledMethodScanResult> findVerifyMethodsFromAnnotation(CalledMethodScanResult whenMethod,
      AnnotationScanResult annotationWithVerify) {
    /*
    find verify method from many annotations
     */

    List<CalledMethodScanResult> verifyList = annotationWithVerify.getCalledMethodScanResultList()
        .stream()
        .filter(calledMethod -> calledMethod.getMethodCalledName()
            .equals(MethodType.VERIFY.getType()))
        .filter(calledMethod -> !calledMethod.getMethodCalledArguments().contains("any"))
        .filter(calledMethod -> checkSimilarity(whenMethod.getMethodCalledArguments(),
            calledMethod.getMethodCalledArguments()))
        .collect(Collectors.toList());
    return verifyList;
  }

  private boolean checkSimilarity(String whenArguments, String verifyArguments) {
      if (whenArguments.contains(".then")) {
        whenArguments = whenArguments.substring(
            whenArguments.indexOf(MethodType.WHEN.getType()) + MethodType.WHEN.getType().length(),
            whenArguments.indexOf(".then"));
        whenArguments = whenArguments.substring(0, whenArguments.lastIndexOf(")"));
      } else {
        whenArguments = whenArguments.substring(
            whenArguments.indexOf(MethodType.WHEN.getType()) + MethodType.WHEN.getType().length(),
            whenArguments.lastIndexOf(")"));
      }
      whenArguments = regexReplaceStringPunctuation(whenArguments);

      if (verifyArguments.substring(verifyArguments.indexOf("("), verifyArguments.indexOf(")"))
          .contains("times")) {
        String temp = verifyArguments.substring(verifyArguments.indexOf("times"),
            verifyArguments.indexOf(")", verifyArguments.indexOf(")") + 1));
        verifyArguments = verifyArguments.replace(temp, "");
      }
      else {
        verifyArguments = verifyArguments.substring(
            verifyArguments.indexOf(MethodType.VERIFY.getType()) + MethodType.VERIFY.getType().length(), verifyArguments.lastIndexOf(")"));
      }
      verifyArguments = regexReplaceStringPunctuation(verifyArguments);

      return whenArguments.equals(verifyArguments);
  }

  private String regexReplaceStringPunctuation(String str) {
    if (str.contains("this.")) {
      str = str.replaceAll("this.", "");
    }
    str = str.replaceAll("\\p{P}", "");
    str = str.replaceAll("\\s+", "");

    return str;
  }

}
