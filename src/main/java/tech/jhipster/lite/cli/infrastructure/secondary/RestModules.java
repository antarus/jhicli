package tech.jhipster.lite.cli.infrastructure.secondary;


import com.fasterxml.jackson.annotation.JsonProperty;
import tech.jhipster.lite.cli.domain.Modules;
import tech.jhipster.lite.cli.error.domain.Assert;

import java.util.Collection;

public record RestModules(@JsonProperty Collection<RestCategory> categories) {


  public RestModules {
    Assert.field("categories", categories).notNull().noNullElement();
  }

  public Modules toDomain() {
    return new Modules(categories().stream().map(RestCategory::toDomain).toList());
  }
}
