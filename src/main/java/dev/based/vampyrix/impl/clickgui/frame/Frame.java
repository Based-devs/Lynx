package dev.based.vampyrix.impl.clickgui.frame;

import dev.based.vampyrix.api.clickgui.component.ADragComponent;
import dev.based.vampyrix.api.clickgui.component.AToggleContainer;
import dev.based.vampyrix.api.module.Category;
import dev.based.vampyrix.api.module.Module;
import dev.based.vampyrix.api.util.misc.StringFormatter;
import dev.based.vampyrix.api.util.render.ColourUtil;
import dev.based.vampyrix.api.util.render.RenderUtil;
import dev.based.vampyrix.api.util.render.TextRenderer;
import dev.based.vampyrix.impl.clickgui.frame.button.Button;
import net.minecraft.client.Minecraft;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Frame extends ADragComponent implements TextRenderer {
    private final List<AToggleContainer> buttons = new ArrayList<>();
    private final Category category;
    private boolean expanded = true;

    public Frame(Category category, List<Module> modules, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.category = category;

        for (Module module : modules) {
            this.buttons.add(new Button(module, x, y, width, 14));
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.width, this.height, ColourUtil.getClientColour().getRGB());
        this.drawString(StringFormatter.formatEnum(this.category), x + ((this.width / 2F) - (Minecraft.getMinecraft().fontRenderer.getStringWidth(StringFormatter.formatEnum(this.category)) / 2F)), y + 4, new Color(255, 255, 255).getRGB(), true);

        if (this.expanded) {
            float yOffset = height;
            for (AToggleContainer button : this.buttons) {
                button.x = this.x;
                button.y = this.y + yOffset;
                yOffset += button.getTotalHeight();
                button.render(mouseX, mouseY, partialTicks);
            }
        }

        this.updateDragPosition(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isWithin(mouseX, mouseY) && mouseButton == 0) {
            this.startDragging(mouseX, mouseY);
        } else if (isWithin(mouseX, mouseY) && mouseButton == 1) {
            this.expanded = !this.expanded;
        }

        if (this.expanded) {
            for (AToggleContainer button : this.buttons) {
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            this.stopDragging();
        }

        if (this.expanded) {
            for (AToggleContainer button : this.buttons) {
                button.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) {
        if (this.expanded) {
            for (AToggleContainer button : this.buttons) {
                button.keyTyped(keyChar, keyCode);
            }
        }
    }
}
