package dev.based.lynx.impl.modules.movement;

import dev.based.lynx.api.module.Category;
import dev.based.lynx.api.module.Module;

public class Flight extends Module {
    public Flight() {
        super("Flight", "Lets you fly in survival mode", Category.MOVEMENT);
    }

    @Override
    public void setupSettings() {
    }

    @Override
    public void onDisable() {
        mc.player.capabilities.isFlying = false;
    }

    @Override
    public void onUpdate() {
        if (nullCheck()) {
            return;
        }

        mc.player.capabilities.isFlying = true;
    }
}
