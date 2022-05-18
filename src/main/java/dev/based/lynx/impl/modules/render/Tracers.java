package dev.based.lynx.impl.modules.render;

import dev.based.lynx.api.module.Category;
import dev.based.lynx.api.module.Module;
import dev.based.lynx.api.module.setting.Setting;
import dev.based.lynx.api.util.misc.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

import java.awt.Color;

import static org.lwjgl.opengl.GL11.*;

public class Tracers extends Module {
    private final Setting<Boolean> players = new Setting<>("Players", true).setDescription("Draw lines to players");
    private final Setting<Color> playerColour = new Setting<>("Colour", Color.WHITE).setDescription("The colour of the lines to players").setParentSetting(players);

    private final Setting<Boolean> mobs = new Setting<>("Mobs", false).setDescription("Draw lines to mobs");
    private final Setting<Color> mobColour = new Setting<>("Colour", Color.RED).setDescription("The colour of the lines to mobs").setParentSetting(mobs);

    private final Setting<Boolean> passive = new Setting<>("Passive", false).setDescription("Draw lines to passive mobs");
    private final Setting<Color> passiveColour = new Setting<>("Colour", Color.GREEN).setDescription("The colour of the lines to passive mobs").setParentSetting(passive);

    private final Setting<Float> width = new Setting<>("Width", 0.5f, 0.1f, 3.0f, 0.1f).setDescription("The width of the tracers");

    public Tracers() {
        super("Tracers", "Draws lines to entities in the world", Category.RENDER);
    }

    @Override
    public void setupSettings() {
        this.registerSetting(players);
        this.registerSetting(mobs);
        this.registerSetting(passive);
        this.registerSetting(width);
    }

    @Override
    public void onRender3D() {
        mc.world.loadedEntityList.stream().filter(this::isEntityValid).forEach(entity -> {
            Vec3d vector = MathUtil.getInterpolatedPosition(entity);

            double x = vector.x - mc.getRenderManager().viewerPosX;
            double y = vector.y - mc.getRenderManager().viewerPosY;
            double z = vector.z - mc.getRenderManager().viewerPosZ;

            Vec3d playerEyesVector = new Vec3d(0, 0, 1).rotatePitch((float) -Math.toRadians(mc.player.rotationPitch)).rotateYaw((float) -Math.toRadians(mc.player.rotationYaw));

            glPushMatrix();

            glDepthMask(false);
            glDisable(GL_DEPTH_TEST);

            glDisable(GL_ALPHA_TEST);
            glEnable(GL_BLEND);
            glDisable(GL_TEXTURE_2D);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glEnable(GL_LINE_SMOOTH);
            glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
            glLineWidth(width.getValue());
            glColor4f(getEntityColour(entity).getRed() / 255f, getEntityColour(entity).getGreen() / 255f, getEntityColour(entity).getBlue() / 255f, getEntityColour(entity).getAlpha() / 255f);

            glBegin(GL_CURRENT_BIT);

            glVertex3d(playerEyesVector.x, playerEyesVector.y + mc.player.eyeHeight - (mc.player.isSneaking() ? 0.08f : 0), playerEyesVector.z);
            glVertex3d(x, y, z);

            glEnd();

            glEnable(GL_DEPTH_TEST);
            glDepthMask(true);

            glEnable(GL_TEXTURE_2D);
            glDisable(GL_BLEND);
            glEnable(GL_ALPHA_TEST);
            glDisable(GL_LINE_SMOOTH);
            glColor4f(1, 1, 1, 1);

            glPopMatrix();
        });
    }

    private boolean isEntityValid(Entity entity) {
        if (entity instanceof EntityPlayer && entity != mc.player && this.players.getValue()) {
            return true;
        }

        if (entity instanceof EntityMob && this.mobs.getValue()) {
            return true;
        }

        return entity instanceof EntityAnimal && this.passive.getValue();
    }

    private Color getEntityColour(Entity entity) {
        if (entity instanceof EntityPlayer) {
            return this.playerColour.getValue();
        }

        if (entity instanceof EntityMob) {
            return this.mobColour.getValue();
        }

        if (entity instanceof EntityAnimal) {
            return this.passiveColour.getValue();
        }

        return Color.WHITE;
    }
}
