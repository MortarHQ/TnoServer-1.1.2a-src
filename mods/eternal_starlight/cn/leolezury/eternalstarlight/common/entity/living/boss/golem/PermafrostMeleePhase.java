package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class PermafrostMeleePhase extends BehaviorPhase<Permafrost> {
   public static final int ID = 1;

   public PermafrostMeleePhase() {
      super(1, 1, 20, 100, 2);
   }

   public boolean canStart(Permafrost entity, boolean cooldownOver) {
      boolean canReachGround = false;
      LivingEntity target = entity.getTarget();
      if (target != null) {
         BlockHitResult result = entity.level()
            .clip(new ClipContext(entity.position(), new Vec3(entity.getX(), target.getY() - 5.0, entity.getZ()), Block.COLLIDER, Fluid.NONE, entity));
         canReachGround = result.getType() != Type.MISS;
      }

      return cooldownOver && this.canReachTarget(entity, 4.0) && canReachGround;
   }

   public void tick(Permafrost entity) {
      if (entity.getTarget() != null) {
         LivingEntity target = entity.getTarget();
         ESEntityUtil.instantLook(entity, target.getEyePosition());
      }

      entity.hurtMarked = true;
      entity.addDeltaMovement(new Vec3(0.0, entity.getBehaviorTicks() <= 7 ? 0.12 : -0.1, 0.0));
   }

   public boolean canContinue(Permafrost entity) {
      return true;
   }
}
