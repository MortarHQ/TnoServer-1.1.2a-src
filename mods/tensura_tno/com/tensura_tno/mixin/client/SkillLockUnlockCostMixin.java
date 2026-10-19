package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.stextras.SkillUnlockEpCost;
import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "org.crypticdev.stextras.storage.STExtarsStorage$Player", remap = false)
public class SkillLockUnlockCostMixin {
   @Inject(method = "getRequiredSoulGradeToUnlockSkill()I", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
   private static void tno$removeSoulGradeCost(CallbackInfoReturnable<Integer> cir) {
      cir.setReturnValue(0);
   }

   @Inject(method = "getRequiredEpToUnlockSkill()D", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
   private static void tno$removeFixedEpRequirement(CallbackInfoReturnable<Double> cir) {
      cir.setReturnValue(0.0);
   }

   @Inject(method = "getRequiredMagiculeToUnlockSkill()D", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
   private static void tno$removeDerivedMagiculeCost(CallbackInfoReturnable<Double> cir) {
      cir.setReturnValue(0.0);
   }

   @Inject(
      method = "canUnlockSkill(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/resources/ResourceLocation;)Z",
      at = @At("RETURN"),
      cancellable = true,
      require = 0,
      remap = false
   )
   private static void tno$requireEnoughCurrentEp(ServerPlayer player, ResourceLocation skillId, CallbackInfoReturnable<Boolean> cir) {
      if (cir.getReturnValueZ() && !SkillUnlockEpCost.canAfford(player, TensuraStorages.getExistenceFrom(player))) {
         cir.setReturnValue(false);
      }
   }

   @Inject(
      method = "unlockSkill(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/resources/ResourceLocation;)Z",
      at = @At("HEAD"),
      cancellable = true,
      require = 0,
      remap = false
   )
   private static void tno$rejectUnlockWhenCurrentEpIsInsufficient(ServerPlayer player, ResourceLocation skillId, CallbackInfoReturnable<Boolean> cir) {
      if (!SkillUnlockEpCost.canAfford(player, TensuraStorages.getExistenceFrom(player))) {
         cir.setReturnValue(false);
      }
   }

   @Inject(
      method = "unlockSkill(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/resources/ResourceLocation;)Z",
      at = @At("RETURN"),
      require = 0,
      remap = false
   )
   private static void tno$consumeOnePercentEp(ServerPlayer player, ResourceLocation skillId, CallbackInfoReturnable<Boolean> cir) {
      if (cir.getReturnValueZ()) {
         SkillUnlockEpCost.consume(player);
      }
   }
}
