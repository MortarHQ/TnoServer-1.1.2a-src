package org.anti_ad.mc.ipnext.mixin;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.anti_ad.mc.ipnext.event.ContinuousCraftingHandler;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;
import org.anti_ad.mc.ipnext.event.LockedSlotKeeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiPlayerGameMode.class)
public class MixinScreenHandler {
   @Inject(
      at = @At(
         value = "HEAD",
         target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;handleInventoryMouseClick(IIILnet/minecraft/world/inventory/ClickType;Lnet/minecraft/world/entity/player/Player;)V"
      ),
      method = "handleInventoryMouseClick",
      cancellable = true
   )
   public void internalOnSlotClickBegin(int var1, int var2, int var3, ClickType var4, Player var5, CallbackInfo var6) {
      boolean var7 = var4 == ClickType.QUICK_MOVE;
      boolean var8 = var4 == ClickType.THROW;
      if (!LockedSlotKeeper.INSTANCE.getProcessingLockedPickups() && (var7 || var8) && !LockSlotsHandler.INSTANCE.isQMoveActionAllowed(var2)) {
         var6.cancel();
      }

      if (GuiSettings.INSTANCE.getCONTINUOUS_CRAFTING_SAVED_VALUE().getValue()) {
         ContinuousCraftingHandler.INSTANCE.setProcessingClick(true);
      }
   }

   @Inject(
      at = @At(
         value = "TAIL",
         target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;handleInventoryMouseClick(IIILnet/minecraft/world/inventory/ClickType;Lnet/minecraft/world/entity/player/Player;)V"
      ),
      method = "handleInventoryMouseClick"
   )
   public void postInternalOnSlotClickBegin(int var1, int var2, int var3, ClickType var4, Player var5, CallbackInfo var6) {
      if (GuiSettings.INSTANCE.getCONTINUOUS_CRAFTING_SAVED_VALUE().getValue()) {
         ContinuousCraftingHandler.INSTANCE.setProcessingClick(false);
      }
   }
}
