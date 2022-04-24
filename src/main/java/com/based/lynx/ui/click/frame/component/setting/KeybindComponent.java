package com.based.lynx.ui.click.frame.component.setting;

import com.based.lynx.setting.Setting;
import com.based.lynx.ui.click.frame.component.module.ModuleComponent;
import com.based.lynx.util.RenderUtil;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lwjgl.opengl.GL11.glScalef;

public class KeybindComponent extends SettingComponent<AtomicInteger> {

    private boolean listening;

    public KeybindComponent(Setting<AtomicInteger> setting, float x, float y, float width, float height, ModuleComponent moduleComponent) {
        super(setting, x, y, width, height, moduleComponent);
    }

    @Override
    public void renderComponent(int mouseX, int mouseY) {
        RenderUtil.drawRect(getX(), getY(), getWidth(), getHeight(), isHovered(mouseX, mouseY) ? new Color(30, 30, 35).getRGB() : new Color(25, 25, 30).getRGB());

        glScalef(0.75f, 0.75f, 0.75f);
        float scaleFactor = 1 / 0.75f;

        RenderUtil.drawStringWithShadow(getSetting().getName(), (getX() + 5) * scaleFactor, (getY() + 4) * scaleFactor, -1, true);

        String character = Keyboard.getKeyName(getSetting().getValue().get());
        float modeX = (getX() + getWidth() - 2 - (Minecraft.getMinecraft().fontRenderer.getStringWidth(listening ? "..." : character) * 0.75f)) * scaleFactor;
        RenderUtil.drawStringWithShadow(listening ? "..." : character, modeX, (getY() + 4) * scaleFactor, new Color(175, 175, 175).getRGB(), true);

        glScalef(scaleFactor, scaleFactor, scaleFactor);

        super.renderComponent(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            listening = !listening;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (listening) {
            if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_RETURN || keyCode == Keyboard.KEY_DELETE) {
                listening = false;
                getSetting().setValue(new AtomicInteger(0));
                return;
            }

            getSetting().setValue(new AtomicInteger(keyCode));
            listening = false;
        }
        super.keyTyped(typedChar, keyCode);
    }
}
