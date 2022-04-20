package dev.based.vampyrix.api.event;

import dev.based.vampyrix.api.event.events.*;
import dev.based.vampyrix.api.util.Wrapper;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventManager implements Wrapper {
    public EventManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        this.getLynx().getEventBus().post(new ClientTickEvent(ClientEvent.Era.POST));
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        this.getLynx().getEventBus().post(LivingUpdateEvent.get(event.getEntityLiving()));
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        this.getLynx().getEventBus().post(new KeyEvent(Keyboard.getEventKey()));
    }

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent event) {
        this.getLynx().getEventBus().post(new RenderEvent.Render2D(event.getPartialTicks()));
    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent event) {
        this.getLynx().getEventBus().post(new RenderEvent.Render3D(event.getPartialTicks()));
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        if (this.getLynx().getEventBus().post(new ChatEvent(event.getOriginalMessage()))) event.setCanceled(true);
    }
}
