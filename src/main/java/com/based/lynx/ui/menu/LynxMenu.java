package com.based.lynx.ui.menu;

import com.based.lynx.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class LynxMenu extends GuiScreen {

    @Override
    public void initGui() {
        this.addButton(new LynxButton(0, width / 2 - 100, height / 2 + 10, 200, 20, "Singleplayer"));
        this.addButton(new LynxButton(1, width / 2 - 100, height / 2 + 35, 200, 20, "Multiplayer"));
        this.addButton(new LynxButton(2, width / 2 - 100, height / 2 + 60, 95, 20, "Options"));
        this.addButton(new LynxButton(3, width / 2 + 5, height / 2 + 60, 95, 20, "Quit"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float xOffset = -1.0f * ((mouseX - this.width / 2.0f) / (this.width / 10.0f));
        float yOffset = -1.0f * ((mouseY - this.height / 2.0f) / (this.height / 10.0f));

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("lynx", "textures/background.png"));
        RenderUtil.drawModalRectWithCustomSizedTexture(-10.0f + xOffset, -10.0f + yOffset, 0, 0, this.width + 10f,this.height + 10f, 1920f, 1080f); //the "1920f, 1080f" might need to be changed

        glPushMatrix();
        glScalef(4, 4, 4);
        drawCenteredString(mc.fontRenderer, "Lynx", (width / 4) / 2, ((height - 70) / 4) / 2, 0xFFFFFF);
        glPopMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new GuiWorldSelection(this));
                break;
            case 1:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 2:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;
            case 3:
                mc.shutdown();
                break;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }
}
