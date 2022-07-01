package tech.jhipster.lite.cli.domain;

import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.cli.error.domain.Assert;

import java.util.Optional;

public record ModulePropertyDescription(String description) {
  public ModulePropertyDescription {
    Assert.field("description", description).notBlank().maxLength(100);
  }

  public static Optional<ModulePropertyDescription> of(String description) {
    return Optional.ofNullable(description).filter(StringUtils::isNotBlank).map(ModulePropertyDescription::new);
  }

  public String get() {
    return description();
  }
}
