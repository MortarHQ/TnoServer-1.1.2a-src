package com.almostreliable.lootjs.loot.modifier;

import com.almostreliable.lootjs.core.LootBucket;
import com.almostreliable.lootjs.core.filters.ItemFilter;
import com.almostreliable.lootjs.loot.LootActionContainer;
import com.almostreliable.lootjs.loot.LootConditionsContainer;
import com.almostreliable.lootjs.loot.LootFunctionsContainer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class GroupedLootAction implements LootAction {
   private final List<LootAction> actions;
   @Nullable
   private final ItemFilter containsLootFilter;
   private final boolean exact;
   private final Predicate<LootContext> compositeCondition;
   private final BiFunction<ItemStack, LootContext, ItemStack> compositeFunction;
   private final NumberProvider rolls;
   private final List<LootItemCondition> conditions;
   private final List<LootItemFunction> functions;

   public GroupedLootAction(
      NumberProvider rolls,
      List<LootItemCondition> conditions,
      List<LootItemFunction> functions,
      Collection<LootAction> actions,
      @Nullable ItemFilter containsLootFilter,
      boolean exact
   ) {
      this.rolls = rolls;
      this.conditions = List.copyOf(conditions);
      this.compositeCondition = Util.allOf(this.conditions);
      this.functions = List.copyOf(functions);
      this.compositeFunction = LootItemFunctions.compose(this.functions);
      this.actions = List.copyOf(actions);
      this.containsLootFilter = containsLootFilter;
      this.exact = exact;
   }

   public NumberProvider rolls() {
      return this.rolls;
   }

   public boolean exact() {
      return this.exact;
   }

   @Nullable
   public ItemFilter containsLootFilter() {
      return this.containsLootFilter;
   }

   public List<LootItemFunction> functions() {
      return this.functions;
   }

   public List<LootItemCondition> conditions() {
      return this.conditions;
   }

   public List<LootAction> actions() {
      return this.actions;
   }

   @Override
   public void apply(LootContext context, LootBucket loot) {
      if (this.matchLoot(loot)) {
         int r = this.rolls().getInt(context);

         for (int i = 0; i < r; i++) {
            if (this.compositeCondition.test(context)) {
               for (LootAction action : this.actions()) {
                  action.apply(context, loot);
               }

               if (!this.functions().isEmpty()) {
                  loot.modifyItems(itemStack -> this.compositeFunction.apply(itemStack, context));
               }
            }
         }
      }
   }

   private boolean matchLoot(LootBucket loot) {
      return this.exact() ? this.matchExact(loot) : this.match(loot);
   }

   private boolean match(LootBucket loot) {
      ItemFilter containsLootFilter = this.containsLootFilter();
      if (containsLootFilter == null) {
         return true;
      }

      for (ItemStack itemStack : loot) {
         if (containsLootFilter.test(itemStack)) {
            return true;
         }
      }

      return false;
   }

   private boolean matchExact(LootBucket loot) {
      if (this.containsLootFilter == null) {
         return true;
      }

      for (ItemStack itemStack : loot) {
         if (!this.containsLootFilter.test(itemStack)) {
            return false;
         }
      }

      return true;
   }

   public static class Builder
      implements LootConditionsContainer<GroupedLootAction.Builder>,
      LootFunctionsContainer<GroupedLootAction.Builder>,
      LootActionContainer<GroupedLootAction.Builder> {
      protected final List<LootAction> actions = new ArrayList<>();
      protected final List<LootItemCondition> conditions = new ArrayList<>();
      protected final List<LootItemFunction> functions = new ArrayList<>();
      protected NumberProvider rolls = ConstantValue.exactly(1.0F);
      @Nullable
      protected ItemFilter containsLootFilter = null;
      protected boolean exact = false;
      @Nullable
      private final ItemFilter lootItemsFilter;

      public Builder(ItemFilter lootItemsFilter) {
         this.lootItemsFilter = lootItemsFilter;
      }

      public Builder() {
         this.lootItemsFilter = null;
      }

      public GroupedLootAction.Builder rolls(NumberProvider rolls) {
         this.rolls = rolls;
         return this;
      }

      public GroupedLootAction.Builder containsLoot(ItemFilter filter) {
         this.containsLootFilter = filter;
         return this;
      }

      public GroupedLootAction.Builder containsLoot(ItemFilter filter, boolean exact) {
         this.containsLootFilter = filter;
         this.exact = exact;
         return this;
      }

      public GroupedLootAction.Builder addCondition(LootItemCondition condition) {
         this.conditions.add(condition);
         return this;
      }

      public GroupedLootAction.Builder addAction(LootAction action) {
         this.actions.add(action);
         return this;
      }

      public GroupedLootAction.Builder addFunction(LootItemFunction lootItemFunction) {
         this.functions.add(lootItemFunction);
         return this;
      }

      public GroupedLootAction build() {
         return this.lootItemsFilter != null
            ? new GroupedLootAction.Filtered(
               this.rolls, this.conditions, this.functions, this.actions, this.lootItemsFilter, this.containsLootFilter, this.exact
            )
            : new GroupedLootAction(this.rolls, this.conditions, this.functions, this.actions, this.containsLootFilter, this.exact);
      }
   }

   public static class Filtered extends GroupedLootAction {
      private final ItemFilter lootItemsFilter;

      public Filtered(
         NumberProvider rolls,
         List<LootItemCondition> conditions,
         List<LootItemFunction> functions,
         Collection<LootAction> actions,
         ItemFilter lootItemsFilter,
         @Nullable ItemFilter containsLootFilter,
         boolean exact
      ) {
         super(rolls, conditions, functions, actions, containsLootFilter, exact);
         this.lootItemsFilter = lootItemsFilter;
      }

      @Override
      public void apply(LootContext context, LootBucket loot) {
         LootBucket filteredLoot = loot.extract(this.lootItemsFilter);
         super.apply(context, filteredLoot);
         loot.merge(filteredLoot);
      }
   }
}
