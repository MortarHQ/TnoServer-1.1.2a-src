package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.IceAndFire;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;

public class IAFLookControl extends LookControl {
   public IAFLookControl(Mob LivingEntityIn) {
      super(LivingEntityIn);
   }

   public void setLookAt(Entity entityIn, float deltaYaw, float deltaPitch) {
      try {
         super.setLookAt(entityIn, deltaYaw, deltaPitch);
      } catch (Exception e) {
         IceAndFire.LOGGER.warn("Stopped a crash from happening relating to faulty looking AI.");
      }
   }
}
