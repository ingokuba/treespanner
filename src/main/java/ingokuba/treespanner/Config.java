package ingokuba.treespanner;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import lombok.SneakyThrows;

public class Config
{

    public static final String  MAX_ITEMS   = "MAX_ITEMS";
    public static final String  MAX_IDENT   = "MAX_IDENT";
    public static final String  MAX_COST    = "MAX_COST";
    public static final String  MAX_NODE_ID = "MAX_NODE_ID";

    private static final Logger LOGGER      = Logger.getLogger(Config.class.getName());

    private Properties          properties;

    private Config()
        throws IOException
    {
        properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("Treespanner.properties"));
    }

    @SneakyThrows(IOException.class)
    public static Config config()
    {
        return new Config();
    }

    @SuppressWarnings("unchecked")
    public <T extends Object> T getProperty(String key, Class<T> type)
    {
        String property = getProperty(key);
        if (property == null) {
            return null;
        }
        if (type.isAssignableFrom(Integer.class)) {
            try {
                return (T)Integer.valueOf(property);
            } catch (NumberFormatException nfe) {
                LOGGER.warning("Property '%s' is not of type '%s'.");
                return null;
            }
        }
        return (T)property;
    }

    public String getProperty(String key)
    {
        return properties.getProperty(key);
    }
}
