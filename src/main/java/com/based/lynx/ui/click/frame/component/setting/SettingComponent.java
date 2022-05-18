package com.based.lynx.ui.click.frame.component.setting;

import com.based.lynx.module.client.Colors;
import com.based.lynx.setting.Setting;
import com.based.lynx.ui.click.frame.component.Component;
import com.based.lynx.ui.click.frame.component.module.ModuleComponent;
import com.based.lynx.util.Animation;
import com.based.lynx.util.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SettingComponent<T> extends Component {

    private final Setting<T> setting;
    private final List<SettingComponent<?>> subsettings = new ArrayList<>();
    private final Animation openAnimation = new Animation(200, false);
    private boolean open;
    private ModuleComponent moduleComponent;

    public SettingComponent(Setting<T> setting, float x, float y, float width, float height, ModuleComponent moduleComponent) {
        super(x, y, width, height);
        this.setting = setting;

        this.moduleComponent = moduleComponent;

        float yOffset = getY() + 13;
        for (Setting<?> subsetting : setting.getSubsettings()) {
            if (subsetting.getValue() instanceof Boolean) {
                subsettings.add(new BooleanComponent((Setting<Boolean>) subsetting, x + 1, yOffset, width - 1, height, moduleComponent));
                yOffset += height;
            } else if (subsetting.getValue() instanceof Enum<?>) {
                subsettings.add(new ModeComponent((Setting<Enum<?>>) subsetting, x + 1, yOffset, width - 1, height, moduleComponent));
                yOffset += height;
            } else if (subsetting.getValue() instanceof Color) {
                subsettings.add(new ColourComponent((Setting<Color>) subsetting, x + 1, yOffset, width - 1, height, moduleComponent));
                yOffset += height;
            } else if (subsetting.getValue() instanceof AtomicInteger) {
                subsettings.add(new KeybindComponent((Setting<AtomicInteger>) subsetting, x + 1, yOffset, width - 1, height, moduleComponent));
                yOffset += height;
            } else if (subsetting.getValue() instanceof Float || setting.getValue() instanceof Double) {
                subsettings.add(new SliderComponent((Setting<Number>) subsetting, x + 1, yOffset, width - 1, height, moduleComponent));
                yOffset += height;
            }
        }
    }

    @Override
    public void renderComponent(int mouseX, int mouseY) {
        if (openAnimation.getAnimationFactor() > 0) {
            float yOffset = getY() + getHeight();
            for (SettingComponent<?> subcomponent : subsettings) {
                if (!subcomponent.getSetting().isVisible()) {
                    continue;
                }

                subcomponent.setY(yOffset);
                subcomponent.renderComponent(mouseX, mouseY);
                RenderUtil.drawRect(getX(), yOffset, 2, subcomponent.getHeight(), Colors.INSTANCE.colour.getValue().getRGB());
                yOffset += subcomponent.getAbsoluteHeight();
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 1) {
            open = !open;
            openAnimation.setState(open);
        }

        if (open) {
            subsettings.forEach(subsetting -> {
                subsetting.mouseClicked(mouseX, mouseY, mouseButton);
            });
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (open) {
            subsettings.forEach(subsetting -> {
                subsetting.mouseReleased(mouseX, mouseY, mouseButton);
            });
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (open) {
            subsettings.forEach(subsetting -> {
                subsetting.keyTyped(typedChar, keyCode);
            });
        }
    }

    public float getAbsoluteHeight() {
        float settingHeight = 0;

        for (SettingComponent<?> subcomponent : subsettings) {
            settingHeight += subcomponent.getAbsoluteHeight();
        }

        return getHeight() + (settingHeight * openAnimation.getAnimationFactor());
    }

    public Setting<T> getSetting() {
        return setting;
    }
}
