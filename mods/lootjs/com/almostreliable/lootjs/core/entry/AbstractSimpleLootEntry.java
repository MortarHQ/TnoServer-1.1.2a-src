package com.almostreliable.lootjs.core.entry;

import com.almostreliable.lootjs.loot.LootConditionList;
import com.almostreliable.lootjs.loot.LootFunctionList;
import com.almostreliable.lootjs.util.DebugInfo;
import javax.annotation.Nullable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;

public abstract class AbstractSimpleLootEntry<E extends LootPoolSingletonContainer> implements SimpleLootEntry {
   protected final E vanillaEntry;
   @Nullable
   private LootConditionList conditions;
   @Nullable
   private LootFunctionList functions;

   public AbstractSimpleLootEntry(E vanillaEntry) {
      this.vanillaEntry = vanillaEntry;
   }

   public AbstractSimpleLootEntry(E vanillaEntry, LootConditionList conditions, LootFunctionList functions) {
      this(vanillaEntry);
      this.conditions = conditions;
      this.functions = functions;
   }

   @Override
   public LootPoolEntryType getVanillaType() {
      return this.vanillaEntry.getType();
   }

   public E getVanillaEntry() {
      return this.vanillaEntry;
   }

   @Override
   public LootFunctionList getFunctions() {
      if (this.functions == null) {
         this.functions = new LootFunctionList(this.vanillaEntry.functions);
         this.vanillaEntry.functions = this.functions.getElements();
         this.vanillaEntry.compositeFunction = this.functions;
      }

      return this.functions;
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
   public void setWeight(int weight) {
      this.vanillaEntry.weight = Math.max(1, weight);
   }

   @Override
   public int getWeight() {
      return this.vanillaEntry.weight;
   }

   @Override
   public void setQuality(int quality) {
      this.vanillaEntry.quality = Math.max(0, quality);
   }

   @Override
   public int getQuality() {
      return this.vanillaEntry.quality;
   }

   @Override
   public void collectDebugInfo(DebugInfo info) {
      info.push();
      this.when(conditions -> conditions.collectDebugInfo(info));
      this.getFunctions().collectDebugInfo(info);
      info.pop();
   }
}
