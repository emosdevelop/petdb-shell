package com.petpdb.shell.command;

import com.petpdb.shell.console.ConsoleService;
import com.petpdb.shell.service.PetDBService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class DumpCommand {

    private final PetDBService service;
    private final ConsoleService console;

    public DumpCommand(PetDBService service, ConsoleService console) {
        this.service = service;
        this.console = console;
    }

    @ShellMethod(value = "Dumps the database into JSON or XML", key = {"DUMP", "dump"})
    public void dump(String extension) {
        String query = String.format("DUMP %s", extension);
        this.console.write(this.service.call(query));
    }
}
