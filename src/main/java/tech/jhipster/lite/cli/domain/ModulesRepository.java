package tech.jhipster.lite.cli.domain;

public interface ModulesRepository {
  Modules list(String server);

  String apply(ModuleSlug module, ModuleToApply moduleToApply);
}
