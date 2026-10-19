package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.ai.AmphithereAIAttackMeleeGoal;
import com.iafenvoy.iceandfire.entity.ai.AmphithereAIFleePlayerGoal;
import com.iafenvoy.iceandfire.entity.ai.AmphithereAIFollowOwnerGoal;
import com.iafenvoy.iceandfire.entity.ai.AmphithereAITargetItemsGoal;
import com.iafenvoy.iceandfire.entity.ai.EntityAIWatchClosestIgnoreRiderGoal;
import com.iafenvoy.iceandfire.entity.pathfinding.FlyingCreatureNavigation;
import com.iafenvoy.iceandfire.entity.util.ICustomMoveController;
import com.iafenvoy.iceandfire.entity.util.IFlapable;
import com.iafenvoy.iceandfire.entity.util.IFlyingMount;
import com.iafenvoy.iceandfire.entity.util.IHasCustomizableAttributes;
import com.iafenvoy.iceandfire.entity.util.ISyncMount;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import com.iafenvoy.iceandfire.entity.util.dragon.IDragonFlute;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.iceandfire.registry.IafSounds;
import com.iafenvoy.iceandfire.registry.tag.IafItemTags;
import com.iafenvoy.iceandfire.render.model.IFChainBuffer;
import com.iafenvoy.uranus.animation.Animation;
import com.iafenvoy.uranus.animation.AnimationHandler;
import com.iafenvoy.uranus.animation.IAnimatedEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class AmphithereEntity
   extends TamableAnimal
   implements ISyncMount,
   IAnimatedEntity,
   IFlapable,
   IDragonFlute,
   IFlyingMount,
   IHasCustomizableAttributes,
   ICustomMoveController {
   public static final Animation ANIMATION_BITE = Animation.create(15);
   public static final Animation ANIMATION_BITE_RIDER = Animation.create(15);
   public static final Animation ANIMATION_WING_BLAST = Animation.create(30);
   public static final Animation ANIMATION_TAIL_WHIP = Animation.create(30);
   public static final Animation ANIMATION_SPEAK = Animation.create(10);
   private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(AmphithereEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(AmphithereEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> FLAP_TICKS = SynchedEntityData.defineId(AmphithereEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Byte> CONTROL_STATE = SynchedEntityData.defineId(AmphithereEntity.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(AmphithereEntity.class, EntityDataSerializers.INT);
   public float flapProgress;
   public float groundProgress = 0.0F;
   public float sitProgress = 0.0F;
   public float diveProgress = 0.0F;
   public IFChainBuffer roll_buffer;
   public IFChainBuffer tail_buffer;
   public IFChainBuffer pitch_buffer;
   public BlockPos orbitPos = null;
   public float orbitRadius = 0.0F;
   public boolean isFallen;
   public BlockPos homePos;
   public boolean hasHomePosition = false;
   protected AmphithereEntity.FlightBehavior flightBehavior = AmphithereEntity.FlightBehavior.WANDER;
   protected int ticksCircling = 0;
   private int animationTick;
   private Animation currentAnimation;
   private int flapTicks = 0;
   private int flightCooldown = 0;
   private int ticksFlying = 0;
   private boolean isFlying;
   private boolean changedFlightBehavior = false;
   private int ticksStill = 0;
   private int ridingTime = 0;
   private boolean isSitting;
   private int navigatorType = 0;

   public AmphithereEntity(EntityType<AmphithereEntity> type, Level worldIn) {
      super(type, worldIn);
      if (worldIn.isClientSide) {
         this.roll_buffer = new IFChainBuffer();
         this.pitch_buffer = new IFChainBuffer();
         this.tail_buffer = new IFChainBuffer();
      }

      this.switchNavigator(0);
   }

   public static BlockPos getPositionRelativetoGround(Entity entity, Level world, int x, int z, RandomSource rand) {
      BlockPos pos = new BlockPos(x, entity.getBlockY(), z);

      for (int yDown = 0; yDown < 6 + rand.nextInt(6); yDown++) {
         if (!world.isEmptyBlock(pos.below(yDown))) {
            return pos.above(yDown);
         }
      }

      return pos;
   }

   public static boolean canAmphithereSpawnOn(
      EntityType<AmphithereEntity> parrotIn, ServerLevelAccessor worldIn, MobSpawnType reason, BlockPos p_223317_3_, RandomSource random
   ) {
      BlockState blockState = worldIn.getBlockState(p_223317_3_.below());
      Block block = blockState.getBlock();
      return blockState.is(BlockTags.LEAVES) || block == Blocks.GRASS_BLOCK || blockState.is(BlockTags.LOGS) || block == Blocks.AIR;
   }

   public static BlockPos getPositionInOrbit(AmphithereEntity entity, Level world, BlockPos orbit, RandomSource rand) {
      float possibleOrbitRadius = entity.orbitRadius + 10.0F;
      float radius = 10.0F;
      if (entity.getCommand() == 2) {
         if (entity.getOwner() != null) {
            orbit = entity.getOwner().blockPosition().above(7);
            radius = 5.0F;
         }
      } else if (entity.hasHomePosition) {
         orbit = entity.homePos.above(30);
         radius = 30.0F;
      }

      float angle = (float) (Math.PI / 180.0) * possibleOrbitRadius;
      double extraX = radius * Mth.sin((float)(Math.PI + angle));
      double extraZ = radius * Mth.cos(angle);
      BlockPos radialPos = BlockPos.containing(orbit.getX() + extraX, orbit.getY(), orbit.getZ() + extraZ);
      entity.orbitRadius = possibleOrbitRadius;
      return radialPos;
   }

   public static Builder bakeAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, (Double)IafCommonConfig.INSTANCE.amphithere.maxHealth.getValue())
         .add(Attributes.MOVEMENT_SPEED, 0.4)
         .add(Attributes.ATTACK_DAMAGE, (Double)IafCommonConfig.INSTANCE.amphithere.attackDamage.getValue())
         .add(Attributes.FLYING_SPEED, (Double)IafCommonConfig.INSTANCE.amphithere.flightSpeed.getValue())
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.STEP_HEIGHT, 1.0);
   }

   public boolean checkSpawnObstruction(LevelReader worldIn) {
      if (worldIn.isUnobstructed(this) && !worldIn.containsAnyLiquid(this.getBoundingBox())) {
         BlockPos blockpos = this.blockPosition();
         if (blockpos.getY() < worldIn.getSeaLevel()) {
            return false;
         }

         BlockState blockstate = worldIn.getBlockState(blockpos.below());
         return blockstate.is(Blocks.GRASS_BLOCK) || blockstate.is(BlockTags.LEAVES);
      } else {
         return false;
      }
   }

   protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
   }

   public float getWalkTargetValue(BlockPos pos) {
      if (this.isFlying()) {
         return this.level().isEmptyBlock(pos) ? 10.0F : 0.0F;
      } else {
         return super.getWalkTargetValue(pos);
      }
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      if (itemstack != null && itemstack.is(IafItemTags.BREED_AMPITHERE)) {
         if (this.getAge() == 0 && !this.isInLove()) {
            this.setOrderedToSit(false);
            this.setInLove(player);
            this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
            if (!player.isCreative()) {
               itemstack.shrink(1);
            }
         }

         return InteractionResult.SUCCESS;
      } else if (itemstack != null && itemstack.is(IafItemTags.HEAL_AMPITHERE) && this.getHealth() < this.getMaxHealth()) {
         this.heal(5.0F);
         this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
         if (!player.isCreative()) {
            itemstack.shrink(1);
         }

         return InteractionResult.SUCCESS;
      } else {
         if (super.mobInteract(player, hand) == InteractionResult.PASS) {
            if (itemstack != null && itemstack.getItem() == IafItems.DRAGON_STAFF.get() && this.isOwnedBy(player)) {
               if (player.isShiftKeyDown()) {
                  this.homePos = this.blockPosition();
                  this.hasHomePosition = true;
                  player.displayClientMessage(
                     Component.translatable("amphithere.command.new_home", new Object[]{this.homePos.getX(), this.homePos.getY(), this.homePos.getZ()}), true
                  );
                  return InteractionResult.SUCCESS;
               }

               return InteractionResult.SUCCESS;
            }

            if (player.isShiftKeyDown() && this.isOwnedBy(player)) {
               if (player.getItemInHand(hand).isEmpty()) {
                  this.setCommand(this.getCommand() + 1);
                  if (this.getCommand() > 2) {
                     this.setCommand(0);
                  }

                  player.displayClientMessage(Component.translatable("amphithere.command." + this.getCommand()), true);
                  this.playSound(SoundEvents.ZOMBIE_INFECT, 1.0F, 1.0F);
                  return InteractionResult.SUCCESS;
               }

               return InteractionResult.SUCCESS;
            }

            if ((!this.isTame() || this.isOwnedBy(player)) && !this.isBaby()) {
               assert itemstack != null;
               if (itemstack.isEmpty()) {
                  player.startRiding(this);
                  return InteractionResult.SUCCESS;
               }
            }
         }

         return super.mobInteract(player, hand);
      }
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(1, new AmphithereAIFollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
      this.goalSelector.addGoal(2, new AmphithereAIAttackMeleeGoal(this, 1.0, true));
      this.goalSelector.addGoal(3, new AmphithereAIFleePlayerGoal(this, 32.0F, 0.8, 1.8));
      this.goalSelector.addGoal(3, new AmphithereEntity.AIFlyWander());
      this.goalSelector.addGoal(3, new AmphithereEntity.AIFlyCircle());
      this.goalSelector.addGoal(3, new AmphithereEntity.AILandWander(this, 1.0));
      this.goalSelector.addGoal(4, new EntityAIWatchClosestIgnoreRiderGoal(this, LivingEntity.class, 6.0F));
      this.goalSelector.addGoal(4, new BreedGoal(this, 1.0));
      this.targetSelector.addGoal(1, new OwnerHurtTargetGoal(this));
      this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
      this.targetSelector.addGoal(3, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(3, new AmphithereAITargetItemsGoal(this, false));
   }

   public boolean isStill() {
      return Math.abs(this.getDeltaMovement().x) < 0.05 && Math.abs(this.getDeltaMovement().z) < 0.05;
   }

   protected void switchNavigator(int navigatorType) {
      if (navigatorType == 0) {
         this.moveControl = new MoveControl(this);
         this.navigation = new WallClimberNavigation(this, this.level());
         this.navigatorType = 0;
      } else if (navigatorType == 1) {
         this.moveControl = new AmphithereEntity.FlyMoveHelper(this);
         this.navigation = new FlyingCreatureNavigation(this, this.level());
         this.navigatorType = 1;
      } else {
         this.moveControl = new FlyingMoveControl(this, 20, false);
         this.navigation = new FlyingCreatureNavigation(this, this.level());
         this.navigatorType = 2;
      }
   }

   public boolean onLeaves() {
      BlockState state = this.level().getBlockState(this.blockPosition().below());
      return state.getBlock() instanceof LeavesBlock;
   }

   public boolean hurt(DamageSource source, float damage) {
      if (!this.isTame() && this.isFlying() && !this.onGround() && source.is(DamageTypeTags.IS_PROJECTILE) && !this.level().isClientSide) {
         this.isFallen = true;
      }

      return source.getEntity() instanceof LivingEntity
            && source.getEntity().isPassengerOfSameVehicle(this)
            && this.isTame()
            && this.isOwnedBy((LivingEntity)source.getEntity())
         ? false
         : super.hurt(source, damage);
   }

   public void positionRider(Entity passenger, MoveFunction callback) {
      super.positionRider(passenger, callback);
      if (this.hasPassenger(passenger) && this.isTame()) {
         this.setYBodyRot(passenger.getYRot() % 360.0F);
         this.setYHeadRot(passenger.getYHeadRot() % 360.0F);
      }

      if (!this.level().isClientSide && !this.isTame() && passenger instanceof Player && this.getAnimation() == NO_ANIMATION && this.random.nextInt(15) == 0) {
         this.setAnimation(ANIMATION_BITE_RIDER);
      }

      if (!this.level().isClientSide && this.getAnimation() == ANIMATION_BITE_RIDER && this.getAnimationTick() == 6 && !this.isTame()) {
         passenger.hurt(this.level().damageSources().mobAttack(this), 1.0F);
      }

      float pitch_forward = this.getXRot() > 0.0F && this.isFlying() ? this.getXRot() / 45.0F * 0.45F : 0.0F;
      float scaled_ground = this.groundProgress * 0.1F;
      float radius = (this.isTame() ? 0.5F : 0.3F) - scaled_ground * 0.5F + pitch_forward;
      float angle = (float) (Math.PI / 180.0) * this.yBodyRot;
      double extraX = radius * Mth.sin((float)(Math.PI + angle));
      double extraZ = radius * Mth.cos(angle);
      passenger.setPos(this.getX() + extraX, this.getY() + 0.7F - scaled_ground * 0.14F + pitch_forward, this.getZ() + extraZ);
   }

   public boolean isFood(ItemStack stack) {
      return stack.is(IafItemTags.BREED_AMPITHERE);
   }

   public void aiStep() {
      super.aiStep();
      if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.getTarget() instanceof Player) {
         this.setTarget(null);
      }

      if (this.isInWater() && this.jumping) {
         this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + 0.1, this.getDeltaMovement().z);
      }

      if (this.isBaby() && this.getTarget() != null) {
         this.setTarget(null);
      }

      if (this.isInLove()) {
         this.setFlying(false);
      }

      if (this.isOrderedToSit() && this.getTarget() != null) {
         this.setTarget(null);
      }

      boolean flapping = this.isFlapping();
      boolean flying = this.isFlying() && this.isOverAir() || this.isOverAir() && !this.onLeaves();
      boolean diving = flying && this.getDeltaMovement().y <= -0.1F || this.isFallen;
      boolean sitting = this.isOrderedToSit() && !this.isFlying();
      boolean notGrounded = flying || this.getAnimation() == ANIMATION_WING_BLAST;
      if (!this.level().isClientSide) {
         if (this.isOrderedToSit() && (this.getCommand() != 1 || this.getControllingPassenger() != null)) {
            this.setOrderedToSit(false);
         }

         if (!this.isOrderedToSit() && this.getCommand() == 1 && this.getControllingPassenger() == null) {
            this.setOrderedToSit(true);
         }

         if (this.isOrderedToSit()) {
            this.getNavigation().stop();
         }

         if (flying) {
            this.ticksFlying++;
         } else {
            this.ticksFlying = 0;
         }
      }

      if (this.isFlying() && this.onGround()) {
         this.setFlying(false);
      }

      if (sitting && this.sitProgress < 20.0F) {
         this.sitProgress += 0.5F;
      } else if (!sitting && this.sitProgress > 0.0F) {
         this.sitProgress -= 0.5F;
      }

      if (this.flightCooldown > 0) {
         this.flightCooldown--;
      }

      if (!this.level().isClientSide) {
         if (this.flightBehavior == AmphithereEntity.FlightBehavior.CIRCLE) {
            this.ticksCircling++;
         } else {
            this.ticksCircling = 0;
         }
      }

      if (this.getUntamedRider() != null && !this.isTame()) {
         this.ridingTime++;
      }

      if (this.getUntamedRider() == null) {
         this.ridingTime = 0;
      }

      if (!this.isTame()
         && this.ridingTime > (Integer)IafCommonConfig.INSTANCE.amphithere.tameTime.getValue()
         && this.getUntamedRider() != null
         && this.getUntamedRider() instanceof Player) {
         this.level().broadcastEntityEvent(this, (byte)45);
         this.tame((Player)this.getUntamedRider());
         if (this.getTarget() == this.getUntamedRider()) {
            this.setTarget(null);
         }
      }

      if (this.isStill()) {
         this.ticksStill++;
      } else {
         this.ticksStill = 0;
      }

      if (!this.isFlying()
         && !this.isBaby()
         && (
            this.onGround() && this.random.nextInt(200) == 0 && this.flightCooldown == 0 && this.getPassengers().isEmpty() && !this.isNoAi() && this.canMove()
               || this.getY() < -1.0
         )) {
         this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + 0.5, this.getDeltaMovement().z);
         this.setFlying(true);
      }

      if (this.getControllingPassenger() != null && this.isFlying() && !this.onGround()) {
         if (this.getControllingPassenger().getXRot() > 25.0F && this.getDeltaMovement().y > -1.0) {
            this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y - 0.1, this.getDeltaMovement().z);
         }

         if (this.getControllingPassenger().getXRot() < -25.0F && this.getDeltaMovement().y < 1.0) {
            this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + 0.1, this.getDeltaMovement().z);
         }
      }

      if (notGrounded && this.groundProgress > 0.0F) {
         this.groundProgress -= 2.0F;
      } else if (!notGrounded && this.groundProgress < 20.0F) {
         this.groundProgress += 2.0F;
      }

      if (diving && this.diveProgress < 20.0F) {
         this.diveProgress++;
      } else if (!diving && this.diveProgress > 0.0F) {
         this.diveProgress--;
      }

      if (this.isFallen && this.flightBehavior != AmphithereEntity.FlightBehavior.NONE) {
         this.flightBehavior = AmphithereEntity.FlightBehavior.NONE;
      }

      if (this.flightBehavior == AmphithereEntity.FlightBehavior.NONE && this.getControllingPassenger() == null && this.isFlying()) {
         this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y - 0.3, this.getDeltaMovement().z);
      }

      if (this.isFlying() && !this.onGround() && this.isFallen && this.getControllingPassenger() == null) {
         this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y - 0.2, this.getDeltaMovement().z);
         this.setXRot(Math.max(this.getXRot() + 5.0F, 75.0F));
      }

      if (this.isFallen && this.onGround()) {
         this.setFlying(false);
         this.flightCooldown = this.isTame() ? 50 : 12000;
         this.isFallen = false;
      }

      if (flying && this.isOverAir()) {
         if (this.getRidingPlayer() == null && this.navigatorType != 1) {
            this.switchNavigator(1);
         }

         if (this.getRidingPlayer() != null && this.navigatorType != 2) {
            this.switchNavigator(2);
         }
      }

      if (!flying && this.navigatorType != 0) {
         this.switchNavigator(0);
      }

      if ((this.hasHomePosition || this.getCommand() == 2) && this.flightBehavior == AmphithereEntity.FlightBehavior.WANDER) {
         this.flightBehavior = AmphithereEntity.FlightBehavior.CIRCLE;
      }

      if (flapping && this.flapProgress < 10.0F) {
         this.flapProgress++;
      } else if (!flapping && this.flapProgress > 0.0F) {
         this.flapProgress--;
      }

      if (this.flapTicks > 0) {
         this.flapTicks--;
      }

      if (this.level().isClientSide) {
         if (!this.onGround()) {
            if (this.isVehicle()) {
               this.roll_buffer.calculateChainFlapBufferHead(40.0F, 1, 2.0F, 0.5F, this);
            } else {
               this.yBodyRot = this.getYRot();
               this.roll_buffer.calculateChainFlapBuffer(70.0F, 1, 2.0F, 0.5F, this);
            }

            this.pitch_buffer.calculateChainPitchBuffer(90.0F, 10, 10.0F, 0.5F, this);
         }

         this.tail_buffer.calculateChainSwingBuffer(70.0F, 20, 5.0F, this);
      }

      if (this.changedFlightBehavior) {
         this.changedFlightBehavior = false;
      }

      if (!flapping && (this.getDeltaMovement().y > 0.15F || this.getDeltaMovement().y > 0.0 && this.tickCount % 200 == 0) && this.isOverAir()) {
         this.flapWings();
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   public boolean isFlapping() {
      return this.flapTicks > 0;
   }

   public int getCommand() {
      return (Integer)this.entityData.get(COMMAND);
   }

   public void setCommand(int command) {
      this.entityData.set(COMMAND, command);
      this.setOrderedToSit(command == 1);
   }

   @Override
   public void flapWings() {
      this.flapTicks = 20;
   }

   public boolean isOrderedToSit() {
      if (this.level().isClientSide) {
         boolean isSitting = ((Byte)this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
         this.isSitting = isSitting;
         return isSitting;
      } else {
         return this.isSitting;
      }
   }

   public void setOrderedToSit(boolean sitting) {
      if (!this.level().isClientSide) {
         this.isSitting = sitting;
      }

      byte b0 = (Byte)this.entityData.get(DATA_FLAGS_ID);
      if (sitting) {
         this.entityData.set(DATA_FLAGS_ID, (byte)(b0 | 1));
      } else {
         this.entityData.set(DATA_FLAGS_ID, (byte)(b0 & -2));
      }
   }

   public LivingEntity getControllingPassenger() {
      for (Entity passenger : this.getPassengers()) {
         if (passenger instanceof Player player
            && this.getTarget() != passenger
            && this.isTame()
            && this.getOwnerUUID() != null
            && this.getOwnerUUID().equals(player.getUUID())) {
            return player;
         }
      }

      return null;
   }

   public Entity getUntamedRider() {
      for (Entity passenger : this.getPassengers()) {
         if (passenger instanceof Player) {
            return passenger;
         }
      }

      return null;
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (this.isTame()) {
         LivingEntity livingentity = this.getOwner();
         if (entityIn == livingentity) {
            return true;
         }

         if (entityIn instanceof TamableAnimal tameable) {
            return tameable.isOwnedBy(livingentity);
         }

         if (livingentity != null) {
            return livingentity.isAlliedTo(entityIn);
         }
      }

      return super.isAlliedTo(entityIn);
   }

   @Override
   public void setConfigurableAttributes() {
      this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((Double)IafCommonConfig.INSTANCE.amphithere.maxHealth.getValue());
      this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((Double)IafCommonConfig.INSTANCE.amphithere.attackDamage.getValue());
      this.getAttribute(Attributes.FLYING_SPEED).setBaseValue((Double)IafCommonConfig.INSTANCE.amphithere.flightSpeed.getValue());
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(VARIANT, 0);
      builder.define(FLYING, false);
      builder.define(FLAP_TICKS, 0);
      builder.define(CONTROL_STATE, (byte)0);
      builder.define(COMMAND, 0);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("Variant", this.getVariant());
      compound.putBoolean("Flying", this.isFlying());
      compound.putInt("FlightCooldown", this.flightCooldown);
      compound.putInt("RidingTime", this.ridingTime);
      compound.putBoolean("HasHomePosition", this.hasHomePosition);
      if (this.homePos != null && this.hasHomePosition) {
         compound.putInt("HomeAreaX", this.homePos.getX());
         compound.putInt("HomeAreaY", this.homePos.getY());
         compound.putInt("HomeAreaZ", this.homePos.getZ());
      }

      compound.putInt("Command", this.getCommand());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setVariant(compound.getInt("Variant"));
      this.setFlying(compound.getBoolean("Flying"));
      this.flightCooldown = compound.getInt("FlightCooldown");
      this.ridingTime = compound.getInt("RidingTime");
      this.hasHomePosition = compound.getBoolean("HasHomePosition");
      if (this.hasHomePosition && compound.getInt("HomeAreaX") != 0 && compound.getInt("HomeAreaY") != 0 && compound.getInt("HomeAreaZ") != 0) {
         this.homePos = new BlockPos(compound.getInt("HomeAreaX"), compound.getInt("HomeAreaY"), compound.getInt("HomeAreaZ"));
      }

      this.setCommand(compound.getInt("Command"));
      this.setConfigurableAttributes();
   }

   public boolean getCanSpawnHere() {
      int i = Mth.floor(this.getX());
      int j = Mth.floor(this.getBoundingBox().minY);
      int k = Mth.floor(this.getZ());
      BlockPos blockpos = new BlockPos(i, j, k);
      Block block = this.level().getBlockState(blockpos.below()).getBlock();
      return this.level().canSeeSkyFromBelowWater(blockpos.above());
   }

   public void tick() {
      super.tick();
      LivingEntity target = this.getTarget();
      if (target != null && this.getAnimation() == ANIMATION_BITE && this.getAnimationTick() == 7) {
         double dist = this.distanceToSqr(target);
         if (dist < 10.0) {
            target.knockback(0.6F, Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)), -Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)));
            target.hurt(this.level().damageSources().mobAttack(this), (int)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
         }
      }

      if (this.getAnimation() == ANIMATION_WING_BLAST && this.getAnimationTick() == 5) {
         this.playSound((SoundEvent)IafSounds.AMPHITHERE_GUST.get(), 1.0F, 1.0F);
      }

      if ((this.getAnimation() == ANIMATION_BITE || this.getAnimation() == ANIMATION_BITE_RIDER) && this.getAnimationTick() == 1) {
         this.playSound((SoundEvent)IafSounds.AMPHITHERE_BITE.get(), 1.0F, 1.0F);
      }

      if (target != null && this.getAnimation() == ANIMATION_WING_BLAST && this.getAnimationTick() > 5 && this.getAnimationTick() < 22) {
         double dist = this.distanceToSqr(target);
         if (dist < 25.0) {
            target.hurt(this.level().damageSources().mobAttack(this), (int)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue() / 2.0F);
            target.hasImpulse = true;
            if (!(this.random.nextDouble() < this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue())) {
               this.hasImpulse = true;
               double d1 = target.getX() - this.getX();

               double d0;
               for (d0 = target.getZ() - this.getZ(); d1 * d1 + d0 * d0 < 1.0E-4; d0 = (Math.random() - Math.random()) * 0.01) {
                  d1 = (Math.random() - Math.random()) * 0.01;
               }

               Vec3 Vector3d = this.getDeltaMovement();
               Vec3 Vector3d1 = new Vec3(d0, 0.0, d1).normalize().scale(0.5);
               this.setDeltaMovement(
                  Vector3d.x / 2.0 - Vector3d1.x, this.onGround() ? Math.min(0.4, Vector3d.y / 2.0 + 0.5) : Vector3d.y, Vector3d.z / 2.0 - Vector3d1.z
               );
            }
         }
      }

      if (this.getAnimation() == ANIMATION_TAIL_WHIP && target != null && this.getAnimationTick() == 7) {
         double dist = this.distanceToSqr(target);
         if (dist < 10.0) {
            target.hurt(this.level().damageSources().mobAttack(this), (int)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
            target.hasImpulse = true;
            float f = Mth.sqrt(0.5F);
            double d1 = target.getX() - this.getX();

            double d0;
            for (d0 = target.getZ() - this.getZ(); d1 * d1 + d0 * d0 < 1.0E-4; d0 = (Math.random() - Math.random()) * 0.01) {
               d1 = (Math.random() - Math.random()) * 0.01;
            }

            Vec3 Vector3d = this.getDeltaMovement();
            Vec3 Vector3d1 = new Vec3(d0, 0.0, d1).normalize().scale(0.5);
            this.setDeltaMovement(
               Vector3d.x / 2.0 - Vector3d1.x, this.onGround() ? Math.min(0.4, Vector3d.y / 2.0 + 0.5) : Vector3d.y, Vector3d.z / 2.0 - Vector3d1.z
            );
         }
      }

      if (this.isGoingUp() && !this.level().isClientSide && !this.isFlying()) {
         this.setDeltaMovement(this.getDeltaMovement().add(0.0, 1.0, 0.0));
         this.setFlying(true);
      }

      if (!this.isOverAir() && this.isFlying() && this.ticksFlying > 25) {
         this.setFlying(false);
      }

      if (this.dismountIAF() && this.isFlying() && this.onGround()) {
         this.setFlying(false);
      }

      if (this.getUntamedRider() != null && this.getUntamedRider().isShiftKeyDown()) {
         this.getUntamedRider().stopRiding();
      }

      if (this.attack() && this.getControllingPassenger() != null && this.getControllingPassenger() instanceof Player) {
         LivingEntity riderTarget = DragonUtils.riderLookingAtEntity(this, this.getControllingPassenger(), 2.5);
         if (this.getAnimation() != ANIMATION_BITE) {
            this.setAnimation(ANIMATION_BITE);
         }

         if (riderTarget != null) {
            riderTarget.hurt(this.level().damageSources().mobAttack(this), (int)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
         }
      }

      if (target != null && this.isOwnedBy(target)) {
         this.setTarget(null);
      }

      if (target != null && this.onGround() && this.isFlying() && this.ticksFlying > 40) {
         this.setFlying(false);
      }
   }

   public boolean doHurtTarget(Entity entityIn) {
      if (this.getAnimation() != ANIMATION_BITE
         && this.getAnimation() != ANIMATION_TAIL_WHIP
         && this.getAnimation() != ANIMATION_WING_BLAST
         && this.getControllingPassenger() == null) {
         if (this.random.nextBoolean()) {
            this.setAnimation(ANIMATION_BITE);
         } else {
            this.setAnimation(!this.getRandom().nextBoolean() && !this.isFlying() ? ANIMATION_TAIL_WHIP : ANIMATION_WING_BLAST);
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public Player getRidingPlayer() {
      return this.getControllingPassenger() instanceof Player player ? player : null;
   }

   @Override
   public boolean isFlying() {
      return this.level().isClientSide ? (this.isFlying = (Boolean)this.entityData.get(FLYING)) : this.isFlying;
   }

   public void setFlying(boolean flying) {
      this.entityData.set(FLYING, flying);
      if (!this.level().isClientSide) {
         this.isFlying = flying;
      }
   }

   public int getVariant() {
      return (Integer)this.entityData.get(VARIANT);
   }

   public void setVariant(int variant) {
      this.entityData.set(VARIANT, variant);
   }

   @Override
   public boolean isGoingUp() {
      return ((Byte)this.entityData.get(CONTROL_STATE) & 1) == 1;
   }

   @Override
   public boolean isGoingDown() {
      return ((Byte)this.entityData.get(CONTROL_STATE) >> 1 & 1) == 1;
   }

   public boolean attack() {
      return ((Byte)this.entityData.get(CONTROL_STATE) >> 2 & 1) == 1;
   }

   public boolean dismountIAF() {
      return ((Byte)this.entityData.get(CONTROL_STATE) >> 3 & 1) == 1;
   }

   @Override
   public void up(boolean up) {
      this.setStateField(0, up);
   }

   @Override
   public void down(boolean down) {
      this.setStateField(1, down);
   }

   @Override
   public void attack(boolean attack) {
      this.setStateField(2, attack);
   }

   @Override
   public void strike(boolean strike) {
   }

   @Override
   public void dismount(boolean dismount) {
      this.setStateField(3, dismount);
   }

   private void setStateField(int i, boolean newState) {
      byte prevState = (Byte)this.entityData.get(CONTROL_STATE);
      if (newState) {
         this.entityData.set(CONTROL_STATE, (byte)(prevState | 1 << i));
      } else {
         this.entityData.set(CONTROL_STATE, (byte)(prevState & ~(1 << i)));
      }
   }

   @Override
   public byte getControlState() {
      return (Byte)this.entityData.get(CONTROL_STATE);
   }

   @Override
   public void setControlState(byte state) {
      this.entityData.set(CONTROL_STATE, state);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)IafSounds.AMPHITHERE_IDLE.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)IafSounds.AMPHITHERE_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)IafSounds.AMPHITHERE_DIE.get();
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
      return new Animation[]{ANIMATION_BITE, ANIMATION_BITE_RIDER, ANIMATION_WING_BLAST, ANIMATION_TAIL_WHIP, ANIMATION_SPEAK};
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

   public boolean isBlinking() {
      return this.tickCount % 50 > 40;
   }

   public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageableEntity) {
      AmphithereEntity amphithere = new AmphithereEntity((EntityType<AmphithereEntity>)IafEntities.AMPHITHERE.get(), this.level());
      amphithere.setVariant(this.getVariant());
      return amphithere;
   }

   public int getBaseExperienceReward() {
      return 10;
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn) {
      spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn);
      this.setVariant(this.getRandom().nextInt(5));
      return spawnDataIn;
   }

   protected float getRiddenSpeed(Player pPlayer) {
      return !this.isFlying() && !this.isHovering()
         ? (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.5F
         : (float)this.getAttributeValue(Attributes.FLYING_SPEED) * 2.0F;
   }

   public void travel(Vec3 travelVector) {
      if (this.isControlledByLocalInstance()) {
         if (this.isInWater()) {
            this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
         } else if (this.isInLava()) {
            this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
         } else if (!this.isFlying() && !this.isHovering()) {
            super.travel(travelVector);
         } else {
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
         }
      } else {
         super.travel(travelVector);
      }
   }

   protected void tickRidden(Player player, Vec3 travelVector) {
      super.tickRidden(player, travelVector);
      Vec2 vec2 = this.getRiddenRotation(player);
      this.setRot(vec2.y, vec2.x);
      this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
      if (this.isControlledByLocalInstance()) {
         Vec3 vec3 = this.getDeltaMovement();
         float vertical = this.isGoingUp() ? 0.2F : (this.isGoingDown() ? -0.2F : 0.0F);
         if (!this.isFlying() && !this.isHovering()) {
            vertical = (float)travelVector.y;
         }

         this.setDeltaMovement(vec3.add(0.0, vertical, 0.0));
      }
   }

   protected Vec3 getRiddenInput(Player player, Vec3 travelVector) {
      float f = player.xxa * 0.5F;
      float f1 = player.zza;
      if (f1 <= 0.0F) {
         f1 *= 0.25F;
      }

      return new Vec3(f, 0.0, f1);
   }

   protected Vec2 getRiddenRotation(LivingEntity entity) {
      return new Vec2(entity.getXRot() * 0.5F, entity.getYRot());
   }

   public boolean canMove() {
      return this.getControllingPassenger() == null && this.sitProgress == 0.0F && !this.isOrderedToSit();
   }

   public void handleEntityEvent(byte id) {
      if (id == 45) {
         this.playEffect();
      } else {
         super.handleEntityEvent(id);
      }
   }

   protected void playEffect() {
      for (int i = 0; i < 7; i++) {
         double d0 = this.random.nextGaussian() * 0.02;
         double d1 = this.random.nextGaussian() * 0.02;
         double d2 = this.random.nextGaussian() * 0.02;
         this.level()
            .addParticle(
               ParticleTypes.HEART,
               this.getX() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
               this.getY() + 0.5 + this.random.nextFloat() * this.getBbHeight(),
               this.getZ() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
               d0,
               d1,
               d2
            );
      }
   }

   @Override
   public void onHearFlute(Player player) {
      if (!this.onGround() && this.isTame()) {
         this.isFallen = true;
      }
   }

   public boolean isPersistenceRequired() {
      return true;
   }

   @Override
   public double getFlightSpeedModifier() {
      return 0.555;
   }

   @Override
   public boolean fliesLikeElytra() {
      return !this.onGround();
   }

   private boolean isOverAir() {
      return this.level().isEmptyBlock(this.blockPosition().below());
   }

   public boolean canBlockPosBeSeen(BlockPos pos) {
      Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
      Vec3 Vector3d1 = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
      return this.level().clip(new ClipContext(Vector3d, Vector3d1, net.minecraft.world.level.ClipContext.Block.COLLIDER, Fluid.NONE, this)).getType()
         == Type.MISS;
   }

   class AIFlyCircle extends Goal {
      BlockPos target;

      public AIFlyCircle() {
      }

      public boolean canUse() {
         if (AmphithereEntity.this.flightBehavior != AmphithereEntity.FlightBehavior.CIRCLE || !AmphithereEntity.this.canMove()) {
            return false;
         } else if (AmphithereEntity.this.isFlying()) {
            AmphithereEntity.this.orbitPos = AmphithereEntity.getPositionRelativetoGround(
               AmphithereEntity.this,
               AmphithereEntity.this.level(),
               AmphithereEntity.this.getBlockX() + AmphithereEntity.this.random.nextInt(30) - 15,
               AmphithereEntity.this.getBlockZ() + AmphithereEntity.this.random.nextInt(30) - 15,
               AmphithereEntity.this.random
            );
            this.target = AmphithereEntity.getPositionInOrbit(
               AmphithereEntity.this, AmphithereEntity.this.level(), AmphithereEntity.this.orbitPos, AmphithereEntity.this.random
            );
            return true;
         } else {
            return false;
         }
      }

      protected boolean isDirectPathBetweenPoints() {
         return AmphithereEntity.this.canBlockPosBeSeen(this.target);
      }

      public boolean canContinueToUse() {
         return false;
      }

      public void tick() {
         if (!this.isDirectPathBetweenPoints()) {
            this.target = AmphithereEntity.getPositionInOrbit(
               AmphithereEntity.this, AmphithereEntity.this.level(), AmphithereEntity.this.orbitPos, AmphithereEntity.this.random
            );
         }

         if (AmphithereEntity.this.level().isEmptyBlock(this.target)) {
            AmphithereEntity.this.moveControl.setWantedPosition(this.target.getX() + 0.5, this.target.getY() + 0.5, this.target.getZ() + 0.5, 0.25);
            if (AmphithereEntity.this.getTarget() == null) {
               AmphithereEntity.this.getLookControl().setLookAt(this.target.getX() + 0.5, this.target.getY() + 0.5, this.target.getZ() + 0.5, 180.0F, 20.0F);
            }
         }
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   class AIFlyWander extends Goal {
      BlockPos target;

      public AIFlyWander() {
      }

      public boolean canUse() {
         if (AmphithereEntity.this.flightBehavior != AmphithereEntity.FlightBehavior.WANDER || !AmphithereEntity.this.canMove()) {
            return false;
         }

         if (!AmphithereEntity.this.isFlying()) {
            return false;
         }

         this.target = AmphithereEntity.getPositionRelativetoGround(
            AmphithereEntity.this,
            AmphithereEntity.this.level(),
            AmphithereEntity.this.getBlockX() + AmphithereEntity.this.random.nextInt(30) - 15,
            AmphithereEntity.this.getBlockZ() + AmphithereEntity.this.random.nextInt(30) - 15,
            AmphithereEntity.this.random
         );
         AmphithereEntity.this.orbitPos = null;
         return !AmphithereEntity.this.getMoveControl().hasWanted() || AmphithereEntity.this.ticksStill >= 50;
      }

      protected boolean isDirectPathBetweenPoints(Entity e) {
         return AmphithereEntity.this.canBlockPosBeSeen(this.target);
      }

      public boolean canContinueToUse() {
         return false;
      }

      public void tick() {
         if (!this.isDirectPathBetweenPoints(AmphithereEntity.this)) {
            this.target = AmphithereEntity.getPositionRelativetoGround(
               AmphithereEntity.this,
               AmphithereEntity.this.level(),
               AmphithereEntity.this.getBlockX() + AmphithereEntity.this.random.nextInt(30) - 15,
               AmphithereEntity.this.getBlockZ() + AmphithereEntity.this.random.nextInt(30) - 15,
               AmphithereEntity.this.random
            );
         }

         if (AmphithereEntity.this.level().isEmptyBlock(this.target)) {
            AmphithereEntity.this.moveControl.setWantedPosition(this.target.getX() + 0.5, this.target.getY() + 0.5, this.target.getZ() + 0.5, 0.25);
            if (AmphithereEntity.this.getTarget() == null) {
               AmphithereEntity.this.getLookControl().setLookAt(this.target.getX() + 0.5, this.target.getY() + 0.5, this.target.getZ() + 0.5, 180.0F, 20.0F);
            }
         }
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class AILandWander extends WaterAvoidingRandomStrollGoal {
      public AILandWander(PathfinderMob creature, double speed) {
         super(creature, speed, 10.0F);
      }

      public boolean canUse() {
         return this.mob.onGround() && super.canUse() && ((AmphithereEntity)this.mob).canMove();
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   public enum FlightBehavior {
      CIRCLE,
      WANDER,
      NONE;
   }

   class FlyMoveHelper extends MoveControl {
      public FlyMoveHelper(AmphithereEntity entity) {
         super(entity);
         this.speedModifier = 1.75;
      }

      public void tick() {
         if (AmphithereEntity.this.canMove()) {
            if (AmphithereEntity.this.horizontalCollision) {
               AmphithereEntity.this.setYRot(AmphithereEntity.this.getYRot() + 180.0F);
               this.speedModifier = 0.1F;
               BlockPos target = AmphithereEntity.getPositionRelativetoGround(
                  AmphithereEntity.this,
                  AmphithereEntity.this.level(),
                  AmphithereEntity.this.getBlockX() + AmphithereEntity.this.random.nextInt(15) - 7,
                  AmphithereEntity.this.getBlockZ() + AmphithereEntity.this.random.nextInt(15) - 7,
                  AmphithereEntity.this.random
               );
               this.wantedX = target.getX();
               this.wantedY = target.getY();
               this.wantedZ = target.getZ();
            }

            if (this.operation == Operation.MOVE_TO) {
               double d0 = this.wantedX - AmphithereEntity.this.getX();
               double d1 = this.wantedY - AmphithereEntity.this.getY();
               double d2 = this.wantedZ - AmphithereEntity.this.getZ();
               double d3 = d0 * d0 + d1 * d1 + d2 * d2;
               d3 = Mth.sqrt((float)d3);
               if (d3 < 6.0 && AmphithereEntity.this.getTarget() == null) {
                  if (!AmphithereEntity.this.changedFlightBehavior
                     && AmphithereEntity.this.flightBehavior == AmphithereEntity.FlightBehavior.WANDER
                     && AmphithereEntity.this.random.nextInt(30) == 0) {
                     AmphithereEntity.this.flightBehavior = AmphithereEntity.FlightBehavior.CIRCLE;
                     AmphithereEntity.this.changedFlightBehavior = true;
                  }

                  if (!AmphithereEntity.this.changedFlightBehavior
                     && AmphithereEntity.this.flightBehavior == AmphithereEntity.FlightBehavior.CIRCLE
                     && AmphithereEntity.this.random.nextInt(5) == 0
                     && AmphithereEntity.this.ticksCircling > 150) {
                     AmphithereEntity.this.flightBehavior = AmphithereEntity.FlightBehavior.WANDER;
                     AmphithereEntity.this.changedFlightBehavior = true;
                  }

                  if (AmphithereEntity.this.hasHomePosition && AmphithereEntity.this.flightBehavior != AmphithereEntity.FlightBehavior.NONE
                     || AmphithereEntity.this.getCommand() == 2) {
                     AmphithereEntity.this.flightBehavior = AmphithereEntity.FlightBehavior.CIRCLE;
                  }
               }

               if (d3 < 1.0 && AmphithereEntity.this.getTarget() == null) {
                  this.operation = Operation.WAIT;
                  AmphithereEntity.this.setDeltaMovement(AmphithereEntity.this.getDeltaMovement().multiply(0.5, 0.5, 0.5));
               } else {
                  AmphithereEntity.this.setDeltaMovement(
                     AmphithereEntity.this.getDeltaMovement()
                        .add(d0 / d3 * 0.5 * this.speedModifier, d1 / d3 * 0.5 * this.speedModifier, d2 / d3 * 0.5 * this.speedModifier)
                  );
                  float f1 = (float)(-(Mth.atan2(d1, d3) * (180.0 / Math.PI)));
                  AmphithereEntity.this.setXRot(f1);
                  if (AmphithereEntity.this.getTarget() == null) {
                     AmphithereEntity.this.setYRot(
                        -((float)Mth.atan2(AmphithereEntity.this.getDeltaMovement().x, AmphithereEntity.this.getDeltaMovement().z)) * (180.0F / (float)Math.PI)
                     );
                  } else {
                     double d4 = AmphithereEntity.this.getTarget().getX() - AmphithereEntity.this.getX();
                     double d5 = AmphithereEntity.this.getTarget().getZ() - AmphithereEntity.this.getZ();
                     AmphithereEntity.this.setYRot(-((float)Mth.atan2(d4, d5)) * (180.0F / (float)Math.PI));
                  }

                  AmphithereEntity.this.yBodyRot = AmphithereEntity.this.getYRot();
               }
            }
         }
      }
   }
}
