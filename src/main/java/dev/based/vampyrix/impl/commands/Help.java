package dev.based.vampyrix.impl.commands;

import dev.based.vampyrix.Vampyrix;
import dev.based.vampyrix.api.command.Command;
import dev.based.vampyrix.api.util.chat.ChatUtil;

public class Help extends Command {
    public Help() {
        super("help", "help", "h");
    }

    @Override
    public void execute(String[] arguments) {
        ChatUtil.sendMessage("Vampyrix");

        for (Command command : Vampyrix.INSTANCE.getCommandManager().getCommands()) {
            ChatUtil.sendMessage(command.getName() + " - " + command.getUsage());
        }
    }
}
