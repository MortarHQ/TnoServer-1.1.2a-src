package com.bobmowzie.mowziesmobs.server.ai;

import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class UseAbilityAI<T extends MowzieGeckoEntity> extends Goal {
   protected final T entity;
   protected AbilityType abilityType;
   protected boolean interruptAbilityOnEnd = true;

   public UseAbilityAI(T entity, AbilityType ability) {
      this(entity, ability, true);
   }

   public UseAbilityAI(T entity, AbilityType ability, boolean interruptsAI) {
      this.entity = entity;
      this.abilityType = ability;
      if (interruptsAI) {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }
   }

   public UseAbilityAI(T entity, AbilityType ability, boolean interruptsAI, boolean interruptAbilityOnEnd) {
      this.entity = entity;
      this.abilityType = ability;
      this.interruptAbilityOnEnd = interruptAbilityOnEnd;
      if (interruptsAI) {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }
   }

   public boolean canUse() {
      return this.entity.getActiveAbility() == null ? false : this.entity.getActiveAbility().getAbilityType() == this.abilityType;
   }

   public void start() {
      super.start();
   }

   public void stop() {
      super.stop();
      if (this.interruptAbilityOnEnd) {
         Ability<?> ability = this.entity.getActiveAbility();
         if (ability != null && ability.getAbilityType() == this.abilityType) {
            AbilityHandler.INSTANCE.sendInterruptAbilityMessage(this.entity, ability.getAbilityType());
         }
      }
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }
}
