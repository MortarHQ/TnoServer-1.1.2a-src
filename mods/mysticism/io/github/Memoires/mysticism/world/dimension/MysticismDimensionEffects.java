package io.github.Memoires.mysticism.world.dimension;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.DimensionSpecialEffects.SkyType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class MysticismDimensionEffects {
   public static class ElementalRealm extends DimensionSpecialEffects {
      public ElementalRealm() {
         super(128.0F, false, SkyType.NONE, false, false);
      }

      @NotNull
      public Vec3 getBrightnessDependentFogColor(Vec3 vec3, float v) {
         return vec3;
      }

      public boolean isFoggyAt(int i, int i1) {
         return false;
      }
   }

   public static class KamuiDimension extends DimensionSpecialEffects {
      public KamuiDimension() {
         super(Float.NaN, false, SkyType.NONE, false, false);
      }

      @NotNull
      public Vec3 getBrightnessDependentFogColor(Vec3 vec3, float v) {
         return vec3;
      }

      public boolean isFoggyAt(int i, int i1) {
         return true;
      }
   }
}
