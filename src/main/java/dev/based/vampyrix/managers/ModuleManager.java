package dev.based.vampyrix.managers;

import dev.based.vampyrix.api.util.Wrapper;
import dev.based.vampyrix.api.module.Category;
import dev.based.vampyrix.api.module.Module;
import dev.based.vampyrix.impl.modules.client.ClickGUI;
import dev.based.vampyrix.impl.modules.movement.Flight;
import dev.based.vampyrix.impl.modules.movement.Step;
import dev.based.vampyrix.impl.modules.render.Tracers;
import me.wolfsurge.cerauno.listener.Listener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

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
    }

    public Module getModuleByName(String name) {
        return this.modules.stream().filter(module -> module.getName().toLowerCase().equals(name)).collect(Collectors.toList()).get(0);
    }

    @Listener
    private void onKeyTyped(InputEvent.KeyInputEvent event) {
        this.getModules().stream().filter(module -> module.getKeybind().getValue().getKeyCode() == Keyboard.getEventKey()).forEach(Module::toggle);
    }

    private int sortABC(Module module1, Module module2) {
        return module1.getName().compareTo(module2.getName());
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public List<Module> getModulesByCategory(Category c) {
        return this.modules.stream().filter(module -> module.getCategory() == c).collect(Collectors.toList());
    }
}
