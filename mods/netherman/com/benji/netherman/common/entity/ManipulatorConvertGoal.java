package com.benji.netherman.common.entity;

import com.benji.netherman.init.ModEntities;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.npc.Villager;

public class ManipulatorConvertGoal extends Goal {
   private final ManipulatorEntity mob;
   private LivingEntity targetVictim;

   public ManipulatorConvertGoal(ManipulatorEntity mob) {
      this.mob = mob;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      if (this.mob.castTicks > 0) {
         return false;
      } else {
         List<Villager> villagers = this.mob.level().getEntitiesOfClass(Villager.class, this.mob.getBoundingBox().inflate(10.0));
         if (!villagers.isEmpty()) {
            this.targetVictim = (LivingEntity)villagers.get(0);
            return true;
         } else {
            List<Piglin> piglins = this.mob.level().getEntitiesOfClass(Piglin.class, this.mob.getBoundingBox().inflate(10.0));
            if (!piglins.isEmpty()) {
               this.targetVictim = (LivingEntity)piglins.get(0);
               return true;
            } else {
               List<GhastlyEntity> ghastlies = this.mob.level().getEntitiesOfClass(GhastlyEntity.class, this.mob.getBoundingBox().inflate(10.0));
               if (!ghastlies.isEmpty()) {
                  this.targetVictim = (LivingEntity)ghastlies.get(0);
                  return true;
               } else {
                  return false;
               }
            }
         }
      }
   }

   public void start() {
      this.mob.getNavigation().stop();
      this.mob.getLookControl().setLookAt(this.targetVictim);
      this.mob.setEntityState(3);
      this.mob.castTicks = 35;
   }

   public void tick() {
      if (this.targetVictim != null) {
         this.mob.getLookControl().setLookAt(this.targetVictim);
      }

      if (this.mob.castTicks == 1 && this.targetVictim != null && this.targetVictim.isAlive()) {
         if (this.targetVictim instanceof Villager) {
            VillagerPrisonerEntity prisoner = (VillagerPrisonerEntity)((EntityType)ModEntities.VILLAGER_PRISONER.get()).create(this.mob.level());
            if (prisoner != null) {
               prisoner.moveTo(this.targetVictim.getX(), this.targetVictim.getY(), this.targetVictim.getZ(), this.targetVictim.getYRot(), 0.0F);
               prisoner.setMasterId(this.mob.getId());
               this.mob.level().addFreshEntity(prisoner);
            }

            this.mob.playSound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED, 1.0F, 1.0F);
         } else if (this.targetVictim instanceof Piglin) {
            PiglinPrisonerEntity prisoner = (PiglinPrisonerEntity)((EntityType)ModEntities.PIGLIN_PRISONER.get()).create(this.mob.level());
            if (prisoner != null) {
               prisoner.moveTo(this.targetVictim.getX(), this.targetVictim.getY(), this.targetVictim.getZ(), this.targetVictim.getYRot(), 0.0F);
               prisoner.setMasterId(this.mob.getId());
               this.mob.level().addFreshEntity(prisoner);
            }

            this.mob.playSound(SoundEvents.PIGLIN_BRUTE_CONVERTED_TO_ZOMBIFIED, 1.0F, 0.8F);
         } else if (this.targetVictim instanceof GhastlyEntity ghastly) {
            ghastly.setHealth(0.0F);
            ghastly.die(this.mob.damageSources().magic());
            this.mob.playSound(SoundEvents.WITHER_SPAWN, 0.5F, 1.5F);
            if (this.mob.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(
                  ParticleTypes.SOUL, this.targetVictim.getX(), this.targetVictim.getY() + 0.5, this.targetVictim.getZ(), 12, 0.3, 0.3, 0.3, 0.05
               );
            }
         }

         if (this.mob.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
               DustParticleOptions.REDSTONE, this.targetVictim.getX(), this.targetVictim.getY() + 1.0, this.targetVictim.getZ(), 10, 0.2, 0.5, 0.2, 0.05
            );
         }

         if (!(this.targetVictim instanceof GhastlyEntity)) {
            this.targetVictim.discard();
         }

         this.targetVictim = null;
      }
   }

   public boolean canContinueToUse() {
      return this.mob.castTicks > 0 && this.targetVictim != null && this.targetVictim.isAlive();
   }
}
