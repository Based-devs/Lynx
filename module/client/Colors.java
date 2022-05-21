package com.based.lynx.module.client;

import com.based.lynx.module.Category;
import com.based.lynx.module.Module;
import com.based.lynx.setting.Setting;

import java.awt.*;

public class Colors extends Module {
    public static Colors INSTANCE;

    public final Setting<Color> color = new Setting<>("Color", new Color(50, 80, 255))
            .setDescription("The client's main colour");

    public Colors() {
        super("Colors", "Control client colors", Category.CLIENT);
        addSetting(color);
        INSTANCE = this;
    }

}
