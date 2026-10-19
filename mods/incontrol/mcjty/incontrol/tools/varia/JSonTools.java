package mcjty.incontrol.tools.varia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import javax.annotation.Nullable;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class JSonTools {
   public static JsonElement getRootElement(String path, String filename, Logger logger) {
      File file;
      if (path == null) {
         file = new File(filename);
      } else {
         file = new File(path + File.separator + "incontrol", filename);
      }

      if (!file.exists()) {
         makeEmptyRuleFile(file, logger);
         return null;
      }

      logger.log(Level.INFO, "Reading rules from " + filename);
      InputStream inputstream = null;

      try {
         inputstream = new FileInputStream(file);
      } catch (FileNotFoundException e) {
         logger.log(Level.ERROR, "Error reading " + filename + "!");
         return null;
      }

      BufferedReader br;
      try {
         br = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
      } catch (Exception e) {
         logger.log(Level.ERROR, "Error reading " + filename + "!");
         return null;
      }

      JsonParser parser = new JsonParser();
      return parser.parse(br);
   }

   private static void makeEmptyRuleFile(File file, Logger logger) {
      PrintWriter writer;
      try {
         writer = new PrintWriter(file);
      } catch (FileNotFoundException e) {
         logger.log(Level.ERROR, "Error writing " + file.getName() + "!");
         return;
      }

      JsonArray array = new JsonArray();
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      writer.print(gson.toJson(array));
      writer.close();
   }

   public static Optional<JsonElement> getElement(JsonObject element, String name) {
      JsonElement el = element.get(name);
      return el != null ? Optional.of(el) : Optional.empty();
   }

   @Nullable
   public static Float parseFloat(JsonObject jsonObject, String name) {
      return jsonObject.has(name) ? jsonObject.get(name).getAsFloat() : null;
   }

   @Nullable
   public static Integer parseInt(JsonObject jsonObject, String name) {
      return jsonObject.has(name) ? jsonObject.get(name).getAsInt() : null;
   }

   @Nullable
   public static Boolean parseBool(JsonObject jsonObject, String name) {
      return jsonObject.has(name) ? jsonObject.get(name).getAsBoolean() : null;
   }

   public static Stream<Pair<String, String>> asPairs(JsonElement element) {
      Builder<Pair<String, String>> builder = Stream.builder();

      for (Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
         builder.add(Pair.of(entry.getKey(), entry.getValue().getAsString()));
      }

      return builder.build();
   }

   public static Stream<JsonElement> asArrayOrSingle(JsonElement element) {
      if (!element.isJsonArray()) {
         return Stream.of(element);
      }

      Builder<JsonElement> builder = Stream.builder();

      for (JsonElement el : element.getAsJsonArray()) {
         builder.add(el);
      }

      return builder.build();
   }

   public static void addPairs(JsonObject parent, String name, Map<String, String> pairs) {
      if (pairs != null) {
         JsonObject object = new JsonObject();

         for (Entry<String, String> entry : pairs.entrySet()) {
            object.add(entry.getKey(), new JsonPrimitive(entry.getValue()));
         }

         parent.add(name, object);
      }
   }

   public static void addArrayOrSingle(JsonObject parent, String name, Collection<String> strings) {
      if (strings != null) {
         if (strings.size() == 1) {
            parent.add(name, new JsonPrimitive(strings.iterator().next()));
         } else {
            JsonArray array = new JsonArray();

            for (String value : strings) {
               array.add(new JsonPrimitive(value));
            }

            parent.add(name, array);
         }
      }
   }

   public static void addIntArrayOrSingle(JsonObject parent, String name, Collection<Integer> integers) {
      if (integers != null) {
         if (integers.size() == 1) {
            parent.add(name, new JsonPrimitive(integers.iterator().next()));
         } else {
            JsonArray array = new JsonArray();

            for (Integer value : integers) {
               array.add(new JsonPrimitive(value));
            }

            parent.add(name, array);
         }
      }
   }
}
