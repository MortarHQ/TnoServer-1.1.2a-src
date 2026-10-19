package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.LootChestBlockEntity;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESCrests;
import cn.leolezury.eternalstarlight.common.data.ESPaintingVariants;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESServerBossEvent;
import cn.leolezury.eternalstarlight.common.entity.living.goal.GatekeeperTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.handler.ESCommonHandler;
import cn.leolezury.eternalstarlight.common.network.OpenGatekeeperGuiPacket;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESBookUtil;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ModelSnapshot;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.Util;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;

public class TheGatekeeper extends ESBoss implements Npc, Merchant {
   public static final int GUI_RESPONSE_EMPTY = 0;
   public static final int GUI_RESPONSE_CHALLENGE = 1;
   public static final int GUI_RESPONSE_TRADE = 2;
   public static final int GUI_RESPONSE_LEAVE = 3;
   public static final byte EVENT_TALK = 100;
   private static final byte EVENT_BLOCK = 101;
   private static final String TAG_OFFERS = "offers";
   private static final String TAG_GATEKEEPER_NAME = "gatekeeper_name";
   private static final String TAG_FIGHT_TARGET = "fight_target";
   private static final String TAG_STANDARD_FIGHT = "standard_fight";
   private static final String TAG_RESTOCK_COOLDOWN = "restock_cooldown";
   private int noTargetTime;
   private final List<BlockPos> recentPositions = new ArrayList<>();
   public boolean healInterrupted = false;
   public boolean healInterruptedIndirect = false;
   public int healCount;
   public int healInterruptedCount;
   protected static final EntityDataAccessor<Direction> STAND_DIRECTION = SynchedEntityData.defineId(TheGatekeeper.class, EntityDataSerializers.DIRECTION);
   private final ESServerBossEvent bossEvent = new ESServerBossEvent(this, this.getUUID(), BossBarColor.WHITE, false);
   private final BehaviorManager<TheGatekeeper> behaviorManager = new BehaviorManager(
      this,
      List.of(
         new GatekeeperStepBackPhase(),
         new GatekeeperJumpStartPhase(),
         new GatekeeperJumpTransitionPhase(),
         new GatekeeperJumpEndPhase(),
         new GatekeeperGreatswordPhase(),
         new GatekeeperHammerPhase(),
         new GatekeeperDashPhase(),
         new GatekeeperGreatswordComboPhase(),
         new GatekeeperBowPhase(),
         new GatekeeperBowComboPhase(),
         new GatekeeperCastFireballPhase(),
         new GatekeeperTeleportPhase(),
         new GatekeeperEatPhase(),
         new GatekeeperEatFailPhase()
      )
   );
   public AnimationState sitAnimationState = new AnimationState();
   public AnimationState standAnimationState = new AnimationState();
   public AnimationState talkAnimationState = new AnimationState();
   public AnimationState stepBackAnimationState = new AnimationState();
   public AnimationState jumpStartAnimationState = new AnimationState();
   public AnimationState jumpTransitionAnimationState = new AnimationState();
   public AnimationState jumpEndAnimationState = new AnimationState();
   public AnimationState greatswordAnimationState = new AnimationState();
   public AnimationState hammerAnimationState = new AnimationState();
   public AnimationState dashAnimationState = new AnimationState();
   public AnimationState greatswordComboAnimationState = new AnimationState();
   public AnimationState bowAnimationState = new AnimationState();
   public AnimationState bowComboAnimationState = new AnimationState();
   public AnimationState castFireballAnimationState = new AnimationState();
   public AnimationState teleportAnimationState = new AnimationState();
   public AnimationState blockAnimationState = new AnimationState();
   public AnimationState eatAnimationState = new AnimationState();
   public AnimationState eatFailAnimationState = new AnimationState();
   public final List<Pair<Vec3, ModelSnapshot>> trailSnapshots = new ArrayList<>(50);
   public float lastTrailTick = 0.0F;
   private String gatekeeperName = "TheGatekeeper";
   @Nullable
   private Player customer;
   @Nullable
   protected MerchantOffers offers;
   private int restockCooldown;
   @Nullable
   private ServerPlayer conversationTarget;
   @Nullable
   private String fightTarget;
   private boolean standardFight = true;

