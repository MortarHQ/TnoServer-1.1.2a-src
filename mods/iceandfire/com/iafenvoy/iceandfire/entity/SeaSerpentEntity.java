package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.IceAndFire;
import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.data.SeaSerpentType;
import com.iafenvoy.iceandfire.entity.ai.FlyingAITargetGoal;
import com.iafenvoy.iceandfire.entity.ai.SeaSerpentAIAttackMeleeGoal;
import com.iafenvoy.iceandfire.entity.ai.SeaSerpentAIGetInWaterGoal;
import com.iafenvoy.iceandfire.entity.ai.SeaSerpentAIJumpGoal;
import com.iafenvoy.iceandfire.entity.ai.SeaSerpentAIMeleeJumpGoal;
import com.iafenvoy.iceandfire.entity.ai.SeaSerpentAIRandomSwimmingGoal;
import com.iafenvoy.iceandfire.entity.ai.SeaSerpentPathNavigatorGoal;
import com.iafenvoy.iceandfire.entity.util.IAnimalFear;
import com.iafenvoy.iceandfire.entity.util.IHasCustomizableAttributes;
import com.iafenvoy.iceandfire.entity.util.IMultipartEntity;
import com.iafenvoy.iceandfire.entity.util.IVillagerFear;
import com.iafenvoy.iceandfire.entity.util.IafEntityUtil;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafRegistries;
import com.iafenvoy.iceandfire.registry.IafSeaSerpentTypes;
import com.iafenvoy.iceandfire.registry.IafSounds;
import com.iafenvoy.uranus.animation.Animation;
import com.iafenvoy.uranus.animation.AnimationHandler;
import com.iafenvoy.uranus.animation.IAnimatedEntity;
import com.iafenvoy.uranus.util.RandomHelper;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class SeaSerpentEntity extends Animal implements IAnimatedEntity, IMultipartEntity, IVillagerFear, IAnimalFear, IHasCustomizableAttributes {
   public static final Animation ANIMATION_BITE = Animation.create(15);
   public static final Animation ANIMATION_SPEAK = Animation.create(15);
   public static final Animation ANIMATION_ROAR = Animation.create(40);
   public static final int TIME_BETWEEN_ROARS = 300;
   private static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(SeaSerpentEntity.class, EntityDataSerializers.STRING);
   private static final EntityDataAccessor<Float> SCALE = SynchedEntityData.defineId(SeaSerpentEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Boolean> JUMPING = SynchedEntityData.defineId(SeaSerpentEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> BREATHING = SynchedEntityData.defineId(SeaSerpentEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> ANCIENT = SynchedEntityData.defineId(SeaSerpentEntity.class, EntityDataSerializers.BOOLEAN);
   private final float[] tailYaw = new float[5];
   private final float[] prevTailYaw = new float[5];
   private final float[] tailPitch = new float[5];
   private final float[] prevTailPitch = new float[5];
   public int swimCycle;
   public float jumpProgress = 0.0F;
   public float wantJumpProgress = 0.0F;
   public float jumpRot = 0.0F;
   public float prevJumpRot = 0.0F;
   public float breathProgress = 0.0F;
   public boolean attackDecision = false;
   public int jumpCooldown = 0;
   private int animationTick;
   private Animation currentAnimation;
   private SlowPartEntity[] segments = new SlowPartEntity[9];
   private float lastScale;
   private boolean isLandNavigator;
   private boolean changedSwimBehavior = false;
   private int ticksSinceRoar = 0;
   private boolean isBreathing;

   public SeaSerpentEntity(EntityType<SeaSerpentEntity> t, Level worldIn) {
      super(t, worldIn);
      this.switchNavigator(false);
      this.noCulling = true;
      this.lastScale = 0.0F;
      this.setPathfindingMalus(PathType.WATER, 0.0F);
   }

   public static boolean isWaterBlock(Level world, BlockPos pos) {
      return world.getFluidState(pos).is(FluidTags.WATER);
   }

   public static Builder bakeAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, (Double)IafCommonConfig.INSTANCE.seaSerpent.baseHealth.getValue())
         .add(Attributes.MOVEMENT_SPEED, 0.15)
         .add(Attributes.ATTACK_DAMAGE, 1.0)
         .add(Attributes.FOLLOW_RANGE, Math.min(2048, (Integer)IafCommonConfig.INSTANCE.dragon.targetSearchLength.getValue()))
         .add(Attributes.ARMOR, 3.0);
   }

   private static boolean canBreak(Block block) {
      return block instanceof CropBlock
         || block instanceof SaplingBlock
         || block instanceof FlowerBlock
         || block == Blocks.DEAD_BUSH
         || block == Blocks.LILY_PAD
         || block == Blocks.RED_MUSHROOM
         || block == Blocks.BROWN_MUSHROOM
         || block == Blocks.NETHER_WART
         || block == Blocks.TALL_GRASS;
   }

   public SoundSource getSoundSource() {
      return SoundSource.HOSTILE;
   }

   public boolean shouldRender(double x, double y, double z) {
      return true;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new SeaSerpentAIGetInWaterGoal(this));
      this.goalSelector.addGoal(1, new SeaSerpentAIMeleeJumpGoal(this));
      this.goalSelector.addGoal(1, new SeaSerpentAIAttackMeleeGoal(this, 1.0, true));
      this.goalSelector.addGoal(2, new SeaSerpentAIRandomSwimmingGoal(this, 1.0, 2));
      this.goalSelector.addGoal(3, new SeaSerpentAIJumpGoal(this, 4));
      this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{MultipartPartEntity.class}).setAlertOthers(new Class[0]));
      this.targetSelector
         .addGoal(
            2,
            new FlyingAITargetGoal<LivingEntity>(
               this,
               LivingEntity.class,
               150,
               false,
               false,
               entity1 -> !(entity1 instanceof SeaSerpentEntity) && DragonUtils.isAlive(entity1) && entity1.isInWaterOrBubble()
            )
         );
      this.targetSelector
         .addGoal(
            3, new FlyingAITargetGoal(this, Player.class, 0, false, false, entity -> !(entity instanceof SeaSerpentEntity) && DragonUtils.isAlive(entity))
         );
   }

   public int getBaseExperienceReward() {
      return this.isAncient() ? 30 : 15;
   }

   public boolean isFood(ItemStack stack) {
      return false;
   }

   public void pushEntities() {
      List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expandTowards(0.2F, 0.0, 0.2F));
      entities.stream().filter(entity -> !(entity instanceof MultipartPartEntity) && entity.isPushable()).forEach(entity -> entity.push(this));
   }

   private void switchNavigator(boolean onLand) {
      if (onLand) {
         this.moveControl = new MoveControl(this);
         this.navigation = new GroundPathNavigation(this, this.level());
         this.navigation.setCanFloat(true);
         this.isLandNavigator = true;
      } else {
         this.moveControl = new SeaSerpentEntity.SwimmingMoveHelper(this);
         this.navigation = new SeaSerpentPathNavigatorGoal(this, this.level());
         this.isLandNavigator = false;
      }
   }

   public boolean isDirectPathBetweenPoints(BlockPos pos) {
      Vec3 vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
      Vec3 bector3d1 = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
      return this.level().clip(new ClipContext(vector3d, bector3d1, net.minecraft.world.level.ClipContext.Block.COLLIDER, Fluid.NONE, this)).getType()
         == Type.MISS;
   }

   @Override
   public void setConfigurableAttributes() {
      this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((Double)IafCommonConfig.INSTANCE.seaSerpent.baseHealth.getValue());
      this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(Math.min(2048, (Integer)IafCommonConfig.INSTANCE.dragon.targetSearchLength.getValue()));
      this.updateAttributes();
   }

   public void updateScale(float scale) {
      this.segments = new SlowPartEntity[9];

      for (int i = 0; i < this.segments.length; i++) {
         if (this.segments[i] == null || this.segments[i].isRemoved()) {
            if (i > 3) {
               this.segments[i] = new SlowPartEntity(this, 0.5F * (i - 3), 180.0F, 0.0F, 0.5F, 0.5F, 1.0F);
            } else {
               this.segments[i] = new SlowPartEntity(this, -0.4F * (i + 1), 180.0F, 0.0F, 0.45F, 0.4F, 1.0F);
            }

            this.level().addFreshEntity(this.segments[i]);
         }

         this.segments[i].updateScale(scale);
      }
   }

   public void onUpdateParts() {
      if (!this.isRemoved()) {
         for (MultipartPartEntity entity : this.segments) {
            entity.copyPosition(this);
            IafEntityUtil.updatePart(entity, this);
         }
      }
   }

   private void removeParts() {
      for (MultipartPartEntity entity : this.segments) {
         if (entity != null) {
            entity.remove(RemovalReason.DISCARDED);
         }
      }
   }

   public void remove(RemovalReason reason) {
      this.removeParts();
      super.remove(reason);
   }

   public EntityDimensions getDimensions(Pose poseIn) {
      return this.getType().getDimensions().scale(this.getAgeScale());
   }

   public float getAgeScale() {
      return this.getSeaSerpentScale();
   }

   public void refreshDimensions() {
      super.refreshDimensions();
      float scale = this.getSeaSerpentScale();
      if (scale != this.lastScale) {
         this.updateScale(this.getSeaSerpentScale());
      }

      this.lastScale = scale;
   }

   public boolean doHurtTarget(Entity entityIn) {
      if (this.getAnimation() != ANIMATION_BITE) {
         this.setAnimation(ANIMATION_BITE);
         return true;
      } else {
         return false;
      }
   }

   public void tick() {
      super.tick();
      if (this.jumpCooldown > 0) {
         this.jumpCooldown--;
      }

      this.refreshDimensions();
      this.onUpdateParts();
      if (this.isInWater()) {
         this.spawnParticlesAroundEntity(this, (int)this.getSeaSerpentScale());
      }

      if (!this.level().isClientSide && this.level().getDifficulty() == Difficulty.PEACEFUL) {
         this.remove(RemovalReason.DISCARDED);
      }

      if (this.getTarget() != null && !this.getTarget().isAlive()) {
         this.setTarget(null);
      }

      System.arraycopy(this.tailYaw, 0, this.prevTailYaw, 0, this.tailYaw.length);
      System.arraycopy(this.tailPitch, 0, this.prevTailPitch, 0, this.tailPitch.length);
      this.tailYaw[0] = this.yBodyRot;
      this.tailPitch[0] = this.getXRot();
      System.arraycopy(this.prevTailYaw, 0, this.tailYaw, 1, this.tailYaw.length - 1);
      System.arraycopy(this.prevTailPitch, 0, this.tailPitch, 1, this.tailPitch.length - 1);
      AnimationHandler.INSTANCE.updateAnimations(this);
      this.setAirSupply(this.getMaxAirSupply());
   }

   public float getPieceYaw(int index, float partialTicks) {
      return index < this.segments.length && index >= 0 ? this.prevTailYaw[index] + (this.tailYaw[index] - this.prevTailYaw[index]) * partialTicks : 0.0F;
   }

   public float getPiecePitch(int index, float partialTicks) {
      return index < this.segments.length && index >= 0 ? this.prevTailPitch[index] + (this.tailPitch[index] - this.prevTailPitch[index]) * partialTicks : 0.0F;
   }

   private void spawnParticlesAroundEntity(Entity entity, int count) {
      for (int i = 0; i < count; i++) {
         int x = (int)Math.round(entity.getX() + this.random.nextFloat() * entity.getBbWidth() * 2.0F - entity.getBbWidth());
         int y = (int)Math.round(entity.getY() + 0.5 + this.random.nextFloat() * entity.getBbHeight());
         int z = (int)Math.round(entity.getZ() + this.random.nextFloat() * entity.getBbWidth() * 2.0F - entity.getBbWidth());
         if (this.level().getBlockState(new BlockPos(x, y, z)).is(Blocks.WATER)) {
            this.level().addParticle(ParticleTypes.BUBBLE, x, y, z, 0.0, 0.0, 0.0);
         }
      }
   }

   private void spawnSlamParticles() {
      for (int i = 0; i < this.getSeaSerpentScale() * 3.0F; i++) {
         for (int i1 = 0; i1 < 5; i1++) {
            double motionX = this.getRandom().nextGaussian() * 0.07;
            double motionY = this.getRandom().nextGaussian() * 0.07;
            double motionZ = this.getRandom().nextGaussian() * 0.07;
            float radius = 1.25F * this.getSeaSerpentScale();
            float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1 * 1.0F;
            double extraX = radius * Mth.sin((float)(Math.PI + angle));
            double extraY = 0.8F;
            double extraZ = radius * Mth.cos(angle);
            if (this.level().isClientSide) {
               this.level()
                  .addParticle(ParticleTypes.BUBBLE, true, this.getX() + extraX, this.getY() + extraY, this.getZ() + extraZ, motionX, motionY, motionZ);
            }
         }
      }
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(VARIANT, IafSeaSerpentTypes.BLUE.getName());
      builder.define(SCALE, 0.0F);
      builder.define(JUMPING, false);
      builder.define(BREATHING, false);
      builder.define(ANCIENT, false);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putString("Variant", this.getVariant());
      compound.putInt("TicksSinceRoar", this.ticksSinceRoar);
      compound.putInt("JumpCooldown", this.jumpCooldown);
      compound.putFloat("Scale", this.getSeaSerpentScale());
      compound.putBoolean("JumpingOutOfWater", this.isJumpingOutOfWater());
      compound.putBoolean("AttackDecision", this.attackDecision);
      compound.putBoolean("Breathing", this.isBreathing());
      compound.putBoolean("Ancient", this.isAncient());
      this.removeParts();
      this.lastScale = 0.0F;
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("Variant") && compound.get("Variant").getId() == 8) {
         this.setVariant(compound.getString("Variant"));
      } else {
         this.setVariant(SeaSerpentType.values().get(compound.getInt("Variant")).getName());
      }

      this.ticksSinceRoar = compound.getInt("TicksSinceRoar");
      this.jumpCooldown = compound.getInt("JumpCooldown");
      this.setSeaSerpentScale(compound.getFloat("Scale"));
      this.setJumpingOutOfWater(compound.getBoolean("JumpingOutOfWater"));
      this.attackDecision = compound.getBoolean("AttackDecision");
      this.setBreathing(compound.getBoolean("Breathing"));
      this.setAncient(compound.getBoolean("Ancient"));
      this.setConfigurableAttributes();
   }

   private void updateAttributes() {
      this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(Math.min(0.25, 0.15 * this.getSeaSerpentScale() * this.getAncientModifier()));
      this.getAttribute(Attributes.ATTACK_DAMAGE)
         .setBaseValue(
            Math.max(4.0, (Double)IafCommonConfig.INSTANCE.seaSerpent.attackDamage.getValue() * this.getSeaSerpentScale() * this.getAncientModifier())
         );
      this.getAttribute(Attributes.MAX_HEALTH)
         .setBaseValue(
            Math.max(10.0, (Double)IafCommonConfig.INSTANCE.seaSerpent.baseHealth.getValue() * this.getSeaSerpentScale() * this.getAncientModifier())
         );
      this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(Math.min(2048, (Integer)IafCommonConfig.INSTANCE.dragon.targetSearchLength.getValue()));
      this.heal(30.0F * this.getSeaSerpentScale());
   }

   private float getAncientModifier() {
      return this.isAncient() ? 1.5F : 1.0F;
   }

   public float getSeaSerpentScale() {
      float scale = (Float)this.entityData.get(SCALE);
      if (scale == 0.0F) {
         scale = (float)RandomHelper.nextDouble(1.0, 5.0);
         this.setSeaSerpentScale(scale);
      }

      return scale;
   }

   private void setSeaSerpentScale(float scale) {
      this.entityData.set(SCALE, scale);
   }

   public String getVariant() {
      return (String)this.entityData.get(VARIANT);
   }

   public void setVariant(String variant) {
      this.entityData.set(VARIANT, variant);
   }

   public boolean isJumpingOutOfWater() {
      return (Boolean)this.entityData.get(JUMPING);
   }

   public void setJumpingOutOfWater(boolean jump) {
      this.entityData.set(JUMPING, jump);
   }

   public boolean isAncient() {
      return (Boolean)this.entityData.get(ANCIENT);
   }

   public void setAncient(boolean ancient) {
      this.entityData.set(ANCIENT, ancient);
   }

   public boolean isBreathing() {
      if (this.level().isClientSide) {
         boolean breathing = (Boolean)this.entityData.get(BREATHING);
         this.isBreathing = breathing;
         return breathing;
      } else {
         return this.isBreathing;
      }
   }

   public void setBreathing(boolean breathing) {
      this.entityData.set(BREATHING, breathing);
      if (!this.level().isClientSide) {
         this.isBreathing = breathing;
      }
   }

   protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
   }

   public void aiStep() {
      super.aiStep();
      if (!this.level().isClientSide && this.level().getDifficulty() == Difficulty.PEACEFUL && this.getTarget() instanceof Player) {
         this.setTarget(null);
      }

      boolean breathing = this.isBreathing() && this.getAnimation() != ANIMATION_BITE && this.getAnimation() != ANIMATION_ROAR;
      boolean jumping = !this.isInWater() && !this.onGround() && this.getDeltaMovement().y >= 0.0;
      boolean wantJumping = false;
      boolean ground = !this.isInWater() && this.onGround();
      boolean prevJumping = this.isJumpingOutOfWater();
      this.ticksSinceRoar++;
      this.jumpCooldown++;
      this.prevJumpRot = this.jumpRot;
      if (this.ticksSinceRoar > 300 && this.isAtSurface() && this.getAnimation() != ANIMATION_BITE && this.jumpProgress == 0.0F && !this.isJumpingOutOfWater()) {
         this.setAnimation(ANIMATION_ROAR);
         this.ticksSinceRoar = 0;
      }

      if (this.getAnimation() == ANIMATION_ROAR && this.getAnimationTick() == 1) {
         this.playSound((SoundEvent)IafSounds.SEA_SERPENT_ROAR.get(), this.getSoundVolume() + 1.0F, 1.0F);
      }

      if (this.getAnimation() == ANIMATION_BITE && this.getAnimationTick() == 5) {
         this.playSound((SoundEvent)IafSounds.SEA_SERPENT_BITE.get(), this.getSoundVolume(), 1.0F);
      }

      if (this.isJumpingOutOfWater() && isWaterBlock(this.level(), this.blockPosition().above(2))) {
         this.setJumpingOutOfWater(false);
      }

      if (this.swimCycle < 38) {
         this.swimCycle += 2;
      } else {
         this.swimCycle = 0;
      }

      if (breathing && this.breathProgress < 20.0F) {
         this.breathProgress += 0.5F;
      } else if (!breathing && this.breathProgress > 0.0F) {
         this.breathProgress -= 0.5F;
      }

      if (jumping && this.jumpProgress < 10.0F) {
         this.jumpProgress += 0.5F;
      } else if (!jumping && this.jumpProgress > 0.0F) {
         this.jumpProgress -= 0.5F;
      }

      if (!wantJumping && this.wantJumpProgress > 0.0F) {
         this.wantJumpProgress -= 2.0F;
      }

      if (this.isJumpingOutOfWater() && this.jumpRot < 1.0F) {
         this.jumpRot += 0.1F;
      } else if (!this.isJumpingOutOfWater() && this.jumpRot > 0.0F) {
         this.jumpRot -= 0.1F;
      }

      if (prevJumping && !this.isJumpingOutOfWater()) {
         this.playSound((SoundEvent)IafSounds.SEA_SERPENT_SPLASH.get(), 5.0F, 0.75F);
         this.spawnSlamParticles();
         this.doSplashDamage();
      }

      if (!ground && this.isLandNavigator) {
         this.switchNavigator(false);
      }

      if (ground && !this.isLandNavigator) {
         this.switchNavigator(true);
      }

      this.setXRot(Mth.clamp((float)this.getDeltaMovement().y * 20.0F, -90.0F, 90.0F));
      if (this.changedSwimBehavior) {
         this.changedSwimBehavior = false;
      }

      if (!this.level().isClientSide) {
         if (this.attackDecision) {
            this.setBreathing(false);
         }

         if (this.getTarget() == null || this.getAnimation() == ANIMATION_ROAR) {
            this.setBreathing(false);
         } else if (!this.attackDecision) {
            if (!this.getTarget().isInWater()
               || !this.hasLineOfSight(this.getTarget())
               || this.distanceTo(this.getTarget()) < 30.0F * this.getSeaSerpentScale()) {
               this.attackDecision = true;
            }

            if (!this.attackDecision) {
               this.shoot(this.getTarget());
            }
         } else if (this.distanceToSqr(this.getTarget()) > 200.0F * this.getSeaSerpentScale()) {
            this.attackDecision = false;
         }
      }

      if (this.getAnimation() == ANIMATION_BITE
         && this.getTarget() != null
         && (this.isTouchingMob(this.getTarget()) || this.distanceToSqr(this.getTarget()) < 50.0)) {
         this.hurtMob(this.getTarget());
      }

      this.breakBlock();
      if (!this.level().isClientSide && this.isPassenger() && this.getRootVehicle() instanceof Boat boat) {
         boat.remove(RemovalReason.KILLED);
         this.stopRiding();
      }
   }

   private boolean isAtSurface() {
      BlockPos pos = this.blockPosition();
      return isWaterBlock(this.level(), pos.below()) && !isWaterBlock(this.level(), pos.above());
   }

   private void doSplashDamage() {
      double getWidth = 2.0 * this.getSeaSerpentScale();

      for (Entity entity : this.level()
         .getEntities(
            this,
            this.getBoundingBox().inflate(getWidth, getWidth * 0.5, getWidth),
            entityx -> entityx instanceof LivingEntity living && !(entityx instanceof SeaSerpentEntity) && DragonUtils.isAlive(living)
         )) {
         if (entity instanceof LivingEntity && DragonUtils.isAlive((LivingEntity)entity)) {
            entity.hurt(this.level().damageSources().mobAttack(this), (int)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
            this.destroyBoat(entity);
            double xRatio = this.getX() - entity.getX();
            double zRatio = this.getZ() - entity.getZ();
            float f = Mth.sqrt((float)(xRatio * xRatio + zRatio * zRatio));
            float strength = 0.3F * this.getSeaSerpentScale();
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.5, 1.0, 0.5));
            entity.setDeltaMovement(entity.getDeltaMovement().add(xRatio / f * strength, strength, zRatio / f * strength));
         }
      }
   }

   public void destroyBoat(Entity sailor) {
      if (sailor.getVehicle() != null && sailor.getVehicle() instanceof Boat boat && !this.level().isClientSide) {
         boat.remove(RemovalReason.KILLED);
         if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            for (int i = 0; i < 3; i++) {
               boat.spawnAtLocation(new ItemStack(boat.getVariant().getPlanks().asItem()), 0.0F);
            }

            for (int j = 0; j < 2; j++) {
               boat.spawnAtLocation(new ItemStack(Items.STICK));
            }
         }
      }
   }

   private boolean isPreyAtSurface() {
      if (this.getTarget() != null) {
         BlockPos pos = this.getTarget().blockPosition();
         return !isWaterBlock(this.level(), pos.above((int)Math.ceil(this.getTarget().getBbHeight())));
      } else {
         return false;
      }
   }

   private void hurtMob(LivingEntity entity) {
      if (this.getAnimation() == ANIMATION_BITE && entity != null && this.getAnimationTick() == 6) {
         this.getTarget().hurt(this.level().damageSources().mobAttack(this), (int)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
         this.attackDecision = this.getRandom().nextBoolean();
      }
   }

   public void moveJumping() {
      float velocity = 0.5F;
      double x = -Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)) * Mth.cos(this.getXRot() * (float) (Math.PI / 180.0));
      double z = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)) * Mth.cos(this.getXRot() * (float) (Math.PI / 180.0));
      float f = Mth.sqrt((float)(x * x + z * z));
      x /= f;
      z /= f;
      x *= velocity;
      z *= velocity;
      this.setDeltaMovement(x, this.getDeltaMovement().y, z);
   }

   public boolean isTouchingMob(Entity entity) {
      if (this.getBoundingBox().expandTowards(1.0, 1.0, 1.0).intersects(entity.getBoundingBox())) {
         return true;
      }

      for (Entity segment : this.segments) {
         if (segment != null && segment.getBoundingBox().expandTowards(1.0, 1.0, 1.0).intersects(entity.getBoundingBox())) {
            return true;
         }
      }

      return false;
   }

   public void breakBlock() {
      if ((Boolean)IafCommonConfig.INSTANCE.seaSerpent.griefing.getValue()) {
         for (int a = (int)Math.round(this.getBoundingBox().minX) - 2; a <= (int)Math.round(this.getBoundingBox().maxX) + 2; a++) {
            for (int b = (int)Math.round(this.getBoundingBox().minY) - 1; b <= (int)Math.round(this.getBoundingBox().maxY) + 2 && b <= 127; b++) {
               for (int c = (int)Math.round(this.getBoundingBox().minZ) - 2; c <= (int)Math.round(this.getBoundingBox().maxZ) + 2; c++) {
                  BlockPos pos = new BlockPos(a, b, c);
                  BlockState state = this.level().getBlockState(pos);
                  FluidState fluidState = this.level().getFluidState(pos);
                  Block block = state.getBlock();
                  if (!state.isAir()
                     && !state.getShape(this.level(), pos).isEmpty()
                     && (canBreak(state.getBlock()) || state.getBlock() instanceof LeavesBlock)
                     && fluidState.isEmpty()
                     && block != Blocks.AIR
                     && !this.level().isClientSide) {
                     this.level().destroyBlock(pos, true);
                  }
               }
            }
         }
      }
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn) {
      spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn);
      this.setVariant(((SeaSerpentType)RandomHelper.randomOne(SeaSerpentType.values())).getName());
      boolean ancient = this.getRandom().nextInt(16) == 1;
      if (ancient) {
         this.setAncient(true);
         this.setSeaSerpentScale(6.0F + this.getRandom().nextFloat() * 3.0F);
      } else {
         this.setSeaSerpentScale(1.5F + this.getRandom().nextFloat() * 4.0F);
      }

      this.updateAttributes();
      return spawnDataIn;
   }

   public void onWorldSpawn(RandomSource random) {
      this.setVariant(((SeaSerpentType)RandomHelper.randomOne(SeaSerpentType.values())).getName());
      boolean ancient = random.nextInt(15) == 1;
      if (ancient) {
         this.setAncient(true);
         this.setSeaSerpentScale(6.0F + random.nextFloat() * 3.0F);
      } else {
         this.setSeaSerpentScale(1.5F + random.nextFloat() * 4.0F);
      }

      this.updateAttributes();
   }

   public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
      return null;
   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int tick) {
      this.animationTick = tick;
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }

   public Animation[] getAnimations() {
      return new Animation[]{ANIMATION_BITE, ANIMATION_ROAR, ANIMATION_SPEAK};
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)IafSounds.SEA_SERPENT_IDLE.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)IafSounds.SEA_SERPENT_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)IafSounds.SEA_SERPENT_DIE.get();
   }

   public void playAmbientSound() {
      if (this.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
         this.setAnimation(ANIMATION_SPEAK);
      }

      super.playAmbientSound();
   }

   protected void playHurtSound(DamageSource source) {
      if (this.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
         this.setAnimation(ANIMATION_SPEAK);
      }

      super.playHurtSound(source);
   }

   @Override
   public boolean shouldAnimalsFear(Entity entity) {
      return true;
   }

   public boolean isBlinking() {
      return this.tickCount % 50 > 43;
   }

   private void shoot(LivingEntity entity) {
      if (!this.attackDecision) {
         if (!this.isInWater()) {
            this.setBreathing(false);
            this.attackDecision = true;
         }

         if (this.isBreathing()) {
            if (this.tickCount % 40 == 0) {
               this.playSound((SoundEvent)IafSounds.SEA_SERPENT_BREATH.get(), 4.0F, 1.0F);
            }

            if (this.tickCount % 10 == 0) {
               this.setYRot(this.yBodyRot);
               float f1 = 0.0F;
               float f2 = 0.0F;
               float f3 = 0.0F;
               float headPosX = f1
                  + (float)(this.segments[0].getX() + 1.3F * this.getSeaSerpentScale() * Mth.cos((float)((this.getYRot() + 90.0F) * Math.PI / 180.0)));
               float headPosZ = f2
                  + (float)(this.segments[0].getZ() + 1.3F * this.getSeaSerpentScale() * Mth.sin((float)((this.getYRot() + 90.0F) * Math.PI / 180.0)));
               float headPosY = f3 + (float)(this.segments[0].getY() + 0.2F * this.getSeaSerpentScale());
               double d2 = entity.getX() - headPosX;
               double d3 = entity.getY() - headPosY;
               double d4 = entity.getZ() - headPosZ;
               float inaccuracy = 1.0F;
               d2 += this.random.nextGaussian() * 0.0075F * inaccuracy;
               d3 += this.random.nextGaussian() * 0.0075F * inaccuracy;
               d4 += this.random.nextGaussian() * 0.0075F * inaccuracy;
               SeaSerpentBubblesEntity entitylargefireball = new SeaSerpentBubblesEntity(
                  (EntityType<? extends Fireball>)IafEntities.SEA_SERPENT_BUBBLES.get(), this.level(), this, d2, d3, d4
               );
               entitylargefireball.setPos(headPosX, headPosY, headPosZ);
               if (!this.level().isClientSide) {
                  this.level().addFreshEntity(entitylargefireball);
               }

               if (!entity.isAlive()) {
                  this.setBreathing(false);
                  this.attackDecision = this.getRandom().nextBoolean();
               }
            }
         } else {
            this.setBreathing(true);
         }
      }

      this.lookAt(entity, 360.0F, 360.0F);
   }

   public SeaSerpentType getEnum() {
      return (SeaSerpentType)IafRegistries.SEA_SERPENT_TYPE.get(IceAndFire.id(this.getVariant()));
   }

   public void travel(Vec3 vec) {
      if (this.isEffectiveAi() && this.isInWater()) {
         this.moveRelative(this.getSpeed(), vec);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
         if (this.getTarget() == null) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
         }
      } else {
         super.travel(vec);
      }
   }

   public boolean killedEntity(ServerLevel world, LivingEntity entity) {
      this.attackDecision = this.getRandom().nextBoolean();
      return this.attackDecision;
   }

   public boolean isPersistenceRequired() {
      return true;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public int getMaxFallDistance() {
      return 1000;
   }

   public boolean shouldUseJumpAttack(LivingEntity attackTarget) {
      return !attackTarget.isInWater() || this.isPreyAtSurface();
   }

   public boolean isInvulnerableTo(DamageSource source) {
      DamageSources damageSources = this.level().damageSources();
      return source == damageSources.fall()
         || source == damageSources.drown()
         || source == damageSources.inWall()
         || source.getEntity() != null && source == damageSources.fallingBlock(source.getEntity())
         || source == damageSources.lava()
         || source.is(DamageTypes.IN_FIRE)
         || super.isInvulnerableTo(source);
   }

   public static class SwimmingMoveHelper extends MoveControl {
      private final SeaSerpentEntity dolphin;

      public SwimmingMoveHelper(SeaSerpentEntity dolphinIn) {
         super(dolphinIn);
         this.dolphin = dolphinIn;
      }

      public void tick() {
         if (this.dolphin.isInWater()) {
            this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add(0.0, 0.005, 0.0));
         }

         if (this.operation == Operation.MOVE_TO && !this.dolphin.getNavigation().isDone()) {
            double d0 = this.wantedX - this.dolphin.getX();
            double d1 = this.wantedY - this.dolphin.getY();
            double d2 = this.wantedZ - this.dolphin.getZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d3 < 2.5000003E-7F) {
               this.mob.setZza(0.0F);
            } else {
               float f = (float)(Mth.atan2(d2, d0) * 180.0F / (float)Math.PI) - 90.0F;
               this.dolphin.setYRot(this.rotlerp(this.dolphin.getYRot(), f, 10.0F));
               this.dolphin.yBodyRot = this.dolphin.getYRot();
               this.dolphin.yHeadRot = this.dolphin.getYRot();
               float f1 = (float)(this.speedModifier * 3.0);
               if (this.dolphin.isInWater()) {
                  this.dolphin.setSpeed(f1 * 0.02F);
                  float f2 = -((float)(Mth.atan2(d1, Mth.sqrt((float)(d0 * d0 + d2 * d2))) * 180.0F / (float)Math.PI));
                  f2 = Mth.clamp(Mth.wrapDegrees(f2), -85.0F, 85.0F);
                  this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add(0.0, this.dolphin.getSpeed() * d1 * 0.6, 0.0));
                  this.dolphin.setXRot(this.rotlerp(this.dolphin.getXRot(), f2, 1.0F));
                  float f3 = Mth.cos(this.dolphin.getXRot() * (float) (Math.PI / 180.0));
                  float f4 = Mth.sin(this.dolphin.getXRot() * (float) (Math.PI / 180.0));
                  this.dolphin.zza = f3 * f1;
                  this.dolphin.yya = -f4 * f1;
               } else {
                  this.dolphin.setSpeed(f1 * 0.1F);
               }
            }
         } else {
            this.dolphin.setSpeed(0.0F);
            this.dolphin.setXxa(0.0F);
            this.dolphin.setYya(0.0F);
            this.dolphin.setZza(0.0F);
         }
      }
   }
}
