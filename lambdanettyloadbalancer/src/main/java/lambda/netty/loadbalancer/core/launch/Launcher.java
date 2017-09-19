package lambda.netty.loadbalancer.core.launch;

import lambda.netty.loadbalancer.core.Server;
import lambda.netty.loadbalancer.core.etcd.EtcdClientException;
import lambda.netty.loadbalancer.core.etcd.EtcdUtil;
import lambda.netty.loadbalancer.core.loadbalance.StateImplJsonHelp;
import lambda.netty.loadbalancer.core.loadbalance.statemodels.InstanceStates;
import lambda.netty.loadbalancer.core.loadbalance.statemodels.State;
import lambda.netty.loadbalancer.core.loadbalance.statemodels.StateImpl;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    public static String getStringValue(String tag) {
        return xmlConfiguration.getString(tag);
    }

    public static int getIntValue(String tag) {

        return xmlConfiguration.getInt(tag);
    }

    public static List<String> getStringValues(String key) {
        Object obj = xmlConfiguration.getProperty(key);
        if (obj instanceof List) {
            return (List) obj;
        }
        return null;
    }

    public static List<Integer> getIntValues(String key) {
        Object obj = xmlConfiguration.getProperty(key);

        if (obj instanceof List) {
            List tmp = (List) obj;
            List<Integer> tmp_return = new ArrayList<>(tmp.size());
            tmp.forEach(x->tmp_return.add(Integer.parseInt((String) x)));

            return tmp_return;
        }
        return null;
    }

    public static boolean getBooleanValue(String key){
        String val = getStringValue(key);

        return val.equals("true")? true:false;
    }

    public static void main(String[] args) {
        logger.info("Starting HTTP Transport service");

//        State  state = new StateImpl();
//        state.pushHost("127.0.0.1:8082");
//        state.setState(InstanceStates.DOWN);
//        state.setDomain("maanadev.org");
//
//        System.out.println(StateImplJsonHelp.toString(state));
//        try {
////            EtcdUtil.putValue("localhost",StateImplJsonHelp.toString(state)).get();
//            System.out.println(EtcdUtil.getValue("localhost").get().getKvs().get(0).getValue().toString(StandardCharsets.UTF_8));
//        } catch (EtcdClientException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        Server.init();
    }
}
