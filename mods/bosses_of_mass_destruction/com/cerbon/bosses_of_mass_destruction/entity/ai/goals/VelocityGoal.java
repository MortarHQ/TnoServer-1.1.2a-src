package com.cerbon.bosses_of_mass_destruction.entity.ai.goals;

import com.cerbon.bosses_of_mass_destruction.entity.ai.ISteering;
import com.cerbon.bosses_of_mass_destruction.entity.ai.ITargetSelector;
import java.util.EnumSet;
import java.util.function.Consumer;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class VelocityGoal extends Goal {
   private final Consumer<Vec3> onTargetSelected;
   private final ISteering steering;
   private final ITargetSelector targetSelector;

   public VelocityGoal(Consumer<Vec3> onTargetSelected, ISteering steering, ITargetSelector targetSelector) {
      this.onTargetSelected = onTargetSelected;
      this.steering = steering;
      this.targetSelector = targetSelector;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      return true;
   }

   public void tick() {
      Vec3 target = this.targetSelector.getTarget();
      Vec3 velocity = this.steering.accelerateTo(target);
      this.onTargetSelected.accept(velocity);
      super.tick();
   }
}
