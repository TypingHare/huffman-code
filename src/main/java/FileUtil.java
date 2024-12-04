import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    /**
     * Outputs a byte array to a file.
     * @param byteArray The byte array to output.
     * @param filepath  The path of the output file.
     */
    public static void outputByteArray(final byte[] byteArray, final String filepath) {
        try (final FileOutputStream fileOutputStream = new FileOutputStream(filepath)) {
            fileOutputStream.write(byteArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read all bytes from a text file.
     * @param filepath the file to read.
     * @return a byte (ASCII character) array.
     */
    public static byte[] readFile(final String filepath) {
        try (final FileInputStream fileInputStream = new FileInputStream(filepath)) {
            // Read all bytes from the given file input stream
            return fileInputStream.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
