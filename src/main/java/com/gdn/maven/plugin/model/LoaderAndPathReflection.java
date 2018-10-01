package com.gdn.maven.plugin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class LoaderAndPathReflection {

  private URLClassLoader loader;
  private List<String> listOfPath = new ArrayList<>();
  private String targetPath;
}
