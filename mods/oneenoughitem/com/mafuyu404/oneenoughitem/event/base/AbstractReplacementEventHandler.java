package com.mafuyu404.oneenoughitem.event.base;

import cc.sighs.oelib.neoforge.data.DataManager;
import com.mafuyu404.oneenoughitem.Oneenoughitem;
import com.mafuyu404.oneenoughitem.data.Replacements;
import java.util.Map.Entry;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractReplacementEventHandler<T> {
   protected abstract void clearModuleCache();

   protected abstract void putToModuleCache(Replacements var1, RegistryLookup<T> var2);

   protected abstract boolean tryResolveData(String var1, RegistryLookup<T> var2);

   protected abstract boolean tryResolveTag(String var1, RegistryLookup<T> var2);

   protected Replacements buildReplacements(Replacements r) {
      return r;
   }

   protected boolean acceptLocation(ResourceLocation location) {
      return true;
   }

   private boolean isValidForCurrentDomain(Replacements r, RegistryLookup<T> registryLookup) {
      String result = r.result();
      if (result != null && !result.isBlank()) {
         boolean resultOk = result.startsWith("#") ? this.tryResolveTag(result.substring(1), registryLookup) : this.tryResolveData(result, registryLookup);
         if (!resultOk) {
            return false;
         }

         for (String id : r.match()) {
            if (id != null && !id.isBlank()) {
               boolean ok = id.startsWith("#") ? this.tryResolveTag(id.substring(1), registryLookup) : this.tryResolveData(id, registryLookup);
               if (ok) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public void rebuildReplacementCache(String reason, DataManager<Replacements> manager, RegistryLookup<T> registryLookup) {
      if (manager == null) {
         Oneenoughitem.LOGGER.warn("No replacement data manager found (reason: {})", reason);
      } else {
         this.clearModuleCache();

         for (Entry<ResourceLocation, Replacements> e : manager.getAllData().entrySet()) {
            ResourceLocation location = e.getKey();
            if (this.acceptLocation(location)) {
               Replacements rr = this.buildReplacements(e.getValue());
               if (this.isValidForCurrentDomain(rr, registryLookup)) {
                  this.putToModuleCache(rr, registryLookup);
               }
            }
         }

         Oneenoughitem.LOGGER.debug("Rebuilt replacement cache (reason: {})", reason);
      }
   }
}
