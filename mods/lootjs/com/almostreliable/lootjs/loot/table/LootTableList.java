package com.almostreliable.lootjs.loot.table;

import com.almostreliable.lootjs.core.entry.SimpleLootEntry;
import com.almostreliable.lootjs.loot.LootFunctionList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;

public class LootTableList implements LootEntriesTransformer, Iterable<MutableLootTable> {
   private final List<MutableLootTable> tables;

   public LootTableList(List<MutableLootTable> tables) {
      this.tables = tables;
   }

   public List<MutableLootTable> getTables() {
      return this.tables;
   }

   public LootTableList createPool(Consumer<MutableLootPool> onCreatePool) {
      this.getTables().forEach(table -> table.createPool(onCreatePool));
      return this;
   }

   public LootTableList firstPool(Consumer<MutableLootPool> onModifyPool) {
      this.getTables().forEach(table -> table.firstPool(onModifyPool));
      return this;
   }

   public LootTableList onDrop(PostLootAction postLootAction) {
      this.getTables().forEach(table -> table.onDrop(postLootAction));
      return this;
   }

   public LootTableList clear() {
      this.getTables().forEach(MutableLootTable::clear);
      return this;
   }

   public LootTableList apply(Consumer<LootFunctionList> onModifiers) {
      this.getTables().forEach(table -> table.apply(onModifiers));
      return this;
   }

   public LootTableList modifyEntry(UnaryOperator<SimpleLootEntry> onTransform, boolean deepTransform) {
      this.getTables().forEach(table -> table.modifyEntry(onTransform, deepTransform));
      return this;
   }

   public LootTableList removeEntry(Predicate<SimpleLootEntry> onRemove, boolean deepRemove) {
      this.getTables().forEach(table -> table.removeEntry(onRemove, deepRemove));
      return this;
   }

   @NotNull
   @Override
   public Iterator<MutableLootTable> iterator() {
      return this.getTables().iterator();
   }
}
