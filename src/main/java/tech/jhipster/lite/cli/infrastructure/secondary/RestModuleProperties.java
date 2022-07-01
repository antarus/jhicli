
package tech.jhipster.lite.cli.infrastructure.secondary;


import com.fasterxml.jackson.annotation.JsonProperty;
import tech.jhipster.lite.cli.error.domain.Assert;
import tech.jhipster.lite.cli.domain.ModuleProperty;

import java.util.Collection;

public record RestModuleProperties(@JsonProperty Collection<RestModuleProperty> definitions) {

  public RestModuleProperties {
    Assert.notNull("definitions", definitions);
  }

  public Collection<ModuleProperty> toDomain() {
    return definitions().stream().map(RestModuleProperty::toDomain).toList();
  }
}
