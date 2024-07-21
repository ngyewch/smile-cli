import java.io.*;

public class SmileOptions {
  private boolean sharedStrings;
  private boolean sharedProperties;
  private boolean rawBinary;

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

  public static SmileOptions fromFile(File inputFile) throws IOException {
    try (final InputStream inputStream = new FileInputStream(inputFile)) {
      final byte[] headerBytes = new byte[4];
      final int readLen = inputStream.read(headerBytes);
      if (readLen != 4) {
        throw new IllegalArgumentException("invalid SMILE file");
      }
      if ((headerBytes[0] != ':') || (headerBytes[1] != ')') || (headerBytes[2] != '\n')) {
        throw new IllegalArgumentException("invalid SMILE file");
      }
      final SmileOptions options = new SmileOptions();
      options.setSharedProperties((headerBytes[3] & 0x01) == 0x01);
      options.setSharedStrings((headerBytes[3] & 0x02) == 0x02);
      options.setRawBinary((headerBytes[3] & 0x04) == 0x04);
      return options;
    }
  }
}
