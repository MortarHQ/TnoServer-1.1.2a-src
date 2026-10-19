package com.tensura_tno.mixin.client;

import com.tensura_tno.network.RimuruModePackets;
import io.github.manasmods.tensura.item.misc.ResetScrollItem;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResetScrollItem.class)
public abstract class ResetScrollCharacterResetMarkerMixin {
   @Inject(method = "resetEverything", at = @At("HEAD"), require = 0, remap = false)
   private static void tensuraTno$notifyClientCharacterReset(ServerPlayer player, CallbackInfo ci) {
      try {
         RimuruModePackets.notifyCharacterReset(player);
      } catch (Throwable var3) {
      }
   }
}
