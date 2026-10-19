package net.unusual.block_factorys_bosses.client.particle.bedrock.motion;

import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.ParticleVector;
import org.joml.Vector3f;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleMotionDynamic(
   ParticleVector linearAcceleration, MathValue linearDragCoefficient, MathValue rotationAcceleration, MathValue rotationDragCoefficient
) implements ParticleMotion {
   @Override
   public void tick(BedrockParticle particle) {
      Vector3f newVelocity = this.linearAcceleration()
         .getNewVector()
         .sub(particle.getVelocity().mul((float)this.linearDragCoefficient().get()))
         .mul(0.05F)
         .add(particle.getVelocity());
      particle.setVelocity(newVelocity);
      float rotationAcceleration = (float)this.rotationAcceleration().get() - particle.getRollVelocity() * (float)this.rotationDragCoefficient().get();
      particle.setRollVelocity(particle.getRollVelocity() + rotationAcceleration * 0.05F);
      particle.physicsStep();
   }

   public static ParticleMotionDynamic deserialize(JsonObject component) {
      ParticleVector linearAcceleration = ParticleVector.deserialize(component.get("linear_acceleration"), new Vec3(0.0, 0.0, 0.0));
      MathValue linearDragCoefficient = BedrockParticleEffect.parseMolangJson(component.get("linear_drag_coefficient"), 0.0);
      MathValue rotationAcceleration = BedrockParticleEffect.parseMolangJson(component.get("rotation_acceleration"), 0.0);
      MathValue rotationDragCoefficient = BedrockParticleEffect.parseMolangJson(component.get("rotation_drag_coefficient"), 0.0);
      return new ParticleMotionDynamic(linearAcceleration, linearDragCoefficient, rotationAcceleration, rotationDragCoefficient);
   }
}
