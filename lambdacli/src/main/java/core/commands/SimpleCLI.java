package core.commands;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

@Component
public class SimpleCLI implements CommandMarker {

    @CliCommand(value = { "web-get", "wg" })
    public String webGet(
            @CliOption(key = "url") String url) {
        return "ok";
    }

    @CliCommand(value = { "web-save", "ws" })
    public String webSave(
            @CliOption(key = "url") String url,
            @CliOption(key = { "out", "file" }) String file) throws FileNotFoundException {
        String contents = url;
        try (PrintWriter out = new PrintWriter(file)) {
            out.write(contents);
        }
        return "Done.";
    }
}
