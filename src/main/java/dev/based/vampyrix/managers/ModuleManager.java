package dev.based.vampyrix.managers;

import dev.based.vampyrix.api.util.Wrapper;
import dev.based.vampyrix.api.module.Category;
import dev.based.vampyrix.api.module.Module;
import dev.based.vampyrix.impl.modules.client.ClickGUI;
import dev.based.vampyrix.impl.modules.movement.Flight;
import dev.based.vampyrix.impl.modules.movement.Step;
import dev.based.vampyrix.impl.modules.render.Tracers;
import net.minecraftforge.common.MinecraftForge;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager implements Wrapper {

    private final List<Module> modules;

    public ModuleManager() {
        this.modules = Arrays.asList(
                // Add movement modules
                new Flight(),
                new Step(),

                // Add render modules
                new Tracers(),

                // Add client modules
                new ClickGUI()
        );

        this.modules.forEach(module -> {
            // Setup module settings
            module.setupSettings();

            // Add module keybind last
            module.registerSetting(module.getKeybind());
        });

        this.modules.sort(this::sortABC);

        this.getVampyrix().getEventBus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().toLowerCase().equals(name)).collect(Collectors.toList()).get(0);
    }

    private int sortABC(Module module1, Module module2) {
        return module1.getName().compareTo(module2.getName());
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getModulesByCategory(Category c) {
        return modules.stream().filter(module -> module.getCategory() == c).collect(Collectors.toList());
    }
}
