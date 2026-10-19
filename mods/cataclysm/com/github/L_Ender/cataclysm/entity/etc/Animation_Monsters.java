package com.github.L_Ender.cataclysm.entity.etc;

import com.github.L_Ender.cataclysm.message.MessageMusic;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.network.PacketDistributor;

public class Animation_Monsters extends Monster implements Enemy {
   private static final ResourceLocation MOB_HEALTH_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("cataclysm", "animation_health");
   private static final ResourceLocation MOB_DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("cataclysm", "animation_damage");
   public int attackTicks;
   @OnlyIn(Dist.CLIENT)
   public Vec3[] socketPosArray;

   public Animation_Monsters(EntityType entity, Level world) {
      super(entity, world);
      if (world.isClientSide) {
         this.socketPosArray = new Vec3[0];
      }
   }

   protected void defineSynchedData(Builder p_326229_) {
      super.defineSynchedData(p_326229_);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide && this.getBossMusic() != null) {
         if (this.canPlayMusic()) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(this, new MessageMusic(this.getId(), true), new CustomPacketPayload[0]);
         } else {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(this, new MessageMusic(this.getId(), false), new CustomPacketPayload[0]);
         }
      }
   }

   public void handleEntityEvent(byte id) {
      super.handleEntityEvent(id);
   }

   public boolean canPlayerHearMusic(Player player) {
      return player != null && this.canAttack(player) && this.distanceTo(player) < 2500.0F;
   }

   public void setConfigattribute(LivingEntity entity, double hpconfig, double dmgconfig) {
      AttributeInstance maxHealthAttr = entity.getAttribute(Attributes.MAX_HEALTH);
      if (maxHealthAttr != null) {
         double difference = maxHealthAttr.getBaseValue() * hpconfig - maxHealthAttr.getBaseValue();
         maxHealthAttr.addTransientModifier(new AttributeModifier(MOB_HEALTH_MODIFIER_ID, difference, Operation.ADD_VALUE));
         entity.setHealth(entity.getMaxHealth());
      }

      AttributeInstance attackDamageAttr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
      if (attackDamageAttr != null) {
         double difference = attackDamageAttr.getBaseValue() * dmgconfig - attackDamageAttr.getBaseValue();
         attackDamageAttr.addTransientModifier(new AttributeModifier(MOB_DAMAGE_MODIFIER_ID, difference, Operation.ADD_VALUE));
      }
   }

   public double calculateRange(DamageSource damagesource) {
      return damagesource.getEntity() != null ? this.distanceToSqr(damagesource.getEntity()) : -1.0;
   }

   public double getAngleBetweenEntities(Entity first, Entity second) {
      return Math.atan2(second.getZ() - first.getZ(), second.getX() - first.getX()) * (180.0 / Math.PI) + 90.0;
   }

   protected boolean canPlayMusic() {
      return !this.isSilent() && this.getTarget() instanceof Player && this.getTarget() != null && this.getTarget().isAlive();
   }

   protected void tickDeath() {
      this.onDeathUpdate(this.deathtimer());
   }

   public SoundEvent getBossMusic() {
      return null;
   }

   public int deathtimer() {
      return 20;
   }

   protected void onDeathAIUpdate() {
   }

   public void onDeathUpdate(int deathDuration) {
      this.onDeathAIUpdate();
      this.deathTime++;
      if (this.deathTime >= deathDuration && !this.level().isClientSide() && !this.isRemoved()) {
         this.level().broadcastEntityEvent(this, (byte)60);
         this.remove(RemovalReason.KILLED);
      }
   }

   public void die(DamageSource cause) {
      if (!CommonHooks.onLivingDeath(this, cause)) {
         if (!this.isRemoved() && !this.dead) {
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
                  this.dropAllDeathLoot(serverlevel, cause);
                  this.createWitherRose(livingentity);
                  this.AfterDefeatBoss(livingentity);
               }

               this.level().broadcastEntityEvent(this, (byte)3);
            }

            this.setPose(Pose.DYING);
         }
      }
   }

   protected void AfterDefeatBoss(@Nullable LivingEntity p_21269_) {
   }

   public void circleEntity(Entity target, float radius, float speed, boolean direction, int circleFrame, float offset, float moveSpeedMultiplier) {
      int directionInt = direction ? 1 : -1;
      double t = directionInt * circleFrame * 0.5 * speed / radius + offset;
      Vec3 movePos = target.position().add(radius * Math.cos(t), 0.0, radius * Math.sin(t));
      this.getNavigation().moveTo(movePos.x, movePos.y, movePos.z, speed * moveSpeedMultiplier);
   }

   protected void repelEntities(float x, float y, float z, float radius) {
      for (Entity entity : this.getEntityLivingBaseNearby(x, y, z, radius)) {
         if (entity.isPickable() && !entity.noPhysics) {
            double angle = (this.getAngleBetweenEntities(this, entity) + 90.0) * Math.PI / 180.0;
            entity.setDeltaMovement(-0.1 * Math.cos(angle), entity.getDeltaMovement().y, -0.1 * Math.sin(angle));
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void setSocketPosArray(int index, Vec3 pos) {
      if (this.socketPosArray != null && this.socketPosArray.length > index) {
         this.socketPosArray[index] = pos;
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
