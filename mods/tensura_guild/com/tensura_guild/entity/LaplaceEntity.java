package com.tensura_guild.entity;

import com.tensura_guild.registry.GuildEntityTypes;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.entity.ai.behaviour.TensuraBehaviourHelper;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.InvalidateNeutralAttackTarget;
import io.github.manasmods.tensura.entity.ai.behaviour.misc.HumanoidConsumeItem;
import io.github.manasmods.tensura.entity.ai.behaviour.misc.WakeUp;
import io.github.manasmods.tensura.entity.ai.behaviour.movement.TeleportToEntity;
import io.github.manasmods.tensura.entity.ai.behaviour.path.SubordinateFollowOwner;
import io.github.manasmods.tensura.entity.ai.sensor.SleepSensor;
import io.github.manasmods.tensura.entity.human.OtherworlderEntity;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.entity.template.subclass.ITeleportation;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.battlewill.MeleeArts;
import io.github.manasmods.tensura.registry.battlewill.ProjectileArts;
import io.github.manasmods.tensura.registry.battlewill.UtilityArts;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.TensuraEntityTypes;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.IAbility;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
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
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.NotNull;

public class LaplaceEntity extends OtherworlderEntity implements SmartBrainOwner<LaplaceEntity>, ITeleportation {
   private static final ResourceLocation COMBAT_SHORT_SWORD_ID = ResourceLocation.fromNamespaceAndPath("tensura", "mithril_short_sword");
   private static final ResourceLocation DEFAULT_MASK_ID = ResourceLocation.fromNamespaceAndPath("tensura", "wonder_pierrot_mask");
   private static final int FALSIFIER_CONCEALMENT_SLOT = 0;
   private static final int FALSIFIER_FAKE_DEATH_SLOT = 1;
   private static final int SEER_SLOT = 2;
   private static final int DIAMOND_PATH_SLOT = 3;
   private static final int MAGIC_BULLET_SLOT = 4;
   private static final int ROARING_LION_PUNCH_SLOT = 5;
   private static final int FALSIFIER_CONCEALMENT_MODE = 0;
   private static final int FALSIFIER_FAKE_DEATH_MODE = 2;
   private static final int SEER_MODE = 0;
   private static final int BATTLEWILL_MODE = 0;
   private static final int FALSIFIER_CONCEALMENT_INTERVAL = 180;
   private static final int FALSIFIER_FAKE_DEATH_INTERVAL = 360;
   private static final int SEER_FUTURE_VISION_INTERVAL = 220;
   private static final int MAGIC_BULLET_INTERVAL = 120;
   private static final int MAGIC_BULLET_HOLD_TICKS = 30;
   private static final int ROARING_LION_PUNCH_INTERVAL = 90;
   private static final double MAGIC_BULLET_MIN_DISTANCE_SQR = 25.0;
   private static final double MAGIC_BULLET_MAX_DISTANCE_SQR = 784.0;
   private static final double ROARING_LION_PUNCH_MAX_DISTANCE_SQR = 36.0;
   private static final String MAGIC_BULLET_STARTED_TAG = "LaplaceMagicBulletStarted";
   private static final String BOSS_BAR_PLAYER_TAG = "LaplaceBossBarPlayer";
   private static final String SPAWNER_DORMANT_TAG = "SpawnerDormant";
   private final ServerBossEvent bossEvent = new ServerBossEvent(
      Component.translatable("bossbar.tensura_guild.laplace"), BossBarColor.BLUE, BossBarOverlay.NOTCHED_20
   );
   private SmartBrainSchedule schedule;
   private UUID bossBarPlayerUuid;
   private boolean spawnerDormant;

   public LaplaceEntity(EntityType<? extends LaplaceEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
      this.disableItemPickup();
   }

