package dev.based.lynx.api.util.render;

import dev.based.lynx.impl.modules.client.Colors;

import java.awt.*;

public class ColourUtil {
    public static Color getClientColour() {
        return Colors.INSTANCE.colour.getValue();
    }

    public static Color integrateAlpha(Color colour, float alpha) {
        float red = colour.getRed() / 255f;
        float green = colour.getGreen() / 255f;
        float blue = colour.getBlue() / 255f;

        return new Color(red, green, blue, alpha / 255f);
    }
}
