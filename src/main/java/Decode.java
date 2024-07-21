import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import org.apache.commons.io.FileUtils;
import picocli.CommandLine;

@CommandLine.Command(name = "decode", description = "Decode SMILE to JSON.")
public class Decode implements Callable<Integer> {

  @CommandLine.Parameters(index = "0", description = "SMILE file/directory")
  private File inputPath;

  @CommandLine.Parameters(index = "1", description = "JSON file/directory")
  private File outputPath;

  @CommandLine.Option(
      names = {"--wrapped"},
      description = "Wrapped output")
  private boolean wrapped;

  @CommandLine.Option(
      names = {"--indented"},
      description = "Indented output")
  private boolean indented;

  @CommandLine.Option(
      names = {"-r", "--recursive"},
      description = "Recursive")
  private boolean recursive;

  @CommandLine.Option(
      names = {"--smile-extension"},
      description = "SMILE extension",
      defaultValue = "smile")
  private String[] smileExtensions = new String[] {"smile"};

  @CommandLine.Option(
      names = {"--copy-source"},
      description = "Copy source file to output directory")
  private boolean copySource;

  @Override
  public Integer call() throws Exception {
    Utils.handleCommand(inputPath, outputPath, smileExtensions, recursive, file -> {
        if (inputPath.isDirectory()) {
            final String relativePath = inputPath.toURI().relativize(file.toURI()).getPath();
            final File smileOutputFile = new File(outputPath, relativePath);
            final File jsonOutputFile = Utils.replaceExtension(smileOutputFile, ".json");
            decode(file, jsonOutputFile, copySource ? smileOutputFile : null);
        } else {
            final File jsonOutputFile = outputPath;
            final File smileOutputFile = Utils.replaceExtension(jsonOutputFile, "." + smileExtensions[0]);
            decode(file, jsonOutputFile, copySource ? smileOutputFile : null);
        }
    });
    return 0;
  }

  private void decode(File smileInputFile, File jsonOutputFile, File smileOutputFile)
      throws IOException {
    final ObjectMapper smileObjectMapper = SmileUtils.newObjectMapper();
    Object o = smileObjectMapper.readValue(smileInputFile, Object.class);
    if (wrapped) {
      final WrappedData wrappedData = new WrappedData();
      wrappedData.setValue(o);
      final SmileOptions options = SmileOptions.fromFile(smileInputFile);
      wrappedData.setSharedProperties(options.isSharedProperties());
      wrappedData.setSharedStrings(options.isSharedStrings());
      wrappedData.setRawBinary(options.isRawBinary());
      o = wrappedData;
    }
    final ObjectMapper jsonObjectMapper = new ObjectMapper();
    jsonObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, indented);
    jsonOutputFile.getParentFile().mkdirs();
    jsonObjectMapper.writeValue(jsonOutputFile, o);
    if (smileOutputFile != null) {
      smileOutputFile.getParentFile().mkdirs();
      FileUtils.copyFile(smileInputFile, smileOutputFile);
    }
  }
}
