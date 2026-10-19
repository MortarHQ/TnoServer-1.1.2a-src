package terrablender.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import terrablender.core.TerraBlender;
import terrablender.worldgen.DefaultNetherRegion;
import terrablender.worldgen.DefaultOverworldRegion;

public class Regions {
   private static Map<RegionType, LinkedHashMap<ResourceLocation, Region>> regions = Maps.newHashMap();
   private static Map<RegionType, Map<ResourceLocation, Integer>> indices = Maps.newHashMap();

   public static void register(ResourceLocation name, Region region) {
      regions.get(region.getType()).put(name, region);
      int index = regions.get(region.getType()).size() - 1;
      indices.get(region.getType()).put(name, index);
      TerraBlender.LOGGER.info("Registered region " + name + " to index " + index + " for type " + region.getType());
   }

   public static void register(ResourceLocation name, int index, Region region) {
      List<Entry<ResourceLocation, Region>> entries = Lists.newArrayList(regions.get(region.getType()).entrySet());
      entries.add(index, Map.entry(name, region));
      regions.get(region.getType()).clear();
      entries.forEach(entry -> regions.get(region.getType()).put(entry.getKey(), entry.getValue()));
      TerraBlender.LOGGER.info("Registered region " + name + " to index " + index + " for type " + region.getType());
   }

   public static void register(Region region) {
      register(region.getName(), region);
   }

   public static void remove(RegionType type, ResourceLocation name) {
      if (regions.get(type).containsKey(name)) {
         regions.get(type).remove(name);
         TerraBlender.LOGGER.info("Removed region " + name);
      }
   }

   public static List<Region> get(RegionType type) {
      return ImmutableList.copyOf(regions.get(type).values());
   }

   public static int getIndex(RegionType type, ResourceLocation location) {
      LinkedHashMap<ResourceLocation, Region> typedRegions = regions.get(type);
      Map<ResourceLocation, Integer> typedIndices = indices.get(type);
      if (typedIndices.containsKey(location)) {
         return typedIndices.get(location);
      }

      if (!typedRegions.containsKey(location)) {
         throw new RuntimeException("Attempted to get index of an unregistered region " + location);
      }

      int index = ImmutableList.copyOf(typedRegions.keySet()).indexOf(location);
      typedIndices.put(location, index);
      return index;
   }

   public static int getCount(RegionType type) {
      return regions.get(type).size();
   }

   static {
      for (RegionType type : RegionType.values()) {
         regions.put(type, Maps.newLinkedHashMap());
         indices.put(type, Maps.newHashMap());
      }

      register(new DefaultOverworldRegion(TerraBlender.CONFIG.vanillaOverworldRegionWeight));
      register(new DefaultNetherRegion(TerraBlender.CONFIG.vanillaNetherRegionWeight));
   }
}
