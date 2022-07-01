package tech.jhipster.lite.cli.technical.infrastructure.secondary;


import com.fasterxml.jackson.annotation.JsonProperty;
import tech.jhipster.lite.cli.error.domain.Assert;
import tech.jhipster.lite.cli.module.domain.Category;

import java.util.Collection;


record RestCategory(@JsonProperty String name, @JsonProperty Collection<RestModule> modules) {

//  private static final Comparator<RestJHipsterModule> MODULE_COMPARATOR = Comparator.comparing(RestJHipsterModule::getSlug);

  RestCategory {
    Assert.field("name", name).notBlank();
    Assert.field("modules", modules).notNull().noNullElement();
  }

  public Category toDomain() {
    return new Category(name(), modules().stream().map(RestModule::toDomain).toList());
  }
}
