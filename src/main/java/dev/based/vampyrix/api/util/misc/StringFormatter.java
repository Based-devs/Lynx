package dev.based.vampyrix.api.util.misc;

public class StringFormatter {

    public static String formatEnum(Enum<?> enumIn) {
        final String text = enumIn.name();
        final StringBuilder formatted = new StringBuilder();

        boolean isFirst = true;
        for (char c : text.toCharArray()) {
            if (c == '_') {
                c = ' ';
            }

            if (isFirst) {
                formatted.append(String.valueOf(c).toUpperCase());
                isFirst = false;
            } else {
                formatted.append(String.valueOf(c).toLowerCase());
            }

            if (c == ' ') {
                isFirst = true;
            }
        }

        return formatted.toString();
    }
}
