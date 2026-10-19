package com.faboslav.friendsandfoes.common.entity.ai.goal.mauler;

import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;

public final class MaulerLookAroundGoal extends RandomLookAroundGoal {
   private final MaulerEntity mauler;

   public MaulerLookAroundGoal(MaulerEntity mauler) {
      super(mauler);
      this.mauler = mauler;
   }

   public boolean canUse() {
      return this.mauler.isBurrowedDown() ? false : super.canUse();
   }
}
