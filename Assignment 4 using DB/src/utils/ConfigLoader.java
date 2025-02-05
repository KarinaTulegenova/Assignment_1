import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    public static Properties loadConfig() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInput = new FileInputStream("assets/config.properties");
            properties.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
