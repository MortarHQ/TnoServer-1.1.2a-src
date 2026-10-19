package net.unusual.block_factorys_bosses.client.particle.bedrock.motion;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.ParticleVector;
import org.joml.Vector3f;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleMotionParametric(ParticleVector relativePosition, @Nullable ParticleVector direction, MathValue rotation) implements ParticleMotion {
   @Override
   public void tick(BedrockParticle particle) {
      BedrockParticleEmitter parent = particle.parent;
      if (parent != null) {
         Vector3f position = this.relativePosition.getNewVector().add((float)parent.getX(), (float)parent.getY(), (float)parent.getZ());
         particle.setPos(position.x(), position.y(), position.z());
         if (this.direction != null) {
            particle.setVelocity(this.direction.getNewVector());
         }

         double newRotation = this.rotation.get();
         particle.setRoll((float)newRotation);
      }
   }

   public static ParticleMotionParametric deserialize(JsonObject component) {
      ParticleVector relativePosition = ParticleVector.deserialize(component.get("relative_position"), new Vec3(0.0, 0.0, 0.0));
      ParticleVector direction = ParticleVector.deserializeOrNull(component.get("direction"));
      MathValue rotation = BedrockParticleEffect.parseMolangJson(component.get("rotation"), 0.0);
      return new ParticleMotionParametric(relativePosition, direction, rotation);
   }
}
