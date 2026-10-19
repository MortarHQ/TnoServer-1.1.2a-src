package net.unusual.block_factorys_bosses.client.particle.bedrock.event;

import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.client.particle.bedrock.AbstractBedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockEmitterParticleOptions;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;
import org.joml.Vector3f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleEventParticleEffect(ResourceLocation particleEffectIdentifier, ParticleEventParticleEffect.ParticleEffectType type)
   implements ParticleEvent {
   @Override
   public void trigger(AbstractBedrockParticle particle) {
      this.type().trigger(particle, this.resolveParticleType(particle));
   }

   private ParticleType<BedrockEmitterParticleOptions> resolveParticleType(AbstractBedrockParticle particle) {
      Registry<ParticleType<?>> particleTypeRegistry = (Registry<ParticleType<?>>)particle.getLevel()
         .registryAccess()
         .registry(Registries.PARTICLE_TYPE)
         .orElseThrow();
      ParticleType<BedrockEmitterParticleOptions> particleType = (ParticleType<BedrockEmitterParticleOptions>)particleTypeRegistry.get(
         this.particleEffectIdentifier()
      );
      if (particleType == null) {
         throw new IllegalArgumentException(
            "Bedrock particle " + particle.particleEffect + " has particle effect with invalid particle: " + this.particleEffectIdentifier()
         );
      } else {
         return particleType;
      }
   }

   public static ParticleEventParticleEffect deserialize(JsonObject json) {
      JsonObject particleEffectJson = json.getAsJsonObject("particle_effect");
      return new ParticleEventParticleEffect(
         ResourceLocation.parse(particleEffectJson.get("effect").getAsString()), ParticleEventParticleEffect.ParticleEffectType.deserialize(particleEffectJson)
      );
   }

   public interface ParticleEffectType {
      void trigger(AbstractBedrockParticle var1, ParticleType<BedrockEmitterParticleOptions> var2);

      static ParticleEventParticleEffect.ParticleEffectType deserialize(JsonObject json) {
         String type = json.getAsJsonPrimitive("type").getAsString();

         return switch (type) {
            case "emitter" -> ParticleEventParticleEffect.ParticleEffectTypeEmitter.INSTANCE;
            case "emitter_bound" -> ParticleEventParticleEffect.ParticleEffectTypeEmitterBound.INSTANCE;
            case "particle" -> ParticleEventParticleEffect.ParticleEffectTypeEmitterParticle.INSTANCE;
            case "particle_with_velocity" -> ParticleEventParticleEffect.ParticleEffectTypeEmitterParticleWithVelocity.INSTANCE;
            default -> throw new IllegalArgumentException("Unknown particle_effect type: " + type);
         };
      }
   }

   public record ParticleEffectTypeEmitter() implements ParticleEventParticleEffect.ParticleEffectType {
      public static ParticleEventParticleEffect.ParticleEffectTypeEmitter INSTANCE = new ParticleEventParticleEffect.ParticleEffectTypeEmitter();

      @Override
      public void trigger(AbstractBedrockParticle particle, ParticleType<BedrockEmitterParticleOptions> particleEffect) {
         particle.getLevel()
            .addParticle(new BedrockEmitterParticleOptions(particleEffect, null, true), particle.getX(), particle.getY(), particle.getZ(), 0.0, 0.0, 0.0);
      }
   }

   public record ParticleEffectTypeEmitterBound() implements ParticleEventParticleEffect.ParticleEffectType {
      public static ParticleEventParticleEffect.ParticleEffectTypeEmitterBound INSTANCE = new ParticleEventParticleEffect.ParticleEffectTypeEmitterBound();

      @Override
      public void trigger(AbstractBedrockParticle particle, ParticleType<BedrockEmitterParticleOptions> particleEffect) {
         particle.getLevel()
            .addParticle(new BedrockEmitterParticleOptions(particleEffect, null, true), particle.getX(), particle.getY(), particle.getZ(), 0.0, 0.0, 0.0);
      }
   }

   public record ParticleEffectTypeEmitterParticle() implements ParticleEventParticleEffect.ParticleEffectType {
      public static ParticleEventParticleEffect.ParticleEffectTypeEmitterParticle INSTANCE = new ParticleEventParticleEffect.ParticleEffectTypeEmitterParticle();

      @Override
      public void trigger(AbstractBedrockParticle particle, ParticleType<BedrockEmitterParticleOptions> particleEffect) {
         particle.getLevel()
            .addParticle(
               new BedrockEmitterParticleOptions(particleEffect, particle instanceof BedrockParticleEmitter emitter ? emitter : null, false),
               particle.getX(),
               particle.getY(),
               particle.getZ(),
               0.0,
               0.0,
               0.0
            );
      }
   }

   public record ParticleEffectTypeEmitterParticleWithVelocity() implements ParticleEventParticleEffect.ParticleEffectType {
      public static ParticleEventParticleEffect.ParticleEffectTypeEmitterParticleWithVelocity INSTANCE = new ParticleEventParticleEffect.ParticleEffectTypeEmitterParticleWithVelocity();

      @Override
      public void trigger(AbstractBedrockParticle particle, ParticleType<BedrockEmitterParticleOptions> particleEffect) {
         Vector3f velocity = particle.getVelocity();
         particle.getLevel()
            .addParticle(
               new BedrockEmitterParticleOptions(particleEffect, particle instanceof BedrockParticleEmitter emitter ? emitter : null, false),
               particle.getX(),
               particle.getY(),
               particle.getZ(),
               velocity.x(),
               velocity.y(),
               velocity.z()
            );
      }
   }
}
