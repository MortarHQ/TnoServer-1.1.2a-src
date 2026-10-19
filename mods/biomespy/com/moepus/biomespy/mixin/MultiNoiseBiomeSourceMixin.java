package com.moepus.biomespy.mixin;

import com.moepus.biomespy.biome.BiomeFinder;
import com.mojang.datafixers.util.Pair;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.Climate.Sampler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MultiNoiseBiomeSource.class)
public abstract class MultiNoiseBiomeSourceMixin extends BiomeSource {
   public Pair<BlockPos, Holder<Biome>> findClosestBiome3d(
      @NotNull BlockPos pos, int radius, int horizontalStep, int verticalStep, Predicate<Holder<Biome>> biomePredicate, Sampler sampler, LevelReader level
   ) {
      return BiomeFinder.findClosestBiome3d(pos, radius, horizontalStep, verticalStep, biomePredicate, sampler, level, (MultiNoiseBiomeSource)this);
   }
}
