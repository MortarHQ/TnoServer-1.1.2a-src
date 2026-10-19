package dev.ftb.mods.ftbchunks.util;

import dev.ftb.mods.ftbchunks.FTBChunksWorldConfig;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class DimensionFilter {
   private static DimensionFilter.WildcardedRLMatcher dimensionMatcherB = null;
   private static DimensionFilter.WildcardedRLMatcher dimensionMatcherW = null;
   private static DimensionFilter.WildcardedRLMatcher noWilderness = null;

   public static boolean isDimensionOK(ResourceKey<Level> levelKey) {
      ResourceLocation name = levelKey.location();
      return !getDimensionBlacklist().test(name) && (getDimensionWhitelist().isEmpty() || getDimensionWhitelist().test(name));
   }

   public static boolean isNoWildernessDimension(ResourceKey<Level> levelKey) {
      return getNoWildernessList().test(levelKey.location());
   }

   private static DimensionFilter.WildcardedRLMatcher getDimensionWhitelist() {
      if (dimensionMatcherW == null) {
         dimensionMatcherW = new DimensionFilter.WildcardedRLMatcher((Collection<String>)FTBChunksWorldConfig.CLAIM_DIMENSION_WHITELIST.get());
      }

      return dimensionMatcherW;
   }

   private static DimensionFilter.WildcardedRLMatcher getDimensionBlacklist() {
      if (dimensionMatcherB == null) {
         dimensionMatcherB = new DimensionFilter.WildcardedRLMatcher((Collection<String>)FTBChunksWorldConfig.CLAIM_DIMENSION_BLACKLIST.get());
      }

      return dimensionMatcherB;
   }

   private static DimensionFilter.WildcardedRLMatcher getNoWildernessList() {
      if (noWilderness == null) {
         noWilderness = new DimensionFilter.WildcardedRLMatcher((Collection<String>)FTBChunksWorldConfig.NO_WILDERNESS_DIMENSIONS.get());
      }

      return noWilderness;
   }

   public static void clearMatcherCaches() {
      dimensionMatcherB = null;
      dimensionMatcherW = null;
      noWilderness = null;
   }

   private static class WildcardedRLMatcher implements Predicate<ResourceLocation> {
      private final Set<String> namespaces = new ObjectOpenHashSet();
      private final Set<ResourceLocation> reslocs = new ObjectOpenHashSet();

      public WildcardedRLMatcher(Collection<String> toMatch) {
         for (String s : toMatch) {
            if (s.endsWith(":*")) {
               this.namespaces.add(s.split(":")[0]);
            } else {
               ResourceLocation location;
               if ((location = ResourceLocation.tryParse(s)) != null) {
                  this.reslocs.add(location);
               }
            }
         }
      }

      public boolean isEmpty() {
         return this.reslocs.isEmpty() && this.namespaces.isEmpty();
      }

      public boolean test(ResourceLocation resourceLocation) {
         return this.reslocs.contains(resourceLocation) || this.namespaces.contains(resourceLocation.getNamespace());
      }
   }
}
