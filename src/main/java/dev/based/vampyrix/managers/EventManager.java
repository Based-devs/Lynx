package dev.based.vampyrix.managers;

import dev.based.vampyrix.Vampyrix;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.input.Keyboard;

import dev.based.vampyrix.api.module.Module;
import dev.based.vampyrix.api.util.Wrapper;
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
        if (!nullCheck() && event.getEntity() == mc.player) {
            this.getVampyrix().getModuleManager().getModules().stream().filter(Module::isEnabled).forEach(Module::onUpdate);
        }
    }
    
    @SubscribeEvent
	public void onKey(InputEvent.KeyInputEvent event) {
        Vampyrix.INSTANCE.getEventBus().post(event);
	}

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent event) {
        if (!nullCheck() && event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            this.getVampyrix().getModuleManager().getModules().stream().filter(Module::isEnabled).forEach(Module::onRender2D);
        }
    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent event) {
        if (!nullCheck()) {
            this.getVampyrix().getModuleManager().getModules().stream().filter(Module::isEnabled).forEach(Module::onRender3D);
        }
    }
}
