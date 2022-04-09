package dev.based.vampyrix.managers;

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
        this.getVampyrix().getEventBus().register(this);
    }
    
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if(!this.nullCheck() && event.getEntity() == mc.player) {
            this.getVampyrix().getModuleManager().getModules().stream().filter(Module::isEnabled).forEach(Module::onUpdate);
        }
    }
    
    @SubscribeEvent
	public void onKey(InputEvent.KeyInputEvent event) {
		if(!this.nullCheck() && Keyboard.getEventKeyState() && Keyboard.getEventKey() > 1) {
            this.getVampyrix().getModuleManager().getModules().stream().filter(module -> module.getKeybind().getValue().getKeyCode() == Keyboard.getEventKey()).forEach(Module::toggle);
        }
	}
}
