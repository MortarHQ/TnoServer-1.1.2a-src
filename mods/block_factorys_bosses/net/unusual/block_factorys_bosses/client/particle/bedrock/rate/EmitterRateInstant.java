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
public record EmitterRateInstant(MathValue count) implements EmitterRate {
   @Override
   public void tick(BedrockParticleEmitter emitter) {
      if (emitter.getAge() == 0) {
         int count = Mth.floor(this.count.get());

         for (int i = 0; i < count; i++) {
            emitter.emitParticle();
         }
      }
   }

   public static EmitterRateInstant deserialize(JsonObject component) {
      MathValue count = BedrockParticleEffect.parseMolangJson(component.get("count"), 10.0);
      return new EmitterRateInstant(count);
   }
}
