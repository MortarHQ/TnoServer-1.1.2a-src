package com.tensura_tno.mixin.client;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "io.github.manasmods.tensura.util.client.ScreenHelper", remap = false)
public class ScreenHelperOpenContainerFixMixin {
   @Redirect(
      method = "openScreen(Lnet/minecraft/client/gui/screens/Screen;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasContainerOpen()Z"),
      remap = false
   )
   private static boolean tensuraTno$bypassContainerOpenCheck(LocalPlayer player) {
      return false;
   }
}
