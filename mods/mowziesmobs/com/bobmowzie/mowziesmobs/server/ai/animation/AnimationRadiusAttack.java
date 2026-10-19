package com.bobmowzie.mowziesmobs.server.ai.animation;

import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;

public class AnimationRadiusAttack<T extends MowzieEntity & IAnimatedEntity> extends SimpleAnimationAI<T> {
   private final float radius;
   private final float damageMultiplier;
   private final float applyKnockbackMultiplier;
   private final int damageFrame;
   private final boolean pureapplyKnockback;

   public AnimationRadiusAttack(
      T entity, Animation animation, float radius, float damageMultiplier, float applyKnockbackMultiplier, int damageFrame, boolean pureapplyKnockback
   ) {
      super(entity, animation);
      this.radius = radius;
      this.damageMultiplier = damageMultiplier;
      this.applyKnockbackMultiplier = applyKnockbackMultiplier;
      this.damageFrame = damageFrame;
      this.pureapplyKnockback = pureapplyKnockback;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
   }

   public void tick() {
      super.tick();
      if (this.entity.getAnimationTick() == this.damageFrame) {
         for (LivingEntity aHit : this.entity.getEntityLivingBaseNearby(this.radius, 2.0F * this.radius, this.radius, this.radius)) {
            if (!(this.entity instanceof EntityUmvuthi) || !(aHit instanceof LeaderSunstrikeImmune)) {
               this.entity.doHurtTarget(aHit, this.damageMultiplier, this.applyKnockbackMultiplier);
               if (this.pureapplyKnockback && !aHit.isInvulnerable() && (!(aHit instanceof Player) || !((Player)aHit).getAbilities().invulnerable)) {
                  double angle = this.entity.getAngleBetweenEntities(this.entity, aHit);
                  double x = this.applyKnockbackMultiplier * Math.cos(Math.toRadians(angle - 90.0));
                  double z = this.applyKnockbackMultiplier * Math.sin(Math.toRadians(angle - 90.0));
                  aHit.setDeltaMovement(x, 0.3, z);
                  if (aHit instanceof ServerPlayer) {
                     ((ServerPlayer)aHit).connection.send(new ClientboundSetEntityMotionPacket(aHit));
                  }
               }
            }
         }
      }
   }
}
