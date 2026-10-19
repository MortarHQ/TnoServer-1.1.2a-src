package org.crypticdev.stextras.mixin.world.inventory;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemCombinerMenu.class)
public abstract class ItemCombinerMenuMixin {
   @Inject(method = "quickMoveStack(Lnet/minecraft/world/entity/player/Player;I)Lnet/minecraft/world/item/ItemStack;", at = @At("TAIL"))
   private void stextras$smithQuick(Player player, int index, CallbackInfoReturnable<ItemStack> cir) {
      if (player instanceof ServerPlayer sp) {
         ItemCombinerMenu self = (ItemCombinerMenu)this;
         if (self instanceof SmithingMenu) {
            if (index == self.getResultSlot()) {
               ItemStack moved = (ItemStack)cir.getReturnValue();
               if (moved != null && !moved.isEmpty()) {
                  STPlayerEvents.fireSmith(sp, moved.copy());
               }
            }
         }
      }
   }
}
