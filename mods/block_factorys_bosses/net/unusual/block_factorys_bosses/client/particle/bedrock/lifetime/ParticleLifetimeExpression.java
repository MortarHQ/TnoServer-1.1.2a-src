package net.unusual.block_factorys_bosses.client.particle.bedrock.lifetime;

import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleLifetimeExpression(MathValue expirationExpression, MathValue maxLifetime) {
   public int getLifetime() {
      return (int)(this.maxLifetime.get() * 20.0);
   }

   public boolean isExpired(BedrockParticle particle) {
      return this.expirationExpression.get() != 0.0;
   }

   public void tick(BedrockParticle particle) {
      if (this.isExpired(particle)) {
         particle.remove();
      }
   }

   public static ParticleLifetimeExpression deserializeFromComponents(JsonObject allComponents) {
      return deserialize(allComponents.getAsJsonObject("minecraft:particle_lifetime_expression"));
   }

   public static ParticleLifetimeExpression deserialize(JsonObject component) {
      MathValue expirationExpression = BedrockParticleEffect.parseMolangJson(component.get("expiration_expression"), 0.0);
      MathValue maxLifetime = MathParser.parseJson(component.get("max_lifetime"));
      return new ParticleLifetimeExpression(expirationExpression, maxLifetime);
   }
}
