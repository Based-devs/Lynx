package dev.based.vampyrix.impl.commands;

import dev.based.vampyrix.Vampyrix;
import dev.based.vampyrix.api.command.Command;
import dev.based.vampyrix.api.util.LoggerUtil;

public class Prefix extends Command {
	public Prefix() {
		super("prefix", "prefix <character>", "p", "pre");
	}

	@Override
	public void execute(String[] arguments) {
		if (arguments[1] != null) {
			this.printUsage();
			return;
		}

		Vampyrix.INSTANCE.getCommandManager().setPrefix(arguments[1]);
		LoggerUtil.sendMessage("Prefix set to " + arguments[1]);
	}
}
