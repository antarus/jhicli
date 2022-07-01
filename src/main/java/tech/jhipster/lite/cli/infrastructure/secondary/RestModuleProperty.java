package tech.jhipster.lite.cli.infrastructure.secondary;


import com.fasterxml.jackson.annotation.JsonProperty;
import tech.jhipster.lite.cli.domain.*;
import tech.jhipster.lite.cli.error.domain.Assert;

public record RestModuleProperty(@JsonProperty ModulePropertyType type, @JsonProperty boolean mandatory, @JsonProperty String key, @JsonProperty String description, @JsonProperty String example) {

  public RestModuleProperty {
    Assert.notNull("type", type);
    Assert.notNull("key", key);
  }

  public ModuleProperty toDomain() {
      return new ModuleProperty(type(), mandatory(), new ModulePropertyKey(key()), ModulePropertyDescription.of(description()), ModulePropertyExample.of(example()));
  }

}
