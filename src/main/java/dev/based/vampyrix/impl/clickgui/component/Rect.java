package dev.based.vampyrix.impl.clickgui.component;

import dev.based.vampyrix.impl.clickgui.frame.Frame;

public class Rect {

    public float x, y, width, height;
    public Frame parent;

    public Rect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isWithin(int mouseX, int mouseY) {
    	return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
