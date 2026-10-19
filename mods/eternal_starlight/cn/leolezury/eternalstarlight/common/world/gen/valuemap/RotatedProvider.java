package cn.leolezury.eternalstarlight.common.world.gen.valuemap;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public record RotatedProvider(ValueMapProvider provider, float pitch, float yaw) implements ValueMapProvider {
   @Override
   public float getValue(float x, float y, float z) {
      Vector3f vector3f = new Vector3f(x, y, z);
      vector3f.rotate(new Quaternionf().rotateX(this.pitch() * (float) (Math.PI / 180.0)).rotateY(this.yaw() * (float) (Math.PI / 180.0)));
      return this.provider().getValue(vector3f.x(), vector3f.y(), vector3f.z());
   }
}
