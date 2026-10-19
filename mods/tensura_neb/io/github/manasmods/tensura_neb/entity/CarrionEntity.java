package io.github.manasmods.tensura_neb.entity;

import com.mojang.datafixers.util.Pair;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.magic.Magic.MagicType;
import io.github.manasmods.tensura.ability.skill.unique.ChosenOneSkill;
import io.github.manasmods.tensura.ability.skill.unique.CookSkill;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.data.TensuraRaceTags;
import io.github.manasmods.tensura.data.TensuraTags.MobEffects;
import io.github.manasmods.tensura.entity.ai.behaviour.TensuraBehaviourHelper;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.CustomHeldAttack;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.CustomRangeAttack;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.InvalidateNeutralAttackTarget;
import io.github.manasmods.tensura.entity.ai.behaviour.misc.HumanoidConsumeItem;
import io.github.manasmods.tensura.entity.ai.behaviour.movement.MoveOrFlyToWalkTarget;
import io.github.manasmods.tensura.entity.ai.behaviour.movement.TeleportToEntity;
import io.github.manasmods.tensura.entity.ai.behaviour.path.SetRandomFlyAndWalkTarget;
import io.github.manasmods.tensura.entity.ai.behaviour.path.SetRandomSwimAndWalkTarget;
import io.github.manasmods.tensura.entity.ai.behaviour.path.SubordinateFollowOwner;
import io.github.manasmods.tensura.entity.human.OtherworlderEntity;
import io.github.manasmods.tensura.entity.magic.beam.BeamProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.AuraBulletProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.AuraSlashProjectile;
import io.github.manasmods.tensura.entity.template.PlayerLikeEntity;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.entity.template.subclass.IFlyingAmphibian;
import io.github.manasmods.tensura.entity.template.subclass.ITeleportation;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.battlewill.MeleeArts;
import io.github.manasmods.tensura.registry.battlewill.ProjectileArts;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.MiscEntityTypes;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import io.github.manasmods.tensura_neb.registry.NebItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Generated;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.InteractWithDoor;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StrafeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CarrionEntity extends OtherworlderEntity implements GeoEntity, SmartBrainOwner<CarrionEntity>, ITeleportation, IFlyingAmphibian {
   private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(CarrionEntity.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(CarrionEntity.class, EntityDataSerializers.BOOLEAN);
   private boolean landNavigating = false;
   private int swimmingTick = 0;
   protected int flyingTick;
   protected boolean wasFlying;
   private final ServerBossEvent bossEvent = (ServerBossEvent)new ServerBossEvent(this.getDisplayName(), BossBarColor.YELLOW, BossBarOverlay.NOTCHED_20)
      .setPlayBossMusic(true);
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public CarrionEntity(EntityType<? extends CarrionEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.xpReward = 6000;
      this.initFlying(this);
      this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
      this.setPathfindingMalus(PathType.FENCE, -1.0F);
      this.setPathfindingMalus(PathType.TRAPDOOR, -1.0F);
      this.setPathfindingMalus(PathType.DANGER_TRAPDOOR, -1.0F);
      this.setPathfindingMalus(PathType.DANGER_TRAPDOOR, -1.0F);
   }

   public static Builder setAttributes() {
      return TensuraTamableEntity.setAttributes()
         .add(Attributes.ARMOR, 30.0)
         .add(Attributes.ATTACK_DAMAGE, 80.0)
         .add(Attributes.MAX_HEALTH, 4000.0)
         .add(Attributes.MOVEMENT_SPEED, 0.35F)
         .add(Attributes.FLYING_SPEED, 0.7F)
         .add(Attributes.FOLLOW_RANGE, 64.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.6F)
         .add(Attributes.STEP_HEIGHT, 2.0)
         .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.5)
         .add(Attributes.ENTITY_INTERACTION_RANGE, 2.0)
         .add(TensuraAttributes.PRESENCE_SENSE, 3.0)
         .add(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, 20.0)
         .add(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, 5.0)
         .add(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, 5.0);
   }

   public void switchMoveControl(MoveControl control) {
      this.moveControl = control;
   }

   public void switchNavigation(PathNavigation navigation) {
      this.navigation = navigation;
   }

   public boolean shouldTarget(LivingEntity target) {
      if (target == this) {
         return false;
      } else if (!target.isAlive()) {
         return false;
      } else if (target.getType().is(TensuraEntityTags.HINATA_NEUTRAL)) {
         return false;
      } else if (target instanceof Mob mob && mob.getTarget() == this) {
         return true;
      } else if (EnergyHelper.getMaxEP(target) < 10000.0) {
         return false;
      } else {
         Optional<ManasRaceInstance> race = RaceAPI.getRaceFrom(target).getRace();
         if (race.isPresent()) {
            return TensuraStorages.getExistenceFrom(target).getAlignment().equals(Alignment.MAJIN) ? true : !race.get().is(TensuraRaceTags.HUMAN_LIKE);
         } else {
            return target.getType().is(TensuraEntityTags.HERO_BOSS);
         }
      }
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(PHASE, 0);
      builder.define(FLYING, false);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("Phase", this.getPhase());
      compound.putBoolean("Flying", this.isFlying());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (this.hasCustomName()) {
         this.bossEvent.setName(this.getDisplayName());
      }

      this.setPhase(compound.getInt("Phase"));
      this.setFlying(compound.getBoolean("Flying"));
   }

   public int getPhase() {
      return (Integer)this.entityData.get(PHASE);
   }

   public void setPhase(int phase) {
      this.entityData.set(PHASE, phase);
   }

   public boolean isFlying() {
      return (Boolean)this.entityData.get(FLYING);
   }

   public void setFlying(boolean flying) {
      this.entityData.set(FLYING, flying);
   }

   public boolean wasFlying() {
      return this.wasFlying;
   }

   public boolean shouldStopFlying(Mob entity) {
      return super.shouldStopFlying(entity) || this.isOrderedToSit() || this.isInLove();
   }

   public boolean canFly() {
      return this.getPhase() == 2;
   }

   public List<ManasSkill> getUniqueSkills() {
      return List.of((ManasSkill)UniqueSkills.ROYAL_BEAST.get());
   }

   public boolean isOnFire() {
      return false;
   }

   public boolean wantsToPickUp(ItemStack pStack) {
      return false;
   }

   public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
      return false;
   }

   public boolean canBeAffected(MobEffectInstance instance) {
      if (instance.is(TensuraMobEffects.getReference(TensuraMobEffects.INFECTION))) {
         return true;
      } else if (instance.getEffect().is(MobEffects.IGNORE_ANTI_MAGICULE)) {
         return true;
      } else {
         return instance.getEffect().is(MobEffects.SKILL_BUFF) ? true : !((MobEffect)instance.getEffect().value()).isBeneficial();
      }
   }

   public boolean hurt(DamageSource pSource, float pAmount) {
      if (this.isInvulnerableTo(pSource)) {
         return false;
      } else {
         return this.shouldDodge(pSource) ? false : super.hurt(pSource, pAmount);
      }
   }

   protected void actuallyHurt(DamageSource source, float damage) {
      float multiplier = 1.0F;
      if (source.tensura$getMagicType() == MagicType.ASPECTUAL || source.tensura$getMagicType() == MagicType.SPIRITUAL) {
         multiplier *= 0.5F;
      }

      super.actuallyHurt(source, damage * multiplier);
   }

   private boolean shouldDodge(DamageSource source) {
      if (this.level().isClientSide()) {
         return false;
      }

      if (this.getAttributeValue(Attributes.MOVEMENT_SPEED) <= 0.0) {
         return false;
      }

      if (source.getDirectEntity() instanceof LivingEntity entity) {
         if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
         }

         if (source.isCreativePlayer()) {
            return false;
         }

         double negate = entity.getAttributeValue(TensuraAttributes.DODGE_NEGATE_CHANCE);
         if (entity.getRandom().nextFloat() * 100.0F < negate) {
            return false;
         }

         if (entity.getRandom().nextFloat() >= 0.15 * this.getPhase()) {
            return false;
         }

         if (this.getTarget() != null) {
            this.teleportTowards(this, this.getTarget(), 5.0);
         }

         this.invulnerableTime = 60;
         return true;
      } else {
         return false;
      }
   }

   public boolean shouldCountMotionBlock() {
      return this.getPhase() != 2 ? true : this.getTarget() != null && this.getTarget().onGround();
   }

   public boolean isPushedByFluid() {
      return false;
   }

   public boolean prefersOnLand() {
      return true;
   }

   public boolean canOpenDoor() {
      return true;
   }

   @Nullable
   public SoundEvent getTeleportSound() {
      return (SoundEvent)TensuraSoundEvents.INSTANT_MOVE.get();
   }

   public void setCustomName(@Nullable Component pName) {
      super.setCustomName(pName);
      this.bossEvent.setName(this.getDisplayName());
   }

   public void startSeenByPlayer(ServerPlayer pPlayer) {
      super.startSeenByPlayer(pPlayer);
      if (!this.isTame()) {
         this.bossEvent.addPlayer(pPlayer);
      }
   }

   public void stopSeenByPlayer(ServerPlayer pPlayer) {
      super.stopSeenByPlayer(pPlayer);
      this.bossEvent.removePlayer(pPlayer);
   }

   protected void applyTamingSideEffects() {
      super.applyTamingSideEffects();
      this.bossEvent.removeAllPlayers();
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      this.tickBrain(this);
      if (!this.isTame()) {
         this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
      }

      if (this.getPhase() == 2) {
         this.bossEvent.setColor(BossBarColor.WHITE);
         this.bossEvent.setOverlay(BossBarOverlay.NOTCHED_10);
      }
   }

   protected boolean canRide(Entity entity) {
      return false;
   }

   public boolean shouldBroadcastTeleport() {
      return false;
   }

   public DamageSource getBaseDamageSource() {
      DamageSource source = super.getBaseDamageSource().tensura$setResistanceBypassLevel(1.0F);
      if (this.getPhase() == 2) {
         source = source.tensura$setBarrierBypassLevel(1.0F);
      }

      return source;
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         this.phaseHandler();
         this.handleFlying(this);
         if (this.tickCount % 20 == 0) {
            if (!this.isAlive()) {
               return;
            }

            if (this.getY() < this.level().getMinBuildHeight() - 100) {
               this.discard();
               return;
            }

            if (this.tickCount % 200 == 0) {
               this.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT), 240, 1, false, false, false));
            }

            IExistence existence = TensuraStorages.getExistenceFrom(this);
            if (this.getHealth() >= this.getMaxHealth() && existence.getSpiritualHealth() >= this.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH)) {
               return;
            }

            if (this.getPhase() == 2) {
               this.heal(4.0F);
               existence.setSpiritualHealth(existence.getSpiritualHealth() + 20.0);
            } else {
               this.heal(12.0F);
               existence.setSpiritualHealth(existence.getSpiritualHealth() + 60.0);
            }
         }
      }
   }

   protected void phaseHandler() {
      switch (this.getPhase()) {
         case 0:
            this.setPhase(1);
            break;
         case 1:
            if (!this.isAlive()) {
               return;
            }

            LivingEntity target = this.getTarget();
            if (target != null) {
               this.lookAt(Anchor.EYES, target.getEyePosition());
            }

            if (this.getHealth() <= this.getMaxHealth() * 0.5) {
               this.enterLastPhase();
            }
      }
   }

   private void enterLastPhase() {
      this.invulnerableTime = 100;
      CookSkill.removeCookedHP(this);
      AttributeInstance shpRegen = this.getAttribute(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION);
      if (shpRegen != null) {
         shpRegen.setBaseValue(shpRegen.getBaseValue() + 30.0);
      }

      EnergyHelper.gainMagicule(this, 100000.0, GainType.MAX);
      EnergyHelper.gainAura(this, 100000.0, GainType.MAX);
      IExistence existence = TensuraStorages.getExistenceFrom(this);
      existence.setMagicule(EnergyHelper.getMaxMagicule(this));
      existence.setAura(EnergyHelper.getMaxAura(this));
      this.setHealth(this.getMaxHealth());
      double SHP = this.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
      if (existence.getSpiritualHealth() < SHP * ChosenOneSkill.CONFIG.shpMultiplier) {
         existence.setSpiritualHealth(SHP * ChosenOneSkill.CONFIG.shpMultiplier);
      }

      existence.markDirty();
      AttributeInstance armor = this.getAttribute(Attributes.ARMOR);
      if (armor != null) {
         armor.setBaseValue(armor.getBaseValue() + 20.0);
      }

      AttributeInstance attack = this.getAttribute(Attributes.ATTACK_DAMAGE);
      if (attack != null) {
         attack.setBaseValue(attack.getBaseValue() + 20.0);
      }

      AttributeInstance knock = this.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
      if (knock != null) {
         knock.setBaseValue(knock.getBaseValue() + 0.2);
      }

      TensuraStorages.resetEffect(this);
      this.dead = false;
      this.deathTime = 0;
      this.unsetRemoved();
      this.setPhase(2);
      this.switchNavigator(this, this.isInWaterOrBubble(), false);
      AABB aabb = this.getBoundingBox().inflate(this.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE) + 6.0);
      List<LivingEntity> list = this.level()
         .getEntitiesOfClass(LivingEntity.class, aabb, entity -> !entity.isAlliedTo(this) && entity != this.getOwner() && entity != this);
      DamageSource source = TensuraDamageTypes.getEntityDamageSource(this.level(), DamageTypes.EXPLOSION, this);

      for (LivingEntity target : list) {
         target.hurt(source, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0F);
         target.setDeltaMovement(0.0, 0.2, 0.0);
         SkillHelper.knockBack(this, target, 3.0F);
      }

      this.level()
         .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.TRANSFORM_BEAST.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.EXPLOSION_EMITTER);
      TensuraParticleHelper.spawnServerParticles(
         this.level(), (ParticleOptions)TensuraParticleTypes.LIGHTNING_SPARK.get(), this.getX(), this.getY(), this.getZ(), 55, 0.08, 0.08, 0.08, 0.5, true
      );
      TensuraParticleHelper.spawnServerParticles(
         this.level(),
         (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(),
         this.getX(),
         this.getY(),
         this.getZ(),
         55,
         0.08,
         0.08,
         0.08,
         0.5,
         true
      );
      TensuraParticleHelper.spawnServerParticles(
         this.level(),
         TensuraParticleUtils.getGoldWave(1.0F, this.getBbWidth() * 7.0F, -0.5F, false),
         this.getX(),
         this.getY() + this.getBbHeight() / 2.0F,
         this.getZ()
      );
   }

   public void auraSlashAttack(LivingEntity target, double multishot, boolean negativeAngle) {
      AuraSlashProjectile slash = new AuraSlashProjectile(this.level(), this);
      slash.setSkill(SkillUtils.getSkillOrNull(this, (ManasSkill)MeleeArts.AURA_SLASH.get()));
      float radius = 2.0F;
      float angle = (float) (Math.PI / 180.0) * this.yBodyRot;
      double extraX = radius * Mth.sin((float)(Math.PI + angle));
      double extraZ = radius * Mth.cos(angle);
      slash.setPos(this.getX() + extraX, this.getEyeY() - 0.5, this.getZ() + extraZ);
      if (multishot > 0.0) {
         int rot = negativeAngle ? -145 : 145;
         float yaw = this.getYRot() * (float) (Math.PI / 180.0);
         float f3 = Mth.sin((float)(yaw + Math.toRadians(rot)));
         float f = -Mth.cos((float)(yaw + Math.toRadians(rot)));
         slash.setPos(slash.getX() + f3 * multishot, slash.getY(), slash.getZ() + f * multishot);
         double d = target.getX() - this.getX();
         double e = target.getY() - slash.getY();
         double g = target.getZ() - this.getZ();
         double h = Math.sqrt(d * d + g * g);
         slash.shoot(d, e + h * 0.2, g, 1.0F, 0.0F);
      } else {
         slash.shootToward(target, 1.0F, 0.0F);
      }

      slash.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
      slash.setNoGravity(true);
      this.level().addFreshEntity(slash);
      this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   private void spawnElephantAuraBullets(LivingEntity entity, Vec3 pos) {
      for (int i = 0; i < 8; i++) {
         Vec3 arrowPos = entity.getEyePosition().add(new Vec3(0.0, 1.0, 0.0).zRot((45 * i - 22.5F) * (float) (Math.PI / 180.0)).xRot((float) (Math.PI / 2)));
         AuraBulletProjectile bullet = new AuraBulletProjectile(entity.level(), entity);
         bullet.setSpeed(0.75F);
         bullet.setPos(arrowPos);
         bullet.shootFromRot(pos.subtract(arrowPos).normalize());
         bullet.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) / 2.0F);
         bullet.setExplosionRadius(4.0F);
         bullet.setSize(0.5F);
         bullet.setColor(7747016);
         bullet.setSkill(SkillUtils.getSkillOrNull(this, (ManasSkill)ProjectileArts.ELEPHANT_STAMPEDE.get()));
         bullet.setApCost(100.0);
         entity.level().addFreshEntity(bullet);
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      }
   }

   public boolean randomTeleport(LivingEntity entity, double pX, double pY, double pZ, boolean pBroadcastTeleport) {
      boolean randomTeleport = super.randomTeleport(entity, pX, pY, pZ, pBroadcastTeleport);
      if (randomTeleport && !this.onGround()) {
         this.setFlying(true);
         this.switchNavigator(this, this.isInWaterOrBubble(), false);
      }

      return randomTeleport;
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      InteractionResult eating = this.handleEating(player, hand, itemstack);
      if (eating.consumesAction()) {
         return eating;
      }

      if (!this.level().isClientSide) {
         if (this.isTame() && this.isOwnedBy(player)) {
            InteractionResult interaction = this.getInventoryInteraction(player, hand);
            if (interaction != InteractionResult.PASS) {
               return interaction;
            }

            this.cycleCommands(this, player);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
         } else {
            return super.mobInteract(player, hand);
         }
      } else {
         boolean flag = this.isOwnedBy(player) || this.isTame();
         return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
      }
   }

   public void ate() {
      super.ate();
      this.heal(3.0F);
   }

   public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
      return false;
   }

   @NotNull
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData) {
      if (!pReason.equals(MobSpawnType.BUCKET)) {
         this.populateDefaultEquipmentSlots(this.random, pDifficulty);
      }

      return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
   }

   protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
      ItemStack stack = new ItemStack((ItemLike)NebItems.WHITE_TIGER_AZURE_DRAGON_HALBERD.get());
      this.inventory.setItem(this.getSlotId(EquipmentSlot.MAINHAND), stack);
      this.updateContainerEquipment();
   }

   protected float getEquipmentDropChance(EquipmentSlot pSlot) {
      return this.isTame() && !this.isTamedByNonPlayer() ? 0.0F : 2.0F;
   }

   public void die(DamageSource source) {
      if (!source.is(DamageTypes.FELL_OUT_OF_WORLD) && !source.is(DamageTypes.GENERIC_KILL) && this.getPhase() < 2) {
         this.enterLastPhase();
      } else {
         super.die(source);
      }
   }

   @NotNull
   protected Provider<?> brainProvider() {
      return new SmartBrainProvider(this, true);
   }

   public List<ExtendedSensor<CarrionEntity>> getSensors() {
      return ObjectArrayList.of(new ExtendedSensor[]{new NearbyLivingEntitySensor(), new HurtBySensor()});
   }

   public BrainActivityGroup<CarrionEntity> getCoreTasks() {
      return BrainActivityGroup.coreTasks(
         new Behavior[]{
            new LookAtTarget(),
            new FirstApplicableBehaviour(
               new ExtendedBehaviour[]{
                  new MoveOrFlyToWalkTarget().startCondition(entity -> !entity.isOrderedToSit() && !entity.isSleeping()).stopIf(TamableAnimal::isOrderedToSit),
                  new MoveToWalkTarget()
                     .cooldownFor(entity -> 0)
                     .startCondition(entity -> !entity.isOrderedToSit() && !entity.isSleeping())
                     .stopIf(TamableAnimal::isOrderedToSit)
               }
            )
         }
      );
   }

   public BrainActivityGroup<CarrionEntity> getIdleTasks() {
      return BrainActivityGroup.idleTasks(
         new Behavior[]{
            new FirstApplicableBehaviour(
               new ExtendedBehaviour[]{
                  TensuraBehaviourHelper.getMoveToWanderPos(),
                  TensuraBehaviourHelper.getPreyTargeting(this, this::shouldTarget),
                  new SubordinateFollowOwner().canTeleportOffGroundWhen(entity -> {
                     if (entity.canFly()) {
                        entity.setFlying(true);
                        return true;
                     } else {
                        return false;
                     }
                  }),
                  new SetPlayerLookTarget(),
                  new SetRandomLookTarget()
               }
            ),
            new InteractWithDoor(),
            new HumanoidConsumeItem().startCondition(PlayerLikeEntity::shouldHeal).stopIf(entity -> !entity.shouldHeal()),
            new OneRandomBehaviour(
               new ExtendedBehaviour[]{
                  new FirstApplicableBehaviour(
                     new ExtendedBehaviour[]{
                        new SetRandomFlyAndWalkTarget().verticalWeight(entity -> 1).startCondition(CarrionEntity::canFly),
                        new SetRandomSwimAndWalkTarget()
                           .speedModifier((entity, pos) -> entity.isInWaterOrBubble() ? 1.5F : 1.0F)
                           .cooldownFor(entity -> 0)
                           .startCondition(entity -> !entity.isOrderedToSit())
                           .stopIf(TamableAnimal::isOrderedToSit)
                     }
                  ),
                  new Idle().runFor(entity -> entity.getRandom().nextInt(30, 60))
               }
            )
         }
      );
   }

   public BrainActivityGroup<CarrionEntity> getFightTasks() {
      return BrainActivityGroup.fightTasks(
         new Behavior[]{
            new InvalidateNeutralAttackTarget().invalidateIf(this::shouldStopTarget),
            new TeleportToEntity()
               .following(Mob::getTarget)
               .canTeleportOffGroundWhen(CarrionEntity::canFly)
               .teleportRadius((entity, target) -> 5)
               .teleportToTargetAfter((entity, target) -> {
                  if (entity.tickCount % 200 == 0) {
                     return 10.0;
                  } else {
                     return entity.tickCount % 20 == 0 ? 20.0 : 32.0;
                  }
               })
               .onSuccessTeleport(
                  (entity, target) -> {
                     TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.SWEEP_ATTACK, 1.0);
                     entity.level()
                        .playSound(
                           null,
                           entity.getX(),
                           entity.getY(),
                           entity.getZ(),
                           (SoundEvent)TensuraSoundEvents.INSTANT_MOVE.get(),
                           SoundSource.NEUTRAL,
                           1.0F,
                           1.0F
                        );
                  }
               ),
            new StrafeTarget().stopStrafingWhen(entity -> !entity.usingRangedWeapon()).startCondition(PlayerLikeEntity::usingRangedWeapon),
            new SetWalkTargetToAttackTarget().speedMod((owner, target) -> 2.0F).startCondition(entity -> !entity.usingRangedWeapon()),
            new FirstApplicableBehaviour(
               new ExtendedBehaviour[]{
                  new FirstApplicableBehaviour(
                     new ExtendedBehaviour[]{
                        new FirstApplicableBehaviour(
                              new ExtendedBehaviour[]{
                                 new CustomRangeAttack(20).maxAttackRadius(24.0F).attackInterval(entity -> 20).performAttack((entity, target) -> {
                                    if (entity.distanceTo(target) > this.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE)) {
                                       entity.teleportTowards(entity, target, 5.0);
                                    }

                                    entity.doHurtTarget(target, 10.0F);
                                    TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.EXPLOSION_EMITTER, 1.0);
                                    entity.level()
                                       .playSound(
                                          null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 5.0F, 1.0F
                                       );
                                 }).startCondition(entity -> entity.getRandom().nextFloat() <= 0.05).whenStarting(
                                    entity -> entity.triggerAnim("miscController", "lion_punch")
                                 ),
                                 new CustomHeldAttack()
                                    .minAttackRadius(0.0F)
                                    .maxAttackRadius(20.0F)
                                    .attackInterval(entity -> 60)
                                    .onTick(
                                       (entity, target, tick) -> {
                                          if (tick > 5) {
                                             if (tick % 3 == 0) {
                                                TensuraParticleHelper.addServerParticlesAroundSelf(
                                                   this, (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(), 1.0
                                                );
                                                TensuraParticleHelper.addServerParticlesAroundSelf(
                                                   this, (ParticleOptions)TensuraParticleTypes.LIGHTNING_SPARK.get(), 1.0
                                                );
                                             }

                                             if (tick >= 20) {
                                                BeamProjectile.spawnLastingBeam(
                                                   (EntityType)MiscEntityTypes.SOLAR_BEAM.get(),
                                                   (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 10.0F,
                                                   3.0F,
                                                   50.0F,
                                                   entity,
                                                   SkillUtils.getSkillOrNull(this, (ManasSkill)MeleeArts.ROARING_LION_PUNCH.get()),
                                                   0,
                                                   Pair.of(0.0, 0.0),
                                                   Pair.of(0.0, 0.0),
                                                   tick
                                                );
                                                entity.level()
                                                   .playSound(
                                                      null,
                                                      this.getX(),
                                                      this.getY(),
                                                      this.getZ(),
                                                      TensuraSoundEvents.CAST_LIGHT,
                                                      SoundSource.NEUTRAL,
                                                      1.0F,
                                                      1.0F
                                                   );
                                             }
                                          }

                                          return tick < 30;
                                       }
                                    )
                                    .startCondition(entity -> entity.getRandom().nextFloat() <= 0.051)
                                    .whenStarting(entity -> entity.triggerAnim("miscController", "roar"))
                              }
                           )
                           .startCondition(entity -> entity.getPhase() == 2),
                        new CustomHeldAttack().minAttackRadius(0.0F).maxAttackRadius(15.0F).attackInterval(entity -> 60).onTick((entity, target, tick) -> {
                           if (tick < 10) {
                              TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.ELECTRIC_SPARK, 1.0);
                           } else if (tick % 10 == 0) {
                              entity.swing(InteractionHand.MAIN_HAND, true);
                              entity.spawnElephantAuraBullets(this, this.getEyePosition().add(0.0, 6.0, 0.0));
                           }

                           return tick < 40;
                        }).startCondition(entity -> entity.getRandom().nextFloat() <= 0.1).whenStarting(
                           entity -> entity.triggerAnim("miscController", "stampede")
                        ),
                        new CustomRangeAttack(10).maxAttackRadius(64.0F).attackInterval(entity -> 40).performAttack((entity, target) -> {
                           if (entity.distanceTo(target) < this.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE) && entity.doHurtTarget(target, 3.0F)) {
                              SkillHelper.knockBack(entity, target, 5.0F);
                           }

                           entity.auraSlashAttack(target, 0.1, false);
                           entity.auraSlashAttack(target, 0.6, false);
                           entity.auraSlashAttack(target, 0.6, true);
                        }).startCondition(entity -> entity.getRandom().nextFloat() <= 0.2).whenStarting(entity -> entity.triggerAnim("miscController", "talon")),
                        new CustomRangeAttack(5).maxAttackRadius(32.0F).attackInterval(entity -> 20).performAttack((entity, target) -> {
                           if (entity.distanceTo(target) < this.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE) && entity.doHurtTarget(target, 2.0F)) {
                              SkillHelper.knockBack(entity, target, 2.0F);
                           }

                           entity.auraSlashAttack(target, 0.0, false);
                        }).startCondition(entity -> entity.getRandom().nextFloat() <= 0.3).whenStarting(entity -> entity.triggerAnim("miscController", "swing"))
                     }
                  ),
                  PlayerLikeEntity.getSpearAttack(20).attackInterval(entity -> 5).attackRadius(25.0F),
                  PlayerLikeEntity.getCrossbowAttack().attackInterval(entity -> 10).attackRadius(30.0F),
                  PlayerLikeEntity.getBowAttack().attackInterval(entity -> 10).attackRadius(25.0F),
                  new AnimatableMeleeAttack(5)
                     .attackInterval(entity -> 10)
                     .whenStarting(entity -> entity.triggerAnim("miscController", entity.getRandom().nextBoolean() ? "poke" : "swing"))
                     .startCondition(entity -> !entity.usingRangedWeapon())
               }
            )
         }
      );
   }

   public boolean isInFlyingPose() {
      if (this.onGround() || this.isNoAi()) {
         return false;
      } else {
         return !this.isFlying() ? false : !this.isInSittingPose() && !this.isOrderedToSit();
      }
   }

   protected PlayState loopController(AnimationState<CarrionEntity> state) {
      String name;
      if (this.isInFlyingPose()) {
         if (state.isMoving()) {
            if (this.isInLiquid() || !this.isAngry() && !this.isSprinting()) {
               name = "animation.royal_beast_carrion.fly_slow";
            } else {
               name = "animation.royal_beast_carrion.fly_fast";
            }
         } else {
            name = "animation.royal_beast_carrion.idle_fly";
         }
      } else if (state.isMoving()) {
         if (this.isInLiquid() || !this.isAngry() && !this.isSprinting()) {
            name = "animation.royal_beast_carrion.walk";
         } else {
            name = "animation.royal_beast_carrion.run";
         }
      } else {
         name = "animation.royal_beast_carrion.idle";
      }

      return state.setAndContinue(RawAnimation.begin().thenLoop(name));
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController[]{
            new AnimationController(this, "loopController", 10, this::loopController),
            new AnimationController(this, "miscController", 3, event -> PlayState.STOP)
               .triggerableAnim("poke", RawAnimation.begin().then("animation.royal_beast_carrion.halberd_poke", LoopType.PLAY_ONCE))
               .triggerableAnim("swing", RawAnimation.begin().then("animation.royal_beast_carrion.halberd_swing", LoopType.PLAY_ONCE))
               .triggerableAnim("stampede", RawAnimation.begin().then("animation.royal_beast_carrion.elephant_stampede", LoopType.PLAY_ONCE))
               .triggerableAnim("lion_punch", RawAnimation.begin().then("animation.royal_beast_carrion.lion_punch", LoopType.PLAY_ONCE))
               .triggerableAnim("talon", RawAnimation.begin().then("animation.royal_beast_carrion.tiger_talon", LoopType.PLAY_ONCE))
               .triggerableAnim("roar", RawAnimation.begin().then("animation.royal_beast_carrion.beast_roar", LoopType.PLAY_ONCE))
         }
      );
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   @Generated
   public boolean isLandNavigating() {
      return this.landNavigating;
   }

   @Generated
   public void setLandNavigating(boolean landNavigating) {
      this.landNavigating = landNavigating;
   }

   @Generated
   public int getSwimmingTick() {
      return this.swimmingTick;
   }

   @Generated
   public void setSwimmingTick(int swimmingTick) {
      this.swimmingTick = swimmingTick;
   }

   @Generated
   public void setFlyingTick(int flyingTick) {
      this.flyingTick = flyingTick;
   }

   @Generated
   public int getFlyingTick() {
      return this.flyingTick;
   }

   @Generated
   public void setWasFlying(boolean wasFlying) {
      this.wasFlying = wasFlying;
   }
}
