package tech.jhipster.lite.cli.domain;


import tech.jhipster.lite.cli.error.domain.Assert;

public record ModulePropertyKey(String key) {
  public ModulePropertyKey {
    Assert.notBlank("key", key);
  }

  public String get() {
    return key();
  }
}
