package com.gdn.maven.plugin.service_impl;

import com.gdn.maven.plugin.model.ClassScanResult;
import com.gdn.maven.plugin.model.InjectedMockScanResult;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class WriterServiceImplTest {

  private WriterServiceImpl writerService = new WriterServiceImpl();

  @Test
  public void writeListToJSONTest() {
    InjectedMockScanResult injectedMockScanResult1 = new InjectedMockScanResult("ClassName1", "classDecalaration1");
    InjectedMockScanResult injectedMockScanResult2 = new InjectedMockScanResult("ClassName2", "classDecalaration2");

    ClassScanResult classScanResult1 = new ClassScanResult();
    classScanResult1.setPathClass("ClassPath1");
    classScanResult1.setInjectedMockScanResult(injectedMockScanResult1);

    ClassScanResult classScanResult2 = new ClassScanResult();
    classScanResult2.setPathClass("ClassPath2");
    classScanResult2.setInjectedMockScanResult(injectedMockScanResult2);

    List<ClassScanResult> classScanResultList = new ArrayList<>();
    classScanResultList.add(classScanResult1);
    classScanResultList.add(classScanResult1);

    writerService.writeListToJSON(classScanResultList);

    File file = new File("ut-scanner-result.json");
    assertTrue(file.exists());
  }
}
