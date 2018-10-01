package com.gdn.maven.plugin.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ClassScanResult {
  String pathClass;
  InjectedMockScanResult injectedMockScanResult;
  List<MockScanResult> mockScanResultList = new ArrayList<>();
  List<AnnotationScanResult> annotationScanResultList = new ArrayList<>();
}