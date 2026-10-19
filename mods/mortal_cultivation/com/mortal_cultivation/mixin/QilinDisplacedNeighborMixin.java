package com.mortal_cultivation.mixin;

import com.mortal_cultivation.client.vfx.QilinClientEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class QilinDisplacedNeighborMixin {
   @Inject(method = "shouldRenderFace", at = @At("HEAD"), cancellable = true, remap = false)
   private static void mortalCultivation$exposeDisplacedNeighbor(
      BlockState state, BlockGetter level, BlockPos pos, Direction face, BlockPos neighbor, CallbackInfoReturnable<Boolean> callback
   ) {
      if (QilinClientEffects.hides(neighbor, level.getBlockState(neighbor))) {
         callback.setReturnValue(true);
      }
   }
}
