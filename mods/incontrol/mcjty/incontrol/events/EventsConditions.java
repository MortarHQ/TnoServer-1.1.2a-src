package mcjty.incontrol.events;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import mcjty.incontrol.ErrorHandler;
import mcjty.incontrol.tools.rules.TestingTools;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class EventsConditions {
   private final Set<ResourceKey<Level>> dimensions;
   private final float random;
   private final Set<String> phases;
   private final Map<String, Predicate<Integer>> numbers;
   public static final EventsConditions DEFAULT = create().build();
   private static final Map<String, EventsConditions.Cmd> CONDITIONS = new HashMap<>();

   private EventsConditions(EventsConditions.Builder builder) {
      this.dimensions = new HashSet<>(builder.dimensions);
      this.random = builder.random;
      this.phases = builder.phases;
      this.numbers = builder.numbers;
   }

   public void validate() {
   }

   public Set<ResourceKey<Level>> getDimensions() {
      return this.dimensions;
   }

   public float getRandom() {
      return this.random;
   }

   public Set<String> getPhases() {
      return this.phases;
   }

   public Map<String, Predicate<Integer>> getNumbers() {
      return this.numbers;
   }

   public static EventsConditions.Builder create() {
      return new EventsConditions.Builder();
   }

   public static void parse(JsonObject object, EventsConditions.Builder builder) {
      for (String attr : object.keySet()) {
         EventsConditions.Cmd cmd = CONDITIONS.get(attr);
         if (cmd == null) {
            ErrorHandler.error("Invalid condition '" + attr + "' for spawner rule!");
            return;
         }

         switch (cmd) {
            case DIMENSION:
               JsonElement value = object.get(attr);
               if (value.isJsonArray()) {
                  for (JsonElement element : value.getAsJsonArray()) {
                     ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(element.getAsString()));
                     builder.dimensions(key);
                  }
                  break;
               }

               ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(value.getAsString()));
               builder.dimensions(key);
               break;
            case RANDOM:
               builder.random(object.get(attr).getAsFloat());
               break;
            case PHASE:
               JsonElement value = object.get(attr);
               if (value.isJsonArray()) {
                  for (JsonElement element : value.getAsJsonArray()) {
                     builder.phase(element.getAsString());
                  }
                  break;
               }

               builder.phase(value.getAsString());
               break;
            case NUMBER:
               JsonElement value = object.get(attr);
               if (value.isJsonArray()) {
                  for (JsonElement element : value.getAsJsonArray()) {
                     TestingTools.NumberResult result = TestingTools.parseNumberCheck(element);
                     if (result != null) {
                        builder.numbers.put(result.number(), result.test());
                     }
                  }
               } else {
                  TestingTools.NumberResult result = TestingTools.parseNumberCheck(value);
                  if (result != null) {
                     builder.numbers.put(result.number(), result.test());
                  }
               }
         }
      }
   }

   static {
      for (EventsConditions.Cmd cmd : EventsConditions.Cmd.values()) {
         CONDITIONS.put(cmd.name().toLowerCase(), cmd);
      }
   }

   public static class Builder {
      private final Set<ResourceKey<Level>> dimensions = new HashSet<>();
      private float random = -1.0F;
      private final Set<String> phases = new HashSet<>();
      private final Map<String, Predicate<Integer>> numbers = new HashMap<>();

      public EventsConditions.Builder dimensions(ResourceKey<Level>... dimensions) {
         Collections.addAll(this.dimensions, dimensions);
         return this;
      }

      public EventsConditions.Builder random(float random) {
         this.random = random;
         return this;
      }

      public EventsConditions.Builder phase(String... phases) {
         Collections.addAll(this.phases, phases);
         return this;
      }

      public EventsConditions.Builder number(String name, Predicate<Integer> predicate) {
         this.numbers.put(name, predicate);
         return this;
      }

      public EventsConditions build() {
         return new EventsConditions(this);
      }
   }

   enum Cmd {
      DIMENSION,
      RANDOM,
      PHASE,
      NUMBER;
   }
}
