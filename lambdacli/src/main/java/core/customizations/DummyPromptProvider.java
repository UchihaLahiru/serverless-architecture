package core.customizations;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.PromptProvider;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DummyPromptProvider implements PromptProvider {
    @Override
    public String getPrompt() {
        return "dummy-service >";
    }

    @Override
    public String getProviderName() {
        return "maanadev";
    }
}
