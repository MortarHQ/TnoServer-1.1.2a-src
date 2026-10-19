package com.chronodawn.fluids;

import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModFluids;
import com.chronodawn.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public abstract class DecorativeWaterFluid extends FlowingFluid {
   public Fluid getFlowing() {
      return (Fluid)ModFluids.DECORATIVE_WATER_FLOWING.get();
   }

   public Fluid getSource() {
      return (Fluid)ModFluids.DECORATIVE_WATER.get();
   }

   public Item getBucket() {
      return (Item)ModItems.DECORATIVE_WATER_BUCKET.get();
   }

   protected boolean canConvertToSource(Level level) {
      return true;
   }

   protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
      BlockEntity blockentity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
      Block.dropResources(state, level, pos, blockentity);
   }

   protected int getSlopeFindDistance(LevelReader level) {
      return 4;
   }

   protected int getDropOff(LevelReader level) {
      return 1;
   }

   public int getTickDelay(LevelReader level) {
      return 5;
   }

   protected float getExplosionResistance() {
      return 100.0F;
   }

   protected BlockState createLegacyBlock(FluidState state) {
      return (BlockState)((Block)ModBlocks.DECORATIVE_WATER.get()).defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
   }

   public boolean isSame(Fluid fluid) {
      return fluid == ModFluids.DECORATIVE_WATER.get() || fluid == ModFluids.DECORATIVE_WATER_FLOWING.get();
   }

   protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos pos, Fluid fluid, Direction direction) {
      return direction == Direction.DOWN && !this.isSame(fluid);
   }

   public static class Flowing extends DecorativeWaterFluid {
      protected void createFluidStateDefinition(Builder<Fluid, FluidState> builder) {
         super.createFluidStateDefinition(builder);
         builder.add(new Property[]{LEVEL});
      }

      public int getAmount(FluidState state) {
         return (Integer)state.getValue(LEVEL);
      }

      public boolean isSource(FluidState state) {
         return false;
      }
   }

   public static class Source extends DecorativeWaterFluid {
      public int getAmount(FluidState state) {
         return 8;
      }

      public boolean isSource(FluidState state) {
         return true;
      }
   }
}
