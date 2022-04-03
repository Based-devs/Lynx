package dev.based.vampyrix.api.clickgui;

public abstract class AToggleContainer extends AComponent {
	
    protected final Toggleable toggleable;

    public AToggleContainer(Toggleable toggleable, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.toggleable = toggleable;
    }

    public abstract int getTotalHeight();
    
    public Toggleable getToggleable() {
        return toggleable;
    }

}
