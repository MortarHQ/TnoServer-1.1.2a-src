package com.chronodawn.entities.bosses;

import com.chronodawn.entities.ai.TimeGuardianRangedAttackGoal;
import com.chronodawn.entities.projectiles.TimeBlastEntity;
import com.chronodawn.registry.ModSounds;
import com.chronodawn.worldgen.protection.BlockProtectionHandler;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TimeGuardianEntity extends Monster implements RangedAttackMob {
   private final ServerBossEvent bossEvent = new ServerBossEvent(this.getDisplayName(), BossBarColor.YELLOW, BossBarOverlay.PROGRESS);
   private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(TimeGuardianEntity.class, EntityDataSerializers.INT);
   public static final int PHASE_1 = 1;
   public static final int PHASE_2 = 2;
   private int teleportCooldown = 0;
   private int aoeCooldown = 0;
   private int postTeleportDelay = 0;
   private int rangedAttackCooldown = 0;
   public static final int TELEPORT_COOLDOWN_TICKS = 100;
   public static final int POST_TELEPORT_DELAY_TICKS = 15;
   public static final int AOE_COOLDOWN_TICKS = 80;
   public static final double AOE_RANGE = 4.0;
   public static final float AOE_DAMAGE = 6.0F;

   public TimeGuardianEntity(EntityType<? extends TimeGuardianEntity> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 100;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(PHASE, 1);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 200.0)
         .add(Attributes.ARMOR, 10.0)
         .add(Attributes.ATTACK_DAMAGE, 10.0)
         .add(Attributes.ATTACK_KNOCKBACK, 1.0)
         .add(Attributes.MOVEMENT_SPEED, 0.2)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new TimeGuardianRangedAttackGoal(this, 0.9, 100, 15.0F, 7.0F, 200));
      this.goalSelector.addGoal(2, new ExtendedMeleeAttackGoal(this, 1.0, false, 3.0));
      this.goalSelector.addGoal(3, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
      this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         this.updatePhase();
         if (this.rangedAttackCooldown > 0) {
            this.rangedAttackCooldown--;
         }

         if (this.getPhase() == 2) {
            this.handlePhase2Abilities();
         }
      }
   }

   public boolean doHurtTarget(Entity target) {
      boolean success = super.doHurtTarget(target);
      if (success && target instanceof LivingEntity livingTarget) {
         livingTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0));
      }

      return success;
   }

   private void updatePhase() {
      float healthPercent = this.getHealth() / this.getMaxHealth();
      int newPhase = healthPercent > 0.5F ? 1 : 2;
      if (newPhase != this.getPhase()) {
         this.setPhase(newPhase);
         if (newPhase == 2) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)ModSounds.BOSS_POWER_UP.get(), SoundSource.HOSTILE, 1.0F, 1.5F);
            if (this.level() instanceof ServerLevel serverLevel) {
               for (int i = 0; i < 30; i++) {
                  double offsetX = (this.random.nextDouble() - 0.5) * 2.0;
                  double offsetY = this.random.nextDouble() * 2.0;
                  double offsetZ = (this.random.nextDouble() - 0.5) * 2.0;
                  serverLevel.sendParticles(ParticleTypes.PORTAL, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 3, 0.0, 0.0, 0.0, 0.0);
               }
            }
         }
      }
   }

   private void handlePhase2Abilities() {
      if (this.teleportCooldown > 0) {
         this.teleportCooldown--;
      }

      if (this.aoeCooldown > 0) {
         this.aoeCooldown--;
      }

      if (this.postTeleportDelay > 0) {
         this.postTeleportDelay--;
      }

      if (this.teleportCooldown <= 0 && this.getTarget() != null && this.postTeleportDelay <= 0) {
         this.performTeleport();
         this.teleportCooldown = 100;
      }

      if (this.aoeCooldown <= 0 && this.getTarget() != null && this.postTeleportDelay <= 0) {
         this.performAoEAttack();
         this.aoeCooldown = 80;
      }
   }

   private void performTeleport() {
      LivingEntity target = this.getTarget();
      if (target != null) {
         Vec3 targetPos = target.position();
         Vec3 targetLook = target.getLookAngle();
         Vec3[] candidatePositions = new Vec3[]{
            targetPos.subtract(targetLook.scale(3.0)),
            targetPos.add(3.0, 0.0, 0.0),
            targetPos.add(-3.0, 0.0, 0.0),
            targetPos.add(0.0, 0.0, 3.0),
            targetPos.add(0.0, 0.0, -3.0),
            targetPos.add(2.0, 0.0, 2.0),
            targetPos.add(-2.0, 0.0, -2.0)
         };
         Vec3 validTeleportPos = null;

         for (Vec3 candidatePos : candidatePositions) {
            if (this.isSafeTeleportPosition(candidatePos)) {
               validTeleportPos = candidatePos;
               break;
            }
         }

         if (validTeleportPos != null) {
            if (this.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(ParticleTypes.PORTAL, this.getX(), this.getY() + 1.0, this.getZ(), 20, 0.5, 0.5, 0.5, 0.1);
               this.teleportTo(validTeleportPos.x, validTeleportPos.y, validTeleportPos.z);
               serverLevel.sendParticles(ParticleTypes.PORTAL, validTeleportPos.x, validTeleportPos.y + 1.0, validTeleportPos.z, 20, 0.5, 0.5, 0.5, 0.1);
            } else {
               this.teleportTo(validTeleportPos.x, validTeleportPos.y, validTeleportPos.z);
            }

            this.level()
               .playSound(
                  null, validTeleportPos.x, validTeleportPos.y, validTeleportPos.z, (SoundEvent)ModSounds.BOSS_TELEPORT.get(), SoundSource.HOSTILE, 1.0F, 1.0F
               );
            this.getNavigation().stop();
            this.postTeleportDelay = 15;
         }
      }
   }

   private boolean isSafeTeleportPosition(Vec3 pos) {
      BlockPos blockPos = BlockPos.containing(pos);
      double distanceFromSpawn = Math.sqrt(
         Math.pow(blockPos.getX() - this.blockPosition().getX(), 2.0) + Math.pow(blockPos.getZ() - this.blockPosition().getZ(), 2.0)
      );
      if (distanceFromSpawn > 20.0) {
         return false;
      }

      for (int xOffset = 0; xOffset <= 1; xOffset++) {
         for (int zOffset = 0; zOffset <= 1; zOffset++) {
            BlockPos basePos = blockPos.offset(xOffset, 0, zOffset);
            BlockPos groundPos = basePos.below();
            if (!this.level().getBlockState(groundPos).isSolid()) {
               return false;
            }

            for (int yOffset = 0; yOffset < 3; yOffset++) {
               BlockPos checkPos = basePos.above(yOffset);
               BlockState blockState = this.level().getBlockState(checkPos);
               if (blockState.isSolid()) {
                  return false;
               }
            }
         }
      }

      int wallCount = 0;
      BlockPos[] adjacentPositions = new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west()};

      for (BlockPos adjacentPos : adjacentPositions) {
         if (this.level().getBlockState(adjacentPos.above()).isSolid()) {
            wallCount++;
         }
      }

      return wallCount < 3;
   }

   private void performAoEAttack() {
      this.swing(InteractionHand.MAIN_HAND);
      AABB aoeBox = this.getBoundingBox().inflate(4.0);
      this.level().getEntitiesOfClass(Player.class, aoeBox).forEach(player -> {
         player.hurt(this.damageSources().mobAttack(this), 6.0F);
         player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 1));
         player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 80, 0));
      });
      if (this.level() instanceof ServerLevel serverLevel) {
         for (int i = 0; i < 100; i++) {
            double angle = (Math.PI * 2) * i / 100.0;
            double offsetX = Math.cos(angle) * 4.0;
            double offsetZ = Math.sin(angle) * 4.0;
            serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH, this.getX() + offsetX, this.getY() + 0.1, this.getZ() + offsetZ, 1, 0.0, 0.05, 0.0, 0.0);
            if (i % 5 == 0) {
               serverLevel.sendParticles(ParticleTypes.PORTAL, this.getX() + offsetX, this.getY() + 0.5, this.getZ() + offsetZ, 2, 0.0, 0.0, 0.0, 0.0);
            }
         }
      }

      this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)ModSounds.BOSS_CAST_SPELL.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
   }

   public int getPhase() {
      return (Integer)this.entityData.get(PHASE);
   }

   public void setPhase(int phase) {
      this.entityData.set(PHASE, phase);
   }

   public boolean isInPostTeleportDelay() {
      return this.postTeleportDelay > 0;
   }

   private boolean isPlayerOnBossFloor(ServerPlayer player) {
      double yDifference = player.getY() - this.getY();
      if (yDifference < -2.0) {
         return false;
      }

      if (yDifference > 5.0) {
         return false;
      }

      double horizontalDistance = Math.sqrt(Math.pow(player.getX() - this.getX(), 2.0) + Math.pow(player.getZ() - this.getZ(), 2.0));
      return !(horizontalDistance > 20.0);
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      if (this.isPlayerOnBossFloor(player)) {
         this.bossEvent.addPlayer(player);
      }
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
      if (this.level() instanceof ServerLevel serverLevel) {
         for (ServerPlayer player : serverLevel.players()) {
            boolean isOnBossFloor = this.isPlayerOnBossFloor(player);
            boolean hasPlayerInBossEvent = this.bossEvent.getPlayers().contains(player);
            if (isOnBossFloor && !hasPlayerInBossEvent) {
               this.bossEvent.addPlayer(player);
            } else if (!isOnBossFloor && hasPlayerInBossEvent) {
               this.bossEvent.removePlayer(player);
            }
         }
      }
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (this.hasCustomName()) {
         this.bossEvent.setName(this.getDisplayName());
      }

      this.entityData.set(PHASE, tag.getInt("Phase"));
      this.teleportCooldown = tag.getInt("TeleportCooldown");
      this.aoeCooldown = tag.getInt("AoeCooldown");
      this.postTeleportDelay = tag.getInt("PostTeleportDelay");
      this.rangedAttackCooldown = tag.getInt("RangedAttackCooldown");
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("Phase", this.getPhase());
      tag.putInt("TeleportCooldown", this.teleportCooldown);
      tag.putInt("AoeCooldown", this.aoeCooldown);
      tag.putInt("PostTeleportDelay", this.postTeleportDelay);
      tag.putInt("RangedAttackCooldown", this.rangedAttackCooldown);
   }

   public void setCustomName(Component name) {
      super.setCustomName(name);
      this.bossEvent.setName(this.getDisplayName());
   }

   public boolean canBeAffected(MobEffectInstance effect) {
      return super.canBeAffected(effect);
   }

   public void die(DamageSource cause) {
      super.die(cause);
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

   public int getRangedAttackCooldown() {
      return this.rangedAttackCooldown;
   }

   public void setRangedAttackCooldown(int ticks) {
      this.rangedAttackCooldown = ticks;
   }

   public void performRangedAttack(LivingEntity target, float velocity) {
      TimeBlastEntity timeBlast = new TimeBlastEntity(this.level(), this);
      double deltaX = target.getX() - this.getX();
      double deltaY = target.getY(0.3333333333333333) - timeBlast.getY();
      double deltaZ = target.getZ() - this.getZ();
      double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
      timeBlast.shoot(deltaX, deltaY + horizontalDistance * 0.2, deltaZ, 1.6F, 1.0F);
      this.level()
         .playSound(
            null,
            this.getX(),
            this.getY(),
            this.getZ(),
            (SoundEvent)ModSounds.BOSS_CAST_SPELL.get(),
            SoundSource.HOSTILE,
            1.0F,
            1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F
         );
      this.level().addFreshEntity(timeBlast);
      this.swing(InteractionHand.MAIN_HAND);
   }
}
