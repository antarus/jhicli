package tech.jhipster.lite.cli.technical.infrastructure.secondary;


import com.fasterxml.jackson.annotation.JsonProperty;
import tech.jhipster.lite.cli.error.domain.Assert;
import tech.jhipster.lite.cli.module.domain.Module;
import tech.jhipster.lite.cli.module.domain.ModuleDescription;
import tech.jhipster.lite.cli.module.domain.ModuleSlug;

public record RestModule(@JsonProperty String slug, @JsonProperty String description,
                         @JsonProperty RestModuleProperties properties) {


  public RestModule {
    Assert.field("slug", slug).notBlank();
    Assert.field("description", description).notBlank();
  }
  public Module toDomain() {
    return new Module(new ModuleSlug(slug()), new ModuleDescription(description()), properties().toDomain());
  }
}
