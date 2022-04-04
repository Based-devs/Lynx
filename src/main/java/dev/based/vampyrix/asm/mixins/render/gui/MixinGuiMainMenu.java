package dev.based.vampyrix.asm.mixins.render.gui;

import dev.based.vampyrix.api.util.render.ColourUtil;
import dev.based.vampyrix.Vampyrix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu {

    @Inject(method = "drawScreen", at = @At("TAIL"))
    public void onDrawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo info) {
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Vampyrix " + TextFormatting.GRAY + Vampyrix.VERSION, 2, 2, ColourUtil.getClientColour().getRGB());
    }

}
