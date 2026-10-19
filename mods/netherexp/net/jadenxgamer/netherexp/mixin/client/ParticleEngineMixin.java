package net.jadenxgamer.netherexp.mixin.client;

import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public abstract class ParticleEngineMixin {
   @Shadow
   protected ClientLevel level;

   @Inject(method = "crack", at = @At("HEAD"), cancellable = true)
   private void netherexp$crack(BlockPos pos, Direction direction, CallbackInfo cir) {
      BlockState state = this.level.getBlockState(pos);
      if (state.is(JNETags.Blocks.FROGMIST)) {
         cir.cancel();
      }
   }
}
