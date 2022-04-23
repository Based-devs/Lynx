package com.based.lynx.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class Wrapper {
    private static FontRenderer fontRenderer;
    public static Minecraft mc;

    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public static EntityPlayerSP getPlayer() {
        return Wrapper.getMinecraft().player;
    }

    public static Entity getRenderEntity() {
        return mc.getRenderViewEntity();
    }

    public static World getWorld() {
        return Wrapper.getMinecraft().world;
    }

    public static int getKey(String keyname) {
        return Keyboard.getKeyIndex(keyname.toUpperCase());
    }

    public static FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    static {
        mc = Minecraft.getMinecraft();
    }
}
