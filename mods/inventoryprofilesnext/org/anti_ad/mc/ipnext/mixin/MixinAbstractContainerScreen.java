package org.anti_ad.mc.ipnext.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.LockedSlotsSettings;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class MixinAbstractContainerScreen {
   @Inject(
      at = @At(
         value = "HEAD",
         target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;slotClicked(Lnet/minecraft/world/inventory/Slot;IILnet/minecraft/world/inventory/ClickType;)V"
      ),
      method = "slotClicked(Lnet/minecraft/world/inventory/Slot;IILnet/minecraft/world/inventory/ClickType;)V",
      cancellable = true
   )
   protected void slotClicked(Slot var1, int var2, int var3, ClickType var4, CallbackInfo var5) {
      Log.INSTANCE.trace("onMouseClick for " + var2);
      LockSlotsHandler.INSTANCE.setLastMouseClickSlot(var1);
      if (var1 != null
         && LockedSlotsSettings.INSTANCE.getLOCK_SLOTS_DISABLE_USER_INTERACTION().getValue()
         && (LockSlotsHandler.INSTANCE.isMappedSlotLocked(var1) || var4 == ClickType.SWAP && LockSlotsHandler.INSTANCE.isSlotLocked(var3))) {
         Log.INSTANCE.trace("cancel for " + var2);
         var5.cancel();
      }
   }

   @Inject(
      at = @At(
         value = "TAIL",
         target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;slotClicked(Lnet/minecraft/world/inventory/Slot;IILnet/minecraft/world/inventory/ClickType;)V"
      ),
      method = "slotClicked(Lnet/minecraft/world/inventory/Slot;IILnet/minecraft/world/inventory/ClickType;)V"
   )
   protected void afterSlotClicked(Slot var1, int var2, int var3, ClickType var4, CallbackInfo var5) {
      LockSlotsHandler.INSTANCE.setLastMouseClickSlot(null);
   }
}
