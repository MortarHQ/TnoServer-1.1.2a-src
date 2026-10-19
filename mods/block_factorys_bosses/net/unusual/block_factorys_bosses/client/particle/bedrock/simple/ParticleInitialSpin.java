package net.unusual.block_factorys_bosses.client.particle.bedrock.simple;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleInitialSpin(MathValue rotation, MathValue rotationRate) {
   public void initialize(BedrockParticle particle) {
      particle.setRoll((float)this.rotation().get());
      particle.setRollVelocity((float)this.rotationRate().get());
   }

   public static ParticleInitialSpin deserializeFromComponents(JsonObject allComponents) {
      return deserialize(allComponents.getAsJsonObject("minecraft:particle_initial_spin"));
   }

   public static ParticleInitialSpin deserialize(@Nullable JsonObject componentJson) {
      return new ParticleInitialSpin(
         BedrockParticleEffect.parseMolangJson(componentJson == null ? null : componentJson.get("rotation"), 0.0),
         BedrockParticleEffect.parseMolangJson(componentJson == null ? null : componentJson.get("rotation_rate"), 0.0)
      );
   }
}
