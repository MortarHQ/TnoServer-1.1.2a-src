package com.almostreliable.lootjs.loot;

import com.almostreliable.lootjs.core.filters.IdFilter;
import com.almostreliable.lootjs.util.DebugInfo;
import com.almostreliable.lootjs.util.ListHolder;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class LootConditionList
   extends ListHolder<LootItemCondition, LootItemCondition>
   implements LootConditionsContainer<LootConditionList>,
   Predicate<LootContext> {
   public LootConditionList() {
   }

   public LootConditionList(List<LootItemCondition> conditions) {
      super(conditions);
   }

   @Override
   public Iterator<LootItemCondition> iterator() {
      return this.elements.listIterator();
   }

   protected LootItemCondition wrap(LootItemCondition entry) {
      return entry;
   }

   protected LootItemCondition unwrap(LootItemCondition entry) {
      return entry;
   }

   public LootConditionList addCondition(LootItemCondition condition) {
      this.add(condition);
      return this;
   }

   public void collectDebugInfo(DebugInfo info) {
      if (!this.isEmpty()) {
         info.add("% Conditions: [");
         info.push();

         for (LootItemCondition entry : this) {
            ResourceLocation key = BuiltInRegistries.LOOT_CONDITION_TYPE.getKey(entry.getType());
            if (key != null) {
               info.add(key.toString());
            }
         }

         info.pop();
         info.add("]");
      }
   }

   public boolean test(LootContext context) {
      for (LootItemCondition condition : this) {
         if (!condition.test(context)) {
            return false;
         }
      }

      return true;
   }

   public boolean remove(IdFilter type) {
      return this.elements.removeIf(element -> type.test(BuiltInRegistries.LOOT_CONDITION_TYPE.getKey(element.getType())));
   }

   public boolean contains(LootItemConditionType type) {
      return this.indexOf(type) != -1;
   }

   public int indexOf(LootItemConditionType type) {
      for (int i = 0; i < this.elements.size(); i++) {
         if (this.elements.get(i).getType().equals(type)) {
            return i;
         }
      }

      return -1;
   }

   public int lastIndexOf(LootItemConditionType type) {
      for (int i = this.elements.size() - 1; i >= 0; i--) {
         if (this.elements.get(i).getType().equals(type)) {
            return i;
         }
      }

      return -1;
   }
}
