package org.anti_ad.mc.ipnext.mixin;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.item.ItemStack;
import org.anti_ad.mc.ipnext.event.LockedSlotKeeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiPlayerGameMode.class)
public class MixinPlayerController {
   @Inject(at = @At("HEAD"), method = "handleCreativeModeItemAdd(Lnet/minecraft/world/item/ItemStack;I)V")
   public void clickCreativeStack(ItemStack var1, int var2, CallbackInfo var3) {
      LockedSlotKeeper.INSTANCE.addIgnoredHotbarSlotId(var2);
   }

   @Inject(at = @At("HEAD"), method = "handlePickItem(I)V")
   public void pickItem(int var1, CallbackInfo var2) {
      LockedSlotKeeper.INSTANCE.ignoredSelectedHotbarSlot(var1);
   }
}
