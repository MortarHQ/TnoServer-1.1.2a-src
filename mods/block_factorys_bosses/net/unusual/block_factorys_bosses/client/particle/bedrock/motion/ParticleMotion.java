package net.unusual.block_factorys_bosses.client.particle.bedrock.motion;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import java.util.Set;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface ParticleMotion {
   Set<String> KNOWN_TYPES = ImmutableSet.of("minecraft:particle_motion_dynamic", "minecraft:particle_motion_parametric");

   void tick(BedrockParticle var1);

   @Nullable
   static ParticleMotion deserialize(JsonObject allComponents) {
      String type = BedrockParticleEffect.getOneComponentOf(allComponents, KNOWN_TYPES);
      if (type == null) {
         return null;
      }

      JsonObject mainComponentJson = allComponents.getAsJsonObject(type);

      return switch (type) {
         case "minecraft:particle_motion_dynamic" -> ParticleMotionDynamic.deserialize(mainComponentJson);
         case "minecraft:particle_motion_parametric" -> ParticleMotionParametric.deserialize(mainComponentJson);
         default -> throw new IllegalArgumentException("Unknown particle motion component type: " + type);
      };
   }
}
