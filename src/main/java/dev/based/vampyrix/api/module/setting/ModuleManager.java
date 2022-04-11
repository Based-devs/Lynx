package dev.based.vampyrix.api.module.setting;

import dev.based.vampyrix.api.util.Wrapper;
import dev.based.vampyrix.api.module.Category;
import dev.based.vampyrix.api.module.Module;
import dev.based.vampyrix.impl.modules.client.ClickGUI;
import dev.based.vampyrix.impl.modules.movement.Flight;
import dev.based.vampyrix.impl.modules.movement.Step;
import dev.based.vampyrix.impl.modules.render.Tracers;
import me.wolfsurge.cerauno.listener.Listener;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
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

    private int sortABC(Module module1, Module module2) {
        return module1.getName().compareTo(module2.getName());
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public List<Module> getModulesByCategory(Category c) {
        return this.modules.stream().filter(module -> module.getCategory() == c).collect(Collectors.toList());
    }
    
    public void forEachEnabled(Consumer<Module> action) {
        this.getModules().stream().filter(Module::isEnabled).forEach(Objects.requireNonNull(action));
    }

    @Listener
    private void onKeyTyped(InputEvent.KeyInputEvent event) {
        if (Keyboard.isCreated() && Keyboard.getEventKey() > 0) {
            this.getModules().stream().filter(module -> module.getKeybind().getValue().getKeyCode() == Keyboard.getEventKey()).forEach(Module::toggle);
        }
    }

    @Listener
    private void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!nullCheck() && event.getEntity() == mc.player) {
            this.forEachEnabled(Module::onUpdate);
        }
    }

    @Listener
    private void onRender2D(RenderGameOverlayEvent event) {
        if (!nullCheck() && event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            this.forEachEnabled(Module::onRender2D);
        }
    }

    @Listener
    private void onRender3D(RenderWorldLastEvent event) {
        if (!nullCheck()) {
            this.forEachEnabled(Module::onRender3D);
        }
    }
}
