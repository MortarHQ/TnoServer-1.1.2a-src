package com.chronodawn.entities.mobs;

import com.chronodawn.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class TimeboundRabbitEntity extends Animal {
   private int jumpTicks;
   private int jumpDuration;
   private boolean wasOnGround;
   private int jumpDelayTicks;

   public TimeboundRabbitEntity(EntityType<? extends TimeboundRabbitEntity> entityType, Level level) {
      super(entityType, level);
      this.jumpControl = new TimeboundRabbitEntity.RabbitJumpControl(this);
      this.moveControl = new TimeboundRabbitEntity.RabbitMoveControl(this);
      this.setSpeedModifier(0.0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(1, new TimeboundRabbitEntity.RabbitPanicGoal(this, 2.2));
      this.goalSelector.addGoal(2, new BreedGoal(this, 0.8));
      this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, stack -> this.isFood(stack), false));
      this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 10.0F));
      this.goalSelector.addGoal(12, new RandomLookAroundGoal(this));
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0).add(Attributes.MOVEMENT_SPEED, 0.3);
   }

   public static boolean checkTimeboundRabbitSpawnRules(
      EntityType<TimeboundRabbitEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return Animal.checkAnimalSpawnRules(entityType, level, spawnType, pos, random);
   }

   public boolean isFood(ItemStack stack) {
      return stack.is(Items.CARROT) || stack.is(Items.GOLDEN_CARROT) || stack.is(Items.DANDELION);
   }

   @Nullable
   public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
      return (AgeableMob)((EntityType)ModEntities.TIMEBOUND_RABBIT.get()).create(level);
   }

   protected float getJumpPower() {
      float power = 0.42F * this.getBlockJumpFactor();
      if (this.moveControl.getSpeedModifier() <= 0.6) {
         power *= 0.6F;
      }

      return power + this.getJumpBoostPower();
   }

   public void jumpFromGround() {
      super.jumpFromGround();
      double speed = this.moveControl.getSpeedModifier();
      if (speed > 0.0) {
         double horizontalSpeed = this.getDeltaMovement().horizontalDistanceSqr();
         if (horizontalSpeed < 0.01) {
            this.moveRelative(0.1F, new Vec3(0.0, 0.0, 1.0));
         }
      }

      if (!this.level().isClientSide) {
         this.level().broadcastEntityEvent(this, (byte)1);
      }
   }

   public float getJumpCompletion(float partialTick) {
      return this.jumpDuration == 0 ? 0.0F : (this.jumpTicks + partialTick) / this.jumpDuration;
   }

   public void setSpeedModifier(double speed) {
      this.getNavigation().setSpeedModifier(speed);
      this.moveControl.setWantedPosition(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ(), speed);
   }

   public void setJumping(boolean jumping) {
      super.setJumping(jumping);
      if (jumping) {
         this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
      }
   }

   public void startJumping() {
      this.setJumping(true);
      this.jumpDuration = 10;
      this.jumpTicks = 0;
   }

   public void customServerAiStep() {
      if (this.jumpDelayTicks > 0) {
         this.jumpDelayTicks--;
      }

      if (this.onGround()) {
         if (!this.wasOnGround) {
            this.setJumping(false);
            this.checkLandingDelay();
         }

         TimeboundRabbitEntity.RabbitJumpControl jumpControl = (TimeboundRabbitEntity.RabbitJumpControl)this.jumpControl;
         if (!jumpControl.wantJump()) {
            if (this.moveControl.hasWanted() && this.jumpDelayTicks == 0) {
               Path path = this.navigation.getPath();
               Vec3 target = new Vec3(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ());
               if (path != null && !path.isDone()) {
                  target = path.getNextEntityPos(this);
               }

               this.facePoint(target.x, target.z);
               this.startJumping();
            }
         } else if (!jumpControl.canJump()) {
            this.enableJumpControl();
         }
      }

      this.wasOnGround = this.onGround();
   }

   public boolean canSpawnSprintParticle() {
      return false;
   }

   private void facePoint(double x, double z) {
      this.setYRot((float)(Mth.atan2(z - this.getZ(), x - this.getX()) * (180.0 / Math.PI)) - 90.0F);
   }

   private void enableJumpControl() {
      ((TimeboundRabbitEntity.RabbitJumpControl)this.jumpControl).setCanJump(true);
   }

   private void disableJumpControl() {
      ((TimeboundRabbitEntity.RabbitJumpControl)this.jumpControl).setCanJump(false);
   }

   private void setLandingDelay() {
      if (this.moveControl.getSpeedModifier() < 2.2) {
         this.jumpDelayTicks = 10;
      } else {
         this.jumpDelayTicks = 1;
      }
   }

   private void checkLandingDelay() {
      this.setLandingDelay();
      this.disableJumpControl();
   }

   public void aiStep() {
      super.aiStep();
      if (this.jumpTicks != this.jumpDuration) {
         this.jumpTicks++;
      } else if (this.jumpDuration != 0) {
         this.jumpTicks = 0;
         this.jumpDuration = 0;
         this.setJumping(false);
      }
   }

   protected SoundEvent getJumpSound() {
      return SoundEvents.RABBIT_JUMP;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.RABBIT_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return SoundEvents.RABBIT_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.RABBIT_DEATH;
   }

   public static class RabbitJumpControl extends JumpControl {
      private final TimeboundRabbitEntity rabbit;
      private boolean canJump;

      public RabbitJumpControl(TimeboundRabbitEntity rabbit) {
         super(rabbit);
         this.rabbit = rabbit;
      }

      public boolean wantJump() {
         return this.jump;
      }

      public boolean canJump() {
         return this.canJump;
      }

      public void setCanJump(boolean canJump) {
         this.canJump = canJump;
      }

      public void tick() {
         if (this.jump) {
            this.rabbit.startJumping();
            this.jump = false;
         }
      }
   }

   static class RabbitMoveControl extends MoveControl {
      private final TimeboundRabbitEntity rabbit;
      private double nextJumpSpeed;

      public RabbitMoveControl(TimeboundRabbitEntity rabbit) {
         super(rabbit);
         this.rabbit = rabbit;
      }

      public void tick() {
         if (this.rabbit.onGround() && !this.rabbit.jumping && !((TimeboundRabbitEntity.RabbitJumpControl)this.rabbit.jumpControl).wantJump()) {
            this.rabbit.setSpeedModifier(0.0);
         } else if (this.hasWanted()) {
            this.rabbit.setSpeedModifier(this.nextJumpSpeed);
         }

         super.tick();
      }

      public void setWantedPosition(double x, double y, double z, double speed) {
         if (this.rabbit.isInWater()) {
            speed = 1.5;
         }

         super.setWantedPosition(x, y, z, speed);
         if (speed > 0.0) {
            this.nextJumpSpeed = speed;
         }
      }
   }

   static class RabbitPanicGoal extends PanicGoal {
      private final TimeboundRabbitEntity rabbit;

      public RabbitPanicGoal(TimeboundRabbitEntity rabbit, double speedModifier) {
         super(rabbit, speedModifier);
         this.rabbit = rabbit;
      }

      public void tick() {
         super.tick();
         this.rabbit.setSpeedModifier(this.speedModifier);
      }
   }
}
