package com.based.lynx.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class LoggerUtil {
    public static void sendMessage(String message) {
        LoggerUtil.sendMessage(message, true);
    }

    public static void sendMessage(String message, boolean waterMark) {
        StringBuilder messageBuilder = new StringBuilder();
        if (waterMark) {
            messageBuilder.append("&9[&9Lynx&9] ");
        }
        messageBuilder.append("&7").append(message);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(messageBuilder.toString().replace("&", "\u00a7")));
    }
}
