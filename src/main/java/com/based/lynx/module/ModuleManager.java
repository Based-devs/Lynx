package com.based.lynx.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.based.lynx.Lynx;
import com.based.lynx.module.Category;
import com.based.lynx.module.Module;
import com.based.lynx.module.client.Colours;
import com.based.lynx.module.combat.AutoArmor;
import com.based.lynx.module.combat.AutoCrystal;
import com.based.lynx.module.combat.AutoLog;
import com.based.lynx.module.combat.Criticals;
import com.based.lynx.module.exploit.PacketCanceller;
import com.based.lynx.module.exploit.PortalGodmode;
import com.based.lynx.module.movement.Jesus;
import com.based.lynx.module.movement.NoSlow;
import com.based.lynx.module.movement.Sprint;
import com.based.lynx.module.movement.Velocity;

public class ModuleManager {
    private final List<Module> modules;

    public ModuleManager() {
        this.modules = Arrays.asList(
                new AutoArmor(),
                new AutoCrystal(),
                new AutoLog(),
                new Criticals(),

                new PacketCanceller(),
                new PortalGodmode(),

                new Jesus(),
                new NoSlow(),
                new Sprint(),
                new Velocity(),

                new Colours()
        );
    }

    public static void onUpdate() {
        Lynx.moduleManager.getModules().stream().filter(Module::isEnabled).forEach(Module::onUpdate);
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public Module getModule(String name) {
        for (Module module : this.modules) {
            if (!module.getName().equalsIgnoreCase(name)) continue;
            return module;
        }
        return null;
    }

    public ArrayList<Module> getModules(Category category) {
        ArrayList<Module> mods = new ArrayList<Module>();
        for (Module module : this.modules) {
            if (!module.getCategory().equals(category)) continue;
            mods.add(module);
        }
        return mods;
    }

    public ArrayList<Module> getEnabledModules() {
        return this.modules.stream().filter(Module::isEnabled).collect(Collectors.toCollection(ArrayList::new));
    }
}
