package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.iceandfire.entity.DragonEggEntity;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DragonAIMateGoal extends Goal {
   private static final BlockState NEST = ((Block)IafBlocks.NEST.get()).defaultBlockState();
   final Level theWorld;
   final double moveSpeed;
   private final DragonBaseEntity dragon;
   int spawnBabyDelay;
   private DragonBaseEntity targetMate;

   public DragonAIMateGoal(DragonBaseEntity dragon, double speedIn) {
      this.dragon = dragon;
      this.theWorld = dragon.level();
      this.moveSpeed = speedIn;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      if (this.dragon.isInLove() && this.dragon.canMove()) {
         this.targetMate = this.getNearbyMate();
         return this.targetMate != null;
      } else {
         return false;
      }
   }

   public boolean continueExecuting() {
      return this.targetMate.isAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
   }

   public void stop() {
      this.targetMate = null;
      this.spawnBabyDelay = 0;
   }

   public void tick() {
      this.dragon.getLookControl().setLookAt(this.targetMate, 10.0F, this.dragon.getMaxHeadXRot());
      this.dragon.getNavigation().moveTo(this.targetMate.getX(), this.targetMate.getY(), this.targetMate.getZ(), this.moveSpeed);
      this.dragon.setFlying(false);
      this.dragon.setHovering(false);
      this.spawnBabyDelay++;
      if (this.spawnBabyDelay >= 60 && this.dragon.distanceTo(this.targetMate) < 35.0F) {
         this.spawnBaby();
      }
   }

   private DragonBaseEntity getNearbyMate() {
      List<? extends DragonBaseEntity> list = this.theWorld
         .getEntitiesOfClass(this.dragon.getClass(), this.dragon.getBoundingBox().inflate(180.0, 180.0, 180.0));
      double d0 = Double.MAX_VALUE;
      DragonBaseEntity mate = null;

      for (DragonBaseEntity partner : list) {
         if (this.dragon.canMate(partner)) {
            double d1 = this.dragon.distanceToSqr(partner);
            if (d1 < d0) {
               mate = partner;
               d0 = d1;
            }
         }
      }

      return mate;
   }

   private void spawnBaby() {
      DragonEggEntity egg = this.dragon.createEgg();
      if (egg != null) {
         this.dragon.setAge(6000);
         this.targetMate.setAge(6000);
         this.dragon.resetLove();
         this.targetMate.resetLove();
         int nestX = (int)(this.dragon.isMale() ? this.targetMate.getX() : this.dragon.getX());
         int nestY = (int)(this.dragon.isMale() ? this.targetMate.getY() : this.dragon.getY()) - 1;
         int nestZ = (int)(this.dragon.isMale() ? this.targetMate.getZ() : this.dragon.getZ());
         egg.moveTo(nestX - 0.5F, nestY + 1.0F, nestZ - 0.5F, 0.0F, 0.0F);
         this.theWorld.addFreshEntity(egg);
         RandomSource random = this.dragon.getRandom();

         for (int i = 0; i < 17; i++) {
            double d0 = random.nextGaussian() * 0.02;
            double d1 = random.nextGaussian() * 0.02;
            double d2 = random.nextGaussian() * 0.02;
            double d3 = random.nextDouble() * this.dragon.getBbWidth() * 2.0 - this.dragon.getBbWidth();
            double d4 = 0.5 + random.nextDouble() * this.dragon.getBbHeight();
            double d5 = random.nextDouble() * this.dragon.getBbWidth() * 2.0 - this.dragon.getBbWidth();
            this.theWorld.addParticle(ParticleTypes.HEART, this.dragon.getX() + d3, this.dragon.getY() + d4, this.dragon.getZ() + d5, d0, d1, d2);
         }

         BlockPos eggPos = new BlockPos(nestX - 2, nestY, nestZ - 2);
         BlockPos dirtPos = eggPos.offset(1, 0, 1);

         for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
               BlockPos add = eggPos.offset(x, 0, z);
               BlockState prevState = this.theWorld.getBlockState(add);
               if (prevState.canBeReplaced()
                  || this.theWorld.getBlockState(add).is(BlockTags.DIRT)
                  || this.theWorld.getBlockState(add).getDestroySpeed(this.theWorld, add) < 5.0F
                  || this.theWorld.getBlockState(add).getDestroySpeed(this.theWorld, add) >= 0.0F) {
                  this.theWorld.setBlockAndUpdate(add, NEST);
               }
            }
         }

         if (this.theWorld.getBlockState(dirtPos).canBeReplaced() || this.theWorld.getBlockState(dirtPos) == NEST) {
            this.theWorld.setBlockAndUpdate(dirtPos, Blocks.DIRT_PATH.defaultBlockState());
         }

         if (this.theWorld.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.theWorld.addFreshEntity(new ExperienceOrb(this.theWorld, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ(), random.nextInt(15) + 10));
         }
      }
   }
}
