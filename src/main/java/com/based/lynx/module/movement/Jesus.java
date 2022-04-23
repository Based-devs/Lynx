package com.based.lynx.module.movement;

import com.based.lynx.event.AddCollisionBoxToListEvent;
import com.based.lynx.event.EventSendPacket;
import com.based.lynx.event.EventTarget;
import com.based.lynx.module.Category;
import com.based.lynx.module.Module;
import com.based.lynx.util.EntityUtil;
import com.based.lynx.util.Wrapper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Jesus extends Module {

    public Jesus() {
        super("Jesus", "", Category.MOVEMENT);
    }

    private static final AxisAlignedBB WATER_WALK_AA = new AxisAlignedBB(0.D, 0.D, 0.D, 1.D, 0.99D, 1.D);

    @Override
    public void onUpdate() {
        if (EntityUtil.isInWater(mc.player) && !mc.player.isSneaking()) {
            mc.player.motionY = 0.1;
            if (mc.player.getRidingEntity() != null && !(mc.player.getRidingEntity() instanceof EntityBoat)) {
                mc.player.getRidingEntity().motionY = 0.3;
            }
        }
    }

    @EventTarget
    public void onCollision(AddCollisionBoxToListEvent event) {
        if (mc.player != null
                && (event.getBlock() instanceof BlockLiquid)
                && (EntityUtil.isDrivenByPlayer(event.getEntity()) || event.getEntity() == mc.player)
                && !(event.getEntity() instanceof EntityBoat)
                && !mc.player.isSneaking()
                && mc.player.fallDistance < 3
                && !EntityUtil.isInWater(mc.player)
                && (EntityUtil.isAboveWater(mc.player, false) || EntityUtil.isAboveWater(mc.player.getRidingEntity(), false))
                && isAboveBlock(mc.player, event.getPos())) {
            AxisAlignedBB axisalignedbb = WATER_WALK_AA.offset(event.getPos());
            if (event.getEntityBox().intersects(axisalignedbb)) event.getCollidingBoxes().add(axisalignedbb);
            event.setCancelled(true);
        }
    }

    @EventTarget
    public void onPacket(EventSendPacket event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            if (EntityUtil.isAboveWater(mc.player, true) && !EntityUtil.isInWater(mc.player) && !isAboveLand(mc.player)) {
                int ticks = mc.player.ticksExisted % 2;
                // if (ticks == 0) ((CPacketPlayer) event.getPacket()).y += 0.02D;
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static boolean isAboveLand(Entity entity) {
        if (entity == null) return false;

        double y = entity.posY - 0.01;

        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++)
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); z++) {
                BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);

                if (Wrapper.getWorld().getBlockState(pos).getBlock().isFullBlock(Wrapper.getWorld().getBlockState(pos)))
                    return true;
            }

        return false;
    }

    private static boolean isAboveBlock(Entity entity, BlockPos pos) {
        return entity.posY >= pos.getY();
    }

}
