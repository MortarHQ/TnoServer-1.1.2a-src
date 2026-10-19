package com.bobmowzie.mowziesmobs.server.ai;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaMinion;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;

public class EntityAIUmvuthanaTradeLook extends LookAtPlayerGoal {
   private final EntityUmvuthanaMinion umvuthana;

   public EntityAIUmvuthanaTradeLook(EntityUmvuthanaMinion umvuthana) {
      super(umvuthana, Player.class, 8.0F);
      this.umvuthana = umvuthana;
      this.setFlags(EnumSet.of(Flag.LOOK));
   }

   public boolean canUse() {
      if (this.umvuthana.isTrading()) {
         this.lookAt = this.umvuthana.getCustomer();
         return true;
      } else {
         return false;
      }
   }
}
