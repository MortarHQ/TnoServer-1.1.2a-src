package cn.leolezury.eternalstarlight.common.world.gen.valuemap;

import net.minecraft.world.phys.Vec3;

public record CylinderProvider(float radius, float height) implements ValueMapProvider {
   @Override
   public float getValue(float x, float y, float z) {
      if (!(y < 0.0F) && !(y > this.height())) {
         Vec3 vec3 = new Vec3(x, y, z);
         return vec3.horizontalDistance() <= this.radius() ? (float)vec3.horizontalDistance() / this.radius() : 1.0F;
      } else {
         return 1.0F;
      }
   }
}
