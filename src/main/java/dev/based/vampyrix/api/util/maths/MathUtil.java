package dev.based.vampyrix.api.util.maths;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {
    public static double roundDouble(double number, int scale) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
