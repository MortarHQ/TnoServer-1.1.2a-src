package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BubbleColumnBlock.class)
public abstract class BubbleColumnBlockMixin {
   @Inject(
      method = "getColumnState(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;",
      at = @At("RETURN"),
      cancellable = true
   )
   private static void getColumnState(BlockState state, CallbackInfoReturnable<BlockState> cir) {
      if (state.is(ESBlocks.THERMAL_SPRINGSTONE.get())) {
         cir.setReturnValue((BlockState)Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(BlockStateProperties.DRAG, false));
      }

      if (state.is((Block)ESBlocks.ABYSSAL_MAGMA_BLOCK.get())
         || state.is((Block)ESBlocks.THERMABYSSAL_MAGMA_BLOCK.get())
         || state.is((Block)ESBlocks.CRYOBYSSAL_MAGMA_BLOCK.get())) {
         cir.setReturnValue((BlockState)Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(BlockStateProperties.DRAG, true));
      }
   }

   @Inject(
      method = "canSurvive(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z",
      at = @At("RETURN"),
      cancellable = true
   )
   private void canSurvive(BlockState state, LevelReader levelReader, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
      if (levelReader.getBlockState(pos.below()).is(ESBlocks.THERMAL_SPRINGSTONE.get())
         || levelReader.getBlockState(pos.below()).is((Block)ESBlocks.ABYSSAL_MAGMA_BLOCK.get())
         || levelReader.getBlockState(pos.below()).is((Block)ESBlocks.THERMABYSSAL_MAGMA_BLOCK.get())
         || levelReader.getBlockState(pos.below()).is((Block)ESBlocks.CRYOBYSSAL_MAGMA_BLOCK.get())) {
         cir.setReturnValue(true);
      }
   }
}
