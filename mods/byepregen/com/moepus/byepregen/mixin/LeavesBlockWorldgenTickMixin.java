package com.moepus.byepregen.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LeavesBlock.class, remap = false, priority = 500)
public abstract class LeavesBlockWorldgenTickMixin {
   @Inject(method = "updateShape", at = @At("HEAD"), cancellable = true)
   private void c6c$skipStableWorldgenLeafTick(
      BlockState state,
      Direction direction,
      BlockState neighborState,
      LevelAccessor level,
      BlockPos pos,
      BlockPos neighborPos,
      CallbackInfoReturnable<BlockState> cir
   ) {
      if (level instanceof WorldGenRegion worldGenRegion) {
         if ((Boolean)state.getValue(LeavesBlock.WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
         }

         cir.setReturnValue(state);
      }
   }
}
