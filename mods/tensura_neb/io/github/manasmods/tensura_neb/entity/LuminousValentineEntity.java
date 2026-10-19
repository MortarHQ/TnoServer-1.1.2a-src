package io.github.manasmods.tensura_neb.entity;

import com.mojang.datafixers.util.Pair;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.magic.Magic.MagicType;
import io.github.manasmods.tensura.ability.skill.intrinsic.CharmSkill;
import io.github.manasmods.tensura.ability.skill.unique.CookSkill;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.data.TensuraTags.MobEffects;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
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
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.magic.barrier.DisintegrationEntity;
import io.github.manasmods.tensura.entity.magic.beam.BloodRayProjectile;
import io.github.manasmods.tensura.entity.magic.field.DeathBlessingField;
import io.github.manasmods.tensura.entity.magic.field.ShadowBindHands;
import io.github.manasmods.tensura.entity.template.GreaterSpiritEntity;
import io.github.manasmods.tensura.entity.template.PlayerLikeEntity;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.entity.template.subclass.IElementalSpirit;
import io.github.manasmods.tensura.entity.template.subclass.IFlying;
import io.github.manasmods.tensura.entity.template.subclass.IFlyingAmphibian;
import io.github.manasmods.tensura.entity.template.subclass.ITeleportation;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.race.RaceUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.magic.SpiritualMagics;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.EnergyHelper.DrainType;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import io.github.manasmods.tensura_neb.item.NightRoseItem;
import io.github.manasmods.tensura_neb.registry.NebItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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

public class LuminousValentineEntity extends OtherworlderEntity implements SmartBrainOwner<LuminousValentineEntity>, ITeleportation, IFlyingAmphibian {
   private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(LuminousValentineEntity.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(LuminousValentineEntity.class, EntityDataSerializers.BOOLEAN);
   private boolean landNavigating = false;
   private int swimmingTick = 0;
   protected int flyingTick;
   protected boolean wasFlying;
   public int disintegrationCooldown = 0;
   public int embraceDrainCooldown = 0;
   public int deathBlessCooldown = 0;
   private UUID disintegrationUUID = null;
   private final ServerBossEvent bossEvent = (ServerBossEvent)new ServerBossEvent(this.getDisplayName(), BossBarColor.WHITE, BossBarOverlay.NOTCHED_20)
      .setPlayBossMusic(true);

   public LuminousValentineEntity(EntityType<? extends LuminousValentineEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.xpReward = 20000;
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
         .add(Attributes.ARMOR, 50.0)
         .add(Attributes.ATTACK_DAMAGE, 100.0)
         .add(Attributes.MAX_HEALTH, 8000.0)
         .add(Attributes.MOVEMENT_SPEED, 0.35F)
         .add(Attributes.FLYING_SPEED, 0.7F)
         .add(Attributes.FOLLOW_RANGE, 64.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.8F)
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
      } else {
         return target instanceof Mob mob && mob.getTarget() == this ? true : EnergyHelper.getMaxEP(target) > 10000.0;
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
      if (this.disintegrationUUID != null) {
         compound.putUUID("Disintegration", this.disintegrationUUID);
      }

      compound.putBoolean("Flying", this.isFlying());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (this.hasCustomName()) {
         this.bossEvent.setName(this.getDisplayName());
      }

      this.setPhase(compound.getInt("Phase"));
      if (compound.contains("Disintegration")) {
         this.disintegrationUUID = compound.getUUID("Disintegration");
      }

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

   public ResourceLocation getTextureLocation() {
      return ResourceLocation.fromNamespaceAndPath("tensura_neb", "textures/entity/luminous_valentine.png");
   }

   public List<ManasSkill> getUniqueSkills() {
      return List.of((ManasSkill)UniqueSkills.LUST.get());
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
      }

      if (pSource.getEntity() instanceof IElementalSpirit) {
         return false;
      }

      if (this.shouldDodge(pSource)) {
         return false;
      }

      boolean hurt = super.hurt(pSource, pAmount);
      if (hurt) {
         if (!this.level().isClientSide() && this.disintegrationUUID != null) {
            Entity entity = ((ServerLevel)this.level()).getEntity(this.disintegrationUUID);
            if (entity instanceof DisintegrationEntity disintegration && disintegration.getAge() < 215) {
               TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.EXPLOSION_EMITTER, 3.0);
               TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.EXPLOSION, 3.0);
               disintegration.remove();
               this.disintegrationCooldown = 1000;
               this.disintegrationUUID = null;
               this.level()
                  .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.BARRIER_BREAK.get(), SoundSource.PLAYERS, 5.0F, 1.0F);
            }
         }

