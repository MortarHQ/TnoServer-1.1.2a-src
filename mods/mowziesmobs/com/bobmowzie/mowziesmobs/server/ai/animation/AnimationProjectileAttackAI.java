package com.bobmowzie.mowziesmobs.server.ai.animation;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class AnimationProjectileAttackAI<T extends MowzieEntity & IAnimatedEntity & RangedAttackMob> extends SimpleAnimationAI<T> {
   private final int attackFrame;
   private final SoundEvent attackSound;

   public AnimationProjectileAttackAI(T entity, Animation animation, int attackFrame, SoundEvent attackSound) {
      this(entity, animation, attackFrame, attackSound, false);
   }

   public AnimationProjectileAttackAI(T entity, Animation animation, int attackFrame, SoundEvent attackSound, boolean hurtInterrupts) {
      super(entity, animation, true, hurtInterrupts);
      this.attackFrame = attackFrame;
      this.attackSound = attackSound;
   }

   public void tick() {
      super.tick();
      LivingEntity entityTarget = this.entity.getTarget();
      if (entityTarget != null) {
         this.entity.lookAt(entityTarget, 100.0F, 100.0F);
         this.entity.getLookControl().setLookAt(entityTarget, 30.0F, 30.0F);
         if (this.entity.getAnimationTick() == this.attackFrame) {
            this.entity.performRangedAttack(entityTarget, 0.0F);
            if (this.attackSound != null) {
               this.entity.playSound(this.attackSound, 1.0F, 1.0F);
            }
         }
      }
   }
}
