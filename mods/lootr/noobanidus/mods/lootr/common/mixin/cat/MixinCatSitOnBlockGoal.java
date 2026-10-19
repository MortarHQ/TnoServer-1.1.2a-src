package noobanidus.mods.lootr.common.mixin.cat;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.LootrTags;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CatSitOnBlockGoal.class)
public class MixinCatSitOnBlockGoal {
   @Redirect(
      method = "isValidTarget",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z")
   )
   protected boolean LootrIsIn(BlockState state, Block block) {
      return !LootrRegistry.isReady() ? state.is(block) : state.is(block) || state.is(LootrTags.Blocks.CATS_CAN_BLOCK);
   }

   @Inject(
      method = "isValidTarget",
      at = @At(
         target = "Lnet/minecraft/world/level/block/entity/ChestBlockEntity;getOpenCount(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)I",
         value = "INVOKE"
      ),
      cancellable = true
   )
   protected void LootrPlayersUsing(LevelReader reader, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
      BlockEntity blockEntity = reader.getBlockEntity(pos);
      if (LootrAPI.resolveBlockEntity(blockEntity) instanceof ILootrBlockEntity lootrBlockEntity && lootrBlockEntity.getPhysicalOpenerCount() < 1) {
         info.setReturnValue(true);
         info.cancel();
      }
   }
}
