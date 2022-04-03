package dev.based.vampyrix.api.clickgui;

public abstract class ADragComponent extends AComponent {
	
    private int dragX, dragY;
    public boolean dragging;
	
    public ADragComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void startDragging(int mouseX, int mouseY) {
        if (isWithin(mouseX, mouseY)) {
            dragging = true;
            dragX = mouseX - x;
            dragY = mouseY - y;
        }
    }

    public void stopDragging(int mouseX, int mouseY) {
        this.dragging = false;
    }

    public void updateDragPosition(int mouseX, int mouseY) {
        if (this.dragging) {
            x = (mouseX - dragX);
            y = (mouseY - dragY);
        }
    }

}
