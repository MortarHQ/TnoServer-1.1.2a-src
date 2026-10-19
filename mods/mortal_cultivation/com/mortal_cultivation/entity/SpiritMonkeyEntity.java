package com.mortal_cultivation.entity;

import com.mortal_cultivation.block.SpiritLeavesBlock;
import com.mortal_cultivation.entity.ai.SpiritMonkeyNavigation;
import com.mortal_cultivation.entity.ai.SpiritMonkeyTreeGoal;
import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.CultivationEntities;
import com.mortal_cultivation.registry.SoundRegistry;
import com.mortal_cultivation.registry.WorldgenRegistry;
import com.mortal_cultivation.registry.item.CultivationItems;
import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SpiritMonkeyEntity extends Animal implements GeoEntity {
   private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(SpiritMonkeyEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> LYING_DOWN = SynchedEntityData.defineId(SpiritMonkeyEntity.class, EntityDataSerializers.BOOLEAN);
   private static final int LIE_DOWN_TICKS = 64;
   private static final EntityDimensions LYING_DIMENSIONS = EntityDimensions.scalable(1.6F, 0.9F).withEyeHeight(0.45F);
   private static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(SpiritMonkeyEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Optional<BlockPos>> CLIMBING_TRUNK = SynchedEntityData.defineId(
      SpiritMonkeyEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS
   );
   private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(SpiritMonkeyEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> TREE_JUMPING = SynchedEntityData.defineId(SpiritMonkeyEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> HANGING = SynchedEntityData.defineId(SpiritMonkeyEntity.class, EntityDataSerializers.BOOLEAN);
   private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.monkey.idle");
   private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.monkey.walk");
   private static final RawAnimation RUN = RawAnimation.begin().thenLoop("animation.monkey.run");
   private static final RawAnimation CLIMB = RawAnimation.begin().thenLoop("animation.monkey.climb");
   private static final RawAnimation CLIMB_IDLE = RawAnimation.begin().thenLoop("animation.monkey.climb_idle");
   private static final RawAnimation SLEEP_START = RawAnimation.begin().thenPlayAndHold("animation.monkey.sleep_start");
   private static final RawAnimation SLEEP = RawAnimation.begin().thenLoop("animation.monkey.sleep");
   private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("animation.monkey.attack");
   private static final RawAnimation HURT = RawAnimation.begin().thenPlay("animation.monkey.hurt");
   private static final RawAnimation FALL = RawAnimation.begin().thenLoop("animation.monkey.fall");
   private static final RawAnimation EAT = RawAnimation.begin().thenLoop("animation.monkey.eat");
   private static final RawAnimation JUMP = RawAnimation.begin().thenPlay("animation.monkey.jump");
   private static final RawAnimation HANG = RawAnimation.begin().thenLoop("animation.monkey.hang");
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   @Nullable
   private BlockPos jumpTrunk;
   @Nullable
   private Vec3 jumpLanding;
   private int jumpTicks;
   private int jumpDuration;
   private double climbingSpeed;
   @Nullable
   private Vec3 surfaceMotion;
   private int sleepCooldown;
   private int sleepingTicks;
   private int eatingTicks;
   private int fruitCooldown;
   public static final double TREE_JUMP_GRAVITY = 0.08;
   public static final double TREE_JUMP_DRAG = 0.98;

   public SpiritMonkeyEntity(EntityType<? extends SpiritMonkeyEntity> type, Level level) {
      super(type, level);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setPathfindingMalus(PathType.LEAVES, 0.0F);
      this.xpReward = 2;
   }

   public static Builder createAttributes() {
      return Animal.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 20.0)
         .add(Attributes.MOVEMENT_SPEED, 0.28)
         .add(Attributes.ATTACK_DAMAGE, 3.0)
         .add(Attributes.FOLLOW_RANGE, 24.0)
         .add(Attributes.STEP_HEIGHT, 1.0)
         .add(Attributes.SAFE_FALL_DISTANCE, 8.0);
   }

   public static boolean checkSpiritMonkeySpawnRules(
      EntityType<SpiritMonkeyEntity> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random
   ) {
      BlockState ground = level.getBlockState(pos.below());
      return level.getBiome(pos).is(WorldgenRegistry.SPIRIT_FORESTS)
         && (ground.is(BlockTags.DIRT) || ground.is(BlockTags.LEAVES) || ground.is(BlockTags.ANIMALS_SPAWNABLE_ON))
         && (MobSpawnType.ignoresLightRequirements(reason) || level.getRawBrightness(pos, 0) > 8)
         && checkMobSpawnRules(type, level, reason, pos, random);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(SLEEPING, false);
      builder.define(LYING_DOWN, false);
      builder.define(CLIMBING, false);
      builder.define(CLIMBING_TRUNK, Optional.empty());
      builder.define(EATING, false);
      builder.define(TREE_JUMPING, false);
      builder.define(HANGING, false);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.25, true));
      this.goalSelector
         .addGoal(
            2,
            new BreedGoal(this, 1.0) {
               public boolean canUse() {
                  return SpiritMonkeyEntity.this.getTarget() == null
                     && SpiritMonkeyEntity.this.onGround()
                     && !SpiritMonkeyEntity.this.isTreeJumping()
                     && super.canUse();
               }
            }
         );
      this.goalSelector.addGoal(3, new SpiritMonkeyEntity.NightSleepGoal());
      this.goalSelector.addGoal(4, new SpiritMonkeyEntity.EatFruitGoal());
      this.goalSelector
         .addGoal(
            5,
            new TemptGoal(this, 1.0, this::isFood, false) {
               public boolean canUse() {
                  SpiritMonkeyEntity monkey = SpiritMonkeyEntity.this;
                  return monkey.getTarget() == null
                     && (monkey.onGround() && !monkey.isClimbing() || ((SpiritMonkeyNavigation)monkey.getNavigation()).isClimbingLedge())
                     && super.canUse();
               }

               public void tick() {
                  SpiritMonkeyEntity monkey = SpiritMonkeyEntity.this;
                  SpiritMonkeyNavigation navigation = (SpiritMonkeyNavigation)monkey.getNavigation();
                  if (navigation.isClimbingLedge() || this.player != null && this.player.getY() > monkey.getY() + monkey.maxUpStep()) {
                     monkey.getLookControl().setLookAt(this.player, 30.0F, 40.0F);
                     if (!navigation.isClimbingLedge()) {
                        navigation.moveTo(this.player, 1.0);
                     }
                  } else {
                     super.tick();
                  }
               }
            }
         );
      this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1));
      this.goalSelector.addGoal(7, new SpiritMonkeyTreeGoal(this));
      this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
   }

   public boolean isFood(ItemStack stack) {
      return stack.is((Item)CultivationItems.SPIRIT_FRUIT.get());
   }

   public SpiritMonkeyEntity getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
      return (SpiritMonkeyEntity)((EntityType)CultivationEntities.SPIRIT_MONKEY.get()).create(level);
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      if (this.isFood(player.getItemInHand(hand)) && !this.level().isClientSide) {
         this.setSleepingState(false);
         this.sleepCooldown = 100;
      }

      return super.mobInteract(player, hand);
   }

   protected PathNavigation createNavigation(Level level) {
      return new SpiritMonkeyNavigation(this, level);
   }

   protected BodyRotationControl createBodyControl() {
      return new BodyRotationControl(this) {
         public void clientTick() {
            BlockPos trunk = SpiritMonkeyEntity.this.getClimbingTrunk();
            if (SpiritMonkeyEntity.this.isClimbing() && !SpiritMonkeyEntity.this.isHanging() && trunk != null) {
               SpiritMonkeyEntity.this.faceBodyTowards(Vec3.atCenterOf(trunk));
            } else if (!SpiritMonkeyEntity.this.isTreeJumping() && !SpiritMonkeyEntity.this.isHanging()) {
               super.clientTick();
            } else {
               SpiritMonkeyEntity.this.yBodyRot = SpiritMonkeyEntity.this.getYRot();
            }
         }
      };
   }

   public void faceBodyTowards(Vec3 point) {
      if (!(point.subtract(this.position()).horizontalDistanceSqr() < 0.0016)) {
         float yaw = (float)(Mth.atan2(point.z - this.getZ(), point.x - this.getX()) * 180.0F / (float)Math.PI) - 90.0F;
         this.setYRot(yaw);
         this.yBodyRot = yaw;
         this.yHeadRot = this.yBodyRot + Mth.clamp(Mth.wrapDegrees(this.yHeadRot - this.yBodyRot), -65.0F, 65.0F);
      }
   }

   public boolean isSleeping() {
      return (Boolean)this.entityData.get(SLEEPING);
   }

   public boolean isLyingDown() {
      return this.isSleeping() && (Boolean)this.entityData.get(LYING_DOWN);
   }

   protected EntityDimensions getDefaultDimensions(Pose pose) {
      return this.isLyingDown() ? LYING_DIMENSIONS.scale(this.getAgeScale()) : super.getDefaultDimensions(pose);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
      super.onSyncedDataUpdated(key);
      if (LYING_DOWN.equals(key) || SLEEPING.equals(key) && !this.isSleeping()) {
         Vec3 feet = this.position();
         boolean grounded = this.onGround();
         this.refreshDimensions();
         AABB atOriginalFeet = this.getBoundingBox().move(feet.subtract(this.position()));
         if (this.level().noBlockCollision(this, atOriginalFeet)) {
            this.setPos(feet);
            this.setOnGround(grounded);
         }
      }
   }

   private void setSleepingState(boolean sleeping) {
      this.sleepingTicks = 0;
      this.entityData.set(LYING_DOWN, false);
      this.entityData.set(SLEEPING, sleeping);
   }

   public boolean isClimbing() {
      return (Boolean)this.entityData.get(CLIMBING);
   }

   public boolean isEatingFruit() {
      return (Boolean)this.entityData.get(EATING);
   }

   public boolean isTreeJumping() {
      return (Boolean)this.entityData.get(TREE_JUMPING);
   }

   public boolean isHanging() {
      return (Boolean)this.entityData.get(HANGING);
   }

   @Nullable
   public BlockPos getClimbingTrunk() {
      return (BlockPos)((Optional)this.entityData.get(CLIMBING_TRUNK)).orElse(null);
   }

   public void climbTrunk(BlockPos trunk, double speed) {
      this.entityData.set(HANGING, false);
      this.entityData.set(CLIMBING_TRUNK, Optional.of(trunk.immutable()));
      this.climbingSpeed = speed;
   }

   public void climbSurface(BlockPos support, Vec3 motion, boolean hanging) {
      this.entityData.set(CLIMBING_TRUNK, Optional.of(support.immutable()));
      this.entityData.set(HANGING, hanging);
      this.surfaceMotion = motion;
   }

   public void moveAlongClimbingFace(BlockPos wall, Vec3 motion) {
      this.entityData.set(HANGING, false);
      this.entityData.set(CLIMBING_TRUNK, Optional.of(wall.immutable()));
      this.entityData.set(CLIMBING, true);
      this.faceBodyTowards(Vec3.atCenterOf(wall));
      this.setDeltaMovement(motion);
      this.move(MoverType.SELF, motion);
      this.fallDistance = 0.0F;
      this.calculateEntityAnimation(true);
   }

   public void stopClimbing() {
      this.surfaceMotion = null;
      this.entityData.set(HANGING, false);
      this.entityData.set(CLIMBING_TRUNK, Optional.empty());
      this.entityData.set(CLIMBING, false);
      this.entityData.set(TREE_JUMPING, false);
      this.jumpTrunk = null;
      this.jumpLanding = null;
   }

   public void startTreeJump(BlockPos targetTrunk, Vec3 landing, Vec3 velocity, int duration) {
      this.stopClimbing();
      this.jumpTrunk = targetTrunk.immutable();
      this.jumpLanding = landing;
      this.jumpDuration = duration;
      this.jumpTicks = 0;
      this.entityData.set(TREE_JUMPING, true);
      this.setOnGround(false);
      this.setDeltaMovement(velocity);
      this.hasImpulse = true;
      this.faceBodyTowards(landing);
   }

   public boolean onClimbable() {
      return this.isClimbing() || super.onClimbable();
   }

   public void tick() {
      if (!this.level().isClientSide) {
         if (this.sleepCooldown > 0) {
            this.sleepCooldown--;
         }

         if (this.fruitCooldown > 0) {
            this.fruitCooldown--;
         }

         LivingEntity target = this.getTarget();
         if (target != null && (!target.isAlive() || !this.canAttack(target) || this.distanceToSqr(target) > 1024.0)) {
            this.setTarget(null);
         }

         if (this.isSleeping() && !this.canSleepNow()) {
            this.setSleepingState(false);
         }
      }

      super.tick();
      if (!this.level().isClientSide) {
         this.tickEatingFruit();
         if (this.isSleeping() && !this.isLyingDown() && ++this.sleepingTicks >= 64) {
            this.entityData.set(LYING_DOWN, true);
         }
      }
   }

   public void travel(Vec3 input) {
      if (this.isEffectiveAi()
         && this.surfaceMotion != null
         && this.getClimbingTrunk() != null
         && this.getTarget() == null
         && !this.isInLiquid()
         && !this.isPassenger()) {
         Vec3 motion = this.surfaceMotion;
         this.surfaceMotion = null;
         this.entityData.set(CLIMBING, true);
         if (this.isHanging()) {
            this.faceBodyTowards(this.position().add(motion.multiply(1.0, 0.0, 1.0).normalize()));
         } else {
            this.faceBodyTowards(Vec3.atCenterOf(this.getClimbingTrunk()));
         }

         this.setDeltaMovement(motion);
         this.move(MoverType.SELF, motion);
         this.fallDistance = 0.0F;
         this.calculateEntityAnimation(true);
      } else if (!this.isEffectiveAi() || !((SpiritMonkeyNavigation)this.getNavigation()).travelClimb()) {
         if (this.isEffectiveAi() && this.isTreeJumping() && this.jumpTrunk != null && this.jumpLanding != null) {
            Vec3 velocity = this.getDeltaMovement();
            this.move(MoverType.SELF, velocity);
            this.setDeltaMovement(velocity.x * 0.98, (velocity.y - 0.08) * 0.98, velocity.z * 0.98);
            this.jumpTicks++;
            if (this.position().distanceToSqr(this.jumpLanding) < 0.42250000000000004 && this.isTouchingTree()) {
               BlockPos trunk = this.jumpTrunk;
               this.stopClimbing();
               this.climbTrunk(trunk, 0.0);
               this.entityData.set(CLIMBING, true);
               this.setDeltaMovement(Vec3.ZERO);
               this.fallDistance = 0.0F;
               this.faceBodyTowards(Vec3.atCenterOf(trunk));
            } else if (this.jumpTicks > this.jumpDuration + 6 || this.onGround() || this.horizontalCollision || this.isInLiquid()) {
               this.stopClimbing();
            }

            this.calculateEntityAnimation(true);
         } else {
            BlockPos climbingTrunk = this.getClimbingTrunk();
            if (this.isEffectiveAi() && climbingTrunk != null && this.getTarget() == null && !this.isInLiquid() && !this.isPassenger() && this.isTouchingTree()
               )
             {
               Vec3 toward = Vec3.atCenterOf(climbingTrunk).subtract(this.position()).multiply(1.0, 0.0, 1.0).normalize().scale(0.08);
               this.entityData.set(CLIMBING, true);
               this.faceBodyTowards(Vec3.atCenterOf(climbingTrunk));
               this.setDeltaMovement(toward.x, this.climbingSpeed, toward.z);
               this.move(MoverType.SELF, this.getDeltaMovement());
               this.fallDistance = 0.0F;
               this.calculateEntityAnimation(true);
            } else {
               if (!this.level().isClientSide) {
                  this.entityData.set(CLIMBING, false);
               }

               super.travel(!this.isSleeping() && !this.isEatingFruit() ? input : Vec3.ZERO);
            }
         }
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      boolean hurt = super.hurt(source, amount);
      if (hurt && !this.level().isClientSide) {
         this.setSleepingState(false);
         this.stopClimbing();
         this.stopEatingFruit();
         this.sleepCooldown = 100;
         if (source.getEntity() instanceof LivingEntity attacker && this.canAttack(attacker)) {
            this.setTarget(attacker);
         }

         if (this.isAlive()) {
            this.triggerAnim("reaction", "hurt");
         }
      }

      return hurt;
   }

   public boolean doHurtTarget(Entity target) {
      this.triggerAnim("reaction", "attack");
      return super.doHurtTarget(target);
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   private boolean canSleepNow() {
      return this.level().isNight()
         && this.getTarget() == null
         && this.sleepCooldown == 0
         && this.onGround()
         && !this.isInLiquid()
         && !this.isOnFire()
         && !this.isPassenger()
         && !this.isClimbing()
         && !this.isTreeJumping()
         && !this.isInLove()
         && !this.isEatingFruit()
         && this.hasRoomToLieDown();
   }

   private boolean hasRoomToLieDown() {
      AABB lyingBounds = LYING_DIMENSIONS.scale(this.getAgeScale() * this.getScale()).makeBoundingBox(this.position());
      return this.level().noBlockCollision(this, lyingBounds) && !this.level().containsAnyLiquid(lyingBounds);
   }

   public boolean isTouchingTree() {
      AABB bounds = this.getBoundingBox().inflate(0.22, 0.12, 0.22);

      for (BlockPos pos : BlockPos.betweenClosed(
         BlockPos.containing(bounds.minX, bounds.minY, bounds.minZ), BlockPos.containing(bounds.maxX, bounds.maxY, bounds.maxZ)
      )) {
         if (this.level().hasChunkAt(pos) && (this.level().getBlockState(pos).is(BlockTags.LOGS) || this.level().getBlockState(pos).is(BlockTags.LEAVES))) {
            return true;
         }
      }

      return false;
   }

   public boolean wantsFruit() {
      return this.fruitCooldown == 0 && this.getMainHandItem().isEmpty() && !this.isInLove() && this.getTarget() == null;
   }

   public boolean canReachFruitFrom(Vec3 eye, BlockPos fruit) {
      if (eye.distanceToSqr(Vec3.atCenterOf(fruit)) > 6.25) {
         return false;
      }

      BlockHitResult hit = this.level().clip(new ClipContext(eye, Vec3.atCenterOf(fruit), Block.COLLIDER, Fluid.NONE, this));
      return hit.getBlockPos().equals(fruit);
   }

   public boolean harvestNearbyFruit() {
      if (this.level() instanceof ServerLevel level && this.wantsFruit() && EventHooks.canEntityGrief(level, this)) {
         BlockPos eye = BlockPos.containing(this.getEyePosition());

         for (BlockPos pos : BlockPos.betweenClosed(eye.offset(-2, -2, -2), eye.offset(2, 2, 2))) {
            if (level.hasChunkAt(pos)
               && level.getBlockState(pos).is((net.minecraft.world.level.block.Block)BlockRegistry.SPIRIT_FRUIT_LEAVES.get())
               && this.canReachFruitFrom(this.getEyePosition(), pos)) {
               ItemStack fruit = SpiritLeavesBlock.harvestFruit(level, pos, this);
               if (!fruit.isEmpty()) {
                  this.setItemSlot(EquipmentSlot.MAINHAND, fruit);
                  this.getLookControl().setLookAt(Vec3.atCenterOf(pos));
                  this.beginEatingFruit();
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean canEatFruit() {
      return this.isFood(this.getMainHandItem())
         && this.getTarget() == null
         && !this.isSleeping()
         && !this.isInLove()
         && !this.isInLiquid()
         && !this.isOnFire()
         && !this.isTreeJumping()
         && (this.onGround() || this.isClimbing());
   }

   private void beginEatingFruit() {
      if (this.canEatFruit()) {
         this.eatingTicks = 0;
         this.entityData.set(EATING, true);
      }
   }

   private void stopEatingFruit() {
      this.entityData.set(EATING, false);
      this.eatingTicks = 0;
   }

   private void tickEatingFruit() {
      if (this.isEatingFruit()) {
         if (!this.canEatFruit()) {
            this.stopEatingFruit();
         } else {
            this.eatingTicks++;
            if (this.eatingTicks % 12 == 0 && this.level() instanceof ServerLevel level) {
               double yaw = this.yBodyRot * (float) (Math.PI / 180.0);
               level.sendParticles(
                  new ItemParticleOption(ParticleTypes.ITEM, this.getMainHandItem()),
                  this.getX() - Math.sin(yaw) * 0.35,
                  this.getEyeY() - 0.15,
                  this.getZ() + Math.cos(yaw) * 0.35,
                  4,
                  0.08,
                  0.06,
                  0.08,
                  0.02
               );
               this.playSound(SoundEvents.GENERIC_EAT, 0.4F, this.getVoicePitch());
            }

            if (this.eatingTicks >= 60) {
               this.getMainHandItem().shrink(1);
               if (this.getMainHandItem().isEmpty()) {
                  this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
               }

               this.gameEvent(GameEvent.EAT);
               this.fruitCooldown = 600 + this.random.nextInt(1200);
               this.stopEatingFruit();
            }
         }
      }
   }

   protected SoundEvent getAmbientSound() {
      return !this.isSleeping() && !this.isEatingFruit() ? (SoundEvent)SoundRegistry.SPIRIT_MONKEY_AMBIENT.get() : null;
   }

   public int getAmbientSoundInterval() {
      return 180;
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)SoundRegistry.SPIRIT_MONKEY_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)SoundRegistry.SPIRIT_MONKEY_HURT.get();
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("FruitCooldown", this.fruitCooldown);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.fruitCooldown = Mth.clamp(tag.getInt("FruitCooldown"), 0, 1800);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(
            this,
            "movement",
            4,
            state -> {
               if (this.isSleeping()) {
                  return state.setAndContinue(this.isLyingDown() ? SLEEP : SLEEP_START);
               } else if (this.isEatingFruit()) {
                  return state.setAndContinue(EAT);
               } else if (this.isTreeJumping()) {
                  return state.setAndContinue(JUMP);
               } else if (this.isHanging()) {
                  return state.setAndContinue(HANG);
               } else if (this.isClimbing()) {
                  return state.setAndContinue(Math.abs(this.getY() - this.yo) > 0.01 ? CLIMB : CLIMB_IDLE);
               } else {
                  return !this.onGround() && this.getDeltaMovement().y < -0.1 && !this.isInLiquid()
                     ? state.setAndContinue(FALL)
                     : state.setAndContinue(state.isMoving() ? (this.isAggressive() ? RUN : WALK) : IDLE);
               }
            }
         )
      );
      controllers.add(new AnimationController(this, "reaction", 2, state -> PlayState.STOP).triggerableAnim("attack", ATTACK).triggerableAnim("hurt", HURT));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   private class EatFruitGoal extends Goal {
      EatFruitGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public boolean canUse() {
         return SpiritMonkeyEntity.this.onGround() && SpiritMonkeyEntity.this.canEatFruit();
      }

      public boolean canContinueToUse() {
         return SpiritMonkeyEntity.this.isEatingFruit() && this.canUse();
      }

      public void start() {
         SpiritMonkeyEntity monkey = SpiritMonkeyEntity.this;
         monkey.getNavigation().stop();
         monkey.getMoveControl().setWantedPosition(monkey.getX(), monkey.getY(), monkey.getZ(), 0.0);
         monkey.setJumping(false);
         monkey.setSpeed(0.0F);
         monkey.beginEatingFruit();
      }

      public void stop() {
         SpiritMonkeyEntity.this.stopEatingFruit();
      }
   }

   private class NightSleepGoal extends Goal {
      NightSleepGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public boolean canUse() {
         return SpiritMonkeyEntity.this.canSleepNow();
      }

      public boolean canContinueToUse() {
         return SpiritMonkeyEntity.this.isSleeping() && SpiritMonkeyEntity.this.canSleepNow();
      }

      public void start() {
         SpiritMonkeyEntity monkey = SpiritMonkeyEntity.this;
         monkey.getNavigation().stop();
         monkey.getMoveControl().setWantedPosition(monkey.getX(), monkey.getY(), monkey.getZ(), 0.0);
         monkey.setJumping(false);
         monkey.setSpeed(0.0F);
         monkey.setXxa(0.0F);
         monkey.setZza(0.0F);
         monkey.setSleepingState(true);
      }

      public void stop() {
         SpiritMonkeyEntity.this.setSleepingState(false);
      }
   }
}
