package net.pixeldreamstudios.mythika.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.pixeldreamstudios.mythika.Mythika;
import net.pixeldreamstudios.mythika.client.ScreenShake;
import net.pixeldreamstudios.mythika.platform.Services;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CyclopsEntity extends Monster implements GeoEntity, TimedAttacker {
   private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
   private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
   private static final RawAnimation ATTACK = RawAnimation.begin().then("attack", LoopType.PLAY_ONCE);
   private static final double WALK_SPEED = 1.24;
   private static final int GAIT_TRANSITION_TICKS = 5;
   private static final Footfalls.Gait WALK_GAIT = new Footfalls.Gait(40.0, 0.082, 0.582);
   private static final int ATTACK_TRANSITION_TICKS = 3;
   private static final int ATTACK_IMPACT_TICK = 28;
   private static final int ATTACK_LOCK_TICKS = 58;
   private static final int ATTACK_COOLDOWN_TICKS = 64;
   private static final double EXTRA_ATTACK_REACH = 1.52;
   private static final double SLAM_DISTANCE = 3.0;
   private static final double SLAM_KNOCKBACK = 1.6;
   private static final double SLAM_LIFT = 0.25;
   public static final int LAIR_REACH = 32;
   private static final int MIN_HOME_RADIUS = 16;
   private static final int STUCK_TICKS = 30;
   private static final double STUCK_SPEED = 0.3;
   private static final TagKey<Block> BREAKABLE = TagKey.create(Registries.BLOCK, Mythika.id("cyclops_breakable"));
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private final GaitTracker gait = new GaitTracker();
   private final Footfalls footfalls = new Footfalls(5);
   private final AttackTimeline attack = new AttackTimeline(28, 58, 64);
   private int blockedTicks;
   private boolean pennedIn;
   @Nullable
   private BlockPos lair;
   private int lairRadius;

   public static boolean canSmash(BlockState state) {
      return Mythika.config.cyclopsBreaksBlocks && state.is(BREAKABLE);
   }

   public CyclopsEntity(EntityType<? extends Monster> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 30;
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setPathfindingMalus(PathType.DAMAGE_FIRE, -1.0F);
      this.setPathfindingMalus(PathType.DANGER_FIRE, 16.0F);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, Mythika.config.cyclopsHealth)
         .add(Attributes.ATTACK_DAMAGE, Mythika.config.cyclopsAttackDamage)
         .add(Attributes.MOVEMENT_SPEED, 0.2)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.STEP_HEIGHT, 1.0);
   }

   public void bindToLair(BlockPos home, int radius) {
      this.lair = home;
      this.lairRadius = radius;
      this.restrictTo(home, radius);
   }

   @Nullable
   public BlockPos lair() {
      return this.lair;
   }

   public int homeRadius() {
      return Math.max(this.lairRadius - 32, 16);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      if (this.lair != null) {
         tag.putIntArray("Lair", new int[]{this.lair.getX(), this.lair.getY(), this.lair.getZ(), this.lairRadius});
      }
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      int[] home = tag.getIntArray("Lair");
      if (home.length == 4) {
         this.bindToLair(new BlockPos(home[0], home[1], home[2]), home[3]);
      }
   }

   protected PathNavigation createNavigation(Level level) {
      return new CyclopsNavigation(this, level);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new TimedMeleeAttackGoal(this, 1.1));
      this.goalSelector.addGoal(2, new ReturnToLairGoal(this));
      this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.85));
      this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 16.0F));
      this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
   }

   protected AABB getAttackBoundingBox() {
      return super.getAttackBoundingBox().inflate(1.52, 0.0, 1.52);
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

   private boolean isPennedIn() {
      AABB box = this.getBoundingBox();

      for (Direction dir : Plane.HORIZONTAL) {
         if (this.level().noCollision(this, box.move(dir.getStepX(), 0.0, dir.getStepZ()))) {
            return false;
         }
      }

      return true;
   }

   public boolean isStuck() {
      return this.blockedTicks >= 30;
   }

   public void aiStep() {
      super.aiStep();
      if (!this.level().isClientSide && this.isAlive() && Mythika.config.cyclopsBreaksBlocks && Services.PLATFORM.canGrief(this)) {
         if (this.tickCount % 10 == 0) {
            this.pennedIn = this.gait.blocksPerSecond() < 0.3 && this.isPennedIn();
         }

         this.blockedTicks = this.pennedIn ? this.blockedTicks + 1 : 0;
         boolean clearingAPath = this.getTarget() != null && this.horizontalCollision;
         if (clearingAPath || this.isStuck()) {
            boolean broke = false;
            AABB reach = this.getBoundingBox().inflate(this.pennedIn ? 1.0 : 0.2);

            for (BlockPos pos : BlockPos.betweenClosed(
               Mth.floor(reach.minX), Mth.floor(reach.minY), Mth.floor(reach.minZ), Mth.floor(reach.maxX), Mth.floor(reach.maxY), Mth.floor(reach.maxZ)
            )) {
               if (canSmash(this.level().getBlockState(pos))) {
                  broke = this.level().destroyBlock(pos, true, this) || broke;
               }
            }

            if (broke) {
               this.playSound(SoundEvents.RAVAGER_ATTACK, 0.6F, 0.6F);
            } else if (this.onGround()) {
               this.jumpFromGround();
            }
         }
      }
   }

   public void tick() {
      super.tick();
      this.gait.tick(this);
      if (!this.level().isClientSide) {
         this.tickFootfalls();
         boolean impact = this.attack.tick();
         this.playSwingFoley(this.attack.currentTick());
         if (impact) {
            this.slamShockwave();
         }
      }
   }

   private void tickFootfalls() {
      boolean landed = this.footfalls.tick(this.gait.isMoving() ? WALK_GAIT : null, this.gaitPlaybackRate());
      if (landed && this.onGround() && !this.isInWater()) {
         this.playSound(SoundEvents.RAVAGER_STEP, 0.6F, 0.7F);
      }
   }

   public double gaitPlaybackRate() {
      return this.gait.rateFor(1.24);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new GaitAnimationController(
            this, "controller", 5, state -> this.gait.isMoving() ? state.setAndContinue(WALK) : state.setAndContinue(IDLE), CyclopsEntity::gaitPlaybackRate
         )
      );
      controllers.add(
         new AnimationController(this, "attackController", 3, state -> PlayState.STOP)
            .triggerableAnim("attack", ATTACK)
            .setCustomInstructionKeyframeHandler(event -> {
               if (this.level().isClientSide) {
                  ScreenShake.onSlam(this);
               }
            })
      );
   }

   public int getMaxHeadXRot() {
      return 20;
   }

   private void playSwingFoley(int tick) {
      switch (tick) {
         case 5:
            this.playSound(SoundEvents.RAVAGER_ROAR, 1.1F, 0.5F);
            break;
         case 23:
            this.playSound(SoundEvents.MACE_SMASH_AIR, 1.4F, 0.6F);
            break;
         case 26:
            this.playSound(SoundEvents.RAVAGER_STEP, 1.3F, 0.5F);
            break;
         case 28:
            this.playSound(SoundEvents.MACE_SMASH_GROUND_HEAVY, 1.5F, 0.7F);
            this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 0.6F);
            this.spawnSlamDebris();
      }
   }

   private Vec3 slamCentre() {
      Vec3 forward = Vec3.directionFromRotation(0.0F, this.yBodyRot);
      return new Vec3(this.getX() + forward.x * 3.0, this.getY(), this.getZ() + forward.z * 3.0);
   }

   private void slamShockwave() {
      Vec3 centre = this.slamCentre();
      double radius = Mythika.config.cyclopsSlamRadius;
      float damage = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
      AABB area = new AABB(centre.x - radius, centre.y - 2.0, centre.z - radius, centre.x + radius, centre.y + 3.0, centre.z + radius);

      for (LivingEntity victim : this.level()
         .getEntitiesOfClass(LivingEntity.class, area, other -> other != this && other.isAlive() && !(other instanceof CyclopsEntity))) {
         double dx = victim.getX() - centre.x;
         double dz = victim.getZ() - centre.z;
         double distance = Math.sqrt(dx * dx + dz * dz);
         if (!(distance > radius)) {
            victim.hurt(this.damageSources().mobAttack(this), damage * (float)(1.0 - 0.5 * distance / radius));
            victim.knockback(1.6, centre.x - victim.getX(), centre.z - victim.getZ());
            victim.setDeltaMovement(victim.getDeltaMovement().add(0.0, 0.25, 0.0));
            victim.hurtMarked = true;
         }
      }
   }

   private void spawnSlamDebris() {
      if (this.level() instanceof ServerLevel server) {
         Vec3 centre = this.slamCentre();
         double x = centre.x;
         double z = centre.z;
         BlockPos pos = BlockPos.containing(x, this.getY() - 0.2, z);
         BlockState struck = server.getBlockState(pos);

         for (int i = 0; i < 3 && struck.isAir(); i++) {
            pos = pos.below();
            struck = server.getBlockState(pos);
         }

         if (!struck.isAir()) {
            server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, struck), x, pos.getY() + 1.0, z, 60, 0.9, 0.08, 0.9, 0.35);
         }
      }
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.RAVAGER_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return SoundEvents.RAVAGER_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.RAVAGER_DEATH;
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
   }

   protected float getSoundVolume() {
      return 1.4F;
   }

   public float getVoicePitch() {
      return 0.6F;
   }
}
