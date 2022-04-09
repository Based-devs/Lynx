package dev.based.vampyrix.impl.clickgui.frame.button.settings;

import dev.based.vampyrix.api.util.Wrapper;
import dev.based.vampyrix.api.util.render.ColourUtil;
import dev.based.vampyrix.api.util.render.RenderUtil;
import dev.based.vampyrix.api.clickgui.button.SettingComponent;
import dev.based.vampyrix.api.module.setting.Setting;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glScalef;

public class BooleanComponent extends SettingComponent<Boolean> implements Wrapper {
    public BooleanComponent(float x, float y, float width, float height, Setting<Boolean> setting) {
        super(x, y, width, height, setting);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(x, y, width, height, isWithin(mouseX, mouseY) ? new Color(23, 23, 23, 200).getRGB() : 0x90000000);

        float scaleFactor = 1 / 0.75f;

        glScalef(0.75f, 0.75f, 0.75f);

        mc.fontRenderer.drawStringWithShadow(getSetting().getName(), (x + 4) * scaleFactor, (y + 4.5f) * scaleFactor, getSetting().getValue() ? ColourUtil.getClientColour().brighter().brighter().getRGB() : -1);

        glScalef(scaleFactor, scaleFactor, scaleFactor);

        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isWithin(mouseX, mouseY) && mouseButton == 0) {
            getSetting().setValue(!getSetting().getValue());
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
