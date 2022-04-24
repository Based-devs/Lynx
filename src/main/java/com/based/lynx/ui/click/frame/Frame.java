package com.based.lynx.ui.click.frame;

import com.based.lynx.Lynx;
import com.based.lynx.module.Category;
import com.based.lynx.module.Module;
import com.based.lynx.ui.click.frame.component.Component;
import com.based.lynx.ui.click.frame.component.module.ModuleComponent;
import com.based.lynx.ui.click.frame.component.setting.SettingComponent;
import com.based.lynx.util.Animation;
import com.based.lynx.util.FontUtil;
import com.based.lynx.util.RenderUtil;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frame {

    private float x;
    private float y;
    private float width;
    private float barHeight;
    private float height;
    private final float maxHeight = 325;
    private boolean open = true;
    private Category category;

    private final List<ModuleComponent> components = new ArrayList<>();

    private final Animation animation = new Animation(200, true);

    public Frame(float x, float y, float width, float barHeight, Category category) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.barHeight = barHeight;
        this.category = category;

        height = maxHeight;

        float yOffset = barHeight;
        for (Module module : Lynx.moduleManager.getModules(category)) {
            components.add(new ModuleComponent(module, x, y + yOffset, width, 13, this));
            yOffset += 13;
        }
    }

    public void drawFrame(int mouseX, int mouseY) {
        RenderUtil.drawRect(x, y, width, barHeight, isHovered(mouseX, mouseY) ? new Color(40, 40, 45).getRGB() : new Color(35, 35, 40).getRGB());

        FontUtil.drawCenteredString(category.getName(), x + width / 2, y + 4, 0xFFFFFF, true);

        float moduleOffset = (components.isEmpty() ? 0 : components.get(0).getY());

        for (ModuleComponent component : components) {
            component.setY(moduleOffset);
            moduleOffset += component.getAbsoluteHeight();
        }

        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            int mouseScroll = Mouse.getDWheel();

            if (mouseScroll > 0) {
                if (components.isEmpty()) {
                    return;
                }

                if (!(components.get(0).getY() >= y + barHeight)) {
                    for (Component component : components) {
                        component.setY(component.getY() + 13);
                    }
                }
            } else if (mouseScroll < 0) {
                if (components.isEmpty()) {
                    return;
                }

                if (!(components.get(components.size() - 1).getY() + components.get(components.size() - 1).getAbsoluteHeight() <= y + maxHeight + 26)) {
                    for (Component component : components) {
                        component.setY(component.getY() - 13);
                    }
                }
            }
        }

        height = MathHelper.clamp((components.isEmpty() ? 0 : components.get(components.size() - 1).getY() - 13) * animation.getAnimationFactor(), 0, maxHeight);

        float h = 0;
        for (ModuleComponent component : components) {
            h += component.getAbsoluteHeight() * animation.getAnimationFactor();
        }

        if (!components.isEmpty()) {
            ModuleComponent lastComponent = components.get(components.size() - 1);

            if (h <= maxHeight) {
                components.get(0).setY(y + barHeight);
            }

            if (lastComponent.getY() + lastComponent.getHeight() > y + h) {
                if (components.get(0).getY() < y && lastComponent.getY() <= maxHeight) {
                    components.get(0).setY(y + barHeight);
                }

                if (lastComponent.getAnimation().getAnimationFactor() > 0 && !lastComponent.getSettingComponents().isEmpty()) {
                    SettingComponent<?> lastSettingComponent = lastComponent.getSettingComponents().get(lastComponent.getSettingComponents().size() - 1);

                    if (lastSettingComponent.getY() + lastSettingComponent.getHeight() < y + h) {
                        lastSettingComponent.setY(y + h - lastSettingComponent.getHeight());
                    }
                } else {
                    if (lastComponent.getY() + lastComponent.getHeight() < y + h) {
                        lastComponent.setY(y + h - lastComponent.getHeight());
                    }
                }
            }
        }

        RenderUtil.startGlScissor(x, y + barHeight - 0.5f, width, MathHelper.clamp(h, 0, maxHeight) + 0.5f);

        for (ModuleComponent component : components) {
            component.renderComponent(mouseX, mouseY);
        }

        if (!components.isEmpty()) {
            RenderUtil.drawRect(x, components.get(components.size() - 1).getY() + components.get(components.size() - 1).getAbsoluteHeight(), width, (height + barHeight + 4) - (components.get(components.size() - 1).getY() + components.get(components.size() - 1).getAbsoluteHeight()), new Color(25, 25, 30).getRGB());
        }

        RenderUtil.endGlScissor();

        RenderUtil.drawRect(x, y + barHeight + MathHelper.clamp(h, 0, maxHeight), width, 2, new Color(35, 35, 40).getRGB());
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 1) {
            open = !open;
            animation.setState(open);
        }

        if (animation.getAnimationFactor() == 1) {
            for (Component component : components) {
                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (animation.getAnimationFactor() == 1) {
            for (Component component : components) {
                    component.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (animation.getAnimationFactor() == 1) {
            for (Component component : components) {
                component.keyTyped(typedChar, keyCode);
            }
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + barHeight;
    }

    public float getY() {
        return y;
    }

    public float getBarHeight() {
        return barHeight;
    }

    public float getMaxHeight() {
        return maxHeight;
    }
}
