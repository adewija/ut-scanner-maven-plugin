package com.gdn.maven.plugin.model;

public enum AnnotationType {
  BEFORE ("Before"),
  AFTER ("After"),
  TEST ("Test"),
  IGNORE ("Ignore"),
  MOCK ("Mock"),
  INJECT_MOCKS ("InjectMocks");

  private String type;

  AnnotationType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
