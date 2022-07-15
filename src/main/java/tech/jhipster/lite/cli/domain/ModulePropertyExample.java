package tech.jhipster.lite.cli.domain;

import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.cli.error.domain.Assert;

import java.util.Optional;

public record ModulePropertyExample(String example) {
  public ModulePropertyExample {
    Assert.notNull("example", example);
  }

  public static Optional<ModulePropertyExample> of(String example) {
    return Optional.ofNullable(example).filter(StringUtils::isNotBlank).map(ModulePropertyExample::new);
  }
  public String get(){
    return example();
  }
}
