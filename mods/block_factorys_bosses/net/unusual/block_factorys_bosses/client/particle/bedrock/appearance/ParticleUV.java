package net.unusual.block_factorys_bosses.client.particle.bedrock.appearance;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import org.joml.Vector2f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface ParticleUV {
   void getUV(BedrockParticle var1, float var2, Vector2f var3, Vector2f var4);

   static ParticleUV deserialize(@Nullable JsonObject component) {
      if (component == null) {
         return ParticleUVFull.INSTANCE;
      } else {
         return component.has("flipbook") ? ParticleUVFlipbook.deserialize(component) : ParticleUVMath.deserialize(component);
      }
   }
}
