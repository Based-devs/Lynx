package com.based.lynx.command;

import com.based.lynx.Lynx;
import com.based.lynx.command.Command;
import com.based.lynx.util.LoggerUtil;

public class Prefix extends Command {
    public Prefix() {
        super("Prefix", "prefix <char>", "prefix");
    }

    @Override
    public void execute(String[] arguments) {
        if (arguments[0].equals("")) {
            this.printUsage();
            return;
        }
        Lynx.commandManager.setPrefix(arguments[0]);
        LoggerUtil.sendMessage("Prefix set to " + arguments[0]);
    }
}
