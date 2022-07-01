package tech.jhipster.lite.cli.module.domain;


import tech.jhipster.lite.cli.error.domain.Assert;

public record ModuleProperty(ModulePropertyType type, boolean mandatory, ModulePropertyKey key, java.util.Optional<ModulePropertyDescription> description, java.util.Optional<ModulePropertyExample> example) {

  public ModuleProperty{
    Assert.notNull("type", type);
    Assert.notNull("key", key);
  }


}
