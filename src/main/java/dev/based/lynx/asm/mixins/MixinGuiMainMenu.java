package dev.based.lynx.asm.mixins;

import dev.based.lynx.Lynx;
import dev.based.lynx.api.util.render.ColourUtil;
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
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Lynx.NAME + TextFormatting.GRAY + Lynx.VERSION, 2, 2, ColourUtil.getClientColour().getRGB());
    }
}
