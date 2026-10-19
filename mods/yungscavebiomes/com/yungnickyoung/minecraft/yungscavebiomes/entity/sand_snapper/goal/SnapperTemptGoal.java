package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ItemModule;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class SnapperTemptGoal extends Goal {
   private final TargetingConditions targetingConditions;
   private final SandSnapperEntity sandSnapper;
   private final double speedModifier;
   private final float minPlayerDistance;
   @Nullable
   private Player player;
   private int cooldownTimer;
   private static final int COOLDOWN = 100;
   private final Ingredient items;

   public SnapperTemptGoal(SandSnapperEntity sandSnapper, double speedModifier, float minPlayerDistance, float maxPlayerDistance) {
      this.sandSnapper = sandSnapper;
      this.speedModifier = speedModifier;
      this.items = Ingredient.of(new ItemLike[]{(ItemLike)ItemModule.PRICKLY_PEACH_ITEM.get()});
      this.minPlayerDistance = minPlayerDistance;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      this.targetingConditions = TargetingConditions.forNonCombat().ignoreLineOfSight().selector(this::isHoldingTemptingItem).range(maxPlayerDistance);
   }

   public boolean canUse() {
      if (this.sandSnapper.recentlyFedTimer > 0) {
         return false;
      } else if (this.cooldownTimer > 0) {
         this.cooldownTimer--;
         return false;
      } else {
         this.player = this.sandSnapper.level().getNearestPlayer(this.targetingConditions, this.sandSnapper);
         return this.player != null;
      }
   }

   public void start() {
      this.sandSnapper.setSubmerged(false);
      this.sandSnapper.setSubmergeLocked(true);
   }

   public void tick() {
      this.sandSnapper.getLookControl().setLookAt(this.player, this.sandSnapper.getMaxHeadYRot() + 20, this.sandSnapper.getMaxHeadXRot());
      double sqrDist = this.sandSnapper.distanceToSqr(this.player);
      if (sqrDist < this.minPlayerDistance * this.minPlayerDistance) {
         this.sandSnapper.getNavigation().stop();
      } else {
         this.sandSnapper.getNavigation().moveTo(this.player, this.speedModifier);
      }
   }

   public boolean canContinueToUse() {
      return this.canUse();
   }

   public void stop() {
      this.sandSnapper.setEating(false);
      this.sandSnapper.setSubmergeLocked(false);
      this.player = null;
      this.sandSnapper.getNavigation().stop();
      this.cooldownTimer = reducedTickDelay(100);
   }

   private boolean isHoldingTemptingItem(LivingEntity livingEntity) {
      return this.items.test(livingEntity.getMainHandItem()) || this.items.test(livingEntity.getOffhandItem());
   }
}
