package me.wesley1808.servercore.mixin.features.activation_range.inactive_ticks;

import me.wesley1808.servercore.common.interfaces.activation_range.Inactive;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GoalSelector.class)
public abstract class GoalSelectorMixin implements Inactive {
   @Unique
   private int servercore$curRate;

   @Shadow
   public abstract void tick();

   @Override
   public void servercore$inactiveTick() {
      if (++this.servercore$curRate % 20 == 0) {
         this.tick();
      }
   }
}
