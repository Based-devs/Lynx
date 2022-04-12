package dev.base.vampyrix.api.command.commands;

import dev.base.vampyrix.Vampyrix;
import dev.base.vampyrix.api.command.Command;
import dev.base.vampyrix.api.util.LoggerUtil;

public class Help extends Command
{
	public Help(String name, String[] alias, String usage)
	{
		super(name, alias, usage);
	}

	@Override
	public void onTrigger(String arguments)
	{
		LoggerUtil.sendMessage("Vampyrix");

		for (Command command : Vampyrix.commandManager.getCommands())
		{
			LoggerUtil.sendMessage(command.getName() + " - " + command.getUsage());
		}
	}

	public void onRun(String arguments) {

	}

}
