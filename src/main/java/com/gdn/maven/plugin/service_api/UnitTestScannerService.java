package com.gdn.maven.plugin.service_api;

import com.gdn.maven.plugin.model.ClassScanResult;

import java.io.File;

public interface UnitTestScannerService {

  ClassScanResult scan (File file);

  ClassScanResult applyRules(ClassScanResult classScanResult);
}
