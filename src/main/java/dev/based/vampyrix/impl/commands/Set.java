package dev.based.vampyrix.impl.commands;

import dev.based.vampyrix.api.command.Command;

public class Set extends Command {
    public Set() {
        super("set", "set <module> <setting> <value>\n ex. set step height 2.0", "s");
    }

    @Override
    public void execute(String[] arguments) {
        /**
        for (Module module : Vampyrix.INSTANCE.getModuleManager().getModules()) {
            if (module.getName().equalsIgnoreCase(arguments[0])) {
                for (Setting<?> setting : module.getSettings()) {
                    if (arguments[1].equalsIgnoreCase(setting.getName())) {
                        if (setting.isInteger() && (setting.getMinIntegerValue() - 1) < Integer.parseInt(args[2].toLowerCase()) && (setting.getMaxIntegerValue() + 1) > Integer.parseInt(args[2].toLowerCase())) {
                            setting.setIntegerValue(Integer.parseInt(args[2].toLowerCase()));
                            LoggerUtil.sendMessage("Set " + setting.getName() + " to " + setting.getIntegerValue());
                            return;
                        }
                        else if (setting.isBoolean()) {
                            setting.setBooleanValue(args[2].equalsIgnoreCase("on") || args[2].equalsIgnoreCase("true"));
                            LoggerUtil.sendMessage("Set " + setting.getName() + " to " + setting.getBooleanValue());
                            return;
                        }
                        else if (setting.isEnum()) {
                            for (String string : setting.getEnumValues()) {
                                if (args[2].equalsIgnoreCase(string)) setting.setEnumValue(string);
                            }
                            LoggerUtil.sendMessage("Set " + setting.getName() + " to " + setting.getEnumValue());
                            return;
                        }
                    }
                }
            }
        }
        */
        this.printUsage();
    }
}