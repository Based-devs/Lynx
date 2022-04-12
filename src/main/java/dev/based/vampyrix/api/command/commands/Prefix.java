package dev.base.vampyrix.api.command.commands;

import dev.base.vampyrix.Vampyrix;
import dev.base.vampyrix.api.command.Command;
import dev.base.vampyrix.api.util.LoggerUtil;


public class Prefix extends Command
{
	public Prefix(String name, String[] alias, String usage)
	{
		super(name, alias, usage);
	}

	@Override
	public void onTrigger(String arguments)
	{
		if (arguments.equals(""))
		{
			printUsage();
			return;
		}

		Vampyrix.commandManager.setPrefix(arguments);
		LoggerUtil.sendMessage("Prefix set to " + arguments);
	}

	public void onRun(String arguments) {

	}
}
