package com.based.lynx.util;

import java.awt.*;

public class ColorUtil {

    public static Color integrateAlpha(Color color, float alpha) {
        float red = color.getRed() / 255f;
        float green = color.getGreen() / 255f;
        float blue = color.getBlue() / 255f;

        return new Color(red, green, blue, alpha / 255f);
    }

}
