package it.hurts.octostudios.reliquified_twilight_forest.mixin;

import it.hurts.octostudios.reliquified_twilight_forest.init.EffectRegistry;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import twilightforest.client.MovingCicadaSoundInstance;

@Mixin(MovingCicadaSoundInstance.class)
public class MovingCicadaSoundInstanceMixin {
   @Shadow
   @Final
   protected LivingEntity wearer;

   @Inject(method = "isWearingCicadaCurio", at = @At("HEAD"), cancellable = true)
   private void isWearingCicadaCurio(CallbackInfoReturnable<Boolean> cir) {
      if (this.wearer.hasEffect(EffectRegistry.CICADA_INFESTATION)) {
         cir.setReturnValue(true);
         cir.cancel();
      }
   }
}
