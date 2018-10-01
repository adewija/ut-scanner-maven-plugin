package com.gdn.maven.plugin.service_impl;

import com.gdn.maven.plugin.model.ClassScanResult;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class DirectoryServiceImplTest {
  private DirectoryServiceImpl directoryService = new DirectoryServiceImpl();

  @Test
  public void exploreTest(){
    List<ClassScanResult> result = new ArrayList<>();
    File newFile = new File(System.getProperty("user.dir"));
    directoryService.explore(newFile,result);
    assertFalse(result.isEmpty());
  }
}