package tech.jhipster.lite.cli.technical.infrastructure.secondary;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import tech.jhipster.lite.cli.module.domain.*;

@Repository
public class RestModulesRepository implements ModulesRepository {

  @Override
  public Modules list(String server) {
    RestTemplate restTemplate = new RestTemplate();

//    String test = restTemplate.getForObject(server + "/api/modules", String.class);
//    System.out.printf(test);
    RestModules result = restTemplate.getForObject(server + "/api/modules", RestModules.class);


//    result.listModule().stream().map(RestModule::toDomain).toList().forEach(module -> {
//      Output.printMessage(module.slug().slug());
//    });

    return result.toDomain();
  }

  @Override
  public void apply(ModuleSlug module, ModuleToApply moduleToApply) {

  }


}
