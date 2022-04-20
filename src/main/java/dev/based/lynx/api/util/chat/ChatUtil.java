package dev.based.lynx.api.util.chat;

import dev.based.lynx.Lynx;
import dev.based.lynx.api.util.Wrapper;
import net.minecraft.util.text.TextComponentString;

import static com.mojang.realmsclient.gui.ChatFormatting.GRAY;
import static com.mojang.realmsclient.gui.ChatFormatting.RESET;

public class ChatUtil implements Wrapper {
    private static final String prefix = GRAY + "[" + Lynx.NAME + "]" + RESET;

    public static void sendMessage(String message) {
        sendMessage(message, true);
    }

    public static void sendMessage(String message, boolean waterMark) {
        if (mc.ingameGUI == null) return;

        if (waterMark)
            mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(prefix + " " + message));
        else mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(message));
    }
}
