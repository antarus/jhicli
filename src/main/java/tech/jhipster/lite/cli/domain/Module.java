package tech.jhipster.lite.cli.module.domain;


import tech.jhipster.lite.cli.error.domain.Assert;

import java.util.Collection;

public record Module(ModuleSlug slug,
                     ModuleDescription description,
                  Collection<ModuleProperty> properties) {


  public Module {
    Assert.notNull("slug", slug);
    Assert.notNull("description", description);
    Assert.field("properties", properties).notNull().noNullElement();
  }

}
