package com.finderfeed.fdlib.systems.config;

import com.finderfeed.fdlib.FDLib;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLPaths;

public abstract class JsonConfig {
   protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private Path path;
   private Path directory;
   private String loadedJson;
   private ResourceLocation name;

   public JsonConfig(ResourceLocation location) {
      String p = location.getPath();
      int id = p.lastIndexOf(47);
      if (id != -1) {
         String filename = p.substring(id + 1);
         p = p.substring(0, id + 1);
         this.directory = FMLPaths.CONFIGDIR.get().resolve(location.getNamespace()).resolve(p);
         this.path = this.directory.resolve(filename + ".json");
      } else {
         this.directory = FMLPaths.CONFIGDIR.get().resolve(location.getNamespace());
         this.path = this.directory.resolve(p + ".json");
      }

      this.name = location;
   }

   public void loadFromDisk() {
      try {
         FDLib.LOGGER.info("Loading config " + this.name);
         if (!Files.exists(this.path)) {
            Files.createDirectories(this.directory);
            Writer writer = Files.newBufferedWriter(this.path);
            GSON.toJson(new JsonObject(), writer);
            writer.close();
         }

         Reader reader = Files.newBufferedReader(this.path);
         JsonObject object = (JsonObject)GSON.fromJson(reader, JsonObject.class);
         reader.close();
         boolean changesWereMade;
         if (object == null) {
            changesWereMade = true;
            object = new JsonObject();
            this.parseJson(object);
         } else {
            changesWereMade = this.parseJson(object);
         }

         if (changesWereMade) {
            Writer writer = Files.newBufferedWriter(this.path);
            GSON.toJson(object, writer);
            writer.close();
         }

         this.loadedJson = object.toString();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public abstract boolean parseJson(JsonObject var1);

   public abstract boolean isClientside();

   public ResourceLocation getName() {
      return this.name;
   }

   public String getLoadedJson() {
      return this.loadedJson;
   }

   public void setLoadedJson(JsonObject loadedJson) {
      this.loadedJson = loadedJson.toString();
   }
}
