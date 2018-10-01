package com.gdn.maven.plugin.service_impl.rules;

import com.gdn.maven.plugin.model.AnnotationScanResult;
import com.gdn.maven.plugin.model.CalledMethodScanResult;
import com.gdn.maven.plugin.model.ClassReference;
import com.gdn.maven.plugin.model.ClassScanResult;
import com.gdn.maven.plugin.model.InjectedMockScanResult;
import com.gdn.maven.plugin.model.MockScanResult;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class WhenAndVerifyCheckerImplTest {
  private WhenAndVerifyCheckerImpl whenAndVerifyChecker = WhenAndVerifyCheckerImpl.getInstance();

  @Test
  public void check_WhenWithVerify() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDeclaration");

    MockScanResult mockScanResult1 = new MockScanResult();
    mockScanResult1.setMockName("mockName1");
    mockScanResult1.setLineNumber(12);
    mockScanResult1.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult1);

    CalledMethodScanResult calledMethodScanResult1 = new CalledMethodScanResult("when",
        "when(this.classDeclaration.getName(argument1,argument2)).thenReturn(this.variable)",
        57,
        "Error");

    CalledMethodScanResult calledMethodScanResult2 = new CalledMethodScanResult("verify",
        "verify(this.classDeclaration).getName(argument1,argument2)",
        89,
        "Error");

    List<CalledMethodScanResult> calledMethodScanResultList1 = new ArrayList<>();
    calledMethodScanResultList1.add(calledMethodScanResult1);
    calledMethodScanResultList1.add(calledMethodScanResult2);

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
    classReference.setClassName("ClassName");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    ClassScanResult result = whenAndVerifyChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDeclaration), mockScanResultList=[MockScanResult(mockName=mockName1, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName1, calledMethodScanResultList=[])])",
        result.toString());
    whenAndVerifyChecker.check(classScanResult, classReferenceList);

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult1))));
    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult2))));

  }

  @Test
  public void check_WhenWithVerifyTimes() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDeclaration");

    MockScanResult mockScanResult1 = new MockScanResult();
    mockScanResult1.setMockName("mockName1");
    mockScanResult1.setLineNumber(12);
    mockScanResult1.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult1);

    CalledMethodScanResult calledMethodScanResult1 = new CalledMethodScanResult("when",
        "when(this.classDeclaration.getName(argument1,argument2)).thenReturn(this.variable)",
        57,
        "Error");

    CalledMethodScanResult calledMethodScanResult2 = new CalledMethodScanResult("verify",
        "verify(this.classDeclaration, times(2)).getName(argument1,argument2)",
        89,
        "Error");

    List<CalledMethodScanResult> calledMethodScanResultList1 = new ArrayList<>();
    calledMethodScanResultList1.add(calledMethodScanResult1);
    calledMethodScanResultList1.add(calledMethodScanResult2);

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
    classReference.setClassName("ClassName");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    ClassScanResult result = whenAndVerifyChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDeclaration), mockScanResultList=[MockScanResult(mockName=mockName1, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName1, calledMethodScanResultList=[])])",
        result.toString());
    whenAndVerifyChecker.check(classScanResult, classReferenceList);

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult1))));
    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult2))));

  }

  @Test
  public void check_DoSomethingWhenWithVerify() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDeclaration");

    MockScanResult mockScanResult1 = new MockScanResult();
    mockScanResult1.setMockName("mockName1");
    mockScanResult1.setLineNumber(12);
    mockScanResult1.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult1);

    CalledMethodScanResult calledMethodScanResult1 = new CalledMethodScanResult("when",
        "doThrow(Exception.class).when(this.classDeclaration).getName(argument1,argument2)",
        57,
        "Error");

    CalledMethodScanResult calledMethodScanResult2 = new CalledMethodScanResult("verify",
        "verify(this.classDeclaration).getName(argument1,argument2)",
        89,
        "Error");

    List<CalledMethodScanResult> calledMethodScanResultList1 = new ArrayList<>();
    calledMethodScanResultList1.add(calledMethodScanResult1);
    calledMethodScanResultList1.add(calledMethodScanResult2);

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
    classReference.setClassName("ClassName");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    ClassScanResult result = whenAndVerifyChecker.check(classScanResult, classReferenceList);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDeclaration), mockScanResultList=[MockScanResult(mockName=mockName1, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName1, calledMethodScanResultList=[])])",
        result.toString());
    whenAndVerifyChecker.check(classScanResult, classReferenceList);

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult1))));
    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult2))));

  }

  @Test
  public void check_WhenAnyArgumentWithVerifyAnyArgument() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDeclaration");

    MockScanResult mockScanResult1 = new MockScanResult();
    mockScanResult1.setMockName("mockName1");
    mockScanResult1.setLineNumber(12);
    mockScanResult1.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult1);

    CalledMethodScanResult calledMethodScanResult1 = new CalledMethodScanResult("when",
        "when(this.classDeclaration.getName(any(),argument2)).thenReturn(this.variable)",
        57,
        "Error");

    CalledMethodScanResult calledMethodScanResult2 = new CalledMethodScanResult("verify",
        "verify(this.classDeclaration).getName(any(),argument2)",
        89,
        "Error");

    List<CalledMethodScanResult> calledMethodScanResultList1 = new ArrayList<>();
    calledMethodScanResultList1.add(calledMethodScanResult1);
    calledMethodScanResultList1.add(calledMethodScanResult2);

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
    classReference.setClassName("ClassName");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    ClassScanResult result = whenAndVerifyChecker.check(classScanResult, classReferenceList);
    whenAndVerifyChecker.check(classScanResult, classReferenceList);

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

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDeclaration), mockScanResultList=[MockScanResult(mockName=mockName1, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName1, calledMethodScanResultList=[CalledMethodScanResult(methodCalledName=when, methodCalledArguments=when(this.classDeclaration.getName(any(),argument2)).thenReturn(this.variable), lineNumber=57, errorMessage=Error), CalledMethodScanResult(methodCalledName=verify, methodCalledArguments=verify(this.classDeclaration).getName(any(),argument2), lineNumber=89, errorMessage=Error)])])",
        result.toString());
  }
}
