package tech.jhipster.lite.cli.domain;

import tech.jhipster.lite.cli.error.domain.Assert;

public record ModuleDescription(String description) {
  public ModuleDescription {
    Assert.field("description", description).notBlank();
  }
  public String get(){
    return description();
  }
}
