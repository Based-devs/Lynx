package com.based.lynx.module.client;

import com.based.lynx.module.Category;
import com.based.lynx.module.Module;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {

    public ClickGUI() {
        super("ClickGUI", "The client's ClickGUI", Category.CLIENT);
        this.setBind(Keyboard.KEY_P);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new com.based.lynx.ui.click.ClickGUI());
        toggle();
    }
}
