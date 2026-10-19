package net.unusual.block_factorys_bosses.client.particle.bedrock.appearance;

import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.ParticleVector2;
import org.joml.Vector2f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleUVMath(float textureWidth, float textureHeight, ParticleVector2 uv, ParticleVector2 uvSize) implements ParticleUV {
   @Override
   public void getUV(BedrockParticle particle, float partialTicks, Vector2f topLeft, Vector2f bottomRight) {
      this.uv.getVector(topLeft);
      this.uvSize.getVector(bottomRight);
      bottomRight.add(topLeft);
      topLeft.div(this.textureWidth, this.textureHeight);
      bottomRight.div(this.textureWidth, this.textureHeight);
   }

   public static ParticleUVMath deserialize(JsonObject component) {
      return new ParticleUVMath(
         component.has("texture_width") ? component.getAsJsonPrimitive("texture_width").getAsFloat() : 1.0F,
         component.has("texture_height") ? component.getAsJsonPrimitive("texture_height").getAsFloat() : 1.0F,
         ParticleVector2.deserialize(component.get("uv")),
         ParticleVector2.deserialize(component.get("uv_size"))
      );
   }
}
