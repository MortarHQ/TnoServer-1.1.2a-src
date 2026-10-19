package net.unusual.block_factorys_bosses.client.particle.bedrock.appearance;

import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.phys.Vec2;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.ParticleVector2;
import org.joml.Vector2f;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleUVFlipbook(
   float textureWidth,
   float textureHeight,
   ParticleVector2 baseUV,
   ParticleVector2 sizeUV,
   ParticleVector2 stepUV,
   float framesPerSecond,
   MathValue maxFrame,
   boolean stretchToLifetime,
   boolean loop
) implements ParticleUV {
   @Override
   public void getUV(BedrockParticle particle, float partialTicks, Vector2f topLeft, Vector2f bottomRight) {
      int maxFrame = (int)this.maxFrame.get();
      int frame;
      if (this.stretchToLifetime) {
         frame = (int)((float)particle.getAge() / particle.getLifetime() * maxFrame);
      } else {
         float secondsAlive = (particle.getAge() + partialTicks) / 20.0F;
         frame = (int)(secondsAlive * this.framesPerSecond());
      }

      if (frame >= maxFrame) {
         if (this.loop()) {
            frame %= maxFrame;
         } else {
            frame = maxFrame - 1;
         }
      }

      Vector2f offset = this.stepUV().getNewVector().mul(frame);
      this.baseUV().getVector(topLeft);
      topLeft.add(offset);
      this.sizeUV().getVector(bottomRight);
      bottomRight.add(topLeft);
      topLeft.div(this.textureWidth(), this.textureHeight());
      bottomRight.div(this.textureWidth(), this.textureHeight());
   }

   public static ParticleUVFlipbook deserialize(JsonObject component) {
      JsonObject flipbookJson = component.getAsJsonObject("flipbook");
      return new ParticleUVFlipbook(
         component.has("texture_width") ? component.getAsJsonPrimitive("texture_width").getAsFloat() : 1.0F,
         component.has("texture_height") ? component.getAsJsonPrimitive("texture_height").getAsFloat() : 1.0F,
         ParticleVector2.deserialize(flipbookJson.get("base_UV")),
         ParticleVector2.deserialize(flipbookJson.get("size_UV"), new Vec2(1.0F, 1.0F)),
         ParticleVector2.deserialize(flipbookJson.get("step_UV"), new Vec2(0.0F, 0.0F)),
         flipbookJson.has("frames_per_second") ? flipbookJson.getAsJsonPrimitive("frames_per_second").getAsFloat() : 0.0F,
         MathParser.parseJson(flipbookJson.get("max_frame")),
         flipbookJson.has("stretch_to_lifetime") && flipbookJson.getAsJsonPrimitive("stretch_to_lifetime").getAsBoolean(),
         flipbookJson.has("loop") && flipbookJson.getAsJsonPrimitive("loop").getAsBoolean()
      );
   }
}
