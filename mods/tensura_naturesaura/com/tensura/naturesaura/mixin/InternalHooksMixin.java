package com.tensura.naturesaura.mixin;

import com.tensura.naturesaura.integration.BiomeAuraResolver;
import de.ellpeck.naturesaura.InternalHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = InternalHooks.class, remap = false)
abstract class InternalHooksMixin {
   @Inject(method = "getAuraInArea(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;I)I", at = @At("RETURN"), cancellable = true, require = 1)
   private void tensuraNaturesAura$adjustAuraInArea(Level level, BlockPos pos, int radius, CallbackInfoReturnable<Integer> callback) {
      callback.setReturnValue(BiomeAuraResolver.adjustAura(level, pos, (Integer)callback.getReturnValue()));
   }

   @Inject(
      method = "getAuraAndSpotAmountInArea(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;I)Lorg/apache/commons/lang3/tuple/Pair;",
      at = @At("RETURN"),
      cancellable = true,
      require = 1
   )
   private void tensuraNaturesAura$adjustAuraAndSpotAmountInArea(Level level, BlockPos pos, int radius, CallbackInfoReturnable<Pair<Integer, Integer>> callback) {
      Pair<Integer, Integer> result = (Pair<Integer, Integer>)callback.getReturnValue();
      callback.setReturnValue(Pair.of(BiomeAuraResolver.adjustAura(level, pos, (Integer)result.getLeft()), (Integer)result.getRight()));
   }

   @Inject(
      method = "triangulateAuraInArea(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;I)I",
      at = @At("RETURN"),
      cancellable = true,
      require = 1
   )
   private void tensuraNaturesAura$adjustTriangulatedAuraInArea(Level level, BlockPos pos, int radius, CallbackInfoReturnable<Integer> callback) {
      callback.setReturnValue(BiomeAuraResolver.adjustAura(level, pos, (Integer)callback.getReturnValue()));
   }
}
