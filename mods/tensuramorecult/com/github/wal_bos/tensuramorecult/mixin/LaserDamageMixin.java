package com.github.wal_bos.tensuramorecult.mixin;

import com.benji.netherman.common.entity.LaserEntity;
import com.github.wal_bos.tensuramorecult.CultCombat;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = LaserEntity.class, remap = false)
public abstract class LaserDamageMixin {
   @Redirect(
      method = "tick",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z")
   )
   private boolean tensuramorecult$laserOwner(Player target, DamageSource source, float amount) {
      return CultCombat.ownedDamage(target, source, amount, (LaserEntity)this);
   }
}
