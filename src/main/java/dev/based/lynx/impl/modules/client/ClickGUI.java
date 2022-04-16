package dev.based.lynx.impl.modules.client;

import dev.based.lynx.api.module.Category;
import dev.based.lynx.api.module.Module;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "Initializes the ClickGUI", Category.CLIENT);
        this.getKeybind().getValue().setKeyCode(Keyboard.KEY_P);
    }

    @Override
    public void setupSettings() {
    }

    @Override
    public void onEnable() {
        if (!nullCheck()) {
            mc.displayGuiScreen(this.getLynx().getClickGUIScreen());
            this.disable();
        }
    }
}