package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.block.entity.ESSkullBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSkullBlock.class)
public abstract class AbstractSkullBlockMixin {
   @Inject(
      method = "newBlockEntity(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/entity/BlockEntity;",
      at = @At("HEAD"),
      cancellable = true
   )
   public void newBlockEntity(BlockPos blockPos, BlockState blockState, CallbackInfoReturnable<BlockEntity> cir) {
      if (BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).getNamespace().equals("eternal_starlight")) {
         cir.setReturnValue(new ESSkullBlockEntity(blockPos, blockState));
      }
   }
}
