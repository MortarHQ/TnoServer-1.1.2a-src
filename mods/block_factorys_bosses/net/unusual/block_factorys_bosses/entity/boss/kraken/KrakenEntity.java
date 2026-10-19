package net.unusual.block_factorys_bosses.entity.boss.kraken;

import com.google.common.primitives.Doubles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.block.entity.KrakenSpawnerBlockEntity;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockEmitterParticleOptions;
import net.unusual.block_factorys_bosses.configuration.ServerConfiguration;
import net.unusual.block_factorys_bosses.entity.boss.AbstractStateBossEntity;
import net.unusual.block_factorys_bosses.entity.control.FollowHeadBodyRotationControl;
import net.unusual.block_factorys_bosses.entity.control.NoLookControl;
import net.unusual.block_factorys_bosses.entity.control.NoMoveControl;
import net.unusual.block_factorys_bosses.entity.decoration.CannonEntity;
import net.unusual.block_factorys_bosses.entity.projectile.CannonballEntity;
import net.unusual.block_factorys_bosses.geckolib.util.SoundKeyframePlayer;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseEntityDataSerializers;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.state.ActiveState;
import net.unusual.block_factorys_bosses.state.State;
import net.unusual.block_factorys_bosses.state.StateController;
import net.unusual.block_factorys_bosses.state.StateGoal;
import net.unusual.block_factorys_bosses.state.StateRef;
import net.unusual.block_factorys_bosses.state.StateRegistry;
import net.unusual.block_factorys_bosses.structures.KrakenShipStructure;
import net.unusual.block_factorys_bosses.util.BossHandling;
import net.unusual.block_factorys_bosses.util.SpatialUtil;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KrakenEntity extends AbstractStateBossEntity {
   public static final MobEffectInstance INVULNERABILITY_EFFECT = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 255, false, false, false);
   public static Map<ServerPlayer, Long> RUNAWAY_MAP = new HashMap<>();
   public static final long RUNAWAY_COOLDOWN = 200L;
   public static final int RAIN_DURATION_INTERVAL = 40;
   public static final float TENTACLE_HEIGHT_OFFSET = -5.0F;
   public static final Vec3 SECOND_PHASE_POSITION = new Vec3(26.0, -5.0, -8.0);
   private static final int KNOCKDOWN_DURATION = 304;
   private static final int KNOCKDOWN_DURATION_START = 76;
   private static final int KNOCKDOWN_DURATION_END = 228;
   public static final Vec3 KNOCKDOWN_POSITION = SECOND_PHASE_POSITION.add(-12.0, 2.0, -7.375);
   protected static final int MAX_CONCURRENT_ATTACKS = 2;
   public static final float CANNONBALL_DAMAGE_MULTIPLIER = 5.0F;
   public static final EntityDimensions ENTITY_DIMENSIONS_ZERO = EntityDimensions.fixed(0.0F, 0.0F);
   private static final String TAG_SHIP_POSITION = "ship_position";
   private static final String TAG_SHIP_FACING = "ship_facing";
   private static final String TAG_OWNED_TENTACLES = "owned_tentacles";
   private static final String TAG_DEAD_TENTACLES = "dead_tentacles";
   private static final String TAG_TENTACLE_RESPAWN_TIMERS = "tentacle_respawn_timers";
   private static final String TAG_BOSS_PHASE_INITIALIZED = "boss_phase_initialized";
   private static final String TAG_IS_HIDDEN = "is_hidden";
   private static final String TAG_DAMAGE_TOWARDS_KNOCKDOWN = "damage_towards_knockdown";
   private static final String TAG_CINEMATIC_ENTITY = "cinematic_entity";
   public static final EntityDataAccessor<Optional<BlockPos>> DATA_SHIP_POSITION = SynchedEntityData.defineId(
      KrakenEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS
   );
   public static final EntityDataAccessor<Optional<Direction>> DATA_SHIP_FACING = SynchedEntityData.defineId(
      KrakenEntity.class, (EntityDataSerializer)BossesRiseEntityDataSerializers.OPTIONAL_DIRECTION.get()
   );
   public static final EntityDataAccessor<Boolean> DATA_IS_HIDDEN = SynchedEntityData.defineId(KrakenEntity.class, EntityDataSerializers.BOOLEAN);
   private final List<UUID> ownedTentacles = new ArrayList<>(10);
   private int deadTentacles = 0;
   protected final SortedSet<KrakenEntity.TentacleRespawnTimer> tentacleRespawnTimers = new TreeSet<>(
      Comparator.comparingLong(KrakenEntity.TentacleRespawnTimer::respawnAtGameTime)
   );
   @Nullable
   private KrakenCinematicEntity cinematicEntity;
   @Nullable
   private UUID cinematicEntityUUID;
   private float damageTowardsKnockdown = 0.0F;
   public static final int FIRST_PHASE = 0;
   public static final int SECOND_PHASE_CINEMATIC_PHASE = 1;
   public static final int SECOND_PHASE = 2;
   public static final int DYING_PHASE = 3;
   protected static final KrakenEntity.TentacleDefinition[] TENTACLES_FIRST_PHASE = new KrakenEntity.TentacleDefinition[]{
      new KrakenEntity.TentacleDefinition("1_port_1", new Vec3(13.5, -5.0, 12.0), 130.0F, KrakenTentacleEntity.TentacleType.MELEE, "Intro_cin_tentacle_1", 6000),
      new KrakenEntity.TentacleDefinition("1_port_2", new Vec3(15.0, -5.0, 2.0), 95.0F, KrakenTentacleEntity.TentacleType.CANNON, "Intro_cin_tentacle_5", 4800),
      new KrakenEntity.TentacleDefinition("1_port_3", new Vec3(16.0, -5.0, -7.0), 75.0F, KrakenTentacleEntity.TentacleType.MELEE, "Intro_cin_tentacle_2", 8400),
      new KrakenEntity.TentacleDefinition("1_port_4", new Vec3(15.5, -5.0, -20.0), 55.0F, KrakenTentacleEntity.TentacleType.MELEE, "Intro_cin_tentacle_3", 7200),
      new KrakenEntity.TentacleDefinition(
         "1_starboard_1", new Vec3(-15.5, -5.0, 7.0), -100.0F, KrakenTentacleEntity.TentacleType.MELEE, "Intro_cin_tentacle_4", 8400
      ),
      new KrakenEntity.TentacleDefinition(
         "1_starboard_2", new Vec3(-16.0, -5.0, -7.0), -90.0F, KrakenTentacleEntity.TentacleType.MELEE, "Intro_cin_tentacle_5", 7200
      ),
      new KrakenEntity.TentacleDefinition(
         "1_starboard_3", new Vec3(-16.0, -5.0, -15.0), -90.0F, KrakenTentacleEntity.TentacleType.CANNON, "Intro_cin_tentacle_3", 4800
      ),
      new KrakenEntity.TentacleDefinition(
         "1_starboard_4", new Vec3(-15.5, -2.0, -26.0), -80.0F, KrakenTentacleEntity.TentacleType.MELEE, "Intro_cin_tentacle_6", 6000
      )
   };
   protected static final KrakenEntity.TentacleDefinition[] TENTACLES_SECOND_PHASE = new KrakenEntity.TentacleDefinition[]{
      new KrakenEntity.TentacleDefinition("2_port_1", new Vec3(16.0, -5.0, 10.0), 150.0F, KrakenTentacleEntity.TentacleType.MELEE, "Intro_cin_tentacle_1", 2400),
      new KrakenEntity.TentacleDefinition("2_port_2", new Vec3(18.0, -5.0, 0.0), 105.0F, KrakenTentacleEntity.TentacleType.MELEE, "Intro_cin_tentacle_2", 2400),
      new KrakenEntity.TentacleDefinition(
         "2_port_3", new Vec3(14.0, -5.0, 16.0), 125.0F, KrakenTentacleEntity.TentacleType.CANNON, "Intro_cin_tentacle_7", 2000
      ),
      new KrakenEntity.TentacleDefinition("2_port_4", new Vec3(15.0, -5.0, -16.0), 35.0F, KrakenTentacleEntity.TentacleType.MELEE, "Intro_cin_tentacle_3", 2400),
      new KrakenEntity.TentacleDefinition("2_port_5", new Vec3(16.0, -2.0, -26.0), 45.0F, KrakenTentacleEntity.TentacleType.CRATE, "Intro_cin_tentacle_4", 2000),
      new KrakenEntity.TentacleDefinition(
         "2_starboard_1", new Vec3(-22.0, -5.0, 10.0), -100.0F, KrakenTentacleEntity.TentacleType.CRATE, "Intro_cin_tentacle_5", 2000
      ),
      new KrakenEntity.TentacleDefinition(
         "2_starboard_2", new Vec3(-14.0, -5.0, -3.0), -105.0F, KrakenTentacleEntity.TentacleType.MELEE, "Intro_cin_tentacle_6", 2400
      ),
      new KrakenEntity.TentacleDefinition(
         "2_starboard_3", new Vec3(-18.0, -5.0, -13.0), -75.0F, KrakenTentacleEntity.TentacleType.MELEE, "Intro_cin_tentacle_7", 2400
      ),
      new KrakenEntity.TentacleDefinition(
         "2_starboard_4", new Vec3(-16.0, -5.0, -19.0), -70.0F, KrakenTentacleEntity.TentacleType.CANNON, "Intro_cin_tentacle_3", 2000
      ),
      new KrakenEntity.TentacleDefinition(
         "2_starboard_5", new Vec3(-16.0, -2.0, -26.0), -80.0F, KrakenTentacleEntity.TentacleType.MELEE, "Intro_cin_tentacle_8", 2400
      )
   };
   private boolean bossPhaseInitialized = false;
   private boolean shipCacheInitialized = false;
   private int ticksSinceLastParticleSpawn = 0;
   private static final BedrockEmitterParticleOptions FOG_PARTICLE = new BedrockEmitterParticleOptions(
      (ParticleType<BedrockEmitterParticleOptions>)BossesRiseParticleTypes.FOG_1.get(), null, false
   );
   private static final String MAIN_CONTROLLER = "main_controller";
   public static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("second_phase_idle");
   public static final RawAnimation DYING_FROM_IDLE_ANIM = RawAnimation.begin().thenPlayAndHold("death_from_idle");
   public static final RawAnimation DYING_FROM_KNOCKDOWN_ANIM = RawAnimation.begin().thenPlayAndHold("death_from_stun");
   public static final RawAnimation KNOCKDOWN_ANIM = RawAnimation.begin().then("stun_fall", LoopType.PLAY_ONCE).then("stun_wake", LoopType.PLAY_ONCE);
   private static final StateRef<KrakenEntity> INTRO_CINEMATIC_STATE = State.named("intro_cinematic");
   private static final StateRef<KrakenEntity> SECOND_PHASE_CINEMATIC_STATE = State.named("second_phase_cinematic");
   private static final StateRef<KrakenEntity> DYING_FROM_IDLE_STATE = State.named("dying_from_idle");
   private static final StateRef<KrakenEntity> DYING_FROM_KNOCKDOWN_STATE = State.named("dying_from_knockdown");
   public static final StateRef<KrakenEntity> KNOCKDOWN_STATE = State.named("knockdown");
   private static final StateRegistry<KrakenEntity> STATE_REGISTRY = new StateRegistry();
   private final StateController<KrakenEntity> stateController = new StateController(this, STATE_REGISTRY, state -> null);

   public KrakenEntity(EntityType<? extends KrakenEntity> entityType, Level level) {
      super(entityType, level);
      this.setNoGravity(true);
      this.moveControl = new NoMoveControl(this);
      this.lookControl = new NoLookControl(this);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(this, "main_controller", state -> state.setAndContinue(IDLE_ANIM))
            .setSoundKeyframeHandler(new SoundKeyframePlayer())
            .triggerableAnim(DYING_FROM_IDLE_STATE.getName(), DYING_FROM_IDLE_ANIM)
            .triggerableAnim(DYING_FROM_KNOCKDOWN_STATE.getName(), DYING_FROM_KNOCKDOWN_ANIM)
            .triggerableAnim(KNOCKDOWN_STATE.getName(), KNOCKDOWN_ANIM)
      );
   }

   public void triggerAnim(String animName) {
      this.triggerAnim("main_controller", animName);
   }

   public void stopTriggeredAnim(@Nullable String animName) {
      this.stopTriggeredAnim("main_controller", animName);
   }

   protected BodyRotationControl createBodyControl() {
      return new FollowHeadBodyRotationControl(this);
   }

   @Override
   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_SHIP_POSITION, Optional.empty());
      builder.define(DATA_SHIP_FACING, Optional.empty());
      builder.define(DATA_IS_HIDDEN, false);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
      super.onSyncedDataUpdated(key);
      if (DATA_IS_HIDDEN.equals(key)) {
         boolean isHidden = this.isHidden();
         this.blocksBuilding = !isHidden;
         this.setInvulnerable(isHidden);
         this.refreshDimensions();
      }
   }

   public boolean isInWall() {
      return false;
   }

   public void setHidden(boolean isHidden) {
      this.entityData.set(DATA_IS_HIDDEN, isHidden);
   }

   public boolean isHidden() {
      return (Boolean)this.entityData.get(DATA_IS_HIDDEN);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new StateGoal<>(this));
      this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, false, false));
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level() instanceof ServerLevel serverLevel) {
         if (serverLevel.getGameTime() % 40L == 0L && !this.isDeadOrDying()) {
            serverLevel.setWeatherParameters(0, 60, true, true);
         }

         this.tryInitializeBossPhase();
         this.tryInitializeShipCache();
         int bossPhase = (Integer)this.entityData.get(DATA_BOSS_PHASE);
         switch (bossPhase) {
            case -1:
            case 1:
               if (this.cinematicEntity != null) {
                  return;
               }

               this.retrieveExistingCinematicEntity();
               break;
            case 0:
               this.incrementBattleTime();
               this.respawnTentaclesWithDelay();
               List<KrakenTentacleEntity> loadedTentacles = this.getLoadedTentacles();
               float progress = 0.0F;

               for (KrakenTentacleEntity tentacleEntity : loadedTentacles) {
                  progress += tentacleEntity.getHealth() / tentacleEntity.getMaxHealth();
               }

               this.bossEvent.setProgress(progress / 8.0F);
               if (loadedTentacles.isEmpty() && this.isArenaLoaded()) {
                  if (!this.ownedTentacles.isEmpty()) {
                     Util.logAndPauseIfInIde("Kraken tried ending phase one while " + this.ownedTentacles.size() + " tentacles are still suspected to exist.");
                  }

                  this.setBossPhase(1);
               }
               break;
            case 2:
               this.incrementBattleTime();
               this.respawnTentaclesWithDelay();
               this.bossEvent.setProgress((float)this.getHealthRatio());
               break;
            case 3:
               if (this.getStateController().isStateActive(KNOCKDOWN_STATE)) {
                  ActiveState<KrakenEntity> activeState = this.getStateController().getActiveState();
                  assert activeState != null;
                  int knockdownTimer = activeState.getTimer();
                  if (knockdownTimer >= 76 && knockdownTimer < 228) {
                     this.getStateController().replaceAll(DYING_FROM_KNOCKDOWN_STATE);
                     this.getStateController().tick();
                  }
               } else if (!this.isInDyingState() && !this.isDeadOrDying()) {
                  this.getStateController().replaceAll(DYING_FROM_IDLE_STATE);
                  this.getStateController().tick();
               }
         }
      } else {
         this.displayWaterMistParticles();
      }
   }

   @Override
   public void die(DamageSource source) {
      super.die(source);
      if (this.level() instanceof ServerLevel level) {
         level.setWeatherParameters(0, 24000, false, false);
      }
   }

   public void baseTick() {
      super.baseTick();
   }

   private void tryInitializeShipCache() {
      if (!this.shipCacheInitialized) {
         if (this.level() instanceof ServerLevel level) {
            this.shipCacheInitialized = true;
            KrakenShipStructure.buildShipCache(level, this.getShipPosition());
         }
      }
   }

   private void retrieveExistingCinematicEntity() {
      if (this.cinematicEntity == null) {
         if (this.cinematicEntityUUID != null) {
            if (this.level() instanceof ServerLevel level) {
               if (level.getEntity(this.cinematicEntityUUID) instanceof KrakenCinematicEntity kce) {
                  this.cinematicEntity = kce;
               }
            }
         }
      }
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      if (!this.isShipPositionInvalid() && this.level() instanceof ServerLevel level) {
         Vec3 shipPosition = this.getShipPosition().getBottomCenter();
         level.getPlayers(player -> shouldChasePlayer(player, shipPosition))
            .forEach(
               player -> {
                  long gameTime = player.serverLevel().getGameTime();
                  if (!RUNAWAY_MAP.containsKey(player) || RUNAWAY_MAP.get(player) + 200L <= gameTime) {
                     RUNAWAY_MAP.put(player, gameTime);
                     KrakenTentacleEntity tentacle = new KrakenTentacleEntity(
                        (EntityType<? extends KrakenTentacleEntity>)BossesRiseEntities.KRAKEN_TENTACLE.get(), this.level()
                     );
                     tentacle.setPos(player.position().subtract(0.0, 5.0, 0.0));
                     tentacle.lookAt(this, 360.0F, 360.0F);
                     tentacle.setOwner(this);
                     tentacle.setTentacleType(KrakenTentacleEntity.TentacleType.RUNAWAY);
                     if (this.level().addFreshEntity(tentacle)) {
                        tentacle.triggerAnim(null, "phase_2_throw_crate");
                        tentacle.setTarget(player);
                        tentacle.targetSelector.removeAllGoals(goal -> true);
                     }
                  }
               }
            );
      }
   }

   public static boolean shouldChasePlayer(ServerPlayer player, Vec3 shipPosition) {
      if (player.isSpectator() || player.isCreative()) {
         return false;
      } else if (player.getFirstPassenger() instanceof KrakenTentacleEntity) {
         return false;
      } else if (player.getY() > shipPosition.y()) {
         return false;
      } else if (player.distanceToSqr(shipPosition) > 4096.0) {
         return false;
      } else if (player.getVehicle() != null && player.getRootVehicle().isInWater()) {
         player.stopRiding();
         return true;
      } else {
         return player.isInWater() || player.level().getBlockState(player.getOnPos().below()).is(Blocks.WATER);
      }
   }

   private void tryInitializeBossPhase() {
      if (!this.bossPhaseInitialized) {
         this.bossPhaseInitialized = true;
         switch (this.getBossPhase()) {
            case -1:
               this.getStateController().push(INTRO_CINEMATIC_STATE);
               break;
            case 0:
               this.initializeFirstPhase();
               break;
            case 1:
               this.getStateController().push(SECOND_PHASE_CINEMATIC_STATE);
               break;
            case 2:
               this.initializeSecondPhase();
         }

         BossHandling.updateBossType(this.bossEvent, this.getBossType());
      }
   }

   private void initializeFirstPhase() {
      this.setHidden(true);
      this.bossEvent.setVisible(true);
      this.tentacleRespawnTimers.clear();
      this.spawnTentacles(this.getPhaseTentacleDefinitions());
      this.stopTentacleAnimations();
   }

   private void moveToSecondPhasePosition() {
      this.moveTo(this.shipToWorldSpace(SECOND_PHASE_POSITION), this.shipRotationToWorldSpace(90.0F), 0.0F);
   }

   private void initializeSecondPhase() {
      this.setHidden(false);
      this.bossEvent.setVisible(true);
      this.moveToSecondPhasePosition();
      this.tentacleRespawnTimers.clear();
      this.spawnTentacles(this.getPhaseTentacleDefinitions());
      this.stopTentacleAnimations();
   }

   @Override
   public StateController<KrakenEntity> getStateController() {
      return this.stateController;
   }

   public boolean isDying() {
      return this.getBossPhase() == 3;
   }

   public boolean isInDyingState() {
      StateRef<KrakenEntity> activeStateRef = this.getStateController().getActiveStateRef();
      return activeStateRef == DYING_FROM_IDLE_STATE || activeStateRef == DYING_FROM_KNOCKDOWN_STATE;
   }

   public boolean isKnockedDown() {
      return this.getStateController().isStateActive(KNOCKDOWN_STATE) || this.isInDyingState();
   }

   public boolean allTentaclesBusy() {
      int attackingTentacles = (int)this.getLoadedTentacles().stream().filter(KrakenTentacleEntity::isAttacking).count();
      return attackingTentacles >= 2;
   }

   @Override
   public void setBossPhase(int phase) {
      super.setBossPhase(phase);
      this.bossPhaseInitialized = false;
   }

   public List<KrakenTentacleEntity> getLoadedTentacles() {
      if (this.level() instanceof ServerLevel level) {
         ArrayList var7 = new ArrayList();

         for (UUID ownedTentacleUuid : this.ownedTentacles) {
            if (level.getEntity(ownedTentacleUuid) instanceof KrakenTentacleEntity tentacle) {
               var7.add(tentacle);
            }
         }

         return var7;
      } else {
         return List.of();
      }
   }

   protected KrakenEntity.TentacleDefinition[] getPhaseTentacleDefinitions() {
      return switch (this.getBossPhase()) {
         case 0 -> TENTACLES_FIRST_PHASE;
         case 2 -> TENTACLES_SECOND_PHASE;
         default -> new KrakenEntity.TentacleDefinition[0];
      };
   }

   @Nullable
   protected KrakenEntity.TentacleDefinition getPhaseTentacleDefinition(@Nullable String tentacleId) {
      if (tentacleId == null) {
         return null;
      }

      KrakenEntity.TentacleDefinition[] tentacleDefinitions = this.getPhaseTentacleDefinitions();

      for (KrakenEntity.TentacleDefinition tentacleDefinition : tentacleDefinitions) {
         if (tentacleDefinition.id().equals(tentacleId)) {
            return tentacleDefinition;
         }
      }

      return null;
   }

   private void spawnTentacles(KrakenEntity.TentacleDefinition[] tentacleDefinitions) {
      for (KrakenEntity.TentacleDefinition tentacleDefinition : tentacleDefinitions) {
         this.spawnTentacle(tentacleDefinition);
      }
   }

   private void stopTentacleAnimations() {
      for (KrakenTentacleEntity tentacle : this.getLoadedTentacles()) {
         tentacle.stopTriggeredAnim(null);
      }
   }

   @Nullable
   private KrakenTentacleEntity spawnTentacle(KrakenEntity.TentacleDefinition tentacleDefinition) {
      return this.spawnTentacle(
         tentacleDefinition.id(),
         tentacleDefinition.offset(),
         tentacleDefinition.rotation(),
         tentacleDefinition.type(),
         tentacleDefinition.introAnimationName()
      );
   }

   @Nullable
   private KrakenTentacleEntity spawnTentacle(String id, Vec3 offset, float facing, KrakenTentacleEntity.TentacleType tentacleType, String spawnAnim) {
      if (tentacleType == KrakenTentacleEntity.TentacleType.CANNON) {
         KrakenTentacleEntity tentacle = this.spawnCannonGrabbyTentacle(id, offset, facing);
         if (tentacle != null) {
            return tentacle;
         }

         tentacleType = KrakenTentacleEntity.TentacleType.MELEE;
      }

      return this.spawnTentacleInternal(id, offset, facing, tentacleType, spawnAnim);
   }

   @Nullable
   private KrakenTentacleEntity spawnTentacleInternal(String id, Vec3 offset, float facing, KrakenTentacleEntity.TentacleType tentacleType, String spawnAnim) {
      KrakenTentacleEntity tentacle = new KrakenTentacleEntity(
         (EntityType<? extends KrakenTentacleEntity>)BossesRiseEntities.KRAKEN_TENTACLE.get(), this.level()
      );
      tentacle.setPos(this.shipToWorldSpace(offset));
      tentacle.setTargetRotation(this.shipRotationToWorldSpace(facing), 30.0F);
      tentacle.setOwner(this);
      tentacle.setTentacleType(tentacleType);
      tentacle.setTentacleId(id);
      double maxHealth = this.getAttributeBaseValue(Attributes.MAX_HEALTH) / 12.0;
      tentacle.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
      tentacle.getAttribute(Attributes.ARMOR).setBaseValue(this.getAttributeBaseValue(Attributes.ARMOR));
      tentacle.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE));
      tentacle.setHealth((float)maxHealth);
      if (!this.level().addFreshEntity(tentacle)) {
         return null;
      }

      tentacle.triggerAnim(null, spawnAnim);
      this.ownedTentacles.add(tentacle.getUUID());
      return tentacle;
   }

   @Nullable
   private KrakenTentacleEntity spawnCannonGrabbyTentacle(String id, Vec3 offset, float facing) {
      KrakenTentacleEntity tentacle = this.spawnTentacleInternal(id, offset, facing, KrakenTentacleEntity.TentacleType.CANNON, "intro_cannon");
      if (tentacle == null) {
         return null;
      } else {
         CannonEntity cannon = this.level()
            .getEntitiesOfClass(CannonEntity.class, tentacle.getBoundingBox().inflate(32.0))
            .stream()
            .filter(candidateCanon -> !(candidateCanon.getFirstPassenger() instanceof KrakenTentacleEntity))
            .min((cannonA, cannonB) -> Doubles.compare(cannonA.distanceToSqr(tentacle), cannonB.distanceToSqr(tentacle)))
            .orElse(null);
         if (cannon != null && tentacle.startRiding(cannon)) {
            tentacle.setYRot(cannon.getCoreYRot() + 180.0F);
            return tentacle;
         } else {
            tentacle.discard();
            return null;
         }
      }
   }

   protected void respawnTentaclesWithDelay() {
      long gameTime = this.level().getGameTime();
      Iterator<KrakenEntity.TentacleRespawnTimer> iterator = this.tentacleRespawnTimers.iterator();

      while (iterator.hasNext()) {
         KrakenEntity.TentacleRespawnTimer timer = iterator.next();
         if (timer.respawnAtGameTime() > gameTime) {
            break;
         }

         KrakenEntity.TentacleDefinition tentacleDefinition = this.getPhaseTentacleDefinition(timer.tentacleId());
         if (tentacleDefinition == null || this.spawnTentacle(tentacleDefinition) != null) {
            iterator.remove();
         }
      }
   }

   public void onTentacleDestroyed(KrakenTentacleEntity tentacleEntity) {
      this.ownedTentacles.remove(tentacleEntity.getUUID());
      if (this.level() instanceof ServerLevel serverLevel) {
         this.deadTentacles++;
         if (this.deadTentacles == 4 || this.deadTentacles == 6 || tentacleEntity.getTentacleType() == KrakenTentacleEntity.TentacleType.CANNON) {
            this.spawnPirates(serverLevel);
         }
      }

      KrakenEntity.TentacleDefinition tentacleDefinition = this.getPhaseTentacleDefinition(tentacleEntity.getTentacleId());
      if (tentacleDefinition != null && this.tentacleRespawnTimers.stream().noneMatch(it -> it.tentacleId().equals(tentacleDefinition.id()))) {
         this.tentacleRespawnTimers
            .add(new KrakenEntity.TentacleRespawnTimer(tentacleDefinition.id(), this.level().getGameTime() + tentacleDefinition.respawnsAfter()));
      }
   }

   public void spawnPirates(ServerLevel level) {
      if (!this.isDeadOrDying()) {
         Vec3 center = this.getShipPosition().getBottomCenter();

         for (KrakenSpawnerBlockEntity.PiratePoint<?> point : KrakenSpawnerBlockEntity.PIRATE_POINTS) {
            point.trySummon(level, center, this.getShipFacing());
         }
      }
   }

   private boolean isArenaLoaded() {
      if (!(this.level() instanceof ServerLevel serverLevel)) {
         return false;
      } else {
         BlockPos var9 = this.getShipPosition().immutable();
         MutableBlockPos mutableBlockPosition = this.blockPosition().mutable();
         int chunkRange = 32;

         for (int x = -chunkRange; x <= chunkRange; x += 16) {
            for (int z = -chunkRange; z <= chunkRange; z += 16) {
               mutableBlockPosition.setWithOffset(var9, x, 0, z);
               long chunkPos = ChunkPos.asLong(mutableBlockPosition);
               if (!serverLevel.areEntitiesLoaded(chunkPos)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public boolean isShipPositionInvalid() {
      return ((Optional)this.entityData.get(DATA_SHIP_POSITION)).isEmpty() || ((Optional)this.entityData.get(DATA_SHIP_FACING)).isEmpty();
   }

   public BlockPos getShipPosition() {
      return (BlockPos)((Optional)this.entityData.get(DATA_SHIP_POSITION)).orElseThrow();
   }

   public Direction getShipFacing() {
      return (Direction)((Optional)this.entityData.get(DATA_SHIP_FACING)).orElseThrow();
   }

   public void setShipPosition(BlockPos shipOrigin, Direction shipFacing) {
      this.entityData.set(DATA_SHIP_POSITION, Optional.of(shipOrigin));
      this.entityData.set(DATA_SHIP_FACING, Optional.of(shipFacing));
   }

   public void setShipPositionToEntityPosition() {
      this.setShipPosition(this.blockPosition(), this.getDirection());
   }

   public Rotation getShipRotation() {
      return switch (this.getShipFacing()) {
         case NORTH -> Rotation.CLOCKWISE_180;
         case EAST -> Rotation.COUNTERCLOCKWISE_90;
         case SOUTH -> Rotation.NONE;
         case WEST -> Rotation.CLOCKWISE_90;
         default -> throw new IllegalStateException();
      };
   }

   public BlockPos shipToWorldSpace(BlockPos offset) {
      BlockPos shipPosition = this.getShipPosition();
      return offset.rotate(this.getShipRotation()).offset(shipPosition.getX(), shipPosition.getY(), shipPosition.getZ());
   }

   public Vec3 shipToWorldSpace(Vec3 offset) {
      Vec3 rotatedOffset = this.shipDirectionToWorldSpace(offset);
      return rotatedOffset.add(this.getShipPosition().getBottomCenter());
   }

   public Vec3 shipDirectionToWorldSpace(Vec3 direction) {
      return switch (this.getShipRotation()) {
         case NONE -> direction;
         case CLOCKWISE_90 -> new Vec3(-direction.z(), direction.y(), direction.x());
         case CLOCKWISE_180 -> new Vec3(-direction.x(), direction.y(), -direction.z());
         case COUNTERCLOCKWISE_90 -> new Vec3(direction.z(), direction.y(), -direction.x());
         default -> throw new MatchException(null, null);
      };
   }

   public float shipRotationToWorldSpace(float rotation) {
      return SpatialUtil.rotateDegrees(rotation, this.getShipRotation());
   }

   public Vec3 worldToShipSpace(Vec3 pos) {
      Vec3 offset = pos.subtract(this.getShipPosition().getBottomCenter());

      return switch (this.getShipRotation()) {
         case NONE -> offset;
         case CLOCKWISE_90 -> new Vec3(offset.z(), offset.y(), -offset.x());
         case CLOCKWISE_180 -> new Vec3(-offset.x(), offset.y(), -offset.z());
         case COUNTERCLOCKWISE_90 -> new Vec3(-offset.z(), offset.y(), offset.x());
         default -> throw new MatchException(null, null);
      };
   }

   public boolean isInBoatAABB(Vec3 position) {
      if (this.isShipPositionInvalid()) {
         return false;
      }

      Vec3 localPos = this.worldToShipSpace(position);
      return Math.abs(localPos.x()) <= 9.5 && Math.abs(localPos.z()) <= 25.0;
   }

   @OnlyIn(Dist.CLIENT)
   private void displayWaterMistParticles() {
      if (this.ticksSinceLastParticleSpawn < 60) {
         this.ticksSinceLastParticleSpawn++;
      } else {
         this.ticksSinceLastParticleSpawn = 0;
         Vec3 shipPosition = this.getShipPosition().getBottomCenter().add(-0.5, -5.0, -0.5);

         for (double x = shipPosition.x() - 40.0; x <= shipPosition.x() + 40.0; x += 2.5) {
            for (double z = shipPosition.z() - 40.0; z <= shipPosition.z() + 40.0; z += 2.5) {
               Vec3 pos = new Vec3(x + this.random.nextFloat(), shipPosition.y() + this.random.nextFloat(), z + this.random.nextFloat());
               if (!this.isInBoatAABB(pos)) {
                  this.level().addParticle(FOG_PARTICLE, pos.x(), pos.y(), pos.z(), 0.0, 0.0, 0.0);
               }
            }
         }
      }
   }

   @Override
   protected ServerBossEvent createBossEvent() {
      ServerBossEvent bossEvent = new ServerBossEvent(this.getDisplayName(), BossBarColor.RED, BossBarOverlay.NOTCHED_10);
      bossEvent.setVisible(false);
      return bossEvent;
   }

   @Override
   protected int getBossType() {
      return this.getBossPhase() < 1 ? 8 : 9;
   }

   @Override
   protected ResourceLocation getNoHitAdvancement() {
      return BossesRise.prefix("no_hit_kraken");
   }

   @Override
   protected ResourceLocation getKillAdvancement() {
      return BossesRise.prefix("kill_kraken");
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      ((Optional)this.entityData.get(DATA_SHIP_POSITION)).ifPresent(pos -> compound.put("ship_position", NbtUtils.writeBlockPos(pos)));
      ((Optional)this.entityData.get(DATA_SHIP_FACING)).ifPresent(facing -> compound.putString("ship_facing", facing.getSerializedName()));
      ListTag ownedTentaclesTag = new ListTag(this.ownedTentacles.size());

      for (UUID ownedTentacleUuid : this.ownedTentacles) {
         ownedTentaclesTag.addLast(NbtUtils.createUUID(ownedTentacleUuid));
      }

      compound.putInt("dead_tentacles", this.deadTentacles);
      compound.put("owned_tentacles", ownedTentaclesTag);
      compound.put(
         "tentacle_respawn_timers",
         (Tag)KrakenEntity.TentacleRespawnTimer.LIST_CODEC.encodeStart(NbtOps.INSTANCE, this.tentacleRespawnTimers.stream().toList()).getOrThrow()
      );
      compound.putBoolean("boss_phase_initialized", this.bossPhaseInitialized);
      compound.putBoolean("is_hidden", this.isHidden());
      compound.putFloat("damage_towards_knockdown", this.damageTowardsKnockdown);
      if (this.cinematicEntity != null && !this.cinematicEntity.isRemoved()) {
         compound.putUUID("cinematic_entity", this.cinematicEntity.getUUID());
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.entityData.set(DATA_SHIP_POSITION, NbtUtils.readBlockPos(compound, "ship_position"));
      this.entityData.set(DATA_SHIP_FACING, Optional.ofNullable(compound.contains("ship_facing") ? Direction.byName(compound.getString("ship_facing")) : null));
      this.ownedTentacles.clear();

      for (Tag ownedTentacleUuidTag : compound.getList("owned_tentacles", 11)) {
         this.ownedTentacles.add(NbtUtils.loadUUID(ownedTentacleUuidTag));
      }

      this.deadTentacles = compound.getInt("dead_tentacles");
      KrakenEntity.TentacleRespawnTimer.LIST_CODEC.decode(NbtOps.INSTANCE, compound.get("tentacle_respawn_timers")).ifSuccess(it -> {
         this.tentacleRespawnTimers.clear();
         this.tentacleRespawnTimers.addAll((Collection<? extends KrakenEntity.TentacleRespawnTimer>)it.getFirst());
      });
      this.bossPhaseInitialized = compound.getBoolean("boss_phase_initialized");
      this.setHidden(compound.contains("is_hidden") ? compound.getBoolean("is_hidden") : true);
      this.damageTowardsKnockdown = compound.getFloat("damage_towards_knockdown");
      if (compound.hasUUID("cinematic_entity")) {
         this.cinematicEntityUUID = compound.getUUID("cinematic_entity");
      }
   }

   public float rotate(Rotation transformRotation) {
      if (!this.isShipPositionInvalid()) {
         this.entityData.set(DATA_SHIP_FACING, Optional.of(transformRotation.rotate(this.getShipFacing())));
      }

      return super.rotate(transformRotation);
   }

   public float mirror(Mirror transformMirror) {
      if (!this.isShipPositionInvalid()) {
         this.entityData.set(DATA_SHIP_FACING, Optional.of(transformMirror.mirror(this.getShipFacing())));
      }

      return super.mirror(transformMirror);
   }

   public void onAddedToLevel() {
      super.onAddedToLevel();
      if (this.isShipPositionInvalid() && !this.level().isClientSide()) {
         this.setShipPositionToEntityPosition();
      }
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData groupData) {
      SpawnGroupData data = super.finalizeSpawn(world, difficulty, reason, groupData);
      this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((Double)ServerConfiguration.KRAKEN_HEALTH.get());
      this.getAttribute(Attributes.ARMOR).setBaseValue((Double)ServerConfiguration.KRAKEN_ARMOR.get());
      this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((Double)ServerConfiguration.KRAKEN_ATK.get());
      this.setHealth(((Double)ServerConfiguration.KRAKEN_HEALTH.get()).floatValue());
      return data;
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      if (CannonballEntity.hasCausedDamage(damageSource)) {
         amount *= 5.0F;
      } else if (damageSource.is(DamageTypeTags.IS_PROJECTILE)) {
         amount *= 0.25F;
      }

      float previousHealth = this.getHealth();
      boolean gotDamaged = super.hurt(damageSource, amount);
      if (gotDamaged && !this.isKnockedDown()) {
         this.damageTowardsKnockdown = this.damageTowardsKnockdown + Math.max(previousHealth - this.getHealth(), 0.0F);
         if (this.damageTowardsKnockdown >= 100.0F && !this.isDying()) {
            this.damageTowardsKnockdown = 0.0F;
            this.getStateController().push(KNOCKDOWN_STATE);
         }
      }

      return gotDamaged;
   }

   @Override
   public boolean shouldCancelDeath() {
      if (!this.isDying()) {
         this.setBossPhase(3);
         return true;
      } else {
         return this.isInDyingState();
      }
   }

   @Override
   public void remove(RemovalReason reason) {
      if (this.level() instanceof ServerLevel level && !this.isShipPositionInvalid()) {
         KrakenShipStructure.evictCache(level, this.getShipPosition());
      }

      super.remove(reason);
      if (reason == RemovalReason.DISCARDED || reason == RemovalReason.KILLED) {
         this.removeOwnedTentacles();
      }
   }

   private void removeOwnedTentacles() {
      for (KrakenTentacleEntity ownedTentacle : this.getLoadedTentacles()) {
         ownedTentacle.discard();
      }
   }

   public AABB getBoundingBoxForCulling() {
      return this.getBoundingBox().inflate(50.0);
   }

   protected EntityDimensions getDefaultDimensions(Pose pose) {
      return this.isHidden() ? ENTITY_DIMENSIONS_ZERO : super.getDefaultDimensions(pose);
   }

   public boolean isPushable() {
      return false;
   }

   public void setDeltaMovement(Vec3 vec) {
   }

   protected void pushEntities() {
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)BossesRiseSounds.KRAKEN_IDLE.value();
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   private void spawnCinematicKraken() {
      this.cinematicEntity = new KrakenCinematicEntity((EntityType<? extends Monster>)BossesRiseEntities.CINEMATIC_KRAKEN.get(), this.level());
      if (this.getBossPhase() == -1) {
         this.cinematicEntity.setPos(this.shipToWorldSpace(new Vec3(-0.5, -5.0, -1.5)));
         this.cinematicEntity.setInitialRotation(this.shipRotationToWorldSpace(180.0F));
      } else {
         this.cinematicEntity.setPos(this.shipToWorldSpace(new Vec3(31.0, -7.0, -6.0)));
         this.cinematicEntity.setInitialRotation(this.shipRotationToWorldSpace(90.0F));
      }

      this.level().addFreshEntity(this.cinematicEntity);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MOVEMENT_SPEED, 0.0)
         .add(Attributes.MAX_HEALTH, 500.0)
         .add(Attributes.ARMOR, 20.0)
         .add(Attributes.ATTACK_DAMAGE, 10.0)
         .add(Attributes.FOLLOW_RANGE, 96.0);
   }

   static {
      STATE_REGISTRY.register(INTRO_CINEMATIC_STATE, State.builder().onStart(state -> {
         KrakenEntity entity = (KrakenEntity)state.getEntity();
         entity.setHidden(true);
         entity.bossEvent.setVisible(false);
         entity.spawnCinematicKraken();
         if (entity.cinematicEntity == null) {
            entity.getStateController().endActive();
         } else {
            entity.cinematicEntity.playAnimation(entity.getBossPhase());
         }
      }).onTick(state -> {
         if (((KrakenEntity)state.getEntity()).cinematicEntity != null && !((KrakenEntity)state.getEntity()).cinematicEntity.animationFinished()) {
            ((KrakenEntity)state.getEntity()).getNearbyPlayers(64.0).forEach(p -> p.addEffect(INVULNERABILITY_EFFECT));
            return State.Result.CONTINUE;
         } else {
            return State.Result.END;
         }
      }).onEnd(state -> ((KrakenEntity)state.getEntity()).setBossPhase(0)));
      STATE_REGISTRY.register(SECOND_PHASE_CINEMATIC_STATE, State.builder().onStart(state -> {
         KrakenEntity entity = (KrakenEntity)state.getEntity();
         entity.bossEvent.setVisible(false);
         entity.setHidden(true);
         entity.playSound((SoundEvent)BossesRiseSounds.KRAKEN_PHASE_TRANS.value());
         entity.moveToSecondPhasePosition();
         entity.spawnCinematicKraken();
         if (entity.cinematicEntity == null) {
            entity.getStateController().endActive();
         } else {
            entity.cinematicEntity.playAnimation(entity.getBossPhase());
         }
      }).onTick(state -> {
         if (((KrakenEntity)state.getEntity()).cinematicEntity != null && !((KrakenEntity)state.getEntity()).cinematicEntity.animationFinished()) {
            ((KrakenEntity)state.getEntity()).getNearbyPlayers(64.0).forEach(p -> p.addEffect(INVULNERABILITY_EFFECT));
            return State.Result.CONTINUE;
         } else {
            return State.Result.END;
         }
      }).onEnd(state -> ((KrakenEntity)state.getEntity()).setBossPhase(2)));
      Consumer<ActiveState<KrakenEntity>> dyingStartCallback = state -> {
         KrakenEntity entity = (KrakenEntity)state.getEntity();
         entity.bossEvent.setVisible(false);
         entity.setHidden(false);
         entity.playSound((SoundEvent)BossesRiseSounds.KRAKEN_DEATH.value());
      };
      Consumer<ActiveState<KrakenEntity>> dyingTickCallback = state -> {
         KrakenEntity entity = (KrakenEntity)state.getEntity();
         RandomSource random = entity.getRandom();
         if (state.getTimer() == 80) {
            entity.getLoadedTentacles().forEach(KrakenTentacleEntity::killQuietly);
         } else if (random.nextInt(6) == 0) {
            List<KrakenTentacleEntity> tentacles = entity.getLoadedTentacles().stream().filter(LivingEntity::isAlive).toList();
            if (!tentacles.isEmpty()) {
               KrakenTentacleEntity tentacle = tentacles.get(random.nextInt(tentacles.size()));
               tentacle.killQuietly();
            }
         }
      };
      Consumer<ActiveState<KrakenEntity>> dyingEndCallback = state -> {
         KrakenEntity entity = (KrakenEntity)state.getEntity();
         entity.simulatePlayerKill();
      };
      STATE_REGISTRY.register(DYING_FROM_IDLE_STATE, State.builder().onStart(state -> {
         KrakenEntity entity = (KrakenEntity)state.getEntity();
         dyingStartCallback.accept(state);
         entity.moveToSecondPhasePosition();
         entity.triggerAnim(DYING_FROM_IDLE_STATE.getName());
      }).onTickFor(180, dyingTickCallback).onEnd(state -> {
         KrakenEntity entity = (KrakenEntity)state.getEntity();
         entity.simulatePlayerKill();
      }));
      STATE_REGISTRY.register(DYING_FROM_KNOCKDOWN_STATE, State.builder().onStart(state -> {
         KrakenEntity entity = (KrakenEntity)state.getEntity();
         dyingStartCallback.accept(state);
         entity.moveTo(entity.shipToWorldSpace(KNOCKDOWN_POSITION));
         entity.triggerAnim(DYING_FROM_KNOCKDOWN_STATE.getName());
      }).onTickFor(180, dyingTickCallback).onEnd(dyingEndCallback));
      STATE_REGISTRY.register(KNOCKDOWN_STATE, State.builder().onStart(state -> {
         KrakenEntity entity = (KrakenEntity)state.getEntity();
         entity.triggerAnim("main_controller", KNOCKDOWN_STATE.getName());
         entity.getLoadedTentacles().forEach(KrakenTentacleEntity::playRandomStunnedAnimation);
      }).onTickFor(304, state -> {
         KrakenEntity entity = (KrakenEntity)state.getEntity();
         int timer = state.getTimer();
         float offsetAmount = Mth.clampedMap(timer, 0.0F, 76.0F, 0.0F, 1.0F);
         offsetAmount -= Mth.clampedMap(timer, 228.0F, 304.0F, 0.0F, 1.0F);
         Vec3 newPosition = SECOND_PHASE_POSITION.lerp(KNOCKDOWN_POSITION, offsetAmount);
         entity.moveTo(entity.shipToWorldSpace(newPosition));
      }).onEnd(state -> {
         KrakenEntity entity = (KrakenEntity)state.getEntity();
         entity.moveToSecondPhasePosition();
      }));
   }

   protected record TentacleDefinition(
      String id, Vec3 offset, float rotation, KrakenTentacleEntity.TentacleType type, String introAnimationName, int respawnsAfter
   ) {
   }

   protected record TentacleRespawnTimer(String tentacleId, long respawnAtGameTime) {
      public static Codec<KrakenEntity.TentacleRespawnTimer> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.STRING.fieldOf("tentacle_id").forGetter(KrakenEntity.TentacleRespawnTimer::tentacleId),
               Codec.LONG.fieldOf("respawn_at_game_time").forGetter(KrakenEntity.TentacleRespawnTimer::respawnAtGameTime)
            )
            .apply(instance, KrakenEntity.TentacleRespawnTimer::new)
      );
      public static Codec<List<KrakenEntity.TentacleRespawnTimer>> LIST_CODEC = CODEC.listOf();
   }
}
