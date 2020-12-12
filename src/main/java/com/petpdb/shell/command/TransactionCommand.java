package com.petpdb.shell.command;

import com.petpdb.shell.console.ConsoleService;
import com.petpdb.shell.service.PetDBService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TransactionCommand {

    private final PetDBService service;
    private final ConsoleService console;

    public TransactionCommand(PetDBService service, ConsoleService console) {
        this.service = service;
        this.console = console;
    }

    @ShellMethod(value = "Begin a new transaction", key = {"BEGIN", "begin"})
    public void begin() {
        this.console.write(this.service.call("BEGIN"));
    }

    @ShellMethod(value = "Rollback active transaction", key = {"ROLLBACK", "rollback"})
    public void rollback() {
        this.console.write(this.service.call("ROLLBACK"));
    }

    @ShellMethod(value = "Commit active transaction", key = {"COMMIT", "commit"})
    public void commit() {
        this.console.write(this.service.call("COMMIT"));
    }

    @ShellMethod(value = "End active transaction", key = {"END", "end"})
    public void end() {
        this.console.write(this.service.call("END"));
    }

}
