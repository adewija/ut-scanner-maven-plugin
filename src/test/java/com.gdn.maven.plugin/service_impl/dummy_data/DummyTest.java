package com.gdn.maven.plugin.service_impl.test;

import com.gdn.maven.plugin.model.*;
import com.gdn.maven.plugin.service_impl.UnitTestScannerServiceImpl;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class DummyTest {

  @Mock
  private UnitTestScannerServiceImpl unitTestScannerService = new UnitTestScannerServiceImpl();

  @Test(expected = Exception.class)
  public void dataDummyForHelperTest() {
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

    classReference.getClassName();

    List<ClassReference> classReferenceList = new ArrayList<>();
    classReferenceList.add(classReference);

    List<String> listOfPath = new ArrayList<>();
    listOfPath.add("path");

    LoaderAndPathReflection loaderAndPathReflection = new LoaderAndPathReflection();
    loaderAndPathReflection.setTargetPath("targetPath");
    loaderAndPathReflection.setLoader(null);
    loaderAndPathReflection.setListOfPath(listOfPath);


    loaderAndPathReflection.getTargetPath();

    ClassScanResult result = unitTestScannerService.applyRules(classScanResult);

    assertEquals(
        "ClassScanResult(pathClass=D:\\Users\\abc.java, injectedMockScanResult=InjectedMockScanResult(className=ClassName, classDeclaration=classDecalaration), mockScanResultList=[MockScanResult(mockName=MockName, lineNumber=12, errorMessage=ErrorMessage)], annotationScanResultList=[AnnotationScanResult(annotationName=Test, lineNumber=45, supermethodName=superMethodName, calledMethodScanResultList=[CalledMethodScanResult(methodCalledName=methodCalledName, methodCalledArguments=argument, lineNumber=87, errorMessage=Error)])])",
        result.toString());

    assertTrue(result.getAnnotationScanResultList()
        .stream()
        .anyMatch(e -> e.getCalledMethodScanResultList()
            .stream()
            .anyMatch(c -> c.equals(calledMethodScanResult))));

    when(unitTestScannerService.applyRules(classScanResult)).thenReturn(null);

    verify(unitTestScannerService).applyRules(classScanResult);

    assertNotNull(result);

    verifyNoMoreInteractions(unitTestScannerService);
  }

  @After
  public void tearDown() {
  }

}
