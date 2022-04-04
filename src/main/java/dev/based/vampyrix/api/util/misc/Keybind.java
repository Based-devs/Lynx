package dev.based.vampyrix.api.util.misc;

/**
 * Used for keybind settings
 * @author Wolfsurge
 * @since 03/04/22
 */
public class Keybind {

    // The keycode
    private int keyCode;

    public Keybind(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * Gets the keycode
     * @return The keycode
     */
    public int getKeyCode() {
        return keyCode;
    }

    /**
     * Sets the keycode
     * @param keyCode The keycode
     */
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

}
