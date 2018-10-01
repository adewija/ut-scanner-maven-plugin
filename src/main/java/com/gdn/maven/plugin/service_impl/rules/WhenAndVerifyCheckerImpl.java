package com.gdn.maven.plugin.service_impl.rules;

import com.gdn.maven.plugin.model.*;
import com.gdn.maven.plugin.service_api.UnitTestRulesService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WhenAndVerifyCheckerImpl implements UnitTestRulesService {

  private static WhenAndVerifyCheckerImpl instance = null;

  private WhenAndVerifyCheckerImpl(){}

  public synchronized static WhenAndVerifyCheckerImpl getInstance() {
    if (instance == null) {
      instance = new WhenAndVerifyCheckerImpl();
    }
    return instance;
  }

  public ClassScanResult check(ClassScanResult classScanResult, List<ClassReference> classReferenceList) {
    /*
    find every annotations on one class
    */
    List<AnnotationScanResult> newList = classScanResult.getAnnotationScanResultList()
        .stream()
        .map(this::checkWhenAndVerifyBothExist)
        .collect(Collectors.toList());
    classScanResult.getAnnotationScanResultList().retainAll(newList);
    return classScanResult;
  }

  private AnnotationScanResult checkWhenAndVerifyBothExist(AnnotationScanResult initialAnnotationScanResult) {
    List<CalledMethodScanResult> removeMethodList = new ArrayList<>();
    /*
    find all the methods with method when in it
     */

    List<CalledMethodScanResult> whenMethodList = initialAnnotationScanResult.getCalledMethodScanResultList().stream()
        .filter(calledMethod-> calledMethod.getMethodCalledName().equals(MethodType.WHEN.getType()))
        .collect(Collectors.toList());

    /*
    for every when methods, find the matching verify method, if found then remove
     */
    for (CalledMethodScanResult whenMethod:whenMethodList) {
      CalledMethodScanResult verifyResult = initialAnnotationScanResult.getCalledMethodScanResultList().stream()
          .filter(verifyMethod -> verifyMethod.getMethodCalledName()
              .equals(MethodType.VERIFY.getType()))
          .filter(calledMethodScanResult -> checkSimilarity(whenMethod.getMethodCalledArguments(),
              calledMethodScanResult.getMethodCalledArguments()))
          .findFirst().orElse(null);
      if (verifyResult != null) {
        removeMethodList.add(whenMethod);
      }
      removeMethodList.add(verifyResult);
    }
      initialAnnotationScanResult.getCalledMethodScanResultList().removeAll(removeMethodList);

    return initialAnnotationScanResult;
  }

  private boolean checkSimilarity(String whenArguments, String verifyArguments) {
    /*
    check if the arguments inside when and verify is the same
     */

    if (whenArguments.contains("any") && verifyArguments.contains("any")){
      return false;
    } else {
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
      verifyArguments = verifyArguments.substring(
          verifyArguments.indexOf(MethodType.VERIFY.getType()) + MethodType.VERIFY.getType().length(), verifyArguments.lastIndexOf(")"));
      verifyArguments = regexReplaceStringPunctuation(verifyArguments);
      return whenArguments.equals(verifyArguments);
    }
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
