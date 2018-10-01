package com.gdn.maven.plugin;

import com.gdn.maven.plugin.helper.DirectoryExplorer;
import com.gdn.maven.plugin.model.ClassScanResult;
import com.gdn.maven.plugin.model.LoaderAndPathReflection;
import com.gdn.maven.plugin.model.StaticClassReferenceAndPathReflectionList;
import com.gdn.maven.plugin.service_api.DirectoryService;
import com.gdn.maven.plugin.service_api.MainCodeScannerService;
import com.gdn.maven.plugin.service_api.WriterService;

import com.gdn.maven.plugin.service_impl.DirectoryServiceImpl;
import com.gdn.maven.plugin.service_impl.MainCodeScannerServiceImpl;
import com.gdn.maven.plugin.service_impl.WriterServiceImpl;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.stream.Collectors;

@Mojo(name = "scan", requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class UnitTestScannerMojo extends AbstractMojo {
  @Parameter(property = "directory")
  private String directory;

  @Parameter(defaultValue = "${project}", readonly = true)
  private MavenProject project;

  @Parameter(defaultValue = "${reactorProjects}", readonly = true)
  private List<MavenProject> projects;

  private static final Logger LOG = LoggerFactory.getLogger(UnitTestScannerMojo.class);

  DirectoryService directoryService = new DirectoryServiceImpl();

  WriterService writerService = new WriterServiceImpl();

  public void execute() throws MojoExecutionException {


    List<String> listOfPath = new ArrayList<>();

    String targetPath = null;

    List<String> classpathElements = null;

    List<URL> projectClasspathList = new ArrayList<URL>();


    try {
      classpathElements = project.getCompileClasspathElements();

      for (String element : classpathElements) {
        try {
          projectClasspathList.add(new File(element).toURI().toURL());
          if (element.contains("\\target\\")) {
            targetPath = element;
            listOfPath.addAll(new DirectoryExplorer().findClassPath(new File(element)));
          }
        } catch (MalformedURLException e) {
          throw new MojoExecutionException(element + " is an invalid classpath element", e);
        }
      }

    } catch (DependencyResolutionRequiredException e) {
      LOG.error("Failed to obtain class elements for reflections on ",
          project.getFile().getPath(),
          e);
    }

    LoaderAndPathReflection loaderAndPathReflection = new LoaderAndPathReflection();

    loaderAndPathReflection.setLoader(new URLClassLoader(projectClasspathList.toArray(new URL[0])));

    loaderAndPathReflection.setListOfPath(listOfPath);

    loaderAndPathReflection.setTargetPath(targetPath);

    StaticClassReferenceAndPathReflectionList.getLoaderAndPathReflectionList()
        .add(loaderAndPathReflection);

    if (!isLastProjectInReactor()) {
      return;
    }

    List<ClassScanResult> classScanResultList = new ArrayList<>();

    File projectDirectory;

    if (directory == null) {
      projectDirectory = new File(System.getProperty("user.dir"));
    } else {
      projectDirectory = new File(directory);
    }

    MainCodeScannerService mainCodeScannerService = new MainCodeScannerServiceImpl();

    StaticClassReferenceAndPathReflectionList.getLoaderAndPathReflectionList()
        .forEach(loaderAndPathReflections -> StaticClassReferenceAndPathReflectionList.getClassReferenceList()
            .addAll(mainCodeScannerService.scanMainCode(loaderAndPathReflections.getTargetPath(),
                loaderAndPathReflections.getListOfPath(),
                loaderAndPathReflections.getLoader())));

    directoryService.explore(projectDirectory, classScanResultList);

    classScanResultList =
        classScanResultList.stream().filter(Objects::nonNull).collect(Collectors.toList());

    writerService.writeListToJSON(classScanResultList);
  }

  private boolean isLastProjectInReactor() {
    final int size = projects.size();
    MavenProject lastProject = (MavenProject) projects.get(size - 1);
    if (lastProject == project) {
      return true;
    } else {
      return false;
    }
  }

}