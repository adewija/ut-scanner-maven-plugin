package com.gdn.maven.plugin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MockScanResult {
  String mockName;
  int lineNumber;
  String errorMessage;
}
