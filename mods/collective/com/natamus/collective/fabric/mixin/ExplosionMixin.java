package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveExplosionEvents;
import net.minecraft.class_1297;
import net.minecraft.class_1927;
import net.minecraft.class_1937;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = class_1927.class, priority = 1001)
public class ExplosionMixin {
   @Final
   @Shadow
   private class_1937 field_9187;
   @Final
   @Shadow
   private class_1297 field_9185;

   @Inject(method = "explode()V", at = @At("TAIL"))
   public void Explosion_explode(CallbackInfo ci) {
      class_1927 explosion = (class_1927)this;
      ((CollectiveExplosionEvents.Explosion_Detonate)CollectiveExplosionEvents.EXPLOSION_DETONATE.invoker())
         .onDetonate(this.field_9187, this.field_9185, explosion);
   }
}
