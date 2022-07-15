package tech.jhipster.lite.cli.domain;


import tech.jhipster.lite.cli.error.domain.Assert;

import java.util.Collection;
import java.util.List;

public record Modules( Collection<Category> categories) {


  public Modules {
    Assert.field("categories", categories).notNull().noNullElement();
  }

  public  List<String>  listModules() {
    return this.categories()
      .stream()
      .map(entry ->  entry.modules().stream().map(m -> m.slug().slug()).toList())
      .flatMap(List::stream)
      .toList();
  }

  public  List<Module>  listModule() {
    return this.categories()
      .stream()
      .map(m -> m.modules().stream().map(ma -> ma).toList())
      .flatMap(List::stream)
      .toList();
  }
}
