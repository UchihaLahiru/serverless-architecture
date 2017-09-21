/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package lambda.netty.loadbalancer.core.scalability;

import lambda.netty.loadbalancer.core.ConfigConstants;
import lambda.netty.loadbalancer.core.launch.Launcher;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for updating the response time for each domain and scale if necessary
 * putTime method is synchronized
 * <p>
 * ProxyBackendHandler is calling putTime method
 * ProxyFrontHandler is setting initial time for response time
 *
 * responseTimesMap is not synchronized so if any read/write happening  locking should be taken care of
 */
public class ScaleInfoDAO {

    private static final Logger logger = Logger.getLogger(ScaleInfoDAO.class);

    private static Map<String, ResponseTimeInfo> responseTimesMap = new HashMap(Launcher.getIntValue(ConfigConstants.CONFIG_SCALABILITY_MAP_SIZE));
    final private static long THRESHOLD = Launcher.getLong(ConfigConstants.CONFIG_SCALABILITY_THRESHOLD);//milliseconds
    private static Object lock = new Object();

    private ScaleInfoDAO() {
    }

    public static void putTime(String domain, long time) {
        if (domain == null) {
            throw new NullPointerException("Domain is null");
        }
        synchronized (lock) {

            ResponseTimeInfo responseTimeInfo = responseTimesMap.get(domain);

            if (responseTimeInfo == null) {
                responseTimeInfo = new ResponseTimeInfo();
            }
            long responseTime = responseTimeInfo.update(time);

            logger.info("DOMAIN: " + domain + " Response Time: " + responseTime);
            if (responseTime > THRESHOLD) {
                // reset info
                responseTimeInfo.clear();
                //call scale
                logger.info("Threshold is passed !: " + THRESHOLD);
                ScalabilityManager.addToQueue(domain);
            } else {
                logger.info("Threshold is not passed !. Domain: " + domain);
            }
        }

    }


}
