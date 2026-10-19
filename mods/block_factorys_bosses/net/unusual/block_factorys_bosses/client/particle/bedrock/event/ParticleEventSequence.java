package net.unusual.block_factorys_bosses.client.particle.bedrock.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.AbstractBedrockParticle;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleEventSequence(List<ParticleEvent> events) implements ParticleEvent {
   @Override
   public void trigger(AbstractBedrockParticle particle) {
      for (ParticleEvent event : this.events) {
         event.trigger(particle);
      }
   }

   public static ParticleEventSequence deserialize(JsonObject json) {
      JsonArray sequenceJsonArray = json.getAsJsonArray("sequence");
      ArrayList<ParticleEvent> events = new ArrayList<>(sequenceJsonArray.size());

      for (JsonElement eventJsonElement : sequenceJsonArray) {
         JsonObject eventJson = eventJsonElement.getAsJsonObject();
         ParticleEvent event = ParticleEvent.deserialize(eventJson);
         events.add(event);
      }

      return new ParticleEventSequence(events);
   }
}
