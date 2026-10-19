package net.unusual.block_factorys_bosses.client.particle.bedrock.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.lang.runtime.SwitchBootstraps;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record EmitterLifetimeEvents(
   @Nullable List<String> creationEvents, @Nullable List<String> expirationEvents, @Nullable List<EmitterLifetimeEvents.TimelineEvent> timelineEvents
) {
   public void tick(BedrockParticleEmitter emitter) {
      if (this.timelineEvents() != null) {
         float time = emitter.elapsedCycleSeconds;
         float previousTime = time - 0.05F;

         for (EmitterLifetimeEvents.TimelineEvent timelineEvent : this.timelineEvents()) {
            if (previousTime > timelineEvent.time()) {
               break;
            }

            if (timelineEvent.time() <= time && timelineEvent.time() > previousTime) {
               this.triggerEvents(emitter, timelineEvent.events());
            }
         }
      }
   }

   public void triggerCreationEvents(BedrockParticleEmitter emitter) {
      this.triggerEvents(emitter, this.creationEvents());
   }

   public void triggerExpirationEvents(BedrockParticleEmitter emitter) {
      this.triggerEvents(emitter, this.expirationEvents());
   }

   private void triggerEvents(BedrockParticleEmitter emitter, @Nullable List<String> eventNames) {
      if (eventNames != null) {
         for (String eventName : eventNames) {
            emitter.triggerParticleEvent(eventName);
         }
      }
   }

   public static EmitterLifetimeEvents deserializeFromComponents(JsonObject allComponents) {
      JsonElement json = allComponents.get("minecraft:emitter_lifetime_events");
      return json != null ? deserialize(json.getAsJsonObject()) : new EmitterLifetimeEvents(null, null, null);
   }

   @Nullable
   private static List<String> parseEventsOrNull(@Nullable JsonElement eventsJson) {
      JsonElement var1 = eventsJson;
      byte var2 = 0;

      while (true) {
         List var10000;
         switch (SwitchBootstraps.typeSwitch<"typeSwitch",JsonArray,JsonPrimitive>(var1, var2)) {
            case -1:
               var10000 = null;
               break;
            case 0:
               JsonArray eventsArray = (JsonArray)var1;
               var10000 = eventsArray.asList().stream().map(JsonElement::getAsString).toList();
               break;
            case 1:
               JsonPrimitive eventsPrimitive = (JsonPrimitive)var1;
               if (!eventsPrimitive.isString()) {
                  var2 = 2;
                  continue;
               }

               var10000 = List.of(eventsPrimitive.getAsString());
               break;
            default:
               throw new IllegalArgumentException("Unknown event names element: " + eventsJson);
         }

         return var10000;
      }
   }

   private static List<String> parseEvents(@Nullable JsonElement eventsJson) {
      List<String> events = parseEventsOrNull(eventsJson);
      return events == null ? List.of() : events;
   }

   static EmitterLifetimeEvents deserialize(JsonObject json) {
      return new EmitterLifetimeEvents(
         parseEventsOrNull(json.get("creation_event")),
         parseEventsOrNull(json.get("expiration_event")),
         json.get("timeline") instanceof JsonObject jsonObject ? EmitterLifetimeEvents.TimelineEvent.deserialize(jsonObject) : null
      );
   }

   public record TimelineEvent(float time, List<String> events) {
      public static List<EmitterLifetimeEvents.TimelineEvent> deserialize(JsonObject timelineJson) {
         return timelineJson.entrySet()
            .stream()
            .map(
               entry -> new EmitterLifetimeEvents.TimelineEvent(
                  Float.parseFloat((String)entry.getKey()), EmitterLifetimeEvents.parseEvents((JsonElement)entry.getValue())
               )
            )
            .sorted((a, b) -> Float.compare(a.time(), b.time()))
            .toList();
      }
   }
}
