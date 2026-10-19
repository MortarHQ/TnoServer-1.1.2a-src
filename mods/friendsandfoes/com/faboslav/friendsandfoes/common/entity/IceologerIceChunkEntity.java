package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import java.util.UUID;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class IceologerIceChunkEntity extends Entity {
   private static final String OWNER_UUID_NBT_NAME = "OwnerUuid";
   private static final String TARGET_UUID_NBT_NAME = "TargetUuid";
   private static final String TICKS_UNTIL_FALL_NBT_NAME = "TicksUntilFall";
   private static final String IDLE_TICKS_NBT_NAME = "IdleTicks";
   private static final int MIN_FLYING_TICKS = 60;
   private static final int MAX_FLYING_TICKS = 100;
   private static final int MIN_IDLE_TICKS = 10;
   private static final int MAX_IDLE_TICKS = 20;
   private static final int SUMMON_TICKS = 30;
   private static final EntityDataAccessor<Integer> TICKS_UNTIL_FALL = SynchedEntityData.defineId(IceologerIceChunkEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> IDLE_TICKS = SynchedEntityData.defineId(IceologerIceChunkEntity.class, EntityDataSerializers.INT);
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerUUID;
   @Nullable
   private LivingEntity target;
   @Nullable
   private UUID targetUUID;
   private int lifetimeTicks;
   private float summonAnimationProgress;
   private float lastSummonAnimationProgress;

   public IceologerIceChunkEntity(EntityType<? extends IceologerIceChunkEntity> entityType, Level world) {
      super(entityType, world);
      this.setInvulnerable(true);
      this.setNoGravity(true);
      this.lifetimeTicks = 0;
      this.summonAnimationProgress = 0.0F;
      this.lastSummonAnimationProgress = 0.0F;
      this.playSummonSound();
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(TICKS_UNTIL_FALL, RandomGenerator.generateInt(60, 100));
      builder.define(IDLE_TICKS, RandomGenerator.generateInt(10, 20));
   }

   public void addAdditionalSaveData(CompoundTag saveData) {
      VersionedNbt.putUUID(saveData, "OwnerUuid", this.getOwnerUuid());
      VersionedNbt.putUUID(saveData, "TargetUuid", this.getTargetUuid());
      saveData.putInt("TicksUntilFall", this.getTicksUntilFall());
      saveData.putInt("IdleTicks", this.getIdleTicks());
   }

   public void readAdditionalSaveData(CompoundTag saveData) {
      this.setOwnerUuid(VersionedNbt.getUUID(saveData, "OwnerUuid"));
      this.setTargetUuid(VersionedNbt.getUUID(saveData, "TargetUuid"));
      this.setTicksUntilFall(VersionedNbt.getInt(saveData, "TicksUntilFall", 100));
      this.setIdleTicks(VersionedNbt.getInt(saveData, "IdleTicks", 20));
   }

   @Nullable
   public UUID getOwnerUuid() {
      return this.ownerUUID;
   }

   public void setOwnerUuid(@Nullable UUID uuid) {
      this.ownerUUID = uuid;
   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel serverLevel) {
         Entity entity = serverLevel.getEntity(this.ownerUUID);
         if (entity instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
         }
      }

      return this.owner;
   }

   @Nullable
   public UUID getTargetUuid() {
      return this.targetUUID;
   }

   public void setTargetUuid(@Nullable UUID uuid) {
      this.targetUUID = uuid;
   }

   @Nullable
   public LivingEntity getTarget() {
      if (this.target == null && this.getTargetUuid() != null && !this.level().isClientSide()) {
         Entity entity = ((ServerLevel)this.level()).getEntity(this.getTargetUuid());
         if (entity instanceof LivingEntity) {
            this.target = (LivingEntity)entity;
         }
      }

      return this.target;
   }

   public void tick() {
      super.tick();
      if (this.lifetimeTicks == 10) {
         this.playSummonSound();
      } else if (this.lifetimeTicks == 40) {
         this.playAmbientSound();
      }

      this.lifetimeTicks++;
      this.setSummonAnimationProgress();
      if (this.getTarget() != null && !this.level().isClientSide()) {
         if (this.getTarget().isAlwaysTicking()) {
            Player playerTarget = (Player)this.getTarget();
            if (playerTarget.isSpectator() || playerTarget.isCreative()) {
               this.customDiscard();
               return;
            }
         }

         if (this.getTicksUntilFall() > 0) {
            this.moveTowardsTarget();
         }
      }

      if (this.getTicksUntilFall() > 0) {
         this.setTicksUntilFall(this.getTicksUntilFall() - 1);
      } else {
         this.push(0.0, -0.05F, 0.0);
         this.move(MoverType.SELF, this.getDeltaMovement());
         if (this.verticalCollision) {
            this.damageHitEntities();
            this.customDiscard();
         }
      }
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      return false;
   }

   public int getTicksUntilFall() {
      return (Integer)this.entityData.get(TICKS_UNTIL_FALL);
   }

   private void setTicksUntilFall(int ticksUntilFall) {
      this.entityData.set(TICKS_UNTIL_FALL, ticksUntilFall);
   }

   private int getIdleTicks() {
      return (Integer)this.entityData.get(IDLE_TICKS);
   }

   private void setIdleTicks(int idleTicks) {
      this.entityData.set(IDLE_TICKS, idleTicks);
   }

   private void damageHitEntities() {
      if (!this.level().isClientSide()) {
         for (LivingEntity hitEntity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2, 0.0, 0.2))) {
            this.damage(hitEntity);
         }
      }
   }

   private void damage(LivingEntity hitEntity) {
      LivingEntity livingEntity = this.getOwner();
      if (hitEntity.isAlive() && !hitEntity.isInvulnerable() && hitEntity != livingEntity && (livingEntity == null || !livingEntity.isAlliedTo(hitEntity))) {
         DamageSource damageSource;
         if (livingEntity == null) {
            damageSource = this.damageSources().magic();
         } else {
            damageSource = this.damageSources().indirectMagic(this, livingEntity);
         }

         VersionedEntity.hurt(hitEntity, damageSource, 12.0F);
         if (hitEntity.canFreeze()) {
            hitEntity.setTicksFrozen(400);
         }
      }
   }

   private void customDiscard() {
      this.playHitSound();
      this.spawnHitParticles();
      this.discard();
   }

   public void moveTowardsTarget() {
      LivingEntity target = this.getTarget();
      if (target != null && target.isAlive() && !(target.getDeltaMovement().lengthSqr() < 1.0E-4)) {
         Vec3 targetPosition = new Vec3(target.getX(), this.getYPositionWithHeightOffset(target.getY(), this.getTarget().getBbHeight()), target.getZ());
         Vec3 targetDirection = targetPosition.subtract(this.position()).normalize();
         this.setDeltaMovement(targetDirection.scale(0.2));
         this.move(MoverType.SELF, this.getDeltaMovement());
      }
   }

   private double getYPositionWithHeightOffset(double y, double height) {
      return Math.min(y + height * height, y + 6.0);
   }

   private SoundEvent getAmbientSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_ICE_CHUNK_AMBIENT.get();
   }

   private void playAmbientSound() {
      SoundEvent soundEvent = this.getAmbientSound();
      this.playSound(soundEvent, 1.0F, RandomGenerator.generateFloat(0.95F, 1.05F));
   }

   private SoundEvent getHitSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_ICE_CHUNK_HIT.get();
   }

   private void playHitSound() {
      SoundEvent soundEvent = this.getHitSound();
      this.playSound(soundEvent, 1.0F, RandomGenerator.generateFloat(0.95F, 1.05F));
   }

   private SoundEvent getSummonSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_ICE_CHUNK_SUMMON.get();
   }

   private void playSummonSound() {
      SoundEvent soundEvent = this.getSummonSound();
      this.playSound(soundEvent, 1.0F, RandomGenerator.generateFloat(0.95F, 1.05F));
   }

   public void spawnHitParticles() {
      this.spawnParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.BLUE_ICE.defaultBlockState()), 16);
   }

   private void spawnParticles(ParticleOptions particleEffect, int amount) {
      Level world = this.level();
      if (!world.isClientSide()) {
         for (int i = 0; i < amount; i++) {
            ((ServerLevel)world).sendParticles(particleEffect, this.getRandomX(0.5), this.getRandomY() + 0.5, this.getRandomZ(0.5), 1, 0.0, 0.0, 0.0, 0.0);
         }
      }
   }

   public float getSummonAnimationProgress() {
      return this.summonAnimationProgress;
   }

   public void setSummonAnimationProgress() {
      this.lastSummonAnimationProgress = this.summonAnimationProgress;
      this.summonAnimationProgress = Math.min(1.0F, this.lifetimeTicks / 30.0F);
   }

   public float getLastSummonAnimationProgress() {
      return this.lastSummonAnimationProgress;
   }

   public static IceologerIceChunkEntity createWithOwnerAndTarget(Level world, LivingEntity owner, LivingEntity target) {
      IceologerIceChunkEntity chunkEntity = new IceologerIceChunkEntity(
         (EntityType<? extends IceologerIceChunkEntity>)FriendsAndFoesEntityTypes.ICE_CHUNK.get(), world
      );
      chunkEntity.setOwnerUuid(owner.getUUID());
      chunkEntity.setTargetUuid(target.getUUID());
      chunkEntity.setPos(target.getX(), chunkEntity.getYPositionWithHeightOffset(target.getY(), target.getBbHeight()), target.getZ());
      return chunkEntity;
   }
}
