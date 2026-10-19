package net.unusual.block_factorys_bosses.client.particle.bedrock.event;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.AbstractBedrockParticle;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface ParticleEvent {
   void trigger(AbstractBedrockParticle var1);

   static ParticleEvent deserialize(JsonObject json) {
      if (json.has("sequence")) {
         return ParticleEventSequence.deserialize(json);
      }

      if (json.has("randomize")) {
         return ParticleEventRandomize.deserialize(json);
      }

      ArrayList<ParticleEvent> events = new ArrayList<>(2);
      if (json.has("particle_effect")) {
         events.add(ParticleEventParticleEffect.deserialize(json));
      }

      if (json.has("sound_effect")) {
         events.add(ParticleEventSoundEffect.deserialize(json));
      }

      if (events.isEmpty()) {
         throw new IllegalArgumentException("Unknown particle event: " + json);
      } else {
         return events.size() == 1 ? events.getFirst() : new ParticleEventSequence(events);
      }
   }
}
