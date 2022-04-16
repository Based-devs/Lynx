package dev.based.lynx.api.clickgui.button;

import dev.based.lynx.api.clickgui.component.AComponent;
import dev.based.lynx.api.module.setting.Setting;
import dev.based.lynx.api.util.misc.Keybind;
import dev.based.lynx.api.util.render.ColourUtil;
import dev.based.lynx.api.util.render.RenderUtil;
import dev.based.lynx.api.util.render.TextRenderer;
import dev.based.lynx.impl.clickgui.frame.button.settings.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SettingComponent<T> extends AComponent implements TextRenderer {
    private final Setting<T> setting;
    private final List<SettingComponent<?>> subcomponents = new ArrayList<>();
    private boolean expanded;

    public SettingComponent(float x, float y, float width, float height, Setting<T> setting) {
        super(x, y, width, height);
        this.setting = setting;

        float offset = y + height;
        for (Setting<?> subsetting : setting.getSubsettings()) {
            if (subsetting.getValue() instanceof Boolean) {
                subcomponents.add(new BooleanComponent(x + 2, offset, width - 2, height, (Setting<Boolean>) subsetting));
            } else if (subsetting.getValue() instanceof Color) {
                subcomponents.add(new ColourComponent(x + 2, offset, width - 2, height, (Setting<Color>) subsetting));
            } else if (subsetting.getValue() instanceof Number) {
                subcomponents.add(new SliderComponent(x + 2, offset, width - 2, height, (Setting<Number>) subsetting));
            } else if (subsetting.getValue() instanceof Enum<?>) {
                subcomponents.add(new EnumComponent(x + 2, offset, width - 2, height, (Setting<Enum<?>>) subsetting));
            } else if (subsetting.getValue() instanceof Keybind) {
                subcomponents.add(new KeybindComponent(x + 2, offset, width - 2, height, (Setting<Keybind>) subsetting));
            }

            offset += height;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        if (!subcomponents.isEmpty()) {
            drawString(expanded ? "-" : "+", x + width - 10, y + 3.5f, -1, false);
        }

        if (isExpanded()) {
            float offset = 0;
            for (SettingComponent<?> subcomponent : getSubcomponents()) {
                if (subcomponent.getSetting().isVisible()) {
                    subcomponent.setPos(x + 2, y + height + offset);
                    subcomponent.render(mouseX, mouseY, partialTicks);
                    offset += subcomponent.getTotalHeight();
                }
            }

            RenderUtil.drawRect(x, y + height, 2, offset, ColourUtil.getClientColour().getRGB());
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isWithin(mouseX, mouseY) && mouseButton == 1) {
            expanded = !expanded;
        }

        if (expanded) {
            for (SettingComponent<?> settingComponent : subcomponents) {
                if (settingComponent.getSetting().isVisible()) {
                    settingComponent.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (expanded) {
            for (SettingComponent<?> settingComponent : subcomponents) {
                if (settingComponent.getSetting().isVisible()) {
                    settingComponent.mouseReleased(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) {
        if (expanded) {
            for (SettingComponent<?> settingComponent : subcomponents) {
                if (settingComponent.getSetting().isVisible()) {
                    settingComponent.keyTyped(keyChar, keyCode);
                }
            }
        }
    }

    public float getTotalHeight() {
        float totalHeight = height;

        if (isExpanded()) {
            for (SettingComponent<?> subcomponent : subcomponents) {
                if (subcomponent.getSetting().isVisible()) {
                    totalHeight += subcomponent.getTotalHeight();
                }
            }
        }

        return totalHeight;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public Setting<T> getSetting() {
        return setting;
    }

    public List<SettingComponent<?>> getSubcomponents() {
        return subcomponents;
    }
}
