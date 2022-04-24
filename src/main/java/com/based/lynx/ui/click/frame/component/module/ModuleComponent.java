package com.based.lynx.ui.click.frame.component.module;

import com.based.lynx.module.Module;
import com.based.lynx.module.client.Colors;
import com.based.lynx.setting.Setting;
import com.based.lynx.ui.click.frame.Frame;
import com.based.lynx.ui.click.frame.component.Component;
import com.based.lynx.ui.click.frame.component.setting.*;
import com.based.lynx.util.Animation;
import com.based.lynx.util.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lwjgl.opengl.GL11.glScalef;

public class ModuleComponent extends Component {

    private final Module module;
    private final List<SettingComponent<?>> settingComponents = new ArrayList<>();
    private final Animation animation = new Animation(200, false);
    private final Animation openAnimation = new Animation(200, false);
    private boolean open;
    private Frame frame;

    public ModuleComponent(Module module, float x, float y, float width, float height, Frame frame) {
        super(x, y, width, height);

        this.module = module;
        this.frame = frame;
        this.animation.setStateHard(module.isEnabled());

        float yOffset = y + height;
        for (Setting<?> setting : module.getSettings()) {
            if (setting.getValue() instanceof Boolean) {
                settingComponents.add(new BooleanComponent((Setting<Boolean>) setting, x, yOffset, width, height, this));
                yOffset += height;
            } else if (setting.getValue() instanceof Enum<?>) {
                settingComponents.add(new ModeComponent((Setting<Enum<?>>) setting, x, yOffset, width, height, this));
                yOffset += height;
            } else if (setting.getValue() instanceof Color) {
                settingComponents.add(new ColourComponent((Setting<Color>) setting, x, yOffset, width, height, this));
                yOffset += height;
            } else if (setting.getValue() instanceof AtomicInteger) {
                settingComponents.add(new KeybindComponent((Setting<AtomicInteger>) setting, x, yOffset, width, height, this));
                yOffset += height;
            } else if (setting.getValue() instanceof Float || setting.getValue() instanceof Double) {
                settingComponents.add(new SliderComponent((Setting<Number>) setting, x, yOffset, width, height, this));
                yOffset += height;
            }
        }
    }

    @Override
    public void renderComponent(int mouseX, int mouseY) {
        RenderUtil.drawRect(getX(), getY(), getWidth(), getHeight(), isHovered(mouseX, mouseY) ? new Color(35, 35, 40).getRGB() : new Color(30, 30, 35).getRGB());

        glScalef(0.75f, 0.75f, 0.75f);
        float scaleFactor = 1 / 0.75f;

        RenderUtil.drawStringWithShadow(module.getName(), (getX() + 5 + (2 * animation.getAnimationFactor())) * scaleFactor, (getY() + 4) * scaleFactor, -1, true);

        glScalef(scaleFactor, scaleFactor, scaleFactor);

        if (openAnimation.getAnimationFactor() > 0) {
            float yOffset = getY() + getHeight();
            for (SettingComponent<?> component : settingComponents) {
                component.setY(yOffset);
                component.renderComponent(mouseX, mouseY);
                RenderUtil.drawRect(getX(), yOffset, 1, component.getHeight(), Colors.INSTANCE.colour.getValue().getRGB());
                yOffset += component.getAbsoluteHeight();
            }
        }

        RenderUtil.drawRect(getX(), getY() + getHeight() - (getHeight() * animation.getAnimationFactor()), 1, getHeight() * animation.getAnimationFactor(), Colors.INSTANCE.colour.getValue().getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && getY() >= frame.getY() + frame.getBarHeight() && getY() <= frame.getY() + frame.getBarHeight() + frame.getMaxHeight() - 13) {
            if (mouseButton == 0) {
                module.toggle();
                this.animation.setState(module.isEnabled());
            } else if (mouseButton == 1) {
                open = !open;
                this.openAnimation.setState(open);
            }
        }

        if (open) {
            for (Component component : settingComponents) {
                if (component.getY() >= frame.getY() + frame.getBarHeight() && component.getY() <= frame.getY() + frame.getBarHeight() + frame.getMaxHeight() - 13) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (open) {
            for (Component component : settingComponents) {
                if (component.getY() >= frame.getY() + frame.getBarHeight() && component.getY() <= frame.getY() + frame.getBarHeight() + frame.getMaxHeight() - 13) {
                    component.mouseReleased(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (open) {
            for (Component component : settingComponents) {
                if (component.getY() >= frame.getY() + frame.getBarHeight() && component.getY() <= frame.getY() + frame.getBarHeight() + frame.getMaxHeight() - 13) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }
    }

    public float getAbsoluteHeight() {
        float settingHeight = 0;

        for (SettingComponent<?> component : settingComponents) {
            settingHeight += component.getAbsoluteHeight();
        }

        return getHeight() + (settingHeight * openAnimation.getAnimationFactor());
    }

    public List<SettingComponent<?>> getSettingComponents() {
        return settingComponents;
    }

    public Animation getAnimation() {
        return animation;
    }

    public Frame getFrame() {
        return frame;
    }
}
