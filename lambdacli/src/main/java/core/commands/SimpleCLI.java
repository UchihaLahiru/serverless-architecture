package core.commands;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

@Component
public class SimpleCLI implements CommandMarker {

    @CliCommand(value = { "create-func", "cf" })
    public String createFuntion(
            @CliOption(key = "name") String name,
            @CliOption(key = "file") String file) {
        return "ok";
    }

}
