package com.petpdb.shell.command;

import com.petpdb.shell.console.ConsoleService;
import com.petpdb.shell.service.PetDBService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class QueryCommand {

    private final PetDBService service;
    private final ConsoleService console;

    public QueryCommand(PetDBService service, ConsoleService console) {
        this.service = service;
        this.console = console;
    }

    @ShellMethod(value = "Get value from given key", key = {"GET", "get"})
    public void get(String key) {
        String query = String.format("GET %s", key);
        console.write(service.call(query));
    }

    @ShellMethod(value = "Set given key and value", key = {"SET", "set"})
    public void set(String key, String value) {
        String query = String.format("SET %s %s", key, value);
        console.write(service.call(query));
    }

    @ShellMethod(value = "Delete given key", key = {"DELETE", "delete", "del"})
    public void delete(String key) {
        String query = String.format("DELETE %s", key);
        console.write(service.call(query));
    }

    @ShellMethod(value = "Count of PetDB keys", key = {"COUNT", "count"})
    public void count() {
        console.write(service.call("COUNT"));
    }
}
