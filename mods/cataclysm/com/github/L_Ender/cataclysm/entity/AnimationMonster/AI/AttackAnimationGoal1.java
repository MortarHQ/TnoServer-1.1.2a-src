package com.github.L_Ender.cataclysm.entity.AnimationMonster.AI;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.LLibrary_Monster;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class AttackAnimationGoal1<T extends LLibrary_Monster & IAnimatedEntity> extends SimpleAnimationGoal<T> {
   private final int look1;
   private final boolean see;

   public AttackAnimationGoal1(T entity, Animation animation, int look1, boolean see) {
      super(entity, animation);
      this.look1 = look1;
      this.see = see;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
   }

   public void tick() {
      LivingEntity target = this.entity.getTarget();
      if (this.see) {
         if (target != null) {
            boolean flag = this.entity.getAnimationTick() < this.look1;
            if (flag) {
               this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
               this.entity.lookAt(target, 30.0F, 30.0F);
            } else {
               this.entity.getLookControl().setLookAt(target, 0.0F, 30.0F);
               this.entity.setYRot(this.entity.yRotO);
            }
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }
      } else if (target != null) {
         boolean flag = this.entity.getAnimationTick() > this.look1;
         if (flag) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
            this.entity.lookAt(target, 30.0F, 30.0F);
         } else {
            this.entity.getLookControl().setLookAt(target, 0.0F, 30.0F);
            this.entity.setYRot(this.entity.yRotO);
         }
      } else {
         this.entity.setYRot(this.entity.yRotO);
      }

      this.entity.setDeltaMovement(0.0, this.entity.getDeltaMovement().y, 0.0);
   }
}
