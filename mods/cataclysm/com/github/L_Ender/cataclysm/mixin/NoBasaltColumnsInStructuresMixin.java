package com.github.L_Ender.cataclysm.mixin;

import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.MixinUtils;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.BasaltColumnsFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BasaltColumnsFeature.class)
public class NoBasaltColumnsInStructuresMixin {
   @Inject(
      method = "canPlaceAt(Lnet/minecraft/world/level/LevelAccessor;ILnet/minecraft/core/BlockPos$MutableBlockPos;)Z",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void cataclysm_noBasaltColumnsInStructures(
      LevelAccessor levelAccessor, int seaLevel, MutableBlockPos mutableBlockPos, CallbackInfoReturnable<Boolean> cir
   ) {
      if (levelAccessor instanceof WorldGenRegion worldGenRegion) {
         if (MixinUtils.isPositionInTaggedStructure(worldGenRegion, mutableBlockPos, ModTag.BLOCKED_BASALT)) {
            cir.setReturnValue(false);
         }
      }
   }
}
