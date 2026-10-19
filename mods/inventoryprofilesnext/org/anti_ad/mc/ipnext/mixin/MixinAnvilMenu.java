package org.anti_ad.mc.ipnext.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.event.AnvilHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public class MixinAnvilMenu {
   @Inject(
      at = @At("HEAD"),
      method = "Lnet/minecraft/world/inventory/AnvilMenu;onTake(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V",
      cancellable = true
   )
   public void onTakeOutputPre(Player var1, ItemStack var2, CallbackInfo var3) {
      if (Vanilla.INSTANCE.mc().isSameThread() && AnvilHandler.INSTANCE.onTakeOutPre((AnvilMenu)this)) {
         var3.cancel();
      }
   }

   @Inject(
      at = @At("TAIL"),
      method = "Lnet/minecraft/world/inventory/AnvilMenu;onTake(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V"
   )
   public void onTakeOutputPost(Player var1, ItemStack var2, CallbackInfo var3) {
      if (Vanilla.INSTANCE.mc().isSameThread()) {
         AnvilHandler.INSTANCE.onTakeOutPost((AnvilMenu)this);
      }
   }
}
