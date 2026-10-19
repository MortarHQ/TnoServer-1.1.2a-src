package com.almostreliable.lootjs.loot;

import com.almostreliable.lootjs.LootJS;
import com.almostreliable.lootjs.core.entry.CompositeLootEntry;
import com.almostreliable.lootjs.core.entry.LootEntry;
import com.almostreliable.lootjs.core.entry.SimpleLootEntry;
import com.almostreliable.lootjs.core.filters.IdFilter;
import com.almostreliable.lootjs.loot.table.LootEntriesTransformer;
import com.almostreliable.lootjs.loot.table.LootEntryAppender;
import com.almostreliable.lootjs.util.DebugInfo;
import com.almostreliable.lootjs.util.ListHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;

public class LootEntryList extends ListHolder<LootEntry, LootPoolEntryContainer> implements LootEntriesTransformer, LootEntryAppender {
   public LootEntryList() {
   }

   public LootEntryList(LootEntry... entries) {
      super(new ArrayList<>(entries.length));

      for (LootEntry entry : entries) {
         this.add(entry);
      }
   }

   public LootEntryList(List<LootPoolEntryContainer> entries) {
      super(entries);
   }

   public ListIterator<LootEntry> iterator() {
      return new LootEntryList.ListIt(this.elements);
   }

   protected LootEntry wrap(LootPoolEntryContainer entry) {
      return LootEntry.ofVanilla(entry);
   }

   protected LootPoolEntryContainer unwrap(LootEntry entry) {
      return entry.getVanillaEntry();
   }

   public List<LootPoolEntryContainer> createVanillaArray() {
      List<LootPoolEntryContainer> entries = new ArrayList<>(this.size());

      for (LootEntry e : this) {
         entries.add(e.getVanillaEntry());
      }

      return entries;
   }

   public void collectDebugInfo(DebugInfo info) {
      if (!this.isEmpty()) {
         info.add("% Entries: [");
         info.push();
         info.add("{");
         info.push();

         for (LootEntry entry : this) {
            entry.collectDebugInfo(info);
         }

         info.pop();
         info.add("}");
         info.pop();
         info.add("]");
      }
   }

   public void modify(UnaryOperator<LootEntry> onTransform) {
      ListIterator<LootEntry> it = this.iterator();

      while (it.hasNext()) {
         try {
            LootEntry entry = it.next();
            LootEntry transformed = onTransform.apply(entry);
            if (transformed != entry) {
               it.set(transformed);
            }
         } catch (Exception e) {
            LootJS.LOG.error("Failed to transform entry", e);
         }
      }
   }

   public LootEntryList modifyEntry(UnaryOperator<SimpleLootEntry> onTransform, boolean deepTransform) {
      this.modify(entry -> {
         if (entry instanceof LootEntriesTransformer helper && deepTransform) {
            helper.modifyEntry(onTransform, true);
            return entry;
         } else {
            return entry instanceof SimpleLootEntry le ? onTransform.apply(le) : entry;
         }
      });
      return this;
   }

   public LootEntryList removeEntry(Predicate<SimpleLootEntry> filter, boolean deepRemove) {
      ListIterator<LootEntry> it = this.iterator();

      while (it.hasNext()) {
         LootEntry entry = it.next();
         if (entry instanceof CompositeLootEntry composite && deepRemove) {
            composite.removeEntry(filter, true);
         } else if (entry instanceof SimpleLootEntry le && filter.test(le)) {
            it.remove();
         }
      }

      return this;
   }

   public LootEntryList addEntry(LootEntry entry) {
      this.add(entry);
      return this;
   }

   public boolean remove(IdFilter type) {
      return this.elements.removeIf(element -> type.test(BuiltInRegistries.LOOT_POOL_ENTRY_TYPE.getKey(element.getType())));
   }

   public boolean contains(LootPoolEntryType type) {
      return this.indexOf(type) != -1;
   }

   public int indexOf(LootPoolEntryType type) {
      for (int i = 0; i < this.elements.size(); i++) {
         if (this.elements.get(i).getType().equals(type)) {
            return i;
         }
      }

      return -1;
   }

   public int lastIndexOf(LootPoolEntryType type) {
      for (int i = this.elements.size() - 1; i >= 0; i--) {
         if (this.elements.get(i).getType().equals(type)) {
            return i;
         }
      }

      return -1;
   }

   private static class ListIt implements ListIterator<LootEntry> {
      private final ListIterator<LootPoolEntryContainer> it;

      public ListIt(List<LootPoolEntryContainer> entries) {
         this.it = entries.listIterator();
      }

      @Override
      public boolean hasNext() {
         return this.it.hasNext();
      }

      public LootEntry next() {
         return LootEntry.ofVanilla(this.it.next());
      }

      @Override
      public boolean hasPrevious() {
         return this.it.hasPrevious();
      }

      public LootEntry previous() {
         return LootEntry.ofVanilla(this.it.previous());
      }

      @Override
      public int nextIndex() {
         return this.it.nextIndex();
      }

      @Override
      public int previousIndex() {
         return this.it.previousIndex();
      }

      @Override
      public void remove() {
         this.it.remove();
      }

      public void set(LootEntry lootEntry) {
         this.it.set(lootEntry.getVanillaEntry());
      }

      public void add(LootEntry lootEntry) {
         this.it.add(lootEntry.getVanillaEntry());
      }
   }
}
