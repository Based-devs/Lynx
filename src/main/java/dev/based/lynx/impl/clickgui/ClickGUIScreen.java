package dev.based.lynx.impl.clickgui;

import dev.based.lynx.api.module.Category;
import dev.based.lynx.api.util.Wrapper;
import dev.based.lynx.impl.clickgui.frame.Frame;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGUIScreen extends GuiScreen implements Wrapper {
    private final ArrayList<Frame> frames = new ArrayList<>();

    public ClickGUIScreen() {
        int xOffset = 10;

        for (Category category : Category.values()) {
            this.frames.add(new Frame(category, this.getLynx().getModuleManager().getModulesByCategory(category), 10 + xOffset, 10, 100, 16));
            xOffset += 105;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Frame frame : this.frames) {
            frame.render(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Frame frame : this.frames) {
            frame.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Frame frame : this.frames) {
            frame.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char keychar, int keycode) throws IOException {
        for (Frame frame : this.frames) {
            frame.keyTyped(keychar, keycode);
        }

        super.keyTyped(keychar, keycode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}