package com.gdn.maven.plugin.service_impl;

import com.gdn.maven.plugin.helper.MethodDeclareVisit;
import com.gdn.maven.plugin.helper.MockAnnotationVisit;
import com.gdn.maven.plugin.model.*;
import com.gdn.maven.plugin.service_impl.rules.AssertCheckerImpl;
import com.gdn.maven.plugin.service_impl.rules.ProblematicAssertCheckerImpl;
import com.gdn.maven.plugin.service_impl.rules.VerifyNoMoreInteractionCheckerImpl;
import com.gdn.maven.plugin.service_impl.rules.VoidVerifyCheckerImpl;
import com.gdn.maven.plugin.service_impl.rules.WhenAndVerifyCheckerImpl;
import com.gdn.maven.plugin.service_impl.rules.WhenFromSetUpAndVerifyFromTestCheckerImpl;
import com.gdn.maven.plugin.service_api.UnitTestScannerService;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class UnitTestScannerServiceImpl implements UnitTestScannerService {

  private static final Logger LOG = LoggerFactory.getLogger(UnitTestScannerService.class);

  @Override
  public ClassScanResult scan(File file) {

    ClassScanResult classScanResult = new ClassScanResult();
    CompilationUnit compilationUnit;

    try {
      compilationUnit = JavaParser.parse(file);
      compilationUnit.accept(new MockAnnotationVisit(), classScanResult);
      compilationUnit.accept(new MethodDeclareVisit(),classScanResult);
      classScanResult.setPathClass(file.getAbsolutePath());

    } catch (FileNotFoundException e) {
      LOG.error("Failed to parse as file {} not found", file.toPath(), e);
    }
    return applyRules(classScanResult);
  }

  @Override
  public ClassScanResult applyRules(ClassScanResult classScanResult) {
    ClassScanResult scanResult;

    scanResult = VerifyNoMoreInteractionCheckerImpl.getInstance().check(classScanResult,
        StaticClassReferenceAndPathReflectionList.getClassReferenceList());

    scanResult = WhenAndVerifyCheckerImpl.getInstance().check(scanResult, StaticClassReferenceAndPathReflectionList
        .getClassReferenceList());

    scanResult = AssertCheckerImpl.getInstance().check(scanResult, StaticClassReferenceAndPathReflectionList
        .getClassReferenceList());

    scanResult = ProblematicAssertCheckerImpl.getInstance().check(scanResult, StaticClassReferenceAndPathReflectionList
        .getClassReferenceList());

    scanResult = WhenFromSetUpAndVerifyFromTestCheckerImpl.getInstance().check(scanResult, StaticClassReferenceAndPathReflectionList
        .getClassReferenceList());

    scanResult = VoidVerifyCheckerImpl.getInstance().check(scanResult, StaticClassReferenceAndPathReflectionList
        .getClassReferenceList());

    List<AnnotationScanResult> deleteList = scanResult.getAnnotationScanResultList()
        .stream()
        .filter(annotationScanResult -> annotationScanResult.getCalledMethodScanResultList()
            .isEmpty())
        .collect(Collectors.toList());

    scanResult.getAnnotationScanResultList().removeAll(deleteList);

    if (scanResult.getAnnotationScanResultList().isEmpty() && scanResult.getMockScanResultList()
        .isEmpty()) {
      return null;
    } else {
      if (scanResult.getMockScanResultList().isEmpty()) {
        scanResult.setMockScanResultList(null);
      }
      if (scanResult.getAnnotationScanResultList().isEmpty()) {
        scanResult.setAnnotationScanResultList(null);
      }
      return scanResult;
    }
  }
}