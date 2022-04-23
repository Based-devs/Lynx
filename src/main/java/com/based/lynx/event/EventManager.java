package com.based.lynx.event;

import com.based.lynx.Lynx;
import com.based.lynx.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventManager {
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().player == null) {
            return;
        }
        Lynx.SendMessage("OnUpdate");
        ModuleManager.onUpdate();
    }
}
