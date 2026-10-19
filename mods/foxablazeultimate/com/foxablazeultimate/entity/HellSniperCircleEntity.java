package com.foxablazeultimate.entity;

import com.foxablazeultimate.ability.skill.hellsniper.HellSniperSkill;
import com.foxablazeultimate.config.HellSniperConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateSounds;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class HellSniperCircleEntity extends Entity implements GeoEntity {
   public static final float APPEARANCE_ANIMATION_TICKS = 2.5F;
   public static final float LOGICAL_HITBOX_SIZE = 0.1F;
   private static final EntityDataAccessor<Optional<UUID>> OWNER_ID = SynchedEntityData.defineId(
      HellSniperCircleEntity.class, EntityDataSerializers.OPTIONAL_UUID
   );
   private static final EntityDataAccessor<Optional<UUID>> TARGET_ID = SynchedEntityData.defineId(
      HellSniperCircleEntity.class, EntityDataSerializers.OPTIONAL_UUID
   );
   private static final EntityDataAccessor<Long> CAST_START = SynchedEntityData.defineId(HellSniperCircleEntity.class, EntityDataSerializers.LONG);
   private static final EntityDataAccessor<Integer> APPEARANCE_TICKS = SynchedEntityData.defineId(HellSniperCircleEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> AIMING_TICKS = SynchedEntityData.defineId(HellSniperCircleEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> FIRING_WARMUP_TICKS = SynchedEntityData.defineId(HellSniperCircleEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> POST_FIRE_TICKS = SynchedEntityData.defineId(HellSniperCircleEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> APPEARANCE_DELAY_TICKS = SynchedEntityData.defineId(HellSniperCircleEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> FORMATION_APPEARANCE_TICKS = SynchedEntityData.defineId(
      HellSniperCircleEntity.class, EntityDataSerializers.INT
   );
   private static final EntityDataAccessor<Boolean> INFINITE_SNIPING = SynchedEntityData.defineId(HellSniperCircleEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Long> INFINITE_ATTACK_END = SynchedEntityData.defineId(HellSniperCircleEntity.class, EntityDataSerializers.LONG);
   private static final EntityDataAccessor<Float> AIM_PITCH = SynchedEntityData.defineId(HellSniperCircleEntity.class, EntityDataSerializers.FLOAT);
   private static final float PITCH_EASING = 0.35F;
   private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);
   private boolean fired;
   private boolean infiniteController;
   private boolean appearanceSoundPlayed;
   private boolean firingChargeSoundPlayed;
   private Vec3 infiniteCentre = Vec3.ZERO;
   private List<UUID> infiniteCircleIds = List.of();
   private float clientPitchPrev;
   private float clientPitch;
   private boolean clientPitchReady;

   public HellSniperCircleEntity(EntityType<? extends HellSniperCircleEntity> entityType, Level level) {
      super(entityType, level);
      this.setNoGravity(true);
      this.noPhysics = true;
      this.noCulling = true;
   }

   public HellSniperCircleEntity(EntityType<? extends HellSniperCircleEntity> entityType, Level level, LivingEntity owner, LivingEntity target) {
      this(entityType, level);
      this.initialize(owner, target);
   }

   protected void defineSynchedData(@NotNull Builder builder) {
      builder.define(OWNER_ID, Optional.empty());
      builder.define(TARGET_ID, Optional.empty());
      builder.define(CAST_START, 0L);
      builder.define(APPEARANCE_TICKS, 3);
      builder.define(AIMING_TICKS, 80);
      builder.define(FIRING_WARMUP_TICKS, 30);
      builder.define(POST_FIRE_TICKS, 6);
      builder.define(APPEARANCE_DELAY_TICKS, 0);
      builder.define(FORMATION_APPEARANCE_TICKS, 3);
      builder.define(INFINITE_SNIPING, false);
      builder.define(INFINITE_ATTACK_END, 0L);
      builder.define(AIM_PITCH, 0.0F);
   }

   protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
      if (tag.hasUUID("Owner")) {
         this.setOwnerId(tag.getUUID("Owner"));
      }

      if (tag.hasUUID("Target")) {
         this.setTargetId(tag.getUUID("Target"));
      }

      if (tag.contains("CastStart")) {
         this.setCastStart(tag.getLong("CastStart"));
      }

      if (tag.contains("AppearanceTicks")) {
         this.entityData.set(APPEARANCE_TICKS, Math.max(1, tag.getInt("AppearanceTicks")));
      }

      if (tag.contains("AimingTicks")) {
         this.entityData.set(AIMING_TICKS, Math.max(1, tag.getInt("AimingTicks")));
      }

      if (tag.contains("FiringWarmupTicks")) {
         this.entityData.set(FIRING_WARMUP_TICKS, Math.max(1, tag.getInt("FiringWarmupTicks")));
      }

      if (tag.contains("PostFireTicks")) {
         this.entityData.set(POST_FIRE_TICKS, Math.max(0, tag.getInt("PostFireTicks")));
      }

      if (tag.contains("AppearanceDelayTicks")) {
         this.entityData.set(APPEARANCE_DELAY_TICKS, Math.max(0, tag.getInt("AppearanceDelayTicks")));
      }

      if (tag.contains("FormationAppearanceTicks")) {
         this.entityData.set(FORMATION_APPEARANCE_TICKS, Math.max(1, tag.getInt("FormationAppearanceTicks")));
      }

      this.entityData.set(INFINITE_SNIPING, tag.getBoolean("InfiniteSniping"));
      if (tag.contains("InfiniteAttackEnd")) {
         this.entityData.set(INFINITE_ATTACK_END, Math.max(0L, tag.getLong("InfiniteAttackEnd")));
      }
   }

   protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
      this.getOwnerId().ifPresent(id -> tag.putUUID("Owner", id));
      this.getTargetId().ifPresent(id -> tag.putUUID("Target", id));
      tag.putLong("CastStart", this.getCastStart());
      tag.putInt("AppearanceTicks", this.getAppearanceDurationTicks());
      tag.putInt("AimingTicks", this.getAimingDurationTicks());
      tag.putInt("FiringWarmupTicks", this.getFiringWarmupTicks());
      tag.putInt("PostFireTicks", this.getPostFireDurationTicks());
      tag.putInt("AppearanceDelayTicks", this.getAppearanceDelayTicks());
      tag.putInt("FormationAppearanceTicks", this.getFormationAppearanceTicks());
      tag.putBoolean("InfiniteSniping", this.isInfiniteSniping());
      tag.putLong("InfiniteAttackEnd", this.getInfiniteAttackEnd());
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide()) {
         this.trackClientPitch();
      } else if (this.resolve(this.getOwnerId()) instanceof LivingEntity livingOwner && livingOwner.isAlive() && !livingOwner.isRemoved()) {
         this.playPhaseSounds();
         if (this.isInfiniteSniping()) {
            this.tickInfiniteSniping(livingOwner);
         } else {
            long age = this.getCastAge();
            if (age > (long)this.getFireAtTicks() + this.getPostFireDurationTicks()) {
               this.discard();
            } else if (!this.fired) {
               if (this.resolve(this.getTargetId()) instanceof LivingEntity livingTarget && HellSniperSkill.isValidLockedTarget(livingOwner, livingTarget)) {
                  this.updateSmoothAimRotation(livingTarget);
                  if (age >= this.getFireAtTicks()) {
                     this.fired = HellSniperSkill.releaseProjectile(livingOwner, livingTarget, this.position());
                     if (!this.fired) {
                        this.discard();
                     }
                  }
               } else {
                  this.discard();
               }
            }
         }
      } else {
         this.discard();
      }
   }

   private void playPhaseSounds() {
      long age = this.getCastAge();
      if (!this.appearanceSoundPlayed && age >= this.getAppearanceDelayTicks()) {
         this.appearanceSoundPlayed = true;
         this.level()
            .playSound(
               null,
               this.getX(),
               this.getY(),
               this.getZ(),
               (SoundEvent)FoxAblazeUltimateSounds.HELL_SNIPER_CIRCLE_APPEAR.get(),
               SoundSource.PLAYERS,
               1.0F,
               1.0F
            );
      }

      int firingPhaseStart = this.getFormationAppearanceTicks() + this.getAimingDurationTicks();
      boolean shouldPlayCharge = !this.isInfiniteSniping() || this.infiniteController;
      if (shouldPlayCharge && !this.firingChargeSoundPlayed && age >= firingPhaseStart) {
         this.firingChargeSoundPlayed = true;
         Vec3 soundPosition = this.isInfiniteSniping() ? this.infiniteCentre : this.position();
         this.level()
            .playSound(
               null,
               soundPosition.x,
               soundPosition.y,
               soundPosition.z,
               (SoundEvent)FoxAblazeUltimateSounds.HELL_SNIPER_FIRING_CHARGE.get(),
               SoundSource.PLAYERS,
               1.0F,
               1.0F
            );
      }
   }

   private void tickInfiniteSniping(LivingEntity owner) {
      long gameTime = this.level().getGameTime();
      long attackEnd = this.getInfiniteAttackEnd();
      if (attackEnd <= 0L || gameTime > attackEnd + this.getPostFireDurationTicks()) {
         this.discard();
      } else if (this.tickCount % 20 == 0 && !HellSniperSkill.ownsHellSniper(owner)) {
         if (this.infiniteController) {
            this.discardInfiniteFormation();
         } else {
            this.discard();
         }
      } else {
         if (this.resolve(this.getTargetId()) instanceof LivingEntity livingTarget && HellSniperSkill.isEligibleTarget(owner, livingTarget)) {
            this.updateInfiniteVerticalAim(livingTarget);
         } else {
            this.setTarget(null);
            this.updateInfiniteVerticalAim(null);
         }

         if (this.infiniteController) {
            long firstShot = this.getCastStart() + this.getFireAtTicks();
            if (gameTime >= firstShot && gameTime <= attackEnd) {
               int interval = Math.max(1, HellSniperConfig.settings().infiniteShotIntervalTicks);
               if ((gameTime - firstShot) % interval == 0L) {
                  List<HellSniperCircleEntity> circles = this.resolveInfiniteCircles();
                  if (circles.isEmpty()) {
                     this.discard();
                  } else {
                     HellSniperSkill.fireInfiniteVolley(owner, circles, this.infiniteCentre, (gameTime - firstShot) / interval);
                  }
               }
            }
         }
      }
   }

   private List<HellSniperCircleEntity> resolveInfiniteCircles() {
      if (this.level() instanceof ServerLevel server) {
         ArrayList var7 = new ArrayList(this.infiniteCircleIds.size());

         for (UUID id : this.infiniteCircleIds) {
            if (server.getEntity(id) instanceof HellSniperCircleEntity circle && circle.isInfiniteSniping() && !circle.isRemoved()) {
               var7.add(circle);
            }
         }

         return var7;
      } else {
         return List.of();
      }
   }

   private void discardInfiniteFormation() {
      for (HellSniperCircleEntity circle : this.resolveInfiniteCircles()) {
         circle.discard();
      }

      this.discard();
   }

   private long getCastAge() {
      long castStart = this.getCastStart();
      return castStart <= 0L ? this.tickCount : Math.max(0L, this.level().getGameTime() - castStart);
   }

   public int getFireAtTicks() {
      return this.getFormationAppearanceTicks() + this.getAimingDurationTicks() + this.getFiringWarmupTicks();
   }

   public boolean shouldBeSaved() {
      return false;
   }

   private Entity resolve(Optional<UUID> id) {
      return !id.isEmpty() && this.level() instanceof ServerLevel server ? server.getEntity(id.get()) : null;
   }

   public void setOwner(LivingEntity owner) {
      this.setOwnerId(owner == null ? null : owner.getUUID());
   }

   public void setTarget(LivingEntity target) {
      this.setTargetId(target == null ? null : target.getUUID());
   }

   public void initialize(LivingEntity owner, LivingEntity target) {
      this.setOwner(owner);
      this.setTarget(target);
      this.setPos(calculateLaunchPosition(owner, HellSniperConfig.settings().projectileLaunchDistance));
      this.updateAimRotation(target);
      this.setDeltaMovement(Vec3.ZERO);
      this.setCastStart(this.level().getGameTime());
      HellSniperConfig.HellSniperSettings config = HellSniperConfig.settings();
      this.entityData.set(APPEARANCE_TICKS, Math.max(1, config.appearanceDurationTicks));
      this.entityData.set(AIMING_TICKS, Math.max(1, config.aimingDurationTicks));
      this.entityData.set(FIRING_WARMUP_TICKS, Math.max(1, config.firingWarmupTicks));
      this.entityData.set(POST_FIRE_TICKS, Math.max(0, config.postFireDurationTicks));
      this.entityData.set(APPEARANCE_DELAY_TICKS, 0);
      this.entityData.set(FORMATION_APPEARANCE_TICKS, Math.max(1, config.appearanceDurationTicks));
   }

   public void initializeInfinite(
      LivingEntity owner, Vec3 position, Vec3 initialAim, long castStart, long attackEnd, int appearanceDelayTicks, int formationAppearanceTicks
   ) {
      this.setOwner(owner);
      this.setTarget(null);
      this.setPos(position);
      this.updateAimRotation(initialAim);
      this.setDeltaMovement(Vec3.ZERO);
      this.setCastStart(castStart);
      this.entityData.set(INFINITE_SNIPING, true);
      this.entityData.set(INFINITE_ATTACK_END, Math.max(castStart + 1L, attackEnd));
      HellSniperConfig.HellSniperSettings config = HellSniperConfig.settings();
      this.entityData.set(APPEARANCE_TICKS, Math.max(1, config.appearanceDurationTicks));
      this.entityData.set(AIMING_TICKS, Math.max(1, config.aimingDurationTicks));
      this.entityData.set(FIRING_WARMUP_TICKS, Math.max(1, config.firingWarmupTicks));
      this.entityData.set(POST_FIRE_TICKS, Math.max(0, config.postFireDurationTicks));
      this.entityData.set(APPEARANCE_DELAY_TICKS, Math.max(0, appearanceDelayTicks));
      this.entityData.set(FORMATION_APPEARANCE_TICKS, Math.max(1, formationAppearanceTicks));
   }

   public void configureInfiniteController(Vec3 centre, List<HellSniperCircleEntity> circles) {
      this.infiniteController = true;
      this.infiniteCentre = centre;
      this.infiniteCircleIds = circles.stream().<UUID>map(Entity::getUUID).toList();
   }

   public void trackInfiniteTarget(LivingEntity target) {
      this.setTarget(target);
   }

   private void updateAimRotation(LivingEntity target) {
      this.updateAimRotation(targetCenter(target));
   }

   private void updateAimRotation(Vec3 targetPosition) {
      Vec3 direction = targetPosition.subtract(this.position());
      if (Double.isFinite(direction.x) && Double.isFinite(direction.y) && Double.isFinite(direction.z) && !(direction.lengthSqr() < 1.0E-8)) {
         direction = direction.normalize();
         float yaw = (float)Math.toDegrees(-Math.atan2(direction.x, direction.z));
         float pitch = this.limitedPitch(direction);
         this.setYRot(yaw);
         this.setAimPitch(pitch);
      }
   }

   private void updateSmoothAimRotation(LivingEntity target) {
      Vec3 direction = targetCenter(target).subtract(this.position());
      if (isFiniteDirection(direction) && !(direction.lengthSqr() < 1.0E-8)) {
         direction = direction.normalize();
         float yaw = (float)Math.toDegrees(-Math.atan2(direction.x, direction.z));
         this.setYRot(yaw);
         this.setAimPitch(this.approachPitch(this.limitedPitch(direction)));
      }
   }

   private void updateInfiniteVerticalAim(LivingEntity target) {
      if (target == null) {
         this.setAimPitch(this.approachPitch(0.0F));
      } else {
         Vec3 direction = targetCenter(target).subtract(this.position());
         if (isFiniteDirection(direction) && !(direction.lengthSqr() < 1.0E-8)) {
            this.setAimPitch(this.approachPitch(this.limitedPitch(direction.normalize())));
         }
      }
   }

   private float limitedPitch(Vec3 direction) {
      float pitch = (float)Math.toDegrees(Math.asin(Mth.clamp(-direction.y, -1.0, 1.0)));
      double configuredLimit = HellSniperConfig.settings().verticalAimLimitDegrees;
      float limit = Double.isFinite(configuredLimit) ? (float)Math.max(0.0, Math.min(90.0, configuredLimit)) : 35.0F;
      return Mth.clamp(pitch, -limit, limit);
   }

   private float approachPitch(float desiredPitch) {
      double configuredStep = HellSniperConfig.settings().verticalAimStepDegrees;
      float maxStep = Double.isFinite(configuredStep) ? (float)Math.max(0.1, Math.min(45.0, configuredStep)) : 4.0F;
      float current = this.getAimPitch();
      float delta = desiredPitch - current;
      return Math.abs(delta) < 1.0E-4F ? desiredPitch : current + Mth.clamp(delta * 0.35F, -maxStep, maxStep);
   }

   private void setAimPitch(float pitch) {
      this.entityData.set(AIM_PITCH, pitch);
      this.setXRot(pitch);
   }

   public float getAimPitch() {
      return (Float)this.entityData.get(AIM_PITCH);
   }

   private void trackClientPitch() {
      float synced = this.getAimPitch();
      if (!this.clientPitchReady) {
         this.clientPitchPrev = synced;
         this.clientPitch = synced;
         this.clientPitchReady = true;
      } else {
         this.clientPitchPrev = this.clientPitch;
         this.clientPitch = synced;
      }
   }

   public float getRenderPitch(float partialTick) {
      return this.level().isClientSide() && this.clientPitchReady ? Mth.lerp(partialTick, this.clientPitchPrev, this.clientPitch) : this.getAimPitch();
   }

   private static boolean isFiniteDirection(Vec3 direction) {
      return Double.isFinite(direction.x) && Double.isFinite(direction.y) && Double.isFinite(direction.z);
   }

   private static Vec3 targetCenter(LivingEntity target) {
      return new Vec3(target.getX(), target.getY(0.5), target.getZ());
   }

   public static Vec3 calculateLaunchPosition(LivingEntity owner, double requestedDistance) {
      Vec3 look = owner.getLookAngle();
      if (!Double.isFinite(look.x) || !Double.isFinite(look.y) || !Double.isFinite(look.z) || look.lengthSqr() < 1.0E-8) {
         look = new Vec3(0.0, 0.0, 1.0);
      }

      double distance = Double.isFinite(requestedDistance) ? Math.max(0.0, requestedDistance) : 1.0;
      return owner.getEyePosition().add(look.normalize().scale(distance));
   }

   public Optional<UUID> getOwnerId() {
      return (Optional<UUID>)this.entityData.get(OWNER_ID);
   }

   public Optional<UUID> getTargetId() {
      return (Optional<UUID>)this.entityData.get(TARGET_ID);
   }

   private void setOwnerId(UUID id) {
      this.entityData.set(OWNER_ID, Optional.ofNullable(id));
   }

   private void setTargetId(UUID id) {
      this.entityData.set(TARGET_ID, Optional.ofNullable(id));
   }

   public long getCastStart() {
      return (Long)this.entityData.get(CAST_START);
   }

   public void setCastStart(long tick) {
      this.entityData.set(CAST_START, tick);
   }

   public int getAppearanceDurationTicks() {
      return (Integer)this.entityData.get(APPEARANCE_TICKS);
   }

   public int getAimingDurationTicks() {
      return (Integer)this.entityData.get(AIMING_TICKS);
   }

   public int getFiringWarmupTicks() {
      return (Integer)this.entityData.get(FIRING_WARMUP_TICKS);
   }

   public int getPostFireDurationTicks() {
      return (Integer)this.entityData.get(POST_FIRE_TICKS);
   }

   public int getAppearanceDelayTicks() {
      return (Integer)this.entityData.get(APPEARANCE_DELAY_TICKS);
   }

   public int getFormationAppearanceTicks() {
      return (Integer)this.entityData.get(FORMATION_APPEARANCE_TICKS);
   }

   public boolean isInfiniteSniping() {
      return (Boolean)this.entityData.get(INFINITE_SNIPING);
   }

   public long getInfiniteAttackEnd() {
      return (Long)this.entityData.get(INFINITE_ATTACK_END);
   }

   public float getAnimationAge(float partialTick) {
      long castStart = this.getCastStart();
      float globalAge = castStart <= 0L ? this.tickCount + partialTick : (float)(this.level().getGameTime() - castStart) + partialTick;
      return Math.max(0.0F, globalAge);
   }

   public double getTick(Object entity) {
      return this.getAnimationAge(0.0F);
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.animationCache;
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(@NotNull ServerEntity entity) {
      Entity owner = this.resolve(this.getOwnerId());
      return new ClientboundAddEntityPacket(this, entity, owner == null ? 0 : owner.getId());
   }
}