   public static Builder setAttributes() {
      return TensuraTamableEntity.setAttributes()
         .add(Attributes.ARMOR, 9.0)
         .add(Attributes.ATTACK_DAMAGE, 18.0)
         .add(Attributes.ATTACK_KNOCKBACK, 1.4)
         .add(Attributes.MAX_HEALTH, 360.0)
         .add(Attributes.MOVEMENT_SPEED, 0.25)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.35)
         .add(Attributes.STEP_HEIGHT, 1.0)
         .add(Attributes.ENTITY_INTERACTION_RANGE, 2.5)
         .add(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, 6.0)
         .add(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, 2.4)
         .add(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, 2.4);
   }

   public boolean isSpawnerDormant() {
      return this.spawnerDormant;
   }

   public void setSpawnerDormant(boolean dormant) {
      this.spawnerDormant = dormant;
      this.setNoAi(false);
      if (dormant) {
         this.clearSpawnerDormantBrainState();
      }
   }

   public ResourceLocation getTextureLocation() {
      return ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/entity/otherworlder/laplace.png");
   }

   public List<ManasSkill> getUniqueSkills() {
      return List.of((ManasSkill)UniqueSkills.FALSIFIER.get(), (ManasSkill)UniqueSkills.SEER.get());
   }

   public LaplaceEntity getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
      LaplaceEntity entity = (LaplaceEntity)GuildEntityTypes.LAPLACE.get().create(pLevel);
      if (entity == null) {
         return null;
      }

      UUID uuid = this.getOwnerUUID();
      if (uuid != null) {
         entity.setOwnerUUID(uuid);
         entity.setTame(true, true);
      }

      return entity;
   }

   public boolean checkSpawnRules(LevelAccessor level, MobSpawnType spawnReason) {
      return TensuraEntityTypes.rollSpawn(TensuraEntityTypes.CONFIG.SpawnChance.otherworlder, level, spawnReason) && super.checkSpawnRules(level, spawnReason);
   }

   @NotNull
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, SpawnGroupData pSpawnData) {
      TensuraBehaviourHelper.setHome(this, pLevel.getLevel(), this.blockPosition());
      this.setPersistenceRequired();
      this.equipDefaultMask();
      SpawnGroupData spawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
      this.disableItemPickup();
      return spawnData;
   }

   protected boolean shouldDespawnInPeaceful() {
      return false;
   }

   public boolean hurt(DamageSource source, float amount) {
      this.rememberAttacker(source.getEntity());
      return super.hurt(source, amount);
   }

   public void onProjectileImpact(
      EntityHitResult hitResult, Projectile projectile, Changeable<ProjectileDeflection> deflection, Changeable<ProjectileHitResult> result
   ) {
      this.rememberAttacker(projectile.getOwner());
   }

   public boolean doHurtTarget(Entity target) {
      boolean hurt = super.doHurtTarget(target);
      if (hurt
         && target instanceof LivingEntity livingTarget
         && livingTarget instanceof ServerPlayer player
         && !player.isAlive()
         && player.getUUID().equals(this.bossBarPlayerUuid)) {
         this.resetBossCombatState();
      }

      return hurt;
   }

   public void onRemovedFromLevel() {
      this.bossEvent.removeAllPlayers();
      super.onRemovedFromLevel();
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      if (this.bossBarPlayerUuid != null) {
         compound.putUUID("LaplaceBossBarPlayer", this.bossBarPlayerUuid);
      }

      if (this.spawnerDormant) {
         compound.putBoolean("SpawnerDormant", true);
      }
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.bossBarPlayerUuid = compound.hasUUID("LaplaceBossBarPlayer") ? compound.getUUID("LaplaceBossBarPlayer") : null;
      this.spawnerDormant = compound.getBoolean("SpawnerDormant");
      if (this.spawnerDormant) {
         this.setSpawnerDormant(true);
      }

      this.equipDefaultMask();
      this.disableItemPickup();
   }

   public boolean wantsToPickUp(ItemStack stack) {
      return false;
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      if (this.isBoundBossBarPlayer(player) && this.canShowLaplaceBossBar(player)) {
         this.restoreBossCombatTarget(player);
         this.showLaplaceBossBar(player);
      }
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   protected @NotNull Provider<?> brainProvider() {
      return new SmartBrainProvider(this, true);
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.spawnerDormant) {
         this.clearSpawnerDormantBrainState();
      }

      this.tickBrain(this);
      if (this.spawnerDormant) {
         this.clearSpawnerDormantBrainState();
      }

      this.tickCombatShortSwords();
      this.tickBossSkills();
      this.tickLaplaceBossBar();
   }

   public List<ExtendedSensor<LaplaceEntity>> getSensors() {
      return ObjectArrayList.of(new ExtendedSensor[]{new NearbyLivingEntitySensor(), new HurtBySensor(), new SleepSensor()});
   }

   public BrainActivityGroup<LaplaceEntity> getCoreTasks() {
      return BrainActivityGroup.coreTasks(new Behavior[]{new WakeUp().stopIf(entity -> {
         if (!TensuraBehaviourHelper.canContinueToSleep(entity)) {
            entity.stopSleeping();
            return true;
         } else {
            return false;
         }
      }), new LookAtTarget(), new FloatToSurfaceOfFluid(), getDormantAwareMoveToWalkTarget()});
   }

   public BrainActivityGroup<LaplaceEntity> getIdleTasks() {
      ExtendedBehaviour<LaplaceEntity> normalIdle = new FirstApplicableBehaviour(
            new ExtendedBehaviour[]{
               TensuraBehaviourHelper.getMoveToWanderPos(),
               TensuraBehaviourHelper.getPreyTargeting(this, entity -> false),
               new SubordinateFollowOwner(),
               new SetPlayerLookTarget(),
               new SetRandomLookTarget()
            }
         )
         .startCondition(entity -> !entity.isSpawnerDormant());
      ExtendedBehaviour<LaplaceEntity> dormantIdle = new FirstApplicableBehaviour(
            new ExtendedBehaviour[]{new SetPlayerLookTarget(), new SetRandomLookTarget(), new Idle().runFor(entity -> entity.getRandom().nextInt(30, 60))}
         )
         .startCondition(LaplaceEntity::isSpawnerDormant);
      ExtendedBehaviour<LaplaceEntity> randomWalkOrIdle = new OneRandomBehaviour(
            new ExtendedBehaviour[]{new SetRandomWalkTarget(), new Idle().runFor(entity -> entity.getRandom().nextInt(30, 60))}
         )
         .startCondition(entity -> !entity.isOrderedToSit() && !entity.isSpawnerDormant());
      return BrainActivityGroup.idleTasks(
         new Behavior[]{
            normalIdle,
            dormantIdle,
            new InteractWithDoor().startCondition(entity -> !entity.isSpawnerDormant()),
            new HumanoidConsumeItem().startCondition(entity -> entity.shouldHeal()).stopIf(entity -> !entity.shouldHeal()),
            randomWalkOrIdle
         }
      );
   }

   public BrainActivityGroup<LaplaceEntity> getFightTasks() {
      return BrainActivityGroup.fightTasks(
         new Behavior[]{
            new InvalidateNeutralAttackTarget().invalidateIf(this::shouldStopTarget),
            new TeleportToEntity()
               .following(Mob::getTarget)
               .teleportRadius((entity, target) -> 4)
               .teleportToTargetAfter((entity, target) -> entity.tickCount % 80 == 0 ? 10.0 : 24.0)
               .onSuccessTeleport((entity, target) -> TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.SWEEP_ATTACK, 1.0)),
            new SetWalkTargetToAttackTarget().speedMod((owner, target) -> 2.0F),
            new AnimatableMeleeAttack(1)
               .attackInterval(entity -> 10)
               .whenStarting(entity -> entity.swing(InteractionHand.MAIN_HAND, true))
               .startCondition(entity -> !entity.usingRangedWeapon())
         }
      );
   }

   public Map<Activity, BrainActivityGroup<? extends LaplaceEntity>> getAdditionalTasks() {
      Map<Activity, BrainActivityGroup<? extends LaplaceEntity>> tasks = new Object2ObjectOpenHashMap();
      tasks.put(Activity.REST, TensuraBehaviourHelper.getHumanoidSleepActivityGroup(this));
      return tasks;
   }

   public SmartBrainSchedule getSchedule() {
      if (this.schedule == null) {
         this.schedule = new SmartBrainSchedule().activityAt(10, Activity.IDLE).activityAt(13000, Activity.REST);
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

   private static ExtendedBehaviour<LaplaceEntity> getDormantAwareMoveToWalkTarget() {
      return new MoveToWalkTarget()
         .cooldownFor(entity -> 0)
         .startCondition(entity -> !entity.isOrderedToSit() && !entity.isSleeping() && !entity.isSpawnerDormant())
         .stopIf(entity -> entity.isOrderedToSit() || entity.isSpawnerDormant());
   }

   private void disableItemPickup() {
      this.setCanPickUpLoot(false);
   }

   private void rememberAttacker(Entity source) {
      if (!this.level().isClientSide() && source instanceof LivingEntity attacker) {
         if (attacker != this && attacker.isAlive() && !this.isAlliedTo(attacker)) {
            if (!(attacker instanceof Player player && (player.isCreative() || player.isSpectator()))) {
               this.wakeFromSpawnerDormancy();
               this.setLastHurtByMob(attacker);
               this.setTarget(attacker);
               this.setAggressive(true);
               BrainUtils.setMemory(this, MemoryModuleType.ATTACK_TARGET, attacker);
               if (attacker instanceof ServerPlayer serverPlayer) {
                  this.showLaplaceBossBar(serverPlayer);
               }
            }
         }
      }
   }

   private void wakeFromSpawnerDormancy() {
      if (this.spawnerDormant) {
         this.setSpawnerDormant(false);
      }
   }

   private void clearSpawnerDormantBrainState() {
      this.setLastHurtByMob(null);
      this.setTarget(null);
      this.setAggressive(false);
      BrainUtils.clearMemory(this, MemoryModuleType.ATTACK_TARGET);
      BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET);
      BrainUtils.clearMemory(this, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      this.getNavigation().stop();
      Vec3 movement = this.getDeltaMovement();
      if (movement.x != 0.0 || movement.z != 0.0) {
         this.setDeltaMovement(0.0, movement.y, 0.0);
      }
   }

   private void tickCombatShortSwords() {
      boolean fighting = this.getTarget() != null || BrainUtils.hasMemory(this, MemoryModuleType.ATTACK_TARGET);
      Item shortSword = (Item)BuiltInRegistries.ITEM.get(COMBAT_SHORT_SWORD_ID);
      if (shortSword != Items.AIR) {
         if (fighting) {
            this.equipCombatShortSword(shortSword, EquipmentSlot.MAINHAND);
            this.equipCombatShortSword(shortSword, EquipmentSlot.OFFHAND);
         } else {
            this.clearCombatShortSword(shortSword, EquipmentSlot.MAINHAND);
            this.clearCombatShortSword(shortSword, EquipmentSlot.OFFHAND);
         }
      }
   }

   private void equipCombatShortSword(Item shortSword, EquipmentSlot slot) {
      ItemStack stack = this.getItemBySlot(slot);
      if (!stack.is(shortSword)) {
         ItemStack weapon = shortSword.getDefaultInstance();
         this.setItemSlot(slot, weapon);
         this.inventory.setItem(this.getSlotId(slot), weapon);
      }
   }

   private void clearCombatShortSword(Item shortSword, EquipmentSlot slot) {
      if (this.getItemBySlot(slot).is(shortSword)) {
         this.setItemSlot(slot, ItemStack.EMPTY);
         this.inventory.setItem(this.getSlotId(slot), ItemStack.EMPTY);
      }
   }

   private void equipDefaultMask() {
      if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
         Item mask = (Item)BuiltInRegistries.ITEM.get(DEFAULT_MASK_ID);
         if (mask != Items.AIR) {
            ItemStack stack = mask.getDefaultInstance();
            this.setItemSlot(EquipmentSlot.HEAD, stack);
            this.inventory.setItem(this.getSlotId(EquipmentSlot.HEAD), stack);
         }
      }
   }

   private void showLaplaceBossBar(ServerPlayer player) {
      if (!player.getUUID().equals(this.bossBarPlayerUuid)) {
         this.bossEvent.removeAllPlayers();
         this.bossBarPlayerUuid = player.getUUID();
      }

      this.bossEvent.addPlayer(player);
      this.updateLaplaceBossBarProgress();
   }

   private void tickLaplaceBossBar() {
      if (this.bossBarPlayerUuid != null) {
         if (this.level() instanceof ServerLevel serverLevel) {
            Player var5 = serverLevel.getPlayerByUUID(this.bossBarPlayerUuid);
            Object target = this.getTarget();
            if (!(var5 instanceof ServerPlayer player)) {
               this.pauseBossCombatForOfflinePlayer();
            } else if (!this.canShowLaplaceBossBar(player)) {
               this.resetBossCombatState();
            } else {
               if (target == null) {
                  this.restoreBossCombatTarget(player);
                  target = player;
               }

               if (target != player || target.isAlive() && !this.shouldStopTarget(this, (LivingEntity)target)) {
                  if (target != player) {
                     this.hideLaplaceBossBar();
                  } else {
                     this.bossEvent.addPlayer(player);
                     this.updateLaplaceBossBarProgress();
                  }
               } else {
                  this.resetBossCombatState();
               }
            }
         } else {
            this.hideLaplaceBossBar();
         }
      }
   }

   private boolean isBoundBossBarPlayer(ServerPlayer player) {
      return this.bossBarPlayerUuid != null && player.getUUID().equals(this.bossBarPlayerUuid);
   }

   private boolean canShowLaplaceBossBar(ServerPlayer player) {
      return player.isAlive() && !player.isCreative() && !player.isSpectator();
   }

   private void restoreBossCombatTarget(ServerPlayer player) {
      this.setLastHurtByMob(player);
      this.setTarget(player);
      this.setAggressive(true);
      BrainUtils.setMemory(this, MemoryModuleType.ATTACK_TARGET, player);
      BrainUtils.clearMemory(this, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
   }

   private void updateLaplaceBossBarProgress() {
      float progress = this.getMaxHealth() <= 0.0F ? 0.0F : this.getHealth() / this.getMaxHealth();
      this.bossEvent.setProgress(Mth.clamp(progress, 0.0F, 1.0F));
   }

   private void hideLaplaceBossBar() {
      this.bossEvent.removeAllPlayers();
      this.bossBarPlayerUuid = null;
   }

   private void resetBossCombatState() {
      this.hideLaplaceBossBar();
      this.clearBossCombatTarget();
   }

   private void pauseBossCombatForOfflinePlayer() {
      this.bossEvent.removeAllPlayers();
      this.clearBossCombatTarget();
   }

   private void clearBossCombatTarget() {
      this.setLastHurtByMob(null);
      this.setTarget(null);
      this.setAggressive(false);
      BrainUtils.clearMemory(this, MemoryModuleType.ATTACK_TARGET);
      BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET);
      BrainUtils.clearMemory(this, MemoryModuleType.LOOK_TARGET);
      BrainUtils.clearMemory(this, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      this.getNavigation().stop();
   }

   private void tickBossSkills() {
      if (!this.level().isClientSide()) {
         SkillStorage skillStorage = SkillAPI.getSkillsFrom(this);
         ManasSkill falsifierSkill = (ManasSkill)UniqueSkills.FALSIFIER.get();
         ManasSkill seerSkill = (ManasSkill)UniqueSkills.SEER.get();
         ManasSkill magicResistanceSkill = (ManasSkill)ResistanceSkills.MAGIC_RESISTANCE.get();
         ManasSkill diamondPathSkill = (ManasSkill)UtilityArts.DIAMOND_PATH.get();
         ManasSkill magicBulletSkill = (ManasSkill)ProjectileArts.MAGIC_BULLET.get();
         ManasSkill roaringLionPunchSkill = (ManasSkill)MeleeArts.ROARING_LION_PUNCH.get();
         Optional<ManasSkillInstance> falsifierInstance = this.ensureBossSkill(skillStorage, falsifierSkill);
         Optional<ManasSkillInstance> seerInstance = this.ensureBossSkill(skillStorage, seerSkill);
         Optional<ManasSkillInstance> magicResistanceInstance = this.ensureBossSkill(skillStorage, magicResistanceSkill);
         Optional<ManasSkillInstance> diamondPathInstance = this.ensureBossSkill(skillStorage, diamondPathSkill);
         Optional<ManasSkillInstance> magicBulletInstance = this.ensureBossSkill(skillStorage, magicBulletSkill);
         Optional<ManasSkillInstance> roaringLionPunchInstance = this.ensureBossSkill(skillStorage, roaringLionPunchSkill);
         this.ensureBossAbilitySlots(falsifierSkill, seerSkill);
         seerInstance.ifPresent(this::activateBossToggleSkill);
         magicResistanceInstance.ifPresent(this::activateBossToggleSkill);
         LivingEntity target = this.getTarget();
         if (target != null && target.isAlive() && !this.shouldStopTarget(this, target)) {
            this.faceCombatTarget(target);
            diamondPathInstance.ifPresent(this::activateBossToggleSkill);
            falsifierInstance.ifPresent(instance -> this.tickFalsifierSkill(skillStorage, instance, target));
            seerInstance.ifPresent(instance -> this.tickSeerSkill(skillStorage, instance));
            magicBulletInstance.ifPresent(instance -> this.tickMagicBulletSkill(skillStorage, instance, target));
            roaringLionPunchInstance.ifPresent(instance -> this.tickRoaringLionPunchSkill(skillStorage, instance, target));
         } else {
            falsifierInstance.ifPresent(instance -> this.stopFalsifierFakeDeath(skillStorage, instance));
            magicBulletInstance.ifPresent(instance -> this.stopMagicBullet(skillStorage, instance));
            diamondPathInstance.ifPresent(this::deactivateBossToggleSkill);
         }
      }
   }

   private Optional<ManasSkillInstance> ensureBossSkill(SkillStorage skillStorage, ManasSkill skill) {
      Optional<ManasSkillInstance> optional = skillStorage.getSkill(skill);
      if (optional.isEmpty()) {
         TensuraSkillInstance instance = new TensuraSkillInstance(skill);
         instance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
         instance.setMastery(instance.getMaxMastery());
         SkillHelper.learnSkill(this, instance);
         optional = skillStorage.getSkill(skill);
      }

      optional.ifPresent(instancex -> {
         CompoundTag tag = instancex.getOrCreateTag();
         boolean dirty = false;
         if (!tag.getBoolean("NoMagiculeCost")) {
            tag.putBoolean("NoMagiculeCost", true);
            dirty = true;
         }

         if (instancex.getMastery() < instancex.getMaxMastery()) {
            instancex.setMastery(instancex.getMaxMastery());
            dirty = true;
         }

         if (dirty) {
            instancex.markDirty();
            skillStorage.checkAndMarkDirty(instancex);
         }
      });
      return optional;
   }

   private void ensureBossAbilitySlots(ManasSkill falsifierSkill, ManasSkill seerSkill) {
      IAbility ability = TensuraStorages.getAbilityFrom(this);
      boolean dirty = false;
      if (ability.getActivePreset() != 0) {
         ability.setActivePreset(0);
         dirty = true;
      }

      if (!ability.isAbilityInActivePreset(0, falsifierSkill, 0)) {
         ability.setAbilitySlot(0, falsifierSkill, 0);
         dirty = true;
      }

      if (!ability.isAbilityInActivePreset(1, falsifierSkill, 2)) {
         ability.setAbilitySlot(1, falsifierSkill, 2);
         dirty = true;
      }

      if (!ability.isAbilityInActivePreset(2, seerSkill, 0)) {
         ability.setAbilitySlot(2, seerSkill, 0);
         dirty = true;
      }

      if (dirty) {
         ability.markDirty();
      }
   }

   private void activateBossToggleSkill(ManasSkillInstance instance) {
      if (!instance.isToggled() && instance.canBeToggled(this) && instance.canInteractSkill(this)) {
         instance.setToggled(true);
         instance.onToggleOn(this);
      }
   }

   private void deactivateBossToggleSkill(ManasSkillInstance instance) {
      if (instance.isToggled() && instance.canBeToggled(this)) {
         instance.setToggled(false);
         instance.onToggleOff(this);
      }
   }

   private void tickFalsifierSkill(SkillStorage skillStorage, ManasSkillInstance instance, LivingEntity target) {
      if (TickingSkill.isTickingSkill(this, instance.getSkill(), 2)) {
         this.faceCombatTarget(target);
      } else if (this.tickCount % 360 == 0 && skillStorage.startHoldSkill(instance, 1, 2)) {
         this.swing(InteractionHand.MAIN_HAND, true);
      } else {
         if (this.tickCount % 180 == 40 && !this.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.FALSIFIER))) {
            this.useFalsifierConcealment(skillStorage, instance, target);
         }
      }
   }

   private void useFalsifierConcealment(SkillStorage skillStorage, ManasSkillInstance instance, LivingEntity target) {
      if (instance.canInteractSkill(this) && 0 < instance.getModes() && (!instance.onCoolDown(0) || instance.canIgnoreCoolDown(this, 0))) {
         this.faceCombatTarget(target);
         instance.onPressed(this, 0, 0);
         this.swing(InteractionHand.MAIN_HAND, true);
         skillStorage.checkAndMarkDirty(instance);
      }
   }

   private void tickSeerSkill(SkillStorage skillStorage, ManasSkillInstance instance) {
      if (this.tickCount % 220 == 80
         && !this.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.FUTURE_VISION))
         && instance.canInteractSkill(this)
         && 0 < instance.getModes()
         && (!instance.onCoolDown(0) || instance.canIgnoreCoolDown(this, 0))) {
         instance.onPressed(this, 2, 0);
         skillStorage.checkAndMarkDirty(instance);
      }
   }

   private void tickMagicBulletSkill(SkillStorage skillStorage, ManasSkillInstance instance, LivingEntity target) {
      if (!TickingSkill.isTickingSkill(this, instance.getSkill(), 0)) {
         double distanceSqr = this.distanceToSqr(target);
         if (this.tickCount % 120 == 30 && !(distanceSqr < 25.0) && !(distanceSqr > 784.0) && this.canUsePressedSkill(instance, 0)) {
            this.faceCombatTarget(target);
            if (skillStorage.startHoldSkill(instance, 4, 0)) {
               instance.getOrCreateTag().putInt("LaplaceMagicBulletStarted", this.tickCount);
               skillStorage.checkAndMarkDirty(instance);
            }
         }
      } else {
         this.faceCombatTarget(target);
         int startedTick = instance.getOrCreateTag().getInt("LaplaceMagicBulletStarted");
         if (startedTick <= 0 || this.tickCount - startedTick >= 30) {
            this.stopMagicBullet(skillStorage, instance);
         }
      }
   }

   private void stopMagicBullet(SkillStorage skillStorage, ManasSkillInstance instance) {
      if (TickingSkill.isTickingSkill(this, instance.getSkill(), 0)) {
         skillStorage.handleSkillRelease(instance, 4, 0, false);
      }

      instance.getOrCreateTag().remove("LaplaceMagicBulletStarted");
      instance.markDirty();
      skillStorage.checkAndMarkDirty(instance);
   }

   private void tickRoaringLionPunchSkill(SkillStorage skillStorage, ManasSkillInstance instance, LivingEntity target) {
      if (this.tickCount % 90 == 10 && !(this.distanceToSqr(target) > 36.0) && this.canUsePressedSkill(instance, 0)) {
         this.faceCombatTarget(target);
         this.useRoaringLionPunch(skillStorage, instance);
      }
   }

   private void useRoaringLionPunch(SkillStorage skillStorage, ManasSkillInstance instance) {
      ItemStack mainHand = this.getItemBySlot(EquipmentSlot.MAINHAND).copy();
      ItemStack inventoryMainHand = this.inventory.getItem(this.getSlotId(EquipmentSlot.MAINHAND)).copy();
      this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
      this.inventory.setItem(this.getSlotId(EquipmentSlot.MAINHAND), ItemStack.EMPTY);
      instance.onPressed(this, 5, 0);
      this.setItemSlot(EquipmentSlot.MAINHAND, mainHand);
      this.inventory.setItem(this.getSlotId(EquipmentSlot.MAINHAND), inventoryMainHand);
      skillStorage.checkAndMarkDirty(instance);
   }

   private boolean canUsePressedSkill(ManasSkillInstance instance, int mode) {
      return instance.canInteractSkill(this) && mode < instance.getModes() && (!instance.onCoolDown(mode) || instance.canIgnoreCoolDown(this, mode));
   }

   private void stopFalsifierFakeDeath(SkillStorage skillStorage, ManasSkillInstance instance) {
      if (TickingSkill.isTickingSkill(this, instance.getSkill(), 2)) {
         skillStorage.handleSkillRelease(instance, 1, 2, true);
      }

      instance.markDirty();
      skillStorage.checkAndMarkDirty(instance);
   }

   private void faceCombatTarget(LivingEntity target) {
      BrainUtils.setMemory(this, MemoryModuleType.LOOK_TARGET, new EntityTracker(target, true));
      this.getLookControl().setLookAt(target, 30.0F, 30.0F);
      this.lookAt(target, 30.0F, 30.0F);
      this.setYRot(this.yHeadRot);
      this.yBodyRot = this.getYRot();
      this.yBodyRotO = this.yBodyRot;
   }
}
