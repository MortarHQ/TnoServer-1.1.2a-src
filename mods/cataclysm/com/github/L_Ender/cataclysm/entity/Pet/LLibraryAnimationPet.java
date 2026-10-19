package com.github.L_Ender.cataclysm.entity.Pet;

import com.github.L_Ender.cataclysm.entity.etc.IFollower;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class LLibraryAnimationPet extends AnimationPet implements IAnimatedEntity, IFollower {
   private int animationTick;
   private Animation currentAnimation;

   public LLibraryAnimationPet(EntityType entity, Level world) {
      super(entity, world);
   }

   protected void onAnimationFinish(Animation animation) {
   }

   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION};
   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int tick) {
      this.animationTick = tick;
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      if (animation == NO_ANIMATION) {
         this.onAnimationFinish(this.currentAnimation);
      }

      this.currentAnimation = animation;
      this.setAnimationTick(0);
   }

   @Override
   protected void defineSynchedData(Builder p_326229_) {
      super.defineSynchedData(p_326229_);
   }
}
