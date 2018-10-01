package com.gdn.maven.plugin.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectoryExplorer {

  List<File> foundFolder = new ArrayList<>();

  List<String> foundPath = new ArrayList<>();

  public List<File> findTestFolder(File file) {
    if (file.isDirectory()) {
      Arrays.stream(file.listFiles()).forEach(this::findTestFolder);
    } else {
      if ((file.getPath().endsWith(".java") && file.getPath().contains("\\test")) || file.getPath()
          .endsWith("Test.java")) {
        foundFolder.add(file);
      }
    }
    return foundFolder;
  }

  public List<String> findClassPath(File file) {
    if (file.isDirectory()) {
      Arrays.stream(file.listFiles()).forEach(this::findClassPath);
    } else {
      if (file.getPath().endsWith(".class") && !file.getPath().contains("$")) {
        foundPath.add(file.getAbsolutePath());
      }
    }
    return foundPath;
  }
}