   public Direction getStandDirection() {
      return (Direction)this.getEntityData().get(STAND_DIRECTION);
   }

   public void setStandDirection(Direction standDirection) {
      this.getEntityData().set(STAND_DIRECTION, standDirection);
   }

   public TheGatekeeper(EntityType<? extends TheGatekeeper> entityType, Level level) {
      super(entityType, level);
      this.noCulling = true;
   }

   public boolean shouldAddTrailSnapshot() {
      return Mth.degreesDifferenceAbs(this.getYRot(), this.yBodyRot) < 30.0F
         && Mth.degreesDifferenceAbs(this.getYRot(), this.yBodyRotO) < 30.0F
         && Mth.degreesDifferenceAbs(this.yBodyRot, this.yBodyRotO) < 30.0F
         && (
            this.getBehaviorState() == 2 && this.getBehaviorTicks() >= 15
               || this.getBehaviorState() == 3
               || this.getBehaviorState() == 7 && this.getBehaviorTicks() > 16 && this.getBehaviorTicks() < 25
         );
   }

   public void setStandardFight(boolean standardFight) {
      this.standardFight = standardFight;
   }

   public boolean isStandardFight() {
      return this.standardFight;
   }

   public void setFightTargetName(String fightTarget) {
      this.fightTarget = fightTarget;
   }

   public Optional<? extends Player> getFightTarget() {
      return this.level().players().stream().filter(p -> p.getName().getString().equals(this.fightTarget)).findFirst();
   }

   @Override
   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(STAND_DIRECTION, Direction.UP);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      if (compoundTag.contains("offers")) {
         DataResult<MerchantOffers> parsed = MerchantOffers.CODEC
            .parse(this.registryAccess().createSerializationContext(NbtOps.INSTANCE), compoundTag.get("offers"));
         parsed.resultOrPartial(Util.prefix("Failed to load offers: ", EternalStarlight.LOGGER::warn))
            .ifPresent(merchantOffers -> this.offers = merchantOffers);
      }

      this.gatekeeperName = compoundTag.getString("gatekeeper_name");
      this.fightTarget = compoundTag.getString("fight_target");
      if (compoundTag.contains("standard_fight")) {
         this.standardFight = compoundTag.getBoolean("standard_fight");
      }

      this.restockCooldown = compoundTag.getInt("restock_cooldown");
      this.bossEvent.setId(this.getUUID());
      if (this.offers == null) {
         this.offers = new MerchantOffers();
         this.addTrades();
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      if (!this.level().isClientSide) {
         MerchantOffers merchantOffers = this.getOffers();
         if (!merchantOffers.isEmpty()) {
            compoundTag.put(
               "offers", (Tag)MerchantOffers.CODEC.encodeStart(this.registryAccess().createSerializationContext(NbtOps.INSTANCE), merchantOffers).getOrThrow()
            );
         }
      }

      compoundTag.putString("gatekeeper_name", this.gatekeeperName);
      if (this.fightTarget != null) {
         compoundTag.putString("fight_target", this.fightTarget);
      }

      compoundTag.putBoolean("standard_fight", this.standardFight);
      compoundTag.putInt("restock_cooldown", this.restockCooldown);
   }

   public void startSeenByPlayer(ServerPlayer serverPlayer) {
      super.startSeenByPlayer(serverPlayer);
      this.bossEvent.addPlayer(serverPlayer);
   }

