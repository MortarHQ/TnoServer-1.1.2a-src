package cn.leolezury.eternalstarlight.common.world.gen.valuemap;

import java.util.List;
import net.minecraft.world.phys.Vec3;

public record MergedProvider(List<MergedProvider.Entry> providers) implements ValueMapProvider {
   @Override
   public float getValue(float x, float y, float z) {
      float sum = 0.0F;
      int count = 0;

      for (MergedProvider.Entry entry : this.providers()) {
         float value = entry.provider().getValue((float)(x - entry.offset().x()), (float)(y - entry.offset().y()), (float)(z - entry.offset().z()));
         if (value < 1.0F && value >= 0.0F) {
            sum += value;
            count++;
         }
      }

      return count == 0 ? 1.0F : sum / count;
   }

   public record Entry(ValueMapProvider provider, Vec3 offset) {
   }
}
