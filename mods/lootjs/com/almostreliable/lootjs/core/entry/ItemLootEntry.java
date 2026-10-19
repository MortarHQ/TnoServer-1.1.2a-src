package com.almostreliable.lootjs.core.entry;

import com.almostreliable.lootjs.core.filters.ItemFilter;
import com.almostreliable.lootjs.mixin.SetComponentsFunctionAccessor;
import com.almostreliable.lootjs.util.DebugInfo;
import com.almostreliable.lootjs.util.Utils;
import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.component.DataComponentPatch.Builder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class ItemLootEntry extends AbstractSimpleLootEntry<LootItem> implements SingleLootEntry {
   public ItemLootEntry(LootItem vanillaEntry) {
      super(vanillaEntry);
   }

   public ItemLootEntry(ItemStack itemStack) {
      super(new LootItem(itemStack.getItem().builtInRegistryHolder(), 1, 0, EMPTY_CONDITIONS, EMPTY_FUNCTIONS));
      if (itemStack.getCount() > 1) {
         this.getFunctions().setCount(ConstantValue.exactly(itemStack.getCount()));
      }

      if (!itemStack.isComponentsPatchEmpty()) {
         Builder builder = DataComponentPatch.builder();

         for (TypedDataComponent<?> component : itemStack.getComponents()) {
            builder.set(Utils.cast(component.type()), component.value());
         }

         SetComponentsFunction function = SetComponentsFunctionAccessor.lootjs$create(new ArrayList<>(), builder.build());
         this.getFunctions().addFunction(function);
      }
   }

   public ItemLootEntry(Item item, @Nullable NumberProvider count) {
      super(new LootItem(item.builtInRegistryHolder(), 1, 0, EMPTY_CONDITIONS, EMPTY_FUNCTIONS));
      if (count != null) {
         this.getFunctions().setCount(count);
      }
   }

   @Override
   public LootPoolEntryType getVanillaType() {
      return LootPoolEntries.ITEM;
   }

   public Item getItem() {
      return (Item)this.vanillaEntry.item.value();
   }

   public void setItem(Item item) {
      if (item == Items.AIR) {
         throw new IllegalStateException("Vanilla Loot Entry cannot be set to AIR, consider using LootEntry.empty()");
      }

      this.vanillaEntry.item = item.builtInRegistryHolder();
   }

   @Override
   public ItemStack createRandomItem(LootContext context) {
      for (LootItemCondition condition : this.getConditions()) {
         if (!condition.test(context)) {
            return ItemStack.EMPTY;
         }
      }

      ItemStack item = new ItemStack(this.getItem());

      for (LootItemFunction function : this.getFunctions()) {
         item = (ItemStack)function.apply(item, context);
      }

      return item;
   }

   public boolean test(ItemFilter filter) {
      return filter.test(new ItemStack(this.getItem()));
   }

   public ItemLootEntry addCondition(LootItemCondition condition) {
      this.getConditions().add(condition);
      return this;
   }

   @Override
   public void collectDebugInfo(DebugInfo info) {
      info.add("% Item: " + BuiltInRegistries.ITEM.getKey(this.getItem()));
      super.collectDebugInfo(info);
   }
}
