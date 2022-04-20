package dev.based.lynx.impl.clickgui.frame.button.settings;

import dev.based.lynx.api.clickgui.button.SettingComponent;
import dev.based.lynx.api.module.setting.Setting;
import dev.based.lynx.api.util.Wrapper;
import dev.based.lynx.api.util.misc.StringFormatter;
import dev.based.lynx.api.util.render.RenderUtil;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glScalef;

public class EnumComponent extends SettingComponent<Enum<?>> implements Wrapper {
    public EnumComponent(float x, float y, float width, float height, Setting<Enum<?>> setting) {
        super(x, y, width, height, setting);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.width, this.height, this.isWithin(mouseX, mouseY) ? new Color(23, 23, 23, 200).getRGB() : 0x90000000);

        float scaleFactor = 1 / 0.75f;

        glScalef(0.75f, 0.75f, 0.75f);

        this.drawString(this.getSetting().getName(), (this.x + 4) * scaleFactor, (this.y + 4.5f) * scaleFactor, -1, true);
        this.drawString(StringFormatter.formatEnum(this.getSetting().getValue()), (this.x + this.width - 4.5f - (this.getStringWidth(StringFormatter.formatEnum(this.getSetting().getValue())) * 0.75f)) * scaleFactor, (y + 4.5f) * scaleFactor, -1, true);

        glScalef(scaleFactor, scaleFactor, scaleFactor);

        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isWithin(mouseX, mouseY) && mouseButton == 0) {
            this.getSetting().setValue(this.getSetting().getNextMode());
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void keyTyped(char keyChar, int keyCode) {}
}
