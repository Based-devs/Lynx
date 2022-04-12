package dev.base.vampyrix.api.command.commands;

import dev.base.vampyrix.Vampyrix;
import dev.base.vampyrix.api.command.Command;
import dev.base.vampyrix.api.module.Module;
import dev.base.vampyrix.api.setting.Setting;
import dev.base.vampyrix.api.util.LoggerUtil;

public class Set extends Command
{
    public Set(String name, String[] alias, String usage)
    {
        super(name, alias, usage);
    }


    @Override
    public void onTrigger(String arguments)
    {
        String[] args = arguments.split(" ");

        for (Module module : Vampyrix.moduleManager.getModules())
        {
            if (module.getName().equalsIgnoreCase(args[0]))
            {

                for (Setting setting : Vampyrix.settingManager.getSettings())
                {
                    if (setting.getModule().equals(module) && args[1].equalsIgnoreCase(setting.getName()))
                    {

                        if (setting.isInteger() && (setting.getMinIntegerValue() - 1) < Integer.parseInt(args[2].toLowerCase()) && (setting.getMaxIntegerValue() + 1) > Integer.parseInt(args[2].toLowerCase()))
                        {
                            setting.setIntegerValue(Integer.parseInt(args[2].toLowerCase()));
                            LoggerUtil.sendMessage("Set " + setting.getName() + " to " + setting.getIntegerValue());
                            return;
                        }
                        else if (setting.isBoolean())
                        {
                            setting.setBooleanValue(args[2].equalsIgnoreCase("on") || args[2].equalsIgnoreCase("true"));
                            LoggerUtil.sendMessage("Set " + setting.getName() + " to " + setting.getBooleanValue());
                            return;
                        }
                        else if (setting.isEnum())
                        {
                            for (String string : setting.getEnumValues())
                            {
                                if (args[2].equalsIgnoreCase(string)) setting.setEnumValue(string);
                            }
                            LoggerUtil.sendMessage("Set " + setting.getName() + " to " + setting.getEnumValue());
                            return;
                        }
                    }
                }
            }
        }

        printUsage();
    }
    @Override
    public void onRun(String arguments) {

    }
}