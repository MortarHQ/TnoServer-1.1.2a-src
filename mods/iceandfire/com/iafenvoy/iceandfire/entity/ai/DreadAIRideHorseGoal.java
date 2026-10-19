package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.DreadKnightEntity;
import com.iafenvoy.iceandfire.util.IafMath;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

public class DreadAIRideHorseGoal extends Goal {
   private final DreadKnightEntity knight;
   private AbstractHorse horse;
   private List<AbstractHorse> list = IafMath.emptyAbstractHorseEntityList;

   public DreadAIRideHorseGoal(DreadKnightEntity knight) {
      this.knight = knight;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      if (this.knight.isPassenger()) {
         this.list = IafMath.emptyAbstractHorseEntityList;
         return false;
      }

      if (this.knight.level().getGameTime() % 4L == 0L) {
         this.list = this.knight
            .level()
            .getEntitiesOfClass(AbstractHorse.class, this.knight.getBoundingBox().inflate(16.0, 7.0, 16.0), entity -> !entity.isVehicle());
      }

      if (this.list.isEmpty()) {
         return false;
      }

      this.horse = this.list.getFirst();
      return true;
   }

   public boolean canContinueToUse() {
      return !this.knight.isPassenger() && this.horse != null && !this.horse.isVehicle();
   }

   public void start() {
      this.horse.getNavigation().stop();
   }

   public void stop() {
      this.horse = null;
      this.knight.getNavigation().stop();
   }

   public void tick() {
      this.knight.getLookControl().setLookAt(this.horse, 30.0F, 30.0F);
      this.knight.getNavigation().moveTo(this.horse, 1.2);
      if (this.knight.distanceToSqr(this.horse) < 4.0) {
         this.horse.setTamed(true);
         this.knight.getNavigation().stop();
         this.knight.startRiding(this.horse);
      }
   }
}
