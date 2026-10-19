package net.unusual.block_factorys_bosses.client.particle.bedrock.shape;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.ParticleVector;
import org.joml.Vector3f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record EmitterShapeCustom(ParticleVector offset, @Nullable ParticleVector direction) implements EmitterShape {
   @Override
   public void getSpawnPosition(BedrockParticleEmitter emitter, Vector3f offset, Vector3f direction) {
      this.offset.getVector(offset);
      if (this.direction == null) {
         direction.set(0.0F, 0.0F, 0.0F);
      } else {
         this.direction.getVector(direction);
      }
   }

   public static EmitterShapeCustom deserialize(JsonObject component) {
      JsonElement offset = component.get("offset");
      JsonElement direction = component.get("direction");
      return new EmitterShapeCustom(
         ParticleVector.deserialize(offset, new Vec3(0.0, 0.0, 0.0)), direction == null ? null : ParticleVector.deserialize(direction)
      );
   }
}
