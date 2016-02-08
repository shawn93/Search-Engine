import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Demonstrates how to use {@link OpenOption} objects to change how a
 * file is opened and written to.
 *
 * <p><em>
 * Note that this class is designed to illustrate a specific concept, and
 * may not be an example of proper class design outside of this context.
 * </em></p>
 */
public class AppendDemo {

	public static void main(String[] args) throws IOException {
		try (
			BufferedWriter writer = Files.newBufferedWriter(
					Paths.get("append.txt"),
					Charset.forName("UTF-8"),
					StandardOpenOption.APPEND,	// append instead of replace
					StandardOpenOption.CREATE,	// create if does not exist
					StandardOpenOption.WRITE);	// open for write access
		) {
			writer.write("Hello world!");
			writer.newLine();
			writer.flush();
		}
	}
}
