package com.github.wal_bos.tensuramorecult.mixin;

import com.benji.netherman.common.entity.AzazelEntity;
import com.benji.netherman.common.entity.AzazelHumanEntity;
import com.github.wal_bos.tensuramorecult.CultCombat;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {AzazelEntity.class, AzazelHumanEntity.class}, remap = false)
public abstract class CutsceneDeathMixin {
   @Inject(method = "setHealth", at = @At("HEAD"), cancellable = true)
   private void tensuramorecult$protectCutsceneHealth(float health, CallbackInfo ci) {
      if (health <= 0.0F && CultCombat.ending((Entity)this)) {
         ci.cancel();
      }
   }
}
