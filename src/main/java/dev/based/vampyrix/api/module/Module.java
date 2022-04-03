package dev.based.vampyrix.api.module;

import org.lwjgl.input.Keyboard;

import dev.based.vampyrix.api.clickgui.Toggleable;
import dev.based.vampyrix.api.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

//Module base class
public class Module implements Wrapper, Toggleable {
	
	protected final Minecraft mc = Minecraft.getMinecraft();
	
	public String name;
	public String description;
	private int keybind;
	private Category category;
	public boolean toggled;
	
	public Module(String name, String description, Category category) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.toggled = false;
		this.keybind = Keyboard.KEY_NONE;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getKeybind() {
		return keybind;
	}
	
	public void setKeybind(int keybind) {
		this.keybind = keybind;
	}
	
	public boolean isToggled() {
		return toggled;
	}
	
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
		
		if(this.toggled) {
			this.onEnable();
		}else {
			this.onDisable();
		}
	}
	
	public void toggle() {
		this.toggled = !this.toggled;
		
		if(this.toggled) {
			this.onEnable();
		}else {
			this.onDisable();
		}
	}
	
	public void onEnable() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void onDisable() {
		MinecraftForge.EVENT_BUS.unregister(this);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Category getCategory() {
		return this.category;	
	}
	
	public void onUpdate() {}
	
}
