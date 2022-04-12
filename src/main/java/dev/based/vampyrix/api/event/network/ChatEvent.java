package dev.based.vampyrix.api.event.network;

import dev.based.vampyrix.api.event.ClientEvent;

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
