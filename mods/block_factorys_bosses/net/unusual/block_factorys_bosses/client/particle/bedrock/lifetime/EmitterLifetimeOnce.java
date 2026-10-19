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
public record EmitterLifetimeOnce(MathValue activeTime) implements EmitterLifetime {
   @Override
   public void tick(BedrockParticleEmitter emitter) {
      if (emitter.getAge() == 0) {
         emitter.cycleDuration = (float)this.activeTime.get();
         emitter.emitting = true;
      } else {
         emitter.elapsedCycleSeconds += 0.05F;
         if (emitter.elapsedCycleSeconds >= emitter.cycleDuration) {
            emitter.remove();
         }
      }
   }

   public static EmitterLifetimeOnce deserialize(JsonObject component) {
      MathValue activeTime = BedrockParticleEffect.parseMolangJson(component.get("active_time"), 10.0);
      return new EmitterLifetimeOnce(activeTime);
   }
}
