package net.unusual.block_factorys_bosses.client.particle.bedrock.simple;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleInitialSpeed(MathValue initialSpeed) {
   public static ParticleInitialSpeed deserializeFromComponents(JsonObject allComponents) {
      return deserialize(allComponents.get("minecraft:particle_initial_speed"));
   }

   public static ParticleInitialSpeed deserialize(@Nullable JsonElement componentJson) {
      return componentJson instanceof JsonPrimitive primitive && primitive.isNumber()
         ? new ParticleInitialSpeed(new Constant(primitive.getAsFloat()))
         : new ParticleInitialSpeed(BedrockParticleEffect.parseMolangJson(componentJson, 0.0));
   }
}
