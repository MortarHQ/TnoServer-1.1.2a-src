package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FarmBlock.class)
public abstract class FarmBlockMixin {
   @Inject(
      method = "getStateForPlacement(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;",
      at = @At("RETURN"),
      cancellable = true
   )
   private void getStateForPlacement(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<BlockState> cir) {
      if (((FarmBlock)this).defaultBlockState().is((Block)ESBlocks.NIGHTFALL_FARMLAND.get())
         && !((FarmBlock)this).defaultBlockState().canSurvive(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos())) {
         cir.setReturnValue(ESBlocks.NIGHTFALL_DIRT.get().defaultBlockState());
      }
   }

   @Inject(
      method = "turnToDirt(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void turnToDirt(Entity entity, BlockState blockState, Level level, BlockPos blockPos, CallbackInfo ci) {
      if (blockState.is((Block)ESBlocks.NIGHTFALL_FARMLAND.get()) || blockState.is((Block)ESBlocks.NIGHTFALL_DIRT_PATH.get())) {
         ci.cancel();
         BlockState blockState2 = Block.pushEntitiesUp(blockState, ESBlocks.NIGHTFALL_DIRT.get().defaultBlockState(), level, blockPos);
         level.setBlockAndUpdate(blockPos, blockState2);
         level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, Context.of(entity, blockState2));
      }
   }
}
