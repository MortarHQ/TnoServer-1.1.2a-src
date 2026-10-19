package com.iafenvoy.uranus.animation;

import com.iafenvoy.uranus.ServerHelper;
import com.iafenvoy.uranus.network.AnimationPayload;
import net.minecraft.world.entity.Entity;
import org.apache.commons.lang3.ArrayUtils;

public enum AnimationHandler {
   INSTANCE;

   public <T extends Entity & IAnimatedEntity> void sendAnimationMessage(T entity, Animation animation) {
      if (!entity.level().isClientSide) {
         entity.setAnimation(animation);
         ServerHelper.sendToAll(new AnimationPayload(entity.getId(), ArrayUtils.indexOf(entity.getAnimations(), animation)));
      }
   }

   public <T extends Entity & IAnimatedEntity> void updateAnimations(T entity) {
      if (entity.getAnimation() == null) {
         entity.setAnimation(IAnimatedEntity.NO_ANIMATION);
      } else if (entity.getAnimation() != IAnimatedEntity.NO_ANIMATION) {
         if (entity.getAnimationTick() == 0 && !AnimationEvents.START.invoker().onStart(entity, entity.getAnimation())) {
            this.sendAnimationMessage(entity, entity.getAnimation());
         }

         if (entity.getAnimationTick() < entity.getAnimation().getDuration()) {
            entity.setAnimationTick(entity.getAnimationTick() + 1);
            AnimationEvents.TICK.invoker().onTick(entity, entity.getAnimation(), entity.getAnimationTick());
         }

         if (entity.getAnimationTick() == entity.getAnimation().getDuration()) {
            entity.setAnimationTick(0);
            entity.setAnimation(IAnimatedEntity.NO_ANIMATION);
         }
      }
   }
}
