package tech.jhipster.lite.cli.util;

import picocli.CommandLine;
import tech.jhipster.lite.cli.JhiCli;

public final class Output {
  private Output() {
  }

  public static void printTitle(String title) {
    System.out.println(CommandLine.Help.Ansi.AUTO.string("@|green,bold " + title + "|@"));
  }

  public static void printError(String message) {
    System.err.println(CommandLine.Help.Ansi.AUTO.string("@|red,bold " + message + "|@"));
  }

  public static void printInfo(String message) {
    System.out.println(CommandLine.Help.Ansi.AUTO.string("@|yellow " + message + "|@"));
  }

  public static void printMessage(String message) {
    System.out.println(CommandLine.Help.Ansi.AUTO.string(message));
  }

  public static void printVerbose(String message) {
    if (JhiCli.verbose) {
      System.out.println(CommandLine.Help.Ansi.AUTO.string("@|italic " + message + "|@"));
    }
  }
  public static void printUsage( CommandLine.Model.CommandSpec spec) {
    spec.commandLine().usage(System.out);
  }
}
