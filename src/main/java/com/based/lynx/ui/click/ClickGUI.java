package com.based.lynx.ui.click;

import com.based.lynx.Lynx;
import com.based.lynx.module.Category;
import com.based.lynx.ui.click.frame.Frame;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends GuiScreen {

    private List<Frame> frames = new ArrayList<>();

    public ClickGUI() {
        float xOffset = 20;
        for (Category category : Category.values()) {
            frames.add(new Frame(xOffset, 20, 95, 16, category));
            xOffset += 100;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        frames.forEach(frame -> frame.drawFrame(mouseX, mouseY));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        frames.forEach(frame -> frame.mouseClicked(mouseX, mouseY, mouseButton));

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        frames.forEach(frame -> frame.mouseReleased(mouseX, mouseY, state));

        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        frames.forEach(frame -> frame.keyTyped(typedChar, keyCode));

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
