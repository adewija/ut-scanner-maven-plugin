package com.gdn.maven.plugin.service_api;

import com.gdn.maven.plugin.model.ClassReference;
import com.gdn.maven.plugin.model.ClassScanResult;

import java.util.List;

public interface UnitTestRulesService {
  ClassScanResult check (ClassScanResult classScanResult, List<ClassReference> classReferenceList);
}
