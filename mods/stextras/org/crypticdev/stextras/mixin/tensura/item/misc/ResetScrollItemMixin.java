package org.crypticdev.stextras.mixin.tensura.item.misc;

import io.github.manasmods.tensura.item.misc.ResetScrollItem;
import io.github.manasmods.tensura.item.misc.ResetScrollItem.ResetType;
import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.crypticdev.stextras.utils.PrestigeUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ResetScrollItem.class)
public abstract class ResetScrollItemMixin {
   @Shadow(remap = false)
   public abstract ResetType getResetType();

   @Inject(
      method = "releaseUsing(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)V",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/item/misc/ResetScrollItem;isFullReset(Lnet/minecraft/server/level/ServerPlayer;)Z")
   )
   private void stextras$OnCharacterReset(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged, CallbackInfo ci) {
      if (this.getResetType() == ResetType.RESET_ALL && livingEntity instanceof ServerPlayer player) {
         PrestigeUtils.doPrestige(player);
      }
   }

   @Inject(method = "isFullReset(Lnet/minecraft/server/level/ServerPlayer;)Z", at = @At("HEAD"), cancellable = true)
   private static void stextras$isFullReset(ServerPlayer player, CallbackInfoReturnable<Boolean> ci) {
      ci.setReturnValue(PrestigeUtils.canPrestige(player));
   }

   @Inject(method = "resetEverything(Lnet/minecraft/server/level/ServerPlayer;)V", at = @At("TAIL"))
   private static void stextras$resetEverything(ServerPlayer player, CallbackInfo ci) {
      PrestigeUtils.resetPlayer(player);
      STPlayerEvents.firePrestigeLevel(player, TensuraStorages.getPlayerDataFrom(player).getResetCounter());
   }
}
