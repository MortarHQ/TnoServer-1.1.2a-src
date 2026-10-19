package dev.latvian.mods.kubejs.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapLike;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;

public interface JsonUtils {
   @HideFromJS
   Gson GSON = new GsonBuilder().disableHtmlEscaping().setLenient().serializeNulls().create();
   MapLike<JsonElement> MAP_LIKE = MapLike.forMap(Map.of(), JsonOps.INSTANCE);

   static JsonElement copy(@Nullable JsonElement element) {
      return (JsonElement)(switch (element) {
         case null -> JsonNull.INSTANCE;
         case JsonNull jsonNull -> JsonNull.INSTANCE;
         case JsonArray jsonElements -> {
            JsonArray a = new JsonArray();

            for (JsonElement e : jsonElements) {
               a.add(copy(e));
            }

            yield a;
         }
         case JsonObject jsonObject -> {
            JsonObject o = new JsonObject();

            for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
               o.add(entry.getKey(), copy(entry.getValue()));
            }

            yield o;
         }
         default -> element;
      });
   }

   static JsonElement of(Context cx, @Nullable Object o) {
      return (JsonElement)(switch (o) {
         case null -> JsonNull.INSTANCE;
         case JsonElement e -> e;
         case JsonSerializable s -> s.toJson(cx);
         case CharSequence ignore -> new JsonPrimitive(o.toString());
         case Boolean b -> new JsonPrimitive(b);
         case Number n -> new JsonPrimitive(n);
         case Character c -> new JsonPrimitive(c);
         default -> cx.isMapLike(o) ? objectOf(cx, o) : (cx.isListLike(o) ? arrayOf(cx, o) : JsonNull.INSTANCE);
      });
   }

   static JsonPrimitive primitiveOf(Context cx, @Nullable Object o) {
      return of(cx, o) instanceof JsonPrimitive p ? p : null;
   }

   @Nullable
   static JsonObject objectOf(Context cx, @Nullable Object map) {
      if (map instanceof JsonObject json) {
         return json;
      } else if (map instanceof CharSequence) {
         try {
            return (JsonObject)GSON.fromJson(map.toString(), JsonObject.class);
         } catch (Exception ex) {
            return null;
         }
      } else {
         Map<?, ?> m = (Map<?, ?>)cx.jsToJava(map, TypeInfo.RAW_MAP);
         if (m == null) {
            return null;
         }

         JsonObject json = new JsonObject();

         for (Entry<?, ?> entry : m.entrySet()) {
            JsonElement e = of(cx, entry.getValue());
            if (e instanceof JsonPrimitive p
               && p.isNumber()
               && p.getAsNumber() instanceof Double d
               && d <= 9.223372E18F
               && d >= -9.223372E18F
               && d == d.longValue()) {
               long l = d.longValue();
               if (l >= -2147483648L && l <= 2147483647L) {
                  json.add(String.valueOf(entry.getKey()), new JsonPrimitive((int)l));
               } else {
                  json.add(String.valueOf(entry.getKey()), new JsonPrimitive(l));
               }
            } else {
               json.add(String.valueOf(entry.getKey()), e);
            }
         }

         return json;
      }
   }

   @Nullable
   static JsonArray arrayOf(Context cx, @Nullable Object array) {
      return switch (array) {
         case JsonArray arr -> {
            JsonArray var14 = arr;
            yield var14;
         }
         case CharSequence cs -> {
            JsonArray var13;
            try {
               var13 = (JsonArray)GSON.fromJson(cs.toString(), JsonArray.class);
            } catch (Exception ex) {
               Object var12 = null;
               yield var12;
            }

            yield var13;
         }
         case Iterable<?> itr -> {
            JsonArray json = new JsonArray();

            for (Object o1 : itr) {
               json.add(of(cx, o1));
            }

            JsonArray var4 = json;
            yield var4;
         }
         case null, default -> {
            Object var15 = null;
            yield var15;
         }
      };
   }

   @Nullable
   static Object toObject(@Nullable JsonElement json) {
      return switch (json) {
         case null -> null;
         case JsonNull jsonNull -> null;
         case JsonObject o -> {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();

            for (Entry<String, JsonElement> entry : o.entrySet()) {
               map.put(entry.getKey(), toObject(entry.getValue()));
            }

            yield map;
         }
         case JsonArray a -> {
            ArrayList<Object> objects = new ArrayList<>(a.size());

            for (JsonElement e : a) {
               objects.add(toObject(e));
            }

            yield objects;
         }
         default -> toPrimitive(json);
      };
   }

   static String toString(JsonElement json) {
      return GSON.toJson(json);
   }

   static String toPrettyString(JsonElement json) {
      StringWriter writer = new StringWriter();
      JsonWriter jsonWriter = new JsonWriter(writer);
      jsonWriter.setIndent("\t");
      GSON.toJson(json, jsonWriter);
      return writer.toString();
   }

   static JsonElement fromString(@Nullable String string) {
      if (string != null && !string.isEmpty() && !string.equals("null")) {
         try {
            return (JsonElement)GSON.fromJson(string, JsonElement.class);
         } catch (Exception ex) {
            ex.printStackTrace();
            return JsonNull.INSTANCE;
         }
      } else {
         return JsonNull.INSTANCE;
      }
   }

   @Nullable
   static Object toPrimitive(@Nullable JsonElement element) {
      return switch (element) {
         case JsonPrimitive p -> {
            if (p.isBoolean()) {
               Boolean var3 = p.getAsBoolean();
               yield var3;
            } else if (p.isNumber()) {
               Number var7 = p.getAsNumber();
               yield var7;
            } else {
               Number var9;
               try {
                  Double.parseDouble(p.getAsString());
                  var9 = p.getAsNumber();
               } catch (Exception ex) {
                  String var8 = p.getAsString();
                  yield var8;
               }

               yield var9;
            }
         }
         case null, default -> {
            Object var10 = null;
            yield var10;
         }
      };
   }
}
