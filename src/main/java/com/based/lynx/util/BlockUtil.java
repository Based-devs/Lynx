package com.based.lynx.util;

import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class BlockUtil {

    public static void placeCrystalOnBlock(BlockPos pos, EnumHand hand, boolean swing, boolean silentSwitch) {
        int oldSlot = Wrapper.getPlayer().inventory.currentItem;
        int crystalSlot = -1;

        for (int i = 0; i < 9; i++) {
            if (Wrapper.getPlayer().inventory.getStackInSlot(i).getItem() == Wrapper.getPlayer().getHeldItem(hand).getItem()) {
                crystalSlot = i;
                break;
            }
        }

        if (crystalSlot > -1) {
            Wrapper.getPlayer().inventory.currentItem = crystalSlot;
            Wrapper.getPlayer().connection.sendPacket(new CPacketHeldItemChange(crystalSlot));
        }

        Wrapper.getMinecraft().playerController.processRightClickBlock(Wrapper.getPlayer(), Wrapper.getMinecraft().world, pos, EnumFacing.UP, new Vec3d(0, 0, 0), hand);

        if (swing) {
            Wrapper.getPlayer().swingArm(hand);
        }

        if (silentSwitch) {
            Wrapper.getPlayer().inventory.currentItem = oldSlot;
            Wrapper.getPlayer().connection.sendPacket(new CPacketHeldItemChange(oldSlot));
        }
    }

}
