package dev.based.vampyrix.impl.clickgui.frame;

import dev.based.vampyrix.api.util.misc.StringFormatter;
import dev.based.vampyrix.api.util.render.ColourUtil;
import dev.based.vampyrix.api.util.render.RenderUtil;
import dev.based.vampyrix.api.clickgui.component.ADragComponent;
import dev.based.vampyrix.api.clickgui.component.AToggleContainer;
import dev.based.vampyrix.impl.clickgui.frame.button.Button;
import dev.based.vampyrix.api.module.Category;
import dev.based.vampyrix.api.module.Module;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frame extends ADragComponent {

    private final List<AToggleContainer> buttons;
    private final Category category;
    private boolean expanded = true;

    public Frame(Category category, List<Module> modules, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.buttons = new ArrayList<>();
        this.category = category;

        for (Module module : modules) {
            buttons.add(new Button(module, x, y, width, 14));
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(x, y, width, height, ColourUtil.getClientColour().getRGB());
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(StringFormatter.formatEnum(category), x + ((width / 2F) - (Minecraft.getMinecraft().fontRenderer.getStringWidth(StringFormatter.formatEnum(category)) / 2F)), y + 4, new Color(255, 255, 255).getRGB());

        if (expanded) {
            float yOffset = height;
            for (AToggleContainer button : buttons) {
                button.x = this.x;
                button.y = this.y + yOffset;
                yOffset += button.getTotalHeight();
                button.render(mouseX, mouseY, partialTicks);
            }
        }

        updateDragPosition(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isWithin(mouseX, mouseY) && mouseButton == 0) {
            startDragging(mouseX, mouseY);
        } else if (isWithin(mouseX, mouseY) && mouseButton == 1) {
            expanded = !expanded;
        }

        if (expanded) {
            for (AToggleContainer button : buttons) {
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            stopDragging();
        }

        if (expanded) {
            for (AToggleContainer button : buttons) {
                button.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) {
        if (expanded) {
            for (AToggleContainer button : buttons) {
                button.keyTyped(keyChar, keyCode);
            }
        }
    }

    public List<AToggleContainer> getButtons() {
        return buttons;
    }
}
