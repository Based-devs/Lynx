package dev.based.lynx.impl.modules.movement;

import dev.based.lynx.api.module.Category;
import dev.based.lynx.api.module.Module;
import dev.based.lynx.api.module.setting.Setting;
import dev.based.lynx.api.util.player.PlayerUtil;
import net.minecraft.network.play.client.CPacketPlayer;

public class Step extends Module {
    private final Setting<Mode> mode = new Setting<>("Mode", Mode.PACKET).setDescription("How to step up the block");
    private final Setting<Float> stepHeight = new Setting<>("Step Height", 1.5F, 0.5F, 2.5F, 0.5F).setDescription("How high to step up the block").setVisibility(() -> !mode.getValue().equals(Mode.PACKET));

    public Step() {
        super("Step", "Lets you instantly step up blocks", Category.MOVEMENT);
    }

    @Override
    public void setupSettings() {
        this.registerSetting(this.mode);
        this.registerSetting(this.stepHeight);
    }

    @Override
    public void onDisable() {
        mc.player.stepHeight = 0.5F;
    }

    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }

        switch (mode.getValue()) {
            case PACKET:
                if (mc.player.collidedHorizontally && !PlayerUtil.isInLiquid() && !mc.player.isOnLadder()) {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1, mc.player.posZ, mc.player.onGround));
                    mc.player.setPosition(mc.player.posX, mc.player.posY + 1, mc.player.posZ);
                }

                break;
            case VANILLA:
                mc.player.stepHeight = this.stepHeight.getValue();
                break;
        }
    }

    public enum Mode {
        VANILLA, PACKET
    }
}
