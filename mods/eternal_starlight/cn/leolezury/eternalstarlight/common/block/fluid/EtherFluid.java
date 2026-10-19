package cn.leolezury.eternalstarlight.common.block.fluid;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESFluids;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public abstract class EtherFluid extends FlowingFluid {
   public static final ResourceLocation ARMOR_MODIFIER_ID = EternalStarlight.id("armor.ether");

   public static AttributeModifier armorModifier(float amount) {
      return new AttributeModifier(ARMOR_MODIFIER_ID, amount, Operation.ADD_VALUE);
   }

   public Fluid getFlowing() {
      return (Fluid)ESFluids.ETHER_FLOWING.get();
   }

   public Fluid getSource() {
      return (Fluid)ESFluids.ETHER_STILL.get();
   }

   protected boolean canConvertToSource(Level level) {
      return true;
   }

   protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
      BlockEntity blockEntity = blockState.hasBlockEntity() ? levelAccessor.getBlockEntity(blockPos) : null;
      Block.dropResources(blockState, levelAccessor, blockPos, blockEntity);
   }

   protected int getSlopeFindDistance(LevelReader levelReader) {
      return 4;
   }

   protected int getDropOff(LevelReader levelReader) {
      return 1;
   }

   public Item getBucket() {
      return ESItems.ETHER_BUCKET.get();
   }

   protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
      return direction == Direction.DOWN && !fluidState.is(FluidTags.WATER);
   }

   public int getTickDelay(LevelReader levelReader) {
      return 5;
   }

   protected float getExplosionResistance() {
      return 100.0F;
   }

   protected BlockState createLegacyBlock(FluidState fluidState) {
      return (BlockState)((LiquidBlock)ESBlocks.ETHER.get()).defaultBlockState().setValue(BlockStateProperties.LEVEL, getLegacyLevel(fluidState));
   }

   public boolean isSame(Fluid fluid) {
      return fluid == ESFluids.ETHER_STILL.get() || fluid == ESFluids.ETHER_FLOWING.get();
   }

   protected void spreadTo(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, Direction direction, FluidState fluidState) {
      if (!blockState.getFluidState().isEmpty() && !this.isSame(blockState.getFluidState().getType())) {
         if (blockState.getBlock() instanceof LiquidBlock) {
            levelAccessor.setBlock(
               blockPos,
               blockState.getFluidState().is(FluidTags.LAVA)
                  ? ESBlocks.MOLTEN_STELLAGMITE.get().defaultBlockState()
                  : ESBlocks.THIOQUARTZ_BLOCK.get().defaultBlockState(),
               3
            );
         }

         levelAccessor.levelEvent(1501, blockPos, 0);
      } else {
         super.spreadTo(levelAccessor, blockPos, blockState, direction, fluidState);
      }
   }

   public static class Flowing extends EtherFluid {
      protected void createFluidStateDefinition(Builder<Fluid, FluidState> builder) {
         builder.add(new Property[]{LEVEL});
         super.createFluidStateDefinition(builder);
      }

      public boolean isSource(FluidState fluidState) {
         return false;
      }

      public int getAmount(FluidState fluidState) {
         return (Integer)fluidState.getValue(LEVEL);
      }
   }

   public static class Still extends EtherFluid {
      public boolean isSource(FluidState fluidState) {
         return true;
      }

      public int getAmount(FluidState fluidState) {
         return 8;
      }
   }
}
