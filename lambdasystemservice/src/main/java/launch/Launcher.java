package launch;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {

    private final static String CONFIG_PROPERTIES_FILE = "config.xml";

    private static final Logger logger = Logger.getLogger(Launcher.class);


    private static XMLConfiguration xmlConfiguration;

    static {
        Configurations configs = new Configurations();
        try {
            xmlConfiguration = configs.xml(CONFIG_PROPERTIES_FILE);
        } catch (ConfigurationException e) {
            logger.error("Cannot read configurations !", e);
        }
    }

    private static final ExecutorService service = Executors.newFixedThreadPool(getIntValue("launcher.threads"));

    public static String getStringValue(String tag) {
        return xmlConfiguration.getString(tag);
    }

    public static int getIntValue(String tag) {

        return xmlConfiguration.getInt(tag);
    }


    public static void main(String[] args) {
        logger.info("Starting HTTP Transport service");
        service.submit(new HttpServerLauncher());
    }
}
