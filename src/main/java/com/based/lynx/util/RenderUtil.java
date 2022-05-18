package com.based.lynx.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glScissor;

public class RenderUtil {

    public static void drawRect(float x, float y, float width, float height, int colour) {
        float c = (float) (colour >> 24 & 255) / 255.0F;
        float c1 = (float) (colour >> 16 & 255) / 255.0F;
        float c2 = (float) (colour >> 8 & 255) / 255.0F;
        float c3 = (float) (colour & 255) / 255.0F;

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x + width, y, 0).color(c1, c2, c3, c).endVertex();
        bufferbuilder.pos(x, y, 0).color(c1, c2, c3, c).endVertex();
        bufferbuilder.pos(x, y + height, 0).color(c1, c2, c3, c).endVertex();
        bufferbuilder.pos(x + width, y + height, 0).color(c1, c2, c3, c).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, (y + height), 0.0D).tex((u * f), ((v + height) * f1)).endVertex();
        bufferbuilder.pos((x + width), (y + height), 0.0D).tex(((u + width) * f), ((v + height) * f1)).endVertex();
        bufferbuilder.pos((x + width), y, 0.0D).tex(((u + width) * f), (v * f1)).endVertex();
        bufferbuilder.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
        tessellator.draw();
    }

    public static void startGlScissor(double x, double y, double width, double height) {
        glPushAttrib(GL_SCISSOR_BIT);
        {
            scissorRect(x, y, width, height);
            glEnable(GL_SCISSOR_TEST);
        }
    }

    public static void endGlScissor() {
        glDisable(GL_SCISSOR_TEST);
        glPopAttrib();
    }

    public static void scissorRect(double x, double y, double width, double height) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        final double scale = sr.getScaleFactor();

        y = sr.getScaledHeight() - y;

        x *= scale;
        y *= scale;
        width *= scale;
        height *= scale;

        glScissor((int) x, (int) (y - height), (int) width, (int) height);
    }

    public static void drawStringWithShadow(String name, float x, float y, int colour, boolean shadow) {
        Wrapper.getMinecraft().fontRenderer.drawString(name, x, y, colour, shadow);
    }

    public static void drawCenteredString(String name, float x, float y, int colour, boolean shadow) {
        Wrapper.getMinecraft().fontRenderer.drawString(name, x - (Wrapper.getMinecraft().fontRenderer.getStringWidth(name) / 2f), y, colour, shadow);
    }
}
