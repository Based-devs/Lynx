package com.based.lynx.event;

import com.based.lynx.Lynx;
import com.based.lynx.module.Module;
import com.based.lynx.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventHandler {
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null) {
            return;
        }

        ModuleManager.onUpdate();
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (!Keyboard.getEventKeyState() || Keyboard.getEventKey() == 0) {
            return;
        }
        for (Module module : Lynx.moduleManager.getModules()) {
            if (module.getBind() != Keyboard.getEventKey()) continue;
            module.toggle();
        }
    }

    @SubscribeEvent
    public void onChatSend(ClientChatEvent event) {
        if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null) {
            return;
        }
        if (event.getMessage().startsWith(Lynx.commandManager.getPrefix())) {
            event.setCanceled(true);
            Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
            Lynx.commandManager.runCommand(event.getMessage());
        }
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null || !event.getType().equals(RenderGameOverlayEvent.ElementType.TEXT)) {
            return;
        }
    }
}
