package com.based.lynx.module.movement;

import com.based.lynx.event.PacketReceiveEvent;
import com.based.lynx.module.Category;
import com.based.lynx.module.Module;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Velocity extends Module {

    public Velocity() {
        super("Velocity", "", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onPacket(PacketReceiveEvent event) {
        if (nullCheck()) return;
        if (event.getPacket() instanceof SPacketEntityVelocity) {
            if (((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId())
                event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketExplosion) event.setCanceled(true);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (nullCheck()) return;
        mc.player.entityCollisionReduction = 1.0f;
    }

}
