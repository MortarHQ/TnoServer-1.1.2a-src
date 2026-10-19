package cc.sighs.oelib.config.util;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.config.ConfigUnit;
import cc.sighs.oelib.config.ui.screen.ConfigScreen;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.DataResult.Error;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public final class ConfigGuiUtil {
   private ConfigGuiUtil() {
   }

   public static JsonElement getPath(JsonObject obj, String path) {
      if (obj != null && path != null) {
         String[] parts = path.split("\\.");
         JsonObject cur = obj;

         for (int i = 0; i < parts.length - 1; i++) {
            String p = parts[i];
            if (!cur.has(p) || !cur.get(p).isJsonObject()) {
               return null;
            }

            cur = cur.getAsJsonObject(p);
         }

         String last = parts[parts.length - 1];
         return cur.has(last) ? cur.get(last) : null;
      } else {
         return null;
      }
   }

   public static void setPath(JsonObject obj, String path, JsonElement value) {
      String[] parts = path.split("\\.");
      JsonObject cur = obj;

      for (int i = 0; i < parts.length - 1; i++) {
         String p = parts[i];
         if (cur.has(p) && cur.get(p).isJsonObject()) {
            cur = cur.getAsJsonObject(p);
         } else {
            JsonObject next = new JsonObject();
            cur.add(p, next);
            cur = next;
         }
      }

      String last = parts[parts.length - 1];
      cur.add(last, value);
   }

   public static String jsonToString(JsonElement el) {
      if (el == null) {
         return "";
      } else {
         return el.isJsonPrimitive() ? el.getAsJsonPrimitive().getAsString() : String.valueOf(el);
      }
   }

   public static JsonPrimitive parsePrimitive(String s) {
      if (s == null) {
         return new JsonPrimitive("");
      }

      String str = s.trim();
      if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")) {
         return new JsonPrimitive(Boolean.parseBoolean(str));
      }

      if (str.matches("^-?\\d+$")) {
         try {
            return new JsonPrimitive(Integer.parseInt(str));
         } catch (Exception var4) {
         }
      }

      if (str.matches("^-?\\d+(?:\\.\\d+)?$")) {
         try {
            return new JsonPrimitive(Double.parseDouble(str));
         } catch (Exception var3) {
         }
      }

      return new JsonPrimitive(str);
   }

   public static ConfigUnit<Object> castUnit(ConfigUnit<?> unit) {
      return (ConfigUnit<Object>)unit;
   }

   public static JsonObject encodeToJsonObject(Codec<?> codec, Object value) {
      DataResult<JsonElement> res = codec.encodeStart(JsonOps.INSTANCE, value);
      JsonElement el = (JsonElement)res.result().orElse(new JsonObject());
      return el.isJsonObject() ? el.getAsJsonObject() : new JsonObject();
   }

   public static JsonObject toJson(Object value, ConfigScreen screen) {
      DataResult<JsonElement> result = screen.codec.codec().encodeStart(JsonOps.INSTANCE, value);
      if (result.error().isPresent()) {
         OELib.LOGGER.error("Failed to encode config {} to JSON: {}", screen.configId, ((Error)result.error().get()).message());
         return new JsonObject();
      } else {
         JsonElement element = (JsonElement)result.result().orElse(new JsonObject());
         return element.isJsonObject() ? element.getAsJsonObject() : new JsonObject();
      }
   }

   public static EditBox createEditBox(String initialValue, int x, int y, int width) {
      EditBox box = new EditBox(Minecraft.getInstance().font, x, y, width, 20, Component.empty());
      box.setMaxLength(2048);
      box.setValue(initialValue);
      box.setFocused(false);
      box.setCursorPosition(0);
      box.setHighlightPos(0);
      return box;
   }
}
