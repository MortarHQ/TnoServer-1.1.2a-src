package org.crypticdev.stextras.mixin.world.inventory;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FurnaceResultSlot.class)
public abstract class FurnaceResultSlotMixin {
   @Shadow
   @Final
   private Player player;

   @Inject(method = "onQuickCraft(Lnet/minecraft/world/item/ItemStack;I)V", at = @At("TAIL"))
   private void stextras$cookQuick(ItemStack stack, int produced, CallbackInfo ci) {
      if (this.player instanceof ServerPlayer sp && stack != null && !stack.isEmpty()) {
         STPlayerEvents.fireCook(sp, stack.copy());
      }
   }

   @Inject(method = "onTake(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"))
   private void stextras$cookTake(Player p, ItemStack stack, CallbackInfo ci) {
      if (p instanceof ServerPlayer sp && stack != null && !stack.isEmpty()) {
         STPlayerEvents.fireCook(sp, stack.copy());
      }
   }
}
