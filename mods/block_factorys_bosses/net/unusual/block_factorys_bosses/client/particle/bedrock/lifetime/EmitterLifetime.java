package net.unusual.block_factorys_bosses.client.particle.bedrock.lifetime;

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
public interface EmitterLifetime {
   Set<String> KNOWN_TYPES = ImmutableSet.of("minecraft:emitter_lifetime_expression", "minecraft:emitter_lifetime_looping", "minecraft:emitter_lifetime_once");

   void tick(BedrockParticleEmitter var1);

   static EmitterLifetime deserialize(JsonObject allComponents) {
      String type = BedrockParticleEffect.getOneComponentOf(allComponents, KNOWN_TYPES);
      if (type == null) {
         throw new IllegalArgumentException("No lifetime component found.");
      }

      JsonObject componentJson = allComponents.getAsJsonObject(type);

      return switch (type) {
         case "minecraft:emitter_lifetime_expression" -> EmitterLifetimeExpression.deserialize(componentJson);
         case "minecraft:emitter_lifetime_looping" -> EmitterLifetimeLooping.deserialize(componentJson);
         case "minecraft:emitter_lifetime_once" -> EmitterLifetimeOnce.deserialize(componentJson);
         default -> throw new IllegalArgumentException("Unknown lifetime component type: " + type);
      };
   }
}
