package net.darkhax.bookshelf.common.impl.data.loot.modifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.darkhax.bookshelf.common.api.data.loot.modifiers.LootPoolAddition;
import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.bookshelf.common.api.registry.register.RegisterLootPoolAdditions;
import net.darkhax.bookshelf.common.api.service.Services;
import net.darkhax.bookshelf.common.impl.Constants;
import net.darkhax.bookshelf.common.mixin.access.loot.AccessorLootPool;
import net.darkhax.bookshelf.common.mixin.access.loot.AccessorLootTable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import org.jetbrains.annotations.Nullable;

public class LootModificationHandler {
   public static final Supplier<LootModificationHandler> HANDLER = CachedSupplier.cache(() -> {
      LootModificationHandler handler = new LootModificationHandler();
      Services.CONTENT_PROVIDERS.get().forEach(provider -> {
         String owner = provider.contentNamespace();
         provider.registerLootPoolAdditions(new RegisterLootPoolAdditions(owner, handler::addPoolEntry));
      });
      return handler;
   });
   private final Map<ResourceLocation, Map<Integer, Map<Integer, List<LootPoolAddition>>>> newPoolEntries = new HashMap<>();

   private void addPoolEntry(ResourceLocation tableId, int poolIndex, int poolHash, LootPoolAddition entry) {
      Map<Integer, Map<Integer, List<LootPoolAddition>>> tableEntries = this.newPoolEntries.computeIfAbsent(tableId, k -> new LinkedHashMap<>());
      Map<Integer, List<LootPoolAddition>> indexEntries = tableEntries.computeIfAbsent(poolIndex, k -> new LinkedHashMap<>());
      List<LootPoolAddition> hashEntries = indexEntries.computeIfAbsent(poolHash, k -> new LinkedList<>());
      hashEntries.add(entry);
   }

   public void processLootTable(ResourceLocation tableId, LootTable table) {
      if (this.newPoolEntries.containsKey(tableId) && table instanceof AccessorLootTable accessor) {
         List<LootPool> pools = accessor.bookshelf$pools();

         for (Entry<Integer, Map<Integer, List<LootPoolAddition>>> indexEntry : this.newPoolEntries.get(tableId).entrySet()) {
            for (Entry<Integer, List<LootPoolAddition>> hashEntry : indexEntry.getValue().entrySet()) {
               LootPool targetPool = findPool(indexEntry.getKey(), hashEntry.getKey(), pools);
               if (targetPool == null) {
                  Constants.LOG
                     .warn(
                        "Could not locate pool {} in table '{}'. The following loot additions will not be applied. {}",
                        new Object[]{hashEntry.getKey(), tableId, hashEntry.getValue().stream().map(a -> a.id().toString()).collect(Collectors.joining(", "))}
                     );
               } else if (targetPool instanceof AccessorLootPool pool) {
                  List<LootPoolEntryContainer> entries = new LinkedList<>(pool.bookshelf$entries());

                  for (LootPoolAddition addition : hashEntry.getValue()) {
                     entries.add(addition.entry());
                     Constants.LOG.debug("Added entry `{}` to pool `{}` in table `{}`.", new Object[]{addition.id(), indexEntry.getKey(), tableId});
                  }

                  pool.bookshelf$setEntries(Collections.unmodifiableList(entries));
               }
            }
         }
      }
   }

   @Nullable
   private static LootPool findPool(int targetIndex, int targetHash, List<LootPool> pools) {
      if (targetIndex > -1 && targetIndex < pools.size() + 1) {
         LootPool pool = pools.get(targetIndex);
         if (pool instanceof ILootPoolHooks hooks && hooks.bookshelf$matches(targetHash)) {
            return pool;
         }
      }

      List<LootPool> matchingHashes = new ArrayList<>();

      for (LootPool pool : pools) {
         if (pool instanceof ILootPoolHooks hooks && hooks.bookshelf$matches(targetHash)) {
            matchingHashes.add(pool);
         }
      }

      return matchingHashes.size() == 1 ? matchingHashes.getFirst() : null;
   }
}
