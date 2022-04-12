package dev.based.vampyrix.impl.clickgui.frame.button.settings;

import dev.based.vampyrix.api.clickgui.button.SettingComponent;
import dev.based.vampyrix.api.module.setting.Setting;
import dev.based.vampyrix.api.util.Wrapper;
import dev.based.vampyrix.api.util.misc.Keybind;
import dev.based.vampyrix.api.util.render.RenderUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glScalef;

public class KeybindComponent extends SettingComponent<Keybind> implements Wrapper {
    private boolean isListening;

    public KeybindComponent(float x, float y, float width, float height, Setting<Keybind> setting) {
        super(x, y, width, height, setting);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.width, this.height, this.isWithin(mouseX, mouseY) ? new Color(23, 23, 23, 200).getRGB() : 0x90000000);

        float scaleFactor = 1 / 0.75f;

        glScalef(0.75f, 0.75f, 0.75f);

        drawString(getSetting().getName(), (x + 5) * scaleFactor, (y + 4.5f) * scaleFactor, -1, true);
        drawString((this.isListening ? "..." : Keyboard.getKeyName(getSetting().getValue().getKeyCode())), (this.x + this.width - 4.5f - (this.getStringWidth(this.isListening ? "..." : Keyboard.getKeyName(this.getSetting().getValue().getKeyCode())) * 0.75f)) * scaleFactor, (y + 4.5f) * scaleFactor, -1, true);

        glScalef(scaleFactor, scaleFactor, scaleFactor);

        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isWithin(mouseX, mouseY) && mouseButton == 0) {
            this.isListening = !this.isListening;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) {
        if (this.isListening) {
            if (keyCode == Keyboard.KEY_DELETE || keyCode == Keyboard.KEY_BACK) {
                getSetting().getValue().setKeyCode(0);
            } else {
                getSetting().getValue().setKeyCode(keyCode);
            }

            this.isListening = false;
        }

        super.keyTyped(keyChar, keyCode);
    }
}
