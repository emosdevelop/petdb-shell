package com.petpdb.shell.command;

import com.petpdb.shell.console.ConsoleService;
import com.petpdb.shell.service.PetDBService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class FlushCommand {

    private final PetDBService service;
    private final ConsoleService console;

    public FlushCommand(PetDBService service, ConsoleService console) {
        this.service = service;
        this.console = console;
    }

    @ShellMethod(value = "Flush the database cache", key = {"FLUSH", "flush"})
    public void flush() {
        this.console.write(this.service.call("FLUSH"));
    }
}
