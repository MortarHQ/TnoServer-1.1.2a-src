package dev.ftb.mods.ftbxmodcompat.ftbquests.filtering;

import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemAPI;
import dev.ftb.mods.ftbfiltersystem.api.FilterException;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.api.ItemFilterAdapter;
import dev.ftb.mods.ftbquests.api.ItemFilterAdapter.Matcher;
import dev.ftb.mods.ftbxmodcompat.FTBXModCompat;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

class FFSSetup {
   public static void init() {
      FTBQuestsAPI.api().registerFilterAdapter(new FFSSetup.FFSAdapter());
      FTBXModCompat.LOGGER.info("[FTB Quests] Enabled FTB Filter System integration");
   }

   private static class FFSAdapter implements ItemFilterAdapter {
      public String getName() {
         return "FTB Filter System";
      }

      public boolean isFilterStack(ItemStack stack) {
         return FTBFilterSystemAPI.api().isFilterItem(stack);
      }

      public boolean doesItemMatch(ItemStack filterStack, ItemStack toCheck, Provider registryAccess) {
         return FTBFilterSystemAPI.api().doesFilterMatch(filterStack, toCheck, registryAccess);
      }

      public Matcher getMatcher(ItemStack filterStack, Provider registryAccess) {
         try {
            return new FFSSetup.FFSMatcher(filterStack, registryAccess);
         } catch (FilterException e) {
            return ItemFilterAdapter.NO_MATCH;
         }
      }

      public ItemStack makeTagFilterStack(TagKey<Item> tag) {
         return FTBFilterSystemAPI.api().makeTagFilter(tag);
      }
   }

   private static class FFSMatcher implements Matcher {
      private final SmartFilter smartFilter;

      public FFSMatcher(ItemStack filterStack, Provider registryAccess) {
         this.smartFilter = FTBFilterSystemAPI.api().parseFilter(filterStack, registryAccess);
      }

      public boolean test(ItemStack stack) {
         return this.smartFilter.test(stack);
      }
   }
}
