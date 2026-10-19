package com.github.L_Ender.cataclysm.entity.Pet.Summoned_Entity;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class SummonerHurtTargetGoal extends TargetGoal {
   private final Abstract_Summoned_Entity tameAnimal;
   private LivingEntity ownerLastHurt;
   private int timestamp;

   public SummonerHurtTargetGoal(Abstract_Summoned_Entity tameAnimal) {
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

         this.ownerLastHurt = livingentity.getLastHurtMob();
         int i = livingentity.getLastHurtMobTimestamp();
         return i != this.timestamp
            && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT)
            && this.tameAnimal.wantsToAttack(this.ownerLastHurt, livingentity);
      } else {
         return false;
      }
   }

   public void start() {
      this.mob.setTarget(this.ownerLastHurt);
      LivingEntity livingentity = this.tameAnimal.getOwner();
      if (livingentity != null) {
         this.timestamp = livingentity.getLastHurtMobTimestamp();
      }

      super.start();
   }
}
