package com.almostreliable.lootjs.core.entry;

import com.almostreliable.lootjs.loot.LootConditionList;
import com.almostreliable.lootjs.loot.LootEntryList;
import com.almostreliable.lootjs.loot.extension.CompositeEntryBaseExtension;
import com.almostreliable.lootjs.loot.table.LootEntriesTransformer;
import com.almostreliable.lootjs.loot.table.LootEntryAppender;
import com.almostreliable.lootjs.util.DebugInfo;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.world.level.storage.loot.entries.CompositeEntryBase;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class CompositeLootEntry implements LootEntry, LootEntriesTransformer, LootEntryAppender {
   private final CompositeEntryBase vanillaEntry;
   @Nullable
   private LootEntryList entries;
   @Nullable
   private LootConditionList conditions;

   public CompositeLootEntry(CompositeEntryBase vanillaEntry) {
      this.vanillaEntry = vanillaEntry;
   }

   public CompositeLootEntry(CompositeEntryBase vanillaEntry, LootEntryList entries, LootConditionList conditions) {
      this.vanillaEntry = vanillaEntry;
      this.entries = entries;
      this.conditions = conditions;
   }

   public LootEntryList getEntries() {
      if (this.entries == null) {
         this.entries = new LootEntryList(((CompositeEntryBaseExtension)this.vanillaEntry).lootjs$getEntries());
      }

      return this.entries;
   }

   public CompositeLootEntry entries(Consumer<LootEntryList> callback) {
      callback.accept(this.getEntries());
      return this;
   }

   @Override
   public LootPoolEntryType getVanillaType() {
      return this.vanillaEntry.getType();
   }

   public CompositeEntryBase getVanillaEntry() {
      return this.vanillaEntry;
   }

   public CompositeLootEntry when(Consumer<LootConditionList> callback) {
      callback.accept(this.getConditions());
      return this;
   }

   @Override
   public LootConditionList getConditions() {
      if (this.conditions == null) {
         this.conditions = new LootConditionList(this.vanillaEntry.conditions);
         this.vanillaEntry.conditions = this.conditions.getElements();
         this.vanillaEntry.compositeCondition = this.conditions;
      }

      return this.conditions;
   }

   @Override
   public void collectDebugInfo(DebugInfo info) {
      info.add(this.getType().toString());
      info.push();
      this.entries(entries -> entries.collectDebugInfo(info));
      this.when(conditions -> conditions.collectDebugInfo(info));
      info.pop();
   }

   public CompositeLootEntry addEntry(LootEntry entry) {
      this.entries(entries -> entries.add(entry));
      return this;
   }

   public CompositeLootEntry modifyEntry(UnaryOperator<SimpleLootEntry> onTransform, boolean deepTransform) {
      this.getEntries().modifyEntry(onTransform, deepTransform);
      return this;
   }

   public CompositeLootEntry removeEntry(Predicate<SimpleLootEntry> onRemove, boolean deepRemove) {
      this.getEntries().removeEntry(onRemove, deepRemove);
      return this;
   }

   public CompositeLootEntry addCondition(LootItemCondition condition) {
      this.getConditions().add(condition);
      return this;
   }
}
