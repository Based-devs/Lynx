package com.based.lynx;

import com.mojang.realmsclient.gui.ChatFormatting;

import java.awt.Font;

import com.based.lynx.EventHandler;
import com.based.lynx.command.CommandManager;
import com.based.lynx.config.Config;
import com.based.lynx.event.EventManager;
import com.based.lynx.module.ModuleManager;
import com.based.lynx.setting.SettingManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "lynx", name = "Lynx", version = "1.0.0")
public class Lynx {
    public static final String name = "Lynx";
    public static final String version = "1.0.0";
    public static ModuleManager moduleManager;
    public static SettingManager settingManager;
    public static CommandManager commandManager;
    public static final EventManager EVENT_MANAGER;

    public static void SendMessage(String string) {
        if (Minecraft.getMinecraft().ingameGUI != null || Minecraft.getMinecraft().player == null) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("\u00a77" + ChatFormatting.BLUE + "[Lynx]\u00a7f " + ChatFormatting.RESET + string));
        }
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {
        commandManager = new CommandManager();
        settingManager = new SettingManager();
        moduleManager = new ModuleManager();
        Config.loadConfig();
        Runtime.getRuntime().addShutdownHook(new Config());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    static {
        EVENT_MANAGER = new EventManager();
    }
}
