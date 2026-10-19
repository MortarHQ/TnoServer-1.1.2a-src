package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.entity.projectile.FrozenTube;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PermafrostRangedPhase extends BehaviorPhase<Permafrost> {
   public static final int ID = 4;

   public PermafrostRangedPhase() {
      super(4, 1, 65, 200);
   }

   public boolean canStart(Permafrost entity, boolean cooldownOver) {
      return cooldownOver && this.canReachTarget(entity, 20.0);
   }

   public void tick(Permafrost entity) {
      Level level = entity.level();
      if (entity.getTarget() != null) {
         LivingEntity target = entity.getTarget();
         ESEntityUtil.instantLook(entity, target.getEyePosition());
         Vec3 launchPos = entity.position().add(0.0, entity.getBbHeight() / 2.0F, 0.0);
         if (entity.getBehaviorTicks() >= 21 && entity.getBehaviorTicks() <= 45) {
            this.performDefaultMeleeAttack(entity, 1.5, true, 360.0F, e -> {
               e.hurtMarked = true;
               e.addDeltaMovement(e.position().subtract(entity.position()).normalize().scale(2.0));
            });
            if (entity.getBehaviorTicks() % 3 == 0) {
               for (int i = 0; i <= 5; i++) {
                  Vec3 targetPos = i == 0
                     ? target.position().add(0.0, target.getBbHeight() / 2.0F, 0.0)
                     : ESMathUtil.rotationToPosition(launchPos, 1.0F, 15.0F, 360.0F * i / 5.0F + entity.getRandom().nextInt(72));
                  Vec3 delta = targetPos.subtract(launchPos).normalize();
                  FrozenTube tube = new FrozenTube(level, entity);
                  tube.shoot(delta.x, delta.y, delta.z, i == 0 ? 1.25F : 0.5F, i == 0 ? 0.1F : 0.3F);
                  tube.setPos(launchPos);
                  tube.playSound(ESSoundEvents.FROZEN_TUBE_THROW.get());
                  level.addFreshEntity(tube);
               }
            }
         }
      }
   }

   public boolean canContinue(Permafrost entity) {
      return true;
   }
}
