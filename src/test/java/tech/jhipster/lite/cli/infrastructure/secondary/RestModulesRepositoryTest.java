package tech.jhipster.lite.cli.infrastructure.secondary;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tech.jhipster.lite.cli.JhiCli;
import tech.jhipster.lite.cli.UnitTest;
import tech.jhipster.lite.cli.domain.ModulePropertyType;
import tech.jhipster.lite.cli.domain.ModuleSlug;
import tech.jhipster.lite.cli.domain.ModuleToApply;
import tech.jhipster.lite.cli.domain.Modules;
import tech.jhipster.lite.cli.error.domain.Assert;
import tech.jhipster.lite.cli.error.domain.MissingMandatoryValueException;

@ExtendWith(MockitoExtension.class)
class RestModulesRepositoryTest {

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private RestModulesRepository restModulesRepository = new RestModulesRepository();

  @Test
  void listModulesWhenServerIsBlankMustThrowException(){
    assertThatThrownBy(() -> restModulesRepository.list(""))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("\"server\"");
  }
  @Test
  void shouldListModules(){
    RestModules response = getRestModules();
    Mockito.when( restTemplate.getForEntity("http://localhost:7471/api/modules", RestModules.class)).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

    assertThat(restModulesRepository.list("http://localhost:7471")).isEqualTo(response.toDomain());
  }
  @Test
  void shouldNotListModules(){
    RestModules response = getRestModules();
    Mockito.when( restTemplate.getForEntity("http://localhost:7471/api/modules", RestModules.class)).thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

    assertThat(restModulesRepository.list("http://localhost:7471")).isNull();
  }

  @Test
  void shouldApplyModules(){
    ModuleSlug slug = new ModuleSlug("postgresql");
    ModuleToApply modToApply = new ModuleToApply("projectFolder", new HashMap<>());


    var jsonObject = new JSONObject();
    var headers= new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    jsonObject.put("projectFolder",modToApply.projectFolder());
    jsonObject.putAll(modToApply.properties());
    HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

    Modules response = getRestModules().toDomain();
    Mockito.when( restTemplate.postForEntity(JhiCli.server + "/api/modules/postgresql/apply", request, Modules.class)).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

    assertThat(restModulesRepository.apply(slug,modToApply)).isEqualTo(new ResponseEntity<>(response, HttpStatus.OK).toString());

  }

  private RestModules getRestModules() {
    Collection<RestCategory> categories = new ArrayList<>();
    Collection<RestModule> modules = new ArrayList<>();
    Collection<RestModuleProperty> moduleProperties =  new ArrayList<>();
    RestModuleProperty moduleProperty = new RestModuleProperty(ModulePropertyType.STRING, false, "baseName", "basename description", "basenameExampel");
    moduleProperties.add(moduleProperty);
    RestModuleProperties properties = new RestModuleProperties(moduleProperties);

    RestModule module1 = new RestModule("test1", "description test1", properties);
    RestModule module2 = new RestModule("test2", "description test2", properties);
    modules.add(module1);
    modules.add(module2);

    RestCategory category = new RestCategory("spring", modules );
    categories.add(category);
    RestModules response = new RestModules(categories);
    return response;
  }
}
