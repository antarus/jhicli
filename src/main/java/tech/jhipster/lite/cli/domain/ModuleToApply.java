package tech.jhipster.lite.cli.domain;

import tech.jhipster.lite.cli.error.domain.Assert;

import java.util.Map;

public record ModuleToApply(String projectFolder,
                     Map<String, Object> properties) {

  public ModuleToApply {
    Assert.field("projectFolder", projectFolder).notBlank();
    Assert.notNull("properties", properties);

  }

}
