package cn.leolezury.eternalstarlight.common.world.gen.valuemap;

import net.minecraft.world.phys.Vec3;

public record SpikeProvider(float radius, float height) implements ValueMapProvider {
   @Override
   public float getValue(float x, float y, float z) {
      if (!(y < 0.0F) && !(y > this.height())) {
         Vec3 vec3 = new Vec3(x, y, z);
         float radiusAtHeight = this.radius() * (1.0F - y / this.height());
         return vec3.horizontalDistance() <= radiusAtHeight ? (float)(this.radius() / this.height() * y + vec3.horizontalDistance()) / this.radius() : 1.0F;
      } else {
         return 1.0F;
      }
   }
}
