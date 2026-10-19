package com.iafenvoy.uranus.animation;

import com.iafenvoy.uranus.event.Event;

public class AnimationEvents {
   public static final Event<AnimationEvents.Start> START = new Event<>(callbacks -> (entity, animation) -> {
      for (AnimationEvents.Start callback : callbacks) {
         if (callback.onStart(entity, animation)) {
            return true;
         }
      }

      return false;
   });
   public static final Event<AnimationEvents.Tick> TICK = new Event<>(callbacks -> (entity, animation, tick) -> {
      for (AnimationEvents.Tick callback : callbacks) {
         callback.onTick(entity, animation, tick);
      }
   });

   public interface Start {
      boolean onStart(IAnimatedEntity var1, Animation var2);
   }

   public interface Tick {
      void onTick(IAnimatedEntity var1, Animation var2, int var3);
   }
}
