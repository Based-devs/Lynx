package dev.based.vampyrix.api.event;

import me.bush.eventbus.event.Event;

public abstract class ClientEvent extends Event {
    private final boolean cancellable;
    private final Era era;

    public ClientEvent() {
        this(Era.POST);
    }

    public ClientEvent(Era era) {
        this(false, era);
    }

    public ClientEvent(boolean cancellable) {
        this(cancellable, Era.POST);
    }

    public ClientEvent(boolean cancellable, Era era) {
        this.cancellable = cancellable;
        this.era = era;
    }

    public Era getEra() {
        return this.era;
    }

    @Override
    protected boolean isCancellable() {
        return cancellable;
    }

    public enum Era {
        PRE, POST
    }
}
