package net.pixeldreamstudios.mythika.entity;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.pixeldreamstudios.mythika.Mythika;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SatyrEntity extends PathfinderMob implements GeoEntity, TimedAttacker {
   private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
   private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
   private static final RawAnimation RUN = RawAnimation.begin().thenLoop("run");
   private static final RawAnimation MUSIC = RawAnimation.begin().thenLoop("music");
   private static final RawAnimation ATTACK = RawAnimation.begin().then("attack", LoopType.PLAY_ONCE);
   private static final double WALK_SPEED = 0.91;
   private static final double RUN_SPEED = 2.19;
   private static final double RUN_THRESHOLD = 1.41;
   private static final int GAIT_TRANSITION_TICKS = 5;
   private static final Footfalls.Gait WALK_GAIT = new Footfalls.Gait(28.6, 0.478, 0.976);
   private static final Footfalls.Gait RUN_GAIT = new Footfalls.Gait(11.9, 0.478, 0.976);
   private static final int ATTACK_TRANSITION_TICKS = 3;
   private static final int MUSIC_TRANSITION_TICKS = 8;
   private static final int ATTACK_IMPACT_TICK = 25;
   private static final int ATTACK_COOLDOWN_TICKS = 41;
   private static final int CAMPFIRE_SEARCH_RADIUS = 10;
   private static final int CAMPFIRE_SEARCH_HEIGHT = 4;
   private static final int CAMPFIRE_SCAN_INTERVAL = 80;
   private static final int CAMPFIRE_RETURN_RADIUS = 32;
   private static final int TURN_TIMEOUT_TICKS = 160;
   private static final int ROLE_INTERVAL = 10;
   private static final double WAIT_WEIGHT = 0.02;
   private static final int MAX_WAIT_TICKS = 400;
   private static final double FURY_PER_PIPER = 0.12;
   private static final int MAX_PIPERS = 5;
   private static final ResourceLocation CHORUS_FURY = Mythika.id("satyr_chorus_fury");
   private static final double PACK_RADIUS = 12.0;
   private static final float PIPING_HEAD_LOOK = 0.25F;
   private static final float HEAD_LOOK_EASING = 0.15F;
   private static final float MELODY_VOLUME = 0.7F;
   private static final float DRONE_VOLUME = 0.45F;
   private static final float WAR_VOLUME = 0.9F;
   private static final double MOUTH_REACH = 2.0;
   public static final int VARIANTS = 3;
   private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(SatyrEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> DATA_PLAYING_MUSIC = SynchedEntityData.defineId(SatyrEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Optional<BlockPos>> DATA_CAMPFIRE = SynchedEntityData.defineId(
      SatyrEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS
   );
   private static final EntityDataAccessor<Boolean> DATA_WAR_SONG = SynchedEntityData.defineId(SatyrEntity.class, EntityDataSerializers.BOOLEAN);
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private final GaitTracker gait = new GaitTracker();
   private final Footfalls footfalls = new Footfalls(5);
   private final AttackTimeline attack = new AttackTimeline(25, 0, 41);
   @Nullable
   private BlockPos knownCampfire;
   private int nextCampfireScan;
   @Nullable
   private BlockPos homeCampfire;
   private boolean buildingFire;
   private boolean champion;
   private int packIndex;
   private int packSize = 1;
   private int nextRoleCheck;
   private double packBearing;
   @Nullable
   private SatyrEntity packChampion;
   private long turnEndedAt;
   private long turnStartedAt;
   private int pipersBehind;
   @Nullable
   private Vec3 aulosMouth;
   private float headLookWeight = 1.0F;

   public SatyrEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 5;
      this.setPathfindingMalus(PathType.DAMAGE_FIRE, -1.0F);
      this.setPathfindingMalus(PathType.DANGER_FIRE, 16.0F);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, Mythika.config.satyrHealth)
         .add(Attributes.ATTACK_DAMAGE, Mythika.config.satyrAttackDamage)
         .add(Attributes.MOVEMENT_SPEED, 0.19)
         .add(Attributes.FOLLOW_RANGE, 20.0);
   }

   public static boolean checkSatyrSpawnRules(EntityType<SatyrEntity> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
      return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && level.getRawBrightness(pos, 0) > 8;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new ChargeInTurnGoal(this));
      this.goalSelector.addGoal(2, new MenacingChorusGoal(this));
      this.goalSelector.addGoal(3, new AvoidEntityGoal(this, Monster.class, 8.0F, 1.4, 1.6));
      this.goalSelector.addGoal(4, new LightCampfireGoal(this));
      this.goalSelector.addGoal(5, new PipeAtCampfireGoal(this));
      this.goalSelector.addGoal(6, new ReturnToCampfireGoal(this));
      this.goalSelector.addGoal(7, new FollowFireBuilderGoal(this));
      this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.75));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{SatyrEntity.class}).setAlertOthers(new Class[0]));
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_VARIANT, 0);
      builder.define(DATA_PLAYING_MUSIC, false);
      builder.define(DATA_CAMPFIRE, Optional.empty());
      builder.define(DATA_WAR_SONG, false);
   }

   public int getVariant() {
      return (Integer)this.entityData.get(DATA_VARIANT);
   }

   public void setVariant(int variant) {
      this.entityData.set(DATA_VARIANT, Mth.clamp(variant, 0, 2));
   }

   public boolean isPlayingMusic() {
      return (Boolean)this.entityData.get(DATA_PLAYING_MUSIC);
   }

   @Nullable
   public BlockPos getCampfire() {
      return (BlockPos)((Optional)this.entityData.get(DATA_CAMPFIRE)).orElse(null);
   }

   public boolean isWarSong() {
      return (Boolean)this.entityData.get(DATA_WAR_SONG);
   }

   public void startPiping(BlockPos fire) {
      this.entityData.set(DATA_CAMPFIRE, Optional.of(fire));
      this.entityData.set(DATA_WAR_SONG, false);
      this.entityData.set(DATA_PLAYING_MUSIC, true);
   }

   public void startWarSong() {
      this.entityData.set(DATA_CAMPFIRE, Optional.empty());
      this.entityData.set(DATA_WAR_SONG, true);
      this.entityData.set(DATA_PLAYING_MUSIC, true);
   }

   public void stopPiping() {
      this.entityData.set(DATA_PLAYING_MUSIC, false);
      this.entityData.set(DATA_WAR_SONG, false);
      this.entityData.set(DATA_CAMPFIRE, Optional.empty());
   }

   public boolean isDroning() {
      return Math.floorMod(this.getUUID().hashCode(), 3) == 0;
   }

   @Nullable
   public BlockPos nearestCampfire() {
      if (this.tickCount >= this.nextCampfireScan) {
         this.nextCampfireScan = this.tickCount + 80;
         this.knownCampfire = BlockPos.findClosestMatch(this.blockPosition(), 10, 4, pos -> isLitCampfire(this.level(), pos))
            .<BlockPos>map(BlockPos::immutable)
            .orElse(null);
         if (this.knownCampfire != null) {
            this.homeCampfire = this.knownCampfire;
         }
      }

      return this.knownCampfire;
   }

   public void forgetCampfire() {
      this.knownCampfire = null;
      this.nextCampfireScan = 0;
   }

   @Nullable
   public BlockPos campfireToReturnTo() {
      if (this.homeCampfire == null || !this.homeCampfire.closerToCenterThan(this.position(), 32.0) || !this.level().hasChunkAt(this.homeCampfire)) {
         return null;
      } else if (!isLitCampfire(this.level(), this.homeCampfire)) {
         this.homeCampfire = null;
         return null;
      } else {
         return this.homeCampfire;
      }
   }

   public void rememberCampfire(BlockPos fire) {
      this.homeCampfire = fire.immutable();
   }

   public void forgetHomeCampfire() {
      this.homeCampfire = null;
   }

   public boolean isBuildingFire() {
      return this.buildingFire;
   }

   public void setBuildingFire(boolean building) {
      this.buildingFire = building;
   }

   public boolean isChampion() {
      return this.champion;
   }

   public int packIndex() {
      return this.packIndex;
   }

   public int packSize() {
      return this.packSize;
   }

   @Nullable
   public SatyrEntity packChampion() {
      return this.packChampion;
   }

   private void tickWarRole() {
      LivingEntity target = this.getTarget();
      if (target == null) {
         this.champion = false;
         this.packChampion = null;
         this.packIndex = 0;
         this.packSize = 1;
         this.setChorusBoost(0);
      } else if (!Mythika.config.satyrPlaysMusic) {
         this.champion = true;
         this.setChorusBoost(0);
      } else if (this.tickCount >= this.nextRoleCheck) {
         this.nextRoleCheck = this.tickCount + 10;
         long now = this.level().getGameTime();
         if (this.champion && now - this.turnStartedAt > 160L) {
            this.turnEndedAt = now;
         }

         List<SatyrEntity> pack = this.level()
            .getEntitiesOfClass(SatyrEntity.class, target.getBoundingBox().inflate(12.0), other -> other.isAlive() && other.getTarget() == target);
         pack.sort(Comparator.comparing(Entity::getUUID));
         int index = pack.indexOf(this);
         if (index < 0) {
            this.champion = true;
            this.packChampion = null;
            this.packIndex = 0;
            this.packSize = 1;
            this.setChorusBoost(0);
         } else {
            this.packIndex = index;
            this.packSize = pack.size();
            SatyrEntity swinging = null;
            SatyrEntity next = null;
            SatyrEntity longestWaited = pack.get(0);
            double best = 0.0;
            int pipers = 0;

            for (SatyrEntity member : pack) {
               if (member.turnEndedAt < longestWaited.turnEndedAt) {
                  longestWaited = member;
               }

               if (member != this && member.isPlayingMusic() && member.isWarSong()) {
                  pipers++;
               }

               if (swinging == null && member.attack.isSwinging()) {
                  swinging = member;
               }

               if (member.canStartAttack()) {
                  double waited = Math.min(now - member.turnEndedAt, 400L);
                  double score = member.distanceTo(target) - waited * 0.02;
                  if (next == null || score < best) {
                     next = member;
                     best = score;
                  }
               }
            }

            if (swinging != null) {
               this.packChampion = swinging;
            } else {
               this.packChampion = next != null ? next : longestWaited;
            }

            if (this.packChampion == this && !this.champion) {
               this.turnStartedAt = now;
            }

            this.champion = this.packChampion == this;
            this.setChorusBoost(this.champion ? Math.min(pipers, 5) : 0);
            double x = 0.0;
            double z = 0.0;

            for (SatyrEntity member : pack) {
               x += member.getX();
               z += member.getZ();
            }

            this.packBearing = Math.atan2(z / pack.size() - target.getZ(), x / pack.size() - target.getX());
         }
      }
   }

   public double packBearing() {
      return this.packBearing;
   }

   private void setChorusBoost(int pipers) {
      if (pipers != this.pipersBehind) {
         this.pipersBehind = pipers;
         this.boost(Attributes.ATTACK_DAMAGE, CHORUS_FURY, pipers * 0.12);
      }
   }

   private void boost(Holder<Attribute> attribute, ResourceLocation id, double amount) {
      AttributeInstance instance = this.getAttribute(attribute);
      if (instance != null) {
         instance.removeModifier(id);
         if (amount > 0.0) {
            instance.addTransientModifier(new AttributeModifier(id, amount, Operation.ADD_MULTIPLIED_BASE));
         }
      }
   }

   public static boolean isLitCampfire(BlockGetter level, BlockPos pos) {
      return CampfireBlock.isLitCampfire(level.getBlockState(pos));
   }

   public void setAulosMouth(Vec3 mouth) {
      this.aulosMouth = mouth;
   }

   public float headLookWeight() {
      return this.headLookWeight;
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData
   ) {
      this.setVariant(this.random.nextInt(3));
      return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("Variant", this.getVariant());
      if (this.homeCampfire != null) {
         tag.putLong("HomeCampfire", this.homeCampfire.asLong());
      }
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setVariant(tag.getInt("Variant"));
      this.homeCampfire = tag.contains("HomeCampfire") ? BlockPos.of(tag.getLong("HomeCampfire")) : null;
   }

   public void tick() {
      super.tick();
      this.gait.tick(this);
      this.tickMusic();
      if (!this.level().isClientSide) {
         this.tickWarRole();
         this.tickFootfalls();
         boolean impact = this.attack.tick();
         this.playSwingFoley(this.attack.currentTick());
         if (impact) {
            this.turnEndedAt = this.level().getGameTime();
            LivingEntity target = this.getTarget();
            if (target != null && this.getAttackBoundingBox().inflate(0.5, 0.0, 0.5).intersects(target.getBoundingBox())) {
               this.doHurtTarget(target);
            }
         }
      }
   }

   public boolean isRunning() {
      return this.gait.blocksPerSecond() >= 1.41;
   }

   private void tickFootfalls() {
      Footfalls.Gait cycle = this.gait.isMoving() ? (this.isRunning() ? RUN_GAIT : WALK_GAIT) : null;
      if (this.footfalls.tick(cycle, this.gaitPlaybackRate()) && this.onGround() && !this.isInWater()) {
         this.playSound(SoundEvents.GOAT_STEP, 0.15F, 1.0F);
      }
   }

   public double gaitPlaybackRate() {
      return this.gait.rateFor(this.isRunning() ? 2.19 : 0.91);
   }

   private void tickMusic() {
      boolean playing = this.isPlayingMusic() && this.isAlive();
      if (this.level().isClientSide) {
         float target = playing ? 0.25F : 1.0F;
         this.headLookWeight = this.headLookWeight + (target - this.headLookWeight) * 0.15F;
      }

      if (playing) {
         boolean war = this.isWarSong();
         boolean droning = this.isDroning();
         long gameTime = this.level().getGameTime();
         int note;
         if (war) {
            note = SatyrTunes.warNote(gameTime, droning);
         } else {
            BlockPos fire = this.getCampfire();
            note = SatyrTunes.revelNote(gameTime, fire == null ? 0L : fire.asLong(), droning);
         }

         if (note != -1) {
            if (!this.level().isClientSide) {
               SoundEvent voice = war && droning ? (SoundEvent)SoundEvents.NOTE_BLOCK_DIDGERIDOO.value() : (SoundEvent)SoundEvents.NOTE_BLOCK_FLUTE.value();
               float volume = war ? 0.9F : (droning ? 0.45F : 0.7F);
               this.level().playSound(null, this.getX(), this.getEyeY(), this.getZ(), voice, this.getSoundSource(), volume, SatyrTunes.pitch(note));
            } else if (this.aulosMouth != null && this.aulosMouth.distanceToSqr(this.position()) < 4.0) {
               this.level().addParticle(ParticleTypes.NOTE, this.aulosMouth.x, this.aulosMouth.y, this.aulosMouth.z, SatyrTunes.colour(note), 0.0, 0.0);
            }
         }
      }
   }

   @Override
   public boolean canStartAttack() {
      return this.attack.canStart();
   }

   @Override
   public void startAttack() {
      this.attack.start();
      this.triggerAnim("attackController", "attack");
   }

   @Override
   public boolean isAttackLocked() {
      return false;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new GaitAnimationController(
            this,
            "controller",
            5,
            state -> this.gait.isMoving() ? state.setAndContinue(this.isRunning() ? RUN : WALK) : state.setAndContinue(IDLE),
            SatyrEntity::gaitPlaybackRate
         )
      );
      controllers.add(new AnimationController(this, "musicController", 8, state -> this.isPlayingMusic() ? state.setAndContinue(MUSIC) : PlayState.STOP));
      controllers.add(new AnimationController(this, "attackController", 3, state -> PlayState.STOP).triggerableAnim("attack", ATTACK));
   }

   public int getMaxHeadXRot() {
      return 30;
   }

   private void playSwingFoley(int tick) {
      switch (tick) {
         case 17:
            this.playSound(SoundEvents.GOAT_PREPARE_RAM, 0.8F, 1.1F);
            break;
         case 25:
            this.playSound(SoundEvents.GOAT_RAM_IMPACT, 0.8F, 1.2F);
      }
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.GOAT_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return SoundEvents.GOAT_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.GOAT_DEATH;
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
   }
}
