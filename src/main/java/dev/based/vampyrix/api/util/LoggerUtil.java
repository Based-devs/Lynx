package dev.based.vampyrix.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

import java.util.logging.Logger;

public class LoggerUtil
{
	public static void sendMessage(String message)
	{
		sendMessage(message, true);
	}

	public static void sendMessage(String message, boolean waterMark)
	{
		StringBuilder messageBuilder = new StringBuilder();
		if (waterMark) messageBuilder.append("&7[&5Vampyrix&7] &r");
		messageBuilder.append("&7").append(message);
		if (Minecraft.getMinecraft().ingameGUI != null) {
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(messageBuilder.toString().replace("&", "\u00A7")));
		}
		Logger.getGlobal().info(messageBuilder.toString());
	}

	public static void sendOverwriteClientMessage ( String string ) {

	}
}
