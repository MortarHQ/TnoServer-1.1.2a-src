package net.unusual.block_factorys_bosses.client.particle.bedrock.curve;

import com.google.gson.JsonObject;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.Mth;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record LinearCurve(List<MathValue> nodes) implements Curve {
   public LinearCurve {
      if (nodes.size() < 2) {
         throw new IllegalArgumentException("LinearCurve must have at least two nodes.");
      }
   }

   @Override
   public double interpolate(float progress) {
      progress = Mth.clamp(progress, 0.0F, 1.0F);
      int lastPointIndex = this.nodes.size() - 1;
      float floatIndex = progress * lastPointIndex;
      int previousIndex = Mth.floor(floatIndex);
      int nextIndex = previousIndex + 1;
      return nextIndex >= this.nodes.size()
         ? this.nodes.get(lastPointIndex).get()
         : Mth.lerp(Mth.frac(floatIndex), this.nodes.get(previousIndex).get(), this.nodes.get(nextIndex).get());
   }

   public static LinearCurve deserialize(JsonObject curveObject) {
      List<MathValue> points = Curve.parseNodes(curveObject);
      return new LinearCurve(points);
   }
}
