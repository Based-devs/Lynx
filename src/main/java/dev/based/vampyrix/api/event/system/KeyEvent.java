package dev.based.vampyrix.api.event.system;

import dev.based.vampyrix.api.event.ClientEvent;

public class KeyEvent extends ClientEvent {
    private static final KeyEvent INSTANCE = new KeyEvent();

    private int key;

    public static KeyEvent get(int key) {
        INSTANCE.key = key;

        return INSTANCE;
    }

    public int getKey() {
        return this.key;
    }
}
