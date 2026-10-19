package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.AmphithereEntity;
import com.iafenvoy.iceandfire.registry.tag.IafItemTags;
import com.iafenvoy.iceandfire.util.IafMath;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;

public class AmphithereAITargetItemsGoal extends TargetGoal {
   protected final DragonAITargetItemsGoal.Sorter theNearestAttackableTargetSorter;
   protected final Predicate<? super ItemEntity> targetEntitySelector;
   protected final int targetChance;
   protected ItemEntity targetEntity;
   private List<ItemEntity> list = IafMath.emptyItemEntityList;

   public AmphithereAITargetItemsGoal(Mob creature, boolean checkSight) {
      this(creature, checkSight, false);
   }

   public AmphithereAITargetItemsGoal(Mob creature, boolean checkSight, boolean onlyNearby) {
      this(creature, 20, checkSight, onlyNearby);
   }

   public AmphithereAITargetItemsGoal(Mob creature, int chance, boolean checkSight, boolean onlyNearby) {
      super(creature, checkSight, onlyNearby);
      this.theNearestAttackableTargetSorter = new DragonAITargetItemsGoal.Sorter(creature);
      this.targetChance = chance;
      this.targetEntitySelector = item -> item != null && !item.getItem().isEmpty() && item.getItem().is(IafItemTags.HEAL_AMPITHERE);
      this.setFlags(EnumSet.of(Flag.TARGET));
   }

   public boolean canUse() {
      if (this.targetChance > 0 && this.mob.getRandom().nextInt(this.targetChance) != 0) {
         return false;
      }

      if (!((AmphithereEntity)this.mob).canMove()) {
         this.list = IafMath.emptyItemEntityList;
         return false;
      }

      if (this.targetEntitySelector.test(this.targetEntity)) {
         return true;
      }

      if (this.mob.level().getGameTime() % 4L == 0L) {
         this.list = this.mob.level().getEntitiesOfClass(ItemEntity.class, this.getTargetableArea(this.getFollowDistance()), this.targetEntitySelector);
      }

      if (this.list.isEmpty()) {
         return false;
      }

      this.list.sort(this.theNearestAttackableTargetSorter);
      this.targetEntity = this.list.getFirst();
      return true;
   }

   protected AABB getTargetableArea(double targetDistance) {
      return this.mob.getBoundingBox().inflate(targetDistance, 4.0, targetDistance);
   }

   public void start() {
      this.mob.getNavigation().moveTo(this.targetEntity.getX(), this.targetEntity.getY(), this.targetEntity.getZ(), 1.0);
      super.start();
   }

   public void stop() {
      this.targetEntity = null;
      super.stop();
   }

   public void tick() {
      super.tick();
      if (this.targetEntity == null || !this.targetEntity.isAlive()) {
         this.stop();
      }

      if (this.targetEntity != null && this.targetEntity.isAlive() && this.mob.distanceToSqr(this.targetEntity) < 1.0) {
         AmphithereEntity hippo = (AmphithereEntity)this.mob;
         this.targetEntity.getItem().shrink(1);
         this.mob.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
         hippo.heal(5.0F);
         this.stop();
      }
   }

   public boolean canContinueToUse() {
      return !this.mob.getNavigation().isDone();
   }
}
