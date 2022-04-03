package dev.based.vampyrix.impl.clickgui;

import java.io.IOException;
import java.util.ArrayList;

import dev.based.vampyrix.api.module.Category;
import dev.based.vampyrix.api.module.Module;
import dev.based.vampyrix.impl.Vampyrix;
import net.minecraft.client.gui.GuiScreen;

//Base ClickGUI class that handles frames
public class ClickGUIScreen extends GuiScreen {

    ArrayList < Frame > frames = new ArrayList < > ();

    public static ClickGUIScreen INSTANCE = new ClickGUIScreen();

    public ClickGUIScreen() {
        int xoffset = 10;
        for (Category category: Category.values()) {
            final ArrayList < Module > modulesList = Vampyrix.INSTANCE.getModuleManager().getModulesByCategory(category);
            frames.add(new Frame(category, modulesList, 10 + xoffset, 10, 100, 12));
            xoffset += 100;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Frame frame: frames) {
            frame.render(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Frame frame: frames) {
            frame.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Frame frame: frames) {
            frame.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char keychar, int keycode) throws IOException {
        for (Frame frame: frames) {
            frame.keyTyped(keychar, keycode);
        }

        super.keyTyped(keychar, keycode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }


}