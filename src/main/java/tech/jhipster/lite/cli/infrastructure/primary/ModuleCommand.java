package tech.jhipster.lite.cli.technical.infrastructure.primary;

import picocli.CommandLine;
import tech.jhipster.lite.cli.module.domain.ModuleService;
import tech.jhipster.lite.cli.util.Output;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "module", description = "Manage module", subcommands = {CommandLine.HelpCommand.class, ModuleListCommand.class, ModuleApplyCommand.class})
public record ModuleCommand(ModuleService moduleService) implements Callable<Integer> {

  @Override
  public Integer call() {
    Output.printError("Choose a subcommand.");
    return 1;
  }

}
