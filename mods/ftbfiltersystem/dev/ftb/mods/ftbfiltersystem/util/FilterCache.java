package dev.ftb.mods.ftbfiltersystem.util;

import dev.ftb.mods.ftbfiltersystem.api.FilterException;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import net.minecraft.core.HolderLookup.Provider;

public enum FilterCache {
   INSTANCE;

   private static final int MAX_SIZE = 1000;
   private final LRUCache<String, SmartFilter> cache = new LRUCache<>(1000);

   public SmartFilter getOrCreateFilter(String filterStr, Provider registryAccess) throws FilterException {
      SmartFilter res = this.cache.get(filterStr);
      if (res == null) {
         try {
            res = FilterParser.parseRaw(filterStr, registryAccess);
         } catch (FilterException var5) {
         }

         this.cache.put(filterStr, res);
      }

      return res;
   }

   public void clear() {
      this.cache.clear();
   }
}
