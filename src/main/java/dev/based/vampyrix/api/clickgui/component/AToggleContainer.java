package dev.based.vampyrix.api.clickgui.component;

public abstract class AToggleContainer extends AComponent {
    protected final Toggleable toggleable;

    public AToggleContainer(Toggleable toggleable, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.toggleable = toggleable;
    }

    public abstract float getTotalHeight();

    public Toggleable getToggleable() {
        return toggleable;
    }
}
