package dev.based.vampyrix.api.clickgui;

import dev.based.vampyrix.impl.clickgui.Frame;

public class Rect {

    public int x, y, width, height;
    public Frame parent;

    public Rect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isWithin(int mouseX, int mouseY) {
    	return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
