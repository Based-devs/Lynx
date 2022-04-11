package dev.based.vampyrix.api.util.render;

import java.awt.*;

public class ColourUtil {
    public static Color getClientColour() {
        return new Color(50, 80, 255);
    }

    public static Color integrateAlpha(Color colour, float alpha) {
        float red = colour.getRed() / 255f;
        float green = colour.getGreen() / 255f;
        float blue = colour.getBlue() / 255f;

        return new Color(red, green, blue, alpha / 255f);
    }
}
