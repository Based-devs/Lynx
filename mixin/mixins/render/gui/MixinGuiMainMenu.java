package com.based.lynx.mixin.mixins.render.gui;

import com.based.lynx.ui.menu.LynxMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen {

    @Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    public void onInit(CallbackInfo ci) {
        mc.displayGuiScreen(new LynxMenu());

        ci.cancel();
    }

}
