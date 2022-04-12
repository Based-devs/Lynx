package dev.based.vampyrix.impl.commands;

import dev.based.vampyrix.Vampyrix;
import dev.based.vampyrix.api.command.Command;
import dev.based.vampyrix.api.util.LoggerUtil;

public class Help extends Command {
	public Help() {
		super("help", "help", "h");
	}

	@Override
	public void execute(String[] arguments)
	{
		LoggerUtil.sendMessage("Vampyrix");

		for (Command command : Vampyrix.INSTANCE.getCommandManager().getCommands()) {
			LoggerUtil.sendMessage(command.getName() + " - " + command.getUsage());
		}
	}
}
