package com.blackgear.vanillabackport.client.resources.color;

import com.blackgear.vanillabackport.core.VanillaBackport;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class LeafColorReloadListener extends SimpleJsonResourceReloadListener {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private static final Map<Block, LeafColorReloadListener.Properties> CUSTOM_COLORS = new ConcurrentHashMap<>();
   public static final LeafColorReloadListener INSTANCE = new LeafColorReloadListener();

   public LeafColorReloadListener() {
      super(GSON, "leaf_colors");
   }

   protected void apply(Map<ResourceLocation, JsonElement> resources, ResourceManager resourceManager, ProfilerFiller profiler) {
      Map<Block, Map<ResourceLocation, LeafColorReloadListener.Properties>> blockEntries = new HashMap<>();
      int skippedCount = 0;

      for (Entry<ResourceLocation, JsonElement> entry : resources.entrySet()) {
         JsonElement element = entry.getValue();
         if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            if (object.has("block") && object.has("properties")) {
               String blockId = object.get("block").getAsString();
               ResourceLocation blockLocation = ResourceLocation.tryParse(blockId);
               if (blockLocation != null && BuiltInRegistries.BLOCK.containsKey(blockLocation)) {
                  JsonObject properties = object.getAsJsonObject("properties");
                  if (!properties.has("color")) {
                     skippedCount++;
                  } else {
                     Block block = (Block)BuiltInRegistries.BLOCK.get(blockLocation);
                     int color = properties.get("color").getAsInt();
                     int priority = properties.has("priority") ? properties.get("priority").getAsInt() : 0;
                     blockEntries.computeIfAbsent(block, k -> new HashMap<>()).put(entry.getKey(), new LeafColorReloadListener.Properties(color, priority));
                  }
               } else {
                  skippedCount++;
               }
            } else {
               skippedCount++;
            }
         }
      }

      CUSTOM_COLORS.clear();

      for (Entry<Block, Map<ResourceLocation, LeafColorReloadListener.Properties>> blockEntry : blockEntries.entrySet()) {
         Block block = blockEntry.getKey();
         LeafColorReloadListener.Properties selectedEntry = getColorProperties(blockEntry);
         if (selectedEntry != null) {
            CUSTOM_COLORS.put(block, selectedEntry);
         }
      }

      for (Entry<Block, LeafColorReloadListener.Properties> finalEntry : CUSTOM_COLORS.entrySet()) {
         Block block = finalEntry.getKey();
         LeafColorReloadListener.Properties finalProps = finalEntry.getValue();
         Map<ResourceLocation, LeafColorReloadListener.Properties> allEntries = blockEntries.get(block);
         if (allEntries != null && allEntries.size() > 1) {
            int maxPriority = allEntries.values().stream().mapToInt(p -> p.priority).max().orElse(0);
            if (finalProps.priority != maxPriority) {
               LeafColorReloadListener.Properties correctEntry = allEntries.values()
                  .stream()
                  .filter(p -> p.priority == maxPriority)
                  .findFirst()
                  .orElse(finalProps);
               CUSTOM_COLORS.put(block, correctEntry);
            }
         }
      }

      if (skippedCount > 0) {
         VanillaBackport.LOGGER.info("Skipping {} leaf color(s).", skippedCount);
      }

      if (!CUSTOM_COLORS.isEmpty()) {
         VanillaBackport.LOGGER
            .info(
               "Loaded {} custom leaf colors: {}",
               CUSTOM_COLORS.size(),
               CUSTOM_COLORS.keySet().stream().map(blockx -> BuiltInRegistries.BLOCK.getKey(blockx).toString()).sorted().toList()
            );
      }
   }

   @Nullable
   private static LeafColorReloadListener.Properties getColorProperties(Entry<Block, Map<ResourceLocation, LeafColorReloadListener.Properties>> blockEntry) {
      Map<ResourceLocation, LeafColorReloadListener.Properties> entries = blockEntry.getValue();
      LeafColorReloadListener.Properties selectedEntry = null;
      int highestPriority = Integer.MIN_VALUE;

      for (Entry<ResourceLocation, LeafColorReloadListener.Properties> entry : entries.entrySet()) {
         LeafColorReloadListener.Properties props = entry.getValue();
         if (props.priority > highestPriority) {
            highestPriority = props.priority;
            selectedEntry = props;
         }
      }

      return selectedEntry;
   }

   public static int getCustomColor(Block block) {
      LeafColorReloadListener.Properties entry = CUSTOM_COLORS.get(block);
      return entry != null ? entry.color : 0;
   }

   public static boolean hasCustomColor(Block block) {
      return CUSTOM_COLORS.containsKey(block);
   }

   private record Properties(int color, int priority) {
   }
}
