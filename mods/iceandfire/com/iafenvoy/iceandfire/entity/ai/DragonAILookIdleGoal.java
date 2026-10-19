package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import java.util.EnumSet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class DragonAILookIdleGoal extends Goal {
   private final DragonBaseEntity dragon;
   private double lookX;
   private double lookZ;
   private int idleTime;

   public DragonAILookIdleGoal(DragonBaseEntity prehistoric) {
      this.dragon = prehistoric;
      this.setFlags(EnumSet.of(Flag.LOOK));
   }

   public boolean canUse() {
      return this.dragon.canMove() && this.dragon.getAnimation() != DragonBaseEntity.ANIMATION_SHAKEPREY && !this.dragon.isFuelingForge()
         ? this.dragon.getRandom().nextFloat() < 0.02F
         : false;
   }

   public boolean canContinueToUse() {
      return this.idleTime >= 0 && this.dragon.canMove();
   }

   public void start() {
      double d0 = (Math.PI * 2) * this.dragon.getRandom().nextDouble();
      this.lookX = Mth.cos((float)d0);
      this.lookZ = Mth.sin((float)d0);
      this.idleTime = 20 + this.dragon.getRandom().nextInt(20);
   }

   public void tick() {
      if (this.idleTime > 0) {
         this.idleTime--;
      }

      this.dragon
         .getLookControl()
         .setLookAt(
            this.dragon.getX() + this.lookX,
            this.dragon.getY() + this.dragon.getEyeHeight(),
            this.dragon.getZ() + this.lookZ,
            this.dragon.getMaxHeadYRot(),
            this.dragon.getMaxHeadXRot()
         );
   }
}
