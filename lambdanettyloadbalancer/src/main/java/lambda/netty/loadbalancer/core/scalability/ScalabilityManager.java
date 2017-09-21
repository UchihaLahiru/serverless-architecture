package lambda.netty.loadbalancer.core.scalability;

import lambda.netty.loadbalancer.core.ConfigConstants;
import lambda.netty.loadbalancer.core.launch.Launcher;
import org.apache.log4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class runs in a separate thread quering the queue for a scaling task
 */
public class ScalabilityManager implements Runnable {

    private static final Logger logger = Logger.getLogger(ScalabilityManager.class);

    private static BlockingQueue<String> queue = new ArrayBlockingQueue<String>(Launcher.getIntValue(ConfigConstants.CONFIG_SCALABILITY_BLOCKING_QUEUE_SIZE));
    private static ExecutorService service = Executors.newFixedThreadPool(Launcher.getIntValue(ConfigConstants.CONFIG_SCALABILITY_THREAD_COUNT));
    private static int QUERY_TIME = Launcher.getIntValue(ConfigConstants.CONFIG_SCALABILITY_QUERY_TIME);

    public void run() {
        logger.info("Starting scalability service !");
        while (true) {

            if (!queue.isEmpty()) {
                try {
                    String domain = queue.take();
                    service.submit(new HandleScaling(domain));
                    logger.info("Spawning instance for domain: " + domain);
                } catch (InterruptedException e) {
                    logger.error("Cannot take value from queue", e);
                }
            }
            try {
                Thread.sleep(QUERY_TIME);
            } catch (InterruptedException e) {
                logger.error("Thread sleep went wrong !", e);
            }
        }
    }

    public static void addToQueue(String domain) {
        queue.add(domain);
    }
}


