package com.gdn.maven.plugin.service_api;

import com.gdn.maven.plugin.model.ClassScanResult;

import java.util.List;

public interface WriterService {

  void writeListToJSON(List<ClassScanResult> classScanResultList);

}
