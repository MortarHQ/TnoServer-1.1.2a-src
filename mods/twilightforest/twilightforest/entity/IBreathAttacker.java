package twilightforest.entity;

import net.minecraft.world.entity.Entity;

public interface IBreathAttacker {
   boolean isBreathing();

   void setBreathing(boolean var1);

   void doBreathAttack(Entity var1);
}