   public void stopSeenByPlayer(ServerPlayer serverPlayer) {
      super.stopSeenByPlayer(serverPlayer);
      this.bossEvent.removePlayer(serverPlayer);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.5, false) {
         protected void checkAndPerformAttack(LivingEntity livingEntity) {
         }
      });
      this.goalSelector.addGoal(2, new LookAtTargetGoal(this));
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(0, new GatekeeperTargetGoal(this) {
         @Override
         public boolean canUse() {
            return super.canUse() && TheGatekeeper.this.standardFight && TheGatekeeper.this.isActivated();
         }
      });
      this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, TheGatekeeper.class) {
         public boolean canUse() {
            return super.canUse() && !TheGatekeeper.this.standardFight && TheGatekeeper.this.isActivated();
         }
      }).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true) {
         public boolean canUse() {
            return super.canUse() && !TheGatekeeper.this.standardFight && TheGatekeeper.this.isActivated();
         }
      });
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.theGatekeeper.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.theGatekeeper.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.theGatekeeper.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.theGatekeeper.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.6)
         .add(Attributes.ARMOR_TOUGHNESS, 5.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData
   ) {
      spawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
      RandomSource randomSource = serverLevelAccessor.getRandom();
      this.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
      return spawnGroupData;
   }

   protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
      super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
      Arrays.fill(this.handDropChances, 0.0F);
      this.setItemSlot(EquipmentSlot.MAINHAND, ESItems.GLISTERING_SWORD.get().getDefaultInstance());
   }

   protected BodyRotationControl createBodyControl() {
      return new BodyRotationControl(this) {
         public void clientTick() {
            if (TheGatekeeper.this.standAnimationState.isStarted()) {
               TheGatekeeper.this.yBodyRot = Mth.wrapDegrees(TheGatekeeper.this.getStandDirection().toYRot());
               TheGatekeeper.this.setYRot(Mth.wrapDegrees(TheGatekeeper.this.getStandDirection().toYRot()));
               TheGatekeeper.this.yHeadRot = Mth.rotateIfNecessary(
                  TheGatekeeper.this.yHeadRot, TheGatekeeper.this.yBodyRot, TheGatekeeper.this.getMaxHeadYRot()
               );
            } else {
               super.clientTick();
            }
         }
      };
   }

   public int getUseItemRemainingTicks() {
      if (this.getBehaviorState() == 9 && this.getBehaviorTicks() >= 6 && this.getBehaviorTicks() <= 26) {
         int useDuration = this.getMainHandItem().getUseDuration(this);
         return useDuration - (this.getBehaviorTicks() - 6);
      }

      if (this.getBehaviorState() == 10) {
         int useDuration = this.getMainHandItem().getUseDuration(this);
         if (this.getBehaviorTicks() >= 5 && this.getBehaviorTicks() <= 20) {
            return useDuration - Math.round((this.getBehaviorTicks() - 5) * 1.3F);
         }

         if (this.getBehaviorTicks() >= 30 && this.getBehaviorTicks() <= 38) {
            return useDuration - Math.round((this.getBehaviorTicks() - 30) * 2.5F);
         }

         if (this.getBehaviorTicks() >= 47 && this.getBehaviorTicks() <= 55) {
            return useDuration - Math.round((this.getBehaviorTicks() - 47) * 2.5F);
         }
      }

      return super.getUseItemRemainingTicks();
   }

   public boolean isUsingItem() {
      if (this.getBehaviorState() == 9 && this.getBehaviorTicks() >= 6 && this.getBehaviorTicks() <= 26) {
         return true;
      } else {
         return this.getBehaviorState() != 10
               || (this.getBehaviorTicks() < 5 || this.getBehaviorTicks() > 20)
                  && (this.getBehaviorTicks() < 30 || this.getBehaviorTicks() > 38)
                  && (this.getBehaviorTicks() < 47 || this.getBehaviorTicks() > 55)
            ? super.isUsingItem()
            : true;
      }
   }

   public ItemStack getUseItem() {
      if (this.getBehaviorState() == 9 && this.getBehaviorTicks() >= 6 && this.getBehaviorTicks() <= 26) {
         return this.getMainHandItem();
      } else {
         return this.getBehaviorState() != 10
               || (this.getBehaviorTicks() < 5 || this.getBehaviorTicks() > 20)
                  && (this.getBehaviorTicks() < 30 || this.getBehaviorTicks() > 38)
                  && (this.getBehaviorTicks() < 47 || this.getBehaviorTicks() > 55)
            ? super.getUseItem()
            : this.getMainHandItem();
      }
   }

   public void handleEntityEvent(byte b) {
      if (b == 100) {
         this.talkAnimationState.start(this.tickCount);
      } else if (b == 101) {
         this.blockAnimationState.start(this.tickCount);
      } else {
         super.handleEntityEvent(b);
      }
   }

   public void stopAllAnimStates() {
      this.talkAnimationState.stop();
      this.stepBackAnimationState.stop();
      this.jumpStartAnimationState.stop();
      this.jumpTransitionAnimationState.stop();
      this.jumpEndAnimationState.stop();
      this.greatswordAnimationState.stop();
      this.hammerAnimationState.stop();
      this.dashAnimationState.stop();
      this.greatswordComboAnimationState.stop();
      this.bowAnimationState.stop();
      this.bowComboAnimationState.stop();
      this.castFireballAnimationState.stop();
      this.teleportAnimationState.stop();
      this.blockAnimationState.stop();
      this.eatAnimationState.stop();
      this.eatFailAnimationState.stop();
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
      if (accessor.equals(BEHAVIOR_STATE)) {
         this.stopAllAnimStates();
         switch (this.getBehaviorState()) {
            case 1:
               this.stepBackAnimationState.start(this.tickCount);
               break;
            case 2:
               this.jumpStartAnimationState.start(this.tickCount);
               break;
            case 3:
               this.jumpTransitionAnimationState.start(this.tickCount);
               break;
            case 4:
               this.jumpEndAnimationState.start(this.tickCount);
               break;
            case 5:
               this.greatswordAnimationState.start(this.tickCount);
               break;
            case 6:
               this.hammerAnimationState.start(this.tickCount);
               break;
            case 7:
               this.dashAnimationState.start(this.tickCount);
               break;
            case 8:
               this.greatswordComboAnimationState.start(this.tickCount);
               break;
            case 9:
               this.bowAnimationState.start(this.tickCount);
               break;
            case 10:
               this.bowComboAnimationState.start(this.tickCount);
               break;
            case 11:
               this.castFireballAnimationState.start(this.tickCount);
               break;
            case 12:
               this.teleportAnimationState.start(this.tickCount);
               break;
            case 13:
               this.eatAnimationState.start(this.tickCount);
               break;
            case 14:
               this.eatFailAnimationState.start(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(accessor);
   }

   public boolean canAttack(LivingEntity livingEntity) {
      return super.canAttack(livingEntity) && this.isActivated();
   }

   @Override
   public void initializeBoss() {
      super.initializeBoss();
      this.setActivated(false);
      this.gatekeeperName = ESCommonHandler.getGatekeeperName();
   }

   @Override
   public boolean shouldPlayBossMusic() {
      return super.shouldPlayBossMusic() && this.isActivated();
   }

   protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
      if (!this.level().isClientSide
         && player instanceof ServerPlayer serverPlayer
         && serverPlayer.getServer() != null
         && this.getTradingPlayer() == null
         && !this.isActivated()
         && this.getTarget() == null
         && this.getFightTarget().isEmpty()) {
         AdvancementHolder killDragon = serverPlayer.getServer().getAdvancements().get(ResourceLocation.withDefaultNamespace("end/kill_dragon"));
         if (killDragon != null && serverPlayer.getAdvancements().getOrStartProgress(killDragon).isDone() && !isPlayerPermitted(serverPlayer)) {
            permitPlayer(serverPlayer);
            ItemStack lootBag = this.getBossLootBag();
            ItemEntity item = player.spawnAtLocation(lootBag);
            if (item != null) {
               ESDataAttachments.IMPORTANT_ITEM.setData(item, true);
               item.setTarget(player.getUUID());
               item.setGlowingTag(true);
               item.setExtendedLifetime();
            }
         }

         this.conversationTarget = serverPlayer;
         ESPlatform.INSTANCE.sendToClient(serverPlayer, new OpenGatekeeperGuiPacket(this.getId(), isPlayerPermitted(serverPlayer)));
      }

      return this.isActivated() ? InteractionResult.PASS : InteractionResult.sidedSuccess(this.level().isClientSide);
   }

   public synchronized void handleDialogueClose(int operation) {
      if (this.conversationTarget != null) {
         if (operation == 1) {
            this.standardFight = true;
            this.setFightTargetName(this.conversationTarget.getName().getString());
            this.setTarget(this.conversationTarget);
            this.setActivated(true);
         }

         if (operation == 2 && isPlayerPermitted(this.conversationTarget) && this.offers != null && !this.offers.isEmpty()) {
            this.setTradingPlayer(this.conversationTarget);
            this.openTradingScreen(this.conversationTarget, this.getDisplayName(), 1);
         }

         if (operation == 3) {
            if (this.level() instanceof ServerLevel serverLevel) {
               RandomSource random = serverLevel.getRandom();

               for (int i = 0; i <= 25; i++) {
                  ESPlatform.INSTANCE
                     .sendToAllClients(
                        serverLevel,
                        new ParticlePacket(
                           ExplosionShockParticleOptions.DEATH,
                           this.getX() + (random.nextFloat() - 0.5F) * this.getBbWidth() * 3.0F,
                           this.getY(),
                           this.getZ() + (random.nextFloat() - 0.5F) * this.getBbWidth() * 3.0F,
                           0.0,
                           1.0,
                           0.0
                        )
                     );
               }
            }

            this.discard();
         }

         this.conversationTarget = null;
      }
   }

   private static void permitPlayer(ServerPlayer player) {
      ((PlayerTrigger)ESCriteriaTriggers.CHALLENGED_GATEKEEPER.get()).trigger(player);
      ESBookUtil.unlock(player, EternalStarlight.id("permitted_by_gatekeeper"));
   }

   public static boolean isPlayerPermitted(ServerPlayer player) {
      if (player.getServer() != null) {
         AdvancementHolder challenge = player.getServer().getAdvancements().get(EternalStarlight.id("challenge_gatekeeper"));
         boolean challenged = challenge != null && player.getAdvancements().getOrStartProgress(challenge).isDone();
         if (challenged) {
            return true;
         }
      }

      return ESBookUtil.getUnlockedParts(player).contains(EternalStarlight.id("permitted_by_gatekeeper"));
   }

   @Override
   public void die(DamageSource source) {
      if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || !this.standardFight) {
         super.die(source);
      } else if (!this.level().isClientSide) {
         this.getFightTarget().ifPresent(p -> {
            if (p instanceof ServerPlayer serverPlayer) {
               permitPlayer(serverPlayer);
               ESDataAttachments.GATEKEEPER_CHALLENGE_COUNT.setData(serverPlayer, ESDataAttachments.GATEKEEPER_CHALLENGE_COUNT.getData(serverPlayer) + 1);
            }
         });
         this.trySpawnLoot();
         this.abortFight();
      }
   }

   protected void tickDeath() {
      super.tickDeath();
      if (this.deathTime == 0) {
         this.stopAllAnimStates();
         this.setBehaviorState(0);
      }
   }

   @Override
   public boolean hurt(DamageSource source, float amount) {
      if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && source.getEntity() == this) {
         return false;
      }

      if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
         || ESConfig.INSTANCE.mobsConfig.theGatekeeper.canAlwaysHurtWhenFighting() && this.isActivated()
         || source.getEntity() != null && this.getTarget() != null && this.getBehaviorState() != 0) {
         if (this.getBehaviorState() == 13 && source.getEntity() != null) {
            this.healInterrupted = true;
            this.healInterruptedIndirect = !source.isDirect();
         }

         return super.hurt(source, amount);
      } else {
         if (this.isActivated()) {
            if (this.getBehaviorState() == 0 && source.getEntity() != null) {
               this.level().broadcastEntityEvent(this, (byte)101);
            }

            if (this.level() instanceof ServerLevel serverLevel && amount >= 7.0F) {
               serverLevel.sendParticles(
                  (SimpleParticleType)ESParticles.PARRY.get(),
                  this.getX(),
                  this.getY() + this.getBbHeight() / 2.0F,
                  this.getZ(),
                  8,
                  0.0,
                  0.0,
                  0.0,
                  0.2 + this.getRandom().nextFloat() * 0.2
               );
               this.playSound(ESSoundEvents.THE_GATEKEEPER_PARRY.get(), this.getSoundVolume(), this.getVoicePitch());
            }
         }

         return false;
      }
   }

   public boolean canBeSeenAsEnemy() {
      return this.isActivated() && super.canBeSeenAsEnemy();
   }

   private void tryTeleportBack() {
      if (this.getInitialPos().dimension() == this.level().dimension()) {
         Vec3 initialPos = this.getInitialPos().pos();
         BlockPos blockPos = BlockPos.containing(initialPos);
         if (initialPos.distanceTo(this.position()) > 15.0) {
            if (this.level().noBlockCollision(this, this.getBoundingBox().move(blockPos.getBottomCenter().subtract(this.position())))) {
               this.setPos(blockPos.getBottomCenter());
               return;
            }

            for (int i = 0; i < 64; i++) {
               BlockPos startPos = blockPos.offset(this.getRandom().nextInt(31) - 15, this.getRandom().nextInt(31) - 15, this.getRandom().nextInt(31) - 15);
               boolean successful = false;
               double finalY = startPos.getY();
               if (this.level().getBlockState(startPos).isAir()) {
                  BlockHitResult result = this.level()
                     .clip(new ClipContext(startPos.getCenter(), startPos.getCenter().add(0.0, -15.0, 0.0), Block.COLLIDER, Fluid.NONE, this));
                  if (result.getType() != Type.MISS) {
                     finalY = result.getLocation().y;
                     successful = true;
                  }
               } else {
                  for (int currentDiff = 0;
                     !this.level().noBlockCollision(this, this.getBoundingBox().move(startPos.getBottomCenter().subtract(this.position()))) && currentDiff < 15;
                     currentDiff++
                  ) {
                     startPos = startPos.above();
                  }

                  if (this.level().noBlockCollision(this, this.getBoundingBox().move(startPos.getBottomCenter().subtract(this.position())))) {
                     BlockHitResult result = this.level()
                        .clip(new ClipContext(startPos.getCenter(), startPos.getCenter().add(0.0, -15.0, 0.0), Block.COLLIDER, Fluid.NONE, this));
                     if (result.getType() != Type.MISS) {
                        finalY = result.getLocation().y;
                        successful = true;
                     }
                  }
               }

               Vec3 target = new Vec3(startPos.getX() + 0.5, finalY, startPos.getZ() + 0.5);
               if (successful && initialPos.distanceTo(target) <= 15.0 && ESPlatform.INSTANCE.postTeleportEvent(this, target)) {
                  this.setPos(target);
                  break;
               }
            }
         }
      }
   }

   public boolean isGatekeeperStuck() {
      if (this.recentPositions.size() < 32) {
         return false;
      }

      float averageX = 0.0F;
      float averageY = 0.0F;
      float averageZ = 0.0F;

      for (BlockPos pos : this.recentPositions) {
         averageX += pos.getX();
         averageY += pos.getY();
         averageZ += pos.getZ();
      }

      averageX /= this.recentPositions.size();
      averageY /= this.recentPositions.size();
      averageZ /= this.recentPositions.size();
      Vec3 averagePos = new Vec3(averageX, averageY, averageZ);
      return this.recentPositions.stream().allMatch(posx -> posx.getBottomCenter().distanceTo(averagePos) < 5.0);
   }

   public void aiStep() {
      super.aiStep();
      this.bossEvent.update();
      if (this.tickCount % 5 == 0 && !this.isActivated()) {
         this.bossEvent.allConvertToUnseen();
      }

      this.refreshDimensions();
      if (!this.level().isClientSide) {
         if (this.standardFight) {
            if (this.tickCount > 5 && this.isAlive() && this.isActivated() && (this.getTarget() == null || !this.getTarget().isAlive())) {
               this.noTargetTime++;
               if (this.getFightTarget().isEmpty() || this.noTargetTime > 200) {
                  this.abortFight();
                  this.noTargetTime = 0;
               }
            }
         } else if (!this.isActivated()) {
            this.setActivated(true);
         }

         if (this.restockCooldown > 0) {
            this.restockCooldown--;
         } else {
            this.restockCooldown = 12000;
            this.restockAll();
         }

         if (this.getTradingPlayer() != null && this.getTradingPlayer().distanceTo(this) > 16.0F) {
            this.setTradingPlayer(null);
         }

         this.setCustomName(Component.literal(this.gatekeeperName));
         this.setCustomNameVisible(true);
         if (this.isLeftHanded()) {
            this.setLeftHanded(false);
         }

         if (this.isActivated() && !this.isNoAi() && this.isAlive()) {
            this.behaviorManager.tick();
            if (this.tickCount % 10 == 0) {
               this.recentPositions.add(this.blockPosition());
            }

            while (this.recentPositions.size() > 32) {
               this.recentPositions.removeFirst();
            }
         } else {
            this.setBehaviorState(0);
            this.setBehaviorTicks(0);
            this.recentPositions.clear();
         }

         if (!this.isActivated() || this.getBehaviorState() != 0) {
            this.getNavigation().stop();
            this.getMoveControl().setWantedPosition(this.getX(), this.getY(), this.getZ(), 0.0);
         }

         if (!this.isActivated()) {
            boolean blockSupport = false;

            for (Direction direction : Direction.values()) {
               if (direction.getAxis() != Axis.Y) {
                  BlockHitResult toSide = this.level()
                     .clip(
                        new ClipContext(
                           this.position().add(0.0, this.getBbHeight(), 0.0),
                           this.position().add(0.0, this.getBbHeight(), 0.0).add(new Vec3(direction.step()).scale(0.75)),
                           Block.COLLIDER,
                           Fluid.NONE,
                           this
                        )
                     );
                  if (toSide.getType() != Type.MISS) {
                     this.setStandDirection(direction.getOpposite());
                     blockSupport = true;
                  }
               }
            }

            if (!blockSupport) {
               this.setStandDirection(Direction.UP);
            }
         } else {
            this.setStandDirection(Direction.UP);
         }
      } else if (this.isActivated()) {
         this.sitAnimationState.stop();
         this.standAnimationState.stop();
      } else if (this.getStandDirection().getAxis() != Axis.Y) {
         this.sitAnimationState.stop();
         this.standAnimationState.startIfStopped(this.tickCount);
      } else {
         this.sitAnimationState.startIfStopped(this.tickCount);
         this.standAnimationState.stop();
      }
   }

   public ItemStack getGatekeeperHammer() {
      LivingEntity target = this.getTarget();
      if (target instanceof ServerPlayer serverPlayer && isPlayerPermitted(serverPlayer) && ESDataAttachments.GATEKEEPER_CHALLENGE_COUNT.getData(target) > 0) {
         ItemStack mace = Items.MACE.getDefaultInstance();
         ItemAttributeModifiers morningStarAttributes = (ItemAttributeModifiers)ESItems.GLISTERING_MORNING_STAR
            .get()
            .getDefaultInstance()
            .get(DataComponents.ATTRIBUTE_MODIFIERS);
         mace.set(DataComponents.ATTRIBUTE_MODIFIERS, morningStarAttributes);
         return mace;
      } else {
         return ESItems.GLISTERING_MORNING_STAR.get().getDefaultInstance();
      }
   }

   public void abortFight() {
      this.setHealth(this.getMaxHealth());
      this.setFightTargetName("");
      this.setTarget(null);
      this.setActivated(false);
      this.tryTeleportBack();
      this.fightParticipants.clear();
      this.healCount = 0;
      this.healInterruptedCount = 0;
      this.setItemInHand(InteractionHand.MAIN_HAND, ESItems.GLISTERING_SWORD.get().getDefaultInstance());
      this.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
   }

   protected EntityDimensions getDefaultDimensions(Pose pose) {
      EntityDimensions dimensions = super.getDefaultDimensions(pose);
      return !this.isActivated() && this.getStandDirection().getAxis() == Axis.Y ? dimensions.scale(1.0F, 0.7F) : dimensions;
   }

   @Override
   protected void grantSpecialLoot(ServerPlayer player) {
      permitPlayer(player);
      ESCrestUtil.upgradeCrest(player, ESCrests.GUIDANCE_OF_STARS);
   }

   @Override
   protected void modifyBossLootChest(LootChestBlockEntity blockEntity) {
      blockEntity.setColor(4344942);
      blockEntity.setRareFlashColor(6648722);
   }

   @Override
   public SoundEvent getBossMusic() {
      return ESSoundEvents.MUSIC_BOSS_GATEKEEPER.get();
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return SoundEvents.PLAYER_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.PLAYER_DEATH;
   }

   public void setTradingPlayer(@Nullable Player player) {
      this.customer = player;
   }

   @Nullable
   public Player getTradingPlayer() {
      return this.customer;
   }

   public MerchantOffers getOffers() {
      if (this.offers == null) {
         this.offers = new MerchantOffers();
         this.addTrades();
      }

      return this.offers;
   }

   protected void addTrades() {
      MerchantOffers merchantoffers = this.getOffers();
      this.addTrades(merchantoffers, GatekeeperTrades.TRADES, 50);
      this.addTrades(
         merchantoffers,
         new ItemListing[]{
            ESEntityUtil.simpleTrade(
               new ItemStack((ItemLike)ESItems.STARLIGHT_SILVER_COIN.get(), 10),
               (ItemStack)Util.make(
                  () -> {
                     ItemStack stack = ESItems.STARLIT_PAINTING.get().getDefaultInstance();
                     stack.set(
                        DataComponents.ENTITY_DATA,
                        ((CustomData)CustomData.EMPTY
                              .update(
                                 this.level().registryAccess().createSerializationContext(NbtOps.INSTANCE),
                                 Painting.VARIANT_MAP_CODEC,
                                 this.level().registryAccess().registryOrThrow(Registries.PAINTING_VARIANT).getHolderOrThrow(ESPaintingVariants.GUARDIAN)
                              )
                              .getOrThrow())
                           .update(compoundTag -> compoundTag.putString("id", "eternal_starlight:painting"))
                     );
                     return stack;
                  }
               ),
               10
            )
         },
         50
      );
   }

   protected void addTrades(MerchantOffers original, ItemListing[] newTrades, int maxNumbers) {
      Set<Integer> set = new HashSet<>();
      if (newTrades.length > maxNumbers) {
         while (set.size() < maxNumbers) {
            set.add(this.random.nextInt(newTrades.length));
         }
      } else {
         for (int i = 0; i < newTrades.length; i++) {
            set.add(i);
         }
      }

      for (Integer integer : set) {
         ItemListing trade = newTrades[integer];
         MerchantOffer merchantoffer = trade.getOffer(this, this.random);
         if (merchantoffer != null) {
            original.add(merchantoffer);
         }
      }
   }

   protected void restockAll() {
      for (MerchantOffer merchantoffer : this.getOffers()) {
         merchantoffer.resetUses();
      }
   }

   public void overrideOffers(@Nullable MerchantOffers offers) {
   }

   public void notifyTrade(MerchantOffer offer) {
      offer.increaseUses();
      this.ambientSoundTime = -this.getAmbientSoundInterval();
      if (offer.shouldRewardExp()) {
         int i = 3 + this.random.nextInt(4);
         this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + 0.5, this.getZ(), i));
      }
   }

   public void notifyTradeUpdated(ItemStack stack) {
   }

   public int getVillagerXp() {
      return 0;
   }

   public void overrideXp(int xp) {
   }

   public boolean showProgressBar() {
      return false;
   }

   public SoundEvent getNotifyTradeSound() {
      return SoundEvents.PLAYER_LEVELUP;
   }

   public boolean isClientSide() {
      return this.level().isClientSide;
   }
}
