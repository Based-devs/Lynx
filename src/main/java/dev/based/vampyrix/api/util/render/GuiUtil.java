package dev.based.vampyrix.api.util.render;

public class GuiUtil {
    public static boolean isMouseOver(float x, float y, float width, float height, float mouseX, float mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
