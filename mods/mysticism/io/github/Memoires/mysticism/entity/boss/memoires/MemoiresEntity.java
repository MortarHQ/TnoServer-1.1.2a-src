package io.github.Memoires.mysticism.entity.boss.memoires;

import com.mojang.datafixers.util.Pair;
import io.github.Memoires.mysticism.bridge.SubordinateAPI;
import io.github.Memoires.mysticism.damage.MysticismDamageHelper;
import io.github.Memoires.mysticism.damage.MysticismDamageTypes;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.entity.MysticismEntityTypes;
import io.github.Memoires.mysticism.registry.item.MysticismWeaponItems;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.data.TensuraRaceTags;
import io.github.manasmods.tensura.entity.ai.behaviour.TensuraBehaviourHelper;
import io.github.manasmods.tensura.entity.ai.behaviour.misc.HumanoidConsumeItem;
import io.github.manasmods.tensura.entity.ai.behaviour.misc.WakeUp;
import io.github.manasmods.tensura.entity.ai.behaviour.path.SetRandomSwimAndWalkTarget;
import io.github.manasmods.tensura.entity.ai.behaviour.path.SubordinateFollowOwner;
import io.github.manasmods.tensura.entity.ai.sensor.SleepSensor;
import io.github.manasmods.tensura.entity.human.OtherworlderEntity;
import io.github.manasmods.tensura.entity.template.PlayerLikeEntity;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.entity.template.subclass.IAmphibian;
import io.github.manasmods.tensura.entity.template.subclass.ITeleportation;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.race.RaceUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.TensuraEntityTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MemoiresEntity extends OtherworlderEntity implements SmartBrainOwner<MemoiresEntity>, ITeleportation, IAmphibian {
   private final ServerBossEvent bossEvent;
   private final List<Holder<MobEffect>> affectedEffects;
   private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(MemoiresEntity.class, EntityDataSerializers.INT);
   private boolean landNavigating = false;
   private int swimmingTick = 0;
   private SmartBrainSchedule schedule;
   private UUID lockedFighterUuid;
   private Boolean dropMainhand;
   private boolean recursionDepthActive = false;
   private int recursionDepthEndTick = 0;
   private int recursionDepthCooldownEndTick = 0;
   private final Map<UUID, Float> recursionDepthMap = new Object2ObjectOpenHashMap();
   private final Map<UUID, Float> recursionDepthMultiplierMap = new Object2ObjectOpenHashMap();
   private static final EntityDataAccessor<Boolean> DYING = SynchedEntityData.defineId(MemoiresEntity.class, EntityDataSerializers.BOOLEAN);

   public MemoiresEntity(EntityType<? extends MemoiresEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
      this.bossEvent = (ServerBossEvent)new ServerBossEvent(this.getDisplayName(), BossBarColor.BLUE, BossBarOverlay.NOTCHED_20).setPlayBossMusic(true);
      this.affectedEffects = List.of(
         TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL),
         TensuraMobEffects.getReference(TensuraMobEffects.INFINITE_IMPRISONMENT),
         TensuraMobEffects.getReference(TensuraMobEffects.LUST_EMBRACEMENT),
         TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION),
         TensuraMobEffects.getReference(TensuraMobEffects.SOUL_DRAIN),
         TensuraMobEffects.getReference(TensuraMobEffects.DROWSINESS)
      );
      this.xpReward = 0;
      this.setPathfindingMalus(PathType.TRAPDOOR, -1.0F);
      this.setPathfindingMalus(PathType.DANGER_TRAPDOOR, -1.0F);
      this.setPathfindingMalus(PathType.DANGER_TRAPDOOR, -1.0F);
   }

   public boolean checkSpawnRules(LevelAccessor pLevel, MobSpawnType pSpawnReason) {
      return TensuraEntityTypes.rollChance(MysticismEntityTypes.MYSTICISM_CONFIG.SpawnChance.memoiresChance, pLevel.getRandom())
         && super.checkSpawnRules(pLevel, pSpawnReason);
   }

   public static Builder setAttributes() {
      return TensuraTamableEntity.setAttributes()
         .add(Attributes.ARMOR, 100.0)
         .add(Attributes.ATTACK_DAMAGE, 25.0)
         .add(Attributes.MAX_HEALTH, 1800.0)
         .add(Attributes.MOVEMENT_SPEED, 0.15F)
         .add(Attributes.FOLLOW_RANGE, 150.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.8F)
         .add(Attributes.STEP_HEIGHT, 2.0)
         .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.6F)
         .add(Attributes.ENTITY_INTERACTION_RANGE, 3.0)
         .add(TensuraAttributes.PRESENCE_SENSE, 3.0)
         .add(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, 100.0)
         .add(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, 25.0)
         .add(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, 25.0);
   }

   public ResourceLocation getTextureLocation() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/otherworlder/memoires.png");
   }

   public boolean isDying() {
      return (Boolean)this.entityData.get(DYING);
   }

   public void setDying(boolean dying) {
      this.entityData.set(DYING, dying);
   }

   protected void updatePoses() {
      if (this.isDying()) {
         this.setPose(Pose.SLEEPING);
      } else {
         super.updatePoses();
      }
   }

   public boolean canUseRecursionDepth() {
      return !this.recursionDepthActive && this.tickCount >= this.recursionDepthCooldownEndTick;
   }

   public void activateRecursionDepth() {
      if (!this.level().isClientSide()) {
         if (this.canUseRecursionDepth()) {
            this.recursionDepthActive = true;
            this.recursionDepthEndTick = this.tickCount + 100;
            this.recursionDepthMap.clear();
            this.recursionDepthMultiplierMap.clear();
            this.level()
               .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.HOSTILE, 1.5F, 1.0F);
            if (this.level() instanceof ServerLevel) {
               TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.END_ROD, 1.5);
            }
         }
      }
   }

   private void endRecursionDepth() {
      if (this.recursionDepthActive) {
         this.recursionDepthActive = false;
         if (this.level() instanceof ServerLevel serverLevel) {
            DamageSource repeaterSrc = MysticismDamageHelper.create(this, MysticismDamageTypes.REPEATED).tensura$setResistanceBypassLevel(1.0F);
            repeaterSrc = repeaterSrc.tensura$setBarrierBypassLevel(1.0F);

            for (Entry<UUID, Float> entry : this.recursionDepthMap.entrySet()) {
               float total = entry.getValue();
               if (!(total <= 0.0F) && serverLevel.getEntity(entry.getKey()) instanceof LivingEntity target && target.isAlive()) {
                  float burst = total * 1.5F;
                  target.invulnerableTime = 0;
                  target.hurt(repeaterSrc, burst);
               }
            }
         }

         this.recursionDepthMap.clear();
         this.recursionDepthMultiplierMap.clear();
         this.recursionDepthCooldownEndTick = this.tickCount + 600;
         this.level()
            .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), SoundSource.HOSTILE, 1.5F, 1.0F);
      }
   }

   public void switchMoveControl(MoveControl control) {
      this.moveControl = control;
   }

   public void switchNavigation(PathNavigation navigation) {
      this.navigation = navigation;
   }

   public boolean isLandNavigating() {
      return this.landNavigating;
   }

   public void setLandNavigating(boolean landNavigating) {
      this.landNavigating = landNavigating;
   }

   public int getSwimmingTick() {
      return this.swimmingTick;
   }

   public void setSwimmingTick(int swimmingTick) {
      this.swimmingTick = swimmingTick;
   }

   public boolean shouldTarget(LivingEntity target) {
      if (target == this) {
         return false;
      } else if (!target.isAlive()) {
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

   private Player getLockedFighter() {
      if (this.lockedFighterUuid == null) {
         return null;
      } else {
         return this.level() instanceof ServerLevel serverLevel ? serverLevel.getServer().getPlayerList().getPlayer(this.lockedFighterUuid) : null;
      }
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(PHASE, 0);
      builder.define(DYING, false);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("Dying", this.isDying());
      compound.putInt("Phase", this.getPhase());
      if (this.lockedFighterUuid != null) {
         compound.putUUID("LockedFighter", this.lockedFighterUuid);
      }
   }

   public DamageSource getBaseDamageSource() {
      DamageSource source = super.getBaseDamageSource().tensura$setResistanceBypassLevel(1.0F);
      if (this.getPhase() == 4) {
         source = source.tensura$setBarrierBypassLevel(1.0F);
      }

      return source;
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (this.hasCustomName()) {
         this.bossEvent.setName(this.getDisplayName());
      }

      this.setPhase(compound.getInt("Phase"));
      this.setDying(compound.getBoolean("Dying"));
      if (compound.hasUUID("LockedFighter")) {
         this.lockedFighterUuid = compound.getUUID("LockedFighter");
      }
   }

   public int getPhase() {
      return (Integer)this.entityData.get(PHASE);
   }

   public void setPhase(int phase) {
      this.entityData.set(PHASE, phase);
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
      if (instance.getEffect().equals(TensuraMobEffects.getReference(MysticismMobEffects.BRIMSTONE_FLAMES))) {
         return true;
      } else if (instance.getEffect().equals(MobEffects.GLOWING)) {
         return true;
      } else if (instance.getEffect().equals(MobEffects.SLOW_FALLING)) {
         return true;
      } else if (this.affectedEffects.contains(instance.getEffect())) {
         return true;
      } else if (instance.getEffect().is(io.github.manasmods.tensura.data.TensuraTags.MobEffects.SKILL_BUFF)) {
         return true;
      } else {
         return instance.getEffect().is(io.github.manasmods.tensura.data.TensuraTags.MobEffects.IGNORE_ANTI_MAGICULE)
            ? true
            : !((MobEffect)instance.getEffect().value()).isBeneficial();
      }
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      this.tickBrain(this);
      this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
   }

   public boolean hurt(DamageSource pSource, float pAmount) {
      if (this.isInvulnerableTo(pSource)) {
         return false;
      }

      if (this.shouldDodge(pSource)) {
         return false;
      }

      if (pSource.getDirectEntity() instanceof Projectile) {
         pAmount *= 0.5F;
         return super.hurt(pSource, pAmount);
      }

      if (pSource.tensura$getMagicType() == null && pSource.tensura$getAbilityInstance() == null) {
         pAmount *= RaceUtils.getPhysicalAttackInputMultiplier(pSource);
      }

      if (this.lockedFighterUuid == null && pSource.getEntity() instanceof Player player && !player.isCreative() && !player.isSpectator() && player.isAlive()) {
         this.lockedFighterUuid = player.getUUID();
         this.setTarget(player);
      }

      boolean hurt = super.hurt(pSource, pAmount);
      if (pSource.getEntity() instanceof LivingEntity damageSource) {
         if (!damageSource.isAlive() || this.isAlliedTo(damageSource)) {
            return true;
         }

         if (damageSource instanceof Player player && (player.isCreative() || player.isSpectator())) {
            return true;
         }
      }

      return hurt;
   }

   private boolean shouldDodge(DamageSource source) {
      if (this.level().isClientSide()) {
         return false;
      }

      if (source.getDirectEntity() instanceof LivingEntity attacker) {
         if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
         }

         if (source.isCreativePlayer()) {
            return false;
         }

         double dodgeChance = 20.0;
         if (this.getRandom().nextDouble() * 100.0 >= 20.0) {
            return false;
         }

         double negate = attacker.getAttributeValue(TensuraAttributes.DODGE_NEGATE_CHANCE);
         if (attacker.getRandom().nextDouble() * 100.0 < negate) {
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

   public boolean isPushedByFluid() {
      return false;
   }

   public List<ManasSkill> getUniqueSkills() {
      return List.of((ManasSkill)UniqueMysticismSkills.REPEATER.get());
   }

   public void setCustomName(@Nullable Component pName) {
      super.setCustomName(pName);
      this.bossEvent.setName(this.getDisplayName());
   }

   public void startSeenByPlayer(ServerPlayer pPlayer) {
      super.startSeenByPlayer(pPlayer);
      this.bossEvent.addPlayer(pPlayer);
   }

   public void stopSeenByPlayer(ServerPlayer pPlayer) {
      super.stopSeenByPlayer(pPlayer);
      this.bossEvent.removePlayer(pPlayer);
   }

   protected boolean canRide(Entity entity) {
      return false;
   }

   public boolean isRecursionDepthActive() {
      return this.recursionDepthActive;
   }

   public void addRecursionDamage(UUID targetId, float amount) {
      this.recursionDepthMap.merge(targetId, amount, Float::sum);
   }

   public float getRecursionDepthMultiplier(UUID targetId) {
      return this.recursionDepthMultiplierMap.getOrDefault(targetId, 1.0F);
   }

   public void setRecursionDepthMultiplier(UUID targetId, float multiplier) {
      this.recursionDepthMultiplierMap.put(targetId, multiplier);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         if (this.recursionDepthActive && this.tickCount >= this.recursionDepthEndTick) {
            this.endRecursionDepth();
         }

         IExistence existence = TensuraStorages.getExistenceFrom(this);
         if (this.getHealth() >= this.getMaxHealth() && existence.getSpiritualHealth() >= this.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH)) {
            return;
         }

         if (this.tickCount % 20 == 0) {
            if (this.tickCount % 200 == 0) {
               this.heal(75.0F);
               existence.setSpiritualHealth(existence.getSpiritualHealth() + 200.0);
               existence.markDirty();
               TensuraStorages.resetEffect(this);
               this.addEffect(new MobEffectInstance(MobEffects.GLOWING, 30, 0, false, false, false));
               TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.COMPOSTER, 1.0);
               this.level()
                  .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_HEAL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
               TensuraParticleHelper.spawnServerParticles(
                  this.level(),
                  TensuraParticleUtils.getColorlessWave(0.9F, this.getBbWidth() * 3.0F, -0.5F, true),
                  this.getX(),
                  this.getY() + this.getBbHeight() * 0.33,
                  this.getZ()
               );
               TensuraParticleHelper.spawnServerParticles(
                  this.level(),
                  TensuraParticleUtils.getColorlessWave(0.9F, this.getBbWidth() * 3.0F, -0.5F, true),
                  this.getX(),
                  this.getY() + this.getBbHeight() * 0.66,
                  this.getZ()
               );
            } else {
               this.heal(10.0F);
               existence.setSpiritualHealth(existence.getSpiritualHealth() + 50.0);
            }
         }
      }
   }

   public boolean randomTeleport(LivingEntity entity, double pX, double pY, double pZ, boolean pBroadcastTeleport) {
      return super.randomTeleport(entity, pX, pY, pZ, pBroadcastTeleport);
   }

   @NotNull
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
      this.inventory.setItem(this.getSlotId(EquipmentSlot.MAINHAND), new ItemStack((ItemLike)MysticismWeaponItems.AXIOM.get()));
      this.inventory.setItem(this.getSlotId(EquipmentSlot.OFFHAND), new ItemStack((ItemLike)MysticismWeaponItems.WALTZ.get()));
      this.updateContainerEquipment();
   }

   protected float getEquipmentDropChance(EquipmentSlot pSlot) {
      if (pSlot != EquipmentSlot.MAINHAND && pSlot != EquipmentSlot.OFFHAND) {
         return 0.0F;
      }

      if (this.checkRepeater()) {
         return 1.0F;
      }

      if (this.dropMainhand == null) {
         this.dropMainhand = this.random.nextBoolean();
      }

      return (!this.dropMainhand || pSlot != EquipmentSlot.MAINHAND) && (this.dropMainhand || pSlot != EquipmentSlot.OFFHAND) ? 0.0F : 1.0F;
   }

   private boolean checkRepeater() {
      Player player = this.getLockedFighter();
      return player != null && SkillUtils.hasSkillFully(player, (ManasSkill)UniqueMysticismSkills.REPEATER.get());
   }

   protected @NotNull Provider<?> brainProvider() {
      return new SmartBrainProvider(this, true);
   }

   public List<ExtendedSensor<MemoiresEntity>> getSensors() {
      return ObjectArrayList.of(new ExtendedSensor[]{new NearbyLivingEntitySensor(), new HurtBySensor(), new SleepSensor()});
   }

   public BrainActivityGroup<MemoiresEntity> getCoreTasks() {
      return BrainActivityGroup.coreTasks(
         new Behavior[]{
            new WakeUp().stopIf(entity -> {
               if (!TensuraBehaviourHelper.canContinueToSleep(entity)) {
                  entity.stopSleeping();
                  return true;
               } else {
                  return false;
               }
            }),
            new LookAtTarget(),
            new FirstApplicableBehaviour(
               new ExtendedBehaviour[]{
                  new MoveToWalkTarget().cooldownFor(e -> 0).startCondition(e -> !e.isOrderedToSit() && !e.isSleeping()).stopIf(SubordinateAPI::isOrderedToSit)
               }
            )
         }
      );
   }

   public BrainActivityGroup<MemoiresEntity> getIdleTasks() {
      return BrainActivityGroup.idleTasks(
         new Behavior[]{
            new FirstApplicableBehaviour(
               new ExtendedBehaviour[]{
                  TensuraBehaviourHelper.getPreyTargeting(this, target -> this.getTarget() != null && this.shouldTarget(target)),
                  new SubordinateFollowOwner().canTeleportOffGroundWhen(entity -> false),
                  TensuraBehaviourHelper.getMoveToWanderPos(),
                  new SetPlayerLookTarget(),
                  new SetRandomLookTarget()
               }
            ),
            new InteractWithDoor(),
            new HumanoidConsumeItem().startCondition(entity -> this.shouldHeal()).stopIf(entity -> !this.shouldHeal()),
            new OneRandomBehaviour(
               new ExtendedBehaviour[]{
                  new FirstApplicableBehaviour(
                     new ExtendedBehaviour[]{
                        new SetRandomSwimAndWalkTarget()
                           .speedModifier((entity, pos) -> this.isInWaterOrBubble() ? 1.5F : 1.0F)
                           .cooldownFor(entity -> 0)
                           .startCondition(entity -> !this.isOrderedToSit() && !this.isSleeping())
                     }
                  ),
                  new Idle().runFor(entity -> this.getRandom().nextInt(30, 60))
               }
            )
         }
      );
   }

   public Map<Activity, BrainActivityGroup<? extends MemoiresEntity>> getAdditionalTasks() {
      Object2ObjectOpenHashMap<Activity, BrainActivityGroup<? extends MemoiresEntity>> map = new Object2ObjectOpenHashMap();
      map.put(Activity.REST, TensuraBehaviourHelper.getHumanoidSleepActivityGroup(this));
      return map;
   }

   public BrainActivityGroup<MemoiresEntity> getFightTasks() {
      return BrainActivityGroup.fightTasks(
         new Behavior[]{
            new MemoiresEntity.MemoiresRecursionDepthBehaviour(),
            new InvalidateAttackTarget().invalidateIf((mob, target) -> this.shouldStopTarget(mob, target)),
            new StrafeTarget().stopStrafingWhen(entity -> !this.usingRangedWeapon()).startCondition(mob -> mob.usingRangedWeapon()),
            new SetWalkTargetToAttackTarget().speedMod((owner, target) -> 2.0F).startCondition(entity -> !this.usingRangedWeapon()),
            new FirstApplicableBehaviour(
                  new ExtendedBehaviour[]{
                     PlayerLikeEntity.getSpearAttack(20).attackInterval(entity -> 5).attackRadius(25.0F),
                     PlayerLikeEntity.getCrossbowAttack().attackInterval(entity -> 10).attackRadius(30.0F),
                     PlayerLikeEntity.getBowAttack().attackInterval(entity -> 10).attackRadius(25.0F),
                     new AnimatableMeleeAttack(1).attackInterval(entity -> 5)
                  }
               )
               .startCondition(entity -> !this.usingRangedWeapon())
         }
      );
   }

   public SmartBrainSchedule getSchedule() {
      if (this.schedule == null) {
         this.schedule = new SmartBrainSchedule().activityAt(10, Activity.IDLE).activityAt(12000, Activity.REST);
      }

      return this.schedule;
   }

   public void startSleeping(BlockPos blockPos) {
      super.startSleeping(blockPos);
      BrainUtils.setMemory(this, MemoryModuleType.LAST_SLEPT, this.level().getGameTime());
      BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET);
      BrainUtils.clearMemory(this, MemoryModuleType.LOOK_TARGET);
      BrainUtils.clearMemory(this, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
   }

   public void stopSleeping() {
      super.stopSleeping();
      BrainUtils.setMemory(this, MemoryModuleType.LAST_WOKEN, this.level().getGameTime());
   }

   public class MemoiresRecursionDepthBehaviour extends ExtendedBehaviour<MemoiresEntity> {
      protected boolean shouldRun(ServerLevel level, MemoiresEntity entity) {
         LivingEntity target = entity.getTarget();
         return target != null && target.isAlive() && entity.canUseRecursionDepth() && entity.distanceToSqr(target) <= 144.0;
      }

      protected void start(MemoiresEntity entity) {
         entity.activateRecursionDepth();
      }

      protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
         return List.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));
      }
   }
}
