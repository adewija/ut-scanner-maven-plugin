package com.gdn.maven.plugin.model;

public enum MethodType {
  ASSERT ("assert"),
  ADD ("add"),
  WHEN ("when"),
  VERIFY("verify"),
  VERIFY_NO_MORE_INTERACTION("verifyNoMoreInteractions"),
  ASSERT_NOT_NULL("assertNotNull");


  private String type;

  MethodType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
