package dev.based.lynx.impl.clickgui.frame.button.settings;

import dev.based.lynx.api.clickgui.button.SettingComponent;
import dev.based.lynx.api.module.setting.Setting;
import dev.based.lynx.api.util.Wrapper;
import dev.based.lynx.api.util.render.ColourUtil;
import dev.based.lynx.api.util.render.RenderUtil;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glScalef;

public class BooleanComponent extends SettingComponent<Boolean> implements Wrapper {
    public BooleanComponent(float x, float y, float width, float height, Setting<Boolean> setting) {
        super(x, y, width, height, setting);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.width, this.height, this.isWithin(mouseX, mouseY) ? new Color(23, 23, 23, 200).getRGB() : 0x90000000);

        float scaleFactor = 1 / 0.75f;

        glScalef(0.75f, 0.75f, 0.75f);

        drawString(this.getSetting().getName(), (x + 4) * scaleFactor, (y + 4.5f) * scaleFactor, this.getSetting().getValue() ? ColourUtil.getClientColour().getRGB() : -1, true);

        glScalef(scaleFactor, scaleFactor, scaleFactor);

        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isWithin(mouseX, mouseY) && mouseButton == 0) {
            this.getSetting().setValue(!this.getSetting().getValue());
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void keyTyped(char keyChar, int keyCode) {}
}
