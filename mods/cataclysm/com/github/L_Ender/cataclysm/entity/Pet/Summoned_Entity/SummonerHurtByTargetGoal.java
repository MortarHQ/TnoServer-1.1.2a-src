package com.github.L_Ender.cataclysm.entity.Pet.Summoned_Entity;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class SummonerHurtByTargetGoal extends TargetGoal {
   private final Abstract_Summoned_Entity tameAnimal;
   private LivingEntity ownerLastHurtBy;
   private int timestamp;

   public SummonerHurtByTargetGoal(Abstract_Summoned_Entity tameAnimal) {
      super(tameAnimal, false);
      this.tameAnimal = tameAnimal;
      this.setFlags(EnumSet.of(Flag.TARGET));
   }

   public boolean canUse() {
      if (this.tameAnimal.isTame()) {
         LivingEntity livingentity = this.tameAnimal.getOwner();
         if (livingentity == null) {
            return false;
         }

         this.ownerLastHurtBy = livingentity.getLastHurtByMob();
         int i = livingentity.getLastHurtByMobTimestamp();
         return i != this.timestamp
            && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT)
            && this.tameAnimal.wantsToAttack(this.ownerLastHurtBy, livingentity);
      } else {
         return false;
      }
   }

   public void start() {
      this.mob.setTarget(this.ownerLastHurtBy);
      LivingEntity livingentity = this.tameAnimal.getOwner();
      if (livingentity != null) {
         this.timestamp = livingentity.getLastHurtByMobTimestamp();
      }

      super.start();
   }
}
