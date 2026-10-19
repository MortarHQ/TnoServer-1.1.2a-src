package xaero.map.biome;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import xaero.map.MapProcessor;
import xaero.map.MapWriter;
import xaero.map.WorldMap;
import xaero.map.cache.BrokenBlockTintCache;
import xaero.map.region.MapTile;

public class BlockTintProvider implements BlockAndTintGetter {
   private final Registry<Biome> biomeRegistry;
   private final BiomeColorCalculator calculator;
   private final MutableBlockPos mutablePos;
   private final MapProcessor mapProcessor;
   private final BrokenBlockTintCache brokenBlockTintCache;
   private final MapWriter mapWriter;
   private BlockState state;
   private boolean overlay;
   private MapTile tile;
   private int caveLayer;

   public BlockTintProvider(
      Registry<Biome> biomeRegistry, BiomeColorCalculator calculator, MapProcessor mapProcessor, BrokenBlockTintCache brokenBlockTintCache, MapWriter mapWriter
   ) {
      this.biomeRegistry = biomeRegistry;
      this.calculator = calculator;
      this.mutablePos = new MutableBlockPos();
      this.mapProcessor = mapProcessor;
      this.brokenBlockTintCache = brokenBlockTintCache;
      this.mapWriter = mapWriter;
   }

   public int getHeight() {
      return 16;
   }

   public int getMinBuildHeight() {
      return this.mutablePos.getY() >> 4 << 4;
   }

   public BlockEntity getBlockEntity(BlockPos blockPos) {
      return null;
   }

   public BlockState getBlockState(BlockPos blockPos) {
      return this.state;
   }

   public FluidState getFluidState(BlockPos blockPos) {
      return this.state == null ? null : this.state.getFluidState();
   }

   public float getShade(Direction direction, boolean bl) {
      return 1.0F;
   }

   public LevelLightEngine getLightEngine() {
      return null;
   }

   public int getBlockTint(BlockPos blockPos, ColorResolver colorResolver) {
      this.mutablePos.set(blockPos);
      return this.calculator.getBiomeColor(colorResolver, this.overlay, this.mutablePos, this.tile, this.caveLayer, this.biomeRegistry, this.mapProcessor);
   }

   public int getBiomeColor(BlockPos blockPos, BlockState state, boolean overlay, MapTile tile, int caveLayer) {
      if (this.brokenBlockTintCache.isBroken(state)) {
         return -1;
      }

      this.mutablePos.set(blockPos);
      this.state = state;
      this.overlay = overlay;
      this.tile = tile;
      this.caveLayer = caveLayer;

      try {
         return Minecraft.getInstance().getBlockColors().getColor(state, this, blockPos, this.mapWriter.getBlockTintIndex(state));
      } catch (Throwable t) {
         this.brokenBlockTintCache.setBroken(state);
         WorldMap.LOGGER.error("Error calculating block tint for block state " + state + "! Total: " + this.brokenBlockTintCache.getSize(), t);
         return -1;
      }
   }
}
