package com.gdn.maven.plugin.service_impl.rules;

import com.gdn.maven.plugin.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProblematicAssertCheckerImplTest {

  private ProblematicAssertCheckerImpl problematicAssertChecker =
      ProblematicAssertCheckerImpl.getInstance();

  @Test
  public void check_UseAssertNotNullTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult = new MockScanResult();
    mockScanResult.setMockName("MockName");
    mockScanResult.setLineNumber(12);
    mockScanResult.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult);

    CalledMethodScanResult calledMethodScanResult1 =
        new CalledMethodScanResult("assertNotNull", "assertNotNull(name)", 89, "Error");

    List<CalledMethodScanResult> calledMethodScanResultList = new ArrayList<>();
    calledMethodScanResultList.add(calledMethodScanResult1);

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
    methodWithReturnTypeName.add("getName");
    methodWithReturnTypeName.add("getAge");

    ClassReference classReference = new ClassReference();
    classReference.setClassName("class");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    ClassScanResult result = problematicAssertChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=MockName, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName, calledMethodScanResultList=[CalledMethodScanResult(methodCalledName=assertNotNull, methodCalledArguments=assertNotNull(name), lineNumber=89, errorMessage=Error)])])",
        result.toString());

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .anyMatch(c -> c.equals(calledMethodScanResult1))));
  }

  @Test
  public void check_assertTrueAndFalseWithTrueAndFalseParameterTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult = new MockScanResult();
    mockScanResult.setMockName("MockName");
    mockScanResult.setLineNumber(12);
    mockScanResult.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult);

    CalledMethodScanResult calledMethodScanResult1 =
        new CalledMethodScanResult("assertTrue", "assertTrue(true)", 89, "Error");

    CalledMethodScanResult calledMethodScanResult2 =
        new CalledMethodScanResult("assertFalse", "assertFalse(false)", 90, "Error");

    List<CalledMethodScanResult> calledMethodScanResultList = new ArrayList<>();
    calledMethodScanResultList.add(calledMethodScanResult1);
    calledMethodScanResultList.add(calledMethodScanResult2);

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
    methodWithReturnTypeName.add("getName");
    methodWithReturnTypeName.add("getAge");

    ClassReference classReference = new ClassReference();
    classReference.setClassName("class");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    ClassScanResult result = problematicAssertChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=MockName, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName, calledMethodScanResultList=[CalledMethodScanResult(methodCalledName=assertTrue, methodCalledArguments=assertTrue(true), lineNumber=89, errorMessage=Don't use true as an argument for : assertTrue(true) at Line 89), CalledMethodScanResult(methodCalledName=assertFalse, methodCalledArguments=assertFalse(false), lineNumber=90, errorMessage=Don't use false as an argument for : assertFalse(false) at Line 90)])])",
        result.toString());

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .anyMatch(c -> c.equals(calledMethodScanResult1))));

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .anyMatch(c -> c.equals(calledMethodScanResult2))));
  }

  @Test
  public void check_assertTrueAndFalseWithVariableBooleanTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult = new MockScanResult();
    mockScanResult.setMockName("MockName");
    mockScanResult.setLineNumber(12);
    mockScanResult.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult);

    CalledMethodScanResult calledMethodScanResult3 =
        new CalledMethodScanResult("assertTrue", "assertTrue(result)", 90, "Error");

    CalledMethodScanResult calledMethodScanResult4 =
        new CalledMethodScanResult("assertFalse", "assertFalse(result)", 91, "Error");

    List<CalledMethodScanResult> calledMethodScanResultList = new ArrayList<>();
    calledMethodScanResultList.add(calledMethodScanResult3);
    calledMethodScanResultList.add(calledMethodScanResult4);

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
    methodWithReturnTypeName.add("getName");
    methodWithReturnTypeName.add("getAge");

    ClassReference classReference = new ClassReference();
    classReference.setClassName("class");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    ClassScanResult result = problematicAssertChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=MockName, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName, calledMethodScanResultList=[])])",
        result.toString());

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult3))));

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult4))));
  }

  @Test
  public void check_assertTrueAndFalseWithMessageAndParameterBooleanTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult = new MockScanResult();
    mockScanResult.setMockName("MockName");
    mockScanResult.setLineNumber(12);
    mockScanResult.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult);

    CalledMethodScanResult calledMethodScanResult5 =
        new CalledMethodScanResult("assertFalse", "assertTrue(\"Message\", false)", 95, "Error");

    CalledMethodScanResult calledMethodScanResult6 =
        new CalledMethodScanResult("assertTrue", "assertTrue(\"Message\", true)", 95, "Error");

    List<CalledMethodScanResult> calledMethodScanResultList = new ArrayList<>();
    calledMethodScanResultList.add(calledMethodScanResult5);
    calledMethodScanResultList.add(calledMethodScanResult6);

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
    methodWithReturnTypeName.add("getName");
    methodWithReturnTypeName.add("getAge");

    ClassReference classReference = new ClassReference();
    classReference.setClassName("class");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    ClassScanResult result = problematicAssertChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=MockName, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName, calledMethodScanResultList=[CalledMethodScanResult(methodCalledName=assertFalse, methodCalledArguments=assertTrue(\"Message\", false), lineNumber=95, errorMessage=Don't use false as an argument for : assertTrue(\"Message\",false) at Line 95), CalledMethodScanResult(methodCalledName=assertTrue, methodCalledArguments=assertTrue(\"Message\", true), lineNumber=95, errorMessage=Don't use true as an argument for : assertTrue(\"Message\",true) at Line 95)])])",
        result.toString());

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .anyMatch(c -> c.equals(calledMethodScanResult5))));

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .anyMatch(c -> c.equals(calledMethodScanResult6))));
  }

  @Test
  public void check_assertTrueAndFalseWithMessageAndVariableBooleanTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult = new MockScanResult();
    mockScanResult.setMockName("MockName");
    mockScanResult.setLineNumber(12);
    mockScanResult.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult);

    CalledMethodScanResult calledMethodScanResult5 =
        new CalledMethodScanResult("assertFalse", "assertTrue(\"Message\", result)", 95, "Error");

    CalledMethodScanResult calledMethodScanResult6 =
        new CalledMethodScanResult("assertTrue", "assertTrue(\"Message\", result)", 95, "Error");

    List<CalledMethodScanResult> calledMethodScanResultList = new ArrayList<>();
    calledMethodScanResultList.add(calledMethodScanResult5);
    calledMethodScanResultList.add(calledMethodScanResult6);

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
    methodWithReturnTypeName.add("getName");
    methodWithReturnTypeName.add("getAge");

    ClassReference classReference = new ClassReference();
    classReference.setClassName("class");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    ClassScanResult result = problematicAssertChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=MockName, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName, calledMethodScanResultList=[])])",
        result.toString());

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult5))));

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult6))));
  }
}