package dev.based.vampyrix.api.event;

import org.lwjgl.input.Keyboard;

import dev.based.vampyrix.api.module.Module;
import dev.based.vampyrix.api.util.Wrapper;
import dev.based.vampyrix.impl.Main;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

//Proccesses and handles events
public class EventManager implements Wrapper {

    public static EventManager INSTANCE;
    
    public EventManager() {
    	MinecraftForge.EVENT_BUS.register(this);
    }
    
    public static EventManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new EventManager();
        }
        return INSTANCE;
    }
    
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if(!nullCheck() && event.getEntity() == mc.player) {
            for (Module m : Main.INSTANCE.getModuleManager().getModules()) {
                if(m.isToggled()) {
                    m.onUpdate();
                }
            }
        }
    }
    
    @SubscribeEvent
	public void onKey(InputEvent.KeyInputEvent e) {
		if(nullCheck())
			return;
		try {
			if(Keyboard.isCreated()) {
				if(Keyboard.getEventKeyState() ) {
					int keyCode = Keyboard.getEventKey();
					if(keyCode <= 0)
						return;
					for(Module m : Main.INSTANCE.getModuleManager().getModules()) {
						if(m.getKeybind() == Keyboard.getEventKey()) {
							m.toggle();
						}
					}
				}
			}
		} catch (Exception q) {q.printStackTrace(); }
	}

}
