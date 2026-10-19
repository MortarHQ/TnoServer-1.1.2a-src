package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.CockatriceEntity;
import com.iafenvoy.iceandfire.registry.tag.IafItemTags;
import com.iafenvoy.iceandfire.util.IafMath;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;

public class CockatriceAITargetItemsGoal<T extends ItemEntity> extends TargetGoal {
   protected final DragonAITargetItemsGoal.Sorter theNearestAttackableTargetSorter;
   protected final Predicate<? super ItemEntity> targetEntitySelector;
   protected final int targetChance;
   protected ItemEntity targetEntity;
   private List<ItemEntity> list = IafMath.emptyItemEntityList;

   public CockatriceAITargetItemsGoal(CockatriceEntity creature, boolean checkSight) {
      this(creature, checkSight, false);
   }

   public CockatriceAITargetItemsGoal(CockatriceEntity creature, boolean checkSight, boolean onlyNearby) {
      this(creature, 10, checkSight, onlyNearby, null);
   }

   public CockatriceAITargetItemsGoal(CockatriceEntity creature, int chance, boolean checkSight, boolean onlyNearby, Predicate<? super T> targetSelector) {
      super(creature, checkSight, onlyNearby);
      this.theNearestAttackableTargetSorter = new DragonAITargetItemsGoal.Sorter(creature);
      this.targetChance = chance;
      this.targetEntitySelector = item -> item != null && !item.getItem().isEmpty() && item.getItem().is(IafItemTags.HEAL_COCKATRICE);
   }

   public boolean canUse() {
      if (this.targetChance > 0 && this.mob.getRandom().nextInt(this.targetChance) != 0) {
         return false;
      }

      if (((CockatriceEntity)this.mob).canMove() && !(this.mob.getHealth() >= this.mob.getMaxHealth())) {
         if (this.mob.level().getGameTime() % 4L == 0L) {
            this.list = this.mob.level().getEntitiesOfClass(ItemEntity.class, this.getTargetableArea(this.getFollowDistance()), this.targetEntitySelector);
         }

         if (this.list.isEmpty()) {
            return false;
         }

         this.list.sort(this.theNearestAttackableTargetSorter);
         this.targetEntity = this.list.getFirst();
         return true;
      } else {
         this.list = IafMath.emptyItemEntityList;
         return false;
      }
   }

   protected AABB getTargetableArea(double targetDistance) {
      return this.mob.getBoundingBox().inflate(targetDistance, 4.0, targetDistance);
   }

   public void start() {
      this.mob.getNavigation().moveTo(this.targetEntity.getX(), this.targetEntity.getY(), this.targetEntity.getZ(), 1.0);
      super.start();
   }

   public void tick() {
      super.tick();
      if (this.targetEntity != null && this.targetEntity.isAlive()) {
         if (this.mob.distanceToSqr(this.targetEntity) < 1.0) {
            CockatriceEntity cockatrice = (CockatriceEntity)this.mob;
            this.targetEntity.getItem().shrink(1);
            this.mob.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
            cockatrice.heal(8.0F);
            cockatrice.setAnimation(CockatriceEntity.ANIMATION_EAT);
            this.stop();
         }
      } else {
         this.stop();
      }
   }

   public boolean canContinueToUse() {
      return !this.mob.getNavigation().isDone();
   }
}
