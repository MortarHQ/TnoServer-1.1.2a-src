package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.PixieEntity;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class PixieAIEnterHouseGoal extends Goal {
   final PixieEntity pixie;
   final RandomSource random;

   public PixieAIEnterHouseGoal(PixieEntity pixieEntityIn) {
      this.pixie = pixieEntityIn;
      this.random = pixieEntityIn.getRandom();
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      if (!this.pixie.isOwnerClose()
         && !this.pixie.getMoveControl().hasWanted()
         && !this.pixie.isPixieSitting()
         && this.random.nextInt(20) == 0
         && this.pixie.ticksUntilHouseAI == 0) {
         BlockPos blockpos1 = PixieEntity.findAHouse(this.pixie, this.pixie.level());
         return !blockpos1.toString().equals(this.pixie.blockPosition().toString());
      } else {
         return false;
      }
   }

   public boolean canContinueToUse() {
      return false;
   }

   public void tick() {
      for (int i = 0; i < 3; i++) {
         BlockPos blockpos1 = PixieEntity.findAHouse(this.pixie, this.pixie.level());
         this.pixie.getMoveControl().setWantedPosition(blockpos1.getX() + 0.5, blockpos1.getY() + 0.5, blockpos1.getZ() + 0.5, 0.25);
         this.pixie.setHousePosition(blockpos1);
         if (this.pixie.getTarget() == null) {
            this.pixie.getLookControl().setLookAt(blockpos1.getX() + 0.5, blockpos1.getY() + 0.5, blockpos1.getZ() + 0.5, 180.0F, 20.0F);
         }
      }
   }
}
