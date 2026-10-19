package net.jadenxgamer.netherexp.core.block.interfaces;

import java.util.Optional;
import net.jadenxgamer.netherexp.core.block.enums.Liquidlogged;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public interface LiquidloggedBlock extends SimpleWaterloggedBlock {
   EnumProperty<Liquidlogged.AllFluids> getLiquidloggedBlockState();

   default boolean canPlaceLiquid(@Nullable Player player, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
      return fluid == Fluids.WATER || fluid == Fluids.LAVA || fluid == JNEFluids.ECTOPLASM_SOURCE.get();
   }

   default boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
      if (state.getValue(this.getLiquidloggedBlockState()) == Liquidlogged.AllFluids.AIR) {
         if (!level.isClientSide()) {
            if (fluidState.getType() == Fluids.WATER) {
               level.setBlock(pos, (BlockState)state.setValue(this.getLiquidloggedBlockState(), Liquidlogged.AllFluids.WATER), 3);
            } else if (fluidState.getType() == Fluids.LAVA) {
               level.setBlock(pos, (BlockState)state.setValue(this.getLiquidloggedBlockState(), Liquidlogged.AllFluids.LAVA), 3);
            } else if (fluidState.getType() == JNEFluids.ECTOPLASM_SOURCE.get()) {
               level.setBlock(pos, (BlockState)state.setValue(this.getLiquidloggedBlockState(), Liquidlogged.AllFluids.ECTOPLASM), 3);
            }

            level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
         }

         return true;
      } else {
         return false;
      }
   }

   default ItemStack pickupBlock(Player player, LevelAccessor level, BlockPos pos, BlockState state) {
      if (state.getValue(this.getLiquidloggedBlockState()) != Liquidlogged.AllFluids.AIR) {
         level.setBlock(pos, (BlockState)state.setValue(this.getLiquidloggedBlockState(), Liquidlogged.AllFluids.AIR), 3);
         if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
         }

         if (state.getValue(this.getLiquidloggedBlockState()) == Liquidlogged.AllFluids.WATER) {
            return new ItemStack(Items.WATER_BUCKET);
         } else {
            return state.getValue(this.getLiquidloggedBlockState()) == Liquidlogged.AllFluids.LAVA
               ? new ItemStack(Items.LAVA_BUCKET)
               : new ItemStack((ItemLike)JNEFluids.ECTOPLASM_BUCKET.get());
         }
      } else {
         return ItemStack.EMPTY;
      }
   }

   default Optional<SoundEvent> getPickupSound(BlockState state) {
      if (state.getValue(this.getLiquidloggedBlockState()) == Liquidlogged.AllFluids.LAVA) {
         return Fluids.LAVA.getPickupSound();
      } else {
         return state.getValue(this.getLiquidloggedBlockState()) == Liquidlogged.AllFluids.ECTOPLASM
            ? JNEFluids.ECTOPLASM.get().getPickupSound()
            : super.getPickupSound(state);
      }
   }
}
