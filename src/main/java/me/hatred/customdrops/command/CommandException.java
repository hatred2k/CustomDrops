package me.hatred.customdrops.command;

import java.util.Collections;
import java.util.List;

public class CommandException extends Exception {

    private List<String> messages;

    public CommandException() {
        this.messages = Collections.singletonList("Failed to execute command.");
    }

    public CommandException(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return this.messages;
    }

}
