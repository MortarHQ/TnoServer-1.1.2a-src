package com.foxablazeultimate.mixin;

import com.foxablazeultimate.ability.skill.fortuna.FortunaSkill;
import io.github.manasmods.tensura.entity.projectile.TensuraFlyingProjectile;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = TensuraFlyingProjectile.class, remap = false)
public abstract class MixinTensuraFlyingProjectileFortuna {
   @Redirect(
      method = "applyWarpShot(Lnet/minecraft/world/entity/projectile/Projectile;Lnet/minecraft/world/entity/Entity;F)Z",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/util/EnergyHelper;isOutOfEnergy(Lnet/minecraft/world/entity/LivingEntity;DD)Z")
   )
   private static boolean foxablazeultimate$fortunaWarpShotCost(LivingEntity owner, double auraCost, double magiculeCost) {
      return EnergyHelper.isOutOfEnergy(owner, auraCost, FortunaSkill.getWarpShotMagiculeCost(owner, magiculeCost));
   }
}
