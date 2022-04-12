package dev.based.vampyrix.api.command;

import dev.based.vampyrix.Vampyrix;
import dev.based.vampyrix.api.util.LoggerUtil;

public abstract class Command {
    private final String name, usage;
    private final String[] aliases;

    public Command(String name, String usage, String... aliases) {
        this.name = name;
        this.usage = usage;
        this.aliases = aliases;
    }

    public void printUsage() {
        LoggerUtil.sendMessage("Usage: " + Vampyrix.INSTANCE.getCommandManager().getPrefix() + usage);
    }

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    public String[] getAliases() {
        return aliases;
    }

    public abstract void execute(String[] arguments);
}
