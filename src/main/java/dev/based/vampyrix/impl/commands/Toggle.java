package dev.based.vampyrix.impl.commands;

import dev.based.vampyrix.Vampyrix;
import dev.based.vampyrix.api.command.Command;
import dev.based.vampyrix.api.module.Module;

public class Toggle extends Command {
    public Toggle() {
        super("Toggle", "toggle <module>", "t");
    }


    @Override
    public void execute(String[] arguments) {
        Module m = Vampyrix.INSTANCE.getModuleManager().getModuleByName(arguments[0]);

        if (m != null) {
            m.toggle();
            return;
        }

        this.printUsage();
    }
}