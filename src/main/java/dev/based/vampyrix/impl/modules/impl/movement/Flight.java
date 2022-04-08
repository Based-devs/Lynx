package dev.based.vampyrix.impl.modules.impl.movement;

import dev.based.vampyrix.impl.modules.Category;
import dev.based.vampyrix.impl.modules.Module;

public class Flight extends Module {

    public Flight() {
        super("Flight", "Lets you fly in survival mode", Category.MOVEMENT);
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
