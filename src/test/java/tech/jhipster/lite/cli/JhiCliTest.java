package tech.jhipster.lite.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import picocli.CommandLine;
import tech.jhipster.lite.cli.domain.*;
import tech.jhipster.lite.cli.infrastructure.secondary.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JhiCliTest {
  @Mock
  private ModuleDomainService moduleService;
  @InjectMocks
  private JhiCli jhiCli;

  @BeforeEach
  public void setup() {
    JhiCli.server = "http://localhost:7471";
    JhiCli.verbose = false;
  }

  @Test
  void shouldDisplayHelpIfNoCommand() {
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream oldErr = System.err;
    PrintStream oldOut = System.out;
    try {
      System.setErr(new PrintStream(err));
      System.setOut(new PrintStream(out));
      Mockito.when(moduleService.list("http://localhost:7471")).thenReturn(getModules());
      CommandLine cmd = jhiCli.buildCommandLine();
      int exitCode = cmd.execute();
      assertThat(exitCode).isEqualTo(1);
    } finally {
      System.setErr(oldErr);
      System.setOut(oldOut);
    }
  }

  @Test
  void shouldDisplayHelpIfNoCommandInModule() {
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream oldErr = System.err;
    PrintStream oldOut = System.out;
    try {
      System.setErr(new PrintStream(err));
      System.setOut(new PrintStream(out));

      Mockito.when(moduleService.list("http://localhost:7471")).thenReturn(getModules());
      String[] args = {"module"};
      CommandLine cmd = jhiCli.buildCommandLine(args);
      int exitCode = cmd.execute(args);
      assertThat(exitCode).isEqualTo(1);
    } finally {
      System.setErr(oldErr);
      System.setOut(oldOut);
    }
  }

  @Test
  void shouldDisplayDescriptionIfModuleDescribe() {
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream oldErr = System.err;
    PrintStream oldOut = System.out;
    try {
      System.setErr(new PrintStream(err));
      System.setOut(new PrintStream(out));
      Mockito.when(moduleService.list("http://localhost:7471")).thenReturn(getModules());
      String[] args = {"module", "describe"};
      CommandLine cmd = jhiCli.buildCommandLine(args);
      int exitCode = cmd.execute(args);
      assertThat(exitCode).isEqualTo(2);
    } finally {
      System.setErr(oldErr);
      System.setOut(oldOut);
    }
  }

  @Test
  void shouldDisplayErrorWhenDescribeUnknownModule() {
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream oldErr = System.err;
    PrintStream oldOut = System.out;
    try {
      System.setErr(new PrintStream(err));
      System.setOut(new PrintStream(out));
      Mockito.when(moduleService.list("http://localhost:7471")).thenReturn(getModules());
      String[] args = {"module", "describe", "toto"};
      CommandLine cmd = jhiCli.buildCommandLine(args);
      int exitCode = cmd.execute(args);
      assertThat(exitCode).isEqualTo(2);
      assertThat(err.toString()).contains("Unmatched argument at index 2");
    } finally {
      System.setErr(oldErr);
      System.setOut(oldOut);
    }
  }

  @Test
  void shouldDisplayHelpIfNoCommandInModuleApply() {
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream oldErr = System.err;
    PrintStream oldOut = System.out;
    try {
      System.setErr(new PrintStream(err));
      System.setOut(new PrintStream(out));
      Mockito.when(moduleService.list("http://localhost:7471")).thenReturn(getModules());
      String[] args = {"module", "apply"};
      CommandLine cmd = jhiCli.buildCommandLine(args);
      int exitCode = cmd.execute(args);
      assertThat(exitCode).isEqualTo(2);
    } finally {
      System.setErr(oldErr);
      System.setOut(oldOut);
    }
  }


  @Test
  void shouldDescribeModuleTest() {
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream oldErr = System.err;
    PrintStream oldOut = System.out;
    RestModules response = getRestModules();
    Mockito.when(moduleService.list("http://localhost:7471")).thenReturn(getModules());
    try {
      System.setErr(new PrintStream(err));
      System.setOut(new PrintStream(out));
      String[] args = {"module", "describe", "test"};
      CommandLine cmd = jhiCli.buildCommandLine(args);
      int exitCode = cmd.execute(args);
      assertThat(exitCode).isZero();
      assertThat(out.toString()).contains("BOOLEAN");
      assertThat(out.toString()).contains("INTEGER");
      assertThat(out.toString()).contains("STRING");
    } finally {
      System.setErr(oldErr);
      System.setOut(oldOut);
    }
  }

  @Test
  void shouldDisplayModuleList() {
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream oldErr = System.err;
    PrintStream oldOut = System.out;
    Mockito.when(moduleService.list("http://localhost:7471")).thenReturn(getModules());
    try {
      System.setErr(new PrintStream(err));
      System.setOut(new PrintStream(out));
      String[] args = {"module", "list"};
      CommandLine cmd = jhiCli.buildCommandLine(args);
      int exitCode = cmd.execute(args);
      assertThat(exitCode).isZero();
    } finally {
      System.setErr(oldErr);
      System.setOut(oldOut);
    }
  }

  @Test
  void shouldNotApplyModuleWhenNoMandatoryOption() {
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream oldErr = System.err;
    PrintStream oldOut = System.out;
    ModuleToApply moduleToApply = new ModuleToApply("/tmp/test", new HashMap<String, Object>());
    Mockito.when(moduleService.list("http://localhost:7471")).thenReturn(getModules());
    try {
      System.setErr(new PrintStream(err));
      System.setOut(new PrintStream(out));
      String[] args = {"module", "apply", "test", "--project-path=/tmp/test"};
      CommandLine cmd = jhiCli.buildCommandLine(args);
      int exitCode = cmd.execute(args);
      assertThat(exitCode).isEqualTo(2);
      assertThat(err.toString()).contains("Missing required options");
    } finally {
      System.setErr(oldErr);
      System.setOut(oldOut);
    }
  }


  @Test
  void shouldApplyModule() {
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream oldErr = System.err;
    PrintStream oldOut = System.out;
    ModuleToApply moduleToApply = new ModuleToApply("/tmp/test", new HashMap<String, Object>());
    Mockito.when(moduleService.list("http://localhost:7471")).thenReturn(getModules());
    Mockito.when(moduleService.apply(new ModuleSlug("test"), moduleToApply)).thenReturn("apply ok");
    try {
      System.setErr(new PrintStream(err));
      System.setOut(new PrintStream(out));
      String[] args = {"module", "apply", "test", "--enabled", "--numbercpu=1", "--project-path=/tmp/test"};
      CommandLine cmd = jhiCli.buildCommandLine(args);
      int exitCode = cmd.execute(args);
      assertThat(exitCode).isZero();
      assertThat(out.toString()).contains("apply ok");
    } finally {
      System.setErr(oldErr);
      System.setOut(oldOut);
    }
  }

  @Test
  void shouldReturnErrorOnApplyModule() {
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream oldErr = System.err;
    PrintStream oldOut = System.out;
    ModuleToApply moduleToApply = new ModuleToApply("/tmp/test", new HashMap<String, Object>());
    Mockito.when(moduleService.list("http://localhost:7471")).thenReturn(getModules());
    Mockito.when(moduleService.apply(new ModuleSlug("test"), moduleToApply)).thenThrow(new RuntimeException("error on apply"));
    try {
      System.setErr(new PrintStream(err));
      System.setOut(new PrintStream(out));
      String[] args = {"module", "apply", "test", "--enabled", "--numbercpu=1", "--project-path=/tmp/test"};
      CommandLine cmd = jhiCli.buildCommandLine(args);
      int exitCode = cmd.execute(args);
      assertThat(exitCode).isEqualTo(1);
      assertThat(err.toString()).contains("error on apply");
    } finally {
      System.setErr(oldErr);
      System.setOut(oldOut);
    }
  }

  @Test
  void shouldReturnErrorOnApplyModuleWithNoProjectPath() {
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream oldErr = System.err;
    PrintStream oldOut = System.out;
    ModuleToApply moduleToApply = new ModuleToApply("/tmp/test", new HashMap<String, Object>());
    Mockito.when(moduleService.list("http://localhost:7471")).thenReturn(getModules());
    try {
      System.setErr(new PrintStream(err));
      System.setOut(new PrintStream(out));
      String[] args = {"module", "apply", "test", "--enabled", "--numbercpu=1", "--project-path="};
      CommandLine cmd = jhiCli.buildCommandLine(args);
      int exitCode = cmd.execute(args);
      assertThat(exitCode).isEqualTo(1);
      assertThat(err.toString()).contains("The option \"--project-path\" is mandatory and wasn't set");
    } finally {
      System.setErr(oldErr);
      System.setOut(oldOut);
    }
  }


  @Test
  void shouldGenerateCompletionCommand() {
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream oldErr = System.err;
    PrintStream oldOut = System.out;
    ModuleToApply moduleToApply = new ModuleToApply("/tmp/test", new HashMap<String, Object>());
    Mockito.when(moduleService.list("http://localhost:7471")).thenReturn(getModules());
    try {
      System.setErr(new PrintStream(err));
      System.setOut(new PrintStream(out));
      String[] args = {"generate-completion"};
      CommandLine cmd = jhiCli.buildCommandLine(args);
      int exitCode = cmd.execute(args);
      assertThat(exitCode).isZero();
      assertThat(out.toString()).contains("_picocli_jhicli_module_apply_test2");
    } finally {
      System.setErr(oldErr);
      System.setOut(oldOut);
    }
  }

  @Test
  void shouldGenerateCompletionCommandWithNonDefaultServer() {
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream oldErr = System.err;
    PrintStream oldOut = System.out;
    ModuleToApply moduleToApply = new ModuleToApply("/tmp/test", new HashMap<String, Object>());
//    Mockito.when( moduleService.list("http://localhost:7471")).thenReturn(getModules());
    Mockito.when(moduleService.list("http://localhost:7472")).thenReturn(getOtherModules());
    try {
      System.setErr(new PrintStream(err));
      System.setOut(new PrintStream(out));
      String[] args = {"-s=http://localhost:7472", "generate-completion"};
      CommandLine cmd = jhiCli.buildCommandLine(args);
      int exitCode = cmd.execute(args);
      assertThat(exitCode).isZero();
      assertThat(out.toString()).doesNotContain("_picocli_jhicli_module_apply_test2");
      assertThat(out.toString()).contains("_picocli_jhicli_module_apply_module1");
    } finally {
      System.setErr(oldErr);
      System.setOut(oldOut);
    }
  }

  @Test
  void shouldDisplayVerbose() {
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream oldErr = System.err;
    PrintStream oldOut = System.out;
    ModuleToApply moduleToApply = new ModuleToApply("/tmp/test", new HashMap<String, Object>());
    Mockito.when(moduleService.list("http://localhost:7472")).thenReturn(getOtherModules());
    try {
      System.setErr(new PrintStream(err));
      System.setOut(new PrintStream(out));
      String[] args = {"-v", "-s=http://localhost:7472","module", "list"};
      CommandLine cmd = jhiCli.buildCommandLine(args);
      int exitCode = cmd.execute(args);
      assertThat(exitCode).isZero();
      assertThat(out.toString()).contains("Module from Server : ");
    } finally {
      System.setErr(oldErr);
      System.setOut(oldOut);
    }
  }

  private List<String> getModule() {
    return List.of("test1", "test2");
  }

  private RestModules getRestModules() {
    Collection<RestCategory> categories = new ArrayList<>();
    Collection<RestModule> modules = new ArrayList<>();
    Collection<RestModuleProperty> moduleProperties = new ArrayList<>();
    RestModuleProperty moduleProperty = new RestModuleProperty(ModulePropertyType.STRING, false, "baseName", "basename description", "basenameExample");
    moduleProperties.add(moduleProperty);
    moduleProperties.add(new RestModuleProperty(ModulePropertyType.INTEGER, true, "numbercpu", "number of cpu", "2"));
    moduleProperties.add(new RestModuleProperty(ModulePropertyType.BOOLEAN, true, "enabled", "", ""));
    RestModuleProperties properties = new RestModuleProperties(moduleProperties);

    RestModule module1 = new RestModule("test", "description test", properties);
    RestModule module2 = new RestModule("test2", "description test2", properties);
    modules.add(module1);
    modules.add(module2);

    RestCategory category = new RestCategory("spring", modules);
    categories.add(category);
    RestModules response = new RestModules(categories);
    return response;
  }

  private Modules getOtherModules() {
    Collection<RestCategory> categories = new ArrayList<>();
    Collection<RestModule> modules = new ArrayList<>();
    Collection<RestModuleProperty> moduleProperties = new ArrayList<>();
    RestModuleProperty moduleProperty = new RestModuleProperty(ModulePropertyType.STRING, false, "baseName", "basename description", "basenameExample");
    moduleProperties.add(moduleProperty);
    moduleProperties.add(new RestModuleProperty(ModulePropertyType.INTEGER, true, "numbercpu", "number of cpu", "2"));
    moduleProperties.add(new RestModuleProperty(ModulePropertyType.BOOLEAN, true, "enabled", "", ""));
    RestModuleProperties properties = new RestModuleProperties(moduleProperties);

    RestModule module1 = new RestModule("module", "description module", properties);
    RestModule module2 = new RestModule("module1", "description module1", properties);
    modules.add(module1);
    modules.add(module2);

    RestCategory category = new RestCategory("spring", modules);
    categories.add(category);
    RestModules response = new RestModules(categories);
    return response.toDomain();
  }

  private Modules getModules() {

    return getRestModules().toDomain();
  }
}
