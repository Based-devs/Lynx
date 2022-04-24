package com.based.lynx.util;

public class FontUtil {

    public static void drawStringWithShadow(String name, float x, float y, int colour, boolean shadow) {
        Wrapper.getMinecraft().fontRenderer.drawString(name, x, y, colour, shadow);
    }

    public static void drawCenteredString(String name, float x, float y, int colour, boolean shadow) {
        Wrapper.getMinecraft().fontRenderer.drawString(name, x - (Wrapper.getMinecraft().fontRenderer.getStringWidth(name) / 2f), y, colour, shadow);
    }

}
