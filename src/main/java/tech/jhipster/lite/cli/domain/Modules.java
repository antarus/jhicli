package tech.jhipster.lite.cli.module.domain;


import tech.jhipster.lite.cli.error.domain.Assert;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public record Modules( Collection<Category> categories) {


  public Modules {
    Assert.field("categories", categories).notNull().noNullElement();
  }

  public List<String> listCategories(){
    return this.categories().stream().map(n-> n.name()).toList();
  }
  public  List<String>  listModules() {
    return this.categories()
      .stream()
      .map(entry ->  entry.modules().stream().map(m -> m.slug().slug()).collect(Collectors.toList()))
      .flatMap(List::stream)
      .toList();
  }

  public  List<Module>  listModule() {
    return this.categories()
      .stream()
      .map(m -> m.modules().stream().map(ma -> ma).collect(Collectors.toList()))
//      .map(entry ->  entry.modules().stream().map(m -> m.slug()).collect(Collectors.toList()))
      .flatMap(List::stream)
//      .sorted(String::compareToIgnoreCase)
      .toList();
  }
}
