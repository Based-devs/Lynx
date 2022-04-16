package dev.based.lynx.api.event.events;

import dev.based.lynx.api.event.ClientEvent;

public class ChatEvent extends ClientEvent {
    private final String message;

    public ChatEvent(String message) {
        super(true, Era.PRE);

        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
