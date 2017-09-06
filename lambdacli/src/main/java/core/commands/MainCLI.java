package core.commands;


import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class MainCLI implements CommandMarker {

    private static final String ALL_FUNCTIONS = "ALL_FUNCTIONSlf";

    @CliCommand(value = { "create_func", "cf" })
    public String createFuntion(
            @CliOption(key = "name",mandatory = true) String name,
            @CliOption(key = "file",mandatory = true) String file,
            @CliOption(key="runtime",mandatory = true)String runtime,
            @CliOption(key="event",mandatory = true)String event) throws FileNotFoundException {
        return name+file+runtime+event;
    }

    @CliCommand(value = { "list_func", "lf" })
    public String listFunctions(
            @CliOption(key = "runtime",specifiedDefaultValue = ALL_FUNCTIONS) String runtime){
        return runtime;
    }

    @CliCommand(value = { "del_func", "dlf" })
    public String deleteFunction(
            @CliOption(key = "id" ,mandatory = true) String id,
            @CliOption(key = "runtime",mandatory = true) String runtime){
        return id+runtime;
    }
    @CliCommand(value = { "update_event", "upev" })
    public String updateEvent(
            @CliOption(key = "id" ,mandatory = true) String id,
            @CliOption(key = "runtime",mandatory = true) String runtime,
                    @CliOption(key = "event",mandatory = true) String event){
        return id+runtime+event;
    }
    @CliCommand(value = { "update_func", "upf" })
    public String updateFunction(
            @CliOption(key = "id" ,mandatory = true) String id,
            @CliOption(key = "event",mandatory = true) String event,
            @CliOption(key = "runtime",mandatory = true) String runtime
            ){
        return id+runtime+event;
    }
}
