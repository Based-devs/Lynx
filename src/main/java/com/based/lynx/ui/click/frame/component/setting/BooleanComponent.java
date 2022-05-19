package com.based.lynx.ui.click.frame.component.setting;

import com.based.lynx.module.client.Colors;
import com.based.lynx.setting.Setting;
import com.based.lynx.ui.click.frame.component.module.ModuleComponent;
import com.based.lynx.util.Animation;
import com.based.lynx.util.RenderUtil;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glScalef;

public class BooleanComponent extends SettingComponent<Boolean> {

    private final Animation animation = new Animation(200, false);

    public BooleanComponent(Setting<Boolean> setting, float x, float y, float width, float height, ModuleComponent moduleComponent) {
        super(setting, x, y, width, height, moduleComponent);

        animation.setStateHard(setting.getValue());
    }

    @Override
    public void renderComponent(int mouseX, int mouseY) {
        RenderUtil.drawRect(getX(), getY(), getWidth(), getHeight(), isHovered(mouseX, mouseY) ? new Color(30, 30, 35).getRGB() : new Color(25, 25, 30).getRGB());

        glScalef(0.75f, 0.75f, 0.75f);
        float scaleFactor = 1 / 0.75f;

        RenderUtil.drawStringWithShadow(getSetting().getName(), (getX() + 7) * scaleFactor, (getY() + 4) * scaleFactor, -1, true);

        glScalef(scaleFactor, scaleFactor, scaleFactor);

        RenderUtil.drawRect(getX() + getWidth() - 12, getY() + 1.5f, 10, 10, new Color(20, 20, 25).getRGB());
        RenderUtil.drawRect((getX() + getWidth() - 12) + (5 * (1 - animation.getAnimationFactor())), getY() + 1.5f + (5 * (1 - animation.getAnimationFactor())), 10 * animation.getAnimationFactor(), 10 * animation.getAnimationFactor(), Colors.INSTANCE.color.getValue().getRGB());

        super.renderComponent(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            getSetting().setValue(!getSetting().getValue());
            animation.setState(getSetting().getValue());
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
