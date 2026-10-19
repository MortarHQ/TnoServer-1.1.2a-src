package cc.abbie.emi_ores.client.config;

import cc.abbie.emi_ores.EmiOres;
import cc.abbie.emi_ores.Platform;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public record EmiOresClientConfig(boolean addBiomesToIndex) {
   public static Codec<EmiOresClientConfig> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(Codec.BOOL.fieldOf("add_biomes_to_index").forGetter(EmiOresClientConfig::addBiomesToIndex))
         .apply(instance, EmiOresClientConfig::new)
   );
   private static final EmiOresClientConfig DEFAULT = new EmiOresClientConfig(true);
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   public static EmiOresClientConfig INSTANCE;

   private static File getConfigFile() {
      return Platform.getConfigDir().resolve("emi_ores-client.json").toFile();
   }

   private static EmiOresClientConfig loadInner() {
      try (FileReader fileReader = new FileReader(getConfigFile())) {
         return CODEC.parse(JsonOps.INSTANCE, (JsonElement)GSON.fromJson(fileReader, JsonElement.class)).resultOrPartial().orElse(DEFAULT);
      } catch (IOException | JsonSyntaxException | JsonIOException e) {
         return DEFAULT;
      }
   }

   public static void load() {
      INSTANCE = loadInner();
      save();
   }

   public static void save() {
      try (FileWriter fileWriter = new FileWriter(getConfigFile())) {
         getConfigFile().getParentFile().mkdirs();
         JsonElement jsonElement = (JsonElement)CODEC.encodeStart(JsonOps.INSTANCE, INSTANCE).getOrThrow();
         GSON.toJson(jsonElement, fileWriter);
      } catch (IOException | IllegalStateException | JsonIOException e) {
         EmiOres.LOG.error("Failed to save config", e);
      }
   }
}
