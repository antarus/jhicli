package tech.jhipster.lite.cli.domain;


import tech.jhipster.lite.cli.error.domain.Assert;

import java.util.Collection;


public record Category( String name,  Collection<Module> modules) {

  public Category {
    Assert.field("name", name).notBlank();
    Assert.field("modules", modules).notNull().noNullElement();
  }

}
