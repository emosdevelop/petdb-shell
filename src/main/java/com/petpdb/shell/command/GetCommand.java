package com.petpdb.shell.command;

import com.petpdb.shell.console.ConsoleService;
import com.petpdb.shell.service.PetDBService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class GetCommand {

    private final PetDBService service;
    private final ConsoleService console;

    public GetCommand(PetDBService service, ConsoleService console) {
        this.service = service;
        this.console = console;
    }

    @ShellMethod(value = "Get value from given key", key = {"GET", "get"})
    public void get(String key) {
        console.write(service.call("GET " + key));
    }
}
