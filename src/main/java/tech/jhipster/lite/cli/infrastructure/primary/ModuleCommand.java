package tech.jhipster.lite.cli.infrastructure.primary;

import picocli.CommandLine;
import tech.jhipster.lite.cli.domain.ModuleService;
import tech.jhipster.lite.cli.util.Output;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "module", description = "Manage module")
public class ModuleCommand implements Callable<Integer> {

  private final ModuleService moduleService;
  public ModuleCommand(ModuleService moduleService) {
    this.moduleService = moduleService;
  }
  @CommandLine.Mixin
  private HelpOptions help= new HelpOptions();

  @CommandLine.Spec
  private  CommandLine.Model.CommandSpec spec;

  @Override
  public Integer call() {
    Output.printError("Choose a subcommand.");
    spec.commandLine().usage(System.out);
    return 1;
  }

  public ModuleService getModuleService() {
    return moduleService;
  }
}