         if (pSource.getEntity() instanceof LivingEntity damageSource) {
            if (!damageSource.isAlive() || this.isAlliedTo(damageSource)) {
               return true;
            }

            if (damageSource instanceof Player player && (player.isCreative() || player.isSpectator())) {
               return true;
            }

            List<GreaterSpiritEntity> list = this.level()
               .getEntitiesOfClass(GreaterSpiritEntity.class, this.getBoundingBox().inflate(32.0), entity -> !entity.isTame());
            if (!list.isEmpty()) {
               list.forEach(spirit -> spirit.setTarget(damageSource));
            }
         }
      }

      return hurt;
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

      if (this.shouldStopMoving()) {
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

         if (entity.getRandom().nextFloat() >= 0.125 * this.getPhase()) {
            return false;
         }

         this.level()
            .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.INSTANT_MOVE.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
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
      return this.getTarget() != null && this.getTarget().onGround();
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
         this.bossEvent.setColor(BossBarColor.RED);
         this.bossEvent.setOverlay(BossBarOverlay.NOTCHED_10);
      }
   }

   private boolean shouldStopMoving() {
      if (this.level().isClientSide() || this.disintegrationUUID == null) {
         return false;
      } else {
         return this.getTarget() == null
            ? false
            : ((ServerLevel)this.level()).getEntity(this.disintegrationUUID) instanceof DisintegrationEntity disintegration && disintegration.getAge() < 215;
      }
   }

   protected boolean canRide(Entity entity) {
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
         if (this.disintegrationCooldown > 0) {
            this.disintegrationCooldown--;
         }

         if (this.embraceDrainCooldown > 0) {
            this.embraceDrainCooldown--;
         }

         if (this.deathBlessCooldown > 0) {
            this.deathBlessCooldown--;
         }

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

            if (this.shouldStopMoving()) {
               return;
            }

            IExistence existence = TensuraStorages.getExistenceFrom(this);
            if (this.getHealth() >= this.getMaxHealth() && existence.getSpiritualHealth() >= this.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH)) {
               return;
            }

            if (this.tickCount % 200 == 0) {
               this.heal(500.0F);
               existence.setSpiritualHealth(existence.getSpiritualHealth() + 300.0);
               TensuraMobEffect.removePredicateEffect(this, effect -> !effect.is(MobEffects.AFFECTED_BY_ANTI_SKILL), 0.0);
               TensuraStorages.resetEffect(this);
               this.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.GLOWING, 30, 0, false, false, false));
               TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.COMPOSTER, 1.0);
               this.level()
                  .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_HEAL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
               TensuraParticleHelper.spawnServerParticles(
                  this.level(),
                  TensuraParticleUtils.getRedWave(0.9F, this.getBbWidth() * 3.0F, -0.5F, true),
                  this.getX(),
                  this.getY() + this.getBbHeight() * 0.33,
                  this.getZ()
               );
               TensuraParticleHelper.spawnServerParticles(
                  this.level(),
                  TensuraParticleUtils.getRedWave(0.9F, this.getBbWidth() * 3.0F, -0.5F, true),
                  this.getX(),
                  this.getY() + this.getBbHeight() * 0.66,
                  this.getZ()
               );
            } else if (this.getPhase() != 2) {
               this.heal(10.0F);
               existence.setSpiritualHealth(existence.getSpiritualHealth() + 50.0);
            } else {
               this.heal(20.0F);
               existence.setSpiritualHealth(existence.getSpiritualHealth() + 100.0);
            }

            existence.markDirty();
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

            if (this.getHealth() <= this.getMaxHealth() * 0.5 || TensuraStorages.getExistenceFrom(this).getMagicule() < EnergyHelper.getMaxMagicule(this) * 0.5
               )
             {
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

      TensuraMobEffect.removePredicateEffect(this, mobEffect -> ((MobEffect)mobEffect.value()).getCategory() == MobEffectCategory.HARMFUL, 0.0);
      IExistence existence = TensuraStorages.getExistenceFrom(this);
      existence.setAura(EnergyHelper.getMaxAura(this));
      existence.setMagicule(EnergyHelper.getMaxMagicule(this));
      existence.markDirty();
      this.setHealth(this.getMaxHealth());
      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(this).getSkill((ManasSkill)ExtraSkills.INFINITE_REGENERATION.get());
      optional.ifPresent(instance -> instance.setMastery(instance.getMaxMastery()));
      existence.setSpiritualHealth(this.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH));
      existence.markDirty();
      this.dead = false;
      this.deathTime = 0;
      this.unsetRemoved();
      this.disintegrationCooldown = 0;
      this.embraceDrainCooldown = 0;
      this.deathBlessCooldown = 0;
      this.setPhase(2);
      this.switchNavigator(this, this.isInWaterOrBubble(), false);
      this.inventory.setItem(this.getSlotId(EquipmentSlot.MAINHAND), ((NightRoseItem)NebItems.NIGHT_ROSE.get()).getDefaultInstance());
      this.updateContainerEquipment();
      AABB aabb = this.getBoundingBox().inflate(this.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE) + 6.0);
      List<LivingEntity> list = this.level()
         .getEntitiesOfClass(LivingEntity.class, aabb, entity -> !entity.isAlliedTo(this) && entity != this.getOwner() && entity != this);
      DamageSource source = TensuraDamageTypes.getEntityDamageSource(this.level(), TensuraDamageTypes.HOLY_DAMAGE, this).tensura$setBarrierBypassLevel(1.0F);

      for (LivingEntity target : list) {
         target.hurt(source, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0F);
         target.setDeltaMovement(0.0, 0.2, 0.0);
         SkillHelper.knockBack(this, target, 3.0F);
      }

      TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.END_ROD, 1.0);
      this.level()
         .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.DEFENCE_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      TensuraParticleHelper.spawnServerParticles(
         this.level(),
         TensuraParticleUtils.getGoldWave(0.9F, this.getBbWidth() * 3.0F, -0.5F, true),
         this.getX(),
         this.getY() + this.getBbHeight() * 0.33,
         this.getZ()
      );
      TensuraParticleHelper.spawnServerParticles(
         this.level(),
         TensuraParticleUtils.getGoldWave(0.9F, this.getBbWidth() * 3.0F, -0.5F, true),
         this.getX(),
         this.getY() + this.getBbHeight() * 0.66,
         this.getZ()
      );
   }

   private void disintegration(LivingEntity target) {
      this.lookAt(Anchor.EYES, target.getEyePosition());
      if (this.disintegrationUUID == null || ((ServerLevel)this.level()).getEntity(this.disintegrationUUID) == null) {
         DisintegrationEntity disintegration = new DisintegrationEntity(this.level(), this);
         disintegration.setSize(3.0F);
         disintegration.setHeight(50.0F);
         disintegration.setLife(310);
         Vec3 pos = ObjectSelectionHelper.getNearestGround(target.position(), this.level(), 10.0, target);
         disintegration.setPos(pos);
         this.level().addFreshEntity(disintegration);
         this.disintegrationUUID = disintegration.getUUID();
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), 40, 9, false, false, false));
         target.moveTo(disintegration.position());
         target.setDeltaMovement(Vec3.ZERO);
         target.hurtMarked = true;
         if (this.getY() < pos.y - 1.0) {
            this.level().playSound(null, this.xo, this.yo, this.zo, (SoundEvent)TensuraSoundEvents.INSTANT_MOVE.get(), this.getSoundSource(), 1.0F, 1.0F);
            this.moveTo(ObjectSelectionHelper.getNearestGround(this.position(), this.level(), 10.0, target));
            this.playSound((SoundEvent)TensuraSoundEvents.INSTANT_MOVE.get(), 1.0F, 1.0F);
         }

         this.getNavigation().stop();
      }

      this.disintegrationCooldown = 1000;
   }

   public void embraceDrain(LivingEntity target) {
      this.embraceDrainCooldown = 800;
      this.teleportTowards(this, target, 3.0);
      TensuraMobEffect.addEffect(
         target,
         new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.LUST_EMBRACEMENT), 100, 1, false, false, false),
         this,
         (ManasSkill)UniqueSkills.LUST.get(),
         0
      );
      TensuraMobEffect.addEffect(
         this,
         new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.LUST_EMBRACEMENT), 100, 1, false, false, false),
         this,
         (ManasSkill)UniqueSkills.LUST.get(),
         0
      );
      this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   private void shadowBind(LivingEntity target) {
      this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.CAST_DARK.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.SQUID_INK);
      if (!SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.DARKNESS_ATTACK_NULLIFICATION.get())) {
         ShadowBindHands hands = new ShadowBindHands(this.level(), this);
         hands.setTarget(target);
         hands.setPos(target.position());
         hands.setSkill(SkillUtils.getSkillOrNull(this, (ManasSkill)SpiritualMagics.SHADOW_BIND.get()));
         float damage = (float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) + target.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH) * 0.01F);
         hands.setDamage(damage);
         hands.setLife(200);
         hands.setSize(target.getBbWidth() / 0.6F);
         hands.setVisualSize(target.getBbHeight() / 1.8F);
         int bindLevel = SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.DARKNESS_ATTACK_RESISTANCE.get()) ? 2 : 10;
         MobEffectInstance slowness = new MobEffectInstance(
            TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), 25, bindLevel, false, false, false
         );
         hands.setMobEffect(slowness);
         this.level().addFreshEntity(hands);
         hands.triggerAnim("controller", "start");
         this.swing(InteractionHand.MAIN_HAND, true);
      }
   }

   private void mindControlSurrounding() {
      LivingEntity livingTarget = this.getTarget();
      if (livingTarget != null) {
         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 3.0F, 1.0F);
         List<Mob> list = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(20.0), mob -> !mob.is(this) && mob.isAlive());
         if (!list.isEmpty()) {
            for (Mob target : list) {
               if (!CharmSkill.isMindControlFailed(this, target, this.level(), true)
                  && !RaceUtils.isSpiritual(target)
                  && !SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get())) {
                  target.setAggressive(true);
                  target.setTarget(livingTarget);
                  if (target instanceof NeutralMob neutralMob) {
                     neutralMob.setTarget(livingTarget);
                  }

                  TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.DAMAGE_INDICATOR);
               }
            }
         }
      }
   }

   private void bloodRay(LivingEntity target) {
      this.lookAt(Anchor.EYES, target.getEyePosition());
      BloodRayProjectile ray = new BloodRayProjectile(this.level(), this);
      ray.setFollowingOwner(true);
      ray.setSize(0.2F);
      ray.setLife(60);
      float damage = (float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0);
      ray.setDamage(damage);
      ray.setRange(40.0F);
      ray.setPos(this.getEyePosition());
      ray.setSkill(SkillUtils.getSkillOrNull(this, (ManasSkill)SpiritualMagics.SWIPE.get()));
      ray.setMpCost(2000.0);
      this.level().addFreshEntity(ray);
      if (this.tickCount % 5 == 0) {
         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.BLOOD_RAY.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
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

   public List<ExtendedSensor<LuminousValentineEntity>> getSensors() {
      return ObjectArrayList.of(new ExtendedSensor[]{new NearbyLivingEntitySensor(), new HurtBySensor()});
   }

   public BrainActivityGroup<LuminousValentineEntity> getCoreTasks() {
      return BrainActivityGroup.coreTasks(
         new Behavior[]{
            new LookAtTarget(),
            new FirstApplicableBehaviour(
               new ExtendedBehaviour[]{
                  new MoveOrFlyToWalkTarget()
                     .startCondition(entity -> !entity.isOrderedToSit() && !entity.isSleeping() && !entity.shouldStopMoving())
                     .stopIf(entity -> entity.isOrderedToSit() || entity.isSleeping() || entity.shouldStopMoving()),
                  new MoveToWalkTarget()
                     .cooldownFor(entity -> 0)
                     .startCondition(entity -> !entity.isOrderedToSit() && !entity.isSleeping() && !entity.shouldStopMoving())
                     .stopIf(entity -> entity.isOrderedToSit() || entity.isSleeping() || entity.shouldStopMoving())
               }
            )
         }
      );
   }

   public BrainActivityGroup<LuminousValentineEntity> getIdleTasks() {
      return BrainActivityGroup.idleTasks(
         new Behavior[]{
            new FirstApplicableBehaviour(
               new ExtendedBehaviour[]{
                  TensuraBehaviourHelper.getPreyTargeting(this, this::shouldTarget), new SubordinateFollowOwner().canTeleportOffGroundWhen(entity -> {
                     if (entity.canFly()) {
                        entity.setFlying(true);
                        return true;
                     } else {
                        return false;
                     }
                  }), TensuraBehaviourHelper.getMoveToWanderPos(), new SetPlayerLookTarget(), new SetRandomLookTarget()
               }
            ),
            new InteractWithDoor(),
            new HumanoidConsumeItem().startCondition(entity -> entity.shouldHeal()).stopIf(entity -> !entity.shouldHeal()),
            new OneRandomBehaviour(
               new ExtendedBehaviour[]{
                  new FirstApplicableBehaviour(
                     new ExtendedBehaviour[]{
                        new SetRandomFlyAndWalkTarget()
                           .verticalWeight(entity -> 1)
                           .startCondition(entity -> entity.canFly() && !entity.isOrderedToSit() && !entity.isSleeping() && !entity.shouldStopMoving()),
                        new SetRandomSwimAndWalkTarget()
                           .speedModifier((entity, pos) -> entity.isInWaterOrBubble() ? 1.5F : 1.0F)
                           .cooldownFor(entity -> 0)
                           .startCondition(entity -> !entity.isOrderedToSit() && !entity.isSleeping() && !entity.shouldStopMoving())
                     }
                  ),
                  new Idle().runFor(entity -> entity.getRandom().nextInt(30, 60))
               }
            )
         }
      );
   }

   public BrainActivityGroup<LuminousValentineEntity> getFightTasks() {
      return BrainActivityGroup.fightTasks(
         new Behavior[]{
            new InvalidateNeutralAttackTarget().invalidateIf(this::shouldStopTarget),
            new TeleportToEntity()
               .following(Mob::getTarget)
               .canTeleportOffGroundWhen(IFlying::canFly)
               .teleportRadius((entity, target) -> 5)
               .teleportToTargetAfter((entity, target) -> {
                  if (entity.tickCount % 200 == 0) {
                     return 10.0;
                  } else {
                     return entity.tickCount % 20 == 0 ? 20.0 : 32.0;
                  }
               })
               .onSuccessTeleport((entity, target) -> {
                  TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.END_ROD, 1.0);
                  entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);
               }),
            new StrafeTarget()
               .stopStrafingWhen(entity -> !entity.usingRangedWeapon())
               .startCondition(entity -> entity.usingRangedWeapon() && !entity.shouldStopMoving()),
            new SetWalkTargetToAttackTarget()
               .speedMod((owner, target) -> 2.0F)
               .startCondition(entity -> !entity.usingRangedWeapon() && !entity.shouldStopMoving()),
            new FirstApplicableBehaviour(
                  new ExtendedBehaviour[]{
                     new FirstApplicableBehaviour(
                        new ExtendedBehaviour[]{
                           new CustomHeldAttack()
                              .minAttackRadius(0.0F)
                              .maxAttackRadius(20.0F)
                              .attackInterval(entity -> 60)
                              .onTick(
                                 (entity, target, tick) -> {
                                    if (tick == 1) {
                                       entity.deathBlessCooldown = 600;
                                    }

                                    entity.level()
                                       .playSound(
                                          null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 1.0F, 1.0F
                                       );
                                    DeathBlessingField field = DeathBlessingField.getBlessing(
                                       3.0,
                                       100,
                                       10.0,
                                       entity,
                                       SkillUtils.getSkillOrNull(entity, (ManasSkill)UniqueSkills.LUST.get()),
                                       (TensuraSkill)UniqueSkills.LUST.get(),
                                       0
                                    );
                                    if (field != null) {
                                       field.setPos(target.position());
                                       field.setCastedTime(field.getCastedTime() + 1);
                                    }

                                    return tick < 100 && target.isAlive();
                                 }
                              )
                              .whenStopping(entity -> {
                                 ManasSkillInstance instance = SkillUtils.getSkillOrNull(entity, (ManasSkill)UniqueSkills.LUST.get());
                                 if (instance != null) {
                                    instance.getOrCreateTag().putInt("BlessingId", 0);
                                 }
                              })
                              .startCondition(entity -> entity.getPhase() == 2 && entity.getRandom().nextFloat() <= 0.2 && entity.deathBlessCooldown <= 0),
                           new CustomHeldAttack()
                              .minAttackRadius(0.0F)
                              .maxAttackRadius(7.0F)
                              .attackInterval(entity -> 60)
                              .requireInSight(true)
                              .onTick(
                                 (entity, target, tick) -> {
                                    if (tick == 1) {
                                       entity.embraceDrain(target);
                                    } else if (tick % 20 == 0 && EnergyHelper.drainEnergy(target, entity, 100000.0, false, DrainType.MAGICULE, GainType.NORMAL)
                                       )
                                     {
                                       DamageSource drainSource = TensuraDamageTypes.getEntityDamageSource(
                                          entity.level(), TensuraDamageTypes.ENERGY_DRAIN, entity
                                       );
                                       target.hurt(drainSource, (float)entity.getAttributeValue(Attributes.ATTACK_DAMAGE));
                                       entity.level()
                                          .playSound(
                                             null,
                                             entity.getX(),
                                             entity.getY(),
                                             entity.getZ(),
                                             (SoundEvent)TensuraSoundEvents.CAST_DARK.get(),
                                             SoundSource.PLAYERS,
                                             1.0F,
                                             1.0F
                                          );
                                    }

                                    return tick < 100 && target.isAlive();
                                 }
                              )
                              .startCondition(entity -> entity.getRandom().nextFloat() <= 0.2 && entity.embraceDrainCooldown <= 0),
                           new CustomRangeAttack(10)
                              .minAttackRadius(10.0F)
                              .maxAttackRadius(48.0F)
                              .attackInterval(entity -> 40)
                              .performAttack(LuminousValentineEntity::shadowBind)
                              .whenStarting(
                                 entity -> {
                                    Vec3 circleVec3 = new Vec3(0.0, entity.getBbHeight() * 3.0F / 4.0F - entity.getEyeHeight(), 0.0);
                                    MagicCircle.castMagicCircle(
                                       1.0F,
                                       25,
                                       MagicCircleVariant.DARK,
                                       entity,
                                       1.0F,
                                       circleVec3,
                                       (ManasSkill)SpiritualMagics.SHADOW_BIND.get(),
                                       0,
                                       Pair.of(0.0, 1000.0)
                                    );
                                 }
                              )
                              .startCondition(entity -> entity.getRandom().nextFloat() <= 0.1),
                           new CustomRangeAttack(10)
                              .maxAttackRadius(50.0F)
                              .attackInterval(entity -> 20)
                              .performAttack(LuminousValentineEntity::bloodRay)
                              .startCondition(entity -> entity.getRandom().nextFloat() <= 0.1),
                           new CustomHeldAttack()
                              .minAttackRadius(0.0F)
                              .maxAttackRadius(30.0F)
                              .attackInterval(entity -> 60)
                              .onTick(
                                 (entity, target, tick) -> {
                                    if (entity.disintegrationUUID == null) {
                                       if (entity.disintegrationCooldown <= 0) {
                                          entity.disintegration(target);
                                       }
                                    } else if (!(
                                       ((ServerLevel)entity.level()).getEntity(entity.disintegrationUUID) instanceof DisintegrationEntity disintegration
                                          && target.isAlive()
                                    )) {
                                       entity.disintegrationUUID = null;
                                    } else if (disintegration.getAffectedEntities().isEmpty()) {
                                       disintegration.discard();
                                       entity.disintegrationUUID = null;
                                    } else if (this.distanceTo(disintegration) <= 10.0F) {
                                       entity.teleportTowards(entity, disintegration, 15.0);
                                    }

                                    double size = entity.getAttributeValue(Attributes.SCALE) * 4.0;
                                    TensuraParticleHelper.addServerAuraParticles(entity, TensuraParticleUtils.getGoldAura(1.0F, (float)size, -0.3F), 3, 0.03);
                                    return tick < 250 && entity.disintegrationUUID != null && target.isAlive();
                                 }
                              )
                              .startCondition(entity -> {
                                 if (entity.disintegrationCooldown > 0) {
                                    return false;
                                 } else {
                                    return entity.getPhase() == 2 ? entity.getRandom().nextFloat() <= 0.2 : entity.getRandom().nextFloat() <= 0.1;
                                 }
                              }),
                           new CustomHeldAttack().minAttackRadius(0.0F).maxAttackRadius(20.0F).attackInterval(entity -> 20).onTick((entity, target, tick) -> {
                              if (tick % 2 == 0) {
                                 TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.HEART, 2.0);
                              } else {
                                 entity.mindControlSurrounding();
                              }

                              return tick < 20 && target.isAlive();
                           }).startCondition(entity -> entity.getRandom().nextFloat() <= 0.1)
                        }
                     ),
                     PlayerLikeEntity.getSpearAttack(20).attackInterval(entity -> 5).attackRadius(25.0F),
                     PlayerLikeEntity.getCrossbowAttack().attackInterval(entity -> 10).attackRadius(30.0F),
                     PlayerLikeEntity.getBowAttack().attackInterval(entity -> 10).attackRadius(25.0F),
                     new AnimatableMeleeAttack(1)
                        .attackInterval(entity -> 15)
                        .whenStarting(entity -> entity.swing(InteractionHand.MAIN_HAND, true))
                        .startCondition(entity -> !entity.usingRangedWeapon())
                  }
               )
               .startCondition(entity -> !entity.shouldStopMoving())
         }
      );
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
