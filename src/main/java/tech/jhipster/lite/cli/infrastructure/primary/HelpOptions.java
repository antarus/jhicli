package tech.jhipster.lite.cli.infrastructure.primary;

import picocli.CommandLine;

public class HelpOptions {
  @CommandLine.Option(
    names = {"-h", "--help"},
    usageHelp = true,
    descriptionKey = "helpCommand.help",
    description = {"Show usage help for the help command and exit."}
  )
  private boolean help;
}

