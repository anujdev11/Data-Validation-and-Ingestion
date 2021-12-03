package app.data_ingestion.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    final static String appConfigPath = ".//src//main//resources//app_config.properties";
    static Properties property;

    /**
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        if (property == null) {
            try (InputStream input = new FileInputStream(appConfigPath)) {
                property = new Properties();
                property.load(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return property.getProperty(key);

    }

}
