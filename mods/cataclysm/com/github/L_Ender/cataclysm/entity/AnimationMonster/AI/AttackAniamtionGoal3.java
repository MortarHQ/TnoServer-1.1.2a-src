package com.github.L_Ender.cataclysm.entity.AnimationMonster.AI;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.LLibrary_Monster;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class AttackAniamtionGoal3<T extends LLibrary_Monster & IAnimatedEntity> extends SimpleAnimationGoal<T> {
   public AttackAniamtionGoal3(T entity, Animation animation) {
      super(entity, animation);
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
   }

   public void tick() {
      this.entity.setDeltaMovement(0.0, this.entity.getDeltaMovement().y, 0.0);
   }
}
