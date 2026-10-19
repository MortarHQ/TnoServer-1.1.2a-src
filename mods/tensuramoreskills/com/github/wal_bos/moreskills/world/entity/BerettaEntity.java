package com.github.wal_bos.moreskills.world.entity;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.world.entity.beretta.BerettaArenaHandler;
import com.github.wal_bos.moreskills.world.entity.beretta.BerettaCombat;
import com.github.wal_bos.moreskills.world.entity.beretta.BerettaDefense;
import com.mojang.datafixers.util.Pair;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.magic.AspectualMagics;
import io.github.manasmods.tensura.registry.magic.SpiritualMagics;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BerettaEntity extends PathfinderMob implements GeoEntity, SmartBrainOwner<BerettaEntity> {
   private static final EntityDataAccessor<Integer> DATA_ATTACK_ID = SynchedEntityData.defineId(BerettaEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_ATTACK_TICKS = SynchedEntityData.defineId(BerettaEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_ATTACK_SEED = SynchedEntityData.defineId(BerettaEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Float> DATA_ORIGIN_X = SynchedEntityData.defineId(BerettaEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> DATA_ORIGIN_Y = SynchedEntityData.defineId(BerettaEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> DATA_ORIGIN_Z = SynchedEntityData.defineId(BerettaEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> DATA_ANCHOR_X = SynchedEntityData.defineId(BerettaEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> DATA_ANCHOR_Y = SynchedEntityData.defineId(BerettaEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> DATA_ANCHOR_Z = SynchedEntityData.defineId(BerettaEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> DATA_DIR_X = SynchedEntityData.defineId(BerettaEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> DATA_DIR_Z = SynchedEntityData.defineId(BerettaEntity.class, EntityDataSerializers.FLOAT);
   private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("Idle");
   private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
   private static final RawAnimation DEATH = RawAnimation.begin().thenPlayAndHold("Death");
   private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);
   private final ServerBossEvent bossEvent = new ServerBossEvent(
      Component.translatable("entity.tensuramoreskills.beretta"), BossBarColor.PURPLE, BossBarOverlay.NOTCHED_10
   );
   private UUID lockedTargetId;
   private int globalCooldown = 30;
   private int lastAttackId;
   private int previousAttackId;
   private int attacksSinceCore;
   private int openingStep;
   private boolean coreQueued;
   private boolean skillsGranted;
   private int evasiveTicks;

   private static EntityConfigs.BerettaConfig cfg() {
      return EntityConfigs.ENTITY.beretta;
   }

   public BerettaEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
      BerettaArenaHandler.init();
      if ((Boolean)cfg().persistent.get()) {
         this.setPersistenceRequired();
      }

      this.setNoGravity(false);
      this.xpReward = (Integer)cfg().xpReward.get();
      this.bossEvent.setDarkenScreen(false);
      this.bossEvent.setPlayBossMusic(true);
      this.bossEvent.setCreateWorldFog(false);
      this.applyConfiguredAttributes();
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 48000.0)
         .add(Attributes.ARMOR, 52.0)
         .add(Attributes.ARMOR_TOUGHNESS, 34.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.MOVEMENT_SPEED, 0.31)
         .add(Attributes.ATTACK_DAMAGE, 480.0)
         .add(Attributes.FOLLOW_RANGE, 88.0)
         .add(TensuraAttributes.MAX_AURA, 360000.0)
         .add(TensuraAttributes.MAX_MAGICULE, 520000.0)
         .add(TensuraAttributes.MAX_SPIRITUAL_HEALTH, 260000.0)
         .add(TensuraAttributes.PRESENCE_SENSE, 5.0)
         .add(TensuraAttributes.PRESENCE_SENSE_RADIUS, 104.0)
         .add(TensuraAttributes.DARKNESS_BOOST, 6.0)
         .add(TensuraAttributes.DARKNESS_RESISTANCE, 0.78)
         .add(TensuraAttributes.EARTH_BOOST, 3.5)
         .add(TensuraAttributes.EARTH_RESISTANCE, 0.72)
         .add(TensuraAttributes.MAGIC_RESISTANCE, 0.58)
         .add(TensuraAttributes.MULTILAYER_BARRIER, 6.0);
   }

   private void applyConfiguredAttributes() {
      this.setBase(Attributes.MAX_HEALTH, (Double)cfg().maxHealth.get());
      this.setBase(Attributes.ARMOR, (Double)cfg().armor.get());
      this.setBase(Attributes.ARMOR_TOUGHNESS, (Double)cfg().armorToughness.get());
      this.setBase(Attributes.KNOCKBACK_RESISTANCE, (Double)cfg().knockbackResistance.get());
      this.setBase(Attributes.MOVEMENT_SPEED, (Double)cfg().movementSpeed.get());
      this.setBase(Attributes.ATTACK_DAMAGE, (Double)cfg().attackDamage.get());
      this.setBase(Attributes.FOLLOW_RANGE, (Double)cfg().followRange.get());
      this.setBase(TensuraAttributes.MAX_AURA, (Double)cfg().maxAura.get());
      this.setBase(TensuraAttributes.MAX_MAGICULE, (Double)cfg().maxMagicule.get());
      this.setBase(TensuraAttributes.MAX_SPIRITUAL_HEALTH, (Double)cfg().maxSpiritualHealth.get());
      this.setBase(TensuraAttributes.PRESENCE_SENSE, (Double)cfg().presenceSense.get());
      this.setBase(TensuraAttributes.PRESENCE_SENSE_RADIUS, (Double)cfg().presenceSenseRadius.get());
      this.setBase(TensuraAttributes.DARKNESS_BOOST, (Double)cfg().darknessBoost.get());
      this.setBase(TensuraAttributes.DARKNESS_RESISTANCE, (Double)cfg().darknessResistance.get());
      this.setBase(TensuraAttributes.EARTH_BOOST, (Double)cfg().earthBoost.get());
      this.setBase(TensuraAttributes.EARTH_RESISTANCE, (Double)cfg().earthResistance.get());
      this.setBase(TensuraAttributes.MAGIC_RESISTANCE, (Double)cfg().magicResistance.get());
      this.setBase(TensuraAttributes.MULTILAYER_BARRIER, (Double)cfg().multilayerBarrier.get());
      super.setHealth(this.getMaxHealth());
   }

   private void setBase(Holder<Attribute> attribute, double value) {
      AttributeInstance instance = this.getAttribute(attribute);
      if (instance != null) {
         instance.setBaseValue(value);
      }
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_ATTACK_ID, 0);
      builder.define(DATA_ATTACK_TICKS, 0);
      builder.define(DATA_ATTACK_SEED, 0);
      builder.define(DATA_ORIGIN_X, 0.0F);
      builder.define(DATA_ORIGIN_Y, 0.0F);
      builder.define(DATA_ORIGIN_Z, 0.0F);
      builder.define(DATA_ANCHOR_X, 0.0F);
      builder.define(DATA_ANCHOR_Y, 0.0F);
      builder.define(DATA_ANCHOR_Z, 0.0F);
      builder.define(DATA_DIR_X, 0.0F);
      builder.define(DATA_DIR_Z, 1.0F);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
   }

   protected @NotNull Provider<?> brainProvider() {
      return new SmartBrainProvider(this, true);
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      this.tickBrain(this);
   }

   public List<ExtendedSensor<BerettaEntity>> getSensors() {
      return ObjectArrayList.of(new ExtendedSensor[]{new NearbyLivingEntitySensor(), new HurtBySensor()});
   }

   public BrainActivityGroup<BerettaEntity> getCoreTasks() {
      return BrainActivityGroup.coreTasks(
         new Behavior[]{
            new LookAtTarget(),
            new MoveToWalkTarget().cooldownFor(entity -> 0).startCondition(BerettaEntity::canUseBrainMovement).stopIf(entity -> !entity.canUseBrainMovement())
         }
      );
   }

   public BrainActivityGroup<BerettaEntity> getIdleTasks() {
      return BrainActivityGroup.idleTasks(
         new Behavior[]{
            new FirstApplicableBehaviour(
               new ExtendedBehaviour[]{
                  new TargetOrRetaliate().attackablePredicate(this::isValidEnemy).noTargetSwapping(),
                  new SetPlayerLookTarget(),
                  new SetRandomLookTarget(),
                  new Idle().runFor(entity -> entity.getRandom().nextInt(20, 46))
               }
            )
         }
      );
   }

   public BrainActivityGroup<BerettaEntity> getFightTasks() {
      return BrainActivityGroup.fightTasks(
         new Behavior[]{
            new InvalidateAttackTarget()
               .invalidateIf(
                  (entity, target) -> !entity.isValidEnemy(target)
                     || entity.distanceToSqr(target) > (Double)cfg().targetLeashDistance.get() * (Double)cfg().targetLeashDistance.get()
               )
               .stopTryingToPathAfter(260L),
            new BerettaEntity.BerettaTacticalMovement()
         }
      );
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         this.setNoGravity(false);
         this.bossEvent.setProgress(Mth.clamp(this.getHealth() / this.getMaxHealth(), 0.0F, 1.0F));
         BerettaDefense.tick(this);
         BerettaArenaHandler.tick(this);
         if (!this.skillsGranted) {
            this.grantTensuraSkills();
            this.fillExistence();
            this.skillsGranted = true;
         }

         if (this.globalCooldown > 0) {
            this.globalCooldown--;
         }

         if (this.evasiveTicks > 0 && this.getAttackId() == 0) {
            this.evasiveTicks--;
         }

         if (this.getAttackId() != 0) {
            this.freezeForAttack();
            this.setAttackTicks(this.getAttackTicks() + 1);
            BerettaCombat.tickAttack(this);
         } else {
            LivingEntity target = this.resolveTarget();
            if (target != null) {
               double range = (Double)cfg().attackRange.get();
               if (this.isPreCastMovementLocked(target)) {
                  this.clearBrainMovement();
                  this.getLookControl().setLookAt(target, 90.0F, 90.0F);
               }

               if (this.globalCooldown <= 0 && this.distanceToSqr(target) <= range * range) {
                  if (this.coreQueued) {
                     BerettaCombat.startCoreExposure(this, target);
                  } else {
                     BerettaCombat.startNextAttack(this, target);
                  }
               }
            }
         }
      }
   }

   private boolean canUseBrainMovement() {
      if (this.isAlive() && !this.isDeadOrDying() && this.getAttackId() == 0) {
         LivingEntity target = this.getBrainOrVanillaTarget();
         return target != null && this.isValidEnemy(target) && !this.isPreCastMovementLocked(target);
      } else {
         return false;
      }
   }

   private boolean isPreCastMovementLocked(LivingEntity target) {
      if (target != null && this.getAttackId() == 0) {
         int lockTicks = Math.max(0, (Integer)cfg().preCastMovementLockTicks.get());
         double range = (Double)cfg().attackRange.get();
         return this.globalCooldown <= lockTicks && this.distanceToSqr(target) <= range * range;
      } else {
         return false;
      }
   }

   private LivingEntity getBrainOrVanillaTarget() {
      LivingEntity target = BrainUtils.getTargetOfEntity(this);
      return target != null ? target : super.getTarget();
   }

   private void clearBrainMovement() {
      BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET);
      this.getNavigation().stop();
      this.setAggressive(false);
   }

   private void freezeForAttack() {
      this.clearBrainMovement();
      Vec3 movement = this.getDeltaMovement();
      if (!BerettaCombat.allowsAttackMovement(this.getAttackId())) {
         this.setDeltaMovement(0.0, Math.min(0.0, movement.y), 0.0);
      } else if (movement.y < -0.72) {
         this.setDeltaMovement(movement.x, -0.72, movement.z);
      }

      Vec3 direction = this.getAttackDirection();
      float yaw = (float)(Mth.atan2(-direction.x, direction.z) * 180.0 / Math.PI);
      this.setYRot(yaw);
      this.setXRot(0.0F);
      this.yRotO = yaw;
      this.xRotO = 0.0F;
      this.yBodyRot = yaw;
      this.yBodyRotO = yaw;
      this.yHeadRot = yaw;
      this.yHeadRotO = yaw;
      this.setAggressive(false);
   }

   public void applyCombatImpulse(Vec3 horizontal, double vertical) {
      Vec3 flat = new Vec3(horizontal.x, 0.0, horizontal.z);
      if (flat.lengthSqr() > 1.0) {
         flat = flat.normalize();
      }

      this.setDeltaMovement(flat.x, Math.max(vertical, this.getDeltaMovement().y), flat.z);
      this.hurtMarked = true;
   }

   private void grantTensuraSkills() {
      this.tryLearn((ManasSkill)SpiritualMagics.DARKNESS.get());
      this.tryLearn((ManasSkill)SpiritualMagics.SHADOW_BIND.get());
      this.tryLearn((ManasSkill)SpiritualMagics.DARK_CUBE.get());
      this.tryLearn((ManasSkill)SpiritualMagics.DARKNESS_CANNON.get());
      this.tryLearn((ManasSkill)SpiritualMagics.TRUE_DARKNESS.get());
      this.tryLearn((ManasSkill)SpiritualMagics.EARTH.get());
      this.tryLearn((ManasSkill)SpiritualMagics.EARTH_SPIKES.get());
      this.tryLearn((ManasSkill)SpiritualMagics.EARTH_STORM.get());
      this.tryLearn((ManasSkill)AspectualMagics.STONE_SHOT.get());
      this.tryLearn((ManasSkill)IntrinsicSkills.POSSESSION.get());
      this.tryLearn((ManasSkill)IntrinsicSkills.DARKNESS_TRANSFORM.get());
   }

   private void tryLearn(ManasSkill skill) {
      try {
         SkillHelper.learnSkill(this, skill);
      } catch (Throwable var3) {
      }
   }

   public void refillExistence() {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(this);
         existence.setAura(Math.max(existence.getAura(), (Double)cfg().maxAura.get()));
         existence.setMagicule(Math.max(existence.getMagicule(), (Double)cfg().maxMagicule.get()));
         existence.setSpiritualHealth(Math.max(existence.getSpiritualHealth(), (Double)cfg().maxSpiritualHealth.get()));
         existence.markDirty();
      } catch (Throwable var2) {
      }
   }

   private void fillExistence() {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(this);
         existence.setAura((Double)cfg().maxAura.get());
         existence.setMagicule((Double)cfg().maxMagicule.get());
         existence.setSpiritualHealth((Double)cfg().maxSpiritualHealth.get());
         existence.markDirty();
      } catch (Throwable var2) {
      }
   }

   public boolean isValidEnemy(Entity entity) {
      if (!(entity instanceof LivingEntity living)) {
         return false;
      } else if (living.isAlive() && living != this) {
         return !(entity instanceof Player player && (player.isCreative() || player.isSpectator()))
            ? !this.isAlliedTo(entity) && !entity.isAlliedTo(this)
            : false;
      } else {
         return false;
      }
   }

   public LivingEntity resolveTarget() {
      if (this.level() instanceof ServerLevel level) {
         LivingEntity brainTarget = BrainUtils.getTargetOfEntity(this);
         if (brainTarget != null && this.isTargetWithinLeash(brainTarget)) {
            this.lockedTargetId = brainTarget.getUUID();
            super.setTarget(brainTarget);
            return brainTarget;
         } else if (this.lockedTargetId != null && level.getEntity(this.lockedTargetId) instanceof LivingEntity living && this.isTargetWithinLeash(living)) {
            BrainUtils.setMemory(this, MemoryModuleType.ATTACK_TARGET, living);
            super.setTarget(living);
            return living;
         } else {
            LivingEntity target = super.getTarget();
            if (target != null && this.isTargetWithinLeash(target)) {
               this.lockedTargetId = target.getUUID();
               BrainUtils.setMemory(this, MemoryModuleType.ATTACK_TARGET, target);
               return target;
            } else {
               this.lockedTargetId = null;
               BrainUtils.clearMemory(this, MemoryModuleType.ATTACK_TARGET);
               super.setTarget(null);
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private boolean isTargetWithinLeash(LivingEntity target) {
      if (!this.isValidEnemy(target)) {
         return false;
      }

      double leash = (Double)cfg().targetLeashDistance.get();
      return this.distanceToSqr(target) <= leash * leash;
   }

   public void setTarget(LivingEntity target) {
      if (target != null && this.isTargetWithinLeash(target)) {
         this.lockedTargetId = target.getUUID();
         super.setTarget(target);
         if (!this.level().isClientSide()) {
            BrainUtils.setMemory(this, MemoryModuleType.ATTACK_TARGET, target);
         }
      } else {
         this.lockedTargetId = null;
         super.setTarget(null);
         if (!this.level().isClientSide()) {
            BrainUtils.clearMemory(this, MemoryModuleType.ATTACK_TARGET);
         }
      }
   }

   public void setAttack(int attackId, LivingEntity target) {
      Vec3 origin = this.position();
      Vec3 velocity = target.getDeltaMovement();
      Vec3 horizontalVelocity = new Vec3(velocity.x, 0.0, velocity.z);
      if (horizontalVelocity.lengthSqr() > 0.64) {
         horizontalVelocity = horizontalVelocity.normalize().scale(0.8);
      }

      double leadTicks = Mth.clamp(this.distanceTo(target) * 0.12, 1.5, 4.5);
      Vec3 anchor = target.position().add(horizontalVelocity.scale(leadTicks));
      Vec3 direction = anchor.subtract(origin);
      direction = new Vec3(direction.x, 0.0, direction.z);
      if (direction.lengthSqr() < 1.0E-4) {
         direction = new Vec3(0.0, 0.0, 1.0);
      }

      direction = direction.normalize();
      this.entityData.set(DATA_ATTACK_ID, attackId);
      this.entityData.set(DATA_ATTACK_TICKS, 0);
      this.entityData.set(DATA_ATTACK_SEED, this.getRandom().nextInt());
      this.entityData.set(DATA_ORIGIN_X, (float)origin.x);
      this.entityData.set(DATA_ORIGIN_Y, (float)origin.y);
      this.entityData.set(DATA_ORIGIN_Z, (float)origin.z);
      this.entityData.set(DATA_ANCHOR_X, (float)anchor.x);
      this.entityData.set(DATA_ANCHOR_Y, (float)anchor.y);
      this.entityData.set(DATA_ANCHOR_Z, (float)anchor.z);
      this.entityData.set(DATA_DIR_X, (float)direction.x);
      this.entityData.set(DATA_DIR_Z, (float)direction.z);
      if (attackId != 10) {
         this.previousAttackId = this.lastAttackId;
         this.lastAttackId = attackId;
      }

      this.lockedTargetId = target.getUUID();
      this.clearBrainMovement();
      if (attackId == 10) {
         BerettaDefense.resetDamageWindow(this);
      }
   }

   public void clearAttack() {
      this.entityData.set(DATA_ATTACK_ID, 0);
      this.entityData.set(DATA_ATTACK_TICKS, 0);
   }

   public int getAttackId() {
      return (Integer)this.entityData.get(DATA_ATTACK_ID);
   }

   public int getAttackTicks() {
      return (Integer)this.entityData.get(DATA_ATTACK_TICKS);
   }

   private void setAttackTicks(int ticks) {
      this.entityData.set(DATA_ATTACK_TICKS, ticks);
   }

   public int getAttackSeed() {
      return (Integer)this.entityData.get(DATA_ATTACK_SEED);
   }

   public Vec3 getAttackOrigin() {
      return new Vec3(
         ((Float)this.entityData.get(DATA_ORIGIN_X)).floatValue(),
         ((Float)this.entityData.get(DATA_ORIGIN_Y)).floatValue(),
         ((Float)this.entityData.get(DATA_ORIGIN_Z)).floatValue()
      );
   }

   public Vec3 getAttackAnchor() {
      return new Vec3(
         ((Float)this.entityData.get(DATA_ANCHOR_X)).floatValue(),
         ((Float)this.entityData.get(DATA_ANCHOR_Y)).floatValue(),
         ((Float)this.entityData.get(DATA_ANCHOR_Z)).floatValue()
      );
   }

   public Vec3 getAttackDirection() {
      Vec3 direction = new Vec3(((Float)this.entityData.get(DATA_DIR_X)).floatValue(), 0.0, ((Float)this.entityData.get(DATA_DIR_Z)).floatValue());
      return direction.lengthSqr() < 1.0E-4 ? new Vec3(0.0, 0.0, 1.0) : direction.normalize();
   }

   public int getLastAttackId() {
      return this.lastAttackId;
   }

   public int getPreviousAttackId() {
      return this.previousAttackId;
   }

   public int getOpeningStep() {
      return this.openingStep;
   }

   public void advanceOpeningStep() {
      if (this.openingStep < 5) {
         this.openingStep++;
      }
   }

   public void finishAttack(int cooldownBonus) {
      int finishedAttack = this.getAttackId();
      this.clearAttack();
      if (finishedAttack != 10) {
         this.attacksSinceCore++;
         if (this.attacksSinceCore >= (Integer)cfg().coreAttacksBeforeExpose.get()) {
            this.coreQueued = true;
            this.globalCooldown = (Integer)cfg().coreExposeDelay.get();
            return;
         }
      }

      int min = (Integer)cfg().minimumAttackCooldown.get();
      int max = Math.max(min, (Integer)cfg().maximumAttackCooldown.get());
      if (this.getHealth() <= this.getMaxHealth() * 0.5F) {
         min = Math.max(2, Mth.floor(min * 0.62F));
         max = Math.max(min, Mth.floor(max * 0.72F));
      }

      this.globalCooldown = min + this.getRandom().nextInt(max - min + 1) + Math.max(0, cooldownBonus);
   }

   public void finishCoreExposure() {
      this.clearAttack();
      this.coreQueued = false;
      this.attacksSinceCore = 0;
      BerettaDefense.resetDamageWindow(this);
      int min = (Integer)cfg().minimumAttackCooldown.get();
      int max = Math.max(min, (Integer)cfg().maximumAttackCooldown.get());
      if (this.getHealth() <= this.getMaxHealth() * 0.5F) {
         min = Math.max(2, Mth.floor(min * 0.62F));
         max = Math.max(min, Mth.floor(max * 0.72F));
      }

      this.globalCooldown = min + this.getRandom().nextInt(max - min + 1) + 8;
   }

   public boolean isCoreExposed() {
      return this.getAttackId() == 10;
   }

   public boolean canBeAffected(MobEffectInstance instance) {
      return false;
   }

   public boolean addEffect(MobEffectInstance effectInstance, Entity source) {
      return false;
   }

   public void heal(float amount) {
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return false;
   }

   public boolean hurt(DamageSource source, float amount) {
      if (this.level().isClientSide()) {
         return false;
      }

      if (BerettaDefense.shouldCancelDamage(this, source, amount)) {
         return false;
      }

      Entity attacker = source.getEntity();
      if (attacker != null && !this.isValidEnemy(attacker)) {
         return false;
      }

      float before = this.getHealth();
      float allowed = BerettaDefense.modifyIncomingDamage(this, source, amount);
      if (allowed <= 0.0F) {
         return false;
      }

      this.invulnerableTime = 0;
      this.hurtTime = 0;
      BerettaDefense.beginDamageApplication(this);

      boolean result;
      try {
         result = super.hurt(source, allowed);
      } finally {
         BerettaDefense.endDamageApplication(this);
      }

      this.invulnerableTime = 0;
      this.hurtTime = 0;
      BerettaDefense.afterDamage(this, before, allowed);
      if (result && this.getAttackId() == 0) {
         int min = Math.max(0, (Integer)cfg().hitEvasiveTicksMin.get());
         int max = Math.max(min, (Integer)cfg().hitEvasiveTicksMax.get());
         this.evasiveTicks = Math.max(this.evasiveTicks, min + this.getRandom().nextInt(max - min + 1));
      }

      return result;
   }

   public void setHealth(float health) {
      super.setHealth(BerettaDefense.guardSetHealth(this, health));
   }

   public void die(DamageSource source) {
      if (!BerettaDefense.shouldStopDeath(this, source)) {
         BerettaArenaHandler.release(this);
         super.die(source);
      }
   }

   public void remove(RemovalReason reason) {
      if (!BerettaDefense.shouldBlockRemoval(this, reason)) {
         BerettaDefense.clear(this);
         BerettaCombat.clearRuntime(this);
         BerettaArenaHandler.release(this);
         super.remove(reason);
      }
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossEvent.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      if (this.lockedTargetId != null) {
         tag.putUUID("LockedTarget", this.lockedTargetId);
      }

      tag.putInt("GlobalCooldown", this.globalCooldown);
      tag.putInt("LastAttack", this.lastAttackId);
      tag.putInt("PreviousAttack", this.previousAttackId);
      tag.putInt("AttacksSinceCore", this.attacksSinceCore);
      tag.putInt("OpeningStep", this.openingStep);
      tag.putBoolean("CoreQueued", this.coreQueued);
      tag.putBoolean("SkillsGranted", this.skillsGranted);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.lockedTargetId = tag.hasUUID("LockedTarget") ? tag.getUUID("LockedTarget") : null;
      this.globalCooldown = tag.getInt("GlobalCooldown");
      this.lastAttackId = tag.getInt("LastAttack");
      this.previousAttackId = tag.getInt("PreviousAttack");
      this.attacksSinceCore = tag.getInt("AttacksSinceCore");
      this.openingStep = tag.getInt("OpeningStep");
      this.coreQueued = tag.getBoolean("CoreQueued");
      this.skillsGranted = tag.getBoolean("SkillsGranted");
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "main", 3, this::animationState));
   }

   private PlayState animationState(AnimationState<BerettaEntity> state) {
      if (this.isDeadOrDying()) {
         return state.setAndContinue(DEATH);
      } else if (this.getAttackId() != 0) {
         return state.setAndContinue(IDLE);
      } else {
         return state.isMoving() ? state.setAndContinue(WALK) : state.setAndContinue(IDLE);
      }
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.animationCache;
   }

   private static final class BerettaTacticalMovement extends ExtendedBehaviour<BerettaEntity> {
      private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = List.of(
         Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT),
         Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED),
         Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED)
      );
      private Vec3 destination = Vec3.ZERO;
      private Vec3 lastPosition = Vec3.ZERO;
      private int repathTicks;
      private int directionTicks;
      private int projectileScanTicks;
      private int stuckCheckTicks;
      private int orbitDirection;
      private BerettaEntity.BerettaTacticalMovement.MovementMode mode = BerettaEntity.BerettaTacticalMovement.MovementMode.ORBIT;

      private BerettaTacticalMovement() {
         this.noTimeout();
      }

      protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
         return MEMORY_REQUIREMENTS;
      }

      protected boolean checkExtraStartConditions(ServerLevel level, BerettaEntity entity) {
         return entity.canUseBrainMovement() && BrainUtils.getTargetOfEntity(entity) != null;
      }

      protected boolean shouldKeepRunning(BerettaEntity entity) {
         LivingEntity target = BrainUtils.getTargetOfEntity(entity);
         return target != null && entity.isValidEnemy(target) && entity.canUseBrainMovement();
      }

      protected void start(BerettaEntity entity) {
         this.orbitDirection = entity.getRandom().nextBoolean() ? 1 : -1;
         this.directionTicks = 42 + entity.getRandom().nextInt(48);
         this.repathTicks = 0;
         this.projectileScanTicks = 0;
         this.stuckCheckTicks = 10;
         this.lastPosition = entity.position();
         this.destination = entity.position();
         entity.setAggressive(true);
      }

      protected void tick(BerettaEntity entity) {
         LivingEntity target = BrainUtils.getTargetOfEntity(entity);
         if (target != null && entity.isValidEnemy(target)) {
            entity.lockedTargetId = target.getUUID();
            entity.getLookControl().setLookAt(target, 72.0F, 72.0F);
            BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new EntityTracker(target, true));
            if (--this.directionTicks <= 0) {
               if (entity.getRandom().nextFloat() < 0.74F) {
                  this.orbitDirection *= -1;
               }

               this.directionTicks = 42 + entity.getRandom().nextInt(49);
               this.repathTicks = 0;
            }

            if (--this.projectileScanTicks <= 0) {
               this.projectileScanTicks = 4;
               int dodgeSide = this.incomingProjectileSide(entity);
               if (dodgeSide != 0) {
                  this.orbitDirection = dodgeSide;
                  entity.evasiveTicks = Math.max(entity.evasiveTicks, 22);
                  this.repathTicks = 0;
               }
            }

            if (--this.stuckCheckTicks <= 0) {
               this.stuckCheckTicks = 10;
               if (entity.position().distanceToSqr(this.lastPosition) < 0.035 && BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET)) {
                  this.orbitDirection *= -1;
                  this.repathTicks = 0;
                  BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
                  if (entity.onGround() && entity.horizontalCollision) {
                     Vec3 motion = entity.getDeltaMovement();
                     entity.setDeltaMovement(motion.x, Math.max(0.36, motion.y), motion.z);
                  }
               }

               this.lastPosition = entity.position();
            }

            if (--this.repathTicks <= 0 || !(entity.position().distanceToSqr(this.destination) > 2.25)) {
               this.mode = this.selectMode(entity, target);
               this.destination = this.selectDestination(entity, target, this.mode);
               float speed = this.speed(entity, this.mode);
               BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(this.destination, speed, 1));
               int min = Math.max(1, (Integer)BerettaEntity.cfg().tacticalRepathMinTicks.get());
               int max = Math.max(min, (Integer)BerettaEntity.cfg().tacticalRepathMaxTicks.get());
               this.repathTicks = min + entity.getRandom().nextInt(max - min + 1);
            }
         }
      }

      protected void stop(BerettaEntity entity) {
         BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
         entity.getNavigation().stop();
         entity.setAggressive(false);
         this.destination = entity.position();
         this.repathTicks = 0;
      }

      private BerettaEntity.BerettaTacticalMovement.MovementMode selectMode(BerettaEntity entity, LivingEntity target) {
         double distance = entity.distanceTo(target);
         if (entity.evasiveTicks > 0) {
            return BerettaEntity.BerettaTacticalMovement.MovementMode.EVADE;
         } else if (distance < (Double)BerettaEntity.cfg().retreatDistance.get()) {
            return BerettaEntity.BerettaTacticalMovement.MovementMode.RETREAT;
         } else if (!entity.hasLineOfSight(target)) {
            return BerettaEntity.BerettaTacticalMovement.MovementMode.FLANK;
         } else {
            return distance > BerettaEntity.cfg().approachStartDistance.get()
               ? BerettaEntity.BerettaTacticalMovement.MovementMode.CLOSE
               : BerettaEntity.BerettaTacticalMovement.MovementMode.ORBIT;
         }
      }

      private float speed(BerettaEntity entity, BerettaEntity.BerettaTacticalMovement.MovementMode selectedMode) {
         return switch (selectedMode) {
            case CLOSE, FLANK -> ((Double)BerettaEntity.cfg().approachSpeed.get()).floatValue();
            case ORBIT -> ((Double)BerettaEntity.cfg().orbitSpeed.get()).floatValue();
            case RETREAT -> ((Double)BerettaEntity.cfg().retreatSpeed.get()).floatValue();
            case EVADE -> ((Double)BerettaEntity.cfg().evasiveSpeed.get()).floatValue();
         };
      }

      private Vec3 selectDestination(BerettaEntity entity, LivingEntity target, BerettaEntity.BerettaTacticalMovement.MovementMode selectedMode) {
         Vec3 targetMotion = target.getDeltaMovement();
         Vec3 lead = new Vec3(targetMotion.x, 0.0, targetMotion.z);
         if (lead.lengthSqr() > 0.81) {
            lead = lead.normalize().scale(0.9);
         }

         double distance = entity.distanceTo(target);
         double leadScale = selectedMode == BerettaEntity.BerettaTacticalMovement.MovementMode.CLOSE ? 4.0 : 2.2;
         Vec3 center = target.position().add(lead.scale(leadScale));
         Vec3 relative = entity.position().subtract(target.position());
         double baseAngle = relative.lengthSqr() < 0.001 ? entity.getRandom().nextDouble() * (float) (Math.PI * 2) : Mth.atan2(relative.z, relative.x);
         double minRadius = Math.min((Double)BerettaEntity.cfg().orbitMinRadius.get(), (Double)BerettaEntity.cfg().orbitMaxRadius.get());
         double maxRadius = Math.max(minRadius, (Double)BerettaEntity.cfg().orbitMaxRadius.get());
         double angleStep;
         double radius;
         switch (selectedMode) {
            case CLOSE:
               angleStep = this.orbitDirection * (0.42 + entity.getRandom().nextDouble() * 0.32);
               radius = minRadius + (maxRadius - minRadius) * 0.58;
               break;
            case ORBIT:
            default:
               angleStep = this.orbitDirection * (0.48 + entity.getRandom().nextDouble() * 0.36);
               radius = minRadius + entity.getRandom().nextDouble() * (maxRadius - minRadius);
               break;
            case RETREAT:
               angleStep = this.orbitDirection * (0.82 + entity.getRandom().nextDouble() * 0.38);
               radius = maxRadius;
               break;
            case FLANK:
               angleStep = this.orbitDirection * (1.04 + entity.getRandom().nextDouble() * 0.44);
               radius = minRadius + entity.getRandom().nextDouble() * (maxRadius - minRadius);
               break;
            case EVADE:
               angleStep = this.orbitDirection * (1.1 + entity.getRandom().nextDouble() * 0.46);
               radius = Mth.clamp(distance + 4.0, minRadius + 2.0, maxRadius + 5.0);
         }

         double angle = baseAngle + angleStep;
         Vec3 raw = new Vec3(center.x + Math.cos(angle) * radius, center.y, center.z + Math.sin(angle) * radius);
         Vec3 walkable = this.findWalkable(entity, raw, center, radius);
         if (walkable != null) {
            return walkable;
         }

         for (int i = 1; i <= 6; i++) {
            double alternateAngle = angle + this.orbitDirection * i * 0.38;
            Vec3 alternate = new Vec3(center.x + Math.cos(alternateAngle) * radius, center.y, center.z + Math.sin(alternateAngle) * radius);
            walkable = this.findWalkable(entity, alternate, center, radius);
            if (walkable != null) {
               return walkable;
            }
         }

         return this.fallbackDestination(entity, target);
      }

      private Vec3 fallbackDestination(BerettaEntity entity, LivingEntity target) {
         Vec3 away = entity.position().subtract(target.position());
         away = new Vec3(away.x, 0.0, away.z);
         if (away.lengthSqr() < 0.001) {
            away = new Vec3(1.0, 0.0, 0.0);
         }

         away = away.normalize();
         Vec3 side = new Vec3(-away.z, 0.0, away.x).scale(this.orbitDirection * 7.0);
         Vec3 raw = entity.position().add(away.scale(5.0)).add(side);
         Vec3 walkable = this.findWalkable(entity, raw, target.position(), 12.0);
         return walkable == null ? entity.position() : walkable;
      }

      private Vec3 findWalkable(BerettaEntity entity, Vec3 raw, Vec3 center, double radius) {
         int baseY = Mth.floor(Mth.clamp(raw.y, entity.level().getMinBuildHeight() + 2.0, entity.level().getMaxBuildHeight() - 3.0));
         int x = Mth.floor(raw.x);
         int z = Mth.floor(raw.z);

         for (int step = 0; step <= 12; step++) {
            int offset = step == 0 ? 0 : (step + 1) / 2 * ((step & 1) == 1 ? -1 : 1);
            BlockPos feet = new BlockPos(x, baseY + offset, z);
            if (this.isWalkable(entity, feet)) {
               Vec3 candidate = Vec3.atBottomCenterOf(feet);
               double dx = candidate.x - center.x;
               double dz = candidate.z - center.z;
               if (!(dx * dx + dz * dz > (radius + 8.0) * (radius + 8.0))) {
                  AABB moved = entity.getBoundingBox().move(candidate.x - entity.getX(), candidate.y - entity.getY(), candidate.z - entity.getZ());
                  if (entity.level().noCollision(entity, moved)) {
                     return candidate;
                  }
               }
            }
         }

         return null;
      }

      private boolean isWalkable(BerettaEntity entity, BlockPos feet) {
         BlockPos below = feet.below();
         if (!entity.level().getFluidState(feet).isEmpty()) {
            return false;
         } else if (!entity.level().getFluidState(feet.above()).isEmpty()) {
            return false;
         } else if (!entity.level().getBlockState(feet).getCollisionShape(entity.level(), feet).isEmpty()) {
            return false;
         } else {
            return !entity.level().getBlockState(feet.above()).getCollisionShape(entity.level(), feet.above()).isEmpty()
               ? false
               : entity.level().getBlockState(below).isFaceSturdy(entity.level(), below, Direction.UP);
         }
      }

      private int incomingProjectileSide(BerettaEntity entity) {
         double scanRadius = (Double)BerettaEntity.cfg().projectileDodgeScanRadius.get();
         if (scanRadius <= 0.0) {
            return 0;
         }

         AABB area = entity.getBoundingBox().inflate(scanRadius);
         Vec3 bossCenter = entity.position().add(0.0, entity.getBbHeight() * 0.5, 0.0);

         for (Projectile projectile : entity.level().getEntitiesOfClass(Projectile.class, area, Entity::isAlive)) {
            Entity owner = projectile.getOwner();
            if (owner != entity && (owner == null || !owner.isAlliedTo(entity) && !entity.isAlliedTo(owner))) {
               Vec3 velocity = projectile.getDeltaMovement();
               if (!(velocity.lengthSqr() < 0.015)) {
                  Vec3 direction = velocity.normalize();
                  Vec3 toBoss = bossCenter.subtract(projectile.position());
                  double along = toBoss.dot(direction);
                  if (!(along < 0.0) && !(along > scanRadius + 3.0)) {
                     Vec3 closest = projectile.position().add(direction.scale(along));
                     if (!(closest.distanceToSqr(bossCenter) > 7.0)) {
                        Vec3 flatToBoss = new Vec3(toBoss.x, 0.0, toBoss.z);
                        Vec3 flatVelocity = new Vec3(direction.x, 0.0, direction.z);
                        if (!(flatToBoss.lengthSqr() < 0.001) && !(flatVelocity.lengthSqr() < 0.001)) {
                           double cross = flatVelocity.normalize().cross(flatToBoss.normalize()).y;
                           return cross >= 0.0 ? -1 : 1;
                        }

                        return entity.getRandom().nextBoolean() ? 1 : -1;
                     }
                  }
               }
            }
         }

         return 0;
      }

      private enum MovementMode {
         CLOSE,
         ORBIT,
         RETREAT,
         FLANK,
         EVADE;
      }
   }
}
