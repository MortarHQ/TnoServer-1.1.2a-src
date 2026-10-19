package com.blackgear.platform.client.animator;

import java.util.function.Function;
import net.minecraft.client.animation.AnimationChannel.Target;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class AnimatedPoint {
   private final Target target;
   private final Function<Float, Float> x;
   private final Function<Float, Float> y;
   private final Function<Float, Float> z;

   public AnimatedPoint(Target target, Function<Float, Float> x, Function<Float, Float> y, Function<Float, Float> z) {
      this.target = target;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public AnimatedPoint(Target target, Function<Float, Vector3f> vector) {
      this(target, ticks -> vector.apply(ticks).x(), ticks -> vector.apply(ticks).y(), ticks -> vector.apply(ticks).z());
   }

   public Target target() {
      return this.target;
   }

   public float getX(float ticks) {
      return this.x.apply(ticks);
   }

   public float getY(float ticks) {
      return this.y.apply(ticks);
   }

   public float getZ(float ticks) {
      return this.z.apply(ticks);
   }
}
