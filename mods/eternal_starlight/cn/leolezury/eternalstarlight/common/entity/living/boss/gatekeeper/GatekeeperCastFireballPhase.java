package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.entity.projectile.GatekeeperFireball;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class GatekeeperCastFireballPhase extends BehaviorPhase<TheGatekeeper> {
   public static final int ID = 11;

   public GatekeeperCastFireballPhase() {
      super(11, 1, 37, 200);
   }

   public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
      return cooldownOver && this.canReachTarget(entity, 100.0) && !this.canReachTarget(entity, 20.0);
   }

   public void onStart(TheGatekeeper entity) {
      entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
   }

   public void tick(TheGatekeeper entity) {
      LivingEntity target = entity.getTarget();
      if (target != null && entity.getBehaviorTicks() == 12) {
         for (int i = 0; i < 6; i++) {
            Vec3 pos = ESMathUtil.rotationToPosition(entity.position(), 3.0F, 0.0F, 60.0F * i);
            GatekeeperFireball fireball = new GatekeeperFireball(entity.level(), entity, new Vec3(0.0, 1.0, 0.0));
            fireball.setTarget(target);
            fireball.setPos(pos);
            entity.level().addFreshEntity(fireball);
         }
      }
   }

   public boolean canContinue(TheGatekeeper entity) {
      return true;
   }
}
