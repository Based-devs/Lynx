package dev.based.vampyrix.api.command;

import java.util.Arrays;
import java.util.List;

public abstract class Command {
    private final String name;
    private final String description;
    private final String syntax;
    private final List<String> aliases;

    public Command(final String name, final String description, final String syntax, final String... aliases){
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.aliases = Arrays.asList(aliases);
    }

    public abstract void onCommand(String[] args);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSyntax() {
        return syntax;
    }

    public List<String> getAliases() {
        return aliases;
    }
}
