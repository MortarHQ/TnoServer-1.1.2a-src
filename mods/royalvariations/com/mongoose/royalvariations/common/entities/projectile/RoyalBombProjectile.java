package com.mongoose.royalvariations.common.entities.projectile;

import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.common.entities.RVEntityType;
import com.mongoose.royalvariations.utils.MobUtil;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;

public class RoyalBombProjectile extends ThrowableProjectile {
   public RoyalBombProjectile(EntityType<? extends ThrowableProjectile> p_37466_, Level p_37467_) {
      super(p_37466_, p_37467_);
   }

   public RoyalBombProjectile(double p_37457_, double p_37458_, double p_37459_, Level p_37460_) {
      super((EntityType)RVEntityType.ROYAL_BOMB_PROJECTILE.get(), p_37457_, p_37458_, p_37459_, p_37460_);
   }

   public RoyalBombProjectile(LivingEntity p_37463_, Level p_37464_) {
      super((EntityType)RVEntityType.ROYAL_BOMB_PROJECTILE.get(), p_37463_, p_37464_);
   }

   protected void defineSynchedData(Builder builder) {
   }

   public void tick() {
      super.tick();
      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles((SimpleParticleType)RVParticleTypes.GOLDEN_TRAIL.get(), this.getX(), this.getY() + 0.5, this.getZ(), 1, 0.1, 0.1, 0.1, 0.1);
      }
   }

   protected void onHitEntity(EntityHitResult pResult) {
      super.onHitEntity(pResult);
      if (pResult.getEntity() instanceof LivingEntity livingEntity) {
         livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 0.5F);
      }
   }

   protected void onHit(HitResult pResult) {
      super.onHit(pResult);
      LivingEntity livingEntity = this.getOwner() instanceof LivingEntity livingEntity1 ? livingEntity1 : null;
      Vec3 vec3 = pResult.getLocation();
      if (pResult instanceof EntityHitResult) {
         vec3 = vec3.add(0.0, 1.0, 0.0);
      }

      if (!this.level().isClientSide) {
         RoyalBomb royalBomb = new RoyalBomb(this.level(), vec3, livingEntity);
         this.level().addFreshEntity(royalBomb);
      }

      this.discard();
   }

   protected boolean canHitEntity(Entity pEntity) {
      if (this.getOwner() != null) {
         if (pEntity == this.getOwner()) {
            return false;
         }

         if (this.getOwner().isPassengerOfSameVehicle(pEntity)) {
            return false;
         }

         if (pEntity instanceof Projectile projectile && projectile.getOwner() == this.getOwner()) {
            return false;
         }

         if (MobUtil.areAllies(pEntity, this.getOwner())) {
            return false;
         }

         if (pEntity instanceof PartEntity<?> partEntity && partEntity.getParent() == pEntity) {
            return false;
         }

         if (this.getOwner() instanceof OwnableEntity owned) {
            if (pEntity instanceof OwnableEntity owned1) {
               if (owned.getOwner() == owned1.getOwner()) {
                  return false;
               }
            } else if (owned.getOwner() == pEntity) {
               return false;
            }
         }
      }

      return !pEntity.isSpectator() && pEntity.isAlive() && pEntity.isPickable() || this.getOwner() == null;
   }
}
