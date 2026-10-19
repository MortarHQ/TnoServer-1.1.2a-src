package io.github.Memoires.mysticism.entity.skill;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import io.github.Memoires.mysticism.ability.skill.unique.EngineerSkill;
import io.github.Memoires.mysticism.entity.projectile.SentryBulletProjectile;
import io.github.Memoires.mysticism.entity.projectile.SentryMissileProjectile;
import io.github.Memoires.mysticism.registry.entity.MysticismProjectileEntityTypes;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.Memoires.mysticism.registry.sound.MysticismSoundEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.CustomRangeAttack;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SentryEntity extends TensuraTamableEntity implements GeoEntity, SmartBrainOwner<SentryEntity> {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private int levelUpAnimationTicks = 0;
   private boolean clientWasLevelingUp = false;
   private static final HashMap<UUID, ArrayList<SentryEntity>> playerTurrets = new HashMap<>();
   private static final EntityDataAccessor<Integer> SENTRY_LEVEL = SynchedEntityData.defineId(SentryEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> PLAYER_PROXIMITY_TIME = SynchedEntityData.defineId(SentryEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> IS_BEING_PLACED = SynchedEntityData.defineId(SentryEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IS_LEVELING_UP = SynchedEntityData.defineId(SentryEntity.class, EntityDataSerializers.BOOLEAN);
   public static final int[] FIRE_RATE = new int[]{
      EngineerSkill.CONFIG.sentryLevel1FireRate, EngineerSkill.CONFIG.sentryLevel2FireRate, EngineerSkill.CONFIG.sentryLevel3FireRate, 10, 15
   };
   public final int LEVEL_2_PROXIMITY_TIME = this.checkMasteryOfOwner()
      ? EngineerSkill.CONFIG.sentryLevel2LevelUpTime / 2
      : EngineerSkill.CONFIG.sentryLevel2LevelUpTime;
   public final int LEVEL_3_PROXIMITY_TIME = this.checkMasteryOfOwner()
      ? EngineerSkill.CONFIG.sentryLevel3LevelUpTime / 2
      : EngineerSkill.CONFIG.sentryLevel3LevelUpTime;
   private long creationTime = System.currentTimeMillis();

   public SentryEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
      super(entityType, level);
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "main_controller", 0, state -> {
         SentryEntity entity = (SentryEntity)state.getAnimatable();
         if (entity.isLevelingUp() && !entity.clientWasLevelingUp) {
            state.getController().forceAnimationReset();
         }

         entity.clientWasLevelingUp = entity.isLevelingUp();
         return !entity.isBeingPlaced() && !entity.isLevelingUp() ? PlayState.STOP : state.setAndContinue(RawAnimation.begin().thenPlay("animation.model.new"));
      }));
      controllers.add(
         new AnimationController(this, "attack_controller", 2, state -> PlayState.STOP)
            .triggerableAnim("shoot_1", RawAnimation.begin().thenPlay("sentry1.shoot"))
            .triggerableAnim("rocket_1", RawAnimation.begin().thenPlay("sentry1.shoot"))
            .triggerableAnim("shoot_2", RawAnimation.begin().thenPlay("sentry5.shoot"))
            .triggerableAnim("rocket_2", RawAnimation.begin().thenPlay("sentry5.shoot"))
      );
   }

   public static Builder setAttributes() {
      return TensuraTamableEntity.setAttributes()
         .add(Attributes.ATTACK_DAMAGE, 1.0)
         .add(Attributes.MAX_HEALTH, 20.0)
         .add(Attributes.MOVEMENT_SPEED, 0.0)
         .add(Attributes.FOLLOW_RANGE, 150.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.1F)
         .add(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, 100.0)
         .add(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, 25.0)
         .add(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, 25.0);
   }

   private void copyAttributeFromOwner(LivingEntity owner, Supplier<Holder<Attribute>> attributeSupplier) {
      if (owner != null) {
         Holder<Attribute> attribute = attributeSupplier.get();
         AttributeInstance ownerAttribute = owner.getAttribute(attribute);
         AttributeInstance selfAttribute = this.getAttribute(attribute);
         if (ownerAttribute != null && selfAttribute != null) {
            selfAttribute.setBaseValue(ownerAttribute.getValue());
         }
      }
   }

   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData
   ) {
      LivingEntity owner = this.getOwner();
      this.copyAttributeFromOwner(owner, () -> Attributes.MAX_HEALTH);
      this.copyAttributeFromOwner(owner, () -> Attributes.ATTACK_DAMAGE);
      this.copyAttributeFromOwner(owner, () -> Attributes.ARMOR);
      this.copyAttributeFromOwner(owner, () -> TensuraAttributes.MAX_MAGICULE);
      this.copyAttributeFromOwner(owner, () -> TensuraAttributes.MAX_AURA);
      this.copyAttributeFromOwner(owner, () -> TensuraAttributes.PHYSICAL_BARRIER);
      this.copyAttributeFromOwner(owner, () -> TensuraAttributes.MULTILAYER_BARRIER);
      this.copyAttributeFromOwner(owner, () -> TensuraAttributes.MAX_SPIRITUAL_HEALTH);
      this.setHealth((float)this.getAttributes().getValue(Attributes.MAX_HEALTH));
      TensuraStorages.getExistenceFrom(this).setSpiritualHealth(this.getAttributes().getValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH));
      return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
   }

   public void setSentryLevel() {
      int proximityTime = this.getPlayerProximityTime();
      int newLevel;
      if (proximityTime >= this.LEVEL_3_PROXIMITY_TIME) {
         newLevel = 3;
      } else if (proximityTime >= this.LEVEL_2_PROXIMITY_TIME) {
         newLevel = 2;
      } else {
         newLevel = 1;
      }

      if (!this.level().isClientSide && newLevel != this.getSentryLevel()) {
         this.setLevelingUp(true);
         this.levelUpAnimationTicks = 83;
         this.level().playSound(null, this.blockPosition(), SoundEvents.PLAYER_LEVELUP, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         if (this.getOwner() instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("mysticism.skill.engineer.turret_evolved", new Object[]{newLevel}).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)),
               false
            );
         }

         this.entityData.set(SENTRY_LEVEL, newLevel);
      }
   }

   public boolean canBeAffected(MobEffectInstance pEffectInstance) {
      return false;
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(SENTRY_LEVEL, 1);
      builder.define(PLAYER_PROXIMITY_TIME, 0);
      builder.define(IS_BEING_PLACED, true);
      builder.define(IS_LEVELING_UP, false);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setSentryLevel(compound.getInt("SentryLevel"));
      this.setPlayerProximityTime(compound.getInt("PlayerProximityTime"));
      this.setBeingPlaced(compound.getBoolean("BeingPlaced"));
      this.setLevelingUp(compound.getBoolean("IsLevelingUp"));
      if (compound.contains("CreationTime")) {
         this.creationTime = compound.getLong("CreationTime");
      }
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("SentryLevel", this.getSentryLevel());
      compound.putInt("PlayerProximityTime", this.getPlayerProximityTime());
      compound.putBoolean("BeingPlaced", this.isBeingPlaced());
      compound.putBoolean("IsLevelingUp", this.isLevelingUp());
      compound.putLong("CreationTime", this.creationTime);
   }

   public boolean isBeingPlaced() {
      return (Boolean)this.entityData.get(IS_BEING_PLACED);
   }

   public boolean isLevelingUp() {
      return (Boolean)this.entityData.get(IS_LEVELING_UP);
   }

   public long getCreationTime() {
      return this.creationTime;
   }

   public int getPlayerProximityTime() {
      return (Integer)this.entityData.get(PLAYER_PROXIMITY_TIME);
   }

   public int getSentryLevel() {
      return (Integer)this.entityData.get(SENTRY_LEVEL);
   }

   private int getFireRate() {
      int level = Mth.clamp((Integer)this.entityData.get(SENTRY_LEVEL), 1, FIRE_RATE.length);
      return FIRE_RATE[level - 1];
   }

   public void setPlayerProximityTime(int time) {
      this.entityData.set(PLAYER_PROXIMITY_TIME, time);
   }

   public void setSentryLevel(int level) {
      this.entityData.set(SENTRY_LEVEL, level);
   }

   public void setBeingPlaced(boolean placed) {
      this.entityData.set(IS_BEING_PLACED, placed);
   }

   public void setLevelingUp(boolean levelingUp) {
      this.entityData.set(IS_LEVELING_UP, levelingUp);
   }

   public boolean isPushable() {
      return false;
   }

   public void setYRot(float yRot) {
      if (this.firstTick) {
         super.setYRot(yRot);
         this.yBodyRot = yRot;
      }
   }

   public void setYHeadRot(float yHeadRot) {
      super.setYHeadRot(yHeadRot);
   }

   public void setYBodyRot(float yBodyRot) {
      if (this.firstTick) {
         super.setYBodyRot(yBodyRot);
      }
   }

   public void customServerAiStep() {
      super.customServerAiStep();
      this.tickBrain(this);
   }

   private boolean canSeeTarget(LivingEntity target) {
      return this.hasLineOfSight(target);
   }

   public Brain<SentryEntity> getBrain() {
      return super.getBrain();
   }

   protected Brain<?> makeBrain(Dynamic<?> dynamic) {
      return this.brainProvider().makeBrain(dynamic);
   }

   public List<ExtendedSensor<SentryEntity>> getSensors() {
      return ObjectArrayList.of(
         new ExtendedSensor[]{new NearbyLivingEntitySensor().setRadius(32.0).setPredicate((target, entity) -> entity.shouldTarget(target)), new HurtBySensor()}
      );
   }

   public BrainActivityGroup<SentryEntity> getCoreTasks() {
      return BrainActivityGroup.coreTasks(new Behavior[]{new LookAtTarget()});
   }

   public boolean shouldTarget(LivingEntity target) {
      if (target == null) {
         return false;
      }

      if (target == this) {
         return false;
      }

      if (!target.isAlive()) {
         return false;
      }

      if (!this.hasLineOfSight(target)) {
         return false;
      }

      LivingEntity owner = this.getOwner();
      if (owner != null) {
         if (target == owner) {
            return false;
         }

         if (target instanceof TamableAnimal tame && tame.getOwner() == owner) {
            return false;
         }

         if (target.isAlliedTo(owner)) {
            return false;
         }
      }

      return true;
   }

   private boolean checkMasteryOfOwner() {
      LivingEntity owner = this.getOwner();
      if (owner != null) {
         Optional<ManasSkillInstance> engineerInstance = SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)UniqueMysticismSkills.ENGINEER.get());
         if (engineerInstance.isPresent()) {
            return engineerInstance.get().isMastered(owner);
         }
      }

      return false;
   }

   public BrainActivityGroup<SentryEntity> getIdleTasks() {
      return BrainActivityGroup.idleTasks(
         new Behavior[]{
            new FirstApplicableBehaviour(
               new ExtendedBehaviour[]{
                  new ExtendedBehaviour<SentryEntity>() {
                     protected boolean checkExtraStartConditions(ServerLevel level, SentryEntity entity) {
                        LivingEntity target = level.getNearestEntity(
                           LivingEntity.class,
                           TargetingConditions.forCombat().range(32.0).selector(entity::shouldTarget),
                           entity,
                           entity.getX(),
                           entity.getY(),
                           entity.getZ(),
                           entity.getBoundingBox().inflate(32.0)
                        );
                        if (target != null) {
                           entity.setTarget(target);
                           BrainUtils.setTargetOfEntity(entity, target);
                           return true;
                        } else {
                           return false;
                        }
                     }

                     protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
                        return List.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT));
                     }
                  },
                  new SetPlayerLookTarget(),
                  new SetRandomLookTarget()
               }
            )
         }
      );
   }

   protected Provider<?> brainProvider() {
      return new SmartBrainProvider(this, true);
   }

   public BrainActivityGroup<SentryEntity> getFightTasks() {
      return BrainActivityGroup.fightTasks(
         new Behavior[]{
            new InvalidateAttackTarget().invalidateIf((entity, target) -> target == null || !target.isAlive() || !entity.shouldTarget(target)),
            new SetWalkTargetToAttackTarget(),
            new LookAtTarget(),
            new CustomRangeAttack(0).maxAttackRadius(25.0F).attackInterval(e -> 60).performAttack((e, target) -> {
               if (e.level() instanceof ServerLevel level) {
                  e.fireRocket(level, e, target);
               }
            }).startCondition(e -> e.getTarget() != null && e.distanceTo(e.getTarget()) > 8.0F && e.getSentryLevel() >= 3),
            new CustomRangeAttack(0).maxAttackRadius(30.0F).attackInterval(SentryEntity::getFireRate).performAttack((e, target) -> {
               if (e.level() instanceof ServerLevel level) {
                  e.fireNormalProjectile(level, e, target);
               }
            }).startCondition(e -> e.getTarget() != null)
         }
      );
   }

   private void fireNormalProjectile(ServerLevel level, SentryEntity sentry, LivingEntity target) {
      SentryBulletProjectile projectile = new SentryBulletProjectile(
         (EntityType<? extends SentryBulletProjectile>)MysticismProjectileEntityTypes.SENTRY_BULLET.get(), level
      );
      projectile.setPos(sentry.getX(), sentry.getEyeY() - 0.1, sentry.getZ());
      projectile.setDamage((float)sentry.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).getValue());
      if (sentry.getSentryLevel() >= 3) {
         projectile.setMagic(true);
      }

      int sentryLevel = this.getSentryLevel();
      if (sentryLevel > 1) {
         this.triggerAnim("attack_controller", "shoot_2");
      } else {
         this.triggerAnim("attack_controller", "shoot_1");
      }

      double d0 = target.getX() - sentry.getX();
      double d1 = target.getY(0.333333) - projectile.getY();
      double d2 = target.getZ() - sentry.getZ();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      projectile.shoot(d0, d1 + d3 * 0.05, d2, 1.6F, 1.0F);
      projectile.setOwner(sentry);
      projectile.setNoGravity(true);
      level.addFreshEntity(projectile);
      level.playSound(
         null,
         sentry.getX(),
         sentry.getY(),
         sentry.getZ(),
         (SoundEvent)MysticismSoundEvents.SENTRY_SHOOT.get(),
         TensuraSkill.ABILITY_SOUND,
         1.0F,
         1.0F / (sentry.getRandom().nextFloat() * 0.4F + 0.8F)
      );
   }

   private void fireRocket(ServerLevel level, SentryEntity sentry, LivingEntity target) {
      this.triggerAnim("attack_controller", "shoot_2");
      SentryMissileProjectile missile = new SentryMissileProjectile(
         (EntityType<? extends SentryMissileProjectile>)MysticismProjectileEntityTypes.SENTRY_MISSILE.get(), level
      );
      missile.setOwner(sentry);
      missile.setPos(sentry.getX(), sentry.getEyeY() - 0.1, sentry.getZ());
      double d0 = target.getX() - sentry.getX();
      double d1 = target.getY(0.333333) - missile.getY();
      double d2 = target.getZ() - sentry.getZ();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      missile.shoot(d0, d1 + d3 * 0.05, d2, 1.6F, 1.0F);
      missile.setDamage((float)sentry.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).getValue());
      level.addFreshEntity(missile);
      level.playSound(
         null, sentry.getX(), sentry.getY(), sentry.getZ(), (SoundEvent)MysticismSoundEvents.MISSILE_SHOOT.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
      );
   }

   public void aiStep() {
      super.aiStep();
      if (this.getTarget() != null && !this.getTarget().isAlive()) {
         this.setTarget(null);
      }
   }

   public void tick() {
      super.tick();
      if (this.isBeingPlaced() && this.tickCount > 83) {
         this.setBeingPlaced(false);
      }

      if (!this.level().isClientSide && this.levelUpAnimationTicks > 0) {
         this.levelUpAnimationTicks--;
         if (this.levelUpAnimationTicks == 0) {
            this.setLevelingUp(false);
         }
      }

      if (this.getOwner() != null) {
         UUID ownerUUID = this.getOwner().getUUID();
         playerTurrets.computeIfAbsent(ownerUUID, k -> new ArrayList<>());
         if (!playerTurrets.get(ownerUUID).contains(this)) {
            playerTurrets.get(ownerUUID).add(this);
         }

         if (playerTurrets.get(ownerUUID).size() > 2) {
            playerTurrets.get(ownerUUID).sort(Comparator.comparingLong(SentryEntity::getCreationTime));
            SentryEntity oldestTurret = playerTurrets.get(ownerUUID).getFirst();
            double x = oldestTurret.getX();
            double y = oldestTurret.getY();
            double z = oldestTurret.getZ();
            if (oldestTurret.level() != null) {
               oldestTurret.level().addParticle(ParticleTypes.EXPLOSION, x, y, z, 1.0, 1.0, 1.0);
            }

            playerTurrets.get(ownerUUID).remove(0);
            oldestTurret.remove(RemovalReason.DISCARDED);
         }

         double distanceToOwner = this.distanceToSqr(this.getOwner().getX(), this.getOwner().getY(), this.getOwner().getZ());
         double maxDistance = 10.0;
         int proximityTime = this.getPlayerProximityTime();
         if (distanceToOwner <= maxDistance * maxDistance) {
            this.setPlayerProximityTime(proximityTime + 1);
         } else if (proximityTime > 0) {
            this.setPlayerProximityTime(proximityTime);
         }

         this.setSentryLevel();
      }

      LivingEntity target = this.getTarget();
      if (target != null) {
         this.updateTurretRotation(target);
      }
   }

   public void remove(RemovalReason reason) {
      if (this.getOwner() != null) {
         UUID ownerUUID = this.getOwner().getUUID();
         if (playerTurrets.containsKey(ownerUUID)) {
            playerTurrets.get(ownerUUID).remove(this);
         }
      }

      super.remove(reason);
   }

   private void updateTurretRotation(LivingEntity target) {
      Vec3 direction = this.calculateDirectionToTarget(target);
      double horizontalDistance = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
      float yaw = (float)Math.toDegrees(Math.atan2(direction.z, direction.x)) - 90.0F;
      this.setYHeadRot(yaw);
      float pitch = (float)(-Math.toDegrees(Math.atan2(direction.y, horizontalDistance)));
      this.setXRot(pitch);
   }

   private Vec3 calculateDirectionToTarget(LivingEntity target) {
      double dx = target.getX() - this.getX();
      double dy = target.getEyeY() - (this.getY() + 0.5);
      double dz = target.getZ() - this.getZ();
      return new Vec3(dx, dy, dz).normalize();
   }
}
