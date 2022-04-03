package dev.based.vampyrix.impl.modules;

import org.lwjgl.input.Keyboard;

import dev.based.vampyrix.api.module.Category;
import dev.based.vampyrix.api.module.Module;
import dev.based.vampyrix.impl.clickgui.ClickGUIScreen;

//Initializes the ClickGUI
public class ClickGUI extends Module {

    public ClickGUI() {
        super("ClickGUI", "Initializes the ClickGUI", Category.CLIENT);
        this.setKeybind(Keyboard.KEY_P); //lets not hardcode the gui bind but i am too lazy to change it rm=n
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen(ClickGUIScreen.INSTANCE);
        setToggled(false);
    }

}