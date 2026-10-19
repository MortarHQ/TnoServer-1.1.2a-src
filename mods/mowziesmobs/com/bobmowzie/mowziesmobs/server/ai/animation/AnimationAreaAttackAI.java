package com.bobmowzie.mowziesmobs.server.ai.animation;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class AnimationAreaAttackAI<T extends MowzieEntity & IAnimatedEntity> extends AnimationAttackAI<T> {
   private final float arc;
   private final float height;
   private final boolean faceTarget;

   public AnimationAreaAttackAI(
      T entity,
      Animation animation,
      SoundEvent attackSound,
      SoundEvent hitSound,
      float applyKnockback,
      float range,
      float height,
      float arc,
      float damageMultiplier,
      int damageFrame
   ) {
      this(entity, animation, attackSound, hitSound, applyKnockback, range, height, arc, damageMultiplier, damageFrame, true);
   }

   public AnimationAreaAttackAI(
      T entity,
      Animation animation,
      SoundEvent attackSound,
      SoundEvent hitSound,
      float applyKnockback,
      float range,
      float height,
      float arc,
      float damageMultiplier,
      int damageFrame,
      boolean faceTarget
   ) {
      super(entity, animation, attackSound, hitSound, applyKnockback, range, damageMultiplier, damageFrame);
      this.arc = arc;
      this.height = height;
      this.faceTarget = faceTarget;
      if (faceTarget) {
         this.setFlags(EnumSet.of(Flag.LOOK));
      }
   }

   @Override
   public void start() {
      super.start();
   }

   @Override
   public void tick() {
      if (this.faceTarget && this.entity.getAnimationTick() < this.damageFrame && this.entityTarget != null) {
         this.entity.lookAt(this.entityTarget, 30.0F, 30.0F);
      } else if (this.entity.getAnimationTick() == this.damageFrame) {
         this.hitEntities();
      }
   }

   public void hitEntities() {
      List<LivingEntity> entitiesHit = this.entity.getEntityLivingBaseNearby(this.range, this.height, this.range, this.range);
      boolean hit = false;

      for (LivingEntity entityHit : entitiesHit) {
         float entityHitAngle = (float)(
            (Math.atan2(entityHit.getZ() - this.entity.getZ(), entityHit.getX() - this.entity.getX()) * (180.0 / Math.PI) - 90.0) % 360.0
         );
         float entityAttackingAngle = this.entity.yBodyRot % 360.0F;
         if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
         }

         if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
         }

         float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
         float entityHitDistance = (float)Math.sqrt(
               (entityHit.getZ() - this.entity.getZ()) * (entityHit.getZ() - this.entity.getZ())
                  + (entityHit.getX() - this.entity.getX()) * (entityHit.getX() - this.entity.getX())
            )
            - entityHit.getBbWidth() / 2.0F;
         if (entityHitDistance <= this.range && entityRelativeAngle <= this.arc / 2.0F && entityRelativeAngle >= -this.arc / 2.0F
            || entityRelativeAngle >= 360.0F - this.arc / 2.0F
            || entityRelativeAngle <= -360.0F + this.arc / 2.0F) {
            this.entity.doHurtTarget(entityHit, this.damageMultiplier, this.applyKnockbackMultiplier);
            this.onAttack(entityHit, this.damageMultiplier, this.applyKnockbackMultiplier);
            hit = true;
         }
      }

      if (hit && this.hitSound != null) {
         this.entity.playSound(this.hitSound, 1.0F, 1.0F);
      }

      if (this.attackSound != null) {
         this.entity.playSound(this.attackSound, 1.0F, 1.0F);
      }
   }
}
