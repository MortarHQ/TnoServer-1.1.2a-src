package com.cerbon.bosses_of_mass_destruction.client.render;

import org.joml.Vector4f;
import software.bernie.geckolib.cache.object.GeoBone;

@FunctionalInterface
public interface IBoneLight {
   int fullbright = 15728880;

   int getLightForBone(GeoBone var1, int var2);

   default Vector4f getColorForBone(GeoBone bone, Vector4f rgbaColor) {
      return rgbaColor;
   }
}
