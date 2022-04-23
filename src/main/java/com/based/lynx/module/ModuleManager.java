package com.based.lynx.module;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.based.lynx.Lynx;
import com.based.lynx.module.Category;
import com.based.lynx.module.Module;

public class ModuleManager {
    private final ArrayList<Module> modules = new ArrayList();

    public ModuleManager() {
        this.modules.add(new AutoArmor("AutoArmor", "", Category.COMBAT));
        this.modules.add(new AutoLog("AutoLog", "", Category.COMBAT));
        this.modules.add(new Criticals("Criticals", "", Category.COMBAT));
//gonna do this later too tired rn
    }

    public static void onUpdate() {
        Lynx.moduleManager.getModules().stream().filter(Module::isEnabled).forEach(Module::onUpdate);
    }

    public ArrayList<Module> getModules() {
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
