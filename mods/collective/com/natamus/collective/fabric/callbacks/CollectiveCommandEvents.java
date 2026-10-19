package com.natamus.collective.fabric.callbacks;

import com.mojang.brigadier.ParseResults;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_2168;

public class CollectiveCommandEvents {
   public static final Event<CollectiveCommandEvents.On_Command_Parse> ON_COMMAND_PARSE = EventFactory.createArrayBacked(
      CollectiveCommandEvents.On_Command_Parse.class, callbacks -> (string, parse) -> {
         for (CollectiveCommandEvents.On_Command_Parse callback : callbacks) {
            callback.onCommandParse(string, parse);
         }
      }
   );

   private CollectiveCommandEvents() {
   }

   @FunctionalInterface
   public interface On_Command_Parse {
      void onCommandParse(String var1, ParseResults<class_2168> var2);
   }
}
