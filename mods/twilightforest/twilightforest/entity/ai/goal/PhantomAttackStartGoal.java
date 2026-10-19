package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import twilightforest.entity.boss.KnightPhantom;

public class PhantomAttackStartGoal extends Goal {
   private final KnightPhantom boss;

   public PhantomAttackStartGoal(KnightPhantom entity) {
      this.boss = entity;
      this.setFlags(EnumSet.of(Flag.LOOK));
   }

   public boolean canUse() {
      return this.boss.getTarget() != null && this.boss.getCurrentFormation() == KnightPhantom.Formation.ATTACK_PLAYER_START;
   }

   public void tick() {
      LivingEntity target = this.boss.getTarget();
      if (target != null) {
         BlockPos targetPos = BlockPos.containing(target.xOld, target.yOld, target.zOld);
         if (this.boss.isMobWithinHomeArea(this.boss)) {
            this.boss.setChargePos(targetPos);
         } else {
            this.boss.setChargePos(this.boss.getRestrictionPoint().pos());
         }
      }
   }
}
