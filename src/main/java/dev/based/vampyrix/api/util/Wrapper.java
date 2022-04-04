package dev.based.vampyrix.api.util;

import dev.based.vampyrix.Vampyrix;
import net.minecraft.client.Minecraft;

public interface Wrapper {
	
    Minecraft mc = Minecraft.getMinecraft();

    default boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    default Vampyrix getVampyrix() {
        return Vampyrix.INSTANCE;
    }

}
