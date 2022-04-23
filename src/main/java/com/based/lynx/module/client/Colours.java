package com.based.lynx.module.client;

import com.based.lynx.module.Category;
import com.based.lynx.module.Module;
import com.based.lynx.setting.Setting;

import java.awt.*;

public class Colours extends Module {
    public static Colours INSTANCE;

    public final Setting<Color> colour = new Setting<>("Colour", new Color(50, 80, 255))
            .setDescription("The client's main colour");

    public Colours() {
        super("Colours", Category.CLIENT);
        addSetting(colour);
        INSTANCE = this;
    }

}
