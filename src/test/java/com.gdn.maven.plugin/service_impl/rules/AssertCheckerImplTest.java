package com.gdn.maven.plugin.service_impl.rules;

import com.gdn.maven.plugin.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AssertCheckerImplTest {

  private AssertCheckerImpl assertChecker = AssertCheckerImpl.getInstance();

  @Test
  public void check_OneEqualsSignFromReferenceClassNoAssertTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult = new MockScanResult();
    mockScanResult.setMockName("MockName");
    mockScanResult.setLineNumber(12);
    mockScanResult.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult);

    CalledMethodScanResult calledMethodScanResult1 =
        new CalledMethodScanResult("getName", "String name = class.getName()", 87, "Error");

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

    ClassScanResult result = assertChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=MockName, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName, calledMethodScanResultList=[CalledMethodScanResult(methodCalledName=getName, methodCalledArguments=String name = class.getName(), lineNumber=87, errorMessage=Error)])])",
        result.toString());

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .anyMatch(c -> c.equals(calledMethodScanResult1))));
  }

  @Test
  public void check_OneEqualsSignNotFromReferenceClassNoAssertTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult = new MockScanResult();
    mockScanResult.setMockName("MockName");
    mockScanResult.setLineNumber(12);
    mockScanResult.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult);

    CalledMethodScanResult calledMethodScanResult1 =
        new CalledMethodScanResult("valueOf", "String name = String.valueOf()", 87, "Error");

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

    ClassScanResult result = assertChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=MockName, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName, calledMethodScanResultList=[])])",
        result.toString());

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult1))));

  }

  @Test
  public void check_NoEqualsSignOneAssertTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult = new MockScanResult();
    mockScanResult.setMockName("MockName");
    mockScanResult.setLineNumber(12);
    mockScanResult.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult);

    CalledMethodScanResult calledMethodScanResult3 =
        new CalledMethodScanResult("assertEquals", "assertEquals(\"budi\", name)", 89, "Error");

    List<CalledMethodScanResult> calledMethodScanResultList = new ArrayList<>();
    calledMethodScanResultList.add(calledMethodScanResult3);

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

    ClassScanResult result = assertChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=MockName, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName, calledMethodScanResultList=[CalledMethodScanResult(methodCalledName=assertEquals, methodCalledArguments=assertEquals(\"budi\", name), lineNumber=89, errorMessage=Error)])])",
        result.toString());

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .anyMatch(c -> c.equals(calledMethodScanResult3))));
  }

  @Test
  public void check_OneEqualsSignOneAssertTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult = new MockScanResult();
    mockScanResult.setMockName("MockName");
    mockScanResult.setLineNumber(12);
    mockScanResult.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult);

    CalledMethodScanResult calledMethodScanResult1 =
        new CalledMethodScanResult("getName", "String name = class.getName()", 87, "Error");

    CalledMethodScanResult calledMethodScanResult3 =
        new CalledMethodScanResult("assertEquals", "assertEquals(\"budi\", name)", 89, "Error");

    List<CalledMethodScanResult> calledMethodScanResultList = new ArrayList<>();
    calledMethodScanResultList.add(calledMethodScanResult1);
    calledMethodScanResultList.add(calledMethodScanResult3);

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

    ClassScanResult result = assertChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=MockName, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName, calledMethodScanResultList=[])])",
        result.toString());

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult1))));

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult3))));
  }

  @Test
  public void check_OneEqualsSignOneAssertUseAssertNotNullTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult = new MockScanResult();
    mockScanResult.setMockName("MockName");
    mockScanResult.setLineNumber(12);
    mockScanResult.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult);

    CalledMethodScanResult calledMethodScanResult1 =
        new CalledMethodScanResult("getName", "String name = class.getName()", 87, "Error");

    CalledMethodScanResult calledMethodScanResult4 =
        new CalledMethodScanResult("assertNotNull", "assertNotNull(name)", 90, "Error");

    List<CalledMethodScanResult> calledMethodScanResultList = new ArrayList<>();
    calledMethodScanResultList.add(calledMethodScanResult1);
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

    ClassScanResult result = assertChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=MockName, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName, calledMethodScanResultList=[CalledMethodScanResult(methodCalledName=assertNotNull, methodCalledArguments=assertNotNull(name), lineNumber=90, errorMessage=Error)])])",
        result.toString());

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult1))));

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .anyMatch(c -> c.equals(calledMethodScanResult4))));
  }

  @Test
  public void check_OneEqualsSignUseThisDeclarationOneAssertTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult = new MockScanResult();
    mockScanResult.setMockName("MockName");
    mockScanResult.setLineNumber(12);
    mockScanResult.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult);

    CalledMethodScanResult calledMethodScanResult2 =
        new CalledMethodScanResult("getName", "this.name = class.getName()", 87, "Error");

    CalledMethodScanResult calledMethodScanResult3 =
        new CalledMethodScanResult("assertEquals", "assertEquals(\"budi\", name)", 89, "Error");

    List<CalledMethodScanResult> calledMethodScanResultList = new ArrayList<>();
    calledMethodScanResultList.add(calledMethodScanResult2);
    calledMethodScanResultList.add(calledMethodScanResult3);

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

    ClassScanResult result = assertChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=MockName, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName, calledMethodScanResultList=[])])",
        result.toString());

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult2))));

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult3))));
  }
}