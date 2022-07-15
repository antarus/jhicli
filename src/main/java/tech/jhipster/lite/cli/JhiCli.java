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

import java.util.concurrent.Callable;

@Command(name = "jhicli", description = "CLI for jhipster lite", showDefaultValues = true)
public class JhiCli implements Callable<Integer> {

  @CommandLine.Mixin
  private final HelpOptions options = new HelpOptions();

  @Option(names = {"-v", "--verbose"}, description = "Print verbose output", scope = CommandLine.ScopeType.LOCAL)
  public static boolean verbose;

  @Option(names = {"-s", "--server"}, description = "server host path", scope = CommandLine.ScopeType.LOCAL)
  public static String server = "http://localhost:7471";

  @CommandLine.Spec
  private CommandSpec spec;

  private final ModuleService moduleService ;

  private static JhiCli jhicli= new JhiCli(new ModuleDomainService(new RestModulesRepository()));

  @Override
  public Integer call() {
    Output.printError("Choose a subcommand");
    Output.printUsage(spec);
    return 1;
  }

  public JhiCli(ModuleService moduleService) {
    this.moduleService = moduleService;
  }

  @ExcludeFromJacocoGeneratedReport
  public static void main(String... args){
    AnsiConsole.systemInstall();

    CommandLine commandLine = jhicli.buildCommandLine(args);

    int exitCode = commandLine.execute(args);
    AnsiConsole.systemUninstall();
    System.exit(exitCode);
  }

  public CommandLine buildCommandLine(String... args) {
    CommandLine commandLine = new CommandLine(this);

    CommandSpec moduleCommand = CommandSpec.forAnnotatedObject(new ModuleCommand(moduleService));
    CommandSpec moduleListCommand= CommandSpec.forAnnotatedObject(new ModuleListCommand());
    moduleCommand.addSubcommand("list", moduleListCommand);

    commandLine.addSubcommand(new CommandLine(moduleCommand)).addSubcommand(new CompletionCommand()).setCaseInsensitiveEnumValuesAllowed(Boolean.TRUE).setSubcommandsCaseInsensitive(Boolean.TRUE).setOptionsCaseInsensitive(Boolean.TRUE);

    this.affectOptionBase(commandLine, moduleCommand, moduleListCommand, args);

    CommandSpec rootApplySpec = CommandSpec.create().name("apply");
    CommandSpec rootDescribeSpec = CommandSpec.create().name("describe");

    moduleService.list(server).listModule().forEach(module -> {
      CommandSpec templateApply = Commands.getCommandApplySpec(moduleService, rootApplySpec, module);
      CommandSpec templateDescribe = Commands.getCommandDescribeSpec(moduleService, module);

      module.properties().forEach(p -> {
        OptionSpec.Builder builder = OptionSpec.builder("--".concat(p.key().get().toLowerCase()));
        if (p.description().isPresent()) {
          builder.description(p.description().get().description());
        }
        builder.paramLabel("<" + p.key().get().toLowerCase() + ">").required(p.mandatory());

        switch (p.type()) {
          case BOOLEAN -> builder.type(boolean.class);
          case INTEGER -> builder.type(int.class);
          default -> builder.type(String.class);
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

  private void affectOptionBase(CommandLine commandLine, CommandSpec moduleCommand, CommandSpec moduleListCommand, String[] args) {
    moduleListCommand.parser().unmatchedArgumentsAllowed(true);
    moduleCommand.parser().unmatchedArgumentsAllowed(true);
    commandLine.getCommandSpec().parser().unmatchedArgumentsAllowed(true);
    commandLine.parseArgs(args);
    moduleListCommand.parser().unmatchedArgumentsAllowed(false);
    moduleCommand.parser().unmatchedArgumentsAllowed(false);
    commandLine.getCommandSpec().parser().unmatchedArgumentsAllowed(false);
  }


}

