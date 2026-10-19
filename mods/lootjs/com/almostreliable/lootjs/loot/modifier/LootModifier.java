package com.almostreliable.lootjs.loot.modifier;

import com.almostreliable.lootjs.core.LootBucket;
import com.almostreliable.lootjs.core.LootType;
import com.almostreliable.lootjs.core.filters.ItemFilter;
import com.almostreliable.lootjs.core.filters.LootTableFilter;
import com.almostreliable.lootjs.loot.extension.LootContextExtension;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class LootModifier extends GroupedLootAction {
   private final Predicate<LootContext> runPredicate;
   private final String name;

   public LootModifier(
      Predicate<LootContext> runPredicate,
      NumberProvider rolls,
      List<LootItemCondition> conditions,
      List<LootItemFunction> functions,
      Collection<LootAction> handlers,
      String name,
      ItemFilter containsLootFilter,
      boolean exact
   ) {
      super(rolls, conditions, functions, handlers, containsLootFilter, exact);
      this.runPredicate = runPredicate;
      this.name = name;
   }

   public String name() {
      return this.name;
   }

   public Predicate<LootContext> runPredicate() {
      return this.runPredicate;
   }

   public void run(LootContext context, LootBucket loot) {
      if (this.runPredicate().test(context)) {
         super.apply(context, loot);
      }
   }

   public record BlockFiltered(Predicate<BlockState> predicate) implements Predicate<LootContext> {
      public boolean test(LootContext context) {
         BlockState blockState = (BlockState)context.getParamOrNull(LootContextParams.BLOCK_STATE);
         return blockState != null && this.predicate.test(blockState) && LootContextExtension.cast(context).lootjs$isType(LootType.BLOCK);
      }
   }

   public static class Builder extends GroupedLootAction.Builder {
      private final Predicate<LootContext> shouldRun;
      private String name;

      public Builder(Predicate<LootContext> shouldRun, String name) {
         this.shouldRun = shouldRun;
         this.name = name;
      }

      public LootModifier.Builder name(String name) {
         this.name = name;
         return this;
      }

      public LootModifier build() {
         return new LootModifier(this.shouldRun, this.rolls, this.conditions, this.functions, this.actions, this.name, this.containsLootFilter, this.exact);
      }
   }

   public record EntityFiltered(HolderSet<EntityType<?>> entities) implements Predicate<LootContext> {
      public boolean test(LootContext context) {
         Entity entity = (Entity)context.getParamOrNull(LootContextParams.THIS_ENTITY);
         return entity != null
            && this.entities.contains(entity.getType().builtInRegistryHolder())
            && LootContextExtension.cast(context).lootjs$isType(LootType.ENTITY);
      }
   }

   public record TableFiltered(LootTableFilter[] filters) implements Predicate<LootContext> {
      public boolean test(LootContext context) {
         for (LootTableFilter filter : this.filters) {
            if (filter.test(context)) {
               return true;
            }
         }

         return false;
      }
   }

   public record TypeFiltered(LootType[] types) implements Predicate<LootContext> {
      public boolean test(LootContext context) {
         LootContextExtension extension = LootContextExtension.cast(context);

         for (LootType type : this.types) {
            if (extension.lootjs$isType(type)) {
               return true;
            }
         }

         return false;
      }
   }
}
