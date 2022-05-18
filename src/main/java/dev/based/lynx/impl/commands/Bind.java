package dev.based.lynx.impl.commands;

import dev.based.lynx.Lynx;
import dev.based.lynx.api.command.Command;
import dev.based.lynx.api.module.Module;
import dev.based.lynx.api.util.chat.ChatUtil;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
    public Bind() {
        super("bind", "bind <module> <key>", "b");
    }

    @Override
    public void execute(String[] arguments) {
        Module module = Lynx.INSTANCE.getModuleManager().getModuleByName(arguments[0]);

        if (module != null) {
            try {
                module.getKeybind().getValue().setKeyCode(Keyboard.getKeyIndex(arguments[1].toUpperCase()));
                ChatUtil.sendMessage(String.format("Bound %s to %s", module.getName(), arguments[1].toUpperCase()));
                return;
            } catch (Exception ignored) {}
        }

        this.printUsage();
    }
}