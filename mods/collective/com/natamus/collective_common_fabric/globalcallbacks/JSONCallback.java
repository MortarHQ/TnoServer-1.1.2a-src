package com.natamus.collective_common_fabric.globalcallbacks;

import com.google.gson.JsonElement;
import com.natamus.collective_common_fabric.implementations.event.Event;
import com.natamus.collective_common_fabric.implementations.event.EventFactory;
import javax.annotation.Nullable;

public class JSONCallback {
   public static final Event<JSONCallback.On_Json_File_Available> JSON_FILE_AVAILABLE = EventFactory.createArrayBacked(
      JSONCallback.On_Json_File_Available.class, callbacks -> (folder, fileName, isCreated, jsonElement) -> {
         for (JSONCallback.On_Json_File_Available callback : callbacks) {
            callback.onJsonFileAvailable(folder, fileName, isCreated, jsonElement);
         }
      }
   );
   public static final Event<JSONCallback.All_Json_Files_Available> ALL_JSON_FILES_AVAILABLE = EventFactory.createArrayBacked(
      JSONCallback.All_Json_Files_Available.class, callbacks -> folder -> {
         for (JSONCallback.All_Json_Files_Available callback : callbacks) {
            callback.onAllJsonFilesAvailable(folder);
         }
      }
   );

   private JSONCallback() {
   }

   @FunctionalInterface
   public interface All_Json_Files_Available {
      void onAllJsonFilesAvailable(String var1);
   }

   @FunctionalInterface
   public interface On_Json_File_Available {
      void onJsonFileAvailable(String var1, String var2, boolean var3, @Nullable JsonElement var4);
   }
}
