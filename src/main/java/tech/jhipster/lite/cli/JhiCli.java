package tech.jhipster.lite.cli;

import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.OptionSpec;
import picocli.CommandLine.Option;
import tech.jhipster.lite.cli.domain.ModuleDomainService;
import tech.jhipster.lite.cli.domain.ModuleService;
import tech.jhipster.lite.cli.infrastructure.primary.CompletionCommand;
import tech.jhipster.lite.cli.infrastructure.primary.HelpOptions;
import tech.jhipster.lite.cli.infrastructure.primary.ModuleCommand;
import tech.jhipster.lite.cli.infrastructure.primary.ModuleListCommand;
import tech.jhipster.lite.cli.infrastructure.secondary.RestModulesRepository;
import tech.jhipster.lite.cli.util.Commands;
import tech.jhipster.lite.cli.util.Output;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command(name = "jhicli", mixinStandardHelpOptions = false, description = "CLI for jhipster lite", showDefaultValues = true)
public class JhiCli implements Callable<Integer> {

  @CommandLine.Mixin
  private HelpOptions options = new HelpOptions();

  @Option(names = {"-v", "--verbose"}, description = "Print verbose output", scope = CommandLine.ScopeType.INHERIT)
  public static boolean verbose;

  @Option(names = {"-s", "--server"}, description = "server host path", scope = CommandLine.ScopeType.INHERIT)
  public static String server = "http://localhost:7471";

  @CommandLine.Spec
  private CommandSpec spec;

  private ModuleService moduleService = new ModuleDomainService(new RestModulesRepository());

  @Override
  public Integer call() throws Exception {
    Output.printError("Choose a subcommand.");
    spec.commandLine().usage(System.out);
    return 1;
  }

  public static void main(String... args) throws IOException {
    AnsiConsole.systemInstall();

    CommandLine commandLine = new JhiCli().buildCommandLine();

    int exitCode = commandLine.execute(args);
    AnsiConsole.systemUninstall();
    System.exit(exitCode);
  }

  public CommandLine buildCommandLine() {
    CommandLine commandLine = new CommandLine(this);


    CommandSpec moduleCommand = CommandSpec.forAnnotatedObject(new ModuleCommand(moduleService));
    moduleCommand.addSubcommand("list", CommandSpec.forAnnotatedObject(new ModuleListCommand()));

    commandLine.addSubcommand(new CommandLine(moduleCommand)).addSubcommand(new CompletionCommand()).setCaseInsensitiveEnumValuesAllowed(Boolean.TRUE).setSubcommandsCaseInsensitive(Boolean.TRUE).setOptionsCaseInsensitive(Boolean.TRUE);

    CommandSpec rootApplySpec = CommandSpec.create().name("apply");
    CommandSpec rootDescribeSpec = CommandSpec.create().name("describe");

    moduleService.list(server).listModule().forEach(module -> {
      CommandSpec templateApply = Commands.getCommandApplySpec(moduleService, rootApplySpec, module);
      CommandSpec templateDescribe = Commands.getCommandDescribeSpec(moduleService, rootDescribeSpec, module);

      module.properties().forEach(p -> {
        OptionSpec.Builder builder = OptionSpec.builder("--".concat(p.key().get().toLowerCase())).description(p.description().get().description()).paramLabel("<" + p.key().get().toLowerCase() + ">").required(p.mandatory());

        switch (p.type()) {
          case STRING -> builder.type(String.class);
          case BOOLEAN -> builder.type(boolean.class);
          case INTEGER -> builder.type(int.class);
        }

        templateApply.addOption(builder.build());
      });

      templateApply.addOption(OptionSpec.builder("-h", "--help").usageHelp(true).descriptionKey("helpCommand.help").description("Show usage help for the help command and exit.").build());
      templateDescribe.addOption(OptionSpec.builder("-h", "--help").usageHelp(true).descriptionKey("helpCommand.help").description("Show usage help for the help command and exit.").build());

      rootApplySpec.addSubcommand(module.slug().slug(), templateApply).usageMessage(new CommandLine.Model.UsageMessageSpec().description("Apply a module"));
      rootDescribeSpec.addSubcommand(module.slug().slug(), templateDescribe).usageMessage(new CommandLine.Model.UsageMessageSpec().description("Describe a module"));
    });

    rootApplySpec.addOption(OptionSpec.builder("-p", "--project-path").type(String.class).required(true).paramLabel("project folder").description("Project server path.").scopeType(CommandLine.ScopeType.INHERIT).build());

    moduleCommand.addSubcommand("apply", rootApplySpec);
    moduleCommand.addSubcommand("describe", rootDescribeSpec);
    return commandLine;
  }


}

