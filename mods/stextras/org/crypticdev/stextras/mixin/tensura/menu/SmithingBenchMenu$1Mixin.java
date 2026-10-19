package org.crypticdev.stextras.mixin.tensura.menu;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "io.github.manasmods.tensura.menu.SmithingBenchMenu$1")
abstract class SmithingBenchMenu$1Mixin {
   @Inject(method = "onTake(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"))
   private void stextras$trSmith_onTake(Player player, ItemStack stack, CallbackInfo ci) {
      if (player instanceof ServerPlayer sp && stack != null && !stack.isEmpty()) {
         STPlayerEvents.fireTRSmith(sp, stack.copy());
      }
   }
}
