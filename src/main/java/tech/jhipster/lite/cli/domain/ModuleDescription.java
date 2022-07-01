package tech.jhipster.lite.cli.module.domain;

import tech.jhipster.lite.cli.error.domain.Assert;

public record ModuleDescription(String description) {
  public ModuleDescription {
    Assert.field("description", description).notBlank();
  }
}
