package com.gdn.maven.plugin.service_impl;

import com.gdn.maven.plugin.model.*;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class MainCodeScannerServiceImplTest {

  private MainCodeScannerServiceImpl mainCodeScannerService = new MainCodeScannerServiceImpl();

  @Test
  public void scanMainCode_success() {
    String targetPath =
        "File:\\" + System.getProperty("user.dir") + "\\src\\test\\resource\\target\\classes";
    String path = "File:\\" + System.getProperty("user.dir")
        + "\\src\\test\\resource\\target\\classes\\com\\gdn\\maven\\plugin\\model\\AnnotationScanResult.class";

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add(path);


    List<URL> projectClasspathList = new ArrayList<>();
    try {
      projectClasspathList.add(new File(targetPath).toURI().toURL());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    LoaderAndPathReflection loaderAndPathReflection = new LoaderAndPathReflection();

    URLClassLoader urlClassLoader = new URLClassLoader(projectClasspathList.toArray(new URL[0]));

    loaderAndPathReflection.setLoader(urlClassLoader);

    List<ClassReference> result = mainCodeScannerService.scanMainCode(targetPath,
        listOfPath,
        loaderAndPathReflection.getLoader());

    assertTrue(result
        .stream()
        .anyMatch(e -> e.getMethodWithReturnTypeName()
            .stream()
            .anyMatch(c -> c.equals("getCalledMethodScanResultList"))));

    assertTrue(result.stream()
        .anyMatch(classReference -> classReference.getClassName().equals("AnnotationScanResult")));

  }

  @Test
  public void scanMainCode_classNotFound() {
    String targetPath =
        "File:\\" + System.getProperty("user.dir") + "\\src\\test\\resource\\target\\classes";
    String path = "File:\\" + System.getProperty("user.dir")
        + "\\src\\test\\resource\\target\\classes\\com\\gdn\\maven\\plugin\\model\\Path.class";

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add(path);


    List<URL> projectClasspathList = new ArrayList<>();
    try {
      projectClasspathList.add(new File(targetPath).toURI().toURL());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    LoaderAndPathReflection loaderAndPathReflection = new LoaderAndPathReflection();

    URLClassLoader urlClassLoader = new URLClassLoader(projectClasspathList.toArray(new URL[0]));

    loaderAndPathReflection.setLoader(urlClassLoader);

    List<ClassReference> result = mainCodeScannerService.scanMainCode(targetPath,
        listOfPath,
        loaderAndPathReflection.getLoader());

    assertTrue(result.isEmpty());
  }

}


