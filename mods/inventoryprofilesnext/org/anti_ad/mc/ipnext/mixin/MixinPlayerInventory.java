package org.anti_ad.mc.ipnext.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.anti_ad.mc.ipnext.config.Debugs;
import org.anti_ad.mc.ipnext.config.LockedSlotsSettings;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;
import org.anti_ad.mc.ipnext.event.LockedSlotKeeper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public abstract class MixinPlayerInventory {
   @Shadow
   @Final
   public NonNullList items;
   private ItemStack addedStack = null;
   private boolean skipChecks = false;

   @Inject(at = @At(value = "HEAD", target = "Lnet/minecraft/world/entity/player/Inventory;getFreeSlot()I"), method = "getFreeSlot", cancellable = true)
   public void getEmptySlot(CallbackInfoReturnable var1) {
      if (!this.skipChecks
         && ((Inventory)this).player.equals(Minecraft.getInstance().player)
         && ModSettings.INSTANCE.getENABLE_LOCK_SLOTS().getValue()
         && !LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_ALLOW_PICKUP_INTO_EMPTY().getValue()
         && !Debugs.INSTANCE.getFORCE_SERVER_METHOD_FOR_LOCKED_SLOTS().getValue()) {
         for (int var2 = 0; var2 < this.items.size(); var2++) {
            if ((
                  !LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_EMPTY_HOTBAR_AS_SEMI_LOCKED().getValue()
                     || !Inventory.isHotbarSlot(var2)
                     || LockedSlotKeeper.INSTANCE.isOnlyHotbarFree()
                     || LockedSlotKeeper.INSTANCE.isIgnored(this.addedStack)
                     || !LockedSlotKeeper.INSTANCE.isHotBarSlotEmpty(var2)
               )
               && !LockSlotsHandler.INSTANCE.isSlotLocked(var2)
               && ((ItemStack)this.items.get(var2)).isEmpty()) {
               var1.setReturnValue(var2);
               return;
            }
         }

         var1.setReturnValue(-1);
      }
   }

   @Inject(
      at = @At(value = "HEAD", target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z"),
      method = "add(Lnet/minecraft/world/item/ItemStack;)Z"
   )
   public void addStackPre(ItemStack var1, CallbackInfoReturnable var2) {
      this.addedStack = var1;
   }

   @Inject(
      at = @At(value = "TAIL", target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z"),
      method = "add(Lnet/minecraft/world/item/ItemStack;)Z"
   )
   public void addStackPost(ItemStack var1, CallbackInfoReturnable var2) {
      this.addedStack = null;
   }

   @Inject(
      at = @At(value = "HEAD", target = "Lnet/minecraft/world/entity/player/Inventory;setPickedItem(Lnet/minecraft/world/item/ItemStack;)V"),
      method = "setPickedItem(Lnet/minecraft/world/item/ItemStack;)V"
   )
   public void setPickedItemPre(ItemStack var1, CallbackInfo var2) {
      this.skipChecks = true;
   }

   @Inject(
      at = @At(value = "TAIL", target = "Lnet/minecraft/world/entity/player/Inventory;setPickedItem(Lnet/minecraft/world/item/ItemStack;)V"),
      method = "setPickedItem(Lnet/minecraft/world/item/ItemStack;)V"
   )
   public void setPickedItemPost(ItemStack var1, CallbackInfo var2) {
      this.skipChecks = false;
   }
}
