package dev.based.lynx.api.event.events;

import dev.based.lynx.api.event.ClientEvent;

public class ClientTickEvent extends ClientEvent {
    public ClientTickEvent(Era era) {
        super(era);
    }
}
