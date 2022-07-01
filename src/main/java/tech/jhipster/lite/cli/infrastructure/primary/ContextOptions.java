package tech.jhipster.lite.cli.infrastructure.primary;

import picocli.CommandLine;

public class ContextOptions {


  @CommandLine.Option(names = {"-p", "--project-path"}, paramLabel = "project folder", description = "Project server path", scope = CommandLine.ScopeType.INHERIT)
  public String project_folder;
}

