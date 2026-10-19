package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.CockatriceEntity;
import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class CockatriceAITargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
   private final CockatriceEntity cockatrice;

   public CockatriceAITargetGoal(CockatriceEntity entityIn, Class<T> classTarget, boolean checkSight, Predicate<LivingEntity> targetSelector) {
      super(entityIn, classTarget, 0, checkSight, false, targetSelector);
      this.cockatrice = entityIn;
      this.setFlags(EnumSet.of(Flag.TARGET));
   }

   public boolean canUse() {
      if (this.mob.getRandom().nextInt(20) != 0 || this.cockatrice.level().getDifficulty() == Difficulty.PEACEFUL) {
         return false;
      } else if (!super.canUse() || this.target == null || this.target.getClass().equals(this.cockatrice.getClass())) {
         return false;
      } else {
         return this.target instanceof Player && !this.cockatrice.isOwnedBy(this.target)
            ? !this.cockatrice.isTame()
            : !this.cockatrice.isOwnedBy(this.target) && this.cockatrice.canMove();
      }
   }
}
