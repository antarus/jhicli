package tech.jhipster.lite.cli;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import picocli.CommandLine;
import tech.jhipster.lite.cli.domain.Module;
import tech.jhipster.lite.cli.domain.ModuleDomainService;
import tech.jhipster.lite.cli.domain.ModulePropertyType;
import tech.jhipster.lite.cli.domain.ModuleService;
import tech.jhipster.lite.cli.domain.Modules;
import tech.jhipster.lite.cli.infrastructure.secondary.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JhiCliTest {
  @Mock
  private RestTemplate restTemplate;


  @InjectMocks
  private RestModulesRepository restModulesRepository = new RestModulesRepository();

  @Mock
  private ModuleService moduleService = new ModuleDomainService(restModulesRepository);

  @InjectMocks
  private JhiCli jhiCli=new JhiCli();

  @Test
  void shouldDisplayHelpIfNoCommand() {
//    ByteArrayOutputStream err= new ByteArrayOutputStream();
//    ByteArrayOutputStream out= new ByteArrayOutputStream();
//    PrintStream oldErr= System.err;
//    PrintStream oldOut= System.out;
    try{
//      System.setErr(new PrintStream(err));
//      System.setOut(new PrintStream(out));
      Mockito.when( moduleService.list("http://localhost:7471")).thenReturn(getModules());
      CommandLine cmd = jhiCli.buildCommandLine();
      int exitCode = cmd.execute();
      assertThat(exitCode).isEqualTo(1);
//      assertThat(out.toString()).contains("Usage:");

    } finally {
//      System.setErr(oldErr);
//      System.setOut(oldOut);
    }

  }
  @Test
   void shouldDisplayModuleList() {
//    ByteArrayOutputStream err= new ByteArrayOutputStream();
//    ByteArrayOutputStream out= new ByteArrayOutputStream();
//    PrintStream oldErr= System.err;
//    PrintStream oldOut= System.out;
    RestModules response = getRestModules();
    Mockito.when( moduleService.list("http://localhost:7471")).thenReturn(getModules());
//    var a =moduleService.list("http://localhost:7471");

    try{
//      System.setErr(new PrintStream(err));
//      System.setOut(new PrintStream(out));

      CommandLine cmd = jhiCli.buildCommandLine();
      int exitCode = cmd.execute("module", "list");
      assertThat(exitCode).isZero();


    } finally {
//      System.setErr(oldErr);
//      System.setOut(oldOut);
    }

  }

  private List<String> getModule() {
    return List.of("test1", "test2");
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
  private Modules getModules() {

    return getRestModules().toDomain();
  }
}
