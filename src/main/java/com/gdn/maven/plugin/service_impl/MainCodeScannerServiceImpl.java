package com.gdn.maven.plugin.service_impl;

import com.gdn.maven.plugin.model.ClassReference;
import com.gdn.maven.plugin.service_api.MainCodeScannerService;

import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class MainCodeScannerServiceImpl implements MainCodeScannerService {

  public List<ClassReference> scanMainCode(String targetPath, List<String> listOfPath, URLClassLoader loader) {
    List<ClassReference> classReferenceList = new ArrayList<>();
    Class clazz = null;

    for (String path : listOfPath) {
      try {
        clazz = Class.forName(getCanonicalName(targetPath, path), false, loader);
      ClassReference classReference = new ClassReference();
      classReference.setClassName(clazz.getSimpleName());
      ArrayList delcaredMethodList = new ArrayList();
      for (Method delcaredMethod : clazz.getDeclaredMethods()) {
        if (!delcaredMethod.getName().contains("$") && !delcaredMethod.getReturnType().toString().contains("void")
            && delcaredMethod.getModifiers() == 1) {
          delcaredMethodList.add(delcaredMethod.getName());
        }
      }
      classReference.setMethodWithReturnTypeName(delcaredMethodList);
      classReferenceList.add(classReference);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    return classReferenceList;
  }

  private String getCanonicalName(String targetPath, String path) {
    String canonicalName = path.substring(targetPath.length() + 1);
    canonicalName = canonicalName.replace(".class", "");
    canonicalName = canonicalName.replace("\\", ".");
    canonicalName = canonicalName.trim();
    return canonicalName;
  }
}


