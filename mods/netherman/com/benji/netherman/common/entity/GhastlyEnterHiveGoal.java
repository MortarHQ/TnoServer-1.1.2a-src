package com.benji.netherman.common.entity;

import com.benji.netherman.common.block.entity.GhastlyNestBlockEntity;
import com.benji.netherman.init.ModBlocks;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.block.Block;

public class GhastlyEnterHiveGoal extends Goal {
   private final GhastlyEntity ghastly;
   private BlockPos targetHive = null;

   public GhastlyEnterHiveGoal(GhastlyEntity ghastly) {
      this.ghastly = ghastly;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      if (!this.ghastly.isTame() && this.ghastly.eatTicks <= 0) {
         if (this.ghastly.getRandom().nextInt(40) == 0) {
            BlockPos mobPos = this.ghastly.blockPosition();

            for (int x = -10; x <= 10; x++) {
               for (int y = -5; y <= 5; y++) {
                  for (int z = -10; z <= 10; z++) {
                     BlockPos checkPos = mobPos.offset(x, y, z);
                     if (this.ghastly.level().getBlockState(checkPos).is((Block)ModBlocks.GHASTLY_NEST.get())) {
                        this.targetHive = checkPos;
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public void start() {
      this.ghastly.getNavigation().moveTo(this.targetHive.getX() + 0.5, this.targetHive.getY() + 0.5, this.targetHive.getZ() + 0.5, 1.2);
   }

   public void tick() {
      if (this.targetHive != null) {
         double dist = this.ghastly.distanceToSqr(this.targetHive.getX() + 0.5, this.targetHive.getY() + 0.5, this.targetHive.getZ() + 0.5);
         if (dist < 2.0) {
            if (this.ghastly.level().getBlockEntity(this.targetHive) instanceof GhastlyNestBlockEntity nest && nest.addGhastly(this.ghastly)) {
               this.ghastly.level().playSound(null, this.targetHive, SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            this.targetHive = null;
         }
      }
   }

   public boolean canContinueToUse() {
      return this.targetHive != null && this.ghastly.level().getBlockState(this.targetHive).is((Block)ModBlocks.GHASTLY_NEST.get());
   }
}
