package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
    private static final Properties prop = new Properties();

    private static void load() {
        try(InputStream file = ReadProperties.class.getClassLoader().getResourceAsStream("application.properties"))
        {
            prop.load(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load the configuration file");
        }
    }

    public static String get(String key) {
        load();
        String value = prop.getProperty(key);

        return System.getenv(value);
    }
}
