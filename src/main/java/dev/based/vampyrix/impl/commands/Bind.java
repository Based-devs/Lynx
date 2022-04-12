package dev.based.vampyrix.impl.commands;

import dev.based.vampyrix.Vampyrix;
import dev.based.vampyrix.api.command.Command;
import dev.based.vampyrix.api.module.Module;
import dev.based.vampyrix.api.util.LoggerUtil;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
    public Bind() {
        super("bind", "bind <module> <key>", "b");
    }

    @Override
    public void execute(String[] arguments) {
        Module module = Vampyrix.INSTANCE.getModuleManager().getModuleByName(arguments[0]);

        if (module != null) {
            try {
                module.getKeybind().getValue().setKeyCode(Keyboard.getKeyIndex(arguments[1].toUpperCase()));
                LoggerUtil.sendMessage(String.format("Bound %s to %s", module.getName(), arguments[1].toUpperCase()));
                return;
            }
            catch (Exception ignored) {}
        }

        this.printUsage();
    }
}