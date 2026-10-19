package net.unusual.block_factorys_bosses.client.particle.bedrock.rate;

import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.Mth;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record EmitterRateSteady(MathValue spawnRate, MathValue maxParticles) implements EmitterRate {
   @Override
   public void tick(BedrockParticleEmitter emitter) {
      int maxParticles = Mth.floor(this.maxParticles.get());
      float spawnRatePerTick = (float)this.spawnRate.get() / 20.0F;
      int spawnCount = Mth.floor(emitter.getAge() * spawnRatePerTick) - Mth.floor((emitter.getAge() - 1) * spawnRatePerTick);

      for (int remaining = spawnCount; remaining > 0 && emitter.getChildren().size() < maxParticles; remaining--) {
         emitter.emitParticle();
      }
   }

   public static EmitterRateSteady deserialize(JsonObject component) {
      MathValue spawnRate = BedrockParticleEffect.parseMolangJson(component.get("spawn_rate"), 1.0);
      MathValue maxParticles = BedrockParticleEffect.parseMolangJson(component.get("max_particles"), 50.0);
      return new EmitterRateSteady(spawnRate, maxParticles);
   }
}
