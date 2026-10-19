package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.config.mob.GauntletConfig;
import com.cerbon.bosses_of_mass_destruction.entity.damage.CompositeDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.damage.DamageMemory;
import com.cerbon.bosses_of_mass_destruction.entity.util.BaseEntity;
import com.cerbon.bosses_of_mass_destruction.entity.util.CompositeDataAccessorHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.CompositeEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.EffectsImmunity;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityBounds;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.cerbons_api.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.cerbons_api.api.static_utilities.MobUtils;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;

public class GauntletEntity extends BaseEntity implements MultipartAwareEntity {
   public final GauntletHitboxes hitboxHelper = new GauntletHitboxes(this);
   public final GauntletClientLaserHandler laserHandler = new GauntletClientLaserHandler(this, this.postTickEvents);
   public final GauntletClientEnergyShieldHandler energyShieldHandler = new GauntletClientEnergyShieldHandler(this, this.postTickEvents);
   public final GauntletBlindnessIndicatorParticles clientBlindnessHandler = new GauntletBlindnessIndicatorParticles(this, this.preTickEvents);
   public final DamageMemory damageMemory = new DamageMemory(5, this);
   private final AnimationHolder animationHandler;
   public static final EntityDataAccessor<Integer> laserTarget = SynchedEntityData.defineId(GauntletEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Boolean> isEnergized = SynchedEntityData.defineId(GauntletEntity.class, EntityDataSerializers.BOOLEAN);

   public GauntletEntity(EntityType<? extends PathfinderMob> entityType, Level level, GauntletConfig mobConfig) {
      super(entityType, level);
      GauntletGoalHandler gauntletGoalHandler = new GauntletGoalHandler(this, this.goalSelector, this.targetSelector, this.postTickEvents, mobConfig);
      this.animationHandler = new AnimationHolder(
         this,
         Map.of(
            (byte)4,
            new AnimationHolder.Animation("punch_start", "punch_loop"),
            (byte)5,
            new AnimationHolder.Animation("punch_stop", "idle"),
            (byte)7,
            new AnimationHolder.Animation("pound_stop", "idle"),
            (byte)8,
            new AnimationHolder.Animation("laser_eye_start", "laser_eye_loop"),
            (byte)9,
            new AnimationHolder.Animation("laser_eye_stop", "idle"),
            (byte)10,
            new AnimationHolder.Animation("swirl_punch", "idle"),
            (byte)11,
            new AnimationHolder.Animation("cast", "idle"),
            (byte)3,
            new AnimationHolder.Animation("death", "idle")
         ),
         (byte)6,
         0
      );
      this.noCulling = true;
      this.laserHandler.initDataTracker();
      this.energyShieldHandler.initDataTracker();
      this.damageHandler = new CompositeDamageHandler(this.hitboxHelper, gauntletGoalHandler, this.damageMemory);
      this.entityEventHandler = new CompositeEntityEventHandler(this.animationHandler, this.laserHandler, this.clientBlindnessHandler);
      this.dataAccessorHandler = new CompositeDataAccessorHandler(this.laserHandler, this.energyShieldHandler);
      this.clientTick = this.laserHandler;
      this.serverTick = serverLevel -> {
         if (this.getTarget() == null) {
            this.heal(mobConfig.idleHealingPerTick);
         }
      };
      this.bossBar = new ServerBossEvent(this.getDisplayName(), BossBarColor.RED, BossBarOverlay.NOTCHED_6);
      this.mobEffectHandler = new EffectsImmunity((MobEffect)MobEffects.WITHER.value(), (MobEffect)MobEffects.POISON.value());
      this.moveHandler = gauntletGoalHandler;
      this.nbtHandler = gauntletGoalHandler;
      this.deathClientTick = new ClientGauntletDeathHandler(this);
      this.deathServerTick = new ServerGauntletDeathHandler(this, CapabilityUtils.getLevelEventScheduler(level), mobConfig);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(laserTarget, 0);
      builder.define(isEnergized, false);
   }

   public void registerControllers(ControllerRegistrar data) {
      this.animationHandler.registerControllers(data);
   }

   protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
   }

   public void travel(@NotNull Vec3 travelVector) {
      VanillaCopiesServer.travel(travelVector, this, 0.85F);
   }

   public void setNextDamagedPart(@Nullable String part) {
      this.hitboxHelper.setNextDamagedPart(part);
   }

   public void onSetPos(double x, double y, double z) {
      if (this.hitboxHelper != null) {
         this.hitboxHelper.updatePosition();
      }
   }

   public boolean onClimbable() {
      return false;
   }

   public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
      return false;
   }

   public int getMaxHeadXRot() {
      return 90;
   }

   public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) {
      return this.hitboxHelper.getHitbox().getBox(bounds);
   }

   public EntityBounds getBounds() {
      return this.hitboxHelper.getHitbox();
   }

   public boolean isInWall() {
      return false;
   }

   public int getArmorValue() {
      return this.getTarget() != null ? super.getArmorValue() : 24;
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)BMDSounds.GAUNTLET_IDLE.get();
   }

   @Nullable
   protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
      return (SoundEvent)BMDSounds.GAUNTLET_HURT.get();
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return (SoundEvent)BMDSounds.GAUNTLET_DEATH.get();
   }

   protected float getSoundVolume() {
      return 2.0F;
   }

   public void checkDespawn() {
      MobUtils.preventDespawnExceptPeaceful(this, this.level());
   }
}
