package com.yungnickyoung.minecraft.yungscavebiomes.mixin.frosted_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.Aquifer.FluidStatus;
import net.minecraft.world.level.levelgen.Aquifer.NoiseBasedAquifer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseBasedAquifer.class)
public abstract class AquiferMixin implements Aquifer {
   @Shadow
   @Final
   private NoiseChunk noiseChunk;

   @Inject(method = "computeFluid", at = @At("RETURN"), cancellable = true)
   private void yungscavebiomes_noLavaAquifersInFrostedCaves(int x, int y, int z, CallbackInfoReturnable<FluidStatus> cir) {
      FluidStatus original = (FluidStatus)cir.getReturnValue();
      NoiseSamplerBiomeHolder holder = (NoiseSamplerBiomeHolder)this.noiseChunk;
      BiomeSource source = holder.getBiomeSource();
      Registry<Biome> biomes = holder.getBiomeRegistry();
      Sampler sampler = holder.getClimateSampler();
      if (biomes != null) {
         Holder<Biome> biome = source.getNoiseBiome(QuartPos.fromBlock(x), QuartPos.fromBlock(y), QuartPos.fromBlock(z), sampler);
         Optional<ResourceKey<Biome>> biomeKey = biomes.getResourceKey((Biome)biome.value());
         if (biomeKey.isPresent() && biomeKey.get() == BiomeModule.FROSTED_CAVES) {
            int originalLevel = original.fluidLevel;
            cir.setReturnValue(new FluidStatus(originalLevel, Blocks.WATER.defaultBlockState()));
         }
      }
   }
}
