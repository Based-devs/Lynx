package dev.based.lynx.impl.commands;

import dev.based.lynx.Lynx;
import dev.based.lynx.api.command.Command;
import dev.based.lynx.api.util.chat.ChatUtil;

public class Help extends Command {
    public Help() {
        super("help", "help", "h");
    }

    @Override
    public void execute(String[] arguments) {
        ChatUtil.sendMessage("Vampyrix");

        for (Command command : Lynx.INSTANCE.getCommandManager().getCommands()) {
            ChatUtil.sendMessage(command.getName() + " - " + command.getUsage());
        }
    }
}
