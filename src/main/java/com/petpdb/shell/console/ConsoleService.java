package com.petpdb.shell.console;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class ConsoleService {

    private final PrintStream out = System.out;
    @Value("${petdb.host}")
    private String host;

    public void write(String message) {
        this.out.print(ConsoleColors.YELLOW);
        this.out.print(message);
        this.out.print(ConsoleColors.RESET);
        this.out.println();
    }

    public String getHost() {
        return host;
    }
}
