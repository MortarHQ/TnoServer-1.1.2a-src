package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.IceAndFireEntityCompat;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = {"com.iafenvoy.iceandfire.entity.DragonBaseEntity", "com.iafenvoy.iceandfire.entity.DragonSkullEntity"}, remap = false)
public abstract class IceAndFireDragonRemainsDamageMixin {
   @Inject(method = "hurt", at = @At("HEAD"), cancellable = true, require = 0)
   private void tensuraTno$protectDragonRemains(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
      if (IceAndFireEntityCompat.shouldBlockDragonRemainsDamage(source, (Entity)this)) {
         cir.setReturnValue(false);
      }
   }
}
