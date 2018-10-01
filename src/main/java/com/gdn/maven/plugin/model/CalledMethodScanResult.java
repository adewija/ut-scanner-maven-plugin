package com.gdn.maven.plugin.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalledMethodScanResult {
  String methodCalledName;
  String methodCalledArguments;
  int lineNumber;
  String errorMessage;
}