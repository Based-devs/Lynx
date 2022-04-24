package com.based.lynx.command;

import com.based.lynx.Lynx;
import com.based.lynx.util.LoggerUtil;

import java.util.Arrays;
import java.util.List;

public abstract class Command {
    private final String name, usage;
    private String[] aliases;

    public Command(String name, String usage, String... aliases) {
        this.name = name;
        this.usage = usage;
        this.aliases = aliases;
    }

    public abstract void onTrigger(String[] arguments);

    public void printUsage() {
        LoggerUtil.sendMessage("Usage: " + Lynx.commandManager.getPrefix() + this.usage);
    }

    public String getName() {
        return this.name;
    }

    public List<String> getAlias() {
        return Arrays.asList(aliases);
    }

    public void setAlias(String[] aliases) {
        this.aliases = aliases;
    }

    public String getUsage() {
        return this.usage;
    }
}
