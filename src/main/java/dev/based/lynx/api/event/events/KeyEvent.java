package dev.based.lynx.api.event.events;

import dev.based.lynx.api.event.ClientEvent;

public class KeyEvent extends ClientEvent {
    private final int key;

    public KeyEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}
