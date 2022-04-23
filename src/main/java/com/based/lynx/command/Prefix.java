package com.based.lynx.command;

import com.based.lynx.Lynx;
import com.based.lynx.command.Command;
import com.based.lynx.util.LoggerUtil;

public class Prefix
        extends Command {
    public Prefix(String name, String[] alias, String usage) {
        super(name, alias, usage);
    }

    @Override
    public void onTrigger(String arguments) {
        if (arguments.equals("")) {
            this.printUsage();
            return;
        }
        Lynx.commandManager.setPrefix(arguments);
        LoggerUtil.sendMessage("Prefix set to " + arguments);
    }
}
