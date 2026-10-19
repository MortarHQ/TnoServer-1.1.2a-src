package com.github.wal_bos.tensuramorecult.mixin;

import com.benji.netherman.common.entity.AzazelSpikeEntity;
import com.github.wal_bos.tensuramorecult.CultCombat;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = AzazelSpikeEntity.class, remap = false)
public abstract class SpikeDamageMixin {
   @Redirect(
      method = "tick",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z")
   )
   private boolean tensuramorecult$spikeOwner(LivingEntity target, DamageSource source, float amount) {
      return CultCombat.ownedDamage(target, source, amount, (AzazelSpikeEntity)this);
   }
}
