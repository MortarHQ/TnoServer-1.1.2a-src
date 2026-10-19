package com.faboslav.friendsandfoes.common.entity.ai.goal.mauler;

import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public final class MaulerMeeleAttackGoal extends MeleeAttackGoal {
   public MaulerMeeleAttackGoal(PathfinderMob mob, double speed, boolean pauseWhenMobIdle) {
      super(mob, speed, pauseWhenMobIdle);
   }

   public void start() {
      MaulerEntity mauler = (MaulerEntity)this.mob;
      if (mauler.isBurrowedDown()) {
         mauler.burrowDownGoal.stop();
      }

      super.start();
   }

   public void tick() {
      MaulerEntity mauler = (MaulerEntity)this.mob;
      if (!(mauler.getBurrowingDownAnimationProgress() > 0.0F)) {
         super.tick();
      }
   }
}
