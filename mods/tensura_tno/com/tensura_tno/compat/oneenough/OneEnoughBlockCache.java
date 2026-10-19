package com.tensura_tno.compat.oneenough;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public final class OneEnoughBlockCache<B, L> {
   private final OneEnoughBlockCache.BlockRegistry<B, L> registry;
   private volatile OneEnoughBlockCache.Snapshot<B, L> current = new OneEnoughBlockCache.Snapshot<>(Map.of(), Map.of());

   public OneEnoughBlockCache(OneEnoughBlockCache.BlockRegistry<B, L> registry) {
      this.registry = registry;
   }

   public synchronized void replaceRules(Map<String, String> blocks, Map<String, String> tags) {
      this.current = new OneEnoughBlockCache.Snapshot<>(immutableCopy(blocks), immutableCopy(tags));
   }

   public synchronized void invalidateResults() {
      OneEnoughBlockCache.Snapshot<B, L> previous = this.current;
      this.current = new OneEnoughBlockCache.Snapshot<>(previous.blocks, previous.tags);
   }

   public Optional<B> resolve(B source, L lookup) {
      if (source == null) {
         return Optional.empty();
      }

      OneEnoughBlockCache.Snapshot<B, L> snapshot;
      Optional<B> result;
      do {
         snapshot = this.current;
         if (snapshot.blocks.isEmpty() && snapshot.tags.isEmpty()) {
            return Optional.empty();
         }

         ConcurrentHashMap<B, Optional<B>> results = snapshot.resultsFor(lookup);
         result = results.get(source);
         if (result == null) {
            String direct = snapshot.blocks.get(this.registry.idOf(source));
            B resolved = direct != null ? this.registry.findBlock(direct) : this.resolveTags(snapshot, source, lookup);
            result = Optional.ofNullable(resolved);
            Optional<B> existing = results.putIfAbsent(source, result);
            if (existing != null) {
               result = existing;
            }
         }
      } while (snapshot != this.current);

      return result;
   }

   public Optional<B> resolveDirect(B source) {
      if (source == null) {
         return Optional.empty();
      }

      OneEnoughBlockCache.Snapshot<B, L> snapshot = this.current;
      if (snapshot.blocks.isEmpty()) {
         return Optional.empty();
      }

      String target = snapshot.blocks.get(this.registry.idOf(source));
      return target == null ? Optional.empty() : Optional.ofNullable(this.registry.findBlock(target));
   }

   public Optional<B> resolveByTags(B source, L lookup) {
      return source == null ? Optional.empty() : Optional.ofNullable(this.resolveTags(this.current, source, lookup));
   }

   private B resolveTags(OneEnoughBlockCache.Snapshot<B, L> snapshot, B source, L lookup) {
      for (Entry<String, String> rule : snapshot.tags.entrySet()) {
         if (this.registry.hasTag(source, lookup, rule.getKey())) {
            B target = this.registry.findBlock(rule.getValue());
            if (target != null) {
               return target;
            }
         }
      }

      return null;
   }

   private static Map<String, String> immutableCopy(Map<String, String> map) {
      return Collections.unmodifiableMap(new LinkedHashMap<>(map));
   }

   public interface BlockRegistry<B, L> {
      String idOf(B var1);

      B findBlock(String var1);

      boolean hasTag(B var1, L var2, String var3);
   }

   private static final class LookupResults<B, L> {
      private final L lookup;
      private final ConcurrentHashMap<B, Optional<B>> results = new ConcurrentHashMap<>();

      private LookupResults(L lookup) {
         this.lookup = lookup;
      }
   }

   private static final class Snapshot<B, L> {
      private final Map<String, String> blocks;
      private final Map<String, String> tags;
      private final Map<L, OneEnoughBlockCache.LookupResults<B, L>> lookups = new IdentityHashMap<>();
      private volatile OneEnoughBlockCache.LookupResults<B, L> lastLookup;

      private Snapshot(Map<String, String> blocks, Map<String, String> tags) {
         this.blocks = blocks;
         this.tags = tags;
      }

      private ConcurrentHashMap<B, Optional<B>> resultsFor(L lookup) {
         OneEnoughBlockCache.LookupResults<B, L> last = this.lastLookup;
         if (last != null && last.lookup == lookup) {
            return last.results;
         }

         synchronized (this.lookups) {
            OneEnoughBlockCache.LookupResults<B, L> found = this.lookups.get(lookup);
            if (found == null) {
               found = new OneEnoughBlockCache.LookupResults<>(lookup);
               this.lookups.put(lookup, found);
            }

            this.lastLookup = found;
            return found.results;
         }
      }
   }
}
