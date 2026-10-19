package xaero.map.biome;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;

public class BiomeBlendCalculator implements BlockAndTintGetter {
   private Level original;

   public void setWorld(Level original) {
      this.original = original;
   }

   public BlockEntity getBlockEntity(BlockPos blockPos) {
      return this.original.getBlockEntity(blockPos);
   }

   public BlockState getBlockState(BlockPos blockPos) {
      return this.original.getBlockState(blockPos);
   }

   public FluidState getFluidState(BlockPos blockPos) {
      return this.original.getFluidState(blockPos);
   }

   public float getShade(Direction direction, boolean bl) {
      return this.original.getShade(direction, bl);
   }

   public LevelLightEngine getLightEngine() {
      return this.original.getLightEngine();
   }

   public int getHeight() {
      return this.original.getHeight();
   }

   public int getMinBuildHeight() {
      return this.original.getMinBuildHeight();
   }

   public int getBlockTint(BlockPos blockPos, ColorResolver colorResolver) {
      MutableBlockPos mutableBlockPos = new MutableBlockPos();
      int x = blockPos.getX();
      int y = blockPos.getY();
      int z = blockPos.getZ();
      int redAccumulator = 0;
      int greenAccumulator = 0;
      int blueAccumulator = 0;
      int count = 0;

      for (int i = -1; i < 2; i++) {
         for (int j = -1; j < 2; j++) {
            mutableBlockPos.set(x + i, y, z + j);
            Holder<Biome> biomeHolder = this.original.getBiome(mutableBlockPos);
            Biome biome = biomeHolder == null ? null : (Biome)biomeHolder.value();
            if (biome != null) {
               int colorSample = colorResolver.getColor(biome, mutableBlockPos.getX(), mutableBlockPos.getZ());
               redAccumulator += colorSample >> 16 & 0xFF;
               greenAccumulator += colorSample >> 8 & 0xFF;
               blueAccumulator += colorSample & 0xFF;
               count++;
            }
         }
      }

      int red = redAccumulator / count;
      int green = greenAccumulator / count;
      int blue = blueAccumulator / count;
      return 0xFF000000 | red << 16 | green << 8 | blue;
   }
}
