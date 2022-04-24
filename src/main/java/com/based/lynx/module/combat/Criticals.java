package com.based.lynx.module.combat;

import com.based.lynx.event.PacketEvent;
import com.based.lynx.module.Category;
import com.based.lynx.module.Module;

import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals", "", Category.COMBAT);
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (nullCheck()) return;

        if (event.getPacket() instanceof CPacketUseEntity && ((CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
        }
    }
}