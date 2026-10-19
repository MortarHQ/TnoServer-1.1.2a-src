package com.finderfeed.raids_enhanced.content.entities.engineer;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;

public class ElectromancerMoveControl extends MoveControl {
   public ElectromancerMoveControl(Mob p_24983_) {
      super(p_24983_);
   }

   public void cancelMovement() {
      this.operation = Operation.WAIT;
   }
}
