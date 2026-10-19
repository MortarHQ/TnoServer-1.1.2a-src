package org.crypticdev.stextras.mixin.tensura.menu;

import io.github.manasmods.tensura.menu.SmithingBenchMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmithingBenchMenu.class)
abstract class SmithingBenchMenuMixin {
   @Inject(method = "quickMoveStack(Lnet/minecraft/world/entity/player/Player;I)Lnet/minecraft/world/item/ItemStack;", at = @At("TAIL"))
   private void stextras$afterQuickMove(Player player, int index, CallbackInfoReturnable<ItemStack> cir) {
      if (player instanceof ServerPlayer sp) {
         if (index == 36) {
            ItemStack moved = (ItemStack)cir.getReturnValue();
            if (moved != null && !moved.isEmpty()) {
               STPlayerEvents.fireTRSmith(sp, moved.copy());
            }
         }
      }
   }
}
