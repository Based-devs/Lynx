package dev.based.vampyrix.impl.clickgui.frame.button.settings;

import dev.based.vampyrix.api.clickgui.button.SettingComponent;
import dev.based.vampyrix.api.module.setting.Setting;
import dev.based.vampyrix.api.util.Wrapper;
import dev.based.vampyrix.api.util.render.ColourUtil;
import dev.based.vampyrix.api.util.render.RenderUtil;
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
        RenderUtil.drawRect(this.x, this.y, this.width, this.height, this.isWithin(mouseX, mouseY) ? new Color(23, 23, 23, 200).getRGB() : 0x90000000);

        float difference = Math.min(this.width, Math.max(0, mouseX - this.x));

        float min = getSetting().getMin().floatValue();
        float max = getSetting().getMax().floatValue();

        if (!Mouse.isButtonDown(0)) {
            this.dragging = false;
        }

        if (this.dragging) {
            if (difference == 0) {
                this.getSetting().setValue(this.getSetting().getMin());
            } else {
                float value = ((difference / this.width) * (max - min) + min);

                float precision = 1 / this.getSetting().getIncrementation().floatValue();
                this.getSetting().setValue(Math.round(Math.max(this.getSetting().getMin().floatValue(), Math.min(this.getSetting().getMax().floatValue(), value)) * precision) / precision);
            }
        }

        float renderWidth = (this.width * (this.getSetting().getValue().floatValue() - min) / (max - min));

        RenderUtil.drawRect(this.x, this.y, renderWidth, this.height, isWithin(mouseX, mouseY) ? ColourUtil.integrateAlpha(ColourUtil.getClientColour(), 240).getRGB() : ColourUtil.getClientColour().getRGB());

        float scaleFactor = 1 / 0.75f;

        glScalef(0.75f, 0.75f, 0.75f);

        this.drawString(this.getSetting().getName(), (this.x + 4) * scaleFactor, (this.y + 4.5f) * scaleFactor, -1, true);
        this.drawString(String.valueOf(this.getSetting().getValue()), (this.x + this.width - 4.5f - (this.getStringWidth(String.valueOf(this.getSetting().getValue())) * 0.75f)) * scaleFactor, (this.y + 4.5f) * scaleFactor, -1, true);

        glScalef(scaleFactor, scaleFactor, scaleFactor);

        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isWithin(mouseX, mouseY) && mouseButton == 0) {
            this.dragging = true;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void keyTyped(char keyChar, int keyCode) {}

    public boolean isDragging() {
        return this.dragging;
    }
}
