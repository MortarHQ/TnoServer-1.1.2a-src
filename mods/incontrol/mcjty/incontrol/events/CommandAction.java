package mcjty.incontrol.events;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import mcjty.incontrol.ErrorHandler;
import mcjty.incontrol.rules.support.RuleKeys;
import org.jetbrains.annotations.Nullable;

public record CommandAction(List<String> commands) {
   @Nullable
   static CommandAction parse(JsonObject object) {
      JsonArray cmdArray = object.getAsJsonArray(RuleKeys.ACTION_COMMAND.name());
      if (cmdArray == null) {
         return null;
      }

      List<String> commands = new ArrayList<>();

      for (JsonElement element : cmdArray) {
         if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
            ErrorHandler.error("Invalid command for command action!");
            return null;
         }

         commands.add(element.getAsString());
      }

      return new CommandAction(commands);
   }
}
