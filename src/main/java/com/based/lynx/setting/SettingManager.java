package com.based.lynx.setting;

import java.util.ArrayList;

import com.based.lynx.module.Module;
import com.based.lynx.setting.Setting;

public class SettingManager {
    public static final ArrayList<Setting> settings = new ArrayList();

    public void addSetting(Setting setting) {
        settings.add(setting);
    }

    public ArrayList<Setting> getSettings(Module module) {
        ArrayList<Setting> sets = new ArrayList<Setting>();
        for (Setting setting : settings) {
            if (!setting.getModule().equals(module)) continue;
            sets.add(setting);
        }
        return sets;
    }

    public static Setting getSetting(String moduleName, String name) {
        for (Setting setting : settings) {
            if (!setting.getModule().getName().equalsIgnoreCase(moduleName) || !setting.getName().equalsIgnoreCase(name))
                continue;
            return setting;
        }
        return null;
    }
}
