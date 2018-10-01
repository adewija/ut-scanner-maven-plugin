package com.gdn.maven.plugin.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class AnnotationScanResult {
  String annotationName;
  int lineNumber;
  String supermethodName;
  List<CalledMethodScanResult> calledMethodScanResultList = new ArrayList<>();
}