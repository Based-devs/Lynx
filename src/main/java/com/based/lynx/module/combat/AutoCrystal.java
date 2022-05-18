package com.based.lynx.module.combat;

import com.based.lynx.event.PacketEvent;
import com.based.lynx.module.Category;
import com.based.lynx.module.Module;
import com.based.lynx.setting.Setting;
import com.based.lynx.util.BlockUtil;
import com.based.lynx.util.EntityUtil;
import com.based.lynx.util.Timer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AutoCrystal extends Module {

    public AutoCrystal() {
        super("AutoCrystal", "Automatically places and breaks crystals to deal damage", Category.COMBAT);
        /*addSetting(logic);
        addSetting(enemyRange);
        addSetting(place);
        addSetting(break);
        addSetting(override);
        addSetting(render);
    }

    public enum Logic {
        PlaceBreak,
        BreakPlace
    }

    public enum Switch {
        Silent,
        Auto,
        None
    }

    // uh idk
    public final Setting<Logic> logic = new Setting<>("Logic", Logic.PlaceBreak)
            .setDescription("What order to perform actions in");


    // targeting
    public final Setting<Float> enemyRange = new Setting<>("EnemyRange", 10F, 1F, 20F, 0.1f)
            .setDescription("The range to target enemies");


    // placing
    public final Setting<Boolean> place = new Setting<>("Place", true)
            .setDescription("Automatically place crystals");

    public final Setting<Float> placeRange = new Setting<>("PlaceRange", 5.0f, 0.0f, 10.0f, 0.1f)
            .setDescription("The max distance to place crystals").setParentSetting(place);

    public final Setting<Float> placeWallRange = new Setting<>("PlaceWallRange", 3.5f, 0.0f, 10.0f, 0.1f)
            .setDescription("The max distance to place crystals through walls").setParentSetting(place);

    public final Setting<Double> placeDelay = new Setting<>("PlaceDelay", 0, 0, 500, 1)
            .setDescription("The delay between placing crystals").setParentSetting(place);

    public final Setting<Float> minPlaceDamage = new Setting<>("MinPlaceDamage", 7.0f, 0.0f, 36.0f, 1F)
            .setDescription("The minimum damage to deal to place crystals").setParentSetting(place);

    public final Setting<Float> maxSelfPlace = new Setting<>("MaxSelfPlace", 8.0f, 0.0f, 36.0f, 1F)
            .setDescription("The maximum damage to deal to yourself when placing crystals").setParentSetting(place);

    public final Setting<Boolean> placeSwing = new Setting<>("PlaceSwing", true)
            .setDescription("Whether to swing when placing crystals").setParentSetting(place);


    // breaking
    public final Setting<Boolean> break = new Setting<>("Break", true)
            .setDescription("Automatically break crystals");

    public final Setting<Float> breakRange = new Setting<>("BreakRange", 5.0f, 0.0f, 10.0f, 0.1f)
            .setDescription("The max distance to break crystals").setParentSetting(break);

    public final Setting<Float> breakWallRange = new Setting<>("BreakWallRange", 3.5f, 0.0f, 10.0f, 0.1f)
            .setDescription("The max distance to break crystals through walls").setParentSetting(break);

    public final Setting<Double> breakDelay = new Setting<>("BreakDelay", 0, 0, 500, 1)
            .setDescription("The delay between breaking crystals").setParentSetting(break);

    public final Setting<Float> minBreakDamage = new Setting<>("MinBreakDamage", 7.0f, 0.0f, 36.0f, 1F)
            .setDescription("The minimum damage to deal to break crystals").setParentSetting(break);

    public final Setting<Float> maxSelfBreak = new Setting<>("MaxSelfBreak", 8.0f, 0.0f, 36.0f, 1F)
            .setDescription("The maximum damage to deal to yourself when breaking crystals").setParentSetting(break);

    public final Setting<Boolean> breakSwing = new Setting<>("BreakSwing", true)
            .setDescription("Whether to swing when breaking crystals").setParentSetting(break);

    public final Setting<Boolean> antiSuicide = new Setting<>("AntiSuicide", true)
            .setDescription("Do not break crystals if they will pop / kill you").setParentSetting(break);

    public final Setting<Switch> switchMode = new Setting<>("Switch", Switch.Silent)
            .setDescription("How to switch to a sword").setParentSetting(break);

    public final Setting<Boolean> packet = new Setting<>("Packet", true)
            .setDescription("Whether to use packets to break crystals").setParentSetting(break);

    public final Setting<Boolean> rotate = new Setting<>("Rotate", true)
            .setDescription("Whether to rotate when breaking crystals").setParentSetting(break);

    public final Setting<Float> ticksExisted = new Setting<>("TicksExisted", 1f, 0.0f, 5f, 1F)
            .setDescription("The amount of ticks that the crystal has existed for before breaking").setParentSetting(break);

    public final Setting<Boolean> oneDotThirteen = new Setting<>("1.13", false)
            .setDescription("someone change this desc idk what it does").setParentSetting(break);

    public final Setting<Boolean> terrainTrace = new Setting<>("TerrainTrace", true)
            .setDescription("idk what this does").setParentSetting(break);

    public final Setting<Boolean> antiWeakness = new Setting<>("AntiWeakness", true)
            .setDescription("Switches to a sword if you have the weakness effect applied").setParentSetting(break);

    public final Setting<Boolean> raytrace = new Setting<>("Raytrace", true)
            .setDescription("Checks you can see the crystal before breaking it").setParentSetting(break);


    // override
    public final Setting<Boolean> override = new Setting<>("Override", true)
            .setDescription("Override the minimum damage in certain situations");

    public final Setting<Float> facePlaceHP = new Setting<>("FacePlaceHP", 10.0F, 0.0F, 36.0F, 1F)
            .setDescription("Override minimum damage if the player is at this health or lower").setParentSetting(override);

    public final Setting<AtomicInteger> facePlaceBind = new Setting<>("FacePlaceBind", new AtomicInteger(0))
            .setDescription("The key to hold to force face placing").setParentSetting(override);

    public final Setting<Float> facePlaceArmor = new Setting<>("FacePlaceArmor", 20.0F, 0.0F, 100.0F, 1F)
            .setDescription("Forces face placing if one of the player's armor pieces is at this health percentage or lower").setParentSetting(override);


    // render
    public final Setting<Boolean> render = new Setting<>("Render", true)
            .setDescription("Render highlights");

    public final Setting<Boolean> box = new Setting<>("Box", true)
            .setDescription("Renders a box").setParentSetting(render);

    public final Setting<Boolean> outline = new Setting<>("Outline", true)
            .setDescription("Renders an outline").setParentSetting(render);

    public final Setting<Float> lineWidth = new Setting<>("LineWidth", 1.0F, 0.1F, 3.0F, 0.1F)
            .setDescription("Line width of the outline").setParentSetting(render);

    public final Setting<Float> fadeTime = new Setting<>("FadeTime", 300F, 0.0F, 1000.0F, 0.1F)
            .setDescription("Time it takes for the highlight to fade").setParentSetting(render);

    public final Setting<Color> colour = new Setting<>("Color", Color.WHITE)
            .setDescription("The colour of the highlight").setParentSetting(render);

    public HashMap<BlockPos, Long> renderMap = new HashMap<>();
    public Timer placeTimer = new Timer();
    public Timer breakTimer = new Timer();
    public BlockPos finalPlacePos = null;
    public float rotYaw;
    public float rotPitch;
    public boolean isRotating;
    public int spoofed = 0;

    @Override
    public void onUpdate() {
        if (nullCheck()) {
            return;
        }

        // Is this bad? yes. can i be bothered to fix it? no.
        List<EntityPlayer> targets = mc.world.playerEntities.stream().filter(e -> e.getHealth() > 0 && e.getDistance(mc.player) <= enemyRange.getValue()).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(e -> mc.player.getDistance(e)));
        EntityPlayer target = targets.get(0);

        if (target == null || target.isDead) {
            return;
        }

        List<BlockPos> placeList = new ArrayList<>(getSphere(new BlockPos(mc.player.getPositionVector()), placeRange.getValue(), placeRange.getValue().intValue(), false, true, 0).stream().filter(pos -> canPlaceCrystal(pos, true, oneDotThirteen.getValue())).collect(Collectors.toList()));
        if (logic.getValue() == Logic.BreakPlace) {
            breakCrystals(target);
            placeCrystals(target, placeList);
        } else if (logic.getValue() == Logic.PlaceBreak) {
            placeCrystals(target, placeList);
            breakCrystals(target);
        }
    }

    @Override
    public void onEnable() {
        placeTimer.reset();
        breakTimer.reset();
    }

    public void placeCrystals(EntityLivingBase target, List<BlockPos> placements) {
        float minDamage = (target.getHealth() + target.getAbsorptionAmount() < facePlaceHP.getValue()) || shouldArmorFacePlace(target) || Keyboard.isKeyDown(facePlaceBind.getValue().get()) ? 1.5f : minPlaceDamage.getValue();
        NonNullList<BlockPos> finalPlacements = NonNullList.create();
        for (BlockPos pos : placements) {
            if (calculateDamage(pos, target, terrainTrace.getValue()) < minDamage) continue;
            if (calculateDamage(pos, mc.player, terrainTrace.getValue()) > maxSelfPlace.getValue()) continue;
            if (mc.player.getPositionVector().distanceTo(new Vec3d(pos)) > placeRange.getValue() && canSeePos(pos))
                continue;
            if (mc.player.getPositionVector().distanceTo(new Vec3d(pos)) > placeWallRange.getValue() && !canSeePos(pos))
                continue;
            if (antiSuicide.getValue() && calculateDamage(pos, mc.player, terrainTrace.getValue()) > (mc.player.getHealth() + mc.player.getAbsorptionAmount()))
                continue;
            if (raytrace.getValue() && !canSeePos(pos)) continue;
            finalPlacements.add(pos);
        }
        finalPlacePos = finalPlacements.stream().max(Comparator.comparingDouble(pos -> calculateDamage(pos, target, terrainTrace.getValue()))).orElse(null);
        if (finalPlacePos != null) {
            if (rotate.getValue()) {
                rotateTo(finalPlacePos);
            }
            renderMap.put(finalPlacePos, System.currentTimeMillis());
            EnumHand crystalHand = mc.player.getHeldItemOffhand().getItem() instanceof ItemEndCrystal ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
            if (switchMode.getValue() == Switch.Auto) {
                mc.player.inventory.currentItem = findHotbarBlock(ItemEndCrystal.class);
            }

            if (placeTimer.hasTimePassed(placeDelay.getValue(), Timer.TimeFormat.MILLISECONDS)) {
                BlockUtil.placeCrystalOnBlock(finalPlacePos, crystalHand, placeSwing.getValue(), switchMode.getValue() == Switch.Silent);
                placeTimer.reset();
            }
        }
    }

    public void breakCrystals(EntityLivingBase target) {
        float minDamage = (target.getHealth() + target.getAbsorptionAmount() < facePlaceHP.getValue()) || shouldArmorFacePlace(target) || Keyboard.isKeyDown(facePlaceBind.getValue().get()) ? 1.5f : minBreakDamage.getValue();
        for (Entity entity : mc.world.loadedEntityList) {
            if (!(entity instanceof EntityEnderCrystal) || entity.getPositionVector().distanceTo(mc.player.getPositionVector()) > breakRange.getValue() && canSeePos(new BlockPos(entity.getPositionVector())) || entity.getPositionVector().distanceTo(mc.player.getPositionVector()) > breakWallRange.getValue() && !canSeePos(new BlockPos(entity.getPositionVector()))) {
                continue;
            }
            if (calculateDamage(entity, target, terrainTrace.getValue()) > minDamage && calculateDamage(entity, mc.player, terrainTrace.getValue()) < maxSelfBreak.getValue()) {
                if (rotate.getValue()) {
                    rotateTo(entity);
                }
                if (breakTimer.hasTimePassed(breakDelay.getValue(), Timer.TimeFormat.MILLISECONDS) || entity.ticksExisted > ticksExisted.getValue()) {
                    int swordSlot = findHotbarBlock(ItemSword.class);
                    int oldSlot = mc.player.inventory.currentItem;
                    if (antiWeakness.getValue()) {
                        mc.player.inventory.currentItem = swordSlot;
                    }
                    EntityUtil.attackEntity(entity, packet.getValue(), breakSwing.getValue());
                    if (antiWeakness.getValue()) {
                        mc.player.inventory.currentItem = oldSlot;
                    }
                    breakTimer.reset();
                }
            }
        }
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        try {
            if (event.getPacket() instanceof SPacketSoundEffect) {
                if (((SPacketSoundEffect) event.getPacket()).getCategory() == SoundCategory.BLOCKS && ((SPacketSoundEffect) event.getPacket()).getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                    for (Entity entity : mc.world.loadedEntityList) {
                        if (entity instanceof EntityEnderCrystal && entity.getDistance(((SPacketSoundEffect) event.getPacket()).getX(), ((SPacketSoundEffect) event.getPacket()).getY(), ((SPacketSoundEffect) event.getPacket()).getZ()) < 6.0f) {
                            entity.setDead();
                        }
                    }
                }
            }
        } catch (Exception ignored) {

        }
    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent event) {
        try {
            for (Map.Entry<BlockPos, Long> map : renderMap.entrySet()) {
                if (System.currentTimeMillis() - map.getValue() < fadeTime.getValue()) {
                    float finalAlpha = 180;
                    drawBoxESP(map.getKey(), colour.getValue(), finalAlpha, lineWidth.getValue(), outline.getValue(), box.getValue(), finalAlpha);
                } else {
                    renderMap.remove(map.getKey());
                }
            }
        } catch (Exception ignored) {

        }
    }

    @Override
    public void onDisable() {
        placeTimer.reset();
        breakTimer.reset();
    }

    public static void drawBoxESP(BlockPos pos, Color color, float alpha, float lineWidth, boolean outline, boolean box, float boxAlpha) {
        AxisAlignedBB bb = new AxisAlignedBB((double) pos.getX() - mc.getRenderManager().viewerPosX, (double) pos.getY() - mc.getRenderManager().viewerPosY, (double) pos.getZ() - mc.getRenderManager().viewerPosZ, (double) (pos.getX() + 1) - mc.getRenderManager().viewerPosX, (double) (pos.getY() + 1) - mc.getRenderManager().viewerPosY, (double) (pos.getZ() + 1) - mc.getRenderManager().viewerPosZ);
        ICamera camera = new Frustum();
        camera.setPosition(Objects.requireNonNull(mc.getRenderViewEntity()).posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);
        if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + mc.getRenderManager().viewerPosX, bb.minY + mc.getRenderManager().viewerPosY, bb.minZ + mc.getRenderManager().viewerPosZ, bb.maxX + mc.getRenderManager().viewerPosX, bb.maxY + mc.getRenderManager().viewerPosY, bb.maxZ + mc.getRenderManager().viewerPosZ))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(lineWidth);
            if (box) {
                RenderGlobal.renderFilledBox(bb, (float) color.getRed() / 255.0f, (float) color.getGreen() / 255.0f, (float) color.getBlue() / 255.0f, boxAlpha / 255.0f);
            }
            if (outline) {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, (float) color.getRed() / 255.0f, (float) color.getGreen() / 255.0f, (float) color.getBlue() / 255.0f, alpha / 255.0f);
            }
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    public static int findHotbarBlock(Class clazz) {
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY) continue;
            if (clazz.isInstance(stack.getItem())) {
                return i;
            }
            if (!(stack.getItem() instanceof ItemBlock) || !clazz.isInstance(block = ((ItemBlock) stack.getItem()).getBlock()))
                continue;
            return i;
        }
        return -1;
    }

    public void rotateTo(BlockPos pos) {
        float[] angle = calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d(pos));
        rotYaw = angle[0];
        rotPitch = angle[1];
        isRotating = true;
    }

    public void rotateTo(Entity entity) {
        float[] angle = calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), entity.getPositionVector());
        rotYaw = angle[0];
        rotPitch = angle[1];
        isRotating = true;
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && isRotating && rotate.getValue()) {
            // FIX THIS USING AN ACCESSOR
            // ((CPacketPlayer) event.getPacket()).yaw = rotYaw;
            // ((CPacketPlayer) event.getPacket()).pitch = rotPitch;

            spoofed++;

            if (spoofed >= 1) {
                isRotating = false;
                spoofed = 0;
            }
        }
    }

    public boolean shouldArmorFacePlace(EntityLivingBase target) {
        for (ItemStack stack : target.getArmorInventoryList()) {
            if (stack == null || stack.getItem() == Items.AIR || stack != null && ((stack.getMaxDamage() - stack.getItemDamage()) / stack.getMaxDamage()) * 100.0f < facePlaceArmor.getValue())
                return true;
        }
        return false;
    }

    public static boolean rayTraceSolidCheck(Vec3d start, Vec3d end, boolean shouldIgnore) {
        if (!Double.isNaN(start.x) && !Double.isNaN(start.y) && !Double.isNaN(start.z)) {
            if (!Double.isNaN(end.x) && !Double.isNaN(end.y) && !Double.isNaN(end.z)) {
                int currX = MathHelper.floor(start.x);
                int currY = MathHelper.floor(start.y);
                int currZ = MathHelper.floor(start.z);
                int endX = MathHelper.floor(end.x);
                int endY = MathHelper.floor(end.y);
                int endZ = MathHelper.floor(end.z);
                BlockPos blockPos = new BlockPos(currX, currY, currZ);
                IBlockState blockState = mc.world.getBlockState(blockPos);
                net.minecraft.block.Block block = blockState.getBlock();
                if ((blockState.getCollisionBoundingBox(mc.world, blockPos) != Block.NULL_AABB) && block.canCollideCheck(blockState, false) && (getBlocks().contains(block) || !shouldIgnore)) {
                    return true;
                }
                double seDeltaX = end.x - start.x;
                double seDeltaY = end.y - start.y;
                double seDeltaZ = end.z - start.z;
                int steps = 200;
                while (steps-- >= 0) {
                    if (Double.isNaN(start.x) || Double.isNaN(start.y) || Double.isNaN(start.z)) return false;
                    if (currX == endX && currY == endY && currZ == endZ) return false;
                    boolean unboundedX = true;
                    boolean unboundedY = true;
                    boolean unboundedZ = true;
                    double stepX = 999.0;
                    double stepY = 999.0;
                    double stepZ = 999.0;
                    double deltaX = 999.0;
                    double deltaY = 999.0;
                    double deltaZ = 999.0;
                    if (endX > currX) {
                        stepX = currX + 1.0;
                    } else if (endX < currX) {
                        stepX = currX;
                    } else {
                        unboundedX = false;
                    }
                    if (endY > currY) {
                        stepY = currY + 1.0;
                    } else if (endY < currY) {
                        stepY = currY;
                    } else {
                        unboundedY = false;
                    }
                    if (endZ > currZ) {
                        stepZ = currZ + 1.0;
                    } else if (endZ < currZ) {
                        stepZ = currZ;
                    } else {
                        unboundedZ = false;
                    }
                    if (unboundedX) deltaX = (stepX - start.x) / seDeltaX;
                    if (unboundedY) deltaY = (stepY - start.y) / seDeltaY;
                    if (unboundedZ) deltaZ = (stepZ - start.z) / seDeltaZ;
                    if (deltaX == 0.0) deltaX = -1.0e-4;
                    if (deltaY == 0.0) deltaY = -1.0e-4;
                    if (deltaZ == 0.0) deltaZ = -1.0e-4;
                    EnumFacing facing;
                    if (deltaX < deltaY && deltaX < deltaZ) {
                        facing = endX > currX ? EnumFacing.WEST : EnumFacing.EAST;
                        start = new Vec3d(stepX, start.y + seDeltaY * deltaX, start.z + seDeltaZ * deltaX);
                    } else if (deltaY < deltaZ) {
                        facing = endY > currY ? EnumFacing.DOWN : EnumFacing.UP;
                        start = new Vec3d(start.x + seDeltaX * deltaY, stepY, start.z + seDeltaZ * deltaY);
                    } else {
                        facing = endZ > currZ ? EnumFacing.NORTH : EnumFacing.SOUTH;
                        start = new Vec3d(start.x + seDeltaX * deltaZ, start.y + seDeltaY * deltaZ, stepZ);
                    }
                    currX = MathHelper.floor(start.x) - (facing == EnumFacing.EAST ? 1 : 0);
                    currY = MathHelper.floor(start.y) - (facing == EnumFacing.UP ? 1 : 0);
                    currZ = MathHelper.floor(start.z) - (facing == EnumFacing.SOUTH ? 1 : 0);
                    blockPos = new BlockPos(currX, currY, currZ);
                    blockState = mc.world.getBlockState(blockPos);
                    block = blockState.getBlock();
                    if (block.canCollideCheck(blockState, false) && (getBlocks().contains(block) || !shouldIgnore)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static float getDamageFromDifficulty(float damage) {
        switch (mc.world.getDifficulty()) {
            case PEACEFUL:
                return 0;
            case EASY:
                return Math.min(damage / 2 + 1, damage);
            case HARD:
                return damage * 3 / 2;
            default:
                return damage;
        }
    }

    public static float calculateDamage(BlockPos pos, Entity target, boolean shouldIgnore) {
        return getExplosionDamage(target, new Vec3d(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5), 6.0f, shouldIgnore);
    }

    public static float calculateDamage(Entity crystal, Entity target, boolean shouldIgnore) {
        return getExplosionDamage(target, new Vec3d(crystal.posX, crystal.posY, crystal.posZ), 6.0f, shouldIgnore);
    }

    public static float getExplosionDamage(Entity targetEntity, Vec3d explosionPosition, float explosionPower, boolean shouldIgnore) {
        Vec3d entityPosition = new Vec3d(targetEntity.posX, targetEntity.posY, targetEntity.posZ);
        if (targetEntity.isImmuneToExplosions()) return 0.0f;
        explosionPower *= 2.0f;
        double distanceToSize = entityPosition.distanceTo(explosionPosition) / explosionPower;
        double blockDensity = 0.0;
        AxisAlignedBB bbox = targetEntity.getEntityBoundingBox().offset(targetEntity.getPositionVector().subtract(entityPosition));
        Vec3d bboxDelta = new Vec3d(1.0 / ((bbox.maxX - bbox.minX) * 2.0 + 1.0), 1.0 / ((bbox.maxY - bbox.minY) * 2.0 + 1.0), 1.0 / ((bbox.maxZ - bbox.minZ) * 2.0 + 1.0));
        double xOff = (1.0 - Math.floor(1.0 / bboxDelta.x) * bboxDelta.x) / 2.0;
        double zOff = (1.0 - Math.floor(1.0 / bboxDelta.z) * bboxDelta.z) / 2.0;
        if (bboxDelta.x >= 0.0 && bboxDelta.y >= 0.0 && bboxDelta.z >= 0.0) {
            int nonSolid = 0;
            int total = 0;
            for (double x = 0.0; x <= 1.0; x += bboxDelta.x) {
                for (double y = 0.0; y <= 1.0; y += bboxDelta.y) {
                    for (double z = 0.0; z <= 1.0; z += bboxDelta.z) {
                        Vec3d startPos = new Vec3d(xOff + bbox.minX + (bbox.maxX - bbox.minX) * x, bbox.minY + (bbox.maxY - bbox.minY) * y, zOff + bbox.minZ + (bbox.maxZ - bbox.minZ) * z);
                        if (!rayTraceSolidCheck(startPos, explosionPosition, shouldIgnore)) ++nonSolid;
                        ++total;
                    }
                }
            }
            blockDensity = (double) nonSolid / (double) total;
        }
        double densityAdjust = (1.0 - distanceToSize) * blockDensity;
        float damage = (float) (int) ((densityAdjust * densityAdjust + densityAdjust) / 2.0 * 7.0 * explosionPower + 1.0);
        if (targetEntity instanceof EntityLivingBase)
            damage = getBlastReduction((EntityLivingBase) targetEntity, getDamageFromDifficulty(damage), new Explosion(mc.world, null, explosionPosition.x, explosionPosition.y, explosionPosition.z, explosionPower / 2.0f, false, true));
        return damage;
    }


    public static List<Block> getBlocks() {
        return Arrays.asList(Blocks.OBSIDIAN, Blocks.BEDROCK, Blocks.COMMAND_BLOCK, Blocks.BARRIER, Blocks.ENCHANTING_TABLE, Blocks.ENDER_CHEST, Blocks.END_PORTAL_FRAME, Blocks.BEACON, Blocks.ANVIL);
    }

    public static float getBlastReduction(EntityLivingBase entity, float damageI, Explosion explosion) {
        float damage = damageI;
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer) entity;
            DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(), (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            int k = 0;
            try {
                k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            } catch (Exception exception) {
            }
            float f = MathHelper.clamp((float) k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(MobEffects.RESISTANCE)) {
                damage -= damage / 4.0f;
            }
            damage = Math.max(damage, 0.0f);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }

    public static float[] calcAngle(Vec3d from, Vec3d to) {
        double difX = to.x - from.x;
        double difY = (to.y - from.y) * -1.0;
        double difZ = to.z - from.z;
        double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[]{(float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist)))};
    }

    public static boolean canSeePos(BlockPos pos) {
        return mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + (double) mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5), false, true, false) == null;
    }

    public static List<BlockPos> getSphere(BlockPos pos, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        int cx = pos.getX();
        int cy = pos.getY();
        int cz = pos.getZ();
        int x = cx - (int) r;
        while ((float) x <= (float) cx + r) {
            int z = cz - (int) r;
            while ((float) z <= (float) cz + r) {
                int y = sphere ? cy - (int) r : cy;
                while (true) {
                    float f = y;
                    float f2 = sphere ? (float) cy + r : (float) (cy + h);
                    if (!(f < f2)) break;
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < (double) (r * r) && (!hollow || dist >= (double) ((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }

    public static boolean canPlaceCrystal(BlockPos blockPos, boolean specialEntityCheck, boolean oneDot15) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);
        try {
            if (mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                return false;
            }
            if (!oneDot15 && mc.world.getBlockState(boost2).getBlock() != Blocks.AIR || mc.world.getBlockState(boost).getBlock() != Blocks.AIR) {
                return false;
            }
            for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost))) {
                if (entity.isDead || specialEntityCheck && entity instanceof EntityEnderCrystal) continue;
                return false;
            }
            if (!oneDot15) {
                for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2))) {
                    if (entity.isDead || specialEntityCheck && entity instanceof EntityEnderCrystal) continue;
                    return false;
                }
            }
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }

}*/
