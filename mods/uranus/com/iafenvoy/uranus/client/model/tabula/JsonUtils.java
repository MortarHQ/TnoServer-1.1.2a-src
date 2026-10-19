package com.iafenvoy.uranus.client.model.tabula;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.Reader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.StringUtils;

public class JsonUtils {
   public static String getString(JsonElement json, String memberName) {
      if (json.isJsonPrimitive()) {
         return json.getAsString();
      } else {
         throw new JsonSyntaxException("Expected " + memberName + " to be a string, was " + toString(json));
      }
   }

   public static String getString(JsonObject json, String memberName) {
      if (json.has(memberName)) {
         return getString(json.get(memberName), memberName);
      } else {
         throw new JsonSyntaxException("Missing " + memberName + ", expected to find a string");
      }
   }

   public static String getString(JsonObject json, String memberName, String fallback) {
      return json.has(memberName) ? getString(json.get(memberName), memberName) : fallback;
   }

   public static Item getByNameOrId(String id) {
      return (Item)BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(id));
   }

   public static Item getItem(JsonElement json, String memberName) {
      if (json.isJsonPrimitive()) {
         String s = json.getAsString();
         return getByNameOrId(s);
      } else {
         throw new JsonSyntaxException("Expected " + memberName + " to be an item, was " + toString(json));
      }
   }

   public static Item getItem(JsonObject json, String memberName) {
      if (json.has(memberName)) {
         return getItem(json.get(memberName), memberName);
      } else {
         throw new JsonSyntaxException("Missing " + memberName + ", expected to find an item");
      }
   }

   public static boolean getBoolean(JsonElement json, String memberName) {
      if (json.isJsonPrimitive()) {
         return json.getAsBoolean();
      } else {
         throw new JsonSyntaxException("Expected " + memberName + " to be a Boolean, was " + toString(json));
      }
   }

   public static boolean getBoolean(JsonObject json, String memberName) {
      if (json.has(memberName)) {
         return getBoolean(json.get(memberName), memberName);
      } else {
         throw new JsonSyntaxException("Missing " + memberName + ", expected to find a Boolean");
      }
   }

   public static boolean getBoolean(JsonObject json, String memberName, boolean fallback) {
      return json.has(memberName) ? getBoolean(json.get(memberName), memberName) : fallback;
   }

   public static float getFloat(JsonElement json, String memberName) {
      if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber()) {
         return json.getAsFloat();
      } else {
         throw new JsonSyntaxException("Expected " + memberName + " to be a Float, was " + toString(json));
      }
   }

   public static float getFloat(JsonObject json, String memberName) {
      if (json.has(memberName)) {
         return getFloat(json.get(memberName), memberName);
      } else {
         throw new JsonSyntaxException("Missing " + memberName + ", expected to find a Float");
      }
   }

   public static float getFloat(JsonObject json, String memberName, float fallback) {
      return json.has(memberName) ? getFloat(json.get(memberName), memberName) : fallback;
   }

   public static int getInt(JsonElement json, String memberName) {
      if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber()) {
         return json.getAsInt();
      } else {
         throw new JsonSyntaxException("Expected " + memberName + " to be a Int, was " + toString(json));
      }
   }

   public static int getInt(JsonObject json, String memberName) {
      if (json.has(memberName)) {
         return getInt(json.get(memberName), memberName);
      } else {
         throw new JsonSyntaxException("Missing " + memberName + ", expected to find a Int");
      }
   }

   public static int getInt(JsonObject json, String memberName, int fallback) {
      return json.has(memberName) ? getInt(json.get(memberName), memberName) : fallback;
   }

   public static JsonObject getJsonObject(JsonElement json, String memberName) {
      if (json.isJsonObject()) {
         return json.getAsJsonObject();
      } else {
         throw new JsonSyntaxException("Expected " + memberName + " to be a JsonObject, was " + toString(json));
      }
   }

   public static JsonObject getJsonObject(JsonObject json, String memberName) {
      if (json.has(memberName)) {
         return getJsonObject(json.get(memberName), memberName);
      } else {
         throw new JsonSyntaxException("Missing " + memberName + ", expected to find a JsonObject");
      }
   }

   public static JsonArray getJsonArray(JsonElement json, String memberName) {
      if (json.isJsonArray()) {
         return json.getAsJsonArray();
      } else {
         throw new JsonSyntaxException("Expected " + memberName + " to be a JsonArray, was " + toString(json));
      }
   }

   public static JsonArray getJsonArray(JsonObject json, String memberName) {
      if (json.has(memberName)) {
         return getJsonArray(json.get(memberName), memberName);
      } else {
         throw new JsonSyntaxException("Missing " + memberName + ", expected to find a JsonArray");
      }
   }

   public static String toString(JsonElement json) {
      String s = StringUtils.abbreviateMiddle(String.valueOf(json), "...", 10);
      if (json == null) {
         return "null (missing)";
      }

      if (json.isJsonNull()) {
         return "null (json)";
      }

      if (json.isJsonArray()) {
         return "an array (" + s + ")";
      }

      if (json.isJsonObject()) {
         return "an object (" + s + ")";
      }

      if (json.isJsonPrimitive()) {
         JsonPrimitive jsonprimitive = json.getAsJsonPrimitive();
         if (jsonprimitive.isNumber()) {
            return "a number (" + s + ")";
         }

         if (jsonprimitive.isBoolean()) {
            return "a boolean (" + s + ")";
         }
      }

      return s;
   }

   public static <T> T gsonDeserialize(Gson gsonIn, Reader readerIn, Class<T> adapter, boolean lenient) {
      try {
         JsonReader jsonreader = new JsonReader(readerIn);
         jsonreader.setLenient(lenient);
         return (T)gsonIn.getAdapter(adapter).read(jsonreader);
      } catch (IOException ioexception) {
         throw new JsonParseException(ioexception);
      }
   }
}
