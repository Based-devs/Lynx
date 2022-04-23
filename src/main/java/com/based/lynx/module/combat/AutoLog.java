package com.based.lynx.module.combat;

import com.based.lynx.module.Category;
import com.based.lynx.module.Module;
import com.based.lynx.setting.Setting;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoLog extends Module {

    private final Setting<Float> health = new Setting<>("Health", 10F, 1F, 36F, 1F)
            .setDescription("The health at which to log out");

    public AutoLog() {
        super("AutoLog", "", Category.COMBAT);
        addSetting(health);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (nullCheck()) return;

        if (mc.player.getHealth() <= health.getValue()) {
            disable();
            mc.world.sendQuittingDisconnectingPacket();
            mc.loadWorld(null);
            mc.displayGuiScreen(new GuiMainMenu());
        }
    }
}