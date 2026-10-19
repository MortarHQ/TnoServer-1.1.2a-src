package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI;

import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Internal_Animation_Monster;
import java.util.EnumSet;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class InternalAttackGoal extends Goal {
   protected final Internal_Animation_Monster entity;
   protected final int getattackstate;
   protected final int attackstate;
   protected final int attackendstate;
   protected final int attackMaxtick;
   protected final int attackseetick;
   protected final float attackrange;

   public InternalAttackGoal(
      Internal_Animation_Monster entity, int getattackstate, int attackstate, int attackendstate, int attackMaxtick, int attackseetick, float attackrange
   ) {
      this.entity = entity;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      this.getattackstate = getattackstate;
      this.attackstate = attackstate;
      this.attackendstate = attackendstate;
      this.attackMaxtick = attackMaxtick;
      this.attackseetick = attackseetick;
      this.attackrange = attackrange;
   }

   public InternalAttackGoal(
      Internal_Animation_Monster entity,
      int getattackstate,
      int attackstate,
      int attackendstate,
      int attackMaxtick,
      int attackseetick,
      float attackrange,
      EnumSet<Flag> interruptFlagTypes
   ) {
      this.entity = entity;
      this.setFlags(interruptFlagTypes);
      this.getattackstate = getattackstate;
      this.attackstate = attackstate;
      this.attackendstate = attackendstate;
      this.attackMaxtick = attackMaxtick;
      this.attackseetick = attackseetick;
      this.attackrange = attackrange;
   }

   public boolean canUse() {
      LivingEntity target = this.entity.getTarget();
      return target != null && target.isAlive() && this.entity.distanceTo(target) < this.attackrange && this.entity.getAttackState() == this.getattackstate;
   }

   public boolean isInterruptable() {
      return false;
   }

   public void start() {
      this.entity.setAttackState(this.attackstate);
      this.entity.getNavigation().stop();
   }

   public void stop() {
      this.EndState();
      LivingEntity target = this.entity.getTarget();
      if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
         this.entity.setTarget((LivingEntity)null);
      }

      this.entity.getNavigation().stop();
      if (this.entity.getTarget() == null) {
         this.entity.setAggressive(false);
      }
   }

   protected void EndState() {
      this.entity.setAttackState(this.attackendstate);
   }

   public boolean canContinueToUse() {
      return this.entity.getAttackState() == this.attackstate && this.entity.attackTicks <= this.attackMaxtick;
   }

   public void tick() {
      LivingEntity target = this.entity.getTarget();
      if (target != null) {
         boolean flag = this.entity.attackTicks < this.attackseetick;
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

      this.entity.getNavigation().stop();
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }
}
