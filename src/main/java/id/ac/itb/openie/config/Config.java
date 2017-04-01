package id.ac.itb.openie.config;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * Created by elvanowen on 2/22/17.
 */
public class Config {

    private Properties properties = new Properties();
    private InputStream input = null;
    private final static String DEFAULT_CONFIG_FILEPATH = "config/config.properties";

    public Config() {
        this(DEFAULT_CONFIG_FILEPATH);
    }

    public Config(String configFilePath) {
        try {
            input = new FileInputStream(configFilePath);

            // load a properties file
            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
