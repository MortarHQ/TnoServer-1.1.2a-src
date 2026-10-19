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
public record BezierCurve(List<MathValue> nodes) implements Curve {
   public BezierCurve {
      if (nodes.size() != 4) {
         throw new IllegalArgumentException("Curve with type bezier must have exactly 4 nodes, but has " + nodes.size() + ".");
      }
   }

   @Override
   public double interpolate(float progress) {
      progress = Mth.clamp(progress, 0.0F, 1.0F);
      float a = (float)this.nodes.get(0).get();
      float b = (float)this.nodes.get(1).get();
      float c = (float)this.nodes.get(2).get();
      float d = (float)this.nodes.get(3).get();
      return cubicBezier(progress, a, b, c, d);
   }

   private static double quadraticBezier(float progress, float a, float b, float c) {
      float progressInv = 1.0F - progress;
      return progressInv * progressInv * a + 2.0F * progress * progressInv * b + progress * progress * c;
   }

   private static double cubicBezier(float progress, float a, float b, float c, float d) {
      float progressInv = 1.0F - progress;
      return progressInv * quadraticBezier(progress, a, b, c) + progress * quadraticBezier(progress, b, c, d);
   }

   public static BezierCurve deserialize(JsonObject curveObject) {
      List<MathValue> points = Curve.parseNodes(curveObject);
      return new BezierCurve(points);
   }
}
