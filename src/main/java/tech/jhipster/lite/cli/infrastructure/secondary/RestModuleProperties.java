package tech.jhipster.lite.cli.technical.infrastructure.secondary;


import com.fasterxml.jackson.annotation.JsonProperty;
import tech.jhipster.lite.cli.error.domain.Assert;
import tech.jhipster.lite.cli.module.domain.ModuleProperty;

import java.util.Collection;

record RestModuleProperties(@JsonProperty Collection<RestModuleProperty> definitions) {

  RestModuleProperties {
    Assert.notNull("definitions", definitions);
  }

  public Collection<ModuleProperty> toDomain() {
    return definitions().stream().map(RestModuleProperty::toDomain).toList();
  }
}
