package dev.based.lynx.impl.commands;

import dev.based.lynx.Lynx;
import dev.based.lynx.api.command.Command;
import dev.based.lynx.api.util.chat.ChatUtil;

public class Prefix extends Command {
    public Prefix() {
        super("prefix", "prefix <character>", "p", "pre");
    }

    @Override
    public void execute(String[] arguments) {
        if (arguments[1] != null) {
            this.printUsage();
            return;
        }

        Lynx.INSTANCE.getCommandManager().setPrefix(arguments[1]);
        ChatUtil.sendMessage("Prefix set to " + arguments[1]);
    }
}
