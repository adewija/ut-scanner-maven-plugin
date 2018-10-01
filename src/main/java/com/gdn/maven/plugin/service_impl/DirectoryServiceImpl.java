package com.gdn.maven.plugin.service_impl;

import com.gdn.maven.plugin.helper.DirectoryExplorer;
import com.gdn.maven.plugin.model.ClassScanResult;
import com.gdn.maven.plugin.service_api.DirectoryService;
import com.gdn.maven.plugin.service_api.UnitTestScannerService;

import java.io.File;
import java.util.List;

public class DirectoryServiceImpl implements DirectoryService {
  private UnitTestScannerService unitTestScannerService = new UnitTestScannerServiceImpl();

  public void explore(File projectDir, List<ClassScanResult> classScanResultList) {

    List<File> listOfTest = new DirectoryExplorer().findTestFolder(projectDir);

    for (File newFile : listOfTest) {
      classScanResultList.add(unitTestScannerService.scan(newFile));
    }

  }
}