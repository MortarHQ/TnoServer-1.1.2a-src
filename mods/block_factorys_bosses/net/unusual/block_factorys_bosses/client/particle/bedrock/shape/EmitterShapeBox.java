package net.unusual.block_factorys_bosses.client.particle.bedrock.shape;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.ParticleDirection;
import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.ParticleVector;
import org.joml.Vector3f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record EmitterShapeBox(ParticleVector offset, ParticleDirection direction, ParticleVector halfDimensions, boolean surfaceOnly) implements EmitterShape {
   @Override
   public void getSpawnPosition(BedrockParticleEmitter emitter, Vector3f offset, Vector3f direction) {
      RandomSource random = emitter.getLevel().getRandom();
      offset.set(random.nextFloat() * 2.0F - 1.0F, random.nextFloat() * 2.0F - 1.0F, random.nextFloat() * 2.0F - 1.0F);
      if (this.surfaceOnly) {
         boolean xySnap = Math.abs(offset.x()) < Math.abs(offset.y());
         boolean xzSnap = Math.abs(offset.x()) < Math.abs(offset.z());
         boolean yzSnap = Math.abs(offset.y()) < Math.abs(offset.z());
         if (xySnap && xzSnap) {
            offset.setComponent(0, offset.x() >= 0.0F ? 1.0F : -1.0F);
         } else if (xySnap && yzSnap) {
            offset.setComponent(1, offset.y() >= 0.0F ? 1.0F : -1.0F);
         } else if (xzSnap && yzSnap) {
            offset.setComponent(2, offset.z() >= 0.0F ? 1.0F : -1.0F);
         }
      }

      offset.mul(this.halfDimensions.getNewVector());
      this.direction.getDirection(offset, direction);
      offset.add(this.offset.getNewVector());
   }

   public static EmitterShapeBox deserialize(JsonObject component) {
      JsonElement offset = component.get("offset");
      JsonElement direction = component.get("direction");
      JsonElement halfDimensions = component.get("half_dimensions");
      JsonElement surfaceOnly = component.get("surface_only");
      return new EmitterShapeBox(
         ParticleVector.deserialize(offset, new Vec3(0.0, 0.0, 0.0)),
         ParticleDirection.deserialize(direction),
         ParticleVector.deserialize(halfDimensions),
         surfaceOnly == null ? false : surfaceOnly.getAsBoolean()
      );
   }
}
