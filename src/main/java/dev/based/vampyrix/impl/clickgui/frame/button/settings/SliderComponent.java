package dev.based.vampyrix.impl.clickgui.frame.button.settings;

import dev.based.vampyrix.api.util.Wrapper;
import dev.based.vampyrix.api.util.render.ColourUtil;
import dev.based.vampyrix.api.util.render.RenderUtil;
import dev.based.vampyrix.api.clickgui.button.SettingComponent;
import dev.based.vampyrix.api.module.setting.Setting;
import org.lwjgl.input.Mouse;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glScalef;

public class SliderComponent extends SettingComponent<Number> implements Wrapper {
    private boolean dragging = false;

    public SliderComponent(float x, float y, float width, float height, Setting<Number> setting) {
        super(x, y, width, height, setting);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(x, y, width, height, isWithin(mouseX, mouseY) ? new Color(23, 23, 23, 200).getRGB() : 0x90000000);

        float difference = Math.min(width, Math.max(0, mouseX - x));

        float min = getSetting().getMin().floatValue();
        float max = getSetting().getMax().floatValue();

        if (!Mouse.isButtonDown(0)) {
            dragging = false;
        }

        if (dragging) {
            if (difference == 0) {
                getSetting().setValue(getSetting().getMin());
            } else {
                float value = ((difference / width) * (max - min) + min);

                float precision = 1 / getSetting().getIncrementation().floatValue();
                getSetting().setValue(Math.round(Math.max(getSetting().getMin().floatValue(), Math.min(getSetting().getMax().floatValue(), value)) * precision) / precision);
            }
        }

        float renderWidth = (width * (getSetting().getValue().floatValue() - min) / (max - min));

        RenderUtil.drawRect(x, y, renderWidth, height, ColourUtil.getClientColour().getRGB());

        float scaleFactor = 1 / 0.75f;

        glScalef(0.75f, 0.75f, 0.75f);

        mc.fontRenderer.drawStringWithShadow(getSetting().getName(), (x + 4) * scaleFactor, (y + 4.5f) * scaleFactor, -1);
        mc.fontRenderer.drawStringWithShadow(String.valueOf(getSetting().getValue()), (x + width - 4.5f - (mc.fontRenderer.getStringWidth(String.valueOf(getSetting().getValue())) * 0.75f)) * scaleFactor, (y + 4.5f) * scaleFactor, -1);

        glScalef(scaleFactor, scaleFactor, scaleFactor);

        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isWithin(mouseX, mouseY) && mouseButton == 0) {
            dragging = true;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) {
    }
}
