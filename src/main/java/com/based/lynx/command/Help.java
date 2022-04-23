package com.based.lynx.command;

import com.based.lynx.Lynx;
import com.based.lynx.util.LoggerUtil;

public class Help
        extends Command {
    public Help(String name, String[] alias, String usage) {
        super(name, alias, usage);
    }

    @Override
    public void onTrigger(String arguments) {
        LoggerUtil.sendMessage("Lynx");
        for (Command command : Lynx.commandManager.getCommands()) {
            LoggerUtil.sendMessage(command.getName() + " - " + command.getUsage());
        }
    }
}
