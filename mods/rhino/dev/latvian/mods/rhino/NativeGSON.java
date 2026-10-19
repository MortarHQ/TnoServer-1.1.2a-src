package dev.latvian.mods.rhino;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

public class NativeGSON extends NativeJSON {
   private final Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setLenient().create();

   static void initGSON(Scriptable scope, boolean sealed, Context cx) {
      NativeGSON obj = new NativeGSON();
      obj.activatePrototypeMap(3);
      obj.setPrototype(getObjectPrototype(scope, cx));
      obj.setParentScope(scope);
      if (sealed) {
         obj.sealObject(cx);
      }

      defineProperty(scope, "JSON", obj, 2, cx);
   }

   public static JsonElement stringify0(Context cx, Object v) {
      return (JsonElement)(switch (v) {
         case null -> JsonNull.INSTANCE;
         case JsonElement json -> json;
         case Boolean b -> new JsonPrimitive(b);
         case CharSequence ignore -> new JsonPrimitive(v.toString());
         case Number n -> new JsonPrimitive(n);
         case NativeString ignore -> new JsonPrimitive(ScriptRuntime.toString(cx, v));
         case NativeNumber ignore -> new JsonPrimitive(ScriptRuntime.toNumber(cx, v));
         case Map<?, ?> map -> {
            JsonObject json = new JsonObject();

            for (Entry<?, ?> entry : map.entrySet()) {
               json.add(entry.getKey().toString(), stringify0(cx, entry.getValue()));
            }

            yield json;
         }
         case Iterable<?> itr -> {
            JsonArray json = new JsonArray();

            for (Object o : itr) {
               json.add(stringify0(cx, o));
            }

            yield json;
         }
         default -> {
            JsonArray json = new JsonArray();
            cx.getCachedClassStorage(false).get(Wrapper.unwrapped(v).getClass()).getDebugInfo().forEach(json::add);
            yield json;
         }
      });
   }

   private NativeGSON() {
   }

   @Override
   public String stringifyJSON(Object value, Object replacer, Object space, Context cx) {
      StringWriter stringWriter = new StringWriter();
      JsonWriter writer = new JsonWriter(stringWriter);
      String indent = null;
      if (space instanceof NativeNumber) {
         space = ScriptRuntime.toNumber(cx, space);
      } else if (space instanceof NativeString) {
         space = ScriptRuntime.toString(cx, space);
      }

      if (space instanceof Number) {
         int gapLength = (int)ScriptRuntime.toInteger(cx, space);
         gapLength = Math.min(10, gapLength);
         indent = gapLength > 0 ? " ".repeat(gapLength) : "";
      } else if (space instanceof String) {
         indent = (String)space;
         if (indent.length() > 10) {
            indent = indent.substring(0, 10);
         }
      }

      writer.setIndent(Objects.requireNonNullElse(indent, ""));
      this.gson.toJson(stringify0(cx, value), writer);
      return stringWriter.toString();
   }
}
