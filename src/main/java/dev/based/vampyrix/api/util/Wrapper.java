package dev.based.vampyrix.api.util;

import dev.based.vampyrix.impl.Main;
import net.minecraft.client.Minecraft;

//Global functions
public interface Wrapper {
	
    Minecraft mc = Minecraft.getMinecraft();

    default boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    default Main getMain() {
        return Main.INSTANCE;
    }

}
