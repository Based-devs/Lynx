package dev.based.vampyrix.api.clickgui.component;

public abstract class ADragComponent extends AComponent {
    public boolean dragging;
    private float dragX, dragY;

    public ADragComponent(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public void startDragging(int mouseX, int mouseY) {
        if (this.isWithin(mouseX, mouseY)) {
            this.dragging = true;
            this.dragX = mouseX - x;
            this.dragY = mouseY - y;
        }
    }

    public void stopDragging() {
        this.dragging = false;
    }

    public void updateDragPosition(int mouseX, int mouseY) {
        if (this.dragging) {
            this.x = (mouseX - this.dragX);
            this.y = (mouseY - this.dragY);
        }
    }
}
