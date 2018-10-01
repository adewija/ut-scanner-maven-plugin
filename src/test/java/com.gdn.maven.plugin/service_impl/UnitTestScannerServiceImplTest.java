package com.gdn.maven.plugin.service_impl;

import com.gdn.maven.plugin.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

public class UnitTestScannerServiceImplTest {

  @InjectMocks
  private UnitTestScannerServiceImpl unitTestScannerService;

  @Before
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void scan() {
    File file = new File(System.getProperty("user.dir"));

    ClassScanResult result = unitTestScannerService.scan(file);

    assertEquals(null, result);
  }

  @Test
  public void applyRules_WithMockNoEmptyAndAnnotNoEmptyTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult = new MockScanResult();
    mockScanResult.setMockName("MockName");
    mockScanResult.setLineNumber(12);
    mockScanResult.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult);

    CalledMethodScanResult calledMethodScanResult =
        new CalledMethodScanResult("methodCalledName", "argument", 87, "Error");
    calledMethodScanResult.setMethodCalledName("methodCalledName");

    List<CalledMethodScanResult> calledMethodScanResultList = new ArrayList<>();
    calledMethodScanResultList.add(calledMethodScanResult);

    AnnotationScanResult annotationScanResult = new AnnotationScanResult();
    annotationScanResult.setAnnotationName("Test");
    annotationScanResult.setLineNumber(45);
    annotationScanResult.setSupermethodName("superMethodName");
    annotationScanResult.setCalledMethodScanResultList(calledMethodScanResultList);

    List<AnnotationScanResult> annotationScanResultList = new ArrayList<>();
    annotationScanResultList.add(annotationScanResult);

    ClassScanResult classScanResult = new ClassScanResult();
    classScanResult.setPathClass("ClassPath");
    classScanResult.setInjectedMockScanResult(injectedMockScanResult);
    classScanResult.setPathClass("D:\\Users\\abc.java");
    classScanResult.setMockScanResultList(mockScanResultList);
    classScanResult.setAnnotationScanResultList(annotationScanResultList);

    List<String> methodWithReturnTypeName = new ArrayList<>();
    methodWithReturnTypeName.add("methodReturnType");

    ClassReference classReference = new ClassReference();
    classReference.setClassName("Class");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    ClassScanResult result = unitTestScannerService.applyRules(classScanResult);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=MockName, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName, calledMethodScanResultList=[CalledMethodScanResult(methodCalledName=methodCalledName, methodCalledArguments=argument, lineNumber=87, errorMessage=Error)])])",
        result.toString());

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .anyMatch(c -> c.equals(calledMethodScanResult))));
  }

  @Test
  public void applyRules_WithMockEmptyAndAnnotEmptyTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");


    CalledMethodScanResult calledMethodScanResult =
        new CalledMethodScanResult("methodCalledName", "argument", 87, "Error");
    calledMethodScanResult.setMethodCalledName("methodCalledName");

    List<CalledMethodScanResult> calledMethodScanResultList = new ArrayList<>();
    calledMethodScanResultList.add(calledMethodScanResult);


    List<AnnotationScanResult> annotationScanResultList = new ArrayList<>();

    ClassScanResult classScanResult = new ClassScanResult();
    classScanResult.setPathClass("ClassPath");
    classScanResult.setInjectedMockScanResult(injectedMockScanResult);
    classScanResult.setPathClass("D:\\Users\\abc.java");
    classScanResult.setAnnotationScanResultList(annotationScanResultList);

    List<String> methodWithReturnTypeName = new ArrayList<>();
    methodWithReturnTypeName.add("methodReturnType");

    ClassReference classReference = new ClassReference();
    classReference.setClassName("Class");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    ClassScanResult result = unitTestScannerService.applyRules(classScanResult);

    assertNull(result);
  }

  @Test
  public void applyRules_WithMockEmptyTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    CalledMethodScanResult calledMethodScanResult =
        new CalledMethodScanResult("methodCalledName", "argument", 87, "Error");
    calledMethodScanResult.setMethodCalledName("methodCalledName");

    List<CalledMethodScanResult> calledMethodScanResultList = new ArrayList<>();
    calledMethodScanResultList.add(calledMethodScanResult);

    AnnotationScanResult annotationScanResult = new AnnotationScanResult();
    annotationScanResult.setAnnotationName("Test");
    annotationScanResult.setLineNumber(45);
    annotationScanResult.setSupermethodName("superMethodName");
    annotationScanResult.setCalledMethodScanResultList(calledMethodScanResultList);

    List<AnnotationScanResult> annotationScanResultList = new ArrayList<>();
    annotationScanResultList.add(annotationScanResult);

    ClassScanResult classScanResult = new ClassScanResult();
    classScanResult.setPathClass("ClassPath");
    classScanResult.setInjectedMockScanResult(injectedMockScanResult);
    classScanResult.setPathClass("D:\\Users\\abc.java");
    classScanResult.setAnnotationScanResultList(annotationScanResultList);

    List<String> methodWithReturnTypeName = new ArrayList<>();
    methodWithReturnTypeName.add("methodReturnType");

    ClassReference classReference = new ClassReference();
    classReference.setClassName("Class");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    ClassScanResult result = unitTestScannerService.applyRules(classScanResult);

    assertEquals("ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=null, annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName, calledMethodScanResultList=[CalledMethodScanResult(methodCalledName=methodCalledName, methodCalledArguments=argument, lineNumber=87, errorMessage=Error)])])", result.toString());

    assertNull(result.getMockScanResultList());
  }

  @Test
  public void applyRules_WithAnnotEmptyTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult = new MockScanResult();
    mockScanResult.setMockName("MockName");
    mockScanResult.setLineNumber(12);
    mockScanResult.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult);

    CalledMethodScanResult calledMethodScanResult =
        new CalledMethodScanResult("methodCalledName", "argument", 87, "Error");
    calledMethodScanResult.setMethodCalledName("methodCalledName");

    List<CalledMethodScanResult> calledMethodScanResultList = new ArrayList<>();
    calledMethodScanResultList.add(calledMethodScanResult);

    ClassScanResult classScanResult = new ClassScanResult();
    classScanResult.setPathClass("ClassPath");
    classScanResult.setInjectedMockScanResult(injectedMockScanResult);
    classScanResult.setPathClass("D:\\Users\\abc.java");
    classScanResult.setMockScanResultList(mockScanResultList);

    List<String> methodWithReturnTypeName = new ArrayList<>();
    methodWithReturnTypeName.add("methodReturnType");

    ClassReference classReference = new ClassReference();
    classReference.setClassName("Class");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    ClassScanResult result = unitTestScannerService.applyRules(classScanResult);

    assertEquals("ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=MockName, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=null)", result.toString());

    assertNull(result.getAnnotationScanResultList());

  }

  @After
  public void tearDown() {
  }
}