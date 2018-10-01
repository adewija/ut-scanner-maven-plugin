package com.gdn.maven.plugin.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public final class StaticClassReferenceAndPathReflectionList {

  static List<ClassReference> classReferenceList = new ArrayList<>();
  static List<LoaderAndPathReflection> loaderAndPathReflectionList = new ArrayList<>();

  public static List<LoaderAndPathReflection> getLoaderAndPathReflectionList() {
    return loaderAndPathReflectionList;
  }

  public static void setLoaderAndPathReflectionList(List<LoaderAndPathReflection> loaderAndPathReflectionList) {
    StaticClassReferenceAndPathReflectionList.loaderAndPathReflectionList = loaderAndPathReflectionList;
  }

  private StaticClassReferenceAndPathReflectionList() {
  }

  public static List<ClassReference> getClassReferenceList() {
    return classReferenceList;
  }

  public static void setClassReferenceList(List<ClassReference> classReferenceList) {
    StaticClassReferenceAndPathReflectionList.classReferenceList = classReferenceList;
  }
}
