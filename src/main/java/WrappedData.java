import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class WrappedData {
  private Object value;
  private boolean sharedStrings;
  private boolean sharedProperties;
  private boolean rawBinary;

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public boolean isSharedStrings() {
    return sharedStrings;
  }

  public void setSharedStrings(boolean sharedStrings) {
    this.sharedStrings = sharedStrings;
  }

  public boolean isSharedProperties() {
    return sharedProperties;
  }

  public void setSharedProperties(boolean sharedProperties) {
    this.sharedProperties = sharedProperties;
  }

  public boolean isRawBinary() {
    return rawBinary;
  }

  public void setRawBinary(boolean rawBinary) {
    this.rawBinary = rawBinary;
  }

  public static WrappedData fromFile(File inputFile) throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    return objectMapper.readValue(inputFile, WrappedData.class);
  }

  @JsonIgnore
  public SmileOptions getSmileOptions() {
    final SmileOptions options = new SmileOptions();
    options.setSharedProperties(isSharedProperties());
    options.setSharedStrings(isSharedStrings());
    options.setRawBinary(isRawBinary());
    return options;
  }
}
