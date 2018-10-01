package com.gdn.maven.plugin.service_impl.rules;

import com.gdn.maven.plugin.model.AnnotationScanResult;
import com.gdn.maven.plugin.model.CalledMethodScanResult;
import com.gdn.maven.plugin.model.ClassReference;
import com.gdn.maven.plugin.model.ClassScanResult;
import com.gdn.maven.plugin.model.MethodType;
import com.gdn.maven.plugin.service_api.UnitTestRulesService;

import java.util.List;
import java.util.stream.Collectors;

public class VoidVerifyCheckerImpl implements UnitTestRulesService {

  private static VoidVerifyCheckerImpl instance = null;

  private VoidVerifyCheckerImpl(){}

  public synchronized static VoidVerifyCheckerImpl getInstance() {
    if (instance == null) {
      instance = new VoidVerifyCheckerImpl();
    }
    return instance;
  }

  public ClassScanResult check(ClassScanResult classScanResult,
      List<ClassReference> classReferenceList) {
    /*
    find every annotations on one class
     */

    if (classScanResult.getInjectedMockScanResult() != null) {
      List<AnnotationScanResult> finalResults = classScanResult.getAnnotationScanResultList()
          .stream()
          .map(annotationScanResult -> checkVerifyNeedNoWhen(annotationScanResult))
          .collect(Collectors.toList());

      classScanResult.getAnnotationScanResultList().retainAll(finalResults);
    }
    return classScanResult;
  }

  private AnnotationScanResult checkVerifyNeedNoWhen(AnnotationScanResult annotationScanResult) {

    /*
    find every method with verify that still remains from the when-verify rules before, and
    remove it (assumptions: void method and verify needs no when)
     */

    List<CalledMethodScanResult> voidVerifyMethodResults =
        annotationScanResult.getCalledMethodScanResultList()
            .stream()
            .filter(calledMethod -> calledMethod.getMethodCalledName()
                .equals(MethodType.VERIFY.getType()))
            .filter(calledMethod -> !calledMethod.getMethodCalledArguments()
                .contains("any"))
            .collect(Collectors.toList());
    annotationScanResult.getCalledMethodScanResultList().removeAll(voidVerifyMethodResults);

    return annotationScanResult;
  }

}