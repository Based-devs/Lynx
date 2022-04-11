package dev.based.vampyrix.api.event;

import dev.based.vampyrix.Vampyrix;
import dev.based.vampyrix.api.event.entity.LivingUpdateEvent;
import dev.based.vampyrix.api.event.render.RenderEvent;
import dev.based.vampyrix.api.event.system.KeyEvent;
import dev.based.vampyrix.api.util.Wrapper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class EventManager implements Wrapper {
    public EventManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        Vampyrix.INSTANCE.getEventBus().post(LivingUpdateEvent.get(event.getEntityLiving()));
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        Vampyrix.INSTANCE.getEventBus().post(KeyEvent.get(Keyboard.getEventKey()));
    }

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent event) {
        this.getVampyrix().getEventBus().post(new RenderEvent.Render2D(event.getPartialTicks()));
    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent event) {
        this.getVampyrix().getEventBus().post(new RenderEvent.Render3D(event.getPartialTicks()));
    }
}
