package net.jadenxgamer.elysium_api.impl.client.assetdriven.fog_settings;

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
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;

public class FogSettingsManager extends SimpleJsonResourceReloadListener {
   private static final Gson GSON = new Gson();
   private static float currentStartMultiplier = 1.0F;
   private static float currentEndMultiplier = 1.0F;
   private static final List<FogSettings> FOG_SETTINGS = new ArrayList<>();
   private static final Set<ResourceLocation> ENABLED_EVENT_FLAGS = new HashSet<>();

   public FogSettingsManager() {
      super(GSON, "elysium_api/fog_settings");
   }

   protected void apply(Map<ResourceLocation, JsonElement> elements, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
      FOG_SETTINGS.clear();

      for (JsonElement element : elements.values()) {
         try {
            JsonObject json = element.getAsJsonObject();
            FogSettings settings = FogSettings.parseSetting(json);
            FOG_SETTINGS.add(settings);
         } catch (Exception e) {
            ElysiumAPI.LOGGER.warn("Couldn't load FogSettings: {}", e.getMessage());
         }
      }

      FOG_SETTINGS.sort(Comparator.comparingInt(FogSettings::priority).reversed());
   }

   @Internal
   public Pair<Float, Float> getSettings(Player player, float fogStart, float fogEnd) {
      if (player == null) {
         return Pair.of(fogStart, fogEnd);
      }

      Level level = player.level();
      BlockPos pos = BlockPos.containing(player.getX(), player.getEyeY(), player.getZ());
      ResourceLocation biome = level.registryAccess().registryOrThrow(Registries.BIOME).getKey((Biome)level.getBiome(pos).value());
      ResourceLocation dimension = level.dimension().location();
      FogSettings matched = null;

      for (FogSettings settings : FOG_SETTINGS) {
         if (this.matches(settings, biome, dimension)) {
            matched = settings;
            break;
         }
      }

      float targetStartMultiplier = 1.0F;
      float targetEndMultiplier = 1.0F;
      float fadeMultiplier = 1.0F;
      if (matched != null) {
         targetStartMultiplier = matched.fogStartMultiplier();
         targetEndMultiplier = matched.fogEndMultiplier();
         fadeMultiplier = matched.fadeMultiplier();
      }

      float delta = Minecraft.getInstance().getTimer().getGameTimeDeltaTicks() * 0.03F * fadeMultiplier;
      currentStartMultiplier = Mth.lerp(delta, currentStartMultiplier, targetStartMultiplier);
      currentEndMultiplier = Mth.lerp(delta, currentEndMultiplier, targetEndMultiplier);
      return Pair.of(fogStart * currentStartMultiplier, fogEnd * currentEndMultiplier);
   }

   private boolean matches(FogSettings settings, ResourceLocation biome, ResourceLocation dimension) {
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
}
