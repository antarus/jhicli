package tech.jhipster.lite.cli.infrastructure.primary;

import picocli.AutoComplete;
import picocli.CommandLine;

@CommandLine.Command(
  name = "generate-completion",
  version = {"generate-completion 4.6.3"},
  mixinStandardHelpOptions = false,
  description = {"Generate bash/zsh completion script for ${ROOT-COMMAND-NAME:-the root command of this command}.", "Run the following command to give `${ROOT-COMMAND-NAME:-$PARENTCOMMAND}` TAB completion in the current shell:", "", "  source <(${PARENT-COMMAND-FULL-NAME:-$PARENTCOMMAND} ${COMMAND-NAME})", ""},
  optionListHeading = "Options:%n",
  helpCommand = true
)
public class CompletionCommand implements Runnable {

  @CommandLine.Spec
  CommandLine.Model.CommandSpec spec;

  public void run() {
    String script = AutoComplete.bash(this.spec.root().name(), this.spec.root().commandLine());
    this.spec.commandLine().getOut().print(script);
    this.spec.commandLine().getOut().print('\n');
    this.spec.commandLine().getOut().flush();
  }
}

