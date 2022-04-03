package dev.based.vampyrix.impl.clickgui;

import java.awt.Color;

import dev.based.vampyrix.api.clickgui.AToggleContainer;
import dev.based.vampyrix.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

//ClickGUI button class that can handle settings in the future
public class Button extends AToggleContainer {

    private final Module module;

    public Button(Module module, int x, int y, int width, int height) {
        super(module, x, y, width, height);
        this.module = module;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        int toggleColor = module.toggled ? new Color(50, 80, 255, 200).getRGB() : new Color(40, 40, 40, 200).getRGB();

        Gui.drawRect(x, y, x + width, y + height, toggleColor);

        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(module.name, x + 2, y + 2.5f, new Color(255, 255, 255).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isWithin(mouseX, mouseY) && mouseButton == 0) {
            module.toggle();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void keyTyped(char keyChar, int keyCode) {

    }

    @Override
    public int getTotalHeight() {
        return height;
    }

}
