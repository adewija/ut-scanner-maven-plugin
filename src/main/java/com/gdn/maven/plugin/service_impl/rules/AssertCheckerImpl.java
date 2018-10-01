package com.gdn.maven.plugin.service_impl.rules;

import com.gdn.maven.plugin.model.*;
import com.gdn.maven.plugin.service_api.UnitTestRulesService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AssertCheckerImpl implements UnitTestRulesService {

  private static AssertCheckerImpl instance = null;

  private AssertCheckerImpl(){}

  public synchronized static AssertCheckerImpl getInstance() {
    if (instance == null) {
      instance = new AssertCheckerImpl();
    }
    return instance;
  }

  public ClassScanResult check(ClassScanResult classScanResult,
      List<ClassReference> classReferenceList) {

    /*
    find every annotations on one class
     */

    List<AnnotationScanResult> finalResults = classScanResult.getAnnotationScanResultList()
        .stream()
        .filter(annotationScanResult -> annotationScanResult.getAnnotationName().equals(AnnotationType.TEST.getType()))
        .map(annotationScanResult -> checkAndDeleteUnnecessaryMethods(annotationScanResult,
            classReferenceList))
        .collect(Collectors.toList());

    classScanResult.getAnnotationScanResultList().retainAll(finalResults);
    return classScanResult;
  }

  private AnnotationScanResult checkAndDeleteUnnecessaryMethods(AnnotationScanResult annotationScanResult,
      List<ClassReference> classReferenceList) {

    List<CalledMethodScanResult> allMethodRemove = new ArrayList<>();

    /*
    check and delete all method that are not from ClassReference
     */

    annotationScanResult = deleteAllMethodAreNotFromClassReference(annotationScanResult, classReferenceList);

    /*
    find every returning methods on one annotation
     */

    List<CalledMethodScanResult> returningMethodResults =
        annotationScanResult.getCalledMethodScanResultList()
            .stream()
            .filter(calledMethodScanResult -> calledMethodScanResult.getMethodCalledArguments()
                .contains("="))
            .collect(Collectors.toList());

      /*
      for every returning methods found, searching for every assert on that same @Test
       */

    for (CalledMethodScanResult returningMethod : returningMethodResults) {
      List<CalledMethodScanResult> assertResults =
          annotationScanResult.getCalledMethodScanResultList()
              .stream()
              .filter(assertMethod -> assertMethod.getMethodCalledName()
                  .contains(MethodType.ASSERT.getType()))
              .filter(assertMethod -> assertMethod.getMethodCalledArguments()
                  .contains(findClassDeclarationFromMethod(returningMethod.getMethodCalledArguments())))
              .collect(Collectors.toList());

        /*
        checking whether assertNotNull is the only assert method on the @Test, so it will still
        show as minor problem.
         */

      if (assertResults.stream()
          .allMatch(assertMethod -> assertMethod.getMethodCalledName().equals(MethodType.ASSERT_NOT_NULL.getType())) && !assertResults.isEmpty()) {
        allMethodRemove.add(returningMethod);
        List<CalledMethodScanResult> removeAssertNotNulls = assertResults.stream()
            .filter(assertMethod -> assertMethod.getMethodCalledName().equals(MethodType.ASSERT_NOT_NULL.getType()))
            .collect(Collectors.toList());
        assertResults.removeAll(removeAssertNotNulls);
      }

      if (!assertResults.isEmpty()) {
        allMethodRemove.add(returningMethod);
        allMethodRemove.addAll(assertResults);
      }
    }

    annotationScanResult.getCalledMethodScanResultList().removeAll(allMethodRemove);
    return annotationScanResult;
  }

  private AnnotationScanResult deleteAllMethodAreNotFromClassReference(AnnotationScanResult annotationScanResult, List<ClassReference> classReferenceList){
    List<CalledMethodScanResult> deletedList = annotationScanResult.getCalledMethodScanResultList().stream()
        .filter(calledMethodScanResult -> calledMethodScanResult.getMethodCalledArguments()
            .contains("="))
        .filter(calledMethodScanResult -> {
          for (ClassReference classReference : classReferenceList){
            for (String methodFromClassReference : classReference.getMethodWithReturnTypeName()){
              if (methodFromClassReference.equals(calledMethodScanResult.getMethodCalledName())){
                return false;
              }
            }
          }
          return true;
        })
        .collect(Collectors.toList());

    annotationScanResult.getCalledMethodScanResultList().removeAll(deletedList);
    return annotationScanResult;
  }

  private String findClassDeclarationFromMethod(String methodArguments) {
    String origin = methodArguments;

    if (origin.contains("this.")) {
      origin = origin.replaceAll("this.", "");
    }

    origin = origin.substring(0, origin.indexOf("="));

    String[] tempArray = origin.split("\\s+");

    origin = tempArray[tempArray.length - 1];

    return origin;
  }
}
