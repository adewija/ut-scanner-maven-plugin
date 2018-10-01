package com.gdn.maven.plugin.service_impl.rules;

import com.gdn.maven.plugin.model.*;
import com.gdn.maven.plugin.service_api.UnitTestRulesService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProblematicAssertCheckerImpl implements UnitTestRulesService {

  private static ProblematicAssertCheckerImpl instance = null;

  private ProblematicAssertCheckerImpl(){}

  public synchronized static ProblematicAssertCheckerImpl getInstance() {
    if (instance == null) {
      instance = new ProblematicAssertCheckerImpl();
    }
    return instance;
  }

  public ClassScanResult check(ClassScanResult classScanResult,
      List<ClassReference> classReferenceList) {
    /*
    find every annotations from one class
     */
    List<AnnotationScanResult> finalResults = classScanResult.getAnnotationScanResultList()
        .stream()
        .map(this::checkMethodFromInjectedMock)
        .collect(Collectors.toList());

    classScanResult.getAnnotationScanResultList().retainAll(finalResults);

    return classScanResult;
  }

  private AnnotationScanResult checkMethodFromInjectedMock(AnnotationScanResult annotationScanResult) {
    /*
    find every assert on one annotations that still remains after checking with returning
    mock-injected method, except assertNotNull, assertTrue(True) or assertFalse(False) and remove
    them
     */
    List<CalledMethodScanResult> assertResults =
        annotationScanResult.getCalledMethodScanResultList()
            .stream()
            .filter(calledMethodScanResult -> calledMethodScanResult.getMethodCalledName()
                .contains(MethodType.ASSERT.getType()))
            .filter(calledMethodScanResult -> !calledMethodScanResult.getMethodCalledName()
                .equals(MethodType.ASSERT_NOT_NULL.getType()))
            .filter(calledMethodScanResult -> !findTrueFalseArgument(calledMethodScanResult))
            .collect(Collectors.toList());

    annotationScanResult.getCalledMethodScanResultList().removeAll(assertResults);

    return annotationScanResult;
  }

  private boolean findTrueFalseArgument(CalledMethodScanResult calledMethodScanResult) {
    /*
    check the assert method if there is assertTrue(True) or assertFalse(False)
     */
    String checkArgument = calledMethodScanResult.getMethodCalledArguments();
    checkArgument =
        checkArgument.substring(checkArgument.indexOf("(") + 1, checkArgument.lastIndexOf(")"));
    if (checkArgument.contains(",")) {
      String[] arguments = checkArgument.split(",");
      Arrays.stream(arguments).forEach(String::trim);

      if (arguments[1].trim().equalsIgnoreCase("true")) {
        String errorMessage = String.format(
            "Don't use true as an argument for : %s at Line %d",
            findArguments(calledMethodScanResult.getMethodCalledArguments()), calledMethodScanResult.getLineNumber());
        calledMethodScanResult.setErrorMessage(errorMessage);
        return true;
      } else if (arguments[1].trim().equalsIgnoreCase("false")) {
        String errorMessage = String.format(
            "Don't use false as an argument for : %s at Line %d",
            findArguments(calledMethodScanResult.getMethodCalledArguments()), calledMethodScanResult.getLineNumber());
        calledMethodScanResult.setErrorMessage(errorMessage);
        return true;
      }
      return false;

    } else {
      if (checkArgument.equalsIgnoreCase("true")) {
        String errorMessage = String.format(
            "Don't use true as an argument for : %s at Line %d",
            findArguments(calledMethodScanResult.getMethodCalledArguments()), calledMethodScanResult.getLineNumber());
        calledMethodScanResult.setErrorMessage(errorMessage);
        return true;
      } else if (checkArgument.equalsIgnoreCase("false")) {
        String errorMessage = String.format(
            "Don't use false as an argument for : %s at Line %d",
            findArguments(calledMethodScanResult.getMethodCalledArguments()), calledMethodScanResult.getLineNumber());
        calledMethodScanResult.setErrorMessage(errorMessage);
        return true;
      }
      return false;
    }
  }

  private String findArguments(String argumentWithTokenRange) {
    String tempArgument = argumentWithTokenRange;
    tempArgument = tempArgument.replaceAll("\\s+", "");
    return tempArgument;
  }
}
