package com.benji.netherman.common.entity;

import com.benji.netherman.common.block.entity.MazeDoorBlockEntity;
import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BellGuardianEntity extends Monster implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private int soundTimer = 0;
   private int stuckTimer = 0;

   public BellGuardianEntity(EntityType<? extends Monster> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 200.0)
         .add(Attributes.MOVEMENT_SPEED, 0.2)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.ATTACK_DAMAGE, 20.0)
         .add(Attributes.FOLLOW_RANGE, 1024.0);
   }

   protected void registerGoals() {
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, false, false));
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide() && this.isAlive()) {
         boolean isMovingOrAggressive = this.getDeltaMovement().horizontalDistanceSqr() > 0.001 || this.getTarget() != null;
         if (isMovingOrAggressive) {
            this.soundTimer++;
            if (this.soundTimer >= 52) {
               this.playRandomBeastSound();
               this.soundTimer = 0;
            }
         } else {
            this.soundTimer = 0;
         }

         if (this.getTarget() != null) {
            LivingEntity target = this.getTarget();
            this.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (this.distanceTo(target) > 1.5) {
               this.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0);
            }

            if (this.horizontalCollision) {
               if (this.openDoorsInFront()) {
                  this.stuckTimer = 0;
               } else {
                  this.stuckTimer++;
                  if (this.stuckTimer >= 20) {
                     this.smashObstaclesInFront();
                     this.stuckTimer = 0;
                  }
               }
            } else {
               this.stuckTimer = 0;
            }
         }
      }
   }

   private boolean openDoorsInFront() {
      Direction facing = this.getDirection();
      BlockPos center = this.blockPosition().relative(facing).above(1);
      boolean foundDoor = false;

      for (int x = -1; x <= 1; x++) {
         for (int y = -1; y <= 1; y++) {
            for (int z = -1; z <= 1; z++) {
               BlockPos p = facing.getAxis() == Axis.Z ? center.offset(x, y, 0) : center.offset(0, y, z);
               BlockState state = this.level().getBlockState(p);
               if (state.is((Block)ModBlocks.MAZE_DOOR.get())) {
                  if (this.level().getBlockEntity(p) instanceof MazeDoorBlockEntity door) {
                     door.openTemporary();
                     foundDoor = true;
                  }
               } else if (state.is((Block)ModBlocks.GRAND_DOOR_PART.get())) {
                  this.openMazeDoorSafely(p);
                  foundDoor = true;
               }
            }
         }
      }

      return foundDoor;
   }

   private void smashObstaclesInFront() {
      Direction facing = this.getDirection();
      BlockPos center = this.blockPosition().relative(facing).above(1);
      boolean brokeSomething = false;

      for (int x = -1; x <= 1; x++) {
         for (int y = -1; y <= 1; y++) {
            for (int z = -1; z <= 1; z++) {
               BlockPos p = facing.getAxis() == Axis.Z ? center.offset(x, y, 0) : center.offset(0, y, z);
               BlockState state = this.level().getBlockState(p);
               if (!state.isAir()
                  && !(state.getDestroySpeed(this.level(), p) < 0.0F)
                  && !state.is((Block)ModBlocks.MAZE_DOOR.get())
                  && !state.is((Block)ModBlocks.GRAND_DOOR_PART.get())) {
                  boolean canGrief = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
                  if (canGrief) {
                     this.level().destroyBlock(p, true);
                     brokeSomething = true;
                  }
               }
            }
         }
      }

      if (brokeSomething) {
         this.playSound(SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, 2.0F, 0.5F);
      }
   }

   private void openMazeDoorSafely(BlockPos partPos) {
      for (BlockPos checkPos : BlockPos.betweenClosed(partPos.offset(-5, -6, -5), partPos.offset(5, 6, 5))) {
         if (this.level().getBlockState(checkPos).is((Block)ModBlocks.MAZE_DOOR.get())
            && this.level().getBlockEntity(checkPos) instanceof MazeDoorBlockEntity door) {
            door.openTemporary();
            return;
         }
      }
   }

   private void playRandomBeastSound() {
      SoundEvent[] sounds = new SoundEvent[]{
         (SoundEvent)ModSounds.BELL_BEAST_1.get(),
         (SoundEvent)ModSounds.BELL_BEAST_3.get(),
         (SoundEvent)ModSounds.BELL_BEAST_4.get(),
         (SoundEvent)ModSounds.BELL_BEAST_5.get(),
         (SoundEvent)ModSounds.BELL_BEAST_6.get(),
         (SoundEvent)ModSounds.BELL_BEAST_7.get(),
         (SoundEvent)ModSounds.BELL_BEAST_8.get(),
         (SoundEvent)ModSounds.BELL_BEAST_9.get()
      };
      SoundEvent randomSound = sounds[this.random.nextInt(sounds.length)];
      this.playSound(randomSound, 1.0F, 1.0F);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(
            this,
            "controller",
            5,
            event -> event.isMoving()
               ? event.setAndContinue(RawAnimation.begin().thenLoop("walk"))
               : event.setAndContinue(RawAnimation.begin().thenLoop("idle"))
         )
      );
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
