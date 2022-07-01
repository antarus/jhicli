package tech.jhipster.lite.cli.technical.infrastructure.secondary;


import com.fasterxml.jackson.annotation.JsonProperty;
import tech.jhipster.lite.cli.error.domain.Assert;
import tech.jhipster.lite.cli.module.domain.*;

record RestModuleProperty(@JsonProperty ModulePropertyType type, @JsonProperty boolean mandatory, @JsonProperty String key, @JsonProperty String description, @JsonProperty String example) {

  RestModuleProperty {
    Assert.notNull("type", type);
    Assert.notNull("key", key);
//    Assert.notNull("description", description);
//    Assert.field("example", example).notBlank();
  }

  public ModuleProperty toDomain() {
      return new ModuleProperty(type(), mandatory(), new ModulePropertyKey(key()), ModulePropertyDescription.of(description()), ModulePropertyExample.of(example()));
  }

}
