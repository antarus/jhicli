package tech.jhipster.lite.cli.domain;

public interface ModuleService {
  Modules list(String server);

  String apply(ModuleSlug module, ModuleToApply moduleToApply);
}
