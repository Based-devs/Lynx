package com.based.lynx.module.movement;

import com.based.lynx.module.Category;
import com.based.lynx.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", "", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onUpdate(final TickEvent.ClientTickEvent event) {
        if (mc.world == null) {
            return;
        }

        if (mc.player.movementInput.moveForward == 0f && mc.player.movementInput.moveStrafe == 0f) return;

        if (!mc.player.isSprinting()) {
            mc.player.setSprinting(true);
        }
    }
}