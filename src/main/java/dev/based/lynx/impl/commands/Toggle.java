package dev.based.lynx.impl.commands;

import dev.based.lynx.Lynx;
import dev.based.lynx.api.command.Command;
import dev.based.lynx.api.module.Module;

public class Toggle extends Command {
    public Toggle() {
        super("toggle", "toggle <module>", "t");
    }


    @Override
    public void execute(String[] arguments) {
        Module m = Lynx.INSTANCE.getModuleManager().getModuleByName(arguments[0]);

        if (m != null) {
            m.toggle();
            return;
        }

        this.printUsage();
    }
}