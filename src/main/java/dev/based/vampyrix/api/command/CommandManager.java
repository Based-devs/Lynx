package dev.based.vampyrix.api.command;

import dev.based.vampyrix.api.event.network.ChatEvent;
import dev.based.vampyrix.api.util.LoggerUtil;
import dev.based.vampyrix.api.util.Wrapper;
import dev.based.vampyrix.impl.commands.*;
import me.bush.eventbus.annotation.EventListener;

import java.util.Arrays;
import java.util.List;


public class CommandManager implements Wrapper {
	private final List<Command> commands;
	private String prefix = ".";

	public CommandManager() {
		this.commands = Arrays.asList(
				new Bind(),
				new Coordinates(),
				new Help(),
				new Prefix(),
				new Set(),
				new Toggle()
		);

		this.getVampyrix().getEventBus().subscribe(this);
	}

	public void runCommand(String args) {
		boolean found = false;
		String[] split = args.split(" ");
		String startCommand = split[0];
		String arguments = args.substring(startCommand.length()).trim();

		for (Command command : getCommands()) {
			for (String alias : command.getAliases()) {
				if (startCommand.equals(getPrefix() + alias)) {
					command.execute(arguments.split(" "));
					found = true;
				}
			}
		}

		if (!found) {
			LoggerUtil.sendMessage("Unknown command");
		}
	}

	public List<Command> getCommands() {
		return this.commands;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@EventListener
	private void onChat(ChatEvent event) {

	}
}
