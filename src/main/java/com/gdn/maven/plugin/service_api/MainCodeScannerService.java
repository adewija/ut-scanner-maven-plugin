package com.gdn.maven.plugin.service_api;

import com.gdn.maven.plugin.model.ClassReference;

import java.net.URLClassLoader;
import java.util.List;

public interface MainCodeScannerService {
  List<ClassReference> scanMainCode(String target, List<String> listOfPath, URLClassLoader loader);
}
