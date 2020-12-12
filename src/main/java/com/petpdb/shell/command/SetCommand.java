package com.petpdb.shell.command;

import com.petpdb.shell.console.ConsoleService;
import com.petpdb.shell.service.PetDBService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SetCommand {

    private final PetDBService service;
    private final ConsoleService console;

    public SetCommand(PetDBService service, ConsoleService console) {
        this.service = service;
        this.console = console;
    }

    @ShellMethod(value = "Set given key and value", key = {"SET", "set"})
    public void set(String key, String value) {
        String query = String.format("SET %s %s", key, value);
        console.write(service.call(query));
    }
}
