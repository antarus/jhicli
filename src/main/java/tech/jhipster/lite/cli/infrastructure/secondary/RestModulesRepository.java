package tech.jhipster.lite.cli.infrastructure.secondary;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import tech.jhipster.lite.cli.JhiCli;
import tech.jhipster.lite.cli.domain.ModuleSlug;
import tech.jhipster.lite.cli.domain.ModuleToApply;
import tech.jhipster.lite.cli.domain.Modules;
import tech.jhipster.lite.cli.domain.ModulesRepository;
import tech.jhipster.lite.cli.error.domain.Assert;

@Repository
public class RestModulesRepository implements ModulesRepository {
  private RestTemplate restTemplate = new RestTemplate();

  @Override
  public Modules list(String server) {
    Assert.field("server", server).notBlank();
    ResponseEntity<RestModules> result = restTemplate.getForEntity(server + "/api/modules", RestModules.class);
    var mod =result.getBody();
    if (mod != null) {
      return mod.toDomain();
    } else {
      return null;
    }


  }

  @Override
  public String apply(ModuleSlug module, ModuleToApply moduleToApply) {
    var jsonObject = new JSONObject();
    var headers= new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    jsonObject.put("projectFolder",moduleToApply.projectFolder());
    jsonObject.putAll(moduleToApply.properties());
    HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

    ResponseEntity<Modules> result = restTemplate.postForEntity(JhiCli.server + "/api/modules/" + module.slug() + "/apply", request, Modules.class);

    return result.toString();
  }


}
