package dev.based.vampyrix.impl.modules.impl.movement;

import dev.based.vampyrix.api.util.player.PlayerUtil;
import dev.based.vampyrix.impl.modules.Category;
import dev.based.vampyrix.impl.modules.Module;
import dev.based.vampyrix.impl.modules.Setting;
import net.minecraft.network.play.client.CPacketPlayer;

public class Step extends Module {

    private final Setting<Mode> mode = new Setting<>("Mode", Mode.PACKET).setDescription("How to step up the block");
    private final Setting<Float> stepHeight = new Setting<>("Step Height", 1.5F, 0.5F, 2.5F, 0.5F).setDescription("How high to step up the block").setVisibility(() -> !mode.getValue().equals(Mode.PACKET));

    public Step() {
        super("Step", "Lets you instantly step up blocks", Category.MOVEMENT);
    }

    @Override
    public void setupSettings() {
        registerSetting(mode);
        registerSetting(stepHeight);
    }

    @Override
    public void onDisable() {
        mc.player.stepHeight = 0.5F;
    }

    @Override
    public void onUpdate() {
        if (nullCheck()) {
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
                mc.player.stepHeight = stepHeight.getValue();
                break;
        }
    }

    public enum Mode {
        VANILLA, PACKET
    }
}
