package com.gdn.maven.plugin.service_impl.rules;

import com.gdn.maven.plugin.model.*;
import com.gdn.maven.plugin.service_api.UnitTestRulesService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VerifyNoMoreInteractionCheckerImpl implements UnitTestRulesService {

  private static VerifyNoMoreInteractionCheckerImpl instance = null;

  private VerifyNoMoreInteractionCheckerImpl(){}

  public synchronized static VerifyNoMoreInteractionCheckerImpl getInstance() {
    if (instance == null) {
      instance = new VerifyNoMoreInteractionCheckerImpl();
    }
    return instance;
  }

  public ClassScanResult check(ClassScanResult classScanResult,
      List<ClassReference> classReferenceList) {

    List<String> findMockList = new ArrayList<>();
    List<AnnotationScanResult> annotationScanResultsList =
        classScanResult.getAnnotationScanResultList();
    List<MockScanResult> mockScanResultList = classScanResult.getMockScanResultList();
    /*
    for every annotations, find method with the name verify no more interactions except
    from the @Before
    */
    if (!mockScanResultList.isEmpty()) {
      annotationScanResultsList.stream()
          .filter(annotationScanResult -> !annotationScanResult.getAnnotationName()
              .contains(AnnotationType.BEFORE.getType()))
          .forEach(annotationScanResult -> {
            List<CalledMethodScanResult> calledMethodScanResultListFiltered =
                annotationScanResult.getCalledMethodScanResultList()
                    .stream()
                    .filter(calledMethodScanResult -> calledMethodScanResult.getMethodCalledName()
                        .contains(MethodType.VERIFY_NO_MORE_INTERACTION.getType()))
                    .filter(calledMethodScanResult -> checkArgumentAndUpdateFindMockList(
                        mockScanResultList,
                        calledMethodScanResult.getMethodCalledArguments(),
                        findMockList))
                    .collect(Collectors.toList());

            annotationScanResult.getCalledMethodScanResultList()
                .removeAll(calledMethodScanResultListFiltered);
          });

      /*
      Remove the mock list which matching verify no more interactions already exist
       */
      List<MockScanResult> result =
          classScanResult.getMockScanResultList().stream().map(mockScanResult -> {
            for (String findMock : findMockList) {
              if (mockScanResult.getMockName().equals(findMock)) {
                return null;
              }
            }
            return mockScanResult;
          }).collect(Collectors.toList());

      classScanResult.getMockScanResultList().retainAll(result);
    }

    return classScanResult;
  }

  private boolean checkArgumentAndUpdateFindMockList(List<MockScanResult> mockScanResultList,
      String methodCalledArgument,
      List<String> findMockList) {
    /*
    check the argument from method verify no more interactions whether they match with mock
    declaration list
     */
    String[] argumentList = findArguments(methodCalledArgument);
    boolean result = false;

    for (MockScanResult mockScanResult : mockScanResultList) {
      for (String argument : argumentList) {
        if (argument.trim().equals(mockScanResult.getMockName())) {
          findMockList.add(mockScanResult.getMockName());
          result = true;
        }
      }
    }

    return result;
  }

  private String[] findArguments(String methodCalledArgument) {
    String argument = methodCalledArgument.substring(methodCalledArgument.indexOf("(") + 1,
        methodCalledArgument.indexOf(")"));
    argument = argument.replace("this.", "");
    String[] argumentList = argument.split(",");
    return argumentList;
  }
}