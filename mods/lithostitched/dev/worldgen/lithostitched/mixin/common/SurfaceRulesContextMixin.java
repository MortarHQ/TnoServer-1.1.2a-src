package dev.worldgen.lithostitched.mixin.common;

import dev.worldgen.lithostitched.duck.ContextAccessor;
import dev.worldgen.lithostitched.duck.ContextBiomeAccessor;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SurfaceSystem;
import net.minecraft.world.level.levelgen.SurfaceRules.Condition;
import net.minecraft.world.level.levelgen.SurfaceRules.Context;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Context.class)
public class SurfaceRulesContextMixin implements ContextAccessor, ContextBiomeAccessor {
   @Shadow
   @Final
   SurfaceSystem system;
   @Shadow
   @Final
   ChunkAccess chunk;
   @Shadow
   int blockX;
   @Shadow
   int blockY;
   @Shadow
   int blockZ;
   @Shadow
   int stoneDepthBelow;
   @Shadow
   NoiseChunk noiseChunk;
   @Shadow
   private RandomState randomState;
   @Shadow
   private Supplier<Holder<Biome>> biome;

   @Override
   public SurfaceSystem lithostitched$getSystem() {
      return this.system;
   }

   @Override
   public ChunkAccess lithostitched$getChunk() {
      return this.chunk;
   }

   @Override
   public NoiseChunk lithostitched$getNoiseChunk() {
      return this.noiseChunk;
   }

   @Override
   public RandomState lithostitched$getRandomState() {
      return this.randomState;
   }

   @Override
   public int lithostitched$getStoneDepthBelow() {
      return this.stoneDepthBelow;
   }

   @Override
   public int lithostitched$getX() {
      return this.blockX;
   }

   @Override
   public int lithostitched$getY() {
      return this.blockY;
   }

   @Override
   public int lithostitched$getZ() {
      return this.blockZ;
   }

   @Override
   public Condition lithostitched$biomeMatches(HolderSet<Biome> biomes) {
      return () -> biomes.contains(this.biome.get());
   }
}
