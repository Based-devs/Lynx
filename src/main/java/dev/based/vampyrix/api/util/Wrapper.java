package dev.based.vampyrix.api.util;

import dev.based.vampyrix.impl.Vampyrix;
import net.minecraft.client.Minecraft;

//Global functions
public interface Wrapper {
	
    Minecraft mc = Minecraft.getMinecraft();

    default boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    default Vampyrix getMain() {
        return Vampyrix.INSTANCE;
    }

}
