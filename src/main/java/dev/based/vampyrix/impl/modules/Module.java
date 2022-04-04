package dev.based.vampyrix.impl.modules;

import dev.based.vampyrix.Vampyrix;
import dev.based.vampyrix.api.util.misc.Keybind;
import org.lwjgl.input.Keyboard;

import dev.based.vampyrix.impl.clickgui.component.Toggleable;
import dev.based.vampyrix.api.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

//Module base class
public class Module implements Wrapper, Toggleable {
	
	protected final Minecraft mc = Minecraft.getMinecraft();
	
	public String name;
	public String description;
	private Category category;
	public boolean enabled;

	public final Setting<Keybind> keybind = new Setting<>("Keybind", new Keybind(Keyboard.KEY_NONE)).setDescription("The keybind to toggle this module");

	private final List<Setting<?>> settings = new ArrayList<>();

	public Module(String name, String description, Category category) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.enabled = false;
	}

	/**
	 * Sets up the settings. Only add parent settings to this method.
	 */
	public void setupSettings() {}

	/**
	 * Registers a setting to the list
	 * @param setting The setting to add
	 */
	public void registerSetting(Setting<?> setting) {
		this.settings.add(setting);
	}

	public void onEnable() {}
	public void onDisable() {}
	public void onUpdate() {}
	
	public void toggle() {
		this.enabled = !this.enabled;
		
		if(this.enabled) {
			MinecraftForge.EVENT_BUS.register(this);
			Vampyrix.INSTANCE.getEventBus().register(this);

			this.onEnable();
		} else {
			MinecraftForge.EVENT_BUS.unregister(this);
			Vampyrix.INSTANCE.getEventBus().unregister(this);

			this.onDisable();
		}
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
