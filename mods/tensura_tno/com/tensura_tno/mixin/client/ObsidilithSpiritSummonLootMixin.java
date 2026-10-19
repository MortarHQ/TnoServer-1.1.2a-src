package com.tensura_tno.mixin.client;

import com.tensura_tno.race.fox_spirit.SpiritSummonEntityHelper;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.ObsidilithUtils", remap = false)
public abstract class ObsidilithSpiritSummonLootMixin {
   @Inject(method = "onDeath", at = @At("HEAD"), cancellable = true, require = 0)
   private static void tno$disableSummonPillar(LivingEntity entity, int experience, CallbackInfo ci) {
      if (SpiritSummonEntityHelper.isSpiritSummon(entity)) {
         ci.cancel();
      }
   }
}
