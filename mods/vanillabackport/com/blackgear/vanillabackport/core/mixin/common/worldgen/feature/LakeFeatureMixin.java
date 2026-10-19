package com.blackgear.vanillabackport.core.mixin.common.worldgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LakeFeature.class)
public class LakeFeatureMixin {
   @ModifyVariable(
      method = "place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z",
      at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/core/BlockPos;below(I)Lnet/minecraft/core/BlockPos;"),
      ordinal = 0
   )
   private BlockPos vb$centerLakeOrigin(BlockPos blockPos) {
      return blockPos.offset(-8, 0, -8);
   }
}
