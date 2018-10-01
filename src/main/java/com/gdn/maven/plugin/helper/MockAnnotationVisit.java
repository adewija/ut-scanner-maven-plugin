package com.gdn.maven.plugin.helper;

import com.gdn.maven.plugin.model.AnnotationType;
import com.gdn.maven.plugin.model.ClassScanResult;
import com.gdn.maven.plugin.model.InjectedMockScanResult;
import com.gdn.maven.plugin.model.MockScanResult;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MockAnnotationVisit extends VoidVisitorAdapter<ClassScanResult> {

  @Override
  public void visit(MarkerAnnotationExpr markerAnnotationExpr, ClassScanResult classScanResult) {
    if (markerAnnotationExpr.getNameAsString().equalsIgnoreCase(AnnotationType.MOCK.getType())) {

      String parentNode = markerAnnotationExpr.getParentNode().get().toString();
      String[] parentNodeSplitList = parentNode.split("\\s+|\\r\\n");
      String mockVariable = parentNodeSplitList[parentNodeSplitList.length - 1].replaceAll(";", "");
      String errorMessage = String.format(
          "Couldn't Find Matching VerifyNoMoreInteractions Method for : %s at Line: %d",
          mockVariable,
          markerAnnotationExpr.getBegin().get().line);

      classScanResult.getMockScanResultList()
          .add(new MockScanResult(mockVariable,
              markerAnnotationExpr.getBegin().get().line,
              errorMessage));
    }

    if (markerAnnotationExpr.getNameAsString()
        .equalsIgnoreCase(AnnotationType.INJECT_MOCKS.getType())) {

      String parentNode = markerAnnotationExpr.getParentNode().get().toString();
      String[] parentNodeSplitList = parentNode.split("\\s+|\\r\\n");
      String injectMockDeclaration =
          parentNodeSplitList[parentNodeSplitList.length - 1].replaceAll(";", "");
      String injectMockClass = parentNodeSplitList[parentNodeSplitList.length - 2];

      classScanResult.setInjectedMockScanResult(new InjectedMockScanResult(injectMockClass,
          injectMockDeclaration));
    }
    super.visit(markerAnnotationExpr, classScanResult);
  }
}