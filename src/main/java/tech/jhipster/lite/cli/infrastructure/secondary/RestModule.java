package tech.jhipster.lite.cli.infrastructure.secondary;


import com.fasterxml.jackson.annotation.JsonProperty;
import tech.jhipster.lite.cli.error.domain.Assert;
import tech.jhipster.lite.cli.domain.Module;
import tech.jhipster.lite.cli.domain.ModuleDescription;
import tech.jhipster.lite.cli.domain.ModuleSlug;

public record RestModule(@JsonProperty String slug, @JsonProperty String description,
                         @JsonProperty RestModuleProperties properties) {


  public RestModule {
    Assert.field("slug", slug).notBlank();
    Assert.field("description", description).notBlank();
    Assert.notNull("properties", properties);
  }
  public Module toDomain() {
    return new Module(new ModuleSlug(slug()), new ModuleDescription(description()), properties().toDomain());
  }
}
