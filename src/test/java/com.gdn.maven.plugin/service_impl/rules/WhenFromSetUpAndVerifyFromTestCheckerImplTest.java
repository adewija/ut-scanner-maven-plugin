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

import static org.junit.Assert.assertTrue;

public class WhenFromSetUpAndVerifyFromTestCheckerImplTest {
  private WhenFromSetUpAndVerifyFromTestCheckerImpl whenFromSetUpChecker =
      WhenFromSetUpAndVerifyFromTestCheckerImpl.getInstance();

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

    List<CalledMethodScanResult> calledMethodScanResultList2 = new ArrayList<>();
    calledMethodScanResultList2.add(calledMethodScanResult2);

    AnnotationScanResult annotationScanResult1 = new AnnotationScanResult();
    annotationScanResult1.setAnnotationName("Before");
    annotationScanResult1.setLineNumber(45);
    annotationScanResult1.setSupermethodName("superMethodName1");
    annotationScanResult1.setCalledMethodScanResultList(calledMethodScanResultList1);

    AnnotationScanResult annotationScanResult2 = new AnnotationScanResult();
    annotationScanResult2.setAnnotationName("Test");
    annotationScanResult2.setLineNumber(70);
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
    classReference.setClassName("ClassName");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    whenFromSetUpChecker.check(classScanResult, classReferenceList);

