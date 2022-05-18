package com.based.lynx.ui.click.frame.component.setting;

import com.based.lynx.module.client.Colors;
import com.based.lynx.setting.Setting;
import com.based.lynx.ui.click.frame.component.module.ModuleComponent;
import com.based.lynx.util.RenderUtil;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glScalef;

public class SliderComponent extends SettingComponent<Number> {

    private boolean dragging;

    public SliderComponent(Setting<Number> setting, float x, float y, float width, float height, ModuleComponent moduleComponent) {
        super(setting, x, y, width, height, moduleComponent);
    }

    @Override
    public void renderComponent(int mouseX, int mouseY) {
        RenderUtil.drawRect(getX(), getY(), getWidth(), getHeight(), isHovered(mouseX, mouseY) ? new Color(30, 30, 35).getRGB() : new Color(25, 25, 30).getRGB());

        glScalef(0.75f, 0.75f, 0.75f);
        float scaleFactor = 1 / 0.75f;

        RenderUtil.drawStringWithShadow(getSetting().getName(), (getX() + 5) * scaleFactor, (getY() + 4) * scaleFactor, -1, true);

        float modeX = (getX() + getWidth() - 2 - (Minecraft.getMinecraft().fontRenderer.getStringWidth(String.valueOf(getSetting().getValue())) * 0.75f)) * scaleFactor;
        RenderUtil.drawStringWithShadow(String.valueOf(getSetting().getValue()), modeX, (getY() + 4) * scaleFactor, new Color(175, 175, 175).getRGB(), true);

        glScalef(scaleFactor, scaleFactor, scaleFactor);

        float difference = Math.min(getWidth() - 5, Math.max(0, mouseX - (getX() + 5)));

        float min = getSetting().getMin().floatValue();
        float max = getSetting().getMax().floatValue();

        if (!Mouse.isButtonDown(0)) {
            this.dragging = false;
        }

        if (this.dragging) {
            if (difference == 0) {
                this.getSetting().setValue(this.getSetting().getMin());
            } else {
                float value = ((difference / (getWidth() - 5)) * (max - min) + min);

                float precision = 1 / this.getSetting().getIncrementation().floatValue();
                this.getSetting().setValue(Math.round(Math.max(this.getSetting().getMin().floatValue(), Math.min(this.getSetting().getMax().floatValue(), value)) * precision) / precision);
            }
        }

        float renderWidth = ((getWidth() - 5) * (this.getSetting().getValue().floatValue() - min) / (max - min));
        RenderUtil.drawRect(getX() + 5, getY() + getHeight() - 1, renderWidth, 1, Colors.INSTANCE.colour.getValue().getRGB());

        super.renderComponent(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            dragging = true;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isDragging() {
        return dragging;
    }
}
