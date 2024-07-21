import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
    name = "smile-cli",
    subcommands = {
      Decode.class,
      Encode.class,
    })
public class Main implements Callable<Integer> {
  public static void main(String... args) {
    int exitCode = new CommandLine(new Main()).execute(args);
    System.exit(exitCode);
  }

  @Override
  public Integer call() throws Exception {
    return 0;
  }
}