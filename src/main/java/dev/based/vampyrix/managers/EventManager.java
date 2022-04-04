package dev.based.vampyrix.managers;

import org.lwjgl.input.Keyboard;

import dev.based.vampyrix.impl.modules.Module;
import dev.based.vampyrix.api.util.Wrapper;
import dev.based.vampyrix.Vampyrix;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class EventManager implements Wrapper {

    public EventManager() {
    	MinecraftForge.EVENT_BUS.register(this);
        getVampyrix().getEventBus().register(this);
    }
    
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if(!nullCheck() && event.getEntity() == mc.player) {
            for (Module m : Vampyrix.INSTANCE.getModuleManager().getModules()) {
                if(m.isEnabled()) {
                    m.onUpdate();
                }
            }
        }
    }
    
    @SubscribeEvent
	public void onKey(InputEvent.KeyInputEvent event) {
		if(nullCheck()) {
            return;
        }

        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() > 1) {
                for (Module m : Vampyrix.INSTANCE.getModuleManager().getModules()) {
                    if (m.getKeybind().getValue().getKeyCode() == Keyboard.getEventKey()) {
                        m.toggle();
                    }
                }
            }
        }
	}

}
