package lambda.netty.loadbalancer.core.scalability;

import org.apache.log4j.Logger;

public class HandleScaling implements Runnable {
    private static final Logger logger = Logger.getLogger(HandleScaling.class);

    private String domain;

    HandleScaling(String domain){
        this.domain = domain;
    }
    @Override
    public void run() {
        logger.info("scaled");
    }
}
