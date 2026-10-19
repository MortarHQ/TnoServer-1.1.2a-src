package com.github.L_Ender.cataclysm.entity.Pet.AI;

import com.github.L_Ender.cataclysm.entity.Pet.InternalAnimationPet;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class InternalPetStateGoal extends Goal {
   protected final InternalAnimationPet entity;
   private final int getattackstate;
   private final int attackstate;
   protected final int attackendstate;
   private final int attackfinaltick;
   protected final int attackseetick;

   public InternalPetStateGoal(InternalAnimationPet entity, int getattackstate, int attackstate, int attackendstate, int attackfinaltick, int attackseetick) {
      this.entity = entity;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      this.getattackstate = getattackstate;
      this.attackstate = attackstate;
      this.attackendstate = attackendstate;
      this.attackfinaltick = attackfinaltick;
      this.attackseetick = attackseetick;
   }

   public InternalPetStateGoal(
      InternalAnimationPet entity, int getattackstate, int attackstate, int attackendstate, int attackfinaltick, int attackseetick, boolean interruptsAI
   ) {
      this.entity = entity;
      if (interruptsAI) {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      this.getattackstate = getattackstate;
      this.attackstate = attackstate;
      this.attackendstate = attackendstate;
      this.attackfinaltick = attackfinaltick;
      this.attackseetick = attackseetick;
   }

   public InternalPetStateGoal(
      InternalAnimationPet entity,
      int getattackstate,
      int attackstate,
      int attackendstate,
      int attackfinaltick,
      int attackseetick,
      EnumSet<Flag> interruptFlagTypes
   ) {
      this.entity = entity;
      this.setFlags(interruptFlagTypes);
      this.getattackstate = getattackstate;
      this.attackstate = attackstate;
      this.attackendstate = attackendstate;
      this.attackfinaltick = attackfinaltick;
      this.attackseetick = attackseetick;
   }

   public boolean canUse() {
      return this.entity.getAttackState() == this.getattackstate;
   }

   public void start() {
      if (this.getattackstate != this.attackstate) {
         this.entity.setAttackState(this.attackstate);
      }
   }

   public void stop() {
      this.entity.setAttackState(this.attackendstate);
   }

   public boolean canContinueToUse() {
      return this.attackfinaltick > 0 ? this.entity.attackTicks <= this.attackfinaltick && this.entity.getAttackState() == this.attackstate : this.canUse();
   }

   public void tick() {
      LivingEntity target = this.entity.getTarget();
      if (this.entity.attackTicks < this.attackseetick && target != null) {
         this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         this.entity.lookAt(target, 30.0F, 30.0F);
      } else {
         this.entity.setYRot(this.entity.yRotO);
      }
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }
}
