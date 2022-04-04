package dev.based.vampyrix.impl.clickgui;

import java.io.IOException;
import java.util.ArrayList;

import dev.based.vampyrix.impl.clickgui.frame.Frame;
import dev.based.vampyrix.impl.modules.Category;
import dev.based.vampyrix.api.util.Wrapper;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUIScreen extends GuiScreen implements Wrapper {

    ArrayList<Frame> frames = new ArrayList < > ();

    public static ClickGUIScreen INSTANCE = new ClickGUIScreen();

    public ClickGUIScreen() {
        int xOffset = 10;
        for (Category category : Category.values()) {
            frames.add(new Frame(category, getVampyrix().getModuleManager().getModulesByCategory(category), 10 + xOffset, 10, 100, 16));
            xOffset += 105;
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