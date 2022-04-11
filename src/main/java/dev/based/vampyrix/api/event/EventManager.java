package dev.based.vampyrix.api.event;

import dev.based.vampyrix.Vampyrix;
import dev.based.vampyrix.api.util.Wrapper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class EventManager implements Wrapper {
    public EventManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        Vampyrix.INSTANCE.getEventBus().post(event);
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        Vampyrix.INSTANCE.getEventBus().post(event);
    }

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent event) {
        this.getVampyrix().getEventBus().post(event);
    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent event) {
        this.getVampyrix().getEventBus().post(event);
    }
}
