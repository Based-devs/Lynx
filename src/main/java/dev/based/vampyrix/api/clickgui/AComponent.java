package dev.based.vampyrix.api.clickgui;

public abstract class AComponent extends Rect {
	
    public AComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public abstract void render(int mouseX, int mouseY, float partialTicks);

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);

    public abstract void mouseReleased(int mouseX, int mouseY, int mouseButton);

    public abstract void keyTyped(char keyChar, int keyCode);

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
