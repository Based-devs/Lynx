package dev.based.vampyrix.api.util;

import dev.based.vampyrix.Lynx;
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
