package dev.based.vampyrix.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.based.vampyrix.impl.modules.Category;
import dev.based.vampyrix.impl.modules.Module;
import dev.based.vampyrix.api.util.Wrapper;
import dev.based.vampyrix.impl.modules.impl.client.ClickGUI;
import dev.based.vampyrix.impl.modules.impl.movement.Flight;
import dev.based.vampyrix.impl.modules.impl.movement.Step;
import net.minecraftforge.common.MinecraftForge;

public class ModuleManager implements Wrapper {
	
	private List<Module> modules;

	public ModuleManager() {
		modules = new ArrayList<>();

		modules = Arrays.asList(
				// Add movement modules
				new Flight(),
				new Step(),

				// Add client modules
				new ClickGUI()
		);

		modules.forEach(module -> {
			// Setup module settings
			module.setupSettings();

			// Add module keybind last
			module.registerSetting(module.getKeybind());
		});

		modules.sort(this::sortABC);

		getVampyrix().getEventBus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public Module getModuleByName(String name) {
		for (Module m : this.modules) {
			if (m.getName().equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}
	
	private int sortABC(Module module1, Module module2) {
		return module1.getName().compareTo(module2.getName());
	}
	
	public List<Module> getModules() {
		return this.modules;
	}
	
	public List<Module> getModulesByCategory(Category c) {
		List<Module> modulesList = new ArrayList<>();
		
		for (Module m : modules) {
			if (m.getCategory() == c) {
				modulesList.add(m);
			}
		}

		return modulesList;
	}

}
