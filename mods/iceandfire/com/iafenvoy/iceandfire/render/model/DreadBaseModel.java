package com.iafenvoy.iceandfire.render.model;

import com.iafenvoy.uranus.animation.Animation;
import com.iafenvoy.uranus.animation.IAnimatedEntity;
import net.minecraft.world.entity.LivingEntity;

abstract class DreadBaseModel<T extends LivingEntity & IAnimatedEntity> extends BipedBaseModel<T> {
   public abstract Animation getSpawnAnimation();

   @Override
   public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
      this.setRotationAnglesSpawn(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
   }

   public void setRotationAnglesSpawn(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entityIn.getAnimation() == this.getSpawnAnimation() && entityIn.getAnimationTick() < 30) {
         this.flap(this.armRight, 0.5F, 0.5F, false, 2.0F, -0.7F, entityIn.tickCount, 1.0F);
         this.flap(this.armLeft, 0.5F, 0.5F, true, 2.0F, -0.7F, entityIn.tickCount, 1.0F);
         this.walk(this.armRight, 0.5F, 0.5F, true, 1.0F, 0.0F, entityIn.tickCount, 1.0F);
         this.walk(this.armLeft, 0.5F, 0.5F, true, 1.0F, 0.0F, entityIn.tickCount, 1.0F);
      }
   }

   @Override
   public void animate(T entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.animator.startAnimate(entity);
      if (this.animator.setAnimation(this.getSpawnAnimation())) {
         this.animator.startKeyframe(0);
         this.animator.move(this.body, 0.0F, 35.0F, 0.0F);
         this.rotate(this.animator, this.armLeft, -180.0F, 0.0F, 0.0F);
         this.rotate(this.animator, this.armRight, -180.0F, 0.0F, 0.0F);
         this.animator.endKeyframe();
         this.animator.startKeyframe(30);
         this.animator.move(this.body, 0.0F, 0.0F, 0.0F);
         this.rotate(this.animator, this.armLeft, -180.0F, 0.0F, 0.0F);
         this.rotate(this.animator, this.armRight, -180.0F, 0.0F, 0.0F);
         this.animator.endKeyframe();
         this.animator.resetKeyframe(5);
      }

      this.animator.endAnimate();
   }
}
