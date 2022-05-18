package dev.based.lynx.api.util.render;

import dev.based.lynx.api.util.Wrapper;

/**
 * Will implement custom font at some point (maybe).
 * Will just be so it's all in one file
 */
public interface TextRenderer extends Wrapper {
    default void drawString(String string, float x, float y, int colour, boolean shadow) {
        mc.fontRenderer.drawString(string, x, y, colour, shadow);
    }

    default float getStringWidth(String string) {
        return mc.fontRenderer.getStringWidth(string);
    }

    default float getFontHeight() {
        return mc.fontRenderer.FONT_HEIGHT;
    }
}
