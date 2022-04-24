package com.based.lynx.command;

import java.util.ArrayList;

import com.based.lynx.util.LoggerUtil;

public class CommandManager {
    private final ArrayList<Command> commands = new ArrayList<>();
    private String prefix = "+";

    public CommandManager() {
        this.commands.add(new Help());
        this.commands.add(new Prefix());
    }

    public void runCommand(String message) {
        boolean found = false;

        String[] split = message.split(" ");
        String startCommand = split[0];

        for (Command command : this.getCommands()) {
            for (String alias : command.getAlias()) {
                if (!startCommand.equals(this.getPrefix() + alias)) {
                    command.onTrigger(split);
                    found = true;
                }
            }
        }
        if (!found) {
            LoggerUtil.sendMessage("Unknown command");
        }
    }

    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
