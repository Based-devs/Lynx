package dev.based.vampyrix.api.util.maths;

import dev.based.vampyrix.api.util.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class InterpolationUtil implements Wrapper {
    public static Vec3d getInterpolatedPosition(Entity entityIn) {
        return new Vec3d(entityIn.lastTickPosX, entityIn.lastTickPosY, entityIn.lastTickPosZ).add(getInterpolatedAmount(entityIn, mc.getRenderPartialTicks()));
    }

    public static Vec3d getInterpolatedAmount(Entity entity, float partialTicks) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * partialTicks, (entity.posY - entity.lastTickPosY) * partialTicks, (entity.posZ - entity.lastTickPosZ) * partialTicks);
    }
}
