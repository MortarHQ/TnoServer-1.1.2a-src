package org.crypticdev.stextras.mixin.tensura.menu;

import io.github.manasmods.tensura.menu.WoodcutterMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.stextras.event.MenuOutputTakeTracker;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WoodcutterMenu.class)
public abstract class WoodcutterMenuMixin {
   @Unique
   private boolean stextras$suppressOutputSlotEvent;

   @Inject(method = "quickMoveStack(Lnet/minecraft/world/entity/player/Player;I)Lnet/minecraft/world/item/ItemStack;", at = @At("HEAD"))
   private void stextras$beforeWoodcutterQuickMove(Player player, int index, CallbackInfoReturnable<ItemStack> cir) {
      this.stextras$suppressOutputSlotEvent = index == 1;
      if (this.stextras$suppressOutputSlotEvent) {
         MenuOutputTakeTracker.beginSuppressingSlotEvents();
      }
   }

   @Inject(method = "quickMoveStack(Lnet/minecraft/world/entity/player/Player;I)Lnet/minecraft/world/item/ItemStack;", at = @At("TAIL"))
   private void stextras$afterWoodcutterQuickMove(Player player, int index, CallbackInfoReturnable<ItemStack> cir) {
      if (this.stextras$suppressOutputSlotEvent) {
         MenuOutputTakeTracker.endSuppressingSlotEvents();
         this.stextras$suppressOutputSlotEvent = false;
      }

      ItemStack moved = (ItemStack)cir.getReturnValue();
      if (index == 1 && player instanceof ServerPlayer sp && moved != null && !moved.isEmpty()) {
         STPlayerEvents.fireWoodcut(sp, moved.copy());
      }
   }
}
