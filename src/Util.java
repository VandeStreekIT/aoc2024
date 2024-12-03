import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Util {
    public static String readFile(String filePath) {
        String content = null;
        try {
            // Lees het bestand als één enkele string
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}