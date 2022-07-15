package tech.jhipster.lite.cli.domain;

import tech.jhipster.lite.cli.error.domain.Assert;

public record ModuleSlug(String slug) {
  public ModuleSlug {
    Assert.field("slug", slug).notBlank().maxLength(100);
  }
  public String get(){
    return slug();
  }
}
