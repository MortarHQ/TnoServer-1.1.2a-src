package com.blackgear.platform.core.mixin.neoforge;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Map;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WeatheringCopper.class)
public interface WeatheringCopperMixin {
   @Inject(method = "lambda$static$0()Lcom/google/common/collect/BiMap;", at = @At("RETURN"), cancellable = true)
   private static void createOxidationLevelIncreasesMap(CallbackInfoReturnable<BiMap<Block, Block>> cir) {
      cir.setReturnValue(HashBiMap.create((Map)cir.getReturnValue()));
   }
}
