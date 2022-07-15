package tech.jhipster.lite.cli.util;

import picocli.CommandLine;
import tech.jhipster.lite.cli.JhiCli;
import tech.jhipster.lite.cli.domain.Module;
import tech.jhipster.lite.cli.domain.ModuleService;
import tech.jhipster.lite.cli.domain.ModuleSlug;
import tech.jhipster.lite.cli.domain.ModuleToApply;
import tech.jhipster.lite.cli.error.domain.MissingMandatoryOptionException;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.Callable;

public class Commands {
  private Commands() {
  }

  public static CommandLine.Model.CommandSpec getCommandDescribeSpec(ModuleService moduleService, Module module) {
    return CommandLine.Model.CommandSpec.wrapWithoutInspection(
      (Callable<Integer>) () -> {
        Optional<Module> optionalModule = moduleService.list(JhiCli.server).listModule().stream().filter(m -> m.slug().slug().equals(module.slug().slug())).findFirst();
        Output.printVerbose("Server : " + JhiCli.server);
        //noinspection OptionalGetWithoutIsPresent
        Output.printInfo("Slug : " + optionalModule.get().slug().get());
        Output.printInfo("Description : " + optionalModule.get().description().get());
        Output.printTitle("---------");
        Output.printInfo("Properties : ");

        optionalModule.get().properties().forEach(p -> {
          Output.printTitle("---------");
          Output.printMessage("Key : " + p.key().get());
          Output.printMessage("Name : " + p.type().name());
          Output.printMessage("Mandatory : " + p.mandatory());
          if (p.description().isPresent()) {
            Output.printMessage("Description : " + p.description().get().get());
          }
          if (p.example().isPresent()) {
            Output.printMessage("Example : " + p.example().get().get());
          }
        });
        return 0;

      });
  }

  public static CommandLine.Model.CommandSpec getCommandApplySpec(ModuleService moduleService, CommandLine.Model.CommandSpec rootApplySpec, Module module) {
    return CommandLine.Model.CommandSpec.wrapWithoutInspection(
      (Callable<Integer>) () -> {
        var properties = new HashMap<String, Object>();
        CommandLine moduleCommandLine = rootApplySpec.subcommands().get(module.slug().slug());
        CommandLine.Model.CommandSpec spec = moduleCommandLine.getCommandSpec();

        var optionProjectPath = spec.options().stream().filter(m1 -> m1.longestName().equals("--project-path") && m1.getValue() != "").findFirst();

        if (!optionProjectPath.isPresent()){
          throw MissingMandatoryOptionException.forEmptyValue("--project-path");
        }
        ModuleToApply moduleToApply = new ModuleToApply(optionProjectPath.get().getValue(), properties);
        try {
          Output.printVerbose("Apply module : " + moduleCommandLine.getCommandName());
          Output.printVerbose("Options : " + moduleToApply);
          var modules = moduleService.apply(new ModuleSlug(moduleCommandLine.getCommandName()), moduleToApply);
          Output.printInfo(modules);
          return 0;
        } catch (Exception ex) {
          Output.printError(ex.getMessage());
          return 1;
        }

      });

  }
}
