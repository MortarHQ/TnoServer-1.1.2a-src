package dev.ftb.mods.ftbchunks.core.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.ftb.mods.ftbchunks.util.FireSpreadHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin {
   @Shadow
   protected abstract int getIgniteOdds(LevelReader var1, BlockPos var2);

   @Redirect(
      method = "tick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/block/FireBlock;getIgniteOdds(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)I"
      )
   )
   private int ftbc$getIgniteOdds(FireBlock instance, LevelReader arg, BlockPos arg2, @Local(name = "arg3", argsOnly = true) BlockPos pos) {
      return FireSpreadHelper.shouldPreventFireSpread(arg, pos, arg2) ? 0 : this.getIgniteOdds(arg, pos);
   }
}
