package com.based.lynx.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathsUtil {

    public static double roundDouble(double number, int scale) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
