package com.based.lynx.module.movement;

import com.based.lynx.module.Category;
import com.based.lynx.module.Module;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoSlow extends Module {

    public NoSlow() {
        super("NoSlow", "", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onInput(InputUpdateEvent event) {
        //2b2t bypass
        mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
        //Thanks FencingF
    }
}