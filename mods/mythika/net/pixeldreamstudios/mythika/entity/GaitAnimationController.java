package net.pixeldreamstudios.mythika.entity;

import java.util.function.ToDoubleFunction;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationController.AnimationStateHandler;

public class GaitAnimationController<T extends GeoAnimatable> extends AnimationController<T> {
   private final ToDoubleFunction<T> rate;
   private double elapsed;
   private double lastRaw;

   public GaitAnimationController(T animatable, String name, int transitionTicks, AnimationStateHandler<T> handler, ToDoubleFunction<T> rate) {
      super(animatable, name, transitionTicks, handler);
      this.rate = rate;
   }

   protected double adjustTick(double tick) {
      double raw = super.adjustTick(tick);
      if (raw <= 0.0) {
         this.elapsed = 0.0;
         this.lastRaw = 0.0;
         return 0.0;
      }

      double delta = raw - this.lastRaw;
      this.lastRaw = raw;
      if (delta > 0.0) {
         this.elapsed = this.elapsed + delta * this.rate.applyAsDouble((T)this.animatable);
      }

      return this.elapsed;
   }
}
