package it.hurts.sskirillss.relics.client.screen.description.misc;

import it.hurts.sskirillss.relics.client.screen.description.general.misc.DescriptionTab;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.world.item.ItemStack;

public class DescriptionCache {
   private static final Map<IRelicItem, DescriptionCache.CacheEntry> CACHE = new HashMap<>();

   public static DescriptionCache.CacheEntry getEntry(IRelicItem relic) {
      return CACHE.computeIfAbsent(relic, entry -> new DescriptionCache.CacheEntry());
   }

   public static void setEntry(IRelicItem relic, DescriptionCache.CacheEntry cache) {
      CACHE.put(relic, cache);
   }

   public static String getSelectedAbility(ItemStack stack) {
      if (stack.getItem() instanceof IRelicItem relic) {
         DescriptionCache.CacheEntry cache = getEntry(relic);
         int index = cache.getSelectionIndex(DescriptionTab.ABILITY);
         List<String> abilities = relic.getAbilitiesData().getAbilities().keySet().stream().filter(entry -> relic.isAbilityEnabled(stack, entry)).toList();
         int size = abilities.size();
         if (size == 0) {
            return "";
         }

         if (index >= size) {
            index = size - 1;
         }

         String ability = abilities.get(index);
         if (ability == null) {
            int var8 = 0;
            ability = abilities.get(var8);
            setEntry(relic, cache.toBuilder().selectionIndex(DescriptionTab.ABILITY, var8).build());
         }

         return ability;
      } else {
         return "";
      }
   }

   public static void setSelectedAbility(ItemStack stack, String ability) {
      if (stack.getItem() instanceof IRelicItem relic) {
         DescriptionCache.CacheEntry var6 = getEntry(relic);
         List<String> abilities = relic.getAbilitiesData().getAbilities().keySet().stream().filter(entry -> relic.isAbilityEnabled(stack, entry)).toList();
         if (abilities.contains(ability)) {
            int index = abilities.indexOf(ability);
            setEntry(relic, var6.toBuilder().selectionIndex(DescriptionTab.ABILITY, index).build());
         }
      }
   }

   public static String getSelectedExperienceSource(ItemStack stack) {
      if (stack.getItem() instanceof IRelicItem relic) {
         DescriptionCache.CacheEntry cache = getEntry(relic);
         int index = cache.getSelectionIndex(DescriptionTab.EXPERIENCE);
         List<String> sources = relic.getLevelingSourcesData()
            .getSources()
            .keySet()
            .stream()
            .filter(entry -> relic.isLevelingSourceEnabled(stack, entry))
            .toList();
         int size = sources.size();
         if (size == 0) {
            return "";
         }

         if (index >= size) {
            index = size - 1;
         }

         String source = sources.get(index);
         if (source == null) {
            int var8 = 0;
            source = sources.get(var8);
            setEntry(relic, cache.toBuilder().selectionIndex(DescriptionTab.EXPERIENCE, var8).build());
         }

         return source;
      } else {
         return "";
      }
   }

   public static void setSelectedExperienceSource(ItemStack stack, String source) {
      if (stack.getItem() instanceof IRelicItem relic) {
         DescriptionCache.CacheEntry var6 = getEntry(relic);
         List<String> sources = relic.getLevelingSourcesData()
            .getSources()
            .keySet()
            .stream()
            .filter(entry -> relic.isLevelingSourceEnabled(stack, entry))
            .toList();
         if (sources.contains(source)) {
            int index = sources.indexOf(source);
            setEntry(relic, var6.toBuilder().selectionIndex(DescriptionTab.EXPERIENCE, index).build());
         }
      }
   }

   public static class CacheEntry {
      private DescriptionTab selectedPage = DescriptionTab.RELIC;
      private Map<DescriptionTab, Integer> selectionIndices = new HashMap<DescriptionTab, Integer>() {
         {
            for (DescriptionTab page : DescriptionTab.values()) {
               this.put(page, 0);
            }
         }
      };

      public int getSelectionIndex(DescriptionTab page) {
         return this.selectionIndices.computeIfAbsent(page, entry -> 0);
      }

      public static DescriptionCache.CacheEntry.CacheEntryBuilder builder() {
         return new DescriptionCache.CacheEntry.CacheEntryBuilder();
      }

      public DescriptionCache.CacheEntry.CacheEntryBuilder toBuilder() {
         return new DescriptionCache.CacheEntry.CacheEntryBuilder().selectedPage(this.selectedPage).selectionIndices(this.selectionIndices);
      }

      public CacheEntry() {
      }

      public CacheEntry(DescriptionTab selectedPage, Map<DescriptionTab, Integer> selectionIndices) {
         this.selectedPage = selectedPage;
         this.selectionIndices = selectionIndices;
      }

      public DescriptionTab getSelectedPage() {
         return this.selectedPage;
      }

      public static class CacheEntryBuilder {
         private DescriptionTab selectedPage;
         private Map<DescriptionTab, Integer> selectionIndices;

         public DescriptionCache.CacheEntry.CacheEntryBuilder selectionIndex(DescriptionTab page, int index) {
            this.selectionIndices.put(page, index);
            return this;
         }

         CacheEntryBuilder() {
         }

         public DescriptionCache.CacheEntry.CacheEntryBuilder selectedPage(DescriptionTab selectedPage) {
            this.selectedPage = selectedPage;
            return this;
         }

         public DescriptionCache.CacheEntry.CacheEntryBuilder selectionIndices(Map<DescriptionTab, Integer> selectionIndices) {
            this.selectionIndices = selectionIndices;
            return this;
         }

         public DescriptionCache.CacheEntry build() {
            return new DescriptionCache.CacheEntry(this.selectedPage, this.selectionIndices);
         }

         @Override
         public String toString() {
            return "DescriptionCache.CacheEntry.CacheEntryBuilder(selectedPage=" + this.selectedPage + ", selectionIndices=" + this.selectionIndices + ")";
         }
      }
   }
}
