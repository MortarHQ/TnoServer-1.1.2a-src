package net.unusual.block_factorys_bosses.client.particle.bedrock.appearance;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.List;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.curve.ColorGradient;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;

public record ParticleAppearanceTinting(MathValue interpolant, ColorGradient color) {
   private static final ParticleAppearanceTinting DEFAULT = new ParticleAppearanceTinting(
      new Constant(0.0), new ColorGradient(List.of(new ColorGradient.ColorNode(0.0F, -1)))
   );

   public int getColor(BedrockParticle particle) {
      float progress = (float)this.interpolant().get();
      return this.color().getColor(progress);
   }

   public static ParticleAppearanceTinting deserializeFromComponents(JsonObject allComponents) {
      JsonObject json = allComponents.getAsJsonObject("minecraft:particle_appearance_tinting");
      return json == null ? DEFAULT : deserialize(json);
   }

   public static ParticleAppearanceTinting deserialize(JsonObject jsonObject) {
      JsonElement colorJson = jsonObject.get("color");
      if (colorJson instanceof JsonArray || colorJson instanceof JsonPrimitive) {
         return new ParticleAppearanceTinting(
            new Constant(0.0), new ColorGradient(List.of(new ColorGradient.ColorNode(0.0F, ColorGradient.parseColor(colorJson))))
         );
      } else if (colorJson instanceof JsonObject colorObject) {
         return new ParticleAppearanceTinting(MathParser.parseJson(colorObject.get("interpolant")), ColorGradient.deserialize(colorObject.get("gradient")));
      } else {
         throw new IllegalArgumentException("Tinting color must be an object or a single color, but received: " + colorJson);
      }
   }
}
