package mcjty.incontrol.events;

import com.google.gson.JsonObject;

public class EventTypeCustom implements EventType {
   private String name;

   @Override
   public EventType.Type type() {
      return EventType.Type.CUSTOM;
   }

   public String getName() {
      return this.name;
   }

   @Override
   public boolean parse(JsonObject object) {
      this.name = object.get("name").getAsString();
      return true;
   }
}
