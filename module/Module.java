package com.based.lynx.module;

import com.based.lynx.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Module {
    protected static final Minecraft mc = Minecraft.getMinecraft();
    private final String name, description;
    private final Category category;
    private final Setting<AtomicInteger> bind = new Setting<>("Keybind", new AtomicInteger(0))
            .setDescription("The keybind to toggle this module");
    private final List<Setting<?>> settings = new ArrayList<>();
    private boolean enabled;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;

        addSetting(bind);
    }

    public void onUpdate() {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void enable() {
        this.setEnabled(true);
        this.onEnable();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void disable() {
        this.setEnabled(false);
        this.onDisable();
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void toggle() {
        if (this.isEnabled()) {
            this.disable();
        } else {
            this.enable();
        }
    }

    protected boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    protected void addSetting(Setting<?> setting) {
        this.settings.add(setting);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Category getCategory() {
        return this.category;
    }

    public int getBind() {
        return this.bind.getValue().get();
    }

    public void setBind(int bind) {
        this.bind.setValue(new AtomicInteger(bind));
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    private void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }
}
