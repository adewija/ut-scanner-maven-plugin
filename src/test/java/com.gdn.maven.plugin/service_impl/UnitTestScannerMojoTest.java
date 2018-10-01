package com.gdn.maven.plugin.service_impl;

import com.gdn.maven.plugin.UnitTestScannerMojo;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UnitTestScannerMojoTest {

  @InjectMocks
  private UnitTestScannerMojo unitTestScannerMojo;

  @Mock
  MavenProject project = new MavenProject();

  @Mock
  List<MavenProject> projects = new ArrayList<>();

  @Before
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void execute_lastProjectTest() {
    try {
      List<String> classPathElements = new ArrayList<>();
      classPathElements.add(System.getProperty("user.dir")+"\\src\\test\\resource\\target\\classes");

      when(project.getCompileClasspathElements()).thenReturn(classPathElements);
      when(projects.size()).thenReturn(1);
      when(projects.get(0)).thenReturn(project);

      unitTestScannerMojo.execute();

      verify(project).getCompileClasspathElements();
      verify(projects).size();
      verify(projects).get(0);
    } catch (MojoExecutionException | DependencyResolutionRequiredException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void execute_throwDependencyResolutionRequiredException() {
    try {

      List<String> classPathElements = new ArrayList<>();
      classPathElements.add("path");

      when(project.getCompileClasspathElements()).thenThrow(DependencyResolutionRequiredException.class);
      when(project.getFile()).thenReturn(new File(classPathElements.get(0)));
      when(projects.size()).thenReturn(2);
      when(projects.get(1)).thenReturn(null);
      unitTestScannerMojo.execute();

      verify(project).getCompileClasspathElements();
      verify(project).getFile();
      verify(projects).size();
      verify(projects).get(1);
    } catch (MojoExecutionException | DependencyResolutionRequiredException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void execute_notLastProjectTest() {
    try {
      List<String> classPathElements = new ArrayList<>();
      classPathElements.add(System.getProperty("user.dir")+"\\src\\test\\resource\\target\\classes");

      when(project.getCompileClasspathElements()).thenReturn(classPathElements);
      when(projects.size()).thenReturn(2);
      when(projects.get(1)).thenReturn(null);

      unitTestScannerMojo.execute();

      verify(project).getCompileClasspathElements();
      verify(projects).size();
      verify(projects).get(1);
    } catch (MojoExecutionException | DependencyResolutionRequiredException e) {
      e.printStackTrace();
    }
  }

  @After
  public void tearDown(){
    verifyNoMoreInteractions(project);
    verifyNoMoreInteractions(projects);
  }
}