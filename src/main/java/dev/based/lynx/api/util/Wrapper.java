package dev.based.lynx.api.util;

import dev.based.lynx.Lynx;
import net.minecraft.client.Minecraft;

public interface Wrapper {
    Minecraft mc = Minecraft.getMinecraft();

    default boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    default Lynx getLynx() {
        return Lynx.INSTANCE;
    }
}
