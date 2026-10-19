package com.teamresourceful.resourcefullib.common.fluid;

import com.teamresourceful.resourcefullib.common.fluid.data.FluidData;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;

public abstract class ResourcefulFlowingFluid extends FlowingFluid {
   private final FluidData data;

   public ResourcefulFlowingFluid(FluidData data) {
      this.data = data;
   }

   @Internal
   public FluidData getData() {
      return this.data;
   }

   @NotNull
   public Fluid getFlowing() {
      return (Fluid)this.data.flowing().get();
   }

   @NotNull
   public Fluid getSource() {
      return (Fluid)this.data.still().get();
   }

   protected boolean canConvertToSource(Level level) {
      return this.data.properties().canConvertToSource();
   }

   protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
      Block.dropResources(state, level, pos, state.hasBlockEntity() ? level.getBlockEntity(pos) : null);
   }

   protected int getSlopeFindDistance(LevelReader levelReader) {
      return this.data.properties().slopeFindDistance();
   }

   protected int getDropOff(LevelReader levelReader) {
      return this.data.properties().dropOff();
   }

   @NotNull
   public Item getBucket() {
      return Objects.requireNonNullElse(this.data.bucket().get(), Items.AIR);
   }

   protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction) {
      return direction == Direction.DOWN && !this.isSame(fluid);
   }

   public int getTickDelay(LevelReader levelReader) {
      return this.data.properties().tickDelay();
   }

   protected float getExplosionResistance() {
      return this.data.properties().explosionResistance();
   }

   @NotNull
   protected BlockState createLegacyBlock(FluidState fluidState) {
      Block block = (Block)this.data.block().get();
      return block == null ? Blocks.AIR.defaultBlockState() : (BlockState)block.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
   }

   public boolean isSame(Fluid fluid) {
      return fluid == this.data.still().get() || fluid == this.data.flowing().get();
   }

   @NotNull
   public Optional<SoundEvent> getPickupSound() {
      return Optional.of(this.data.properties().sounds().getOrDefault("bucket_fill", SoundEvents.BUCKET_FILL));
   }

   public static class Flowing extends ResourcefulFlowingFluid {
      public Flowing(FluidData data) {
         super(data);
         this.registerDefaultState((FluidState)((FluidState)this.getStateDefinition().any()).setValue(LEVEL, 7));
         data.setFlowing(() -> this);
      }

      protected void createFluidStateDefinition(Builder<Fluid, FluidState> builder) {
         super.createFluidStateDefinition(builder);
         builder.add(new Property[]{LEVEL});
      }

      public boolean isSource(FluidState state) {
         return false;
      }

      public int getAmount(FluidState state) {
         return (Integer)state.getValue(LEVEL);
      }
   }

   public static class Still extends ResourcefulFlowingFluid {
      public Still(FluidData data) {
         super(data);
         data.setStill(() -> this);
      }

      public int getAmount(FluidState state) {
         return 8;
      }

      public boolean isSource(FluidState state) {
         return true;
      }
   }
}
