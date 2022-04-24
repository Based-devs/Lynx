package com.based.lynx.ui.click.frame.component.setting;

import com.based.lynx.setting.Setting;
import com.based.lynx.ui.click.frame.component.module.ModuleComponent;
import com.based.lynx.util.*;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glScalef;

public class ColourComponent extends SettingComponent<Color> {

    private final Animation animation = new Animation(200, false);
    private boolean open;

    private final List<SettingComponent<?>> pickerComponents = new ArrayList<>();
    private final Setting<Number> hueSetting = new Setting<>("Hue", 0f, 0f, 360f, 1f);
    private final Setting<Number> alphaSetting = new Setting<>("Alpha", 0f, 0f, 255f, 1f);
    private boolean draggingPicker;
    private Color finalColour;

    public ColourComponent(Setting<Color> setting, float x, float y, float width, float height, ModuleComponent moduleComponent) {
        super(setting, x, y, width, height, moduleComponent);

        float[] hsbValues = Color.RGBtoHSB(setting.getValue().getRed(), setting.getValue().getGreen(), setting.getValue().getBlue(), null);

        this.hueSetting.setValue((int) (hsbValues[0] * 360));
        this.alphaSetting.setValue(setting.getValue().getAlpha());

        pickerComponents.add(new SliderComponent(hueSetting, x + 2, y + 61, width - 2, 13, moduleComponent));
        pickerComponents.add(new SliderComponent(alphaSetting, x + 2, y + 74, width - 2, 13, moduleComponent));

        this.finalColour = setting.getValue();
    }

    @Override
    public void renderComponent(int mouseX, int mouseY) {
        RenderUtil.drawRect(getX(), getY(), getWidth(), getHeight(), isHovered(mouseX, mouseY) ? new Color(30, 30, 35).getRGB() : new Color(25, 25, 30).getRGB());

        glScalef(0.75f, 0.75f, 0.75f);
        float scaleFactor = 1 / 0.75f;

        FontUtil.drawStringWithShadow(getSetting().getName(), (getX() + 5 + (2 * animation.getAnimationFactor())) * scaleFactor, (getY() + 4) * scaleFactor, -1, true);

        glScalef(scaleFactor, scaleFactor, scaleFactor);

        if (!Mouse.isButtonDown(0)) {
            this.draggingPicker = false;
        }

        if (animation.getAnimationFactor() > 0) {
            float offset = getY() + 75;
            for (SettingComponent<?> component : this.pickerComponents) {
                component.setX(getX() + 2);
                component.setY(offset);
                offset += component.getAbsoluteHeight();
            }

            this.pickerComponents.forEach(component -> component.renderComponent(mouseX, mouseY));

            float hue = this.hueSetting.getValue().floatValue();

            // Picker attributes
            Color colour = Color.getHSBColor(hue / 360, 1, 1);

            float pickerX = getX() + 4;
            float pickerY = getY() + 14;
            float pickerWidth = 85;
            float pickerHeight = 60;

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
            for (SettingComponent<?> settingComponent : this.pickerComponents) {
                if (settingComponent.getSetting() == this.hueSetting && ((SliderComponent) settingComponent).isDragging()) {
                    hue = this.hueSetting.getValue().floatValue();
                    float[] hsb2 = Color.RGBtoHSB(this.finalColour.getRed(), this.finalColour.getGreen(), this.finalColour.getBlue(), null);
                    this.finalColour = new Color(Color.HSBtoRGB(hue / 360, hsb2[1], hsb2[2]));
                }

                // If we are dragging a slider, we don't want to pick a colour
                if (settingComponent instanceof SliderComponent && ((SliderComponent) settingComponent).isDragging()) {
                    this.draggingPicker = false;
                }
            }

            if (this.draggingPicker) {
                float saturation, brightness, satDiff = Math.min(pickerWidth, Math.max(0, mouseX - pickerX));

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

                this.finalColour = new Color(Color.HSBtoRGB(hue / 360, saturation / 100, brightness / 100));
            }

            // Get final HSB colours
            float[] finHSB = Color.RGBtoHSB(this.finalColour.getRed(), this.finalColour.getGreen(), this.finalColour.getBlue(), null);

            // Picker X and Y
            float pickerIndicatorX = pickerX + (finHSB[1]) * pickerWidth;
            float pickerIndicatorY = pickerY + (1 - (finHSB[2])) * pickerHeight;

            // Draw picker indicator (is that the right word? idfk)
            RenderUtil.drawRect(pickerIndicatorX - 1.5f, pickerIndicatorY - 1.5f, 3, 3, -1);
            RenderUtil.drawRect(pickerIndicatorX - 1, pickerIndicatorY - 1, 2, 2, this.finalColour.getRGB());
        }

        this.getSetting().setValue(ColourUtil.integrateAlpha(this.finalColour, this.alphaSetting.getValue().floatValue()));

        super.renderComponent(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 1) {
            open = !open;
            animation.setState(open);
        }

        if (this.open) {
            this.pickerComponents.forEach(component -> component.mouseClicked(mouseX, mouseY, mouseButton));

            if (mouseX >= getX() + 3 && mouseX <= getX() + 91 && mouseY >= getY() + 14 && mouseY <= getY() + 74) {
                this.draggingPicker = true;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.draggingPicker = false;

        super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public float getHeight() {
        return 13 + (91 * animation.getAnimationFactor());
    }
}
