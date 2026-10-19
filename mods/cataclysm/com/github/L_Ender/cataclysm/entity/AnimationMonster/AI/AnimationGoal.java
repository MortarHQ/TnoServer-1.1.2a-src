package com.github.L_Ender.cataclysm.entity.AnimationMonster.AI;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.LLibrary_Monster;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public abstract class AnimationGoal<T extends LLibrary_Monster & IAnimatedEntity> extends Goal {
   protected final T entity;

   protected AnimationGoal(T entity) {
      this(entity, true);
   }

   protected AnimationGoal(T entity, boolean interruptsAI) {
      this.entity = entity;
      if (interruptsAI) {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }
   }

   public boolean canUse() {
      return this.test(this.entity.getAnimation());
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   protected abstract boolean test(Animation var1);
}
