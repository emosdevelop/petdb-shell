package com.petpdb.shell.command;

import com.petpdb.shell.console.ConsoleService;
import com.petpdb.shell.service.PetDBService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ConnectionCommand {

    private final PetDBService service;
    private final ConsoleService console;

    public ConnectionCommand(PetDBService service, ConsoleService console) {
        this.service = service;
        this.console = console;
    }

    @ShellMethod(value = "Open the connection to PetDB", key = {"OPEN", "open"})
    public void open() {
        service.open();
        this.console.write("Open!");
    }

    @ShellMethod(value = "Close the connection to PetDB", key = {"CLOSE", "close"})
    public void close() {
        service.close();
        this.console.write("Closed!");
    }
}
