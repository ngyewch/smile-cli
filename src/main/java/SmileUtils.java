import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.dataformat.smile.SmileGenerator;

public class SmileUtils {
  public static SmileFactory newSmileFactory(SmileOptions options) {
    final SmileFactory smileFactory = new SmileFactory();
    smileFactory.configure(SmileGenerator.Feature.CHECK_SHARED_NAMES, options.isSharedProperties());
    smileFactory.configure(
        SmileGenerator.Feature.CHECK_SHARED_STRING_VALUES, options.isSharedStrings());
    smileFactory.configure(SmileGenerator.Feature.ENCODE_BINARY_AS_7BIT, !options.isRawBinary());
    return smileFactory;
  }

  public static ObjectMapper newObjectMapper(SmileOptions options) {
    return new ObjectMapper(newSmileFactory(options));
  }

  public static ObjectMapper newObjectMapper() {
    return new ObjectMapper(new SmileFactory());
  }
}
