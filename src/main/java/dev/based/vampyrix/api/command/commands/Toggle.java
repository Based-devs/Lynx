package dev.base.vampyrix.api.command.commands;

import dev.base.vampyrix.Vampyrix;
import dev.base.vampyrix.api.command.Command;
import dev.base.vampyrix.api.module.Module;

public class Toggle extends Command
{
    public Toggle(String name, String[] alias, String usage)
    {
        super(name, alias, usage);
    }


    @Override
    public void onTrigger(String arguments)
    {

        Module m = Vampyrix.moduleManager.getModule(arguments);
        if (m != null)
        {
            m.toggle();
            return;
        }

        printUsage();

    }

    @Override
    public void onRun(String arguments) {

    }
}