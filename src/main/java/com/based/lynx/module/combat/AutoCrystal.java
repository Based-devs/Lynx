public class AutoCrystal extends Module {

    public AutoCrystal() {
        super("AutoCrystal", "Automatically places and breaks crystals to deal damage", Category.COMBAT, true, false, false);
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

    public final Setting<Logic> logic = this.register(new Setting("Logic", Logic.BreakPlace));
    public final Setting<Float> enemyRange = this.register(new Setting("Enemy Range", 10.0f, 0.0f, 20.0f));
    public final Setting<Float> placeRange = this.register(new Setting("Place Range", 5.0f, 0.0f, 10.0f));
    public final Setting<Float> placeWallRange = this.register(new Setting("Place Wall Range", 3.5f, 0.0f, 10.0f));
    public final Setting<Integer> placeDelay = this.register(new Setting("Place Delay", 0, 0, 500));
    public final Setting<Float> minPlaceDamage = this.register(new Setting("Min Place Damage", 7.0f, 0.0f, 36.0f));
    public final Setting<Float> maxSelfPlace = this.register(new Setting("Max Self Place", 8.0f, 0.0f, 36.0f));
    public final Setting<Boolean> placeSwing = this.register(new Setting("Place Swing", true));
    public final Setting<Float> breakRange = this.register(new Setting("Break Range", 5.0f, 0.0f, 10.0f));
    public final Setting<Float> breakWallRange = this.register(new Setting("Break Wall Range", 3.5f, 0.0f, 10.0f));
    public final Setting<Integer> breakDelay = this.register(new Setting("Break Delay", 50, 0, 500));
    public final Setting<Float> minBreakDamage = this.register(new Setting("Min Break Damage", 7.0f, 0.0f, 36.0f));
    public final Setting<Float> maxSelfBreak = this.register(new Setting("Max Self Break", 10.0f, 0.0f, 36.0f));
    public final Setting<Boolean> antiSuicide = this.register(new Setting("Anti Suicide", true));
    public final Setting<Boolean> breakSwing = this.register(new Setting("Break Swing", true));
    public final Setting<Switch> switchMode = this.register(new Setting("Switch Mode", Switch.Silent));
    public final Setting<Boolean> packet = this.register(new Setting("Packet", true));
    public final Setting<Boolean> rotate = this.register(new Setting("Rotate", false));
    public final Setting<Integer> ticksExisted = this.register(new Setting("Ticks Existed", 1, 0, 10));
    public final Setting<Boolean> oneDotThirteen = this.register(new Setting("1.13+", true));
    public final Setting<Boolean> terrainTrace = this.register(new Setting("Terrain Trace", true));
    public final Setting<Boolean> antiWeakness = this.register(new Setting("Anti Weakness", true));
    public final Setting<Boolean> raytrace = this.register(new Setting("Raytrace", false));
    public final Setting<Float> facePlaceHP = this.register(new Setting("Face Place HP", 14.0f, 0.0f, 36.0f));
    public final Setting<Integer> facePlaceDelay = this.register(new Setting("Face Place Delay", 10, 0, 150));
    public final Setting<Bind> facePlaceBind = this.register(new Setting("Face Place Bind", new Bind(-1)));
    public final Setting<Float> facePlaceArmor = this.register(new Setting("Face Place Armor %", 20.0f, 0.0f, 100.0f));
    public final Setting<Boolean> render = this.register(new Setting("Render", true));
    public final Setting<Boolean> box = this.register(new Setting("Box", true));
    public final Setting<Boolean> outline = this.register(new Setting("Outline", true));
    public final Setting<Float> lineWidth = this.register(new Setting("Line Width", 2.0f));
    public final Setting<Integer> fadeTime = this.register(new Setting("Fade Time", 300, 0, 1000));
    public final Setting<Integer> red = this.register(new Setting("Red", 255, 0, 255));
    public final Setting<Integer> green = this.register(new Setting("Green", 255, 0, 255));
    public final Setting<Integer> blue = this.register(new Setting("Blue", 255, 0, 255));
    public final Setting<Float> startAlpha = this.register(new Setting("Start Alpha", 100, 0, 255));
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
        EntityLivingBase target = EntityUtil.getClosestEnemy(enemyRange.getValue());
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
        float minDamage = (target.getHealth() + target.getAbsorptionAmount() < facePlaceHP.getValue()) || shouldArmorFacePlace(target) || facePlaceBind.getValue().isDown() ? 1.5f : minPlaceDamage.getValue();
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
            if (target.getHealth() + target.getAbsorptionAmount() < facePlaceHP.getValue()) {
                placeTimer.setMs(facePlaceDelay.getValue());
            }
            if (placeTimer.passedMs(placeDelay.getValue())) {
                BlockUtil.placeCrystalOnBlock(finalPlacePos, crystalHand, placeSwing.getValue(), false, switchMode.getValue() == Switch.Silent);
                placeTimer.reset();
            }
        }
    }

    public void breakCrystals(EntityLivingBase target) {
        float minDamage = (target.getHealth() + target.getAbsorptionAmount() < facePlaceHP.getValue()) || shouldArmorFacePlace(target) || facePlaceBind.getValue().isDown() ? 1.5f : minBreakDamage.getValue();
        for (Entity entity : mc.world.loadedEntityList) {
            if (!(entity instanceof EntityEnderCrystal) || entity.getPositionVector().distanceTo(mc.player.getPositionVector()) > breakRange.getValue() && canSeePos(new BlockPos(entity.getPositionVector())) || entity.getPositionVector().distanceTo(mc.player.getPositionVector()) > breakWallRange.getValue() && !canSeePos(new BlockPos(entity.getPositionVector()))) {
                continue;
            }
            if (calculateDamage(entity, target, terrainTrace.getValue()) > minDamage && calculateDamage(entity, mc.player, terrainTrace.getValue()) < maxSelfBreak.getValue()) {
                if (rotate.getValue()) {
                    rotateTo(entity);
                }
                if (breakTimer.passedMs(breakDelay.getValue()) || entity.ticksExisted > ticksExisted.getValue()) {
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
    public void onRender3D(Render3DEvent event) {
        try {
            for (Map.Entry<BlockPos, Long> map : renderMap.entrySet()) {
                if (System.currentTimeMillis() - map.getValue() < fadeTime.getValue()) {
                    float finalAlpha = startAlpha.getValue();
                    drawBoxESP(map.getKey(), new Color(red.getValue(), green.getValue(), blue.getValue()), finalAlpha, lineWidth.getValue(), outline.getValue(), box.getValue(), finalAlpha);
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
        AxisAlignedBB bb = new AxisAlignedBB((double) pos.getX() - RenderUtil.mc.getRenderManager().viewerPosX, (double) pos.getY() - RenderUtil.mc.getRenderManager().viewerPosY, (double) pos.getZ() - RenderUtil.mc.getRenderManager().viewerPosZ, (double) (pos.getX() + 1) - RenderUtil.mc.getRenderManager().viewerPosX, (double) (pos.getY() + 1) - RenderUtil.mc.getRenderManager().viewerPosY, (double) (pos.getZ() + 1) - RenderUtil.mc.getRenderManager().viewerPosZ);
        ICamera camera = new Frustum();
        camera.setPosition(Objects.requireNonNull(RenderUtil.mc.getRenderViewEntity()).posX, RenderUtil.mc.getRenderViewEntity().posY, RenderUtil.mc.getRenderViewEntity().posZ);
        if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + RenderUtil.mc.getRenderManager().viewerPosX, bb.minY + RenderUtil.mc.getRenderManager().viewerPosY, bb.minZ + RenderUtil.mc.getRenderManager().viewerPosZ, bb.maxX + RenderUtil.mc.getRenderManager().viewerPosX, bb.maxY + RenderUtil.mc.getRenderManager().viewerPosY, bb.maxZ + RenderUtil.mc.getRenderManager().viewerPosZ))) {
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
            ItemStack stack = InventoryUtil.mc.player.inventory.getStackInSlot(i);
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
        if (event.getStage() == 0 && event.getPacket() instanceof CPacketPlayer && isRotating && rotate.getValue()) {
            ((CPacketPlayer) event.getPacket()).yaw = rotYaw;
            ((CPacketPlayer) event.getPacket()).pitch = rotPitch;
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

}