package dev.based.lynx.api.command;

import dev.based.lynx.Lynx;
import dev.based.lynx.api.util.chat.ChatUtil;

public abstract class Command {
    private final String name, usage;
    private final String[] aliases;

    public Command(String name, String usage, String... aliases) {
        this.name = name;
        this.usage = usage;
        this.aliases = aliases;
    }

    public void printUsage() {
        ChatUtil.sendMessage("Usage: " + Lynx.INSTANCE.getCommandManager().getPrefix() + usage);
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
