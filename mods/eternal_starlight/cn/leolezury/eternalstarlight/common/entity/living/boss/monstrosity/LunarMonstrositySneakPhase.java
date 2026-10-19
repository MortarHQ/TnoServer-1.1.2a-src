package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESBlockUtil;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class LunarMonstrositySneakPhase extends BehaviorPhase<LunarMonstrosity> {
   public static final int ID = 6;

   public LunarMonstrositySneakPhase() {
      super(6, 1, 200, 0, 7);
   }

   public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
      return false;
   }

   public void tick(LunarMonstrosity entity) {
      entity.clearFire();
      if ((
            entity.isInLava()
               || ESBlockUtil.getBlocksInBoundingBox(entity.getNormalStateBoundingBox().inflate(1.0))
                  .stream()
                  .anyMatch(pos -> entity.level().getFluidState(pos).is(FluidTags.LAVA))
         )
         && entity.tickCount % 20 == 0) {
         Vec3 fleePos = LandRandomPos.getPosAway(entity, 20, 8, entity.position());
         if (fleePos != null) {
            entity.randomTeleport(fleePos.x, fleePos.y, fleePos.z, false);
         }
      }
   }

   public boolean canContinue(LunarMonstrosity entity) {
      return true;
   }
}
