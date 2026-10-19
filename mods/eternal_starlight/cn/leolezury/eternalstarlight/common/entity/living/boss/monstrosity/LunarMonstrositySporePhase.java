package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.entity.projectile.LunarSpore;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class LunarMonstrositySporePhase extends BehaviorPhase<LunarMonstrosity> {
   public static final int ID = 2;

   public LunarMonstrositySporePhase() {
      super(2, 1, 100, 200);
   }

   public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
      return cooldownOver && entity.getTarget() != null;
   }

   public void tick(LunarMonstrosity entity) {
      LivingEntity target = entity.getTarget();
      if ((entity.getBehaviorTicks() + 5) % 10 == 0 && target != null) {
         Vec3 shootPos = ESMathUtil.rotationToPosition(
            entity.position().add(0.0, entity.getEyeHeight(), 0.0), entity.getBbWidth() * 1.2F, 0.0F, entity.yBodyRot + 90.0F
         );
         LunarSpore spore = new LunarSpore(entity.level(), entity, shootPos.x, shootPos.y, shootPos.z);
         spore.setNoGravity(true);
         spore.setDeltaMovement(entity.getRayRotationTarget().subtract(shootPos).normalize().scale(0.9));
         entity.level().addFreshEntity(spore);
      }
   }

   public boolean canContinue(LunarMonstrosity entity) {
      return true;
   }
}
