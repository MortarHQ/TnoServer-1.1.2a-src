package net.unusual.block_factorys_bosses.client.particle.bedrock.curve;

import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleCurve(Curve curve, MathValue input, MathValue inputHorizontalRange) {
   public double getValue() {
      float progress = (float)(this.input.get() / this.inputHorizontalRange.get());
      return this.curve.interpolate(progress);
   }

   public static ParticleCurve deserialize(JsonObject curveObject) {
      String type = curveObject.get("type").getAsString();
      MathValue input = MathParser.parseJson(curveObject.get("input"));
      MathValue inputHorizontalRange = BedrockParticleEffect.parseMolangJson(type.equals("bezier_chain") ? null : curveObject.get("horizontal_range"), 1.0);

      Record curve = switch (type) {
         case "linear" -> LinearCurve.deserialize(curveObject);
         case "bezier" -> BezierCurve.deserialize(curveObject);
         case "bezier_chain" -> BezierChainCurve.deserialize(curveObject);
         default -> throw new IllegalArgumentException("Unknown curve type: " + type);
      };
      return new ParticleCurve((Curve)curve, input, inputHorizontalRange);
   }
}
