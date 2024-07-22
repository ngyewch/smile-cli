import com.fasterxml.jackson.annotation.JsonIgnore;

public class WrappedData<T> {
  private T value;
  private boolean sharedStrings;
  private boolean sharedProperties;
  private boolean rawBinary;

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
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

  @JsonIgnore
  public SmileOptions getSmileOptions() {
    final SmileOptions options = new SmileOptions();
    options.setSharedProperties(isSharedProperties());
    options.setSharedStrings(isSharedStrings());
    options.setRawBinary(isRawBinary());
    return options;
  }
}
