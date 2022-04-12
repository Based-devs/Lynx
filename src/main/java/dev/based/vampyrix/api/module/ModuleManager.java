package dev.based.vampyrix.api.module;

import dev.based.vampyrix.api.event.entity.LivingUpdateEvent;
import dev.based.vampyrix.api.event.render.RenderEvent;
import dev.based.vampyrix.api.event.system.KeyEvent;
import dev.based.vampyrix.api.util.Wrapper;
import dev.based.vampyrix.impl.modules.client.ClickGUI;
import dev.based.vampyrix.impl.modules.client.Colors;
import dev.based.vampyrix.impl.modules.combat.AutoArmor;
import dev.based.vampyrix.impl.modules.movement.Flight;
import dev.based.vampyrix.impl.modules.movement.Step;
import dev.based.vampyrix.impl.modules.render.Tracers;
import me.bush.eventbus.annotation.EventListener;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ModuleManager implements Wrapper {
    private final List<Module> modules;

    public ModuleManager() {
        this.modules = Arrays.asList(
                // Add movement modules
                new Flight(), new Step(),

                // Add render modules
                new Tracers(),

                // Add client modules
                new ClickGUI(), Colors.INSTANCE,
                // Add combat modules
                new AutoArmor()
                //sup
        );

        this.modules.forEach(module -> {
            // Setup module settings
            module.setupSettings();

            // Add module keybind last
            module.registerSetting(module.getKeybind());
        });

        this.modules.sort(this::sortABC);

        this.getVampyrix().getEventBus().subscribe(this);
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public Module getModuleByName(String name) {
        return this.modules.stream().filter(module -> module.getName().toLowerCase().equals(name)).collect(Collectors.toList()).get(0);
    }

    private int sortABC(Module module1, Module module2) {
        return module1.getName().compareTo(module2.getName());
    }

    public List<Module> getModulesByCategory(Category c) {
        return this.modules.stream().filter(module -> module.getCategory() == c).collect(Collectors.toList());
    }

    public void forEachEnabled(Consumer<Module> action) {
        this.modules.stream().filter(Module::isEnabled).forEach(Objects.requireNonNull(action));
    }

    @EventListener
    private void onKeyTyped(KeyEvent event) {
        if (event.getKey() != 0) {
            this.modules.stream().filter(module -> module.getKeybind().getValue().getKeyCode() == event.getKey()).forEach(Module::toggle);
        }
    }

    @EventListener
    private void onUpdate(LivingUpdateEvent event) {
        if (!nullCheck() && event.getEntity() == mc.player) {
            this.forEachEnabled(Module::onUpdate);
        }
    }

    @EventListener
    private void onRender2D(RenderEvent.Render2D event) {
        if (!nullCheck()) {
            this.forEachEnabled(Module::onRender2D);
        }
    }

    @EventListener
    private void onRender3D(RenderEvent.Render3D event) {
        if (!nullCheck()) {
            this.forEachEnabled(Module::onRender3D);
        }
    }
}
