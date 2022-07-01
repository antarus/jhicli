package tech.jhipster.lite.cli.infrastructure.primary;

import picocli.CommandLine;
import tech.jhipster.lite.cli.JhiCli;
import tech.jhipster.lite.cli.util.Output;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "list", description = "List available modules")
public class ModuleListCommand implements Callable<Integer> {

  @CommandLine.ParentCommand
  private ModuleCommand cli;

  @CommandLine.Mixin
  private HelpOptions help= new HelpOptions();

  @Override
  public Integer call() {

    var modules= cli.getModuleService().list(JhiCli.server);
    modules.listModules().forEach(Output::printMessage);

    return 0;
  }

}
