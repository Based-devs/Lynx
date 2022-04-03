package dev.based.vampyrix.api.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.based.vampyrix.impl.modules.*;

public class ModuleManager {
	
	private List<Module> modules;

	public ModuleManager() {
		modules = new ArrayList<>();

		modules = Arrays.asList(
				new ClickGUI()
		);
		
		modules.sort(this::sortABC);
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
