package lambda.netty.loadbalancer.core.scalability;

import lambda.netty.loadbalancer.core.ConfigConstants;
import lambda.netty.loadbalancer.core.launch.Launcher;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for updating the response time for each domain and scale if necessary
 * putTime method is synchronized
 *
 * ProxyBackendHandler is calling putTime method
 * ProxyFrontHandler is setting initial time for response time
 */
public class ScaleInfoDAO {

    private static final Logger logger = Logger.getLogger(ScaleInfoDAO.class);

    private static Map<String,ResponseTimeInfo> responseTimesMap = new HashMap(Launcher.getIntValue(ConfigConstants.SCALABILITY_MAP_SIZE));
    final private static long THRESHOLD = Launcher.getLong(ConfigConstants.SCALABILITY_THRESHOLD);//milliseconds
    private static Object lock = new Object();

    private ScaleInfoDAO(){}

    public  static void putTime(String domain,long time){
        if(domain==null){
            throw new NullPointerException("Domain is null");
        }
        synchronized (lock){

            ResponseTimeInfo responseTimeInfo = responseTimesMap.get(domain);

            if(responseTimeInfo==null){
                responseTimeInfo = new ResponseTimeInfo();
            }
            long responseTime =responseTimeInfo.update(time);

            logger.info("DOMAIN: "+domain+" Response Time: "+ responseTime);
            if(responseTime>THRESHOLD){
                //call scale
                logger.info("Threshold is passed !: "+ THRESHOLD);
                ScalabilityManager.addToQueue(domain);
                responseTimeInfo.clear();
            }else {
                logger.info("Threshold is not passed !. Domain: " +domain);
            }
        }

    }


}
