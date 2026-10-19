package com.bobmowzie.mowziesmobs.server.ai;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaMinion;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;

public final class EntityAIUmvuthanaTrade extends Goal {
   private final EntityUmvuthanaMinion umvuthana;

   public EntityAIUmvuthanaTrade(EntityUmvuthanaMinion umvuthana) {
      this.umvuthana = umvuthana;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.TARGET));
   }

   public boolean canUse() {
      if (this.umvuthana.isAlive() && !this.umvuthana.isInWater() && this.umvuthana.onGround() && !this.umvuthana.hurtMarked) {
         Player plyr = this.umvuthana.getCustomer();
         return plyr != null && this.umvuthana.distanceToSqr(plyr) <= 16.0 && plyr.containerMenu != null;
      } else {
         return false;
      }
   }

   public void start() {
      this.umvuthana.getNavigation().stop();
   }

   public void stop() {
      this.umvuthana.setCustomer(null);
   }
}
