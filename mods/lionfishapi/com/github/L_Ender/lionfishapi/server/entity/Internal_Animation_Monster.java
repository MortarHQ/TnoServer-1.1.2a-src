package com.github.L_Ender.lionfishapi.server.entity;

import java.util.List;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.CommonHooks;

public class Internal_Animation_Monster extends Monster {
   protected static final ResourceLocation HEALTHMMULTIPLER_ID = ResourceLocation.withDefaultNamespace("random_spawn_bonus");
   protected static final ResourceLocation RANDOM_SPAWN_BONUS_ID = ResourceLocation.withDefaultNamespace("random_spawn_bonus");
   public static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Internal_Animation_Monster.class, EntityDataSerializers.INT);
   protected boolean dropAfterDeathAnim = false;
   private int killDataRecentlyHit;
   private DamageSource killDataCause;
   private Player killDataAttackingPlayer;
   public int attackTicks;
   public int attackCooldown;
   public int customDeathTime;

   public Internal_Animation_Monster(EntityType entity, Level world) {
      super(entity, world);
   }

   protected void defineSynchedData(Builder p_326272_) {
      super.defineSynchedData(p_326272_);
      p_326272_.define(ATTACK_STATE, 0);
   }

   public int getAttackState() {
      return (Integer)this.entityData.get(ATTACK_STATE);
   }

   public void setAttackState(int input) {
      this.attackTicks = 0;
      this.entityData.set(ATTACK_STATE, input);
   }

   public void tick() {
      super.tick();
      if (this.getAttackState() > 0) {
         this.attackTicks++;
      }

      if (this.attackCooldown > 0) {
         this.attackCooldown--;
      }
   }

   public static void setConfigattribute(LivingEntity entity, double hpconfig, double dmgconfig) {
      AttributeInstance maxHealthAttr = entity.getAttribute(Attributes.MAX_HEALTH);
      if (maxHealthAttr != null) {
         double difference = maxHealthAttr.getBaseValue() * hpconfig - maxHealthAttr.getBaseValue();
         maxHealthAttr.addTransientModifier(new AttributeModifier(HEALTHMMULTIPLER_ID, difference, Operation.ADD_VALUE));
         entity.setHealth(entity.getMaxHealth());
      }

      AttributeInstance attackDamageAttr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
      if (attackDamageAttr != null) {
         double difference = attackDamageAttr.getBaseValue() * dmgconfig - attackDamageAttr.getBaseValue();
         attackDamageAttr.addTransientModifier(new AttributeModifier(HEALTHMMULTIPLER_ID, difference, Operation.ADD_VALUE));
      }
   }

   public double getAngleBetweenEntities(Entity first, Entity second) {
      return Math.atan2(second.getZ() - first.getZ(), second.getX() - first.getX()) * (180.0 / Math.PI) + 90.0;
   }

   public static void disableShield(LivingEntity livingEntity, int ticks) {
      ((Player)livingEntity).getCooldowns().addCooldown(livingEntity.getUseItem().getItem(), ticks);
      livingEntity.stopUsingItem();
      livingEntity.level().broadcastEntityEvent(livingEntity, (byte)30);
   }

   protected final void tickDeath() {
      this.onDeathUpdate(this.deathtimer());
   }

   public int deathtimer() {
      return 20;
   }

   protected void onDeathAIUpdate() {
   }

   private void onDeathUpdate(int deathDuration) {
      this.onDeathAIUpdate();
      this.deathTime++;
      if (this.deathTime >= deathDuration && !this.level().isClientSide() && !this.isRemoved()) {
         this.lastHurtByPlayer = this.killDataAttackingPlayer;
         this.lastHurtByPlayerTime = this.killDataRecentlyHit;
         if (this.level() instanceof ServerLevel serverlevel && this.dropAfterDeathAnim && this.killDataCause != null) {
            this.dropAllDeathLoot(serverlevel, this.killDataCause);
         }

         this.level().broadcastEntityEvent(this, (byte)60);
         this.remove(RemovalReason.KILLED);
      }
   }

   public void die(DamageSource cause) {
      if (!CommonHooks.onLivingDeath(this, cause)) {
         if (!this.dead) {
            Entity entity = cause.getEntity();
            LivingEntity livingentity = this.getKillCredit();
            if (this.deathScore >= 0 && livingentity != null) {
               livingentity.awardKillScore(this, this.deathScore, cause);
            }

            if (this.isSleeping()) {
               this.stopSleeping();
            }

            this.dead = true;
            this.getCombatTracker().recheckStatus();
            if (this.level() instanceof ServerLevel serverlevel) {
               if (entity == null || entity.killedEntity(serverlevel, this)) {
                  this.gameEvent(GameEvent.ENTITY_DIE);
                  this.createWitherRose(livingentity);
                  if (!this.dropAfterDeathAnim) {
                     this.dropAllDeathLoot(serverlevel, cause);
                  }
               }

               this.level().broadcastEntityEvent(this, (byte)3);
            }

            this.killDataCause = cause;
            this.killDataRecentlyHit = this.lastHurtByPlayerTime;
            this.killDataAttackingPlayer = this.lastHurtByPlayer;
            this.setPose(Pose.DYING);
         }
      }
   }

   protected void repelEntities(float x, float y, float z, float radius) {
      for (Entity entity : this.getEntityLivingBaseNearby(x, y, z, radius)) {
         if (entity.isPickable() && !entity.noPhysics) {
            double angle = (this.getAngleBetweenEntities(this, entity) + 90.0) * Math.PI / 180.0;
            entity.setDeltaMovement(-0.1 * Math.cos(angle), entity.getDeltaMovement().y, -0.1 * Math.sin(angle));
         }
      }
   }

   public boolean canBePushedByEntity(Entity entity) {
      return true;
   }

   public void push(Entity entityIn) {
      if (!this.isSleeping() && !this.isPassengerOfSameVehicle(entityIn) && !entityIn.noPhysics && !this.noPhysics) {
         double d0 = entityIn.getX() - this.getX();
         double d1 = entityIn.getZ() - this.getZ();
         double d2 = Mth.absMax(d0, d1);
         if (d2 >= 0.01F) {
            d2 = Mth.sqrt((float)d2);
            d0 /= d2;
            d1 /= d2;
            double d3 = 1.0 / d2;
            if (d3 > 1.0) {
               d3 = 1.0;
            }

            d0 *= d3;
            d1 *= d3;
            d0 *= 0.05F;
            d1 *= 0.05F;
            if (!this.isVehicle() && this.canBePushedByEntity(entityIn)) {
               this.push(-d0, 0.0, -d1);
            }

            if (!entityIn.isVehicle()) {
               entityIn.push(d0, 0.0, d1);
            }
         }
      }
   }

   public List<LivingEntity> getEntityLivingBaseNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      return this.getEntitiesNearby(LivingEntity.class, distanceX, distanceY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double dX, double dY, double dZ, double r) {
      return this.level()
         .getEntitiesOfClass(
            entityClass,
            this.getBoundingBox().inflate(dX, dY, dZ),
            e -> e != this && this.distanceTo(e) <= r + e.getBbWidth() / 2.0F && e.getY() <= this.getY() + dY
         );
   }
}
