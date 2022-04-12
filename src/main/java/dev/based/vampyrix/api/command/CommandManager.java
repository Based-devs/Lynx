package dev.base.vampyrix.api.command;

import dev.base.vampyrix.api.util.LoggerUtil;
import dev.base.vampyrix.api.command.commands.*;

import java.util.ArrayList;


public class CommandManager
{
	private final ArrayList<Command> commands = new ArrayList<>();
	private String prefix = ".";

	public CommandManager()
	{
		commands.add(new Help("Help", new String[]{"h", "help"}, "help"));
		commands.add(new Prefix("Prefix", new String[]{"prefix"}, "prefix <char>"));
		commands.add(new Bind("Bind", new String[]{"bind"}, "bind <module> <key>"));
	}

	public void runCommand(String args)
	{
		boolean found = false;
		String[] split = args.split(" ");
		String startCommand = split[0];
		String arguments = args.substring(startCommand.length()).trim();

		for (Command command : getCommands())
		{
			for (String alias : command.getAlias())
			{
				if (startCommand.equals(getPrefix() + alias))
				{
					command.onTrigger(arguments);
					found = true;
				}
			}
		}

		if (!found)
		{
			LoggerUtil.sendMessage("Unknown command");
		}
	}

	public ArrayList<Command> getCommands()
	{
		return commands;
	}

	public String getPrefix()
	{
		return prefix;
	}

	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}
}
