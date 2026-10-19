package io.github.manasmods.tensura_neb.entity;

import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.common.CoercionSkill;
import io.github.manasmods.tensura.ability.skill.extra.HakiSkill;
import io.github.manasmods.tensura.ability.skill.unique.CookSkill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.entity.ai.behaviour.TensuraBehaviourHelper;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.CustomRangeAttack;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.InvalidateNeutralAttackTarget;
import io.github.manasmods.tensura.entity.ai.behaviour.misc.HumanoidConsumeItem;
import io.github.manasmods.tensura.entity.ai.behaviour.movement.TeleportToEntity;
import io.github.manasmods.tensura.entity.ai.behaviour.path.SubordinateFollowOwner;
import io.github.manasmods.tensura.entity.human.OtherworlderEntity;
import io.github.manasmods.tensura.entity.magic.breath.BlackFlameBreathProjectile;
import io.github.manasmods.tensura.entity.magic.breath.BreathEntity;
import io.github.manasmods.tensura.entity.magic.breath.ParalysingBreathProjectile;
import io.github.manasmods.tensura.entity.magic.breath.PoisonousBreathProjectile;
import io.github.manasmods.tensura.entity.magic.lightning.BlackLightningBolt;
import io.github.manasmods.tensura.entity.projectile.WebBulletProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.HellFlareProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.IceLanceProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.WaterBladeProjectile;
import io.github.manasmods.tensura.entity.template.PlayerLikeEntity;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.entity.template.subclass.ITeleportation;
import io.github.manasmods.tensura.item.tool.custom.ArmorsaurusGauntletItem;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.item.TensuraArmorItems;
import io.github.manasmods.tensura.registry.item.TensuraToolItems;
import io.github.manasmods.tensura.registry.magic.AspectualMagics;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura_neb.registry.NebEntityTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.InteractWithDoor;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StrafeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RimuruOgreFightEntity extends OtherworlderEntity implements SmartBrainOwner<RimuruOgreFightEntity>, ITeleportation {
   private static final EntityDataAccessor<Boolean> FINAL_PHASE = SynchedEntityData.defineId(RimuruOgreFightEntity.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Integer> BREATHING = SynchedEntityData.defineId(RimuruOgreFightEntity.class, EntityDataSerializers.INT);
   private final ServerBossEvent bossEvent = new ServerBossEvent(this.getDisplayName(), BossBarColor.BLUE, BossBarOverlay.NOTCHED_20);
   private UUID breathUUID = null;
   private boolean flameBreath = false;

   public RimuruOgreFightEntity(EntityType<? extends RimuruOgreFightEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.xpReward = 2000;
      ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
   }

   public static Builder setAttributes() {
      return TensuraTamableEntity.setAttributes()
         .add(Attributes.ARMOR, 10.0)
         .add(Attributes.ATTACK_DAMAGE, 50.0)
         .add(Attributes.ATTACK_KNOCKBACK, 3.0)
         .add(Attributes.MAX_HEALTH, 2000.0)
         .add(Attributes.MOVEMENT_SPEED, 0.3F)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
         .add(Attributes.STEP_HEIGHT, 2.0)
         .add(Attributes.ENTITY_INTERACTION_RANGE, 2.0)
         .add(TensuraAttributes.PRESENCE_SENSE, 3.0)
         .add(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, 10.0)
         .add(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, 5.0)
         .add(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, 5.0);
   }

   public ResourceLocation getTextureLocation() {
      return ResourceLocation.fromNamespaceAndPath("tensura_neb", "textures/entity/rimuru_ogre_fight.png");
   }

   public List<ManasSkill> getUniqueSkills() {
      return List.of((ManasSkill)UniqueSkills.PREDATOR.get(), (ManasSkill)UniqueSkills.GREAT_SAGE.get());
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(FINAL_PHASE, false);
      builder.define(BREATHING, 0);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("FinalPhase", this.isFinalPhase());
      compound.putInt("Breathing", this.getBreathing());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setFinalPhase(compound.getBoolean("FinalPhase"));
      this.setBreathing(compound.getInt("Breathing"));
   }

   public boolean isFinalPhase() {
      return (Boolean)this.entityData.get(FINAL_PHASE);
   }

   public void setFinalPhase(boolean phase) {
      this.entityData.set(FINAL_PHASE, phase);
   }

   public int getBreathing() {
      return (Integer)this.entityData.get(BREATHING);
   }

   public void setBreathing(int breathing) {
      this.entityData.set(BREATHING, breathing);
   }

   public void setCustomName(@Nullable Component pName) {
      super.setCustomName(pName);
      this.bossEvent.setName(this.getDisplayName());
   }

   public void startSeenByPlayer(ServerPlayer pPlayer) {
      super.startSeenByPlayer(pPlayer);
      if (!this.isTame() && !this.getType().equals(NebEntityTypes.RIMURU_CLONE.get())) {
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
      if (!this.isTame() && !this.getType().equals(NebEntityTypes.RIMURU_CLONE.get())) {
         if (!this.isTame()) {
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
         }

         if (this.isFinalPhase()) {
            this.bossEvent.setColor(BossBarColor.PURPLE);
            this.bossEvent.setOverlay(BossBarOverlay.NOTCHED_10);
         }
      }
   }

   protected boolean canRide(Entity entity) {
      return false;
   }

   public boolean shouldBroadcastTeleport() {
      return false;
   }

   public boolean hurt(DamageSource source, float pAmount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         return this.shouldDodge(source) ? false : super.hurt(source, pAmount);
      }
   }

   public void onProjectileImpact(
      EntityHitResult hitResult, Projectile projectile, Changeable<ProjectileDeflection> deflection, Changeable<ProjectileHitResult> result
   ) {
      if (!(projectile instanceof AbstractArrow arrow && arrow.getPierceLevel() > 0)) {
         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 2.0F, 1.0F);
         if (projectile.getOwner() instanceof LivingEntity shooter) {
            this.setLastHurtByMob(shooter);
         }

         result.set(ProjectileHitResult.PASS);
         TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.SQUID_INK);
         TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.SQUID_INK, 2.0);
         projectile.discard();
      }
   }

   private boolean shouldDodge(DamageSource source) {
      if (this.level().isClientSide()) {
         return false;
      }

      if (source.getDirectEntity() instanceof LivingEntity entity) {
         if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
         }

         if (!TensuraDamageHelper.isPhysicalAttack(source)) {
            return false;
         }

         if (entity.getRandom().nextFloat() >= 0.2) {
            return false;
         }

         if (source.isCreativePlayer()) {
            return false;
         }

         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_TELEPORT, SoundSource.PLAYERS, 2.0F, 1.0F);
         if (!this.level().isClientSide() && this.getTarget() != null) {
            TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.SQUID_INK);
            this.teleportTowards(this, this.getTarget(), 12.0);
            TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.SQUID_INK);
         }

         this.setLastHurtByMob(entity);
         return true;
      } else {
         return false;
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         this.enterLastPhase();
         if (this.tickCount % 20 == 0) {
            if (!this.isAlive()) {
               return;
            }

            if (this.getY() < this.level().getMinBuildHeight() - 100) {
               this.discard();
               return;
            }

            IExistence existence = TensuraStorages.getExistenceFrom(this);
            if (this.getHealth() >= this.getMaxHealth() && existence.getSpiritualHealth() >= this.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH)) {
               return;
            }

            if (this.isFinalPhase()) {
               this.heal(15.0F);
               existence.setSpiritualHealth(existence.getSpiritualHealth() + 60.0);
            } else {
               this.heal(5.0F);
               existence.setSpiritualHealth(existence.getSpiritualHealth() + 20.0);
            }
         }

         if (this.getBreathing() > 0 && this.isAlive() && this.getTarget() != null) {
            this.setBreathing(this.getBreathing() - 1);
            if (this.breathUUID == null) {
               this.spawnBreath();
            } else {
               this.level()
                  .playSound(
                     null,
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     this.flameBreath ? (SoundEvent)TensuraSoundEvents.BREATH_FIRE.get() : (SoundEvent)TensuraSoundEvents.BREATH_POISON.get(),
                     SoundSource.HOSTILE,
                     0.75F,
                     1.0F
                  );
            }
         } else if (this.breathUUID != null) {
            Entity breath = ((ServerLevel)this.level()).getEntity(this.breathUUID);
            if (breath instanceof BreathEntity) {
               breath.discard();
            }

            this.setBreathing(0);
            this.breathUUID = null;
         }
      }
   }

   protected void spawnBreath() {
      int random = this.getRandom().nextInt(3);
      BreathEntity breath = null;
      if (random == 0) {
         breath = new ParalysingBreathProjectile(this.level(), this);
         breath.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) / 2.0F);
         breath.setSkill(SkillUtils.getSkillOrNull(this, (ManasSkill)IntrinsicSkills.PARALYSING_BREATH.get()));
         this.flameBreath = false;
      } else if (random == 1) {
         breath = new PoisonousBreathProjectile(this.level(), this);
         breath.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) / 2.0F);
         breath.setSkill(SkillUtils.getSkillOrNull(this, (ManasSkill)IntrinsicSkills.POISONOUS_BREATH.get()));
         this.flameBreath = false;
      } else if (random == 2) {
         breath = new BlackFlameBreathProjectile(this.level(), this);
         breath.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) / 3.0F);
         breath.setSecondaryDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) / 4.0F);
         breath.setSkill(SkillUtils.getSkillOrNull(this, (ManasSkill)ExtraSkills.BLACK_FLAME.get()));
         this.flameBreath = true;
      }

      if (breath != null) {
         breath.setLife(90);
         breath.setMpCost(50.0);
         breath.setPos(this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0));
         this.level().addFreshEntity(breath);
         this.breathUUID = breath.getUUID();
      }
   }

   protected void shootWaterBlade(LivingEntity target) {
      this.lookAt(Anchor.EYES, target.getEyePosition());
      this.swing(InteractionHand.MAIN_HAND, true);
      WaterBladeProjectile blade = new WaterBladeProjectile(this.level(), this);
      blade.setPos(this.getEyePosition());
      blade.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0F);
      blade.setSkill(this.getSkill((ManasSkill)CommonSkills.WATER_BLADE.get()));
      blade.shootToward(target, 3.0F, 0.0F);
      this.level().addFreshEntity(blade);
      this.level()
         .playSound(
            null,
            this.getX(),
            this.getY(),
            this.getZ(),
            (SoundEvent)TensuraSoundEvents.CAST_WATER.get(),
            SoundSource.HOSTILE,
            0.5F,
            0.4F + (this.random.nextFloat() * 0.4F + 0.8F)
         );
   }

   protected void shootIcicle(LivingEntity target, int arrowAmount) {
      this.lookAt(Anchor.EYES, target.getEyePosition());
      int arrowRot = 360 / arrowAmount;

      for (int i = 0; i < arrowAmount; i++) {
         Vec3 offset = new Vec3(0.0, 2.0, 0.0)
            .zRot((arrowRot * i - arrowRot / 2.0F) * (float) (Math.PI / 180.0))
            .xRot(-this.getXRot() * (float) (Math.PI / 180.0))
            .yRot(-this.getYRot() * (float) (Math.PI / 180.0));
         IceLanceProjectile lance = new IceLanceProjectile(this.level(), this);
         lance.setNoGravity(true);
         lance.setPos(this.getEyePosition().add(offset));
         lance.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
         lance.setSecondaryDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
         lance.setHitRadius(3.0F);
         lance.setSkill(this.getSkill((ManasSkill)AspectualMagics.ICICLE_LANCE.get()));
         lance.shootToward(target, offset.add(0.0, target.getEyeHeight(), 0.0), 2.0F, 0.0F);
         this.level().addFreshEntity(lance);
      }

      this.level()
         .playSound(
            null,
            this.getX(),
            this.getY(),
            this.getZ(),
            (SoundEvent)TensuraSoundEvents.CAST_ICE.get(),
            SoundSource.HOSTILE,
            0.5F,
            0.4F + (this.random.nextFloat() * 0.4F + 0.8F)
         );
   }

   protected void shootWebBullet(LivingEntity target) {
      this.lookAt(Anchor.EYES, target.getEyePosition());
      WebBulletProjectile bullet = new WebBulletProjectile(
         this.level(), this, true, ((Item)TensuraToolItems.STICKY_STEEL_WEB_CARTRIDGE.get()).getDefaultInstance()
      );
      double d1 = target.getX() - this.getX();
      double d2 = target.getY() - bullet.getY();
      double d3 = target.getZ() - this.getZ();
      double f = Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
      bullet.shoot(d1, d2 + f, d3, 2.0F, 0.0F);
      this.level().addFreshEntity(bullet);
      this.swing(InteractionHand.MAIN_HAND, true);
      this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
   }

   protected void shootHellFlare(LivingEntity target) {
      this.lookAt(Anchor.EYES, target.getEyePosition());
      this.swing(InteractionHand.MAIN_HAND, true);
      HellFlareProjectile projectile = new HellFlareProjectile(this.level(), this);
      projectile.setPos(this.getEyePosition());
      projectile.setSkill(this.getSkill((ManasSkill)ExtraSkills.BLACK_FLAME.get()));
      projectile.setDamage((float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 5.0));
      projectile.setSecondaryDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
      projectile.setLimited(true);
      projectile.setAreaLife(80);
      projectile.setAreaRadius(3.0F);
      projectile.shootToward(target, 1.5F, 0.0F);
      this.level().addFreshEntity(projectile);
      this.level()
         .playSound(
            null,
            this.getX(),
            this.getY(),
            this.getZ(),
            (SoundEvent)TensuraSoundEvents.CAST_FIRE.get(),
            SoundSource.HOSTILE,
            0.5F,
            0.4F + (this.random.nextFloat() * 0.4F + 0.8F)
         );
   }

   protected void blackLightning(LivingEntity target) {
      BlackLightningBolt bolt = new BlackLightningBolt(this.level(), this);
      bolt.setTensuraDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 3.0F);
      bolt.setAdditionalVisual(6);
      bolt.setRadius(5.0F);
      bolt.setSkill(this.getSkill((ManasSkill)ExtraSkills.BLACK_LIGHTNING.get()));
      bolt.setPos(target.position());
      this.level().addFreshEntity(bolt);
      this.swing(InteractionHand.MAIN_HAND, true);
      this.level()
         .playSound(
            null,
            this.getX(),
            this.getY(),
            this.getZ(),
            (SoundEvent)TensuraSoundEvents.CAST_LIGHTNING.get(),
            SoundSource.HOSTILE,
            0.5F,
            0.4F + (this.random.nextFloat() * 0.4F + 0.8F)
         );
   }

   protected void coercion(Vec3 targetPos) {
      Level level = this.level();
      if (!level.isClientSide()) {
         Vec3 source = this.getEyePosition().add(this.getLookAngle().scale(2.0));
         Vec3 sourceToTarget = targetPos.subtract(source);
         Vec3 normalizes = sourceToTarget.normalize();
         level.playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.COERCION.get(), SoundSource.PLAYERS, 5.0F, 1.0F);

         for (int particleIndex = 1; particleIndex < Mth.floor(sourceToTarget.length()); particleIndex++) {
            Vec3 particlePos = source.add(normalizes.scale(particleIndex));
            TensuraParticleHelper.spawnServerParticles(level, TensuraParticleUtils.getColorlessSonic(0.85F, 2.5F), particlePos.x, particlePos.y, particlePos.z);
            AABB aabb = new AABB(ObjectSelectionHelper.getBlockPos(particlePos)).inflate(4.0);
            List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, aabb, targetx -> !targetx.is(this) && !this.isAlliedTo(targetx));
            if (!list.isEmpty()) {
               double ownerEP = EnergyHelper.getMaxEP(this);
               LivingEntity owner = (LivingEntity)(this.getControllingPassenger() != null ? this.getControllingPassenger() : this);
               ManasSkillInstance coercion = this.getSkill((ManasSkill)CommonSkills.COERCION.get());

               for (LivingEntity target : list) {
                  if (!this.hasPassenger(target)) {
                     DamageSource damagesource = ((CoercionSkill)CommonSkills.COERCION.get()).createSource(coercion, this, DamageTypes.SONIC_BOOM, 0);
                     target.hurt(damagesource, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.25F);
                     double targetEP = EnergyHelper.getMaxEP(target);
                     double difference = ownerEP / targetEP;
                     if (!(difference <= 2.0)) {
                        int fearLevel = (int)(CoercionSkill.CONFIG.epDifferenceMultiplier * (difference - 2.0));
                        fearLevel = Math.min(fearLevel, TensuraMobEffect.CONFIG.maxFear);
                        TensuraMobEffect.addEffect(
                           target,
                           TensuraMobEffects.getReference(TensuraMobEffects.FEAR),
                           CoercionSkill.CONFIG.fearDuration,
                           fearLevel,
                           false,
                           false,
                           true,
                           owner.getUUID(),
                           (ManasSkill)CommonSkills.COERCION.get(),
                           0
                        );
                        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, CoercionSkill.CONFIG.fearDuration, fearLevel, true, false, true), this);
                        HakiSkill.hakiPush(target, this, coercion, fearLevel);
                     }
                  }
               }
            }
         }
      }
   }

   private void enterLastPhase() {
      if (this.isAlive() && !this.isFinalPhase()) {
         if (this.getHealth() <= this.getMaxHealth() * 0.5 || this.getHealth() <= 1000.0F) {
            if (this.getType().equals(NebEntityTypes.RIMURU_CLONE.get())) {
               return;
            }

            this.invulnerableTime = 100;
            CookSkill.removeCookedHP(this);
            IExistence existence = TensuraStorages.getExistenceFrom(this);
            if (existence.getMagicule() <= 0.0) {
               existence.setMagicule(50.0);
            }

            this.setHealth(this.getMaxHealth());
            double SHP = this.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
            if (existence.getSpiritualHealth() < SHP) {
               existence.setSpiritualHealth(SHP);
            }

            existence.markDirty();
            TensuraStorages.resetEffect(this);
            this.dead = false;
            this.deathTime = 0;
            this.unsetRemoved();
            this.setFinalPhase(true);
            this.inventory
               .setItem(this.getSlotId(EquipmentSlot.MAINHAND), ((ArmorsaurusGauntletItem)TensuraToolItems.ARMORSAURUS_GAUNTLET.get()).getDefaultInstance());
            this.inventory
               .setItem(this.getSlotId(EquipmentSlot.OFFHAND), ((ArmorsaurusGauntletItem)TensuraToolItems.ARMORSAURUS_GAUNTLET.get()).getDefaultInstance());
            this.inventory.setItem(this.getSlotId(EquipmentSlot.HEAD), ((Item)TensuraArmorItems.ARMORSAURUS_HELMET.get()).getDefaultInstance());
            this.inventory.setItem(this.getSlotId(EquipmentSlot.CHEST), ((Item)TensuraArmorItems.ARMORSAURUS_CHESTPLATE.get()).getDefaultInstance());
            this.inventory.setItem(this.getSlotId(EquipmentSlot.LEGS), ((Item)TensuraArmorItems.ARMORSAURUS_LEGGINGS.get()).getDefaultInstance());
            this.inventory.setItem(this.getSlotId(EquipmentSlot.FEET), ((Item)TensuraArmorItems.ARMORSAURUS_BOOTS.get()).getDefaultInstance());
            this.updateContainerEquipment();
            this.summonClones(1, 5);
            this.summonClones(1, 5);
            this.summonClones(3, 7);
            this.summonClones(3, 7);
            TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.SQUID_INK, 1.0);
            double size = this.getAttributeValue(Attributes.SCALE) * 4.0;
            TensuraParticleHelper.addServerAuraParticles(this, TensuraParticleUtils.getBlackAura(1.0F, (float)size, -0.3F), 3, 0.01);
            this.level()
               .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.DEFENCE_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            TensuraParticleHelper.spawnServerParticles(
               this.level(),
               TensuraParticleUtils.getBlackWave(0.9F, this.getBbWidth() * 3.0F, -0.5F, true),
               this.getX(),
               this.getY() + this.getBbHeight() * 0.33,
               this.getZ()
            );
            TensuraParticleHelper.spawnServerParticles(
               this.level(),
               TensuraParticleUtils.getBlackWave(0.9F, this.getBbWidth() * 3.0F, -0.5F, true),
               this.getX(),
               this.getY() + this.getBbHeight() * 0.66,
               this.getZ()
            );
         }
      }
   }

   private void summonClones(int minRadius, int maxRadius) {
      if (this.level() instanceof ServerLevel level) {
         int var17 = Mth.floor(this.getX());
         int j = Mth.floor(this.getY());
         int k = Mth.floor(this.getZ());
         double EP = EnergyHelper.getBaseMaxEP(this);
         RimuruCloneEntity clone = new RimuruCloneEntity((EntityType<? extends RimuruCloneEntity>)NebEntityTypes.RIMURU_CLONE.get(), level);

         for (int l = 0; l < 50; l++) {
            int i1 = var17 + Mth.nextInt(this.random, minRadius, maxRadius) * Mth.nextInt(this.random, -1, 1);
            int j1 = j + Mth.nextInt(this.random, minRadius, maxRadius) * Mth.nextInt(this.random, -1, 1);
            int k1 = k + Mth.nextInt(this.random, minRadius, maxRadius) * Mth.nextInt(this.random, -1, 1);
            clone.setPos(i1, j1, k1);
            if (level.isUnobstructed(clone) && level.noCollision(clone) && !level.containsAnyLiquid(clone.getBoundingBox())) {
               clone.finalizeSpawn(level, level.getCurrentDifficultyAt(clone.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
               clone.setTarget(this.getTarget());
               clone.copySkills(this);
               EnergyHelper.setBaseMaxEP(clone, EP * 0.1F);
               IExistence existence = TensuraStorages.getExistenceFrom(clone);
               existence.setSummonedSecond(300);
               existence.setSummoner(this.getUUID());
               existence.markDirty();
               level.addFreshEntityWithPassengers(clone);
               clone.inventory
                  .setItem(clone.getSlotId(EquipmentSlot.MAINHAND), ((ArmorsaurusGauntletItem)TensuraToolItems.ARMORSAURUS_GAUNTLET.get()).getDefaultInstance());
               clone.inventory
                  .setItem(clone.getSlotId(EquipmentSlot.OFFHAND), ((ArmorsaurusGauntletItem)TensuraToolItems.ARMORSAURUS_GAUNTLET.get()).getDefaultInstance());
               clone.inventory.setItem(clone.getSlotId(EquipmentSlot.HEAD), ((Item)TensuraArmorItems.ARMORSAURUS_HELMET.get()).getDefaultInstance());
               clone.inventory.setItem(clone.getSlotId(EquipmentSlot.CHEST), ((Item)TensuraArmorItems.ARMORSAURUS_CHESTPLATE.get()).getDefaultInstance());
               clone.inventory.setItem(clone.getSlotId(EquipmentSlot.LEGS), ((Item)TensuraArmorItems.ARMORSAURUS_LEGGINGS.get()).getDefaultInstance());
               clone.inventory.setItem(clone.getSlotId(EquipmentSlot.FEET), ((Item)TensuraArmorItems.ARMORSAURUS_BOOTS.get()).getDefaultInstance());
               clone.updateContainerEquipment();
               TensuraParticleHelper.addServerParticlesAroundSelf(clone, ParticleTypes.SQUID_INK, 1.0);
               double size = clone.getAttributeValue(Attributes.SCALE) * 4.0;
               TensuraParticleHelper.addServerAuraParticles(clone, TensuraParticleUtils.getBlackAura(1.0F, (float)size, -0.3F), 3, 0.01);
               break;
            }
         }
      }
   }

   @Nullable
   protected ManasSkillInstance getSkill(ManasSkill manasSkill) {
      Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(this).getSkill(manasSkill);
      if (skill.isEmpty()) {
         return null;
      } else {
         return !skill.get().canInteractSkill(this) ? null : skill.get();
      }
   }

   protected float getEquipmentDropChance(EquipmentSlot pSlot) {
      if (this.isTame()) {
         return 0.0F;
      }

      float chance = super.getEquipmentDropChance(pSlot);
      return pSlot.equals(EquipmentSlot.MAINHAND) ? Math.max(0.05F, chance) : chance;
   }

   public void die(DamageSource source) {
      if (source.is(DamageTypes.FELL_OUT_OF_WORLD) || source.is(DamageTypes.GENERIC_KILL)) {
         super.die(source);
      } else if (!this.isFinalPhase()) {
         this.enterLastPhase();
      } else {
         super.die(source);
      }
   }

   @Nullable
   public SoundEvent getTeleportSound() {
      return (SoundEvent)TensuraSoundEvents.INSTANT_MOVE.get();
   }

   public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
      return false;
   }

   @NotNull
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData) {
      if (!pReason.equals(MobSpawnType.BUCKET)) {
         this.populateDefaultEquipmentSlots(this.random, pDifficulty);
      }

      TensuraBehaviourHelper.setHome(this, pLevel.getLevel(), this.blockPosition());
      return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
   }

   protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
      ItemStack stack = new ItemStack((ItemLike)TensuraToolItems.ARMORSAURUS_GAUNTLET.get());
      this.setItemSlot(EquipmentSlot.MAINHAND, stack);
      this.inventory.setItem(this.getSlotId(EquipmentSlot.MAINHAND), stack);
      this.updateContainerEquipment();
   }

   @NotNull
   protected Provider<?> brainProvider() {
      return new SmartBrainProvider(this, true);
   }

   public List<ExtendedSensor<RimuruOgreFightEntity>> getSensors() {
      return ObjectArrayList.of(new ExtendedSensor[]{new NearbyLivingEntitySensor(), new HurtBySensor()});
   }

   public BrainActivityGroup<RimuruOgreFightEntity> getCoreTasks() {
      return BrainActivityGroup.coreTasks(new Behavior[]{new LookAtTarget(), new FloatToSurfaceOfFluid(), TensuraTamableEntity.getMoveToWalkTarget()});
   }

   public BrainActivityGroup<RimuruOgreFightEntity> getIdleTasks() {
      return BrainActivityGroup.idleTasks(
         new Behavior[]{
            new FirstApplicableBehaviour(
               new ExtendedBehaviour[]{
                  TensuraBehaviourHelper.getMoveToWanderPos(),
                  TensuraBehaviourHelper.getPreyTargeting(this, this::shouldTarget),
                  new SubordinateFollowOwner(),
                  new SetPlayerLookTarget(),
                  new SetRandomLookTarget()
               }
            ),
            new InteractWithDoor(),
            new HumanoidConsumeItem().startCondition(entity -> entity.shouldHeal()).stopIf(entity -> !entity.shouldHeal()),
            new OneRandomBehaviour(new ExtendedBehaviour[]{new SetRandomWalkTarget(), new Idle().runFor(entity -> entity.getRandom().nextInt(30, 60))})
               .startCondition(entity -> !entity.isOrderedToSit())
         }
      );
   }

   public BrainActivityGroup<RimuruOgreFightEntity> getFightTasks() {
      return BrainActivityGroup.fightTasks(
         new Behavior[]{
            new InvalidateNeutralAttackTarget().invalidateIf(this::shouldStopTarget),
            new TeleportToEntity().following(Mob::getTarget).teleportRadius((entity, target) -> 10).teleportToTargetAfter((entity, target) -> {
               if (entity.tickCount % 200 == 0) {
                  return 10.0;
               } else {
                  return entity.tickCount % 20 == 0 ? 20.0 : 32.0;
               }
            }).onSuccessTeleport((entity, target) -> {
               TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.SQUID_INK, 1.0);
               entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);
            }),
            new StrafeTarget().stopStrafingWhen(entity -> !entity.usingRangedWeapon()).startCondition(PlayerLikeEntity::usingRangedWeapon),
            new SetWalkTargetToAttackTarget().speedMod((owner, target) -> 2.0F).startCondition(entity -> !entity.usingRangedWeapon()),
            new FirstApplicableBehaviour(
                  new ExtendedBehaviour[]{
                     PlayerLikeEntity.getSpearAttack(20).attackInterval(entity -> 5).attackRadius(25.0F),
                     PlayerLikeEntity.getCrossbowAttack().attackInterval(entity -> 10).attackRadius(30.0F),
                     PlayerLikeEntity.getBowAttack().attackInterval(entity -> 10).attackRadius(25.0F),
                     new FirstApplicableBehaviour(
                           new ExtendedBehaviour[]{
                              new CustomRangeAttack(5)
                                 .maxAttackRadius(64.0F)
                                 .attackInterval(entity -> 20)
                                 .performAttack(RimuruOgreFightEntity::blackLightning)
                                 .startCondition(
                                    entity -> entity.getRandom().nextFloat() <= 0.1 && entity.getSkill((ManasSkill)ExtraSkills.BLACK_LIGHTNING.get()) != null
                                 ),
                              new CustomRangeAttack(5)
                                 .maxAttackRadius(32.0F)
                                 .attackInterval(entity -> 20)
                                 .performAttack(RimuruOgreFightEntity::shootHellFlare)
                                 .startCondition(
                                    entity -> entity.getRandom().nextFloat() <= 0.1 && entity.getSkill((ManasSkill)ExtraSkills.BLACK_FLAME.get()) != null
                                 )
                           }
                        )
                        .startCondition(RimuruOgreFightEntity::isFinalPhase),
                     new CustomRangeAttack(5)
                        .maxAttackRadius(32.0F)
                        .attackInterval(entity -> 10)
                        .performAttack(RimuruOgreFightEntity::shootWebBullet)
                        .startCondition(
                           entity -> entity.getRandom().nextFloat() <= 0.1 && entity.getSkill((ManasSkill)ExtraSkills.STICKY_STEEL_THREAD.get()) != null
                        ),
                     new CustomRangeAttack(5)
                        .maxAttackRadius(32.0F)
                        .attackInterval(entity -> 20)
                        .performAttack((entity, target) -> entity.shootIcicle(target, entity.getRandom().nextInt(7, 10)))
                        .startCondition(
                           entity -> entity.getRandom().nextFloat() <= 0.2 && entity.getSkill((ManasSkill)AspectualMagics.ICICLE_LANCE.get()) != null
                        ),
                     new CustomRangeAttack(5)
                        .maxAttackRadius(32.0F)
                        .attackInterval(entity -> 10)
                        .performAttack(RimuruOgreFightEntity::shootWaterBlade)
                        .startCondition(entity -> entity.getRandom().nextFloat() <= 0.4 && entity.getSkill((ManasSkill)CommonSkills.WATER_BLADE.get()) != null),
                     new CustomRangeAttack(5)
                        .maxAttackRadius(64.0F)
                        .attackInterval(entity -> 10)
                        .performAttack((entity, target) -> entity.coercion(entity.getEyePosition().add(entity.getLookAngle().scale(15.0))))
                        .startCondition(entity -> entity.getRandom().nextInt(10) == 1 && entity.getSkill((ManasSkill)CommonSkills.COERCION.get()) != null),
                     new CustomRangeAttack(0)
                        .maxAttackRadius(12.0F)
                        .attackInterval(entity -> 160)
                        .performAttack((entity, target) -> entity.setBreathing(80))
                        .startCondition(entity -> entity.getRandom().nextInt(15) == 1),
                     new AnimatableMeleeAttack(1)
                        .attackInterval(entity -> 5)
                        .whenStarting(entity -> entity.swing(InteractionHand.MAIN_HAND, true))
                        .startCondition(entity -> !entity.usingRangedWeapon())
                  }
               )
               .startCondition(entity -> entity.getBreathing() <= 0)
         }
      );
   }
}
