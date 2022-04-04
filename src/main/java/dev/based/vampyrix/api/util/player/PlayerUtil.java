package dev.based.vampyrix.api.util.player;

import dev.based.vampyrix.api.util.Wrapper;

public class PlayerUtil implements Wrapper {

    public static boolean isInLiquid() {
        return mc.player.isInWater() || mc.player.isInLava();
    }

}
