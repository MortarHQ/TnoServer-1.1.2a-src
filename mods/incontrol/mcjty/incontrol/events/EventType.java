package mcjty.incontrol.events;

import com.google.gson.JsonObject;

public interface EventType {
   EventType.Type type();

   boolean parse(JsonObject var1);

   enum Type {
      MOB_KILLED,
      BLOCK_BROKEN,
      CUSTOM;

      public static EventType.Type getType(String str) {
         return valueOf(str.toUpperCase());
      }
   }
}
