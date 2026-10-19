package me.wesley1808.servercore.mixin.optimizations.ticking.chunk.random;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = LiquidBlock.class, priority = 900)
public class LiquidBlockMixin {
   @Redirect(
      method = "isRandomlyTicking(Lnet/minecraft/world/level/block/state/BlockState;)Z",
      require = 0,
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;isRandomlyTicking()Z")
   )
   private boolean servercore$cancelDuplicateFluidTicks(FluidState fluidState) {
      return false;
   }
}
