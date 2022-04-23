package com.based.lynx.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.based.lynx.Lynx;
import com.based.lynx.module.Module;
import com.based.lynx.setting.Setting;
import com.based.lynx.setting.SettingManager;
import com.based.lynx.util.FileUtil;
import net.minecraft.client.Minecraft;

public class Config
        extends Thread {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final File mainFolder = new File(Config.mc.gameDir + File.separator + "lynx");
    private static final String MODULES = "Modules.JSON";
    private static final String SETTINGS = "Settings.JSON";
    private static final String BINDS = "Binds.JSON";

    @Override
    public void run() {
        if (!mainFolder.exists() && !mainFolder.mkdirs()) {
            System.out.println("Failed to create config folder");
        }
        try {
            FileUtil.saveFile(new File(mainFolder.getAbsolutePath(), MODULES), Lynx.moduleManager.getEnabledModules().stream().map(Module::getName).collect(Collectors.toCollection(ArrayList::new)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtil.saveFile(new File(mainFolder.getAbsolutePath(), SETTINGS), Config.getSettings());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtil.saveFile(new File(mainFolder.getAbsolutePath(), BINDS), Lynx.moduleManager.getModules().stream().map(module -> module.getName() + ":" + module.getBind()).collect(Collectors.toCollection(ArrayList::new)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig() {
        if (!mainFolder.exists()) {
            return;
        }
        try {
            String[] split;
            for (String s : FileUtil.loadFile(new File(mainFolder.getAbsolutePath(), MODULES))) {
                try {
                    Lynx.moduleManager.getModule(s).enable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (String s : FileUtil.loadFile(new File(mainFolder.getAbsolutePath(), SETTINGS))) {
                try {
                    split = s.split(":");
                    Config.saveSetting(SettingManager.getSetting(split[1], split[0]), split[2]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (String s : FileUtil.loadFile(new File(mainFolder.getAbsolutePath(), BINDS))) {
                try {
                    split = s.split(":");
                    Lynx.moduleManager.getModule(split[0]).setBind(Integer.parseInt(split[1]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> getSettings() {
        ArrayList<String> content = new ArrayList<String>();
        for (Setting setting : SettingManager.settings) {
            switch (setting.getType()) {
                case BOOLEAN: {
                    content.add(String.format("%s:%s:%s", setting.getName(), setting.getModule().getName(), setting.getBooleanValue()));
                    break;
                }
                case INTEGER: {
                    content.add(String.format("%s:%s:%s", setting.getName(), setting.getModule().getName(), setting.getIntegerValue()));
                    break;
                }
                case ENUM: {
                    content.add(String.format("%s:%s:%s", setting.getName(), setting.getModule().getName(), setting.getEnumValue()));
                    break;
                }
            }
        }
        return content;
    }

    private static void saveSetting(Setting setting, String value) {
        switch (setting.getType()) {
            case BOOLEAN: {
                setting.setBooleanValue(Boolean.parseBoolean(value));
                break;
            }
            case INTEGER: {
                setting.setIntegerValue(Integer.parseInt(value));
                break;
            }
            case ENUM: {
                setting.setEnumValue(value);
                break;
            }
        }
    }
}
