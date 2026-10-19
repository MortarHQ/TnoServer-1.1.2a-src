package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import com.iafenvoy.iceandfire.entity.util.dragon.IafDragonAttacks;
import com.iafenvoy.iceandfire.particle.DragonFlameParticleType;
import com.iafenvoy.iceandfire.registry.IafDragonTypes;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.iceandfire.registry.IafSounds;
import com.iafenvoy.iceandfire.registry.tag.IafEntityTags;
import com.iafenvoy.uranus.animation.Animation;
import com.iafenvoy.uranus.animation.IAnimatedEntity;
import java.util.Objects;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FireDragonEntity extends DragonBaseEntity {
   public static final ResourceLocation FEMALE_LOOT = ResourceLocation.fromNamespaceAndPath("iceandfire", "entities/dragon/fire_dragon_female");
   public static final ResourceLocation MALE_LOOT = ResourceLocation.fromNamespaceAndPath("iceandfire", "entities/dragon/fire_dragon_male");
   public static final ResourceLocation SKELETON_LOOT = ResourceLocation.fromNamespaceAndPath("iceandfire", "entities/dragon/fire_dragon_skeleton");

   public FireDragonEntity(EntityType<? extends FireDragonEntity> t, Level worldIn) {
      super(
         t,
         worldIn,
         IafDragonTypes.FIRE,
         1.0,
         1 + (Integer)IafCommonConfig.INSTANCE.dragon.attackDamage.getValue(),
         (Double)IafCommonConfig.INSTANCE.dragon.maxHealth.getValue() * 0.04,
         (Double)IafCommonConfig.INSTANCE.dragon.maxHealth.getValue(),
         0.15F,
         0.4F
      );
      this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
      this.setPathfindingMalus(PathType.LAVA, 8.0F);
      ANIMATION_SPEAK = Animation.create(20);
      ANIMATION_BITE = Animation.create(35);
      ANIMATION_SHAKEPREY = Animation.create(65);
      ANIMATION_TAILWHACK = Animation.create(40);
      ANIMATION_FIRECHARGE = Animation.create(30);
      ANIMATION_WINGBLAST = Animation.create(50);
      ANIMATION_ROAR = Animation.create(40);
      ANIMATION_EPIC_ROAR = Animation.create(60);
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
   }

   @Override
   protected boolean shouldTarget(Entity entity) {
      return entity instanceof DragonBaseEntity && !this.isTame()
         ? entity.getType() != this.getType() && this.getBbWidth() >= entity.getBbWidth() && !((DragonBaseEntity)entity).isMobDead()
         : entity instanceof Player
            || DragonUtils.isDragonTargetable(entity, IafEntityTags.FIRE_DRAGON_TARGETS)
            || !this.isTame() && DragonUtils.isVillager(entity);
   }

   @Override
   public String getVariantName(int variant) {
      return switch (variant) {
         case 1 -> "green_";
         case 2 -> "bronze_";
         case 3 -> "gray_";
         default -> "red_";
      };
   }

   @Override
   public boolean doHurtTarget(Entity entityIn) {
      this.getLookControl().setLookAt(entityIn, 30.0F, 30.0F);
      if (!this.isPlayingAttackAnimation()) {
         switch (this.groundAttack) {
            case BITE:
               this.setAnimation(ANIMATION_BITE);
               break;
            case TAIL_WHIP:
               this.setAnimation(ANIMATION_TAILWHACK);
               break;
            case SHAKE_PREY:
               boolean flag = false;
               if (new Random().nextInt(2) == 0
                  && this.isDirectPathBetweenPoints(
                     this, this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0), entityIn.position().add(0.0, entityIn.getBbHeight() / 2.0F, 0.0)
                  )
                  && entityIn.getBbWidth() < this.getBbWidth() * 0.5F
                  && this.getControllingPassenger() == null
                  && this.getDragonStage() > 1
                  && !(entityIn instanceof DragonBaseEntity)
                  && !DragonUtils.isAnimaniaMob(entityIn)) {
                  this.setAnimation(ANIMATION_SHAKEPREY);
                  flag = true;
                  entityIn.startRiding(this);
               }

               if (!flag) {
                  this.groundAttack = IafDragonAttacks.Ground.BITE;
                  this.setAnimation(ANIMATION_BITE);
               }
               break;
            case WING_BLAST:
               this.setAnimation(ANIMATION_WINGBLAST);
         }
      }

      return false;
   }

   @Override
   public void aiStep() {
      super.aiStep();
      LivingEntity attackTarget = this.getTarget();
      if (!this.level().isClientSide && attackTarget != null) {
         if (this.getBoundingBox()
            .inflate(2.5F + this.getRenderSize() * 0.33F, 2.5F + this.getRenderSize() * 0.33F, 2.5F + this.getRenderSize() * 0.33F)
            .intersects(attackTarget.getBoundingBox())) {
            this.doHurtTarget(attackTarget);
         }

         if (this.groundAttack == IafDragonAttacks.Ground.FIRE && (this.usingGroundAttack || this.onGround())) {
            this.shootFireAtMob(attackTarget);
         }

         if (this.airAttack == IafDragonAttacks.Air.TACKLE && !this.usingGroundAttack && this.distanceToSqr(attackTarget) < 100.0) {
            double difX = attackTarget.getX() - this.getX();
            double difY = attackTarget.getY() + attackTarget.getBbHeight() - this.getY();
            double difZ = attackTarget.getZ() - this.getZ();
            this.setDeltaMovement(this.getDeltaMovement().add(difX * 0.1, difY * 0.1, difZ * 0.1));
            if (this.getBoundingBox()
               .inflate(1.0F + this.getRenderSize() * 0.5F, 1.0F + this.getRenderSize() * 0.5F, 1.0F + this.getRenderSize() * 0.5F)
               .intersects(attackTarget.getBoundingBox())) {
               this.doHurtTarget(attackTarget);
               this.usingGroundAttack = true;
               this.randomizeAttacks();
               this.setFlying(false);
               this.setHovering(false);
            }
         }
      }
   }

   @Override
   protected void breathFireAtPos(BlockPos burningTarget) {
      if (this.isBreathingFire()) {
         if (this.isActuallyBreathingFire()) {
            this.setYRot(this.yBodyRot);
            if (this.tickCount % 5 == 0) {
               this.playSound((SoundEvent)IafSounds.FIREDRAGON_BREATH.get(), 4.0F, 1.0F);
            }

            this.breathAttack(burningTarget.getX() + 0.5F, burningTarget.getY() + 0.5F, burningTarget.getZ() + 0.5F, false);
         }
      } else {
         this.setBreathingFire(true);
      }
   }

   @Override
   public void riderShootFire(Entity controller) {
      if (this.getRandom().nextInt(5) == 0 && !this.isBaby()) {
         if (this.getAnimation() != ANIMATION_FIRECHARGE) {
            this.setAnimation(ANIMATION_FIRECHARGE);
         } else if (this.getAnimationTick() == 20) {
            this.setYRot(this.yBodyRot);
            Vec3 headVec = this.getHeadPosition();
            this.playSound((SoundEvent)IafSounds.FIREDRAGON_BREATH.get(), 4.0F, 1.0F);
            double d2 = controller.getLookAngle().x;
            double d3 = controller.getLookAngle().y;
            double d4 = controller.getLookAngle().z;
            float inaccuracy = 1.0F;
            d2 += this.random.nextGaussian() * 0.0075F * inaccuracy;
            d3 += this.random.nextGaussian() * 0.0075F * inaccuracy;
            d4 += this.random.nextGaussian() * 0.0075F * inaccuracy;
            FireDragonChargeEntity entitylargefireball = new FireDragonChargeEntity(
               (EntityType<? extends Fireball>)IafEntities.FIRE_DRAGON_CHARGE.get(), this.level(), this, d2, d3, d4
            );
            entitylargefireball.setPos(headVec.x, headVec.y, headVec.z);
            if (!this.level().isClientSide) {
               this.level().addFreshEntity(entitylargefireball);
            }
         }
      } else if (this.isBreathingFire()) {
         if (this.isActuallyBreathingFire()) {
            this.setYRot(this.yBodyRot);
            if (this.tickCount % 5 == 0) {
               this.playSound((SoundEvent)IafSounds.FIREDRAGON_BREATH.get(), 4.0F, 1.0F);
            }

            HitResult mop = this.rayTraceRider(controller, 10 * this.getDragonStage(), 1.0F);
            if (mop != null) {
               this.breathAttack(mop.getLocation().x, mop.getLocation().y, mop.getLocation().z, false);
            }
         }
      } else {
         this.setBreathingFire(true);
      }
   }

   protected float getBlockSpeedFactor() {
      if (this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).is(BlockTags.SOUL_SPEED_BLOCKS)) {
         return this.getDragonStage() >= 2 ? 1.0F : 0.8F;
      } else {
         return super.getBlockSpeedFactor();
      }
   }

   @Override
   public void travel(Vec3 pTravelVector) {
      if (this.isInLava()) {
         if (this.isEffectiveAi() && this.getControllingPassenger() == null) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.7));
         } else {
            Objects.requireNonNull(this);
            if (this.getControllingPassenger() != null && !this.isHovering() && !this.isFlying()) {
               LivingEntity rider = this.getControllingPassenger();
               float speed = (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);
               float lavaSpeedMod = (float)(0.28F + 0.1 * Mth.map(speed, this.minimumSpeed, this.maximumSpeed, 0.0, 1.5));
               speed *= lavaSpeedMod;
               speed *= rider.isSprinting() ? 1.4F : 1.0F;
               float vertical = 0.0F;
               if (this.isGoingUp() && !this.isGoingDown()) {
                  vertical = 0.8F;
               } else if (this.isGoingDown() && !this.isGoingUp()) {
                  vertical = -0.8F;
               } else if (this.isGoingUp() && this.isGoingDown() && this.isControlledByLocalInstance()) {
                  this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.3F, 1.0));
               }

               Vec3 travelVector = new Vec3(rider.xxa, vertical, rider.zza);
               if (this.isControlledByLocalInstance()) {
                  this.setSpeed(speed);
                  this.moveRelative(this.getSpeed(), travelVector);
                  this.move(MoverType.SELF, this.getDeltaMovement());
                  Vec3 currentMotion = this.getDeltaMovement();
                  if (this.horizontalCollision) {
                     currentMotion = new Vec3(currentMotion.x, 0.2, currentMotion.z);
                  }

                  this.setDeltaMovement(currentMotion.scale(0.7));
                  this.calculateEntityAnimation(false);
               } else {
                  this.setDeltaMovement(Vec3.ZERO);
               }

               this.tryCheckInsideBlocks();
            } else {
               super.travel(pTravelVector);
            }
         }
      } else {
         Objects.requireNonNull(this);
         if (this.getControllingPassenger() != null
            && !this.isHovering()
            && !this.isFlying()
            && this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getFluidState().is(FluidTags.LAVA)) {
            LivingEntity rider = this.getControllingPassenger();
            double forward = rider.zza;
            double strafing = rider.xxa;
            double vertical = pTravelVector.y;
            float speed = (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);
            float groundSpeedModifier = (float)(1.8F * this.getFlightSpeedModifier());
            speed *= groundSpeedModifier;
            forward *= rider.isSprinting() ? 1.2F : 1.0;
            forward *= rider.zza > 0.0F ? 1.0 : 0.2F;
            strafing *= 0.05F;
            if (this.isControlledByLocalInstance()) {
               this.setSpeed(speed);
               super.travel(new Vec3(strafing, vertical, forward));
               Vec3 currentMotion = this.getDeltaMovement();
               if (this.horizontalCollision) {
                  currentMotion = new Vec3(currentMotion.x, 0.2, currentMotion.z);
               }

               this.setDeltaMovement(currentMotion.scale(0.7));
            } else {
               this.setDeltaMovement(Vec3.ZERO);
            }

            this.tryCheckInsideBlocks();
         } else {
            super.travel(pTravelVector);
         }
      }
   }

   @Override
   public ResourceLocation getDeadLootTable() {
      if (this.getDeathStage() >= this.getAgeInDays() / 5 / 2) {
         return SKELETON_LOOT;
      } else {
         return this.isMale() ? MALE_LOOT : FEMALE_LOOT;
      }
   }

   private void shootFireAtMob(LivingEntity entity) {
      if (this.usingGroundAttack && this.groundAttack == IafDragonAttacks.Ground.FIRE
         || !this.usingGroundAttack && (this.airAttack == IafDragonAttacks.Air.SCORCH_STREAM || this.airAttack == IafDragonAttacks.Air.HOVER_BLAST)) {
         if (this.usingGroundAttack && this.getRandom().nextInt(5) == 0 || !this.usingGroundAttack && this.airAttack == IafDragonAttacks.Air.HOVER_BLAST) {
            if (this.getAnimation() != ANIMATION_FIRECHARGE) {
               this.setAnimation(ANIMATION_FIRECHARGE);
            } else if (this.getAnimationTick() == 20) {
               this.setYRot(this.yBodyRot);
               Vec3 headVec = this.getHeadPosition();
               double d2 = entity.getX() - headVec.x;
               double d3 = entity.getY() - headVec.y;
               double d4 = entity.getZ() - headVec.z;
               float inaccuracy = 1.0F;
               d2 += this.random.nextGaussian() * 0.0075F * inaccuracy;
               d3 += this.random.nextGaussian() * 0.0075F * inaccuracy;
               d4 += this.random.nextGaussian() * 0.0075F * inaccuracy;
               this.playSound((SoundEvent)IafSounds.FIREDRAGON_BREATH.get(), 4.0F, 1.0F);
               FireDragonChargeEntity entitylargefireball = new FireDragonChargeEntity(
                  (EntityType<? extends Fireball>)IafEntities.FIRE_DRAGON_CHARGE.get(), this.level(), this, d2, d3, d4
               );
               entitylargefireball.setPos(headVec.x, headVec.y, headVec.z);
               if (!this.level().isClientSide) {
                  this.level().addFreshEntity(entitylargefireball);
               }

               if (!entity.isAlive()) {
                  this.setBreathingFire(false);
               }

               this.randomizeAttacks();
            }
         } else if (this.isBreathingFire()) {
            if (this.isActuallyBreathingFire()) {
               this.setYRot(this.yBodyRot);
               if (this.tickCount % 5 == 0) {
                  this.playSound((SoundEvent)IafSounds.FIREDRAGON_BREATH.get(), 4.0F, 1.0F);
               }

               this.breathAttack(entity.getX(), entity.getY(), entity.getZ(), false);
               if (!entity.isAlive()) {
                  this.setBreathingFire(false);
                  this.randomizeAttacks();
               }
            }
         } else {
            this.setBreathingFire(true);
         }
      }

      this.lookAt(entity, 360.0F, 360.0F);
   }

   @Override
   public Entity createCharge(double velocityX, double velocityY, double velocityZ) {
      this.playSound((SoundEvent)IafSounds.FIREDRAGON_BREATH.get(), 4.0F, 1.0F);
      return new FireDragonChargeEntity(
         (EntityType<? extends Fireball>)IafEntities.FIRE_DRAGON_CHARGE.get(), this.level(), this, velocityX, velocityY, velocityZ
      );
   }

   @Override
   public ParticleOptions createBreathParticle() {
      return new DragonFlameParticleType(this.getAgeScale());
   }

   protected SoundEvent getAmbientSound() {
      return this.isTeen()
         ? (SoundEvent)IafSounds.FIREDRAGON_TEEN_IDLE.get()
         : (this.isMature() ? (SoundEvent)IafSounds.FIREDRAGON_ADULT_IDLE.get() : (SoundEvent)IafSounds.FIREDRAGON_CHILD_IDLE.get());
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return this.isTeen()
         ? (SoundEvent)IafSounds.FIREDRAGON_TEEN_HURT.get()
         : (this.isMature() ? (SoundEvent)IafSounds.FIREDRAGON_ADULT_HURT.get() : (SoundEvent)IafSounds.FIREDRAGON_CHILD_HURT.get());
   }

   protected SoundEvent getDeathSound() {
      return this.isTeen()
         ? (SoundEvent)IafSounds.FIREDRAGON_TEEN_DEATH.get()
         : (this.isMature() ? (SoundEvent)IafSounds.FIREDRAGON_ADULT_DEATH.get() : (SoundEvent)IafSounds.FIREDRAGON_CHILD_DEATH.get());
   }

   @Override
   public SoundEvent getRoarSound() {
      return this.isTeen()
         ? (SoundEvent)IafSounds.FIREDRAGON_TEEN_ROAR.get()
         : (this.isMature() ? (SoundEvent)IafSounds.FIREDRAGON_ADULT_ROAR.get() : (SoundEvent)IafSounds.FIREDRAGON_CHILD_ROAR.get());
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{
         IAnimatedEntity.NO_ANIMATION,
         ANIMATION_EAT,
         ANIMATION_SPEAK,
         ANIMATION_BITE,
         ANIMATION_SHAKEPREY,
         ANIMATION_TAILWHACK,
         ANIMATION_FIRECHARGE,
         ANIMATION_WINGBLAST,
         ANIMATION_ROAR,
         ANIMATION_EPIC_ROAR
      };
   }

   public boolean isFood(ItemStack stack) {
      return !stack.isEmpty() && stack.getItem() != null && stack.getItem() == IafItems.FIRE_STEW.get();
   }

   @Override
   protected void spawnDeathParticles() {
      for (int k = 0; k < 3; k++) {
         double d2 = this.random.nextGaussian() * 0.02;
         double d0 = this.random.nextGaussian() * 0.02;
         double d1 = this.random.nextGaussian() * 0.02;
         if (this.level().isClientSide) {
            this.level()
               .addParticle(
                  ParticleTypes.FLAME,
                  this.getX() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
                  this.getY() + this.random.nextFloat() * this.getBbHeight(),
                  this.getZ() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
                  d2,
                  d0,
                  d1
               );
         }
      }
   }

   @Override
   public void spawnBabyParticles() {
      if (this.level().isClientSide) {
         for (int i = 0; i < 5; i++) {
            float radiusAdd = i * 0.15F;
            float headPosX = (float)(
               this.getX() + 1.8F * this.getRenderSize() * (0.3F + radiusAdd) * Mth.cos((float)((this.getYRot() + 90.0F) * Math.PI / 180.0))
            );
            float headPosZ = (float)(
               this.getY() + 1.8F * this.getRenderSize() * (0.3F + radiusAdd) * Mth.sin((float)((this.getYRot() + 90.0F) * Math.PI / 180.0))
            );
            float headPosY = (float)(this.getZ() + 0.5 * this.getRenderSize() * 0.3F);
            this.level().addParticle(new DragonFlameParticleType(this.getAgeScale()), headPosX, headPosY, headPosZ, 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   public Item getSkull() {
      return (Item)IafItems.DRAGON_SKULL_FIRE.get();
   }

   @Override
   public Item getBloodItem() {
      return (Item)IafItems.FIRE_DRAGON_BLOOD.get();
   }

   @Override
   public Item getFleshItem() {
      return (Item)IafItems.FIRE_DRAGON_FLESH.get();
   }

   @Override
   public ItemLike getHeartItem() {
      return (ItemLike)IafItems.FIRE_DRAGON_HEART.get();
   }
}
