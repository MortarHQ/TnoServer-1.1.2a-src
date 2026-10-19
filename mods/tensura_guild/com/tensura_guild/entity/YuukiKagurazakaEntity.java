package com.tensura_guild.entity;

import com.tensura_guild.GuildYuukiAggroEvents;
import com.tensura_guild.data.GuildSignatureData;
import com.tensura_guild.dialogue.GuildDialogue;
import com.tensura_guild.dialogue.GuildDialogueManager;
import com.tensura_guild.network.OpenDialoguePayload;
import com.tensura_guild.registry.GuildAttachments;
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
import io.github.manasmods.tensura.data.TensuraTags.MobEffects;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
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
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.IAbility;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
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

public class YuukiKagurazakaEntity extends OtherworlderEntity implements SmartBrainOwner<YuukiKagurazakaEntity>, ITeleportation {
   public static final ResourceLocation INTRO_DIALOGUE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "yuuki_intro");
   public static final ResourceLocation GUILD_MENU_DIALOGUE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "yuuki_guild_menu");
   private static final ResourceLocation COMBAT_KATANA_ID = ResourceLocation.fromNamespaceAndPath("tensura", "pure_magisteel_katana");
   private static final double DIALOGUE_MAX_DISTANCE_SQR = 16.0;
   private static final int ANTI_SKILL_SLOT = 0;
   private static final int GREED_FLARE_SLOT = 1;
   private static final int GREED_DEATH_SLOT = 2;
   private static final int GREED_FLARE_MODE = 1;
   private static final int GREED_DEATH_MODE = 2;
   private static final int GREED_FLARE_INTERVAL = 160;
   private static final int GREED_DEATH_INTERVAL = 400;
   private static final int ANTI_SKILL_HIT_DURATION = 100;
   private static final double GREED_FLARE_MAX_DISTANCE_SQR = 576.0;
   private static final double GREED_DEATH_MAX_DISTANCE_SQR = 9.0;
   private static final String BOSS_BAR_PLAYER_TAG = "YuukiBossBarPlayer";
   private static final String SPAWNER_DORMANT_TAG = "SpawnerDormant";
   private final ServerBossEvent bossEvent = new ServerBossEvent(
      Component.translatable("bossbar.tensura_guild.yuuki_kagurazaka"), BossBarColor.BLUE, BossBarOverlay.NOTCHED_20
   );
   private SmartBrainSchedule schedule;
   private UUID dialoguePlayerUuid;
   private UUID bossBarPlayerUuid;
   private boolean spawnerDormant;

   public YuukiKagurazakaEntity(EntityType<? extends YuukiKagurazakaEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
      this.disableItemPickup();
   }

   public static Builder setAttributes() {
      return TensuraTamableEntity.setAttributes()
         .add(Attributes.ARMOR, 15.0)
         .add(Attributes.ATTACK_DAMAGE, 30.0)
         .add(Attributes.ATTACK_KNOCKBACK, 2.0)
         .add(Attributes.MAX_HEALTH, 600.0)
         .add(Attributes.MOVEMENT_SPEED, 0.23)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
         .add(Attributes.STEP_HEIGHT, 1.0)
         .add(Attributes.ENTITY_INTERACTION_RANGE, 2.5)
         .add(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, 10.0)
         .add(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, 4.0)
         .add(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, 4.0);
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
      return ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/entity/otherworlder/yuuki_kagurazaka.png");
   }

   public List<ManasSkill> getUniqueSkills() {
      return List.of((ManasSkill)UniqueSkills.CREATOR.get(), (ManasSkill)UniqueSkills.ANTI_SKILL.get(), (ManasSkill)UniqueSkills.GREED.get());
   }

   public YuukiKagurazakaEntity getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
      YuukiKagurazakaEntity entity = (YuukiKagurazakaEntity)GuildEntityTypes.YUUKI_KAGURAZAKA.get().create(pLevel);
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

   @NotNull
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, SpawnGroupData pSpawnData) {
      TensuraBehaviourHelper.setHome(this, pLevel.getLevel(), this.blockPosition());
      this.setPersistenceRequired();
      SpawnGroupData spawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
      this.disableItemPickup();
      return spawnData;
   }

   protected boolean shouldDespawnInPeaceful() {
      return false;
   }

   @NotNull
   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      if (hand == InteractionHand.MAIN_HAND && !this.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
         if (((GuildSignatureData)serverPlayer.getData(GuildAttachments.GUILD_SIGNATURE)).hasSignature() && this.startDialogue(serverPlayer)) {
            PacketDistributor.sendToPlayer(
               serverPlayer, new OpenDialoguePayload(GUILD_MENU_DIALOGUE, this.getId(), createGuildMenuDialogue()), new CustomPacketPayload[0]
            );
         } else if (!((Set)serverPlayer.getData(GuildAttachments.SEEN_DIALOGUES)).contains(INTRO_DIALOGUE) && this.startDialogue(serverPlayer)) {
            GuildDialogue dialogue = GuildDialogueManager.get(INTRO_DIALOGUE);
            if (dialogue != null) {
               PacketDistributor.sendToPlayer(serverPlayer, new OpenDialoguePayload(INTRO_DIALOGUE, this.getId(), dialogue), new CustomPacketPayload[0]);
            } else {
               this.stopDialogue(serverPlayer);
            }
         }
      }

      return InteractionResult.sidedSuccess(this.level().isClientSide());
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
      if (hurt && target instanceof LivingEntity livingTarget) {
         this.applyAntiSkillOnHit(livingTarget);
         if (livingTarget instanceof ServerPlayer player && !player.isAlive() && player.getUUID().equals(this.bossBarPlayerUuid)) {
            this.resetBossCombatState();
         }
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
         compound.putUUID("YuukiBossBarPlayer", this.bossBarPlayerUuid);
      }

      if (this.spawnerDormant) {
         compound.putBoolean("SpawnerDormant", true);
      }
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.bossBarPlayerUuid = compound.hasUUID("YuukiBossBarPlayer") ? compound.getUUID("YuukiBossBarPlayer") : null;
      this.spawnerDormant = compound.getBoolean("SpawnerDormant");
      if (this.spawnerDormant) {
         this.setSpawnerDormant(true);
      }

      this.disableItemPickup();
   }

   public boolean wantsToPickUp(ItemStack stack) {
      return false;
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      if (this.isBoundBossBarPlayer(player) && this.canShowYuukiBossBar(player)) {
         this.restoreBossCombatTarget(player);
         this.showYuukiBossBar(player);
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

      this.tickDialogueLock();
      this.tickCombatKatana();
      this.tickBossSkills();
      this.tickYuukiBossBar();
   }

   public List<ExtendedSensor<YuukiKagurazakaEntity>> getSensors() {
      return ObjectArrayList.of(new ExtendedSensor[]{new NearbyLivingEntitySensor(), new HurtBySensor(), new SleepSensor()});
   }

   public BrainActivityGroup<YuukiKagurazakaEntity> getCoreTasks() {
      return BrainActivityGroup.coreTasks(new Behavior[]{new WakeUp().stopIf(entity -> {
         if (!TensuraBehaviourHelper.canContinueToSleep(entity)) {
            entity.stopSleeping();
            return true;
         } else {
            return false;
         }
      }), new LookAtTarget(), new FloatToSurfaceOfFluid(), getDormantAwareMoveToWalkTarget()});
   }

   public BrainActivityGroup<YuukiKagurazakaEntity> getIdleTasks() {
      ExtendedBehaviour<YuukiKagurazakaEntity> normalIdle = new FirstApplicableBehaviour(
            new ExtendedBehaviour[]{
               TensuraBehaviourHelper.getMoveToWanderPos(),
               TensuraBehaviourHelper.getPreyTargeting(this, entity -> false),
               new SubordinateFollowOwner(),
               new SetPlayerLookTarget(),
               new SetRandomLookTarget()
            }
         )
         .startCondition(entity -> !entity.isSpawnerDormant());
      ExtendedBehaviour<YuukiKagurazakaEntity> dormantIdle = new FirstApplicableBehaviour(
            new ExtendedBehaviour[]{new SetPlayerLookTarget(), new SetRandomLookTarget(), new Idle().runFor(entity -> entity.getRandom().nextInt(30, 60))}
         )
         .startCondition(YuukiKagurazakaEntity::isSpawnerDormant);
      ExtendedBehaviour<YuukiKagurazakaEntity> randomWalkOrIdle = new OneRandomBehaviour(
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

   public BrainActivityGroup<YuukiKagurazakaEntity> getFightTasks() {
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

   public Map<Activity, BrainActivityGroup<? extends YuukiKagurazakaEntity>> getAdditionalTasks() {
      Map<Activity, BrainActivityGroup<? extends YuukiKagurazakaEntity>> tasks = new Object2ObjectOpenHashMap();
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

   private static ExtendedBehaviour<YuukiKagurazakaEntity> getDormantAwareMoveToWalkTarget() {
      return new MoveToWalkTarget()
         .cooldownFor(entity -> 0)
         .startCondition(entity -> !entity.isOrderedToSit() && !entity.isSleeping() && !entity.isSpawnerDormant())
         .stopIf(entity -> entity.isOrderedToSit() || entity.isSpawnerDormant());
   }

   private void disableItemPickup() {
      this.setCanPickUpLoot(false);
   }

   public boolean startDialogue(ServerPlayer player) {
      if (this.dialoguePlayerUuid != null && !this.dialoguePlayerUuid.equals(player.getUUID())) {
         return false;
      }

      this.dialoguePlayerUuid = player.getUUID();
      this.applyDialogueLock(player);
      return true;
   }

   public void stopDialogue(Player player) {
      if (player != null && this.dialoguePlayerUuid != null && this.dialoguePlayerUuid.equals(player.getUUID())) {
         this.dialoguePlayerUuid = null;
         this.clearDialogueLock();
      }
   }

   public boolean isInDialogue() {
      return this.dialoguePlayerUuid != null;
   }

   private void rememberAttacker(Entity source) {
      if (!this.level().isClientSide() && source instanceof LivingEntity attacker) {
         if (attacker != this && attacker.isAlive() && !this.isAlliedTo(attacker)) {
            if (!(attacker instanceof Player player && (player.isCreative() || player.isSpectator()))) {
               this.wakeFromSpawnerDormancy();
               this.cancelDialogue();
               this.setLastHurtByMob(attacker);
               this.setTarget(attacker);
               this.setAggressive(true);
               BrainUtils.setMemory(this, MemoryModuleType.ATTACK_TARGET, attacker);
               GuildYuukiAggroEvents.requestLaplaceAssist(this, attacker);
               if (attacker instanceof ServerPlayer serverPlayer) {
                  this.showYuukiBossBar(serverPlayer);
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

   private void cancelDialogue() {
      if (this.dialoguePlayerUuid != null) {
         this.dialoguePlayerUuid = null;
         this.clearDialogueLock();
      }
   }

   private void tickDialogueLock() {
      if (this.level() instanceof ServerLevel serverLevel && this.dialoguePlayerUuid != null) {
         Player player = serverLevel.getPlayerByUUID(this.dialoguePlayerUuid);
         if (player == null || !player.isAlive() || this.distanceToSqr(player) > 16.0) {
            this.dialoguePlayerUuid = null;
            this.clearDialogueLock();
         } else if (!this.hurtMarked && this.hurtTime <= 0 && this.getTarget() == null && !BrainUtils.hasMemory(this, MemoryModuleType.ATTACK_TARGET)) {
            this.applyDialogueLock(player);
         } else {
            this.stopDialogue(player);
         }
      }
   }

   private void applyDialogueLock(Player player) {
      this.getNavigation().stop();
      BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET);
      BrainUtils.clearMemory(this, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      BrainUtils.setMemory(this, MemoryModuleType.LOOK_TARGET, new EntityTracker(player, true));
      this.getLookControl().setLookAt(player, 30.0F, 30.0F);
      this.lookAt(player, 30.0F, 30.0F);
      this.setYRot(this.yHeadRot);
      this.yBodyRot = this.getYRot();
      this.yBodyRotO = this.yBodyRot;
   }

   private void clearDialogueLock() {
      BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET);
      BrainUtils.clearMemory(this, MemoryModuleType.LOOK_TARGET);
      BrainUtils.clearMemory(this, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      this.getNavigation().stop();
   }

   private void tickCombatKatana() {
      boolean fighting = this.getTarget() != null || BrainUtils.hasMemory(this, MemoryModuleType.ATTACK_TARGET);
      Item katana = (Item)BuiltInRegistries.ITEM.get(COMBAT_KATANA_ID);
      if (katana != Items.AIR) {
         ItemStack mainHand = this.getItemBySlot(EquipmentSlot.MAINHAND);
         if (fighting) {
            if (!mainHand.is(katana)) {
               ItemStack weapon = katana.getDefaultInstance();
               this.setItemSlot(EquipmentSlot.MAINHAND, weapon);
               this.inventory.setItem(this.getSlotId(EquipmentSlot.MAINHAND), weapon);
            }
         } else if (mainHand.is(katana)) {
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            this.inventory.setItem(this.getSlotId(EquipmentSlot.MAINHAND), ItemStack.EMPTY);
         }
      }
   }

   private void showYuukiBossBar(ServerPlayer player) {
      if (!player.getUUID().equals(this.bossBarPlayerUuid)) {
         this.bossEvent.removeAllPlayers();
         this.bossBarPlayerUuid = player.getUUID();
      }

      this.bossEvent.addPlayer(player);
      this.updateYuukiBossBarProgress();
   }

   private void tickYuukiBossBar() {
      if (this.bossBarPlayerUuid != null) {
         if (this.level() instanceof ServerLevel serverLevel) {
            Player var5 = serverLevel.getPlayerByUUID(this.bossBarPlayerUuid);
            Object target = this.getTarget();
            if (!(var5 instanceof ServerPlayer player)) {
               this.pauseBossCombatForOfflinePlayer();
            } else if (!this.canShowYuukiBossBar(player)) {
               this.resetBossCombatState();
            } else {
               if (target == null) {
                  this.restoreBossCombatTarget(player);
                  target = player;
               }

               if (target != player || target.isAlive() && !this.shouldStopTarget(this, (LivingEntity)target)) {
                  if (target != player) {
                     this.hideYuukiBossBar();
                  } else {
                     this.bossEvent.addPlayer(player);
                     this.updateYuukiBossBarProgress();
                  }
               } else {
                  this.resetBossCombatState();
               }
            }
         } else {
            this.hideYuukiBossBar();
         }
      }
   }

   private boolean isBoundBossBarPlayer(ServerPlayer player) {
      return this.bossBarPlayerUuid != null && player.getUUID().equals(this.bossBarPlayerUuid);
   }

   private boolean canShowYuukiBossBar(ServerPlayer player) {
      return player.isAlive() && !player.isCreative() && !player.isSpectator();
   }

   private void restoreBossCombatTarget(ServerPlayer player) {
      this.cancelDialogue();
      this.setLastHurtByMob(player);
      this.setTarget(player);
      this.setAggressive(true);
      BrainUtils.setMemory(this, MemoryModuleType.ATTACK_TARGET, player);
      BrainUtils.clearMemory(this, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
   }

   private void updateYuukiBossBarProgress() {
      float progress = this.getMaxHealth() <= 0.0F ? 0.0F : this.getHealth() / this.getMaxHealth();
      this.bossEvent.setProgress(Mth.clamp(progress, 0.0F, 1.0F));
   }

   private void hideYuukiBossBar() {
      this.bossEvent.removeAllPlayers();
      this.bossBarPlayerUuid = null;
   }

   private void resetBossCombatState() {
      this.hideYuukiBossBar();
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
         ManasSkill antiSkill = (ManasSkill)UniqueSkills.ANTI_SKILL.get();
         ManasSkill greedSkill = (ManasSkill)UniqueSkills.GREED.get();
         Optional<ManasSkillInstance> antiSkillInstance = this.ensureBossSkill(skillStorage, antiSkill);
         Optional<ManasSkillInstance> greedInstance = this.ensureBossSkill(skillStorage, greedSkill);
         this.ensureBossAbilitySlots(antiSkill, greedSkill);
         antiSkillInstance.ifPresent(this::activateBossAntiSkill);
         LivingEntity target = this.getTarget();
         if (target != null && target.isAlive() && !this.shouldStopTarget(this, target)) {
            this.faceCombatTarget(target);
            greedInstance.ifPresent(instance -> this.tickGreedSkill(skillStorage, instance, target));
         } else {
            greedInstance.ifPresent(instance -> this.stopGreedDeath(skillStorage, instance));
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

   private void ensureBossAbilitySlots(ManasSkill antiSkill, ManasSkill greedSkill) {
      IAbility ability = TensuraStorages.getAbilityFrom(this);
      boolean dirty = false;
      if (ability.getActivePreset() != 0) {
         ability.setActivePreset(0);
         dirty = true;
      }

      if (!ability.isAbilityInActivePreset(0, antiSkill, 0)) {
         ability.setAbilitySlot(0, antiSkill, 0);
         dirty = true;
      }

      if (!ability.isAbilityInActivePreset(1, greedSkill, 1)) {
         ability.setAbilitySlot(1, greedSkill, 1);
         dirty = true;
      }

      if (!ability.isAbilityInActivePreset(2, greedSkill, 2)) {
         ability.setAbilitySlot(2, greedSkill, 2);
         dirty = true;
      }

      if (dirty) {
         ability.markDirty();
      }
   }

   private void activateBossAntiSkill(ManasSkillInstance instance) {
      if (!instance.isToggled() && instance.canBeToggled(this) && instance.canInteractSkill(this)) {
         instance.setToggled(true);
         instance.onToggleOn(this);
      }
   }

   private void tickGreedSkill(SkillStorage skillStorage, ManasSkillInstance instance, LivingEntity target) {
      if (TickingSkill.isTickingSkill(this, instance.getSkill(), 2)) {
         if (this.distanceToSqr(target) > 9.0) {
            this.stopGreedDeath(skillStorage, instance);
         } else {
            this.faceCombatTarget(target);
         }
      } else if (this.tickCount % 400 == 0 && this.distanceToSqr(target) <= 9.0 && skillStorage.startHoldSkill(instance, 2, 2)) {
         this.swing(InteractionHand.MAIN_HAND, true);
      } else {
         if (this.tickCount % 160 == 40 && this.distanceToSqr(target) <= 576.0) {
            this.useGreedFlare(skillStorage, instance, target);
         }
      }
   }

   private void useGreedFlare(SkillStorage skillStorage, ManasSkillInstance instance, LivingEntity target) {
      if (instance.canInteractSkill(this) && 1 < instance.getModes() && (!instance.onCoolDown(1) || instance.canIgnoreCoolDown(this, 1))) {
         this.faceCombatTarget(target);
         instance.onPressed(this, 1, 1);
         this.swing(InteractionHand.MAIN_HAND, true);
         skillStorage.checkAndMarkDirty(instance);
      }
   }

   private void stopGreedDeath(SkillStorage skillStorage, ManasSkillInstance instance) {
      if (TickingSkill.isTickingSkill(this, instance.getSkill(), 2)) {
         skillStorage.handleSkillRelease(instance, 2, 2, true);
      }

      instance.getOrCreateTag().remove("target");
      instance.getOrCreateTag().putInt("heldSeconds", 0);
      instance.markDirty();
      skillStorage.checkAndMarkDirty(instance);
   }

   private void applyAntiSkillOnHit(LivingEntity target) {
      if (!this.level().isClientSide() && target != this && !target.isAlliedTo(this)) {
         ManasSkill antiSkill = (ManasSkill)UniqueSkills.ANTI_SKILL.get();
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(this).getSkill(antiSkill);
         if (!optional.isEmpty() && (optional.get().isToggled() || TensuraStorages.getAbilityFrom(this).isAbilityInActivePreset(antiSkill))) {
            target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), 100, 0, false, false, false), this);
            TensuraMobEffect.removePredicateEffect(target, effect -> effect.is(MobEffects.AFFECTED_BY_ANTI_SKILL));
            TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.ENCHANTED_HIT, 1.0);
         }
      }
   }

   private void faceCombatTarget(LivingEntity target) {
      BrainUtils.setMemory(this, MemoryModuleType.LOOK_TARGET, new EntityTracker(target, true));
      this.getLookControl().setLookAt(target, 30.0F, 30.0F);
      this.lookAt(target, 30.0F, 30.0F);
      this.setYRot(this.yHeadRot);
      this.yBodyRot = this.getYRot();
      this.yBodyRotO = this.yBodyRot;
   }

   private static GuildDialogue createGuildMenuDialogue() {
      return new GuildDialogue(List.of(new GuildDialogue.Line("dialogue.tensura_guild.speaker.yuuki", "dialogue.tensura_guild.yuuki_guild_menu.1", true, true)));
   }
}
