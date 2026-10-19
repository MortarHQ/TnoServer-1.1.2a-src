package com.iafenvoy.iceandfire.entity.ai;

import com.iafenvoy.iceandfire.entity.CockatriceEntity;
import com.iafenvoy.iceandfire.entity.util.IafEntityUtil;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class CockatriceAIAggroLookGoal extends NearestAttackableTargetGoal<Player> {
   private final CockatriceEntity cockatrice;
   private final TargetingConditions predicate;
   private Player player;

   public CockatriceAIAggroLookGoal(CockatriceEntity cockatriceIn) {
      super(cockatriceIn, Player.class, false);
      this.cockatrice = cockatriceIn;
      Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = target -> IafEntityUtil.isEntityLookingAt(target, this.cockatrice, 0.6F)
         && this.cockatrice.distanceTo(target) < this.getFollowDistance();
      this.predicate = TargetingConditions.forCombat().range(25.0).selector(LIVING_ENTITY_SELECTOR);
   }

   public boolean canUse() {
      if (this.cockatrice.isTame()) {
         return false;
      }

      this.player = this.cockatrice.level().getNearestPlayer(this.predicate, this.cockatrice.getX(), this.cockatrice.getY(), this.cockatrice.getZ());
      return this.player != null;
   }

   public void stop() {
      this.player = null;
      super.stop();
   }

   public boolean canContinueToUse() {
      if (this.player == null || this.player.isCreative() || this.player.isSpectator()) {
         return this.targetMob != null && this.targetMob.isAlive() || super.canContinueToUse();
      }

      if (!IafEntityUtil.isEntityLookingAt(this.player, this.cockatrice, 0.4F)) {
         return false;
      }

      this.cockatrice.lookAt(this.player, 10.0F, 10.0F);
      if (!this.cockatrice.isTame()) {
         this.cockatrice.setTargetedEntity(this.player.getId());
         this.cockatrice.setTarget(this.player);
      }

      return true;
   }
}
