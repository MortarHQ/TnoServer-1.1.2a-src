package net.unusual.block_factorys_bosses.client.particle.bedrock.lifetime;

import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record EmitterLifetimeExpression(MathValue activationExpression, MathValue expirationExpression) implements EmitterLifetime {
   private boolean isExpired(BedrockParticleEmitter emitter) {
      return this.expirationExpression.get() != 0.0;
   }

   private boolean isEmitting(BedrockParticleEmitter emitter) {
      return this.activationExpression.get() != 0.0;
   }

   @Override
   public void tick(BedrockParticleEmitter emitter) {
      if (!this.isExpired(emitter)) {
         emitter.remove();
      } else {
         emitter.emitting = this.isEmitting(emitter);
      }
   }

   public static EmitterLifetimeExpression deserialize(JsonObject component) {
      MathValue activationExpression = BedrockParticleEffect.parseMolangJson(component.get("activation_expression"), 1.0);
      MathValue expirationExpression = BedrockParticleEffect.parseMolangJson(component.get("expiration_expression"), 0.0);
      return new EmitterLifetimeExpression(activationExpression, expirationExpression);
   }
}
