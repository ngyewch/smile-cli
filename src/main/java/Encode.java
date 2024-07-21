import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.FileUtils;
import picocli.CommandLine;

@CommandLine.Command(name = "encode", description = "Encode JSON to SMILE.")
public class Encode implements Callable<Integer> {

  @CommandLine.Parameters(index = "0", description = "JSON file/directory")
  private File inputPath;

  @CommandLine.Parameters(index = "1", description = "SMILE file/directory")
  private File outputPath;

  @CommandLine.Option(
      names = {"--wrapped"},
      description = "Wrapped input")
  private boolean wrapped;

  @CommandLine.Option(
      names = {"--indented"},
      description = "Indented output")
  private boolean indented;

  @CommandLine.Option(
      names = {"--shared-properties"},
      description = "Shared properties")
  private Boolean sharedProperties;

  @CommandLine.Option(
      names = {"--shared-strings"},
      description = "Shared strings")
  private Boolean sharedStrings;

  @CommandLine.Option(
      names = {"--raw-binary"},
      description = "Raw binary")
  private Boolean rawBinary;

  @CommandLine.Option(
      names = {"-r", "--recursive"},
      description = "Recursive")
  private boolean recursive;

  @CommandLine.Option(
      names = {"--json-extension"},
      description = "JSON extension",
      defaultValue = "json")
  private String[] jsonExtensions = new String[] {"json"};

  @CommandLine.Option(
      names = {"--copy-source"},
      description = "Copy source file to output directory")
  private boolean copySource;

  @Override
  public Integer call() throws Exception {
    Utils.handleCommand(
        inputPath,
        outputPath,
        jsonExtensions,
        recursive,
        file -> {
          if (inputPath.isDirectory()) {
            final String relativePath = inputPath.toURI().relativize(file.toURI()).getPath();
            final File jsonOutputFile = new File(outputPath, relativePath);
            final File smileOutputFile = Utils.replaceExtension(jsonOutputFile, ".smile");
            encode(file, smileOutputFile, copySource ? jsonOutputFile : null);
          } else {
            final File smileOutputFile = outputPath;
            final File jsonOutputFile =
                Utils.replaceExtension(smileOutputFile, "." + jsonExtensions[0]);
            encode(file, smileOutputFile, copySource ? jsonOutputFile : null);
          }
        });
    return 0;
  }

  private WrappedData readJsonFile(File jsonInputFile) throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper();
    if (wrapped) {
      return objectMapper.readValue(jsonInputFile, WrappedData.class);
    } else {
      final Object o = objectMapper.readValue(jsonInputFile, Object.class);
      final WrappedData wrappedData = new WrappedData();
      wrappedData.setValue(o);
      return wrappedData;
    }
  }

  private void encode(File jsonInputFile, File smileOutputFile, File jsonOutputFile)
      throws IOException {
    final WrappedData wrappedData = readJsonFile(jsonInputFile);
    boolean optionsOverride = false;
    if (sharedProperties != null) {
      wrappedData.setSharedProperties(sharedProperties);
      optionsOverride = true;
    }
    if (sharedStrings != null) {
      wrappedData.setSharedStrings(sharedStrings);
      optionsOverride = true;
    }
    if (rawBinary != null) {
      wrappedData.setRawBinary(rawBinary);
      optionsOverride = true;
    }
    final SmileOptions smileOptions = wrappedData.getSmileOptions();
    final ObjectMapper smileObjectMapper = SmileUtils.newObjectMapper(smileOptions);
    smileOutputFile.getParentFile().mkdirs();
    smileObjectMapper.writeValue(smileOutputFile, wrappedData.getValue());
    if (jsonOutputFile != null) {
      jsonOutputFile.getParentFile().mkdirs();
      if (optionsOverride) {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, indented);
        objectMapper.writeValue(jsonOutputFile, wrappedData);
      } else {
        FileUtils.copyFile(jsonInputFile, jsonOutputFile);
      }
    }
  }
}
