package tech.jhipster.lite.cli.technical.infrastructure.primary;

import picocli.CommandLine;
import tech.jhipster.lite.cli.JhiCli;
import tech.jhipster.lite.cli.util.Output;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "list", description = "List available modules", subcommands = CommandLine.HelpCommand.class)
public class ModuleListCommand implements Callable<Integer> {

  @CommandLine.ParentCommand
  private ModuleCommand cli;


//  @Autowired
//  private ModuleService moduleService;

//  public ModuleListCommand(ModuleService moduleService) {
//    this.moduleService = moduleService;
//  }

  @Override
  public Integer call() {
//    cli.
    var modules= cli.moduleService().list(JhiCli.host);
//    Output.printMessage(modules.listModules().stream().collect(Collectors.joining(" ")));
    modules.listModules().forEach(module -> {
      Output.printMessage(module);
    });
//
//    RestTemplate restTemplate = new RestTemplate();
//
//    String test = restTemplate.getForObject(server + "/api/modules", String.class);
//    System.out.printf(test);
//    Modules result = restTemplate.getForObject(server + "/api/modules", Modules.class);
//
//    result.listModules().forEach(module -> {
//      Output.printMessage(module);
//    });

    return 0;
  }

}