    assertTrue(classScanResult.getAnnotationScanResultList()
        .stream()
        .allMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult1))));
    assertTrue(classScanResult.getAnnotationScanResultList()
        .stream()
        .allMatch(e -> e.getCalledMethodScanResultList()
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

    List<CalledMethodScanResult> calledMethodScanResultList2 = new ArrayList<>();
    calledMethodScanResultList2.add(calledMethodScanResult2);

    AnnotationScanResult annotationScanResult1 = new AnnotationScanResult();
    annotationScanResult1.setAnnotationName("Before");
    annotationScanResult1.setLineNumber(45);
    annotationScanResult1.setSupermethodName("superMethodName1");
    annotationScanResult1.setCalledMethodScanResultList(calledMethodScanResultList1);

    AnnotationScanResult annotationScanResult2 = new AnnotationScanResult();
    annotationScanResult2.setAnnotationName("Test");
    annotationScanResult2.setLineNumber(70);
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
    classReference.setClassName("ClassName");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    whenFromSetUpChecker.check(classScanResult, classReferenceList);

    assertTrue(classScanResult.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .anyMatch(c -> c.equals(calledMethodScanResult1))));
    assertTrue(classScanResult.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .anyMatch(c -> c.equals(calledMethodScanResult2))));

  }

  @Test
  public void check_WhenAnyArgumentWithVerifyAnyArgumentWithTimes() {
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
        "verify(this.classDeclaration, times = 2).getName(argument1,argument2)",
        89,
        "Error");

    List<CalledMethodScanResult> calledMethodScanResultList1 = new ArrayList<>();
    calledMethodScanResultList1.add(calledMethodScanResult1);

    List<CalledMethodScanResult> calledMethodScanResultList2 = new ArrayList<>();
    calledMethodScanResultList2.add(calledMethodScanResult2);

    AnnotationScanResult annotationScanResult1 = new AnnotationScanResult();
    annotationScanResult1.setAnnotationName("Before");
    annotationScanResult1.setLineNumber(45);
    annotationScanResult1.setSupermethodName("superMethodName1");
    annotationScanResult1.setCalledMethodScanResultList(calledMethodScanResultList1);

    AnnotationScanResult annotationScanResult2 = new AnnotationScanResult();
    annotationScanResult2.setAnnotationName("Test");
    annotationScanResult2.setLineNumber(70);
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
    classReference.setClassName("ClassName");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    whenFromSetUpChecker.check(classScanResult, classReferenceList);

    assertTrue(classScanResult.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult1))));
    assertTrue(classScanResult.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult2))));

  }

  @Test
  public void check_doSomethingWhenWithVerify() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDeclaration");

    MockScanResult mockScanResult1 = new MockScanResult();
    mockScanResult1.setMockName("mockName1");
    mockScanResult1.setLineNumber(12);
    mockScanResult1.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult1);

    CalledMethodScanResult calledMethodScanResult1 = new CalledMethodScanResult("when",
        "doThrow(Exception).when(this.classDeclaration).getName(argument1,argument2)",
        57,
        "Error");

    CalledMethodScanResult calledMethodScanResult2 = new CalledMethodScanResult("verify",
        "verify(this.classDeclaration).getName(argument1,argument2)",
        89,
        "Error");

    List<CalledMethodScanResult> calledMethodScanResultList1 = new ArrayList<>();
    calledMethodScanResultList1.add(calledMethodScanResult1);

    List<CalledMethodScanResult> calledMethodScanResultList2 = new ArrayList<>();
    calledMethodScanResultList2.add(calledMethodScanResult2);

    AnnotationScanResult annotationScanResult1 = new AnnotationScanResult();
    annotationScanResult1.setAnnotationName("Before");
    annotationScanResult1.setLineNumber(45);
    annotationScanResult1.setSupermethodName("superMethodName1");
    annotationScanResult1.setCalledMethodScanResultList(calledMethodScanResultList1);

    AnnotationScanResult annotationScanResult2 = new AnnotationScanResult();
    annotationScanResult2.setAnnotationName("Test");
    annotationScanResult2.setLineNumber(70);
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
    classReference.setClassName("ClassName");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    whenFromSetUpChecker.check(classScanResult, classReferenceList);

    assertTrue(classScanResult.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult1))));
    assertTrue(classScanResult.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult2))));

  }

  @Test
  public void check_doSomethingWhenWithVerify_anyArguments() {
    InjectedMockScanResult injectedMockScanResult =
        new InjectedMockScanResult("ClassName", "classDeclaration");

    MockScanResult mockScanResult1 = new MockScanResult();
    mockScanResult1.setMockName("mockName1");
    mockScanResult1.setLineNumber(12);
    mockScanResult1.setErrorMessage("ErrorMessage");

    List<MockScanResult> mockScanResultList = new ArrayList<>();
    mockScanResultList.add(mockScanResult1);

    CalledMethodScanResult calledMethodScanResult1 = new CalledMethodScanResult("when",
        "doThrow(Exception).when(this.classDeclaration).getName(argument1,argument2)",
        57,
        "Error");

    CalledMethodScanResult calledMethodScanResult2 = new CalledMethodScanResult("verify",
        "verify(this.classDeclaration).getName(any(),argument2)",
        89,
        "Error");

    List<CalledMethodScanResult> calledMethodScanResultList1 = new ArrayList<>();
    calledMethodScanResultList1.add(calledMethodScanResult1);

    List<CalledMethodScanResult> calledMethodScanResultList2 = new ArrayList<>();
    calledMethodScanResultList2.add(calledMethodScanResult2);

    AnnotationScanResult annotationScanResult1 = new AnnotationScanResult();
    annotationScanResult1.setAnnotationName("Before");
    annotationScanResult1.setLineNumber(45);
    annotationScanResult1.setSupermethodName("superMethodName1");
    annotationScanResult1.setCalledMethodScanResultList(calledMethodScanResultList1);

    AnnotationScanResult annotationScanResult2 = new AnnotationScanResult();
    annotationScanResult2.setAnnotationName("Test");
    annotationScanResult2.setLineNumber(70);
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
    classReference.setClassName("ClassName");
    classReference.setMethodWithReturnTypeName(methodWithReturnTypeName);

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("targetPath");

    whenFromSetUpChecker.check(classScanResult, classReferenceList);

    assertTrue(classScanResult.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .noneMatch(c -> c.equals(calledMethodScanResult1))));
    assertTrue(classScanResult.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .anyMatch(c -> c.equals(calledMethodScanResult2))));

  }

}
