package tech.jhipster.lite.cli.util;

import picocli.CommandLine;
import tech.jhipster.lite.cli.JhiCli;
import tech.jhipster.lite.cli.domain.Module;
import tech.jhipster.lite.cli.domain.ModuleService;
import tech.jhipster.lite.cli.domain.ModuleSlug;
import tech.jhipster.lite.cli.domain.ModuleToApply;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.Callable;

public class Commands {
  private Commands() {
  }

  public static CommandLine.Model.CommandSpec getCommandDescribeSpec(ModuleService moduleService, CommandLine.Model.CommandSpec rootDescribeSpec, Module module) {
    return CommandLine.Model.CommandSpec.wrapWithoutInspection(
      (Callable<Integer>) () -> {
        var properties = new HashMap<String, Object>();
        CommandLine moduleCommandLine = rootDescribeSpec.subcommands().get(module.slug().slug());
        CommandLine.Model.CommandSpec spec = moduleCommandLine.getCommandSpec().parent();


        Optional<Module> optionalModule = moduleService.list(JhiCli.server).listModule().stream().filter(m -> m.slug().slug().equals(module.slug().slug())).findFirst();
        if (optionalModule.isEmpty()) {
          throw new IllegalArgumentException("the module %s is not found".formatted(module));
        }
        Output.printMessage("Slug : " + optionalModule.get().slug().slug());
        Output.printMessage("Description : " + optionalModule.get().description().description());
        Output.printTitle("---------");
        Output.printMessage("Properties : ");

        optionalModule.get().properties().forEach(p -> {
          Output.printTitle("---------");
          Output.printMessage("Key : " + p.key().key());
          Output.printMessage("Name : " + p.type().name());
          Output.printMessage("Mandatory : " + p.mandatory());
          if (p.description().isPresent()) {
            Output.printMessage("Description : " + p.description().get().description());
          }
          if (p.example().isPresent()) {
            Output.printMessage("Example : " + p.example().get().example());
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

        var optionProjectPath = spec.options().stream().filter(m1 -> m1.longestName().equals("--project-path") && m1.getValue() != null).findFirst();

        if (optionProjectPath.isPresent()) {
          ModuleToApply moduleToApply = new ModuleToApply(optionProjectPath.get().getValue(), properties);
          try {
            var modules = moduleService.apply(new ModuleSlug(moduleCommandLine.getCommandName()), moduleToApply);
            Output.printMessage(modules);
            return 0;
          } catch (Exception ex) {
            Output.printError(ex.getMessage());
            return 1;
          }
        } else {
          Output.printError("Option -p or --project-path is mandatory");
          return 1;
        }
      });

  }
}
