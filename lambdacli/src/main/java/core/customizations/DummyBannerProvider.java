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
        return "This is a CLI for dummy service";
    }

    @Override
    public String getProviderName() {
        return "E12peraCom";
    }
}
