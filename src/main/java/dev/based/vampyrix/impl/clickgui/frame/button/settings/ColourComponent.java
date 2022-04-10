package dev.based.vampyrix.impl.clickgui.frame.button.settings;

import dev.based.vampyrix.api.clickgui.button.SettingComponent;
import dev.based.vampyrix.api.module.setting.Setting;
import dev.based.vampyrix.api.util.maths.MathsUtil;
import dev.based.vampyrix.api.util.render.ColourUtil;
import dev.based.vampyrix.api.util.render.GuiUtil;
import dev.based.vampyrix.api.util.render.RenderUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glScalef;

public class ColourComponent extends SettingComponent<Color> {

    private boolean open;

    private final List<SettingComponent<?>> pickerComponents = new ArrayList<>();
    private final Setting<Number> hueSetting = new Setting<>("Hue", 0f, 0f, 360f, 1f);
    private final Setting<Number> alphaSetting = new Setting<>("Alpha", 0f, 0f, 255f, 1f);

    private boolean draggingPicker;
    private Color finalColour;

    public ColourComponent(float x, float y, float width, float height, Setting<Color> setting) {
        super(x, y, width, height, setting);

        float[] hsbValues = Color.RGBtoHSB(setting.getValue().getRed(), setting.getValue().getGreen(), setting.getValue().getBlue(), null);

        hueSetting.setValue(hsbValues[0]);
        alphaSetting.setValue(setting.getValue().getAlpha());

        pickerComponents.add(new SliderComponent(x + 2, y + height, width - 2, height, hueSetting));
        pickerComponents.add(new SliderComponent(x + 2, y + height, width - 2, height, alphaSetting));

        finalColour = setting.getValue();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(x, y, width, height, isWithin(mouseX, mouseY) ? new Color(23, 23, 23, 200).getRGB() : 0x90000000);

        float scaleFactor = 1 / 0.75f;

        glScalef(0.75f, 0.75f, 0.75f);

        drawString(getSetting().getName(), (x + 4) * scaleFactor, (y + 4.5f) * scaleFactor, -1, true);

        glScalef(scaleFactor, scaleFactor, scaleFactor);

        RenderUtil.drawRect(x + width - 13, y + 2, 10, 10, getSetting().getValue().getRGB());

        if (open) {
            RenderUtil.drawRect(x, y + height + (pickerComponents.size() * height), width, 74, 0x90000000);
            RenderUtil.drawRect(x, y + height, 2, getTotalHeight() - height, ColourUtil.getClientColour().getRGB());

            float offset = y + height;
            for (SettingComponent<?> component : pickerComponents) {
                component.setPos(x + 2, offset);
                offset += component.getTotalHeight();
            }

            pickerComponents.forEach(component -> component.render(mouseX, mouseY, partialTicks));

            float hue = this.hueSetting.getValue().floatValue();

            // Picker attributes
            Color colour = Color.getHSBColor(hueSetting.getValue().floatValue() / 360, 1, 1);

            float pickerX = x + 4;
            float pickerY = y + height + (pickerComponents.size() * height) + 2;
            float pickerWidth = width - 6;
            float pickerHeight = 70;

            // GL shit pt 1
            GlStateManager.pushMatrix();
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.shadeModel(7425);

            BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

            // Add positions
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos(pickerX + pickerWidth, pickerY, 0).color(colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getAlpha()).endVertex();
            bufferbuilder.pos(pickerX, pickerY, 0).color(255, 255, 255, 255).endVertex();
            bufferbuilder.pos(pickerX, pickerY + pickerHeight, 0).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(pickerX + pickerWidth, pickerY + pickerHeight, 0).color(0, 0, 0, 255).endVertex();

            // Draw rect
            Tessellator.getInstance().draw();

            // GL shit pt 2
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            GlStateManager.popMatrix();

            // awful thing to check if we are dragging the hue slider
            for (SettingComponent<?> settingComponent : pickerComponents) {
                if (settingComponent.getSetting() == this.hueSetting && ((SliderComponent) settingComponent).isDragging()) {
                    hue = hueSetting.getValue().floatValue();
                    float[] hsb2 = Color.RGBtoHSB(finalColour.getRed(), finalColour.getGreen(), finalColour.getBlue(), null);
                    finalColour = new Color(Color.HSBtoRGB(hue / 360, hsb2[1], hsb2[2]));
                }

                // If we are dragging a slider, we don't want to pick a colour
                if (settingComponent instanceof SliderComponent && ((SliderComponent) settingComponent).isDragging()) {
                    draggingPicker = false;
                }
            }

            if (draggingPicker) {
                float saturation;
                float brightness;

                float satDiff = Math.min(pickerWidth, Math.max(0, mouseX - pickerX));

                if (satDiff == 0) {
                    saturation = 0;
                } else {
                    saturation = (float) MathsUtil.roundDouble(((satDiff / pickerWidth) * 100), 0);
                }

                float brightDiff = Math.min(pickerHeight, Math.max(0, pickerY + pickerHeight - mouseY));

                if (brightDiff == 0) {
                    brightness = 0;
                } else {
                    brightness = (float) MathsUtil.roundDouble(((brightDiff / pickerHeight) * 100), 0);
                }

                finalColour = new Color(Color.HSBtoRGB(hue / 360, saturation / 100, brightness / 100));
            }

            // Get final HSB colours
            float[] finHSB = Color.RGBtoHSB(finalColour.getRed(), finalColour.getGreen(), finalColour.getBlue(), null);

            // Picker X and Y
            float pickerIndicatorX = pickerX + (finHSB[1]) * pickerWidth;
            float pickerIndicatorY = pickerY + (1 - (finHSB[2])) * pickerHeight;

            // Draw picker indicator (is that the right word? idfk)
            RenderUtil.drawRect(pickerIndicatorX - 1.5f, pickerIndicatorY - 1.5f, 3, 3, -1);
            RenderUtil.drawRect(pickerIndicatorX - 1, pickerIndicatorY - 1, 2, 2, finalColour.getRGB());
        }

        getSetting().setValue(ColourUtil.integrateAlpha(finalColour, alphaSetting.getValue().floatValue()));

        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isWithin(mouseX, mouseY)) {
            open = !open;
        }

        if (open) {
            pickerComponents.forEach(component -> component.mouseClicked(mouseX, mouseY, mouseButton));

            if (GuiUtil.isMouseOver(x + 3, y + height + (pickerComponents.size() * height) + 2, 93, 70, mouseX, mouseY)) {
                draggingPicker = true;
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        draggingPicker = false;
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) {
    }

    @Override
    public float getTotalHeight() {
        return open ? (pickerComponents.size() * height) + 88 : super.getTotalHeight();
    }
}
