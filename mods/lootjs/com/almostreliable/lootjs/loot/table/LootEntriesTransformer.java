package com.almostreliable.lootjs.loot.table;

import com.almostreliable.lootjs.core.entry.ItemLootEntry;
import com.almostreliable.lootjs.core.entry.SimpleLootEntry;
import com.almostreliable.lootjs.core.entry.TableReferenceLootEntry;
import com.almostreliable.lootjs.core.entry.TagLootEntry;
import com.almostreliable.lootjs.core.filters.IdFilter;
import com.almostreliable.lootjs.core.filters.ItemFilter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import net.minecraft.world.item.Item;

public interface LootEntriesTransformer {
   LootEntriesTransformer modifyEntry(UnaryOperator<SimpleLootEntry> var1, boolean var2);

   default LootEntriesTransformer modifyEntry(UnaryOperator<SimpleLootEntry> onTransform) {
      this.modifyEntry(onTransform, true);
      return this;
   }

   default LootEntriesTransformer modifyItemEntry(UnaryOperator<ItemLootEntry> onTransform) {
      this.modifyEntry(le -> le instanceof ItemLootEntry ile ? onTransform.apply(ile) : le);
      return this;
   }

   LootEntriesTransformer removeEntry(Predicate<SimpleLootEntry> var1, boolean var2);

   default LootEntriesTransformer removeEntry(Predicate<SimpleLootEntry> onRemove) {
      this.removeEntry(onRemove, true);
      return this;
   }

   default LootEntriesTransformer removeItem(ItemFilter filter) {
      this.removeItem(filter, true);
      return this;
   }

   default LootEntriesTransformer removeItem(ItemFilter filter, boolean deepRemove) {
      this.removeEntry(entry -> entry instanceof ItemLootEntry ile && ile.test(filter), deepRemove);
      return this;
   }

   default LootEntriesTransformer removeTag(String tag) {
      this.removeTag(tag, true);
      return this;
   }

   default LootEntriesTransformer removeTag(String tag, boolean deepRemove) {
      this.removeEntry(entry -> entry instanceof TagLootEntry tle && tle.isTag(tag), deepRemove);
      return this;
   }

   default LootEntriesTransformer removeReference(IdFilter filter) {
      this.removeReference(filter, true);
      return this;
   }

   default LootEntriesTransformer removeReference(IdFilter filter, boolean deepRemove) {
      this.removeEntry(entry -> entry instanceof TableReferenceLootEntry d && filter.test(d.getLocation()), deepRemove);
      return this;
   }

   default LootEntriesTransformer replaceItem(ItemFilter filter, Item item) {
      this.replaceItem(filter, item, true);
      return this;
   }

   default LootEntriesTransformer replaceItem(ItemFilter filter, Item item, boolean deepReplace) {
      this.modifyEntry(entry -> {
         if (entry instanceof ItemLootEntry ile && ile.test(filter)) {
            ile.setItem(item);
         }

         return entry;
      }, deepReplace);
      return this;
   }

   default boolean hasItem(ItemFilter filter) {
      AtomicBoolean hasItem = new AtomicBoolean(false);
      this.modifyItemEntry(ile -> {
         if (hasItem.get() || ile.test(filter)) {
            hasItem.set(true);
         }

         return ile;
      });
      return hasItem.get();
   }
}
