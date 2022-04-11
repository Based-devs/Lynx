package dev.based.vampyrix.api.module;

import dev.based.vampyrix.api.clickgui.component.Toggleable;
import dev.based.vampyrix.api.module.setting.Setting;
import dev.based.vampyrix.api.util.Wrapper;
import dev.based.vampyrix.api.util.misc.Keybind;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class Module implements Wrapper, Toggleable {
    public final Setting<Keybind> keybind = new Setting<>("Keybind", new Keybind(Keyboard.KEY_NONE)).setDescription("The keybind to toggle this module");
    protected final Minecraft mc = Minecraft.getMinecraft();
    private final String name, description;
    private final Category category;
    private final List<Setting<?>> settings = new ArrayList<>();
    private boolean enabled = false;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    /**
     * Sets up the settings. Only add parent settings to this method.
     */
    public void setupSettings() {}

    /**
     * Registers a setting to the list
     *
     * @param setting The setting to add
     */
    public void registerSetting(Setting<?> setting) {
        this.settings.add(setting);
    }

    public void onEnable() {}
    public void onDisable() {}

    public void onUpdate() {}
    public void onRender2D() {}
    public void onRender3D() {}

    public void enable() {
        if (!this.enabled) {
            this.enabled = true;

            MinecraftForge.EVENT_BUS.register(this);
            this.getVampyrix().getEventBus().register(this);
            this.onEnable();
        }
    }

    public void disable() {
        if (this.enabled) {
            this.enabled = false;

            MinecraftForge.EVENT_BUS.unregister(this);
            this.getVampyrix().getEventBus().unregister(this);
            this.onDisable();
        }
    }

    public void toggle() {
        if (this.enabled) this.disable();
        else this.enable();
    }

    // Getters
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Setting<Keybind> getKeybind() {
        return keybind;
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }
}
