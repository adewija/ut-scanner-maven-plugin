package com.gdn.maven.plugin.service_api;

import com.gdn.maven.plugin.model.ClassScanResult;

import java.io.File;
import java.util.List;

public interface DirectoryService {

  void explore(File projectDir, List<ClassScanResult> classScanResultList);

}
