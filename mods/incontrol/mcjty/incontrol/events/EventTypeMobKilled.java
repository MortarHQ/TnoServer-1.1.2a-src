package mcjty.incontrol.events;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import mcjty.incontrol.ErrorHandler;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class EventTypeMobKilled implements EventType {
   private List<ResourceLocation> mobs;
   private boolean playerKill = false;

   @Override
   public EventType.Type type() {
      return EventType.Type.MOB_KILLED;
   }

   public List<ResourceLocation> getMobs() {
      return this.mobs;
   }

   public boolean isPlayerKill() {
      return this.playerKill;
   }

   @Override
   public boolean parse(JsonObject object) {
      this.mobs = new ArrayList<>();
      JsonElement mob = object.get("mob");
      if (mob == null) {
         ErrorHandler.error("No mob specified!");
         return false;
      }

      if (mob.isJsonArray()) {
         for (JsonElement element : mob.getAsJsonArray()) {
            ResourceLocation rl = ResourceLocation.parse(element.getAsString());
            if (!BuiltInRegistries.ENTITY_TYPE.containsKey(rl)) {
               ErrorHandler.error("Unknown mob '" + rl + "'!");
               return true;
            }

            this.mobs.add(rl);
         }
      } else {
         ResourceLocation rl = ResourceLocation.parse(mob.getAsString());
         if (!BuiltInRegistries.ENTITY_TYPE.containsKey(rl)) {
            ErrorHandler.error("Unknown mob '" + rl + "'!");
            return true;
         }

         this.mobs.add(rl);
      }

      if (this.mobs.isEmpty()) {
         ErrorHandler.error("No mobs specified!");
         return false;
      }

      if (object.has("player")) {
         this.playerKill = object.get("player").getAsBoolean();
      }

      return true;
   }
}
