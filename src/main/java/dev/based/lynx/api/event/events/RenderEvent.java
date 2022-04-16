package dev.based.lynx.api.event.events;

import dev.based.lynx.api.event.ClientEvent;

public abstract class RenderEvent extends ClientEvent {
    private final float partialTicks;

    protected RenderEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public static class Render2D extends RenderEvent {
        public Render2D(float partialTicks) {
            super(partialTicks);
        }
    }

    public static class Render3D extends RenderEvent {
        public Render3D(float partialTicks) {
            super(partialTicks);
        }
    }
}
