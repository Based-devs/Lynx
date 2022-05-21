package com.based.lynx.util;

import java.awt.*;

public class ColourUtil {

    public static Color integrateAlpha(Color colour, float alpha) {
        float red = colour.getRed() / 255f;
        float green = colour.getGreen() / 255f;
        float blue = colour.getBlue() / 255f;

        return new Color(red, green, blue, alpha / 255f);
    }

}
