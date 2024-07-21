import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class Utils {
  public static File replaceExtension(File file, String newExtension) {
    return new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName()) + newExtension);
  }

  public static void handleCommand(
      File inputPath,
      File outputPath,
      String[] inputExtensions,
      boolean recursive,
      FileHandler fileHandler)
      throws IOException {
    if (inputPath.isDirectory()) {
      if (outputPath.exists() && !outputPath.isDirectory()) {
        throw new IllegalArgumentException(
            "outputPath must be a directory when inputPath is a directory");
      }
      final Collection<File> inputFiles =
          FileUtils.listFiles(inputPath, inputExtensions, recursive);
      for (final File inputFile : inputFiles) {
        fileHandler.handle(inputFile);
      }
    } else {
      if (outputPath.isDirectory()) {
        throw new IllegalArgumentException(
            "outputPath must not be a directory when inputPath is a file");
      }
      fileHandler.handle(inputPath);
    }
  }

  public interface FileHandler {
    void handle(File file) throws IOException;
  }
}
