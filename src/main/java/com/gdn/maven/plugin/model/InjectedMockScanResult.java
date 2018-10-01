package com.gdn.maven.plugin.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InjectedMockScanResult {
  String className;
  String classDeclaration;
}