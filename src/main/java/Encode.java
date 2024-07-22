import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
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

  private static ObjectMapper newObjectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    return objectMapper;
  }

  private WrappedData<BigDecimal> readJsonFileAsBigDecimal(File jsonInputFile) throws IOException {
    final ObjectMapper objectMapper = newObjectMapper();
    if (wrapped) {
      final TypeReference<WrappedData<BigDecimal>> typeRef = new TypeReference<>() {};
      return objectMapper.readValue(jsonInputFile, typeRef);
    } else {
      final TypeReference<BigDecimal> typeRef = new TypeReference<>() {};
      final BigDecimal value = objectMapper.readValue(jsonInputFile, typeRef);
      final WrappedData<BigDecimal> wrappedData = new WrappedData<>();
      wrappedData.setValue(value);
      return wrappedData;
    }
  }

  private WrappedData<BigInteger> readJsonFileAsBigInteger(File jsonInputFile) throws IOException {
    final ObjectMapper objectMapper = newObjectMapper();
    if (wrapped) {
      final TypeReference<WrappedData<BigInteger>> typeRef = new TypeReference<>() {};
      return objectMapper.readValue(jsonInputFile, typeRef);
    } else {
      final TypeReference<BigInteger> typeRef = new TypeReference<>() {};
      final BigInteger value = objectMapper.readValue(jsonInputFile, typeRef);
      final WrappedData<BigInteger> wrappedData = new WrappedData<>();
      wrappedData.setValue(value);
      return wrappedData;
    }
  }

  private WrappedData<byte[]> readJsonFileAsBinary(File jsonInputFile) throws IOException {
    final ObjectMapper objectMapper = newObjectMapper();
    if (wrapped) {
      final TypeReference<WrappedData<byte[]>> typeRef = new TypeReference<>() {};
      return objectMapper.readValue(jsonInputFile, typeRef);
    } else {
      final TypeReference<byte[]> typeRef = new TypeReference<>() {};
      final byte[] value = objectMapper.readValue(jsonInputFile, typeRef);
      final WrappedData<byte[]> wrappedData = new WrappedData<>();
      wrappedData.setValue(value);
      return wrappedData;
    }
  }

  private WrappedData<Boolean> readJsonFileAsBoolean(File jsonInputFile) throws IOException {
    final ObjectMapper objectMapper = newObjectMapper();
    if (wrapped) {
      final TypeReference<WrappedData<Boolean>> typeRef = new TypeReference<>() {};
      return objectMapper.readValue(jsonInputFile, typeRef);
    } else {
      final TypeReference<Boolean> typeRef = new TypeReference<>() {};
      final Boolean value = objectMapper.readValue(jsonInputFile, typeRef);
      final WrappedData<Boolean> wrappedData = new WrappedData<>();
      wrappedData.setValue(value);
      return wrappedData;
    }
  }

  private WrappedData<Double> readJsonFileAsDouble(File jsonInputFile) throws IOException {
    final ObjectMapper objectMapper = newObjectMapper();
    if (wrapped) {
      final TypeReference<WrappedData<Double>> typeRef = new TypeReference<>() {};
      return objectMapper.readValue(jsonInputFile, typeRef);
    } else {
      final TypeReference<Double> typeRef = new TypeReference<>() {};
      final Double value = objectMapper.readValue(jsonInputFile, typeRef);
      final WrappedData<Double> wrappedData = new WrappedData<>();
      wrappedData.setValue(value);
      return wrappedData;
    }
  }

  private WrappedData<Float> readJsonFileAsFloat(File jsonInputFile) throws IOException {
    final ObjectMapper objectMapper = newObjectMapper();
    if (wrapped) {
      final TypeReference<WrappedData<Float>> typeRef = new TypeReference<>() {};
      return objectMapper.readValue(jsonInputFile, typeRef);
    } else {
      final TypeReference<Float> typeRef = new TypeReference<>() {};
      final Float value = objectMapper.readValue(jsonInputFile, typeRef);
      final WrappedData<Float> wrappedData = new WrappedData<>();
      wrappedData.setValue(value);
      return wrappedData;
    }
  }

  private WrappedData<Integer> readJsonFileAsInteger(File jsonInputFile) throws IOException {
    final ObjectMapper objectMapper = newObjectMapper();
    if (wrapped) {
      final TypeReference<WrappedData<Integer>> typeRef = new TypeReference<>() {};
      return objectMapper.readValue(jsonInputFile, typeRef);
    } else {
      final TypeReference<Integer> typeRef = new TypeReference<>() {};
      final Integer value = objectMapper.readValue(jsonInputFile, typeRef);
      final WrappedData<Integer> wrappedData = new WrappedData<>();
      wrappedData.setValue(value);
      return wrappedData;
    }
  }

  private WrappedData<Long> readJsonFileAsLong(File jsonInputFile) throws IOException {
    final ObjectMapper objectMapper = newObjectMapper();
    if (wrapped) {
      final TypeReference<WrappedData<Long>> typeRef = new TypeReference<>() {};
      return objectMapper.readValue(jsonInputFile, typeRef);
    } else {
      final TypeReference<Long> typeRef = new TypeReference<>() {};
      final Long value = objectMapper.readValue(jsonInputFile, typeRef);
      final WrappedData<Long> wrappedData = new WrappedData<>();
      wrappedData.setValue(value);
      return wrappedData;
    }
  }

  private WrappedData<String> readJsonFileAsString(File jsonInputFile) throws IOException {
    final ObjectMapper objectMapper = newObjectMapper();
    if (wrapped) {
      final TypeReference<WrappedData<String>> typeRef = new TypeReference<>() {};
      return objectMapper.readValue(jsonInputFile, typeRef);
    } else {
      final TypeReference<String> typeRef = new TypeReference<>() {};
      final String value = objectMapper.readValue(jsonInputFile, typeRef);
      final WrappedData<String> wrappedData = new WrappedData<>();
      wrappedData.setValue(value);
      return wrappedData;
    }
  }

  private WrappedData<Object> readJsonFile(File jsonInputFile) throws IOException {
    final ObjectMapper objectMapper = newObjectMapper();
    if (wrapped) {
      final TypeReference<WrappedData<Object>> typeRef = new TypeReference<>() {};
      return objectMapper.readValue(jsonInputFile, typeRef);
    } else {
      final TypeReference<Object> typeRef = new TypeReference<>() {};
      final Object value = objectMapper.readValue(jsonInputFile, typeRef);
      final WrappedData<Object> wrappedData = new WrappedData<>();
      wrappedData.setValue(value);
      return wrappedData;
    }
  }

  private void encode(File jsonInputFile, File smileOutputFile, File jsonOutputFile)
      throws IOException {
    final WrappedData wrappedData;
    if (jsonInputFile.getParentFile().getName().equals("big_decimal")) {
      wrappedData = readJsonFileAsBigDecimal(jsonInputFile);
    } else if (jsonInputFile.getParentFile().getName().equals("big_integer")) {
      wrappedData = readJsonFileAsBigInteger(jsonInputFile);
    } else if (jsonInputFile.getParentFile().getName().equals("binary")) {
      wrappedData = readJsonFileAsBinary(jsonInputFile);
    } else if (jsonInputFile.getParentFile().getName().equals("boolean")) {
      wrappedData = readJsonFileAsBoolean(jsonInputFile);
    } else if (jsonInputFile.getParentFile().getName().equals("double")) {
      wrappedData = readJsonFileAsDouble(jsonInputFile);
    } else if (jsonInputFile.getParentFile().getName().equals("float")) {
      wrappedData = readJsonFileAsFloat(jsonInputFile);
    } else if (jsonInputFile.getParentFile().getName().equals("integer")) {
      wrappedData = readJsonFileAsInteger(jsonInputFile);
    } else if (jsonInputFile.getParentFile().getName().equals("long")) {
      wrappedData = readJsonFileAsLong(jsonInputFile);
    } else if (jsonInputFile.getParentFile().getName().equals("string")) {
      wrappedData = readJsonFileAsString(jsonInputFile);
    } else {
      wrappedData = readJsonFile(jsonInputFile);
    }
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
