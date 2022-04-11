package dev.based.vampyrix.impl.clickgui.frame.button;

import dev.based.vampyrix.api.util.misc.Keybind;
import dev.based.vampyrix.api.util.render.ColourUtil;
import dev.based.vampyrix.api.util.render.RenderUtil;
import dev.based.vampyrix.api.clickgui.component.AToggleContainer;
import dev.based.vampyrix.api.clickgui.button.SettingComponent;
import dev.based.vampyrix.api.util.render.TextRenderer;
import dev.based.vampyrix.impl.clickgui.frame.button.settings.*;
import dev.based.vampyrix.api.module.Module;
import dev.based.vampyrix.api.module.setting.Setting;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Button extends AToggleContainer implements TextRenderer {

    private final Module module;
    private final List<SettingComponent<?>> components = new ArrayList<>();
    private boolean expanded;

    public Button(Module module, float x, float y, float width, float height) {
        super(module, x, y, width, height);
        this.module = module;

        float offset = y + height + height + 2;
        for (Setting<?> setting : this.module.getSettings()) {
            if (setting.getValue() instanceof Boolean) {
                this.components.add(new BooleanComponent(x + 2, offset, width - 3, height, (Setting<Boolean>) setting));
            } else if (setting.getValue() instanceof Color) {
                this.components.add(new ColourComponent(x + 2, offset, width - 3, height, (Setting<Color>) setting));
            } else if (setting.getValue() instanceof Number) {
                this.components.add(new SliderComponent(x + 2, offset, width - 3, height, (Setting<Number>) setting));
            } else if (setting.getValue() instanceof Enum<?>) {
                this.components.add(new EnumComponent(x + 2, offset, width - 3, height, (Setting<Enum<?>>) setting));
            } else if (setting.getValue() instanceof Keybind) {
                this.components.add(new KeybindComponent(x + 2, offset, width - 3, height, (Setting<Keybind>) setting));
            }

            offset += height;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.width, this.height, this.isWithin(mouseX, mouseY) ? new Color(23, 23, 23, 200).getRGB() : 0x90000000);

        this.drawString(this.module.getName(), this.x + 4, y + 3.5f, this.module.isEnabled() ? ColourUtil.getClientColour().getRGB() : -1, true);
        this.drawString(this.expanded ? "-" : "+", this.x + this.width - 10, y + 3.5f, -1, false);

        if (this.expanded) {
            float barHeight = 0;
            for (SettingComponent<?> component : this.components) {
                if (component.getSetting().isVisible()) {
                    component.setPos(this.x + 3, this.y + this.height + barHeight);
                    component.render(mouseX, mouseY, partialTicks);
                    barHeight += component.getTotalHeight();
                }
            }

            RenderUtil.drawRect(x + 1, this.y + this.height, 2, barHeight, ColourUtil.getClientColour().getRGB());
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isWithin(mouseX, mouseY) && mouseButton == 0) {
            this.module.toggle();
        } else if (isWithin(mouseX, mouseY) && mouseButton == 1) {
            this.expanded = !this.expanded;
        }

        if (this.expanded) {
            for (SettingComponent<?> settingComponent : this.components) {
                if (settingComponent.getSetting().isVisible()) {
                    settingComponent.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (this.expanded) {
            for (SettingComponent<?> settingComponent : this.components) {
                if (settingComponent.getSetting().isVisible()) {
                    settingComponent.mouseReleased(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) {
        if (this.expanded) {
            for (SettingComponent<?> settingComponent : this.components) {
                if (settingComponent.getSetting().isVisible()) {
                    settingComponent.keyTyped(keyChar, keyCode);
                }
            }
        }
    }

    @Override
    public float getTotalHeight() {
        float totalHeight = this.height;

        if (this.expanded) {
            for (SettingComponent<?> settingComponent : this.components) {
                if (settingComponent.getSetting().isVisible()) {
                    totalHeight += settingComponent.getTotalHeight();
                }
            }
        }

        return totalHeight;
    }
}
