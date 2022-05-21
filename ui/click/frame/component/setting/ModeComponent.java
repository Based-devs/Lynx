package com.based.lynx.ui.click.frame.component.setting;

import com.based.lynx.setting.Setting;
import com.based.lynx.ui.click.frame.component.module.ModuleComponent;
import com.based.lynx.util.RenderUtil;
import net.minecraft.client.Minecraft;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glScalef;

public class ModeComponent extends SettingComponent<Enum<?>> {

    public ModeComponent(Setting<Enum<?>> setting, float x, float y, float width, float height, ModuleComponent moduleComponent) {
        super(setting, x, y, width, height, moduleComponent);
    }

    @Override
    public void renderComponent(int mouseX, int mouseY) {
        RenderUtil.drawRect(getX(), getY(), getWidth(), getHeight(), isHovered(mouseX, mouseY) ? new Color(30, 30, 35).getRGB() : new Color(25, 25, 30).getRGB());

        glScalef(0.75f, 0.75f, 0.75f);
        float scaleFactor = 1 / 0.75f;

        RenderUtil.drawStringWithShadow(getSetting().getName(), (getX() + 5) * scaleFactor, (getY() + 4) * scaleFactor, -1, true);

        float modeX = (getX() + getWidth() - 2 - (Minecraft.getMinecraft().fontRenderer.getStringWidth(this.getSetting().getValue().name()) * 0.75f)) * scaleFactor;
        RenderUtil.drawStringWithShadow(getSetting().getValue().name(), modeX, (getY() + 4) * scaleFactor, new Color(175, 175, 175).getRGB(), true);

        glScalef(scaleFactor, scaleFactor, scaleFactor);

        super.renderComponent(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            getSetting().setValue(getSetting().getNextMode());
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
