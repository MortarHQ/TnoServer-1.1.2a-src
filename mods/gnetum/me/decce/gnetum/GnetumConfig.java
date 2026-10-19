package me.decce.gnetum;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import me.decce.gnetum.mixins.GuiLayerManagerAccessor;
import me.decce.gnetum.util.AnyBooleanValue;
import me.decce.gnetum.util.TriStateBoolean;
import me.decce.gnetum.util.TwoStateBoolean;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;

public class GnetumConfig {
   public static final int UNLIMITED_FPS = 125;
   public static final int SCREEN_UNLIMITED_FPS = 65;
   private static final Path PATH = FMLPaths.CONFIGDIR.get().resolve("gnetum.json");
   public TwoStateBoolean enabled = new TwoStateBoolean(AnyBooleanValue.ON);
   public TwoStateBoolean showHudFps = new TwoStateBoolean(AnyBooleanValue.ON);
   public TwoStateBoolean downscale = new TwoStateBoolean(AnyBooleanValue.OFF);
   public int numberOfPasses = 3;
   private int maxFps = 60;
   public int screenMaxFps = 20;
   public HashMap<String, CacheSetting> mapVanillaElements = new HashMap<>();
   public HashMap<String, CacheSetting> mapModdedElementsPre = new HashMap<>();
   public HashMap<String, CacheSetting> mapModdedElementsPost = new HashMap<>();

   public boolean isEnabled() {
      return this.enabled.get();
   }

   private static GnetumConfig createDefault() {
      GnetumConfig config = new GnetumConfig();
      config.mapVanillaElements.put("gnetum:minecraft_hand", new CacheSetting(1, new TriStateBoolean(AnyBooleanValue.AUTO, false)));
      return config;
   }

   private static GnetumConfig load() {
      try {
         if (!Files.exists(PATH)) {
            return createDefault();
         }

         String json = Files.readString(PATH);
         Gson gson = new Gson();
         GnetumConfig config = (GnetumConfig)gson.fromJson(json, GnetumConfig.class);
         if (!config.mapVanillaElements.containsKey("gnetum:minecraft_hand")) {
            config.mapVanillaElements.put("gnetum:minecraft_hand", new CacheSetting(1, new TriStateBoolean(AnyBooleanValue.AUTO, false)));
         }

         config.mapVanillaElements.get("gnetum:minecraft_hand").enabled.defaultValue = false;
         config.validate();
         return config;
      } catch (IOException e) {
         Gnetum.LOGGER.error("Failed to read configuration!", e);
         return createDefault();
      }
   }

   public void validate() {
      this.numberOfPasses = clamp(this.numberOfPasses, 2, 10);
      this.mapVanillaElements.forEach((s, c) -> c.pass = clamp(c.pass, 1, this.numberOfPasses));
      this.mapModdedElementsPre.forEach((s, c) -> c.pass = clamp(c.pass, 1, this.numberOfPasses));
      this.mapModdedElementsPost.forEach((s, c) -> c.pass = clamp(c.pass, 1, this.numberOfPasses));
      this.maxFps = clamp(this.maxFps, 1, 125);
      this.screenMaxFps = clamp(this.screenMaxFps, 1, 65);
      this.hideElementsOrphanOrUncached();
      this.removeObsoleteVanillaElements();
   }

   private void hideElementsOrphanOrUncached() {
      this.mapModdedElementsPre
         .entrySet()
         .stream()
         .filter(
            entry -> !"gnetum_unknown".equals(entry.getKey()) && !ModList.get().isLoaded(entry.getKey())
               || Gnetum.uncachedElements.has(entry.getKey(), ElementType.PRE)
         )
         .forEach(entry -> entry.getValue().hidden = true);
      this.mapModdedElementsPost
         .entrySet()
         .stream()
         .filter(
            entry -> !"gnetum_unknown".equals(entry.getKey()) && !ModList.get().isLoaded(entry.getKey())
               || Gnetum.uncachedElements.has(entry.getKey(), ElementType.POST)
         )
         .forEach(entry -> entry.getValue().hidden = true);
      GuiLayerManagerAccessor accessor = GuiHelper.getGuiLayerManagerAccessor();
      this.mapVanillaElements.forEach((s, c) -> {
         if (!s.startsWith("gnetum")) {
            if (Gnetum.uncachedElements.has(s) || accessor.getLayers().stream().noneMatch(layer -> layer.name().toString().equals(s))) {
               c.hidden = true;
            }
         }
      });
   }

   private void removeObsoleteVanillaElements() {
      if (PackedVanillaElements.set != null) {
         PackedVanillaElements.set.forEach(key -> this.mapVanillaElements.remove(key));
      }

      this.mapVanillaElements.remove("gnetum.packedElement.left");
      this.mapVanillaElements.remove("gnetum.packedElement.right");
   }

   private static int clamp(int value, int min, int max) {
      return Math.max(Math.min(value, max), min);
   }

   public static void reload() {
      Gnetum.config = load();
   }

   public static void reset() {
      Gnetum.config = createDefault();
      Gnetum.config.save();
   }

   public void save() {
      this.validate();
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String json = gson.toJson(this);

      try {
         Files.writeString(PATH, json, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
      } catch (IOException e) {
         Gnetum.LOGGER.error("Failed to save config file!", e);
      }

      if (PerformanceAnalyzer.latestAnalysisResult != null) {
         PerformanceAnalyzer.latestAnalysisResult.markOutdated();
      }
   }

   public int getMaxFps() {
      if (this.maxFps <= this.screenMaxFps) {
         return this.maxFps;
      } else if (this.screenMaxFps == 65) {
         return this.maxFps;
      } else {
         return Minecraft.getInstance().screen == null ? this.maxFps : this.screenMaxFps;
      }
   }

   public int getRawMaxFps() {
      return this.maxFps;
   }

   public void setMaxFps(int maxFps) {
      this.maxFps = maxFps;
   }
}
