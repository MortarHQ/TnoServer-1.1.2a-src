package com.tensura_tno.mixin.client;

import com.tensura_tno.race.fox_spirit.SpiritSummonEntityHelper;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.benji.netherman.entity.AzazelEntity", remap = false)
public abstract class AzazelSpiritSummonLootMixin {
   @Shadow
   @Final
   public static EntityDataAccessor<Integer> ATTACK_STATE;

   @Inject(method = "startDeathCinematic", at = @At("HEAD"), cancellable = true, require = 0)
   private void tno$removeSummonWithoutRewardBarrel(CallbackInfo ci) {
      Entity entity = (Entity)this;
      if (!entity.level().isClientSide() && SpiritSummonEntityHelper.isSpiritSummon(entity)) {
         entity.discard();
         ci.cancel();
      }
   }

   @Inject(method = "tick", at = @At("HEAD"), cancellable = true, remap = true, require = 0)
   private void tno$removeRestoredDyingSummon(CallbackInfo ci) {
      Entity entity = (Entity)this;
      if (!entity.level().isClientSide() && SpiritSummonEntityHelper.isSpiritSummon(entity) && (Integer)entity.getEntityData().get(ATTACK_STATE) == 9) {
         entity.discard();
         ci.cancel();
      }
   }
}
