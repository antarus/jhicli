package tech.jhipster.lite.cli.infrastructure.secondary;


import com.fasterxml.jackson.annotation.JsonProperty;
import tech.jhipster.lite.cli.error.domain.Assert;
import tech.jhipster.lite.cli.domain.Category;

import java.util.Collection;


public record RestCategory(@JsonProperty String name, @JsonProperty Collection<RestModule> modules) {
  public RestCategory {
    Assert.field("name", name).notBlank();
    Assert.field("modules", modules).notNull().noNullElement();
  }

  public Category toDomain() {
    return new Category(name(), modules().stream().map(RestModule::toDomain).toList());
  }
}
