package com.based.lynx.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public interface Wrapper {
    Minecraft mc = Minecraft.getMinecraft();

    static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    static EntityPlayerSP getPlayer() {
        return Wrapper.getMinecraft().player;
    }

    static Entity getRenderEntity() {
        return mc.getRenderViewEntity();
    }

    static World getWorld() {
        return Wrapper.getMinecraft().world;
    }

    static int getKey(String keyname) {
        return Keyboard.getKeyIndex(keyname.toUpperCase());
    }
}
