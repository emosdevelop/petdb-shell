package com.petpdb.shell.console;

import com.petpdb.shell.client.PetDB;
import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class Prompt implements PromptProvider {

    private final ConsoleService console;

    public Prompt(ConsoleService console) {
        this.console = console;
    }

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(console.getHost() + ">");
    }
}
