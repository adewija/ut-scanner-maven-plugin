package com.gdn.maven.plugin.service_impl.rules;

import com.gdn.maven.plugin.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VerifyNoMoreInteractionCheckerImplTest {

  private VerifyNoMoreInteractionCheckerImpl verifyNoMoreInteractionChecker =
      VerifyNoMoreInteractionCheckerImpl.getInstance();

  @Test
  public void checkTest_OneMockNoVerifyNoMoreInteractionsTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult1 = new MockScanResult();
    mockScanResult1.setMockName("mockName1");
    mockScanResult1.setLineNumber(12);
    mockScanResult1.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult1);

    CalledMethodScanResult calledMethodScanResult1 =
        new CalledMethodScanResult("assertEquals", "assertEquals(1,1)", 89, "Error");

    List<CalledMethodScanResult> calledMethodScanResultList1 = new ArrayList<>();
    calledMethodScanResultList1.add(calledMethodScanResult1);

    AnnotationScanResult annotationScanResult1 = new AnnotationScanResult();
    annotationScanResult1.setAnnotationName("Test");
    annotationScanResult1.setLineNumber(45);
    annotationScanResult1.setSupermethodName("superMethodName1");
    annotationScanResult1.setCalledMethodScanResultList(calledMethodScanResultList1);

    List<AnnotationScanResult> annotationScanResultList = new ArrayList<>();
    annotationScanResultList.add(annotationScanResult1);

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

    ClassScanResult result =
        verifyNoMoreInteractionChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=mockName1, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName1, calledMethodScanResultList=[CalledMethodScanResult(methodCalledName=assertEquals, methodCalledArguments=assertEquals(1,1), lineNumber=89, errorMessage=Error)])])",
        result.toString());

    assertTrue(result.getMockScanResultList().stream().anyMatch(e -> e.equals(mockScanResult1)));

  }

  @Test
  public void checkTest_NoMockOneVerifyNoMoreInteractionsTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");


    CalledMethodScanResult calledMethodScanResult2 = new CalledMethodScanResult(
        "verifyNoMoreInteractions",
        "verifyNoMoreInteractions(mockName2)",
        89,
        "Error");

    List<CalledMethodScanResult> calledMethodScanResultList2 = new ArrayList<>();
    calledMethodScanResultList2.add(calledMethodScanResult2);

    AnnotationScanResult annotationScanResult2 = new AnnotationScanResult();
    annotationScanResult2.setAnnotationName("After");
    annotationScanResult2.setLineNumber(100);
    annotationScanResult2.setSupermethodName("superMethodName2");
    annotationScanResult2.setCalledMethodScanResultList(calledMethodScanResultList2);

    List<AnnotationScanResult> annotationScanResultList = new ArrayList<>();
    annotationScanResultList.add(annotationScanResult2);

    ClassScanResult classScanResult = new ClassScanResult();
    classScanResult.setPathClass("ClassPath");
    classScanResult.setInjectedMockScanResult(injectedMockScanResult);
    classScanResult.setPathClass("D:\\Users\\abc.java");
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

    ClassScanResult result =
        verifyNoMoreInteractionChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[], annotationScanResultList=[AnnotationScanResult(annotationName=After, lineNumber=100, supermethodName=superMethodName2, calledMethodScanResultList=[CalledMethodScanResult(methodCalledName=verifyNoMoreInteractions, methodCalledArguments=verifyNoMoreInteractions(mockName2), lineNumber=89, errorMessage=Error)])])",
        result.toString());

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .anyMatch(c -> c.equals(calledMethodScanResult2))));
  }

  @Test
  public void checkTest_OneMockOneVerifyNoMoreInteractionsSingleParametersTest() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult1 = new MockScanResult();
    mockScanResult1.setMockName("mockName1");
    mockScanResult1.setLineNumber(12);
    mockScanResult1.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult1);

    CalledMethodScanResult calledMethodScanResult2 = new CalledMethodScanResult(
        "verifyNoMoreInteractions",
        "verifyNoMoreInteractions(mockName1)",
        89,
        "Error");

    List<CalledMethodScanResult> calledMethodScanResultList2 = new ArrayList<>();
    calledMethodScanResultList2.add(calledMethodScanResult2);

    AnnotationScanResult annotationScanResult2 = new AnnotationScanResult();
    annotationScanResult2.setAnnotationName("After");
    annotationScanResult2.setLineNumber(100);
    annotationScanResult2.setSupermethodName("superMethodName2");
    annotationScanResult2.setCalledMethodScanResultList(calledMethodScanResultList2);

    List<AnnotationScanResult> annotationScanResultList = new ArrayList<>();
    annotationScanResultList.add(annotationScanResult2);

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

    ClassScanResult result =
        verifyNoMoreInteractionChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[], annotationScanResultList=[AnnotationScanResult(annotationName=After, lineNumber=100, supermethodName=superMethodName2, calledMethodScanResultList=[])])",
        result.toString());

    assertTrue(result.getMockScanResultList().stream().noneMatch(e -> e.equals(mockScanResult1)));

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult2))));
  }

  @Test
  public void checkTest_OneMockDoubleVerifyNoMoreInteractions() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult1 = new MockScanResult();
    mockScanResult1.setMockName("mockName1");
    mockScanResult1.setLineNumber(12);
    mockScanResult1.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult1);

    CalledMethodScanResult calledMethodScanResult1 = new CalledMethodScanResult(
        "verifyNoMoreInteractions",
        "verifyNoMoreInteractions(mockName1)",
        89,
        "Error");

    List<CalledMethodScanResult> calledMethodScanResultList1 = new ArrayList<>();
    calledMethodScanResultList1.add(calledMethodScanResult1);

    AnnotationScanResult annotationScanResult1 = new AnnotationScanResult();
    annotationScanResult1.setAnnotationName("Test");
    annotationScanResult1.setLineNumber(45);
    annotationScanResult1.setSupermethodName("superMethodName1");
    annotationScanResult1.setCalledMethodScanResultList(calledMethodScanResultList1);

    CalledMethodScanResult calledMethodScanResult2 = new CalledMethodScanResult(
        "verifyNoMoreInteractions",
        "verifyNoMoreInteractions(mockName1)",
        89,
        "Error");

    List<CalledMethodScanResult> calledMethodScanResultList2 = new ArrayList<>();
    calledMethodScanResultList2.add(calledMethodScanResult2);

    AnnotationScanResult annotationScanResult2 = new AnnotationScanResult();
    annotationScanResult2.setAnnotationName("After");
    annotationScanResult2.setLineNumber(100);
    annotationScanResult2.setSupermethodName("superMethodName2");
    annotationScanResult2.setCalledMethodScanResultList(calledMethodScanResultList2);

    List<AnnotationScanResult> annotationScanResultList = new ArrayList<>();
    annotationScanResultList.add(annotationScanResult1);
    annotationScanResultList.add(annotationScanResult2);

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

    ClassScanResult result =
        verifyNoMoreInteractionChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName1, calledMethodScanResultList=[]), AnnotationScanResult(annotationName=After, lineNumber=100, supermethodName=superMethodName2, calledMethodScanResultList=[])])",
        result.toString());

    assertTrue(result.getMockScanResultList().stream().noneMatch(e -> e.equals(mockScanResult1)));

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult2))));
  }

  @Test
  public void checkTest_OneMockOneVerifyNoMoreInteractionsDoubleParameters() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDecalaration");

    MockScanResult mockScanResult1 = new MockScanResult();
    mockScanResult1.setMockName("mockName1");
    mockScanResult1.setLineNumber(12);
    mockScanResult1.setErrorMessage("ErrorMessage");

    MockScanResult mockScanResult2 = new MockScanResult();
    mockScanResult2.setMockName("mockName2");
    mockScanResult2.setLineNumber(15);
    mockScanResult2.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult1);
    mockScanResultList.add(mockScanResult2);

    CalledMethodScanResult calledMethodScanResult2 = new CalledMethodScanResult(
        "verifyNoMoreInteractions",
        "verifyNoMoreInteractions(mockName1, mockName2)",
        89,
        "Error");

    List<CalledMethodScanResult> calledMethodScanResultList2 = new ArrayList<>();
    calledMethodScanResultList2.add(calledMethodScanResult2);

    AnnotationScanResult annotationScanResult2 = new AnnotationScanResult();
    annotationScanResult2.setAnnotationName("After");
    annotationScanResult2.setLineNumber(100);
    annotationScanResult2.setSupermethodName("superMethodName2");
    annotationScanResult2.setCalledMethodScanResultList(calledMethodScanResultList2);

    List<AnnotationScanResult> annotationScanResultList = new ArrayList<>();
    annotationScanResultList.add(annotationScanResult2);

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

    ClassScanResult result =
        verifyNoMoreInteractionChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[], annotationScanResultList=[AnnotationScanResult(annotationName=After, lineNumber=100, supermethodName=superMethodName2, calledMethodScanResultList=[])])",
        result.toString());

    assertTrue(result.getMockScanResultList().stream().noneMatch(e -> e.equals(mockScanResult1)));

    assertTrue(result.getMockScanResultList().stream().noneMatch(e -> e.equals(mockScanResult2)));

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult2))));

  }
}