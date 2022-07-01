package tech.jhipster.lite.cli.module.domain;

import tech.jhipster.lite.cli.error.domain.Assert;

public record ModuleSlug(String slug) {
  public ModuleSlug(String slug) {
    Assert.field("slug", slug).notBlank().maxLength(100);
    this.slug = slug;
  }
}
