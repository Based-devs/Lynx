package dev.based.vampyrix.impl.clickgui.frame.button;

import dev.based.vampyrix.api.util.misc.Keybind;
import dev.based.vampyrix.api.util.render.ColourUtil;
import dev.based.vampyrix.api.util.render.RenderUtil;
import dev.based.vampyrix.impl.clickgui.component.AToggleContainer;
import dev.based.vampyrix.impl.clickgui.frame.button.settings.impl.BooleanComponent;
import dev.based.vampyrix.impl.clickgui.frame.button.settings.SettingComponent;
import dev.based.vampyrix.impl.clickgui.frame.button.settings.impl.EnumComponent;
import dev.based.vampyrix.impl.clickgui.frame.button.settings.impl.KeybindComponent;
import dev.based.vampyrix.impl.clickgui.frame.button.settings.impl.SliderComponent;
import dev.based.vampyrix.impl.modules.Module;
import dev.based.vampyrix.impl.modules.Setting;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Button extends AToggleContainer {

    private boolean expanded;
    private final Module module;
    private final List<SettingComponent<?>> components = new ArrayList<>();

    public Button(Module module, float x, float y, float width, float height) {
        super(module, x, y, width, height);
        this.module = module;

        float offset = y + height + height + 2;
        for (Setting<?> setting : this.module.getSettings()) {
            if (setting.getValue() instanceof Boolean) {
                components.add(new BooleanComponent(x + 2, offset, width - 3, height, (Setting<Boolean>) setting));
            } else if (setting.getValue() instanceof Number) {
                components.add(new SliderComponent(x + 2, offset, width - 3, height, (Setting<Number>) setting));
            } else if (setting.getValue() instanceof Enum<?>) {
                components.add(new EnumComponent(x + 2, offset, width - 3, height, (Setting<Enum<?>>) setting));
            } else if (setting.getValue() instanceof Keybind) {
                components.add(new KeybindComponent(x + 2, offset, width - 3, height, (Setting<Keybind>) setting));
            }

            offset += height;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(x, y, width, height, isWithin(mouseX, mouseY) ? new Color(23, 23, 23, 200).getRGB() : 0x90000000);

        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(module.getName(), x + 4, y + 3.5f, module.isEnabled() ? ColourUtil.getClientColour().brighter().brighter().getRGB() : -1);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(expanded ? "-" : "+", x + width - 10, y + 3.5f, -1);

        if (expanded) {
            float barHeight = 0;
            for (SettingComponent<?> component : components) {
                if (component.getSetting().isVisible()) {
                    component.setPos(x + 3,y + height + barHeight);
                    component.render(mouseX, mouseY, partialTicks);
                    barHeight += component.getTotalHeight();
                }
            }

            RenderUtil.drawRect(x + 1, y + height, 2, barHeight,  ColourUtil.getClientColour().getRGB());
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isWithin(mouseX, mouseY) && mouseButton == 0) {
            module.toggle();
        } else if (isWithin(mouseX, mouseY) && mouseButton == 1) {
            expanded = !expanded;
        }

        if (expanded) {
            for (SettingComponent<?> settingComponent : components) {
                if (settingComponent.getSetting().isVisible()) {
                    settingComponent.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (expanded) {
            for (SettingComponent<?> settingComponent : components) {
                if (settingComponent.getSetting().isVisible()) {
                    settingComponent.mouseReleased(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) {
        if (expanded) {
            for (SettingComponent<?> settingComponent : components) {
                if (settingComponent.getSetting().isVisible()) {
                    settingComponent.keyTyped(keyChar, keyCode);
                }
            }
        }
    }

    @Override
    public float getTotalHeight() {
        float totalHeight = height;

        if (expanded) {
            for (SettingComponent<?> settingComponent : components) {
                if (settingComponent.getSetting().isVisible()) {
                    totalHeight += settingComponent.getTotalHeight();
                }
            }
        }

        return totalHeight;
    }

}
