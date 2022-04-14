package dev.based.vampyrix.api.event.events;

import dev.based.vampyrix.api.event.ClientEvent;

public class ClientTickEvent extends ClientEvent {
    public ClientTickEvent(Era era) {
        super(era);
    }
}
