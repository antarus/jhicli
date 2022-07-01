package tech.jhipster.lite.cli.module.domain;

public interface ModulesRepository {
  Modules list(String server);

  void apply(ModuleSlug module, ModuleToApply moduleToApply);
}
