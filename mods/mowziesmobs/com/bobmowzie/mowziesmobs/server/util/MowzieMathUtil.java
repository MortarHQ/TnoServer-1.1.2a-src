package com.bobmowzie.mowziesmobs.server.util;

import net.minecraft.util.Mth;

public class MowzieMathUtil {
   public static float approachSmooth(float current, float previous, float desired, float desiredSpeed, float deltaSpeed) {
      float prevSpeed = current - previous;
      desiredSpeed = Mth.abs(desiredSpeed);
      desiredSpeed = current < desired ? desiredSpeed : -desiredSpeed;
      float speed = Mth.approach(prevSpeed, desiredSpeed, deltaSpeed);
      float speedApproachReduction = (float)(
         1.0 - Math.pow(Mth.clamp(-Mth.abs(current - desired) / Mth.abs(2.0F * desiredSpeed / deltaSpeed) + 1.0F, 0.0F, 1.0F), 4.0)
      );
      speed *= speedApproachReduction;
      return current < desired ? Mth.clamp(current + speed, current, desired) : Mth.clamp(current + speed, desired, current);
   }

   public static float approachDegreesSmooth(float current, float previous, float desired, float desiredSpeed, float deltaSpeed) {
      float desiredDifference = Mth.degreesDifference(current, desired);
      float previousDifference = Mth.degreesDifference(current, previous);
      return approachSmooth(current, current + previousDifference, current + desiredDifference, desiredSpeed, deltaSpeed);
   }
}
