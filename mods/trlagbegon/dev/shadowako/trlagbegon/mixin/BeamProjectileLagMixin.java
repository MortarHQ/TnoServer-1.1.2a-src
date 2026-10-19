package dev.shadowako.trlagbegon.mixin;

import io.github.manasmods.tensura.entity.magic.beam.BeamProjectile;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BeamProjectile.class, remap = false)
public abstract class BeamProjectileLagMixin {
   @Inject(method = "handleBlockInteraction", at = @At("HEAD"), cancellable = true, remap = false)
   private void trlagbegon$throttleBeamBlockInteraction(CallbackInfo ci) {
      Entity self = (Entity)this;
      if (!self.level().isClientSide() && self.tickCount % 5 != 0) {
         ci.cancel();
      }
   }

   @Redirect(
      method = "handleBlockInteraction",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/entity/magic/beam/BeamProjectile;getSize()F"),
      remap = false
   )
   private float trlagbegon$capBeamSize(BeamProjectile self) {
      return Math.min(4.0F, self.getSize());
   }
}
