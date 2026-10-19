package net.unusual.block_factorys_bosses.client.particle.bedrock.rate;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import java.util.Set;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface EmitterRate {
   Set<String> KNOWN_TYPES = ImmutableSet.of("minecraft:emitter_rate_instant", "minecraft:emitter_rate_steady");

   void tick(BedrockParticleEmitter var1);

   static EmitterRate deserialize(JsonObject allComponents) {
      String type = BedrockParticleEffect.getOneComponentOf(allComponents, KNOWN_TYPES);
      if (type == null) {
         throw new IllegalArgumentException("No rate component found.");
      }

      JsonObject componentJson = allComponents.getAsJsonObject(type);

      return switch (type) {
         case "minecraft:emitter_rate_instant" -> EmitterRateInstant.deserialize(componentJson);
         case "minecraft:emitter_rate_steady" -> EmitterRateSteady.deserialize(componentJson);
         default -> throw new IllegalArgumentException("Unknown rate component type: " + type);
      };
   }
}
