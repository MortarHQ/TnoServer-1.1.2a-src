package org.anti_ad.mc.ipnext.mixin;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import org.anti_ad.mc.ipnext.event.CuttersDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiPlayerGameMode.class)
public class MixinMultiPlayerGameMode {
   @Inject(
      at = @At("HEAD"),
      method = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;handleInventoryMouseClick(IIILnet/minecraft/world/inventory/ClickType;Lnet/minecraft/world/entity/player/Player;)V",
      cancellable = true
   )
   public void clickSlotPre(int var1, int var2, int var3, ClickType var4, Player var5, CallbackInfo var6) {
      if (var2 == 1 && (CuttersDispatcher.INSTANCE.isAnyStillCrafting() || CuttersDispatcher.INSTANCE.isAnyRefillTick())) {
         var6.cancel();
      }
   }

   @Inject(
      at = @At("TAIL"),
      method = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;handleInventoryMouseClick(IIILnet/minecraft/world/inventory/ClickType;Lnet/minecraft/world/entity/player/Player;)V"
   )
   public void clickSlot(int var1, int var2, int var3, ClickType var4, Player var5, CallbackInfo var6) {
      if (var2 == 1 && CuttersDispatcher.INSTANCE.isAnyOldScreen()) {
         CuttersDispatcher.INSTANCE.onCrafted();
      }
   }
}
