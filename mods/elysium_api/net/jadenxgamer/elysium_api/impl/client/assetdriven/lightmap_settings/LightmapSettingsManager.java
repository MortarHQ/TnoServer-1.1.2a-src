package net.jadenxgamer.elysium_api.impl.client.assetdriven.lightmap_settings;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.jadenxgamer.elysium_api.ElysiumAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.joml.Vector3f;

public class LightmapSettingsManager extends SimpleJsonResourceReloadListener {
   private static final Gson GSON = new Gson();
   private static final Vector3f currentSkyColor = new Vector3f(1.0F, 1.0F, 1.0F);
   private static final Vector3f currentBlockColor = new Vector3f(1.0F, 1.0F, 1.0F);
   private static float currentAmbientBrightness = 0.0F;
   private static final List<LightmapSettings> LIGHTMAP_SETTINGS = new ArrayList<>();
   private static final Set<ResourceLocation> ENABLED_EVENT_FLAGS = new HashSet<>();
   public static final ResourceLocation GUI_LIGHTMAP = ElysiumAPI.elysiumPath("textures/misc/gui.png");
   private boolean usingGuiLightmap = false;

   public LightmapSettingsManager() {
      super(GSON, "elysium_api/lightmap_settings");
   }

   protected void apply(Map<ResourceLocation, JsonElement> elements, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
      LIGHTMAP_SETTINGS.clear();

      for (JsonElement element : elements.values()) {
         try {
            JsonObject json = element.getAsJsonObject();
            LightmapSettings settings = LightmapSettings.parseSetting(json);
            LIGHTMAP_SETTINGS.add(settings);
         } catch (Exception e) {
            ElysiumAPI.LOGGER.warn("Couldn't load LightmapSettings: {}", e.getMessage());
         }
      }

      LIGHTMAP_SETTINGS.sort(Comparator.comparingInt(LightmapSettings::priority).reversed());
   }

   @Internal
   public Triple<Vector3f, Vector3f, Float> getSettings(Player player) {
      if (player == null) {
         return Triple.of(new Vector3f(1.0F, 1.0F, 1.0F), new Vector3f(1.0F, 1.0F, 1.0F), 0.0F);
      }

      Level level = player.level();
      BlockPos pos = BlockPos.containing(player.getX(), player.getEyeY(), player.getZ());
      ResourceLocation biome = level.registryAccess().registryOrThrow(Registries.BIOME).getKey((Biome)level.getBiome(pos).value());
      ResourceLocation dimension = level.dimension().location();
      LightmapSettings matched = null;

      for (LightmapSettings settings : LIGHTMAP_SETTINGS) {
         if (this.matches(settings, biome, dimension)) {
            matched = settings;
            break;
         }
      }

      Vector3f targetSky = new Vector3f(1.0F, 1.0F, 1.0F);
      Vector3f targetBlock = new Vector3f(1.0F, 1.0F, 1.0F);
      float targetBrightness = 0.0F;
      float fadeMultiplier = 1.0F;
      if (matched != null) {
         targetSky = matched.skyLightColor();
         targetBlock = matched.blockLightColor();
         targetBrightness = matched.ambientBrightness();
         fadeMultiplier = matched.fadeMultiplier();
      }

      float delta = Minecraft.getInstance().getTimer().getGameTimeDeltaTicks() * 0.03F * fadeMultiplier;
      currentSkyColor.lerp(targetSky, delta);
      currentBlockColor.lerp(targetBlock, delta);
      currentAmbientBrightness = Mth.lerp(delta, currentAmbientBrightness, targetBrightness);
      return Triple.of(currentSkyColor, currentBlockColor, currentAmbientBrightness);
   }

   private boolean matches(LightmapSettings settings, ResourceLocation biome, ResourceLocation dimension) {
      if (!settings.eventFlags().isEmpty()) {
         for (ResourceLocation flag : settings.eventFlags()) {
            if (!ENABLED_EVENT_FLAGS.contains(flag)) {
               return false;
            }
         }
      }
      return switch (settings.type()) {
         case GLOBAL -> true;
         case BIOME -> settings.biomes().contains(biome);
         case DIMENSION -> settings.dimensions().contains(dimension);
         case NOT_BIOME -> !settings.biomes().contains(biome);
         case NOT_DIMENSION -> !settings.dimensions().contains(dimension);
      };
   }

   public static void enableEventFlag(ResourceLocation flag) {
      ENABLED_EVENT_FLAGS.add(flag);
   }

   public static void disableEventFlag(ResourceLocation flag) {
      ENABLED_EVENT_FLAGS.remove(flag);
   }

   @Internal
   public void setupForGUI(boolean gui) {
      this.usingGuiLightmap = gui;
   }

   @Internal
   public boolean isGui() {
      return this.usingGuiLightmap;
   }
}
