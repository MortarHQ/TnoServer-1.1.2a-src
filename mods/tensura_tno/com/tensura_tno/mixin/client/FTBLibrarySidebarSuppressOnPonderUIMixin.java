package com.tensura_tno.mixin.client;

import com.tensura_tno.util.PondererScreenIds;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "dev.ftb.mods.ftblibrary.FTBLibraryClient", remap = false)
public class FTBLibrarySidebarSuppressOnPonderUIMixin {
   @Inject(method = "areButtonsVisible(Lnet/minecraft/client/gui/screens/Screen;)Z", at = @At("RETURN"), cancellable = true, remap = false, require = 0)
   private static void tensuraTno$hideOnPonderScreens(Screen gui, CallbackInfoReturnable<Boolean> cir) {
      if (Boolean.TRUE.equals(cir.getReturnValue())) {
         if (PondererScreenIds.isPonderUI(gui)) {
            cir.setReturnValue(false);
         }
      }
   }
}
