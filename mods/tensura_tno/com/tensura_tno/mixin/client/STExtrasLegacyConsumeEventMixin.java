package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.stextras.STExtrasConsumptionCompat;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "org.crypticdev.stextras.event.STPlayerEvents", remap = false)
public class STExtrasLegacyConsumeEventMixin {
   @Inject(
      method = "fireConsume(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;)V",
      at = @At("HEAD"),
      cancellable = true,
      require = 0,
      remap = false
   )
   private static void tensuraTno$suppressReturnStackEvent(ServerPlayer player, ItemStack stack, CallbackInfo ci) {
      if (STExtrasConsumptionCompat.isReplacing(player)) {
         ci.cancel();
      }
   }
}
