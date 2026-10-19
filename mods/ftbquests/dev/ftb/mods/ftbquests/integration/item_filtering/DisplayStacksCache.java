package dev.ftb.mods.ftbquests.integration.item_filtering;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import dev.ftb.mods.ftbquests.FTBQuests;
import dev.ftb.mods.ftbquests.api.ItemFilterAdapter;
import dev.ftb.mods.ftbquests.api.event.CustomFilterDisplayItemsEvent;
import dev.ftb.mods.ftbquests.client.FTBQuestsClient;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DisplayStacksCache {
   private static final int MAX_CACHE_SIZE = 1024;
   private static final Int2ObjectLinkedOpenHashMap<List<ItemStack>> cache = new Int2ObjectLinkedOpenHashMap(1024);
   private static List<ItemStack> extraCache = null;

   @NotNull
   public static List<ItemStack> getCachedDisplayStacks(ItemStack filterStack, ItemFilterAdapter adapter, Provider registryAccess) {
      int key = ItemStack.hashItemAndComponents(filterStack);
      List<ItemStack> result = (List<ItemStack>)cache.getAndMoveToFirst(key);
      if (result == null) {
         result = computeMatchingStacks(adapter.getMatcher(filterStack, registryAccess));
         cache.put(key, result);
         if (cache.size() >= 1024) {
            cache.removeLast();
         }
      }

      return result;
   }

   private static List<ItemStack> computeMatchingStacks(ItemFilterAdapter.Matcher matcher) {
      FTBQuestsClient.creativeTabDisplayParams().ifPresent(params -> {
         if (CreativeModeTabs.tryRebuildTabContents(params.enabledFeatures(), params.hasPermissions(), params.holders())) {
            FTBQuests.LOGGER.debug("creative tabs rebuilt, search tab now has {} items", CreativeModeTabs.searchTab().getSearchTabDisplayItems().size());
         }
      });
      Builder<ItemStack> builder = ImmutableList.builder();
      CreativeModeTabs.searchTab().getSearchTabDisplayItems().stream().filter(matcher).forEach(builder::add);
      getExtraDisplayCache().stream().filter(matcher).forEach(builder::add);
      return builder.build();
   }

   public static void clear() {
      cache.clear();
      extraCache = null;
   }

   @NotNull
   private static List<ItemStack> getExtraDisplayCache() {
      if (extraCache == null) {
         Builder<ItemStack> builder = ImmutableList.builder();
         ((Consumer)CustomFilterDisplayItemsEvent.ADD_ITEMSTACK.invoker()).accept(new CustomFilterDisplayItemsEvent(builder::add));
         extraCache = builder.build();
      }

      return extraCache;
   }
}
