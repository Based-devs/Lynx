package dev.based.vampyrix.impl.modules.client;

import dev.based.vampyrix.api.module.Category;
import dev.based.vampyrix.api.module.Module;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "Initializes the ClickGUI", Category.CLIENT);
        this.getKeybind().getValue().setKeyCode(Keyboard.KEY_P);
    }

    @Override
    public void setupSettings() {}

    @Override
    public void onEnable() {
        if (!nullCheck()) {
            mc.displayGuiScreen(this.getVampyrix().getClickGUIScreen());
            this.disable();
        }
    }
}