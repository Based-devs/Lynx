package com.based.lynx.command;

import com.based.lynx.Lynx;
import com.based.lynx.util.LoggerUtil;

public class Help extends Command {
    public Help() {
        super("Help", "help", "h", "help");
    }

    @Override
    public void execute(String[] arguments) {
        LoggerUtil.sendMessage("Lynx");
        for (Command command : Lynx.commandManager.getCommands()) {
            LoggerUtil.sendMessage(command.getName() + " - " + command.getUsage());
        }
    }
}
