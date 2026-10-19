package com.almostreliable.lootjs.loot.table;

import com.almostreliable.lootjs.core.entry.LootEntry;
import com.almostreliable.lootjs.core.entry.SimpleLootEntry;
import com.almostreliable.lootjs.loot.LootConditionList;
import com.almostreliable.lootjs.loot.LootEntryList;
import com.almostreliable.lootjs.loot.LootFunctionList;
import com.almostreliable.lootjs.loot.extension.LootPoolExtension;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class MutableLootPool implements LootEntriesTransformer, LootEntryAppender {
   @Nullable
   private LootConditionList conditions;
   @Nullable
   private LootFunctionList functions;
   @Nullable
   private LootEntryList entries;
   private final LootPool vanillaPool;

   public MutableLootPool(LootPool pool) {
      this.vanillaPool = pool;
   }

   @Nullable
   public String getName() {
      return this.vanillaPool.getName();
   }

   public MutableLootPool name(String name) {
      LootPoolExtension.cast(this.vanillaPool).lootjs$setName(name);
      return this;
   }

   public MutableLootPool rolls(NumberProvider rolls) {
      this.vanillaPool.setRolls(rolls);
      return this;
   }

   public MutableLootPool bonusRolls(NumberProvider bonusRolls) {
      this.vanillaPool.setBonusRolls(bonusRolls);
      return this;
   }

   public MutableLootPool when(Consumer<LootConditionList> onConditions) {
      onConditions.accept(this.getConditions());
      return this;
   }

   public MutableLootPool apply(Consumer<LootFunctionList> onModifiers) {
      onModifiers.accept(this.getFunctions());
      return this;
   }

   public LootConditionList getConditions() {
      if (this.conditions == null) {
         this.conditions = LootPoolExtension.cast(this.vanillaPool).lootjs$getConditions();
      }

      return this.conditions;
   }

   public LootFunctionList getFunctions() {
      if (this.functions == null) {
         this.functions = LootPoolExtension.cast(this.vanillaPool).lootjs$getFunctions();
      }

      return this.functions;
   }

   public LootEntryList getEntries() {
      if (this.entries == null) {
         this.entries = LootPoolExtension.cast(this.vanillaPool).lootjs$getEntries();
      }

      return this.entries;
   }

   public LootPool getVanillaPool() {
      return this.vanillaPool;
   }

   public MutableLootPool addEntry(LootEntry entry) {
      this.getEntries().add(entry);
      return this;
   }

   public MutableLootPool modifyEntry(UnaryOperator<SimpleLootEntry> onTransform, boolean deepTransform) {
      this.getEntries().modifyEntry(onTransform, deepTransform);
      return this;
   }

   public MutableLootPool removeEntry(Predicate<SimpleLootEntry> onRemove, boolean deepRemove) {
      this.getEntries().removeEntry(onRemove, deepRemove);
      return this;
   }
}
