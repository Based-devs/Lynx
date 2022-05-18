package com.based.lynx.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.based.lynx.module.client.*;
import com.based.lynx.module.combat.*;
import com.based.lynx.module.exploit.*;
import com.based.lynx.module.misc.*;
import com.based.lynx.module.movement.*;

public class ModuleManager {
    private final List<Module> modules;

    public ModuleManager() {
        this.modules = Arrays.asList(
                new AutoArmor(),
               // new AutoCrystal(),
                new AutoLog(),
                new Criticals(),

                new PacketCanceller(),
                new PortalGodmode(),

                new FakePlayer(),

                new Jesus(),
                new NoSlow(),
                new Sprint(),
                new Velocity(),
                new ClickGUI(),
                new Colors()
        );
    }

    public void onUpdate() {
        this.getModules().stream().filter(Module::isEnabled).forEach(Module::onUpdate);
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
        ArrayList<Module> mods = new ArrayList<>();
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
