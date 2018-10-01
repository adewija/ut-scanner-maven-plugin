package com.gdn.maven.plugin.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClassReference {
  String className;
  List<String> methodWithReturnTypeName = new ArrayList<>();
}
