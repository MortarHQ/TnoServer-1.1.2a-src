package com.github.wal_bos.tensuramorecult.mixin;

import com.benji.netherman.common.entity.AzazelEntity;
import com.benji.netherman.common.entity.LaserEntity;
import com.github.wal_bos.tensuramorecult.CultCombat;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = AzazelEntity.class, remap = false)
public abstract class AzazelDamageMixin {
   @Redirect(
      method = "performMidasAttack",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z")
   )
   private boolean tensuramorecult$midasOwner(Player target, DamageSource source, float amount) {
      return CultCombat.ownedDamage(target, source, amount, (AzazelEntity)this);
   }

   @ModifyArg(
      method = "performArrowAttack",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"),
      index = 0
   )
   private Entity tensuramorecult$laserOwner(Entity entity) {
      if (entity instanceof LaserEntity) {
         entity.getPersistentData().putUUID("tensuramorecult_owner", ((AzazelEntity)this).getUUID());
      }

      return entity;
   }
}
