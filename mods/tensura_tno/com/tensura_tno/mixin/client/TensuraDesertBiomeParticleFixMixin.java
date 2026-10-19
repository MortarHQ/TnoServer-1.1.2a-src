package com.tensura_tno.mixin.client;

import io.github.manasmods.tensura.world.biome.overworld.BarrenLandBiome;
import io.github.manasmods.tensura.world.biome.overworld.DesertOfDeathBiome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin({DesertOfDeathBiome.class, BarrenLandBiome.class})
public class TensuraDesertBiomeParticleFixMixin {
   @ModifyArg(
      method = "create",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/biome/AmbientParticleSettings;<init>(Lnet/minecraft/core/particles/ParticleOptions;F)V",
         remap = false
      ),
      index = 1,
      remap = false
   )
   private static float tensuraTno$fixBiomeParticleProbability(float probability) {
      return 0.002F;
   }
}
