package com.chronodawn.entities.bosses;

import com.chronodawn.entities.projectiles.GearProjectileEntity;
import com.chronodawn.registry.ModSounds;
import com.chronodawn.worldgen.protection.BlockProtectionHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class ClockworkColossusEntity extends Monster implements RangedAttackMob {
   private final ServerBossEvent bossEvent = new ServerBossEvent(this.getDisplayName(), BossBarColor.RED, BossBarOverlay.PROGRESS);
   private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(ClockworkColossusEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> OVERCHARGED = SynchedEntityData.defineId(ClockworkColossusEntity.class, EntityDataSerializers.BOOLEAN);
   private static final int PHASE_1 = 1;
   private static final int PHASE_2 = 2;
   private int gearShotCooldown = 0;
   private int groundSlamCooldown = 0;
   private boolean repairProtocolUsed = false;
   private int repairCooldown = 0;
   private static final int GEAR_SHOT_COOLDOWN_TICKS = 160;
   private static final int GROUND_SLAM_COOLDOWN_TICKS = 200;
   private static final int GROUND_SLAM_RANGE = 10;
   private static final int GROUND_SLAM_WIDTH = 3;
   private static final float GROUND_SLAM_DAMAGE = 6.0F;
   private static final float REPAIR_PROTOCOL_THRESHOLD = 0.4F;
   private static final float REPAIR_PROTOCOL_HEAL = 30.0F;
   private static final int REPAIR_PROTOCOL_COOLDOWN = 3600;

   public ClockworkColossusEntity(EntityType<? extends ClockworkColossusEntity> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 100;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(PHASE, 1);
      builder.define(OVERCHARGED, false);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 200.0)
         .add(Attributes.ARMOR, 8.0)
         .add(Attributes.ATTACK_DAMAGE, 12.0)
         .add(Attributes.ATTACK_KNOCKBACK, 1.5)
         .add(Attributes.MOVEMENT_SPEED, 0.18)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(4, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         this.updatePhase();
         if (this.gearShotCooldown > 0) {
            this.gearShotCooldown--;
         }

         if (this.groundSlamCooldown > 0) {
            this.groundSlamCooldown--;
         }

         if (this.repairCooldown > 0) {
            this.repairCooldown--;
         }

         if (this.getPhase() == 2) {
            this.handlePhase2Abilities();
         }

         if (this.getTarget() != null && this.gearShotCooldown <= 0) {
            double distance = this.distanceTo(this.getTarget());
            if (distance >= 5.0 && distance <= 16.0) {
               this.performGearShot(this.getTarget());
               this.gearShotCooldown = 160;
            }
         }
      }
   }

   private void updatePhase() {
      float healthPercent = this.getHealth() / this.getMaxHealth();
      int newPhase = healthPercent > 0.5F ? 1 : 2;
      if (newPhase != this.getPhase()) {
         this.setPhase(newPhase);
         if (newPhase == 2 && !this.isOvercharged()) {
            this.activateOvercharge();
         }
      }
   }

   private void activateOvercharge() {
      this.entityData.set(OVERCHARGED, true);
      this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 999999, 1));
      this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)ModSounds.COLOSSUS_ACTIVATE.get(), SoundSource.HOSTILE, 1.0F, 1.2F);
      if (this.level() instanceof ServerLevel serverLevel) {
         for (int i = 0; i < 50; i++) {
            double offsetX = (this.random.nextDouble() - 0.5) * 2.0;
            double offsetY = this.random.nextDouble() * 2.5;
            double offsetZ = (this.random.nextDouble() - 0.5) * 2.0;
            serverLevel.sendParticles(ParticleTypes.FLAME, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 5, 0.0, 0.0, 0.0, 0.05);
         }
      }
   }

   private void handlePhase2Abilities() {
      float healthPercent = this.getHealth() / this.getMaxHealth();
      if (!this.repairProtocolUsed && healthPercent <= 0.4F && this.repairCooldown <= 0) {
         this.performRepairProtocol();
         this.repairProtocolUsed = true;
         this.repairCooldown = 3600;
      }

      if (this.getTarget() != null && this.groundSlamCooldown <= 0) {
         double distance = this.distanceTo(this.getTarget());
         if (distance <= 8.0) {
            this.performGroundSlam();
            this.groundSlamCooldown = 200;
         }
      }
   }

   private void performRepairProtocol() {
      this.heal(30.0F);
      this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)ModSounds.COLOSSUS_STOMP.get(), SoundSource.HOSTILE, 1.0F, 1.5F);
      if (this.level() instanceof ServerLevel serverLevel) {
         for (int i = 0; i < 30; i++) {
            double offsetX = (this.random.nextDouble() - 0.5) * 2.0;
            double offsetY = this.random.nextDouble() * 2.5;
            double offsetZ = (this.random.nextDouble() - 0.5) * 2.0;
            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 3, 0.0, 0.0, 0.0, 0.0);
         }
      }
   }

   private void performGroundSlam() {
      if (this.getTarget() != null) {
         this.swing(InteractionHand.MAIN_HAND);
         LivingEntity target = this.getTarget();
         double deltaX = target.getX() - this.getX();
         double deltaZ = target.getZ() - this.getZ();
         double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
         if (distance != 0.0) {
            double dirX = deltaX / distance;
            double dirZ = deltaZ / distance;
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)ModSounds.BOSS_POWER_UP.get(), SoundSource.HOSTILE, 0.8F, 0.6F);

            for (int range = 1; range <= 10; range++) {
               double centerX = this.getX() + dirX * range;
               double centerZ = this.getZ() + dirZ * range;
               double perpX = -dirZ;
               double perpZ = dirX;

               for (int width = -1; width <= 1; width++) {
                  double checkX = centerX + perpX * width;
                  double checkZ = centerZ + perpZ * width;
                  this.level()
                     .getEntitiesOfClass(Player.class, new AABB(checkX - 0.5, this.getY() - 1.0, checkZ - 0.5, checkX + 0.5, this.getY() + 3.0, checkZ + 0.5))
                     .forEach(player -> {
                        player.hurt(this.damageSources().mobAttack(this), 6.0F);
                        player.knockback(0.3, -dirX, -dirZ);
                     });
                  if (this.level() instanceof ServerLevel serverLevel) {
                     serverLevel.sendParticles(ParticleTypes.SMOKE, checkX, this.getY() + 0.1, checkZ, 3, 0.2, 0.1, 0.2, 0.01);
                  }
               }
            }
         }
      }
   }

   private void performGearShot(LivingEntity target) {
      GearProjectileEntity gearProjectile = new GearProjectileEntity(this.level(), this);
      double deltaX = target.getX() - this.getX();
      double deltaY = target.getY(0.3333333333333333) - gearProjectile.getY();
      double deltaZ = target.getZ() - this.getZ();
      double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
      gearProjectile.shoot(deltaX, deltaY + horizontalDistance * 0.1, deltaZ, 1.5F, 1.0F);
      this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)ModSounds.GEAR_LAUNCH.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
      this.level().addFreshEntity(gearProjectile);
      this.swing(InteractionHand.MAIN_HAND);
   }

   public void performRangedAttack(LivingEntity target, float velocity) {
      this.performGearShot(target);
   }

   public int getPhase() {
      return (Integer)this.entityData.get(PHASE);
   }

   public void setPhase(int phase) {
      this.entityData.set(PHASE, phase);
   }

   public boolean isOvercharged() {
      return (Boolean)this.entityData.get(OVERCHARGED);
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossEvent.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (this.hasCustomName()) {
         this.bossEvent.setName(this.getDisplayName());
      }

      this.entityData.set(PHASE, tag.getInt("Phase"));
      this.entityData.set(OVERCHARGED, tag.getBoolean("Overcharged"));
      this.repairProtocolUsed = tag.getBoolean("RepairProtocolUsed");
      this.gearShotCooldown = tag.getInt("GearShotCooldown");
      this.groundSlamCooldown = tag.getInt("GroundSlamCooldown");
      this.repairCooldown = tag.getInt("RepairCooldown");
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("Phase", this.getPhase());
      tag.putBoolean("Overcharged", this.isOvercharged());
      tag.putBoolean("RepairProtocolUsed", this.repairProtocolUsed);
      tag.putInt("GearShotCooldown", this.gearShotCooldown);
      tag.putInt("GroundSlamCooldown", this.groundSlamCooldown);
      tag.putInt("RepairCooldown", this.repairCooldown);
   }

   public void setCustomName(Component name) {
      super.setCustomName(name);
      this.bossEvent.setName(this.getDisplayName());
   }

   public boolean canBeAffected(MobEffectInstance effect) {
      return effect.getEffect() == MobEffects.MOVEMENT_SLOWDOWN ? false : super.canBeAffected(effect);
   }

   public boolean isPersistenceRequired() {
      return true;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public void die(DamageSource cause) {
      super.die(cause);
      if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
         BlockProtectionHandler.onBossDefeatedAt(serverLevel, this.blockPosition());
      }
   }
}
