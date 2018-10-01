package com.gdn.maven.plugin.helper;


import com.gdn.maven.plugin.model.CalledMethodScanResult;
import com.gdn.maven.plugin.model.ClassScanResult;
import com.gdn.maven.plugin.model.MethodType;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodCallVisit extends VoidVisitorAdapter<ClassScanResult> {
  @Override
  public void visit(MethodCallExpr methodCallExpr, ClassScanResult classScanResult) {

    if (methodCallExpr.getName().toString().contains(MethodType.ASSERT.getType())) {
      classScanResult.getAnnotationScanResultList()
          .get(classScanResult.getAnnotationScanResultList().size() - 1)
          .getCalledMethodScanResultList()
          .add(new CalledMethodScanResult(methodCallExpr.getNameAsString(),
              findArguments(methodCallExpr.getParentNode().get().getTokenRange().get().toString()),
              methodCallExpr.getBegin().get().line,
              setErrorMessage(MethodType.ASSERT, methodCallExpr)));
    }

    if (methodCallExpr.getName().toString().equals(MethodType.WHEN.getType())) {
      classScanResult.getAnnotationScanResultList()
          .get(classScanResult.getAnnotationScanResultList().size() - 1)
          .getCalledMethodScanResultList()
          .add(new CalledMethodScanResult(methodCallExpr.getNameAsString(),
              findArguments(methodCallExpr.getParentNode().get().getTokenRange().get().toString()),
              methodCallExpr.getBegin().get().line,
              setErrorMessage(MethodType.WHEN, methodCallExpr)));
    }

    if (methodCallExpr.getName().toString().equals(MethodType.VERIFY.getType())) {
      classScanResult.getAnnotationScanResultList()
          .get(classScanResult.getAnnotationScanResultList().size() - 1)
          .getCalledMethodScanResultList()
          .add(new CalledMethodScanResult(methodCallExpr.getNameAsString(),
              findArguments(methodCallExpr.getParentNode().get().getTokenRange().get().toString()),
              methodCallExpr.getBegin().get().line,
              setErrorMessage(MethodType.VERIFY, methodCallExpr)));
    }

    if (methodCallExpr.getName()
        .toString()
        .equals(MethodType.VERIFY_NO_MORE_INTERACTION.getType())) {
      classScanResult.getAnnotationScanResultList()
          .get(classScanResult.getAnnotationScanResultList().size() - 1)
          .getCalledMethodScanResultList()
          .add(new CalledMethodScanResult(methodCallExpr.getNameAsString(),
              findArguments(methodCallExpr.getParentNode().get().getTokenRange().get().toString()),
              methodCallExpr.getBegin().get().line,
              setErrorMessage(MethodType.VERIFY_NO_MORE_INTERACTION, methodCallExpr)));
    }

    if (methodCallExpr.getParentNode().get().getTokenRange().get().toString().contains("=") && isVariable(
        findArguments(methodCallExpr.getParentNode().get().getTokenRange().get().toString()))) {
      classScanResult.getAnnotationScanResultList()
          .get(classScanResult.getAnnotationScanResultList().size() - 1)
          .getCalledMethodScanResultList()
          .add(new CalledMethodScanResult(methodCallExpr.getNameAsString(),
              findArguments(methodCallExpr.getParentNode().get().getTokenRange().get().toString()),
              methodCallExpr.getBegin().get().line,
              String.format(
                  "Couldn't Find Matching Assert Method from Test Class for Variable: %s at Line %d",
                  findArguments(methodCallExpr.getParentNode().get().getTokenRange().get().toString()),
                  methodCallExpr.getBegin().get().line)));

    }

    super.visit(methodCallExpr, classScanResult);
  }

  private String setErrorMessage(MethodType methodType, MethodCallExpr methodCallExpr) {
    String errorMessage = null;

    if (!containsAnyMethod(methodCallExpr.getParentNode().get().getParentNode().get().toString())) {
      switch (methodType) {
        case ASSERT:
          if (methodCallExpr.getNameAsString().contains("assertNotNull")) {
            errorMessage = String.format(
                "Please Use Other Assert Method Instead of assertNotNull for : %s at Line %d",
                findArguments(methodCallExpr.getParentNode().get().getTokenRange().get().toString()),
                methodCallExpr.getBegin().get().line);
          } else {
            errorMessage = String.format(
                "Couldn't Find Matching Method to be Asserted for : %s at Line: %d",
                findArguments(methodCallExpr.getParentNode().get().getTokenRange().get().toString()),
                methodCallExpr.getBegin().get().line);
          }
          break;
        case WHEN:
          errorMessage = String.format(
              "Couldn't Find Matching Verify Method for : %s at Line: %d",
              findArguments(methodCallExpr.getParentNode().get().getTokenRange().get().toString()),
              methodCallExpr.getBegin().get().line);

          break;
        case VERIFY:
          errorMessage = String.format(
              "Couldn't Find Matching Void Test Method for : %s at Line: %d",
              findArguments(methodCallExpr.getParentNode().get().getTokenRange().get().toString()),
              methodCallExpr.getBegin().get().line);
          break;
        case VERIFY_NO_MORE_INTERACTION:
          errorMessage = String.format(
              "Couldn't Find Matching Mocked Declarations for : %s at Line: %d",
              findArguments(methodCallExpr.getParentNode().get().getTokenRange().get().toString()),
              methodCallExpr.getBegin().get().line);
          break;
      }
    } else {
      errorMessage = String.format(
          "Please Consider to not Use any() as an Argument for : %s at Line: %d",
          findArguments(methodCallExpr.getParentNode().get().getTokenRange().get().toString()),
          methodCallExpr.getBegin().get().line);
    }


    return errorMessage;
  }

  private boolean containsAnyMethod(String methodCallArguments) {
    boolean result = false;
    if (methodCallArguments.contains("any")) {
      char charAt = methodCallArguments.charAt(methodCallArguments.indexOf("any") - 1);
      if (!Character.isLetter(charAt)) {
        result = true;
      }
    }
    return result;
  }

  private String findArguments(String argumentWithTokenRange) {
    String tempArgument = argumentWithTokenRange;
    tempArgument = tempArgument.replaceAll("\\s+", "");
    return tempArgument;
  }

  private boolean isVariable(String variable) {
    boolean result = false;
    if (variable.contains("=")) {
      char charAt = variable.charAt(variable.lastIndexOf("=") - 1);
      if (Character.isLetter(charAt)) {
        result = true;
      }
    }
    return result;
  }
}