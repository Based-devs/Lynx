package dev.based.lynx.api.command;

import dev.based.lynx.api.event.events.ChatEvent;
import dev.based.lynx.api.util.chat.ChatUtil;
import dev.based.lynx.api.util.Wrapper;
import dev.based.lynx.impl.commands.*;
import me.bush.eventbus.annotation.EventListener;

import java.util.Arrays;
import java.util.List;

public class CommandManager implements Wrapper {
    private final List<Command> commands;
    private String prefix = ".";

    public CommandManager() {
        this.commands = Arrays.asList(new Bind(), new Coordinates(), new Help(), new Prefix(), new Set(), new Toggle());

        this.getLynx().getEventBus().subscribe(this);
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
        if (!event.getMessage().startsWith(this.getPrefix())) return;

        event.cancel();

        boolean found = false;
        String[] args = event.getMessage().split(" ");
        String startCommand = args[0];

        for (Command command : this.getCommands()) {
            for (String alias : command.getAliases()) {
                if (startCommand.equals(this.getPrefix() + alias)) {
                    command.execute(Arrays.copyOfRange(args, 1, args.length - 1));
                    found = true;
                }
            }
        }

        if (!found) {
            ChatUtil.sendMessage("Unknown command");
        }
    }
}
