package net.pixeldreamstudios.mythika.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.pixeldreamstudios.mythika.Mythika;
import net.pixeldreamstudios.mythika.client.ScreenShake;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MinotaurEntity extends Monster implements GeoEntity, TimedAttacker {
   private static final EntityDataAccessor<Byte> DATA_CHARGE_PHASE = SynchedEntityData.defineId(MinotaurEntity.class, EntityDataSerializers.BYTE);
   private static final byte EVENT_CHARGE_IMPACT = 101;
   private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
   private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
   private static final RawAnimation CHARGE = RawAnimation.begin().thenLoop("charge");
   private static final RawAnimation CHARGE_WINDUP = RawAnimation.begin().thenLoop("charge_windup");
   private static final RawAnimation STUN = RawAnimation.begin().thenLoop("stunned");
   private static final RawAnimation ATTACK = RawAnimation.begin().then("attack", LoopType.PLAY_ONCE);
   private static final double WALK_SPEED = 1.42;
   private static final double CHARGE_SPEED = 8.82;
   private static final int GAIT_TRANSITION_TICKS = 5;
   private static final Footfalls.Gait WALK_GAIT = new Footfalls.Gait(30.8, 0.062, 0.562);
   private static final Footfalls.Gait CHARGE_GAIT = new Footfalls.Gait(12.4, 0.065, 0.561);
   private static final Footfalls.Gait WINDUP_GAIT = new Footfalls.Gait(20.0, 0.0, 0.46);
   private static final double STUN_HEAD_FORWARD = 1.28;
   private static final double STUN_HEAD_HEIGHT = 1.72;
   private static final int ATTACK_TRANSITION_TICKS = 3;
   private static final double EXTRA_ATTACK_REACH = 0.35;
   private static final int ATTACK_IMPACT_TICK = 17;
   private static final int ATTACK_LOCK_TICKS = 29;
   private static final int ATTACK_COOLDOWN_TICKS = 35;
   private static final int CHARGE_COOLDOWN_TICKS = 80;
   private static final int CHARGE_RECOVERY_TICKS = 20;
   private static final int WITHDRAW_TICKS = 200;
   private static final TagKey<Block> BREAKABLE = TagKey.create(Registries.BLOCK, Mythika.id("minotaur_breakable"));
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private final GaitTracker gait = new GaitTracker();
   private final Footfalls footfalls = new Footfalls(5);
   private final AttackTimeline attack = new AttackTimeline(17, 29, 35);
   private int chargeCooldown;
   @Nullable
   private BoundingBox labyrinth;
   private int withdrawTicks;

   public static boolean canSmash(BlockState state) {
      return Mythika.config.minotaurChargeBreaksBlocks && state.is(BREAKABLE);
   }

   public MinotaurEntity(EntityType<? extends Monster> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 20;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_CHARGE_PHASE, (byte)MinotaurEntity.ChargePhase.NONE.ordinal());
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, Mythika.config.minotaurHealth)
         .add(Attributes.ATTACK_DAMAGE, Mythika.config.minotaurAttackDamage)
         .add(Attributes.ATTACK_KNOCKBACK, 1.5)
         .add(Attributes.MOVEMENT_SPEED, 0.2)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
         .add(Attributes.FOLLOW_RANGE, 28.0)
         .add(Attributes.STEP_HEIGHT, 1.0);
   }

   protected PathNavigation createNavigation(Level level) {
      return new MinotaurNavigation(this, level);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new MinotaurChargeGoal(this));
      this.goalSelector.addGoal(2, new RetreatIntoLabyrinthGoal(this));
      this.goalSelector.addGoal(3, new TimedMeleeAttackGoal(this, 1.25));
      this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.9));
      this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 12.0F));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public void bindToLabyrinth(BoundingBox maze) {
      this.labyrinth = maze;
      this.restrictTo(new BlockPos(maze.getCenter()), Math.max(maze.getXSpan(), maze.getZSpan()));
   }

   @Nullable
   public BoundingBox labyrinth() {
      return this.labyrinth;
   }

   public boolean guardsLabyrinth() {
      return this.labyrinth != null;
   }

   public boolean isInsideLabyrinth(BlockPos pos) {
      return this.labyrinth == null || this.labyrinth.isInside(pos);
   }

   public boolean isInsideLabyrinth(int x, int y, int z) {
      return this.labyrinth == null || this.labyrinth.isInside(x, y, z);
   }

   public boolean isInsideLabyrinth(Vec3 pos) {
      return this.isInsideLabyrinth(BlockPos.containing(pos));
   }

   public boolean isInsideLabyrinth(Entity entity) {
      return this.isInsideLabyrinth(entity.blockPosition());
   }

   public boolean isWithinRestriction(BlockPos pos) {
      return this.labyrinth != null ? this.labyrinth.isInside(pos) : super.isWithinRestriction(pos);
   }

   public void withdraw() {
      this.setTarget(null);
      this.withdrawTicks = 200;
   }

   public boolean isWithdrawing() {
      return this.withdrawTicks > 0;
   }

   public void stopWithdrawing() {
      this.withdrawTicks = 0;
   }

   public boolean hurt(DamageSource source, float amount) {
      boolean hurt = super.hurt(source, amount);
      if (hurt && !this.level().isClientSide && source.getEntity() != null && !this.isInsideLabyrinth(source.getEntity())) {
         this.withdraw();
      }

      return hurt;
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      if (this.labyrinth != null) {
         tag.putIntArray(
            "Labyrinth",
            new int[]{this.labyrinth.minX(), this.labyrinth.minY(), this.labyrinth.minZ(), this.labyrinth.maxX(), this.labyrinth.maxY(), this.labyrinth.maxZ()}
         );
      }
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      int[] box = tag.getIntArray("Labyrinth");
      if (box.length == 6) {
         this.bindToLabyrinth(new BoundingBox(box[0], box[1], box[2], box[3], box[4], box[5]));
      }
   }

   public boolean isCharging() {
      return this.chargePhase() == MinotaurEntity.ChargePhase.CHARGING;
   }

   public MinotaurEntity.ChargePhase chargePhase() {
      byte ordinal = (Byte)this.entityData.get(DATA_CHARGE_PHASE);
      MinotaurEntity.ChargePhase[] phases = MinotaurEntity.ChargePhase.values();
      return ordinal >= 0 && ordinal < phases.length ? phases[ordinal] : MinotaurEntity.ChargePhase.NONE;
   }

   public void setChargePhase(MinotaurEntity.ChargePhase phase) {
      this.entityData.set(DATA_CHARGE_PHASE, (byte)phase.ordinal());
   }

   public boolean canStartCharge() {
      return this.chargeCooldown <= 0 && !this.isAttackLocked();
   }

   public void startChargeCooldown() {
      this.chargeCooldown = 80;
   }

   public void recoverFromCharge() {
      this.attack.hold(20);
   }

   public void onChargeImpact() {
      this.level().broadcastEntityEvent(this, (byte)101);
   }

   public void handleEntityEvent(byte id) {
      if (id == 101) {
         ScreenShake.onSlam(this);
      } else {
         super.handleEntityEvent(id);
      }
   }

   public void spawnHoofDust() {
      if (this.level() instanceof ServerLevel server) {
         Vec3 var3 = Vec3.directionFromRotation(0.0F, this.yBodyRot);
         server.sendParticles(ParticleTypes.POOF, this.getX() + var3.x * 1.1, this.getY() + 0.1, this.getZ() + var3.z * 1.1, 6, 0.25, 0.02, 0.25, 0.02);
      }
   }

   public void spawnStunStars() {
      if (this.level() instanceof ServerLevel server) {
         Vec3 var3 = Vec3.directionFromRotation(0.0F, this.yBodyRot);
         server.sendParticles(ParticleTypes.END_ROD, this.getX() + var3.x * 1.28, this.getY() + 1.72, this.getZ() + var3.z * 1.28, 3, 0.4, 0.22, 0.4, 0.005);
      }
   }

   protected AABB getAttackBoundingBox() {
      return super.getAttackBoundingBox().inflate(0.35, 0.0, 0.35);
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
      return this.attack.isLocked();
   }

   public void tick() {
      super.tick();
      this.gait.tick(this);
      if (!this.level().isClientSide) {
         if (this.chargeCooldown > 0) {
            this.chargeCooldown--;
         }

         if (this.withdrawTicks > 0) {
            this.withdrawTicks--;
         }

         if (this.getTarget() != null && !this.isInsideLabyrinth(this.getTarget())) {
            this.withdraw();
         }

         this.tickFootfalls();
         boolean impact = this.attack.tick();
         this.playSwingFoley(this.attack.currentTick());
         LivingEntity target = this.getTarget();
         if (impact && target != null && this.getAttackBoundingBox().inflate(0.5, 0.0, 0.5).intersects(target.getBoundingBox())) {
            this.doHurtTarget(target);
         }
      }
   }

   @Nullable
   private Footfalls.Gait currentGait() {
      return switch (this.chargePhase()) {
         case WINDUP -> WINDUP_GAIT;
         case CHARGING -> CHARGE_GAIT;
         case STUNNED -> null;
         default -> this.gait.isMoving() ? WALK_GAIT : null;
      };
   }

   private void tickFootfalls() {
      Footfalls.Gait cycle = this.currentGait();
      if (this.footfalls.tick(cycle, this.gaitPlaybackRate())) {
         if (cycle == WINDUP_GAIT) {
            this.playSound(SoundEvents.HOGLIN_STEP, 1.4F, 0.5F);
            this.spawnHoofDust();
         } else if (this.onGround() && !this.isInWater()) {
            boolean galloping = cycle == CHARGE_GAIT;
            this.playSound(SoundEvents.HOGLIN_STEP, galloping ? 1.0F : 0.5F, galloping ? 0.6F : 0.8F);
         }
      }
   }

   public double gaitPlaybackRate() {
      return this.gait.rateFor(this.isCharging() ? 8.82 : 1.42);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new GaitAnimationController(this, "controller", 5, state -> {
         switch (this.chargePhase()) {
            case WINDUP:
               return state.setAndContinue(CHARGE_WINDUP);
            case CHARGING:
               return state.setAndContinue(CHARGE);
            case STUNNED:
               return state.setAndContinue(STUN);
            default:
               return state.setAndContinue(this.gait.isMoving() ? WALK : IDLE);
         }
      }, MinotaurEntity::gaitPlaybackRate));
      controllers.add(new AnimationController(this, "attackController", 3, state -> PlayState.STOP).triggerableAnim("attack", ATTACK));
   }

   public int getMaxHeadXRot() {
      return 25;
   }

   private void playSwingFoley(int tick) {
      switch (tick) {
         case 5:
            this.playSound(SoundEvents.HOGLIN_ANGRY, 1.0F, 0.7F);
            break;
         case 15:
            this.playSound(SoundEvents.MACE_SMASH_AIR, 0.7F, 1.2F);
            break;
         case 17:
            this.playSound(SoundEvents.GOAT_RAM_IMPACT, 1.2F, 0.6F);
            this.playSound(SoundEvents.HOGLIN_ATTACK, 1.0F, 0.8F);
      }
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.HOGLIN_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return SoundEvents.HOGLIN_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.HOGLIN_DEATH;
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
   }

   public float getVoicePitch() {
      return 0.75F;
   }

   public enum ChargePhase {
      NONE,
      WINDUP,
      CHARGING,
      STUNNED;
   }
}
