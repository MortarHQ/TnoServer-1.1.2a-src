package com.chronodawn.entities.bosses;

import com.chronodawn.entities.ai.GroundSlamGoal;
import com.chronodawn.registry.ModSounds;
import com.chronodawn.worldgen.protection.BlockProtectionHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ChronosWardenEntity extends Monster {
   private final ServerBossEvent bossEvent = new ServerBossEvent(this.getDisplayName(), BossBarColor.BLUE, BossBarOverlay.PROGRESS);
   private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(ChronosWardenEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> IN_STONE_STANCE = SynchedEntityData.defineId(ChronosWardenEntity.class, EntityDataSerializers.BOOLEAN);
   private static final int PHASE_1 = 1;
   private static final int PHASE_2 = 2;
   private int groundSlamCooldown = 0;
   private int stoneStanceCooldown = 0;
   private int stoneStanceDuration = 0;
   private static final int GROUND_SLAM_COOLDOWN_PHASE1 = 200;
   private static final int GROUND_SLAM_COOLDOWN_PHASE2 = 140;
   private static final double GROUND_SLAM_RANGE = 4.0;
   private static final float GROUND_SLAM_DAMAGE = 2.0F;
   private static final int STONE_STANCE_COOLDOWN_TICKS = 400;
   private static final int STONE_STANCE_DURATION_TICKS = 100;
   private static final float STONE_STANCE_DAMAGE_REDUCTION = 0.8F;

   public ChronosWardenEntity(EntityType<? extends ChronosWardenEntity> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 80;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(PHASE, 1);
      builder.define(IN_STONE_STANCE, false);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 180.0)
         .add(Attributes.ARMOR, 12.0)
         .add(Attributes.ATTACK_DAMAGE, 9.0)
         .add(Attributes.ATTACK_KNOCKBACK, 1.0)
         .add(Attributes.MOVEMENT_SPEED, 0.2)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new GroundSlamGoal(this, 2.0, 6.0));
      this.goalSelector.addGoal(2, new ExtendedMeleeAttackGoal(this, 1.0, false, 3.0));
      this.goalSelector.addGoal(3, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
      this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         this.updatePhase();
         if (this.groundSlamCooldown > 0) {
            this.groundSlamCooldown--;
         }

         if (this.stoneStanceCooldown > 0) {
            this.stoneStanceCooldown--;
         }

         if (this.isInStoneStance()) {
            this.stoneStanceDuration--;
            if (this.stoneStanceDuration <= 0) {
               this.exitStoneStance();
            }

            if (this.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY() + 1.0, this.getZ(), 2, 0.5, 0.5, 0.5, 0.01);
            }
         }

         if (this.getPhase() == 2) {
            this.handlePhase2Abilities();
         }
      }
   }

   private void updatePhase() {
      float healthPercentage = this.getHealth() / this.getMaxHealth();
      int newPhase = healthPercentage > 0.6F ? 1 : 2;
      if (newPhase != this.getPhase()) {
         this.setPhase(newPhase);
         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, this.blockPosition(), (SoundEvent)ModSounds.WARDEN_ROAR.get(), SoundSource.HOSTILE, 1.0F, 0.8F);
         }
      }
   }

   private void handlePhase2Abilities() {
      if (!this.isInStoneStance() && this.stoneStanceCooldown == 0 && this.getTarget() != null) {
         this.enterStoneStance();
      }
   }

   private void enterStoneStance() {
      this.entityData.set(IN_STONE_STANCE, true);
      this.stoneStanceDuration = 100;
      this.stoneStanceCooldown = 400;
      this.level().playSound(null, this.blockPosition(), (SoundEvent)ModSounds.TIME_NEEDLE_BREAK.get(), SoundSource.HOSTILE, 1.0F, 0.5F);
      this.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0, false, false));
      this.getNavigation().stop();
      this.setDeltaMovement(Vec3.ZERO);
   }

   private void exitStoneStance() {
      this.entityData.set(IN_STONE_STANCE, false);
      this.level().playSound(null, this.blockPosition(), (SoundEvent)ModSounds.TIME_NEEDLE_PLACE.get(), SoundSource.HOSTILE, 1.0F, 0.8F);
   }

   public boolean isInStoneStance() {
      return (Boolean)this.entityData.get(IN_STONE_STANCE);
   }

   public boolean doHurtTarget(Entity target) {
      if (this.isInStoneStance()) {
         return false;
      }

      boolean success = super.doHurtTarget(target);
      if (success && target instanceof LivingEntity livingTarget) {
         livingTarget.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 1));
      }

      return success;
   }

   public boolean hurt(DamageSource source, float amount) {
      if (this.isInStoneStance()) {
         if (source.getEntity() instanceof LivingEntity attacker) {
            Vec3 attackerPos = attacker.position();
            Vec3 myPos = this.position();
            Vec3 toAttacker = attackerPos.subtract(myPos).normalize();
            Vec3 myLookVec = this.getLookAngle();
            double dot = myLookVec.dot(toAttacker);
            if (dot < 0.0) {
               return super.hurt(source, amount);
            }
         }

         amount *= 0.19999999F;
      }

      return super.hurt(source, amount);
   }

   public void performGroundSlam() {
      if (!this.level().isClientSide && !this.isInStoneStance()) {
         this.level().playSound(null, this.blockPosition(), (SoundEvent)ModSounds.BOSS_POWER_UP.get(), SoundSource.HOSTILE, 0.5F, 0.8F);
         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY() + 0.1, this.getZ(), 8, 2.0, 0.1, 2.0, 0.0);
         }

         AABB area = new AABB(this.blockPosition()).inflate(4.0);
         this.level().getEntitiesOfClass(LivingEntity.class, area).forEach(entity -> {
            if (entity != this && entity instanceof Player) {
               entity.hurt(this.damageSources().mobAttack(this), 2.0F);
               Vec3 direction = entity.position().subtract(this.position()).normalize();
               entity.setDeltaMovement(entity.getDeltaMovement().add(direction.x * 0.5, 0.3, direction.z * 0.5));
               entity.hurtMarked = true;
            }
         });
         int cooldown = this.getPhase() == 1 ? 200 : 140;
         this.groundSlamCooldown = cooldown;
      }
   }

   public boolean canGroundSlam() {
      return this.groundSlamCooldown == 0 && !this.isInStoneStance();
   }

   public int getPhase() {
      return (Integer)this.entityData.get(PHASE);
   }

   private void setPhase(int phase) {
      this.entityData.set(PHASE, phase);
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossEvent.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   public void aiStep() {
      super.aiStep();
      this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (this.hasCustomName()) {
         this.bossEvent.setName(this.getDisplayName());
      }

      this.entityData.set(PHASE, compound.getInt("Phase"));
      this.entityData.set(IN_STONE_STANCE, compound.getBoolean("InStoneStance"));
      this.groundSlamCooldown = compound.getInt("GroundSlamCooldown");
      this.stoneStanceCooldown = compound.getInt("StoneStanceCooldown");
      this.stoneStanceDuration = compound.getInt("StoneStanceDuration");
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("Phase", this.getPhase());
      compound.putBoolean("InStoneStance", this.isInStoneStance());
      compound.putInt("GroundSlamCooldown", this.groundSlamCooldown);
      compound.putInt("StoneStanceCooldown", this.stoneStanceCooldown);
      compound.putInt("StoneStanceDuration", this.stoneStanceDuration);
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.isInStoneStance()) {
         this.getNavigation().stop();
         this.setDeltaMovement(Vec3.ZERO);
      }
   }

   public void die(DamageSource source) {
      super.die(source);
      if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
         BlockProtectionHandler.onBossDefeatedAt(serverLevel, this.blockPosition());
      }
   }

   public boolean isPersistenceRequired() {
      return true;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }
}
