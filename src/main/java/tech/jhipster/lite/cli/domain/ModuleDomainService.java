package tech.jhipster.lite.cli.module.domain;

public class ModuleDomainService implements ModuleService {
  private final ModulesRepository modulesRepository;

  public ModuleDomainService(ModulesRepository modulesRepository) {
    this.modulesRepository = modulesRepository;
  }

  @Override
  public Modules list(String server){
    return this.modulesRepository.list(server);
  }
}
