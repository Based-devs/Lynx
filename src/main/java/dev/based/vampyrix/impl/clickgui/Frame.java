package dev.based.vampyrix.impl.clickgui;

import java.awt.Color;
import java.util.ArrayList;

import dev.based.vampyrix.api.clickgui.ADragComponent;
import dev.based.vampyrix.api.clickgui.AToggleContainer;
import dev.based.vampyrix.api.module.Category;
import dev.based.vampyrix.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

//ClickGUI frame class that handles buttons
public class Frame extends ADragComponent {

    private final ArrayList < AToggleContainer > buttons;

    private final Category category;

    public Frame(Category category, ArrayList < Module > modules, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.buttons = new ArrayList < > ();
        this.category = category;

        for (Module module: modules) {
            buttons.add(new Button(module, x, y, width, 12));
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + height, new Color(50, 80, 255, 200).getRGB());
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(category.name, x + 2 f, y + 2.5 f, new Color(255, 255, 255).getRGB());
        int yOffset = height;
        for (AToggleContainer button: buttons) {
            button.x = this.x;
            button.y = this.y + yOffset;
            yOffset += button.getTotalHeight();
            button.render(mouseX, mouseY, partialTicks);
        }

        updateDragPosition(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isWithin(mouseX, mouseY) && mouseButton == 0) {
            startDragging(mouseX, mouseY);
        }

        for (AToggleContainer button: buttons) {
            button.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            stopDragging(mouseX, mouseY);
        }

        for (AToggleContainer button: buttons) {
            button.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) {
        for (AToggleContainer button: buttons) {
            button.keyTyped(keyChar, keyCode);
        }
    }

    public ArrayList < AToggleContainer > getButtons() {
        return buttons;
    }

}
