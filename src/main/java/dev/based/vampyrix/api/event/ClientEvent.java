package dev.based.vampyrix.api.event;

import me.bush.eventbus.event.Event;

public abstract class ClientEvent extends Event {
    private final boolean cancellable;

    public ClientEvent() {
        this(false);
    }

    public ClientEvent(boolean cancellable) {
        this.cancellable = cancellable;
    }

    @Override
    protected boolean isCancellable() {
        return cancellable;
    }
}
