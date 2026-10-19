package cc.sighs.oelib.config.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Map.Entry;

public final class GsonUtil {
   private GsonUtil() {
   }

   public static void removeBlankStringValues(JsonObject obj) {
      if (obj != null) {
         for (Entry<String, JsonElement> e : new ArrayList(obj.entrySet())) {
            JsonElement v = e.getValue();
            if (isBlankString(v)) {
               obj.remove(e.getKey());
            }
         }
      }
   }

   public static void removeBlankStringElements(JsonArray arr) {
      if (arr != null) {
         for (int i = arr.size() - 1; i >= 0; i--) {
            JsonElement el = arr.get(i);
            if (isBlankString(el)) {
               arr.remove(i);
            }
         }
      }
   }

   public static boolean isBlankString(JsonElement el) {
      return el != null && el.isJsonPrimitive() && el.getAsJsonPrimitive().isString() && el.getAsString().isBlank();
   }
}
