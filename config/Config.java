package com.based.lynx.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.based.lynx.Lynx;
import com.based.lynx.module.Module;
import com.based.lynx.setting.Setting;
import com.based.lynx.util.FileUtil;
import net.minecraft.client.Minecraft;

public class Config
        extends Thread {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final File mainFolder = new File(Config.mc.gameDir + File.separator + "lynx");
    private static final String MODULES = "Modules.JSON";
    private static final String SETTINGS = "Settings.JSON";
    private static final String BINDS = "Binds.JSON";

    public static void loadConfig() {
        if (!mainFolder.exists()) {
            return;
        }
    }

    @Override
    public void run() {

    }
}
