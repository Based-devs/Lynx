package com.based.lynx.command;

import com.based.lynx.Lynx;
import com.based.lynx.util.LoggerUtil;

public class Command {
    private String name;
    private String[] alias;
    private String usage;

    public Command(String name, String[] alias, String usage) {
        this.setName(name);
        this.setAlias(alias);
        this.setUsage(usage);
    }

    public void onTrigger(String arguments) {
    }

    public void printUsage() {
        LoggerUtil.sendMessage("Usage: " + Lynx.commandManager.getPrefix() + this.usage);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getAlias() {
        return this.alias;
    }

    public void setAlias(String[] alias) {
        this.alias = alias;
    }

    public String getUsage() {
        return this.usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
