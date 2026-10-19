package com.foxablazeultimate.entity;

import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.beam.PredatorMistProjectile;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class VeldoraBlackMistProjectile extends PredatorMistProjectile {
   private int damageIntervalTicks = 10;

   public VeldoraBlackMistProjectile(EntityType<? extends VeldoraBlackMistProjectile> entityType, Level level) {
      super(entityType, level);
      this.setElementalAttack(true);
      this.setConsumeProjectile(false);
      this.setBlockMode(1);
   }

   public VeldoraBlackMistProjectile(EntityType<? extends VeldoraBlackMistProjectile> entityType, Level level, LivingEntity owner) {
      this(entityType, level);
      this.setOwner(owner);
   }

   public ResourceKey<DamageType> getDamageType() {
      return TensuraDamageTypes.BLACK_LIGHTNING;
   }

   public ResourceKey<DamageType> getSecondaryDamageType() {
      return TensuraDamageTypes.LIGHTNING_ELEMENTAL;
   }

   protected void addAdditionalSaveData(@NotNull CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("DamageIntervalTicks", this.damageIntervalTicks);
   }

   protected void readAdditionalSaveData(@NotNull CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("DamageIntervalTicks")) {
         this.setDamageIntervalTicks(compound.getInt("DamageIntervalTicks"));
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide() && this.tickCount % 3 == 0) {
         this.spawnBlackLightningTrail();
      }
   }

   protected boolean hitEntity(Entity entity, ProjectileHitResult customResult) {
      if (this.getLife() - this.getAge() < 29) {
         return false;
      } else {
         return this.tickCount % this.damageIntervalTicks != 0 ? false : this.hitEntityWithoutPredation(entity, customResult);
      }
   }

   protected void handleBlockInteraction() {
   }

   private boolean hitEntityWithoutPredation(Entity entity, ProjectileHitResult result) {
      if (result == ProjectileHitResult.PASS) {
         return false;
      }

      boolean success = false;
      if (this.getBurnTicks() > 0) {
         this.applyBurn(entity);
         success = true;
      }

      if (!(this.getDamage() > 0.0F) && !(this.getSecondaryDamage() > 0.0F)) {
         if (entity instanceof LivingEntity target) {
            success = this.applyMobEffects(target) || success;
         }
      } else if (result != ProjectileHitResult.HIT_NO_DAMAGE) {
         if (this.dealDamage(entity)) {
            if (entity instanceof LivingEntity target) {
               this.applyMobEffects(target);
            }

            success = true;
         }

         if (this.getKnockForce() > 0.0F) {
            this.knockBack(entity);
            success = true;
         }
      }

      return success;
   }

   private void spawnBlackLightningTrail() {
      Vec3 start = this.position();
      Vec3 end = new Vec3(this.getCollideX(), this.getCollideY(), this.getCollideZ());
      Vec3 offset = end.subtract(start);
      double length = Math.min(Math.max(0.0, offset.length()), Math.max(1.0F, this.getRange()));
      if (!(length <= 0.1)) {
         Vec3 direction = offset.normalize();
         int points = Math.max(2, Math.min(8, (int)Math.ceil(length / 5.0)));

         for (int i = 1; i <= points; i++) {
            Vec3 pos = start.add(direction.scale(length * i / (points + 1.0)));
            TensuraParticleHelper.spawnServerParticles(
               this.level(), (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), pos.x, pos.y, pos.z, 1, 0.16, 0.16, 0.16, 0.04, true
            );
            if (this.tickCount % 6 == 0 && i % 2 == 0) {
               TensuraParticleHelper.spawnServerParticles(
                  this.level(), (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get(), pos.x, pos.y, pos.z, 1, 0.08, 0.08, 0.08, 0.02, true
               );
            }
         }
      }
   }

   public void setDamageIntervalTicks(int damageIntervalTicks) {
      this.damageIntervalTicks = Math.max(1, damageIntervalTicks);
   }
}
