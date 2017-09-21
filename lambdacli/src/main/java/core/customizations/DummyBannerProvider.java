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

package core.customizations;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.BannerProvider;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DummyBannerProvider implements BannerProvider {
    @Override
    public String getBanner() {
        return "\n" +
                ".____                  ___.        .___        ___________              __  .__                \n" +
                "|    |   _____    _____\\_ |__    __| _/____    \\__    ___/___   _______/  |_|__| ____    ____  \n" +
                "|    |   \\__  \\  /     \\| __ \\  / __ |\\__  \\     |    |_/ __ \\ /  ___/\\   __\\  |/    \\  / ___\\ \n" +
                "|    |___ / __ \\|  Y Y  \\ \\_\\ \\/ /_/ | / __ \\_   |    |\\  ___/ \\___ \\  |  | |  |   |  \\/ /_/  >\n" +
                "|_______ (____  /__|_|  /___  /\\____ |(____  /   |____| \\___  >____  > |__| |__|___|  /\\___  / \n" +
                "        \\/    \\/      \\/    \\/      \\/     \\/               \\/     \\/               \\//_____/  \n";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getWelcomeMessage() {
        return "This is a CLI for lambda service";
    }

    @Override
    public String getProviderName() {
        return "E12peraCom";
    }
}
