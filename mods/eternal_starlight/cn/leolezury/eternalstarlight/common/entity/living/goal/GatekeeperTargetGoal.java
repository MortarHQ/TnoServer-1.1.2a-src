package cn.leolezury.eternalstarlight.common.entity.living.goal;

import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class GatekeeperTargetGoal extends TargetGoal {
   private static final TargetingConditions GATEKEEPER_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();

   public GatekeeperTargetGoal(PathfinderMob pathfinderMob) {
      super(pathfinderMob, false);
      this.setFlags(EnumSet.of(Flag.TARGET));
   }

   public boolean canUse() {
      if (this.mob instanceof TheGatekeeper gatekeeper && gatekeeper.getFightTarget().isPresent()) {
         Player player = gatekeeper.getFightTarget().get();
         return this.canAttack(player, GATEKEEPER_TARGETING);
      } else {
         return false;
      }
   }

   public void start() {
      if (this.mob instanceof TheGatekeeper gatekeeper && gatekeeper.getFightTarget().isPresent()) {
         this.mob.setTarget((LivingEntity)gatekeeper.getFightTarget().get());
         this.targetMob = this.mob.getTarget();
         this.unseenMemoryTicks = 300;
      }

      super.start();
   }
}